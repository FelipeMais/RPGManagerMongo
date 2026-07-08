package dao.mongo;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.Updates;
import contracts.RpgClassDAO;
import model.RpgClass;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RpgClassMongoDAO implements RpgClassDAO {

    private final MongoCollection<Document> collection;

    public RpgClassMongoDAO(MongoDatabase database) {
        this.collection = database.getCollection("classe");
    }

    @Override
    public void insert(RpgClass newRpgClass) throws SQLException {
        try {
            Integer rpgClassId = newRpgClass.getIdClass() != null ? newRpgClass.getIdClass() : nextRpgClassId();
            Document doc = new Document("_id", rpgClassId)
                    .append("nome_classe", newRpgClass.getClassName())
                    .append("descricao", newRpgClass.getDescription());

            collection.insertOne(doc);
        } catch (MongoException e) {
            throw new SQLException("Erro ao inserir classe no MongoDB", e);
        }
    }

    @Override
    public void update(RpgClass rpgClass) throws SQLException {
        try {
            Bson filter = Filters.eq("_id", rpgClass.getIdClass());
            Bson updates = Updates.combine(
                    Updates.set("nome_classe", rpgClass.getClassName()),
                    Updates.set("descricao", rpgClass.getDescription())
            );

            collection.updateOne(filter, updates);
        } catch (MongoException e) {
            throw new SQLException("Erro ao atualizar classe no MongoDB", e);
        }
    }

    @Override
    public void remove(Integer rpgClassId) throws SQLException {
        try {
            collection.deleteOne(Filters.eq("_id", rpgClassId));
        } catch (MongoException e) {
            throw new SQLException("Erro ao remover classe no MongoDB", e);
        }
    }

    @Override
    public RpgClass findById(Integer rpgClassId) throws SQLException {
        try {
            Document doc = collection.find(Filters.eq("_id", rpgClassId)).first();
            if (doc == null) {
                return null;
            }
            return RpgClass.fromDocument(doc);
        } catch (MongoException e) {
            throw new SQLException("Erro ao buscar classe por ID no MongoDB", e);
        }
    }

    @Override
    public List<RpgClass> listAll() throws SQLException {
        List<RpgClass> rpgClasses = new ArrayList<>();

        try (MongoCursor<Document> cursor = collection.find().iterator()) {
            while (cursor.hasNext()) {
                rpgClasses.add(RpgClass.fromDocument(cursor.next()));
            }
        } catch (MongoException e) {
            throw new SQLException("Erro ao listar classes no MongoDB", e);
        }

        return rpgClasses;
    }

    private Integer nextRpgClassId() {
        Document lastRpgClass = collection.find()
                .sort(Sorts.descending("_id"))
                .first();

        if (lastRpgClass == null || lastRpgClass.getInteger("_id") == null) {
            return 1;
        }
        return lastRpgClass.getInteger("_id") + 1;
    }
}
