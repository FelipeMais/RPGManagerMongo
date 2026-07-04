package dao.mongo;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.Updates;
import contracts.AttributeDAO;
import model.Attribute;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AttributeMongoDAO implements AttributeDAO {

    private final MongoCollection<Document> collection;
    private final MongoCollection<Document> itemCollection;

    public AttributeMongoDAO(MongoDatabase database) {
        this.collection = database.getCollection("qualidades");
        this.itemCollection = database.getCollection("itens");
    }

    @Override
    public void insert(Attribute newAttribute) throws SQLException {
        try {
            Integer attributeId = newAttribute.getId() != null ? newAttribute.getId() : nextAttributeId();
            Document doc = new Document("_id", attributeId)
                    .append("nome_qualidade", newAttribute.getName());

            collection.insertOne(doc);
        } catch (MongoException e) {
            throw new SQLException("Erro ao inserir atributo no MongoDB", e);
        }
    }

    @Override
    public void update(Attribute attribute) throws SQLException {
        try {
            Bson filter = Filters.eq("_id", attribute.getId());
            Bson update = Updates.set("nome_qualidade", attribute.getName());
            collection.updateOne(filter, update);
        } catch (MongoException e) {
            throw new SQLException("Erro ao atualizar atributo no MongoDB", e);
        }
    }

    @Override
    public void remove(Integer attributeId) throws SQLException {
        try {
            if (isAttributeInUse(attributeId)) {
                throw new SQLException("Nao foi possivel remover o atributo porque ele esta vinculado a itens.");
            }

            Bson filter = Filters.eq("_id", attributeId);
            collection.deleteOne(filter);
        } catch (MongoException e) {
            throw new SQLException("Erro ao remover atributo no MongoDB", e);
        }
    }

    @Override
    public Attribute findById(Integer attributeId) throws SQLException {
        try {
            Document doc = collection.find(Filters.eq("_id", attributeId)).first();
            if (doc == null) {
                return null;
            }
            return fromDocument(doc);
        } catch (MongoException e) {
            throw new SQLException("Erro ao buscar atributo por ID no MongoDB", e);
        }
    }

    @Override
    public List<Attribute> listAll() throws SQLException {
        List<Attribute> attributes = new ArrayList<>();

        try (MongoCursor<Document> cursor = collection.find().iterator()) {
            while (cursor.hasNext()) {
                attributes.add(fromDocument(cursor.next()));
            }
        } catch (MongoException e) {
            throw new SQLException("Erro ao listar atributos no MongoDB", e);
        }

        return attributes;
    }

    private Integer nextAttributeId() {
        Document lastAttribute = collection.find()
                .sort(Sorts.descending("_id"))
                .first();

        if (lastAttribute == null || lastAttribute.getInteger("_id") == null) {
            return 1;
        }
        return lastAttribute.getInteger("_id") + 1;
    }

    private boolean isAttributeInUse(Integer attributeId) {
        Bson currentFormat = Filters.elemMatch("qualidades", Filters.eq("id_qualidade", attributeId));
        Bson oldFormat = Filters.elemMatch("qualidades", Filters.eq("qualidade_id", attributeId));
        return itemCollection.find(Filters.or(currentFormat, oldFormat)).first() != null;
    }

    private Attribute fromDocument(Document doc) {
        return new Attribute(
                doc.getInteger("_id"),
                doc.getString("nome_qualidade")
        );
    }
}
