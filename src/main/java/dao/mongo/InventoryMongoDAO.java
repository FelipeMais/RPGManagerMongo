package dao.mongo;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
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

    private final MongoCollection<Document> inventoryCollection;
    private final MongoCollection<Document> itemsCollection;
    private final ItemAttributeMongoDAO itemAttributeDAO;

    public InventoryMongoDAO(MongoDatabase database) {
        this.inventoryCollection = database.getCollection("inventario");
        this.itemsCollection = database.getCollection("itens");
        this.itemAttributeDAO = new ItemAttributeMongoDAO(database);
    }

    @Override
    public void insert(InventoryItem inventoryItem) throws SQLException {
        try {
            Document doc = new Document("id_item", inventoryItem.getItemId())
                    .append("id_personagem", inventoryItem.getCharacterId())
                    .append("quantidade", inventoryItem.getQuantity());

            inventoryCollection.insertOne(doc);
        } catch (MongoException e) {
            throw new SQLException("Erro ao inserir item no inventário no MongoDB", e);
        }
    }

    @Override
    public void update(InventoryItem inventoryItem) throws SQLException {
        try {
            Bson filter = Filters.and(
                    Filters.eq("id_item", inventoryItem.getItemId()),
                    Filters.eq("id_personagem", inventoryItem.getCharacterId())
            );
            Bson updates = Updates.set("quantidade", inventoryItem.getQuantity());

            inventoryCollection.updateOne(filter, updates);
        } catch (MongoException e) {
            throw new SQLException("Erro ao atualizar item do inventário no MongoDB", e);
        }
    }

    @Override
    public void remove(Integer characterId, Integer itemId) throws SQLException {
        try {
            Bson filter = Filters.and(
                    Filters.eq("id_personagem", characterId),
                    Filters.eq("id_item", itemId)
            );
            inventoryCollection.deleteOne(filter);
        } catch (MongoException e) {
            throw new SQLException("Erro ao remover item do inventário no MongoDB", e);
        }
    }

    @Override
    public void removeByCharacterId(Integer characterId) throws SQLException {
        try {
            inventoryCollection.deleteMany(Filters.eq("id_personagem", characterId));
        } catch (MongoException e) {
            throw new SQLException("Erro ao limpar o inventário do personagem no MongoDB", e);
        }
    }

    @Override
    public InventoryItem findByCharacterIdAndItemId(Integer characterId, Integer itemId) throws SQLException {
        try {
            Bson filter = Filters.and(
                    Filters.eq("id_personagem", characterId),
                    Filters.eq("id_item", itemId)
            );

            Document invDoc = inventoryCollection.find(filter).first();
            if (invDoc == null) {
                return null;
            }

            return fromDocument(invDoc);
        } catch (MongoException e) {
            throw new SQLException("Erro ao buscar item específico no inventário no MongoDB", e);
        }
    }

    @Override
    public List<InventoryItem> findByCharacterId(Integer characterId) throws SQLException {
        List<InventoryItem> inventoryItems = new ArrayList<>();

        try (MongoCursor<Document> cursor = inventoryCollection.find(Filters.eq("id_personagem", characterId)).iterator()) {
            while (cursor.hasNext()) {
                inventoryItems.add(fromDocument(cursor.next()));
            }
        } catch (MongoException e) {
            throw new SQLException("Erro ao listar o inventário do personagem no MongoDB", e);
        }

        // Ordenação em memória pelo nome do item
        inventoryItems.sort((a, b) -> a.getItem().getName().compareToIgnoreCase(b.getItem().getName()));

        return inventoryItems;
    }

    // --- Método Auxiliar de Montagem Completa ---

    private InventoryItem fromDocument(Document invDoc) throws SQLException {
        Integer characterId = invDoc.getInteger("id_personagem");
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