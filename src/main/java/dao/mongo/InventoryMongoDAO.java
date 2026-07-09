package dao.mongo;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import contracts.InventoryDAO;
import model.Item;
import model.relationship.InventoryItem;
import model.relationship.ItemAttribute;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InventoryMongoDAO implements InventoryDAO {

    private final MongoCollection<Document> characterCollection;
    private final MongoCollection<Document> itemsCollection;
    private final ItemAttributeMongoDAO itemAttributeDAO;

    public InventoryMongoDAO(MongoDatabase database) {
        this.characterCollection = database.getCollection("personagem");
        this.itemsCollection = database.getCollection("itens");
        this.itemAttributeDAO = new ItemAttributeMongoDAO(database);
    }

    @Override
    public void insert(InventoryItem inventoryItem) throws SQLException {
        try {
            Document newItem = new Document("id_item", inventoryItem.getItemId())
                    .append("quantidade", inventoryItem.getQuantity());

            characterCollection.updateOne(
                    Filters.eq("_id", inventoryItem.getCharacterId()),
                    Updates.push("inventario", newItem)
            );
        } catch (MongoException e) {
            throw new SQLException("Erro ao inserir item no inventário do personagem no MongoDB", e);
        }
    }

    @Override
    public void update(InventoryItem inventoryItem) throws SQLException {
        try {
            Bson filter = Filters.and(
                    Filters.eq("_id", inventoryItem.getCharacterId()),
                    Filters.eq("inventario.id_item", inventoryItem.getItemId())
            );

            Bson update = Updates.set("inventario.$.quantidade", inventoryItem.getQuantity());

            characterCollection.updateOne(filter, update);
        } catch (MongoException e) {
            throw new SQLException("Erro ao atualizar item do inventário no MongoDB", e);
        }
    }

    @Override
    public void remove(Integer characterId, Integer itemId) throws SQLException {
        try {
            characterCollection.updateOne(
                    Filters.eq("_id", characterId),
                    Updates.pull("inventario", new Document("id_item", itemId))
            );
        } catch (MongoException e) {
            throw new SQLException("Erro ao remover item do inventário no MongoDB", e);
        }
    }

    @Override
    public void removeByCharacterId(Integer characterId) throws SQLException {
        try {
            characterCollection.updateOne(
                    Filters.eq("_id", characterId),
                    Updates.set("inventario", new ArrayList<Document>())
            );
        } catch (MongoException e) {
            throw new SQLException("Erro ao limpar o inventário do personagem no MongoDB", e);
        }
    }

    @Override
    public InventoryItem findByCharacterIdAndItemId(Integer characterId, Integer itemId) throws SQLException {
        try {
            Document charDoc = characterCollection.find(Filters.eq("_id", characterId)).first();
            if (charDoc == null) {
                return null;
            }

            List<Document> inventario = charDoc.getList("inventario", Document.class);
            if (inventario == null) return null;

            for (Document invDoc : inventario) {
                if (itemId.equals(invDoc.getInteger("id_item"))) {
                    return fromEmbeddedDocument(characterId, invDoc);
                }
            }

            return null;
        } catch (MongoException e) {
            throw new SQLException("Erro ao buscar item específico no inventário no MongoDB", e);
        }
    }

    @Override
    public List<InventoryItem> findByCharacterId(Integer characterId) throws SQLException {
        List<InventoryItem> inventoryItems = new ArrayList<>();

        try {
            Document charDoc = characterCollection.find(Filters.eq("_id", characterId)).first();

            if (charDoc != null) {
                List<Document> inventario = charDoc.getList("inventario", Document.class);
                if (inventario != null) {
                    for (Document invDoc : inventario) {
                        inventoryItems.add(fromEmbeddedDocument(characterId, invDoc));
                    }
                }
            }
        } catch (MongoException e) {
            throw new SQLException("Erro ao listar o inventário do personagem no MongoDB", e);
        }

        inventoryItems.sort((a, b) -> a.getItem().getName().compareToIgnoreCase(b.getItem().getName()));

        return inventoryItems;
    }

    private InventoryItem fromEmbeddedDocument(Integer characterId, Document invDoc) throws SQLException {
        Integer itemId = invDoc.getInteger("id_item");
        Integer quantity = invDoc.getInteger("quantidade");

        Document itemDoc = itemsCollection.find(Filters.eq("_id", itemId)).first();

        if (itemDoc == null) {
            throw new SQLException("Inconsistência: O item de ID " + itemId + " não existe na coleção 'itens'.");
        }

        String itemName = itemDoc.getString("nome_item");
        String itemDescription = itemDoc.getString("descricao");
        BigDecimal weight = extractBigDecimal(itemDoc, "peso");
        BigDecimal monetaryValue = extractBigDecimal(itemDoc, "valor_monetario");

        List<ItemAttribute> attributes = itemAttributeDAO.findByItemId(itemId);

        return new InventoryItem(
                itemId,
                characterId,
                quantity,
                new Item(itemId, itemName, itemDescription, weight, monetaryValue, attributes)
        );
    }

    private BigDecimal extractBigDecimal(Document doc, String fieldName) {
        Number number = doc.get(fieldName, Number.class);
        if (number == null) {
            return BigDecimal.ZERO;
        }
        return new BigDecimal(number.toString());
    }
}