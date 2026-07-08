package dao.mongo;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import contracts.MagicAttributeDAO;
import model.Attribute;
import model.relationship.MagicAttribute;
import org.bson.Document;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MagicAttributeMongoDAO implements MagicAttributeDAO {

    private final MongoCollection<Document> magicCollection;
    private final MongoCollection<Document> attributeCollection;

    public MagicAttributeMongoDAO(MongoDatabase database) {
        this.magicCollection = database.getCollection("magias");
        this.attributeCollection = database.getCollection("qualidades");
    }

    @Override
    public void insert(MagicAttribute magicAttribute) throws SQLException {
        try {
            Document magic = magicCollection.find(Filters.eq("_id", magicAttribute.getMagicId())).first();
            if (magic == null) {
                throw new SQLException("Magia informada nao existe.");
            }
            if (findAttributeDocument(magicAttribute.getAttributeId()) == null) {
                throw new SQLException("Atributo informado nao existe.");
            }
            if (containsAttribute(magic, magicAttribute.getAttributeId())) {
                throw new SQLException("Atributo ja vinculado a magia.");
            }

            magicCollection.updateOne(
                    Filters.eq("_id", magicAttribute.getMagicId()),
                    Updates.push("qualidades", toDocument(magicAttribute))
            );
        } catch (MongoException e) {
            throw new SQLException("Erro ao inserir atributo da magia no MongoDB", e);
        }
    }

    @Override
    public void removeByMagicId(Integer magicId) throws SQLException {
        try {
            magicCollection.updateOne(
                    Filters.eq("_id", magicId),
                    Updates.set("qualidades", new ArrayList<Document>())
            );
        } catch (MongoException e) {
            throw new SQLException("Erro ao remover atributos da magia no MongoDB", e);
        }
    }

    @Override
    public List<MagicAttribute> findByMagicId(Integer magicId) throws SQLException {
        try {
            Document magic = magicCollection.find(Filters.eq("_id", magicId)).first();
            if (magic == null) {
                return new ArrayList<>();
            }

            List<MagicAttribute> attributes = new ArrayList<>();
            for (Document attributeDocument : getAttributeDocuments(magic)) {
                Integer attributeId = getAttributeId(attributeDocument);
                Document attributeFound = findAttributeDocument(attributeId);
                Attribute attribute = attributeFound != null
                        ? new Attribute(attributeId, attributeFound.getString("nome_qualidade"))
                        : null;

                attributes.add(new MagicAttribute(
                        magicId,
                        attributeId,
                        attributeDocument.getInteger("valor"),
                        attribute
                ));
            }

            attributes.sort(Comparator.comparing(MagicAttribute::getAttributeId));
            return attributes;
        } catch (MongoException e) {
            throw new SQLException("Erro ao buscar atributos da magia no MongoDB", e);
        }
    }

    private Document toDocument(MagicAttribute magicAttribute) {
        return new Document("id_qualidade", magicAttribute.getAttributeId())
                .append("valor", magicAttribute.getValue());
    }

    private Document findAttributeDocument(Integer attributeId) {
        if (attributeId == null) {
            return null;
        }
        return attributeCollection.find(Filters.eq("_id", attributeId)).first();
    }

    private boolean containsAttribute(Document magic, Integer attributeId) {
        for (Document attributeDocument : getAttributeDocuments(magic)) {
            if (attributeId.equals(getAttributeId(attributeDocument))) {
                return true;
            }
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    private List<Document> getAttributeDocuments(Document magic) {
        Object value = magic.get("qualidades");
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
