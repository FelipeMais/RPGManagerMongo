package dao.mongo;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.Updates;
import contracts.CharacterDAO;
import model.Character;
import model.dto.CharacterWeight;
import model.relationship.InventoryItem;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CharacterMongoDAO implements CharacterDAO {

    private final MongoCollection<Document> characterCollection;
    private final MongoCollection<Document> sheetCollection;
    private final InventoryMongoDAO inventoryDAO;

    public CharacterMongoDAO(MongoDatabase database) {
        this.characterCollection = database.getCollection("personagem");
        this.sheetCollection = database.getCollection("fichas");
        this.inventoryDAO = new InventoryMongoDAO(database);
    }

    @Override
    public Integer insert(Character newCharacter) throws SQLException {
        try {
            Integer characterId = newCharacter.getId() != null ? newCharacter.getId() : nextCharacterId();

            Document doc = new Document("_id", characterId)
                    .append("id_jogador", newCharacter.getPlayerId())
                    .append("id_ficha", newCharacter.getSheetId())
                    .append("local_atual", newCharacter.getCurrentLocationId())
                    .append("nome_personagem", newCharacter.getName())
                    .append("pontos_vida", newCharacter.getHitPoints())
                    .append("pontos_mana", newCharacter.getManaPoints())
                    .append("historia", newCharacter.getHistory());

            characterCollection.insertOne(doc);

            persistInventory(characterId, newCharacter.getInventory());

            return characterId;
        } catch (MongoException e) {
            throw new SQLException("Erro ao inserir personagem no MongoDB", e);
        }
    }

    @Override
    public void update(Character character) throws SQLException {
        try {
            Bson filter = Filters.eq("_id", character.getId());
            Bson updates = Updates.combine(
                    Updates.set("id_jogador", character.getPlayerId()),
                    Updates.set("id_ficha", character.getSheetId()),
                    Updates.set("local_atual", character.getCurrentLocationId()),
                    Updates.set("nome_personagem", character.getName()),
                    Updates.set("pontos_vida", character.getHitPoints()),
                    Updates.set("pontos_mana", character.getManaPoints()),
                    Updates.set("historia", character.getHistory())
            );

            characterCollection.updateOne(filter, updates);

        } catch (MongoException e) {
            throw new SQLException("Erro ao atualizar personagem no MongoDB", e);
        }
    }

    @Override
    public void remove(Integer characterId) throws SQLException {
        try {
            inventoryDAO.removeByCharacterId(characterId);
            characterCollection.deleteOne(Filters.eq("_id", characterId));
        } catch (MongoException e) {
            throw new SQLException("Erro ao remover personagem no MongoDB", e);
        }
    }

    @Override
    public Character findById(Integer characterId) throws SQLException {
        try {
            Document doc = characterCollection.find(Filters.eq("_id", characterId)).first();
            if (doc == null) {
                return null;
            }
            return fromDocument(doc);
        } catch (MongoException e) {
            throw new SQLException("Erro ao buscar personagem por ID no MongoDB", e);
        }
    }

    @Override
    public List<Character> listAll() throws SQLException {
        List<Character> characters = new ArrayList<>();
        try (MongoCursor<Document> cursor = characterCollection.find().iterator()) {
            while (cursor.hasNext()) {
                characters.add(fromDocument(cursor.next()));
            }
        } catch (MongoException e) {
            throw new SQLException("Erro ao listar personagens no MongoDB", e);
        }
        return characters;
    }

    @Override
    public List<Character> findByClassAndSpecies(Integer classId, Integer speciesId) throws SQLException {
        List<Character> filteredCharacters = new ArrayList<>();

        try (MongoCursor<Document> cursor = characterCollection.find().iterator()) {
            while (cursor.hasNext()) {
                Document charDoc = cursor.next();
                Integer sheetId = charDoc.getInteger("id_ficha");

                if (sheetId != null) {
                    Document sheetDoc = sheetCollection.find(Filters.eq("_id", sheetId)).first();
                    if (sheetDoc != null) {
                        Integer charClassId = sheetDoc.getInteger("id_classe");
                        Integer charSpeciesId = sheetDoc.getInteger("id_especie");

                        boolean matchClass = (classId == 0 || classId.equals(charClassId));
                        boolean matchSpecies = (speciesId == 0 || speciesId.equals(charSpeciesId));

                        if (matchClass && matchSpecies) {
                            filteredCharacters.add(fromDocument(charDoc));
                        }
                    }
                }
            }
        } catch (MongoException e) {
            throw new SQLException("Erro ao filtrar personagens por classe e espécie no MongoDB", e);
        }

        return filteredCharacters;
    }

    @Override
    public List<CharacterWeight> findPersonagensComSobrecarga(BigDecimal limitePeso) throws SQLException {
        List<CharacterWeight> sobrecarregados = new ArrayList<>();

        List<Character> allCharacters = listAll();

        for (Character character : allCharacters) {
            BigDecimal pesoTotal = BigDecimal.ZERO;

            if (character.getInventory() != null) {
                for (InventoryItem invItem : character.getInventory()) {
                    if (invItem.getItem().getWeight() != null) {
                        BigDecimal pesoItem = invItem.getItem().getWeight();
                        BigDecimal quantidade = new BigDecimal(invItem.getQuantity());
                        pesoTotal = pesoTotal.add(pesoItem.multiply(quantidade));
                    }
                }
            }

            if (pesoTotal.compareTo(limitePeso) > 0) {
                sobrecarregados.add(new CharacterWeight(character, pesoTotal));
            }
        }

        return sobrecarregados;
    }

    private Integer nextCharacterId() {
        Document lastChar = characterCollection.find()
                .sort(Sorts.descending("_id"))
                .first();

        if (lastChar == null || lastChar.getInteger("_id") == null) {
            return 1;
        }
        return lastChar.getInteger("_id") + 1;
    }

    private void persistInventory(Integer characterId, List<InventoryItem> inventoryItems) throws SQLException {
        if (inventoryItems == null) {
            return;
        }
        for (InventoryItem inventoryItem : inventoryItems) {
            inventoryDAO.insert(inventoryItem);
        }
    }

    private Character fromDocument(Document doc) throws SQLException {
        Character character = new Character(
                doc.getInteger("_id"),
                doc.getInteger("id_jogador"),
                doc.getInteger("id_ficha"),
                doc.getInteger("local_atual"),
                doc.getString("nome_personagem"),
                doc.getInteger("pontos_vida"),
                doc.getInteger("pontos_mana"),
                doc.getString("historia"),
                inventoryDAO.findByCharacterId(doc.getInteger("_id")));

        return character;
    }
}