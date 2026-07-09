package dao.mongo;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.Updates;
import contracts.CharacterSheetDAO;
import model.Ability;
import model.CharacterSheet;
import model.Magic;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CharacterSheetMongoDAO implements CharacterSheetDAO {

    private final MongoCollection<Document> collection;
    private final MagicMongoDAO magicDAO;
    private final AbilityMongoDAO abilityDAO;

    public CharacterSheetMongoDAO(MongoDatabase database) {
        this.collection = database.getCollection("fichas");
        this.magicDAO = new MagicMongoDAO(database);
        this.abilityDAO = new AbilityMongoDAO(database);
    }

    @Override
    public Integer insert(CharacterSheet newCharacterSheet) throws SQLException {
        try {
            Integer sheetId = newCharacterSheet.getId() != null ? newCharacterSheet.getId() : nextCharacterSheetId();

            Document doc = new Document("_id", sheetId)
                    .append("id_classe", newCharacterSheet.getClassId())
                    .append("id_especie", newCharacterSheet.getSpeciesId())
                    .append("pontos_vida_max", newCharacterSheet.getMaxHitPoints())
                    .append("pontos_mana_max", newCharacterSheet.getMaxManaPoints())
                    .append("forca", newCharacterSheet.getStrength())
                    .append("destreza", newCharacterSheet.getDexterity())
                    .append("constituicao", newCharacterSheet.getConstitution())
                    .append("inteligencia", newCharacterSheet.getIntelligence())
                    .append("sabedoria", newCharacterSheet.getWisdom())
                    .append("carisma", newCharacterSheet.getCharisma())
                    .append("nivel", newCharacterSheet.getLevel())
                    .append("magias_conhecidas", mapMagicsToIds(newCharacterSheet.getKnownMagics()))
                    .append("habilidades_conhecidas", mapAbilitiesToIds(newCharacterSheet.getKnownAbilities()));

            collection.insertOne(doc);
            return sheetId;
        } catch (MongoException e) {
            throw new SQLException("Erro ao inserir ficha no MongoDB", e);
        }
    }

    @Override
    public void update(CharacterSheet characterSheet) throws SQLException {
        try {
            Bson filter = Filters.eq("_id", characterSheet.getId());
            Bson updates = Updates.combine(
                    Updates.set("id_classe", characterSheet.getClassId()),
                    Updates.set("id_especie", characterSheet.getSpeciesId()),
                    Updates.set("pontos_vida_max", characterSheet.getMaxHitPoints()),
                    Updates.set("pontos_mana_max", characterSheet.getMaxManaPoints()),
                    Updates.set("forca", characterSheet.getStrength()),
                    Updates.set("destreza", characterSheet.getDexterity()),
                    Updates.set("constituicao", characterSheet.getConstitution()),
                    Updates.set("inteligencia", characterSheet.getIntelligence()),
                    Updates.set("sabedoria", characterSheet.getWisdom()),
                    Updates.set("carisma", characterSheet.getCharisma()),
                    Updates.set("nivel", characterSheet.getLevel()),
                    Updates.set("magias_conhecidas", mapMagicsToIds(characterSheet.getKnownMagics())),
                    Updates.set("habilidades_conhecidas", mapAbilitiesToIds(characterSheet.getKnownAbilities()))
            );

            collection.updateOne(filter, updates);
        } catch (MongoException e) {
            throw new SQLException("Erro ao atualizar ficha no MongoDB", e);
        }
    }

    @Override
    public void remove(Integer characterSheetId) throws SQLException {
        try {
            collection.deleteOne(Filters.eq("_id", characterSheetId));
        } catch (MongoException e) {
            throw new SQLException("Erro ao remover ficha no MongoDB", e);
        }
    }

    @Override
    public CharacterSheet findById(Integer characterSheetId) throws SQLException {
        try {
            Document doc = collection.find(Filters.eq("_id", characterSheetId)).first();
            if (doc == null) {
                return null;
            }
            return fromDocument(doc);
        } catch (MongoException e) {
            throw new SQLException("Erro ao buscar ficha por ID no MongoDB", e);
        }
    }

    @Override
    public List<CharacterSheet> listAll() throws SQLException {
        List<CharacterSheet> sheets = new ArrayList<>();

        try (MongoCursor<Document> cursor = collection.find().iterator()) {
            while (cursor.hasNext()) {
                sheets.add(fromDocument(cursor.next()));
            }
        } catch (MongoException e) {
            throw new SQLException("Erro ao listar fichas no MongoDB", e);
        }

        return sheets;
    }

    private Integer nextCharacterSheetId() {
        Document lastSheet = collection.find()
                .sort(Sorts.descending("_id"))
                .first();

        if (lastSheet == null || lastSheet.getInteger("_id") == null) {
            return 1;
        }
        return lastSheet.getInteger("_id") + 1;
    }

    private CharacterSheet fromDocument(Document doc) throws SQLException {
        List<Integer> magicIds = doc.getList("magias_conhecidas", Integer.class);
        List<Integer> abilityIds = doc.getList("habilidades_conhecidas", Integer.class);

        List<Magic> knownMagics = fetchMagicsByIds(magicIds);
        List<Ability> knownAbilities = fetchAbilitiesByIds(abilityIds);

        return new CharacterSheet(
                doc.getInteger("_id"),
                doc.getInteger("id_classe"),
                doc.getInteger("id_especie"),
                doc.getInteger("pontos_vida_max"),
                doc.getInteger("pontos_mana_max"),
                doc.getInteger("forca"),
                doc.getInteger("destreza"),
                doc.getInteger("constituicao"),
                doc.getInteger("inteligencia"),
                doc.getInteger("sabedoria"),
                doc.getInteger("carisma"),
                doc.getInteger("nivel"),
                knownMagics,
                knownAbilities
        );
    }


    private List<Integer> mapMagicsToIds(List<Magic> magics) {
        List<Integer> ids = new ArrayList<>();
        if (magics == null) return ids;
        for (Magic m : magics) {
            if (m.getId() != null) {
                ids.add(m.getId());
            }
        }
        return ids;
    }

    private List<Integer> mapAbilitiesToIds(List<Ability> abilities) {
        List<Integer> ids = new ArrayList<>();
        if (abilities == null) return ids;
        for (Ability a : abilities) {
            if (a.getId() != null) {
                ids.add(a.getId());
            }
        }
        return ids;
    }


    private List<Magic> fetchMagicsByIds(List<Integer> ids) throws SQLException {
        List<Magic> magics = new ArrayList<>();
        if (ids == null) return magics;
        for (Integer id : ids) {
            Magic m = magicDAO.findById(id);
            if (m != null) {
                magics.add(m);
            }
        }
        return magics;
    }

    private List<Ability> fetchAbilitiesByIds(List<Integer> ids) throws SQLException {
        List<Ability> abilities = new ArrayList<>();
        if (ids == null) return abilities;
        for (Integer id : ids) {
            Ability a = abilityDAO.findById(id);
            if (a != null) {
                abilities.add(a);
            }
        }
        return abilities;
    }
}