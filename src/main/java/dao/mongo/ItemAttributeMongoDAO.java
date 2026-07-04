package dao.mongo;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import contracts.ItemAttributeDAO;
import model.Attribute;
import model.relationship.ItemAttribute;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ItemAttributeMongoDAO implements ItemAttributeDAO {

    private final MongoCollection<Document> itemCollection;
    private final MongoCollection<Document> attributeCollection;

    public ItemAttributeMongoDAO(MongoDatabase database) {
        this.itemCollection = database.getCollection("itens");
        this.attributeCollection = database.getCollection("qualidades");
    }

    @Override
    public void insert(ItemAttribute itemAttribute) throws SQLException {
        try {
            Document item = itemCollection.find(Filters.eq("_id", itemAttribute.getItemId())).first();
            if (item == null) {
                throw new SQLException("Item informado nao existe.");
            }
            if (findAttributeDocument(itemAttribute.getAttributeId()) == null) {
                throw new SQLException("Atributo informado nao existe.");
            }
            if (containsAttribute(item, itemAttribute.getAttributeId())) {
                throw new SQLException("Atributo ja vinculado ao item.");
            }

            itemCollection.updateOne(
                    Filters.eq("_id", itemAttribute.getItemId()),
                    Updates.push("qualidades", toDocument(itemAttribute))
            );
        } catch (MongoException e) {
            throw new SQLException("Erro ao inserir atributo do item no MongoDB", e);
        }
    }

    @Override
    public void removeByItemId(Integer itemId) throws SQLException {
        try {
            itemCollection.updateOne(
                    Filters.eq("_id", itemId),
                    Updates.set("qualidades", new ArrayList<Document>())
            );
        } catch (MongoException e) {
            throw new SQLException("Erro ao remover atributos do item no MongoDB", e);
        }
    }

    @Override
    public List<ItemAttribute> findByItemId(Integer itemId) throws SQLException {
        try {
            Document item = itemCollection.find(Filters.eq("_id", itemId)).first();
            if (item == null) {
                return new ArrayList<>();
            }

            List<ItemAttribute> attributes = new ArrayList<>();
            List<Document> attributeDocuments = getAttributeDocuments(item);

            for (Document attributeDocument : attributeDocuments) {
                Integer attributeId = getAttributeId(attributeDocument);
                Document attributeFound = findAttributeDocument(attributeId);
                Attribute attribute = attributeFound != null
                        ? new Attribute(attributeId, attributeFound.getString("nome_qualidade"))
                        : null;

                attributes.add(new ItemAttribute(
                        itemId,
                        attributeId,
                        attributeDocument.getInteger("valor"),
                        attribute
                ));
            }

            attributes.sort(Comparator.comparing(ItemAttribute::getAttributeId));
            return attributes;
        } catch (MongoException e) {
            throw new SQLException("Erro ao buscar atributos do item no MongoDB", e);
        }
    }

    private Document toDocument(ItemAttribute itemAttribute) {
        return new Document("id_qualidade", itemAttribute.getAttributeId())
                .append("valor", itemAttribute.getValue());
    }

    private Document findAttributeDocument(Integer attributeId) {
        if (attributeId == null) {
            return null;
        }
        return attributeCollection.find(Filters.eq("_id", attributeId)).first();
    }

    private boolean containsAttribute(Document item, Integer attributeId) {
        for (Document attributeDocument : getAttributeDocuments(item)) {
            if (attributeId.equals(getAttributeId(attributeDocument))) {
                return true;
            }
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    private List<Document> getAttributeDocuments(Document item) {
        Object value = item.get("qualidades");
        if (value instanceof List<?>) {
            return (List<Document>) value;
        }
        return new ArrayList<>();
    }

    private Integer getAttributeId(Document attributeDocument) {
        Integer attributeId = attributeDocument.getInteger("id_qualidade");
        if (attributeId == null) {
            attributeId = attributeDocument.getInteger("qualidade_id");
        }
        return attributeId;
    }
}
