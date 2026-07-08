package dao.mongo;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.Updates;
import contracts.LocationTypeDAO;
import model.LocationType;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LocationTypeMongoDAO implements LocationTypeDAO {

    private final MongoCollection<Document> collection;

    public LocationTypeMongoDAO(MongoDatabase database) {
        this.collection = database.getCollection("tipolocal");
    }

    @Override
    public void insert(LocationType newLocationType) throws SQLException {
        try {
            Integer locationTypeId = newLocationType.getId() != null ? newLocationType.getId() : nextLocationTypeId();
            Document doc = new Document("_id", locationTypeId)
                    .append("nome_tipo_local", newLocationType.getName())
                    .append("descricao", newLocationType.getDescription());

            collection.insertOne(doc);
        } catch (MongoException e) {
            throw new SQLException("Erro ao inserir tipo de local no MongoDB", e);
        }
    }

    @Override
    public void update(LocationType locationType) throws SQLException {
        try {
            Bson filter = Filters.eq("_id", locationType.getId());
            Bson updates = Updates.combine(
                    Updates.set("nome_tipo_local", locationType.getName()),
                    Updates.set("descricao", locationType.getDescription())
            );

            collection.updateOne(filter, updates);
        } catch (MongoException e) {
            throw new SQLException("Erro ao atualizar tipo de local no MongoDB", e);
        }
    }

    @Override
    public void remove(Integer locationTypeId) throws SQLException {
        try {
            collection.deleteOne(Filters.eq("_id", locationTypeId));
        } catch (MongoException e) {
            throw new SQLException("Erro ao remover tipo de local no MongoDB", e);
        }
    }

    @Override
    public LocationType findById(Integer locationTypeId) throws SQLException {
        try {
            Document doc = collection.find(Filters.eq("_id", locationTypeId)).first();
            if (doc == null) {
                return null;
            }
            return LocationType.fromDocument(doc);
        } catch (MongoException e) {
            throw new SQLException("Erro ao buscar tipo de local por ID no MongoDB", e);
        }
    }

    @Override
    public List<LocationType> listAll() throws SQLException {
        List<LocationType> locationTypes = new ArrayList<>();

        try (MongoCursor<Document> cursor = collection.find().iterator()) {
            while (cursor.hasNext()) {
                locationTypes.add(LocationType.fromDocument(cursor.next()));
            }
        } catch (MongoException e) {
            throw new SQLException("Erro ao listar tipos de local no MongoDB", e);
        }

        return locationTypes;
    }

    private Integer nextLocationTypeId() {
        Document lastLocationType = collection.find()
                .sort(Sorts.descending("_id"))
                .first();

        if (lastLocationType == null || lastLocationType.getInteger("_id") == null) {
            return 1;
        }
        return lastLocationType.getInteger("_id") + 1;
    }
}
