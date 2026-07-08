package dao.mongo;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.Updates;
import contracts.MagicDAO;
import model.Magic;
import model.relationship.MagicAttribute;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MagicMongoDAO implements MagicDAO {

    private final MongoCollection<Document> collection;
    private final MagicAttributeMongoDAO magicAttributeDAO;

    public MagicMongoDAO(MongoDatabase database) {
        this.collection = database.getCollection("magias");
        this.magicAttributeDAO = new MagicAttributeMongoDAO(database);
    }

    @Override
    public void insert(Magic newMagic) throws SQLException {
        try {
            Integer magicId = newMagic.getId() != null ? newMagic.getId() : nextMagicId();
            Document doc = new Document("_id", magicId)
                    .append("nome_magia", newMagic.getName())
                    .append("descricao", newMagic.getDescription())
                    .append("custo_mana", newMagic.getManaCost())
                    .append("nivel_minimo", newMagic.getMinLevel())
                    .append("dados", newMagic.getDices())
                    .append("qualidades", toAttributeDocuments(newMagic.getAttributes()));

            collection.insertOne(doc);
        } catch (MongoException e) {
            throw new SQLException("Erro ao inserir magia no MongoDB", e);
        }
    }

    @Override
    public void update(Magic magic) throws SQLException {
        try {
            Bson filter = Filters.eq("_id", magic.getId());
            Bson updates = Updates.combine(
                    Updates.set("nome_magia", magic.getName()),
                    Updates.set("descricao", magic.getDescription()),
                    Updates.set("custo_mana", magic.getManaCost()),
                    Updates.set("nivel_minimo", magic.getMinLevel()),
                    Updates.set("dados", magic.getDices()),
                    Updates.set("qualidades", toAttributeDocuments(magic.getAttributes()))
            );

            collection.updateOne(filter, updates);
        } catch (MongoException e) {
            throw new SQLException("Erro ao atualizar magia no MongoDB", e);
        }
    }

    @Override
    public void remove(Integer magicId) throws SQLException {
        try {
            collection.deleteOne(Filters.eq("_id", magicId));
        } catch (MongoException e) {
            throw new SQLException("Erro ao remover magia no MongoDB", e);
        }
    }

    @Override
    public Magic findById(Integer idMagia) throws SQLException {
        try {
            Document doc = collection.find(Filters.eq("_id", idMagia)).first();
            if (doc == null) {
                return null;
            }
            return fromDocument(doc);
        } catch (MongoException e) {
            throw new SQLException("Erro ao buscar magia por ID no MongoDB", e);
        }
    }

    @Override
    public List<Magic> listAll() throws SQLException {
        List<Magic> magics = new ArrayList<>();

        try (MongoCursor<Document> cursor = collection.find().iterator()) {
            while (cursor.hasNext()) {
                magics.add(fromDocument(cursor.next()));
            }
        } catch (MongoException e) {
            throw new SQLException("Erro ao listar magias no MongoDB", e);
        }

        return magics;
    }

    private Integer nextMagicId() {
        Document lastMagic = collection.find()
                .sort(Sorts.descending("_id"))
                .first();

        if (lastMagic == null || lastMagic.getInteger("_id") == null) {
            return 1;
        }
        return lastMagic.getInteger("_id") + 1;
    }

    private List<Document> toAttributeDocuments(List<MagicAttribute> attributes) {
        List<Document> attributeDocuments = new ArrayList<>();
        if (attributes == null) {
            return attributeDocuments;
        }

        for (MagicAttribute attribute : attributes) {
            attributeDocuments.add(new Document("id_qualidade", attribute.getAttributeId())
                    .append("valor", attribute.getValue()));
        }
        return attributeDocuments;
    }

    private Magic fromDocument(Document doc) throws SQLException {
        Magic magic = new Magic(
                doc.getInteger("_id"),
                doc.getString("nome_magia"),
                doc.getString("descricao"),
                doc.getInteger("custo_mana"),
                doc.getInteger("nivel_minimo"),
                doc.getString("dados"),
                new ArrayList<>()
        );
        magic.setAttributes(magicAttributeDAO.findByMagicId(magic.getId()));
        return magic;
    }
}
