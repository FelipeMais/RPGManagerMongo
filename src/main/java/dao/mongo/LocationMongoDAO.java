package dao.mongo;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.Updates;
import contracts.LocationDAO;
import model.Location;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LocationMongoDAO implements LocationDAO {

    private final MongoCollection<Document> collection;

    public LocationMongoDAO(MongoDatabase database) {
        this.collection = database.getCollection("local");
    }

    @Override
    public void insert(Location newLocation) throws SQLException {
        try {
            Integer locationId = newLocation.getId() != null ? newLocation.getId() : nextLocationId();
            Document doc = new Document("_id", locationId)
                    .append("local_pai", newLocation.getParentId())
                    .append("id_tipo_local", newLocation.getLocationTypeId())
                    .append("nome_local", newLocation.getName())
                    .append("descricao", newLocation.getDescription());

            collection.insertOne(doc);
        } catch (MongoException e) {
            throw new SQLException("Erro ao inserir local no MongoDB", e);
        }
    }

    @Override
    public void update(Location location) throws SQLException {
        try {
            Bson filter = Filters.eq("_id", location.getId());
            Bson updates = Updates.combine(
                    Updates.set("local_pai", location.getParentId()),
                    Updates.set("id_tipo_local", location.getLocationTypeId()),
                    Updates.set("nome_local", location.getName()),
                    Updates.set("descricao", location.getDescription())
            );

            collection.updateOne(filter, updates);
        } catch (MongoException e) {
            throw new SQLException("Erro ao atualizar local no MongoDB", e);
        }
    }

    @Override
    public void remove(Integer locationId) throws SQLException {
        try {
            collection.deleteOne(Filters.eq("_id", locationId));
        } catch (MongoException e) {
            throw new SQLException("Erro ao remover local no MongoDB", e);
        }
    }

    @Override
    public Location findById(Integer locationId) throws SQLException {
        try {
            Document doc = collection.find(Filters.eq("_id", locationId)).first();
            if (doc == null) {
                return null;
            }
            return Location.fromDocument(doc);
        } catch (MongoException e) {
            throw new SQLException("Erro ao buscar local por ID no MongoDB", e);
        }
    }

    @Override
    public List<Location> listAll() throws SQLException {
        List<Location> locations = new ArrayList<>();

        try (MongoCursor<Document> cursor = collection.find().iterator()) {
            while (cursor.hasNext()) {
                locations.add(Location.fromDocument(cursor.next()));
            }
        } catch (MongoException e) {
            throw new SQLException("Erro ao listar locais no MongoDB", e);
        }

        return locations;
    }

    private Integer nextLocationId() {
        Document lastLocation = collection.find()
                .sort(Sorts.descending("_id"))
                .first();

        if (lastLocation == null || lastLocation.getInteger("_id") == null) {
            return 1;
        }
        return lastLocation.getInteger("_id") + 1;
    }
}
