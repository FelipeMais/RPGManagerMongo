package dao.mongo;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.Updates;
import contracts.ItemDAO;
import model.Item;
import model.relationship.ItemAttribute;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemMongoDAO implements ItemDAO {

    private final MongoCollection<Document> collection;
    private final ItemAttributeMongoDAO itemAttributeDAO;

    public ItemMongoDAO(MongoDatabase database) {
        this.collection = database.getCollection("itens");
        this.itemAttributeDAO = new ItemAttributeMongoDAO(database);
    }

    @Override
    public void insert(Item newItem) throws SQLException {
        try {
            Integer itemId = newItem.getId() != null ? newItem.getId() : nextItemId();
            Document doc = new Document("_id", itemId)
                    .append("nome_item", newItem.getName())
                    .append("descricao", newItem.getDescription())
                    .append("peso", newItem.getWeight())
                    .append("valor_monetario", newItem.getMonetaryValue())
                    .append("qualidades", toAttributeDocuments(newItem.getAttributes()));

            collection.insertOne(doc);
        } catch (MongoException e) {
            throw new SQLException("Erro ao inserir item no MongoDB", e);
        }
    }

    @Override
    public void update(Item item) throws SQLException {
        try {
            Bson filter = Filters.eq("_id", item.getId());
            Bson updates = Updates.combine(
                    Updates.set("nome_item", item.getName()),
                    Updates.set("descricao", item.getDescription()),
                    Updates.set("peso", item.getWeight()),
                    Updates.set("valor_monetario", item.getMonetaryValue()),
                    Updates.set("qualidades", toAttributeDocuments(item.getAttributes()))
            );

            collection.updateOne(filter, updates);
        } catch (MongoException e) {
            throw new SQLException("Erro ao atualizar item no MongoDB", e);
        }
    }

    @Override
    public void remove(Integer itemId) throws SQLException {
        try {
            Bson filter = Filters.eq("_id", itemId);
            collection.deleteOne(filter);
        } catch (MongoException e) {
            throw new SQLException("Erro ao remover item no MongoDB", e);
        }
    }

    @Override
    public Item findById(Integer idItem) throws SQLException {
        try {
            Bson filter = Filters.eq("_id", idItem);
            Document doc = collection.find(filter).first();

            if (doc == null) {
                return null;
            }

            return fromDocument(doc);
        } catch (MongoException e) {
            throw new SQLException("Erro ao buscar item por ID no MongoDB", e);
        }
    }

    @Override
    public List<Item> listAll() throws SQLException {
        List<Item> itens = new ArrayList<>();

        try (MongoCursor<Document> cursor = collection.find().iterator()) {
            while (cursor.hasNext()) {
                itens.add(fromDocument(cursor.next()));
            }
        } catch (MongoException e) {
            throw new SQLException("Erro ao listar todos os itens no MongoDB", e);
        }

        return itens;
    }

    private Integer nextItemId() {
        Document lastItem = collection.find()
                .sort(Sorts.descending("_id"))
                .first();

        if (lastItem == null || lastItem.getInteger("_id") == null) {
            return 1;
        }
        return lastItem.getInteger("_id") + 1;
    }

    private List<Document> toAttributeDocuments(List<ItemAttribute> attributes) {
        List<Document> attributeDocuments = new ArrayList<>();
        if (attributes == null) {
            return attributeDocuments;
        }

        for (ItemAttribute attribute : attributes) {
            attributeDocuments.add(new Document("id_qualidade", attribute.getAttributeId())
                    .append("valor", attribute.getValue()));
        }
        return attributeDocuments;
    }

    private Item fromDocument(Document doc) throws SQLException {
        Item item = Item.fromDocument(doc);
        item.setAttributes(itemAttributeDAO.findByItemId(item.getId()));
        return item;
    }
}
