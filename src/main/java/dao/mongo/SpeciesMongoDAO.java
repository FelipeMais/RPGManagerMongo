package dao.mongo;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.Updates;
import contracts.SpeciesDAO;
import model.Species;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SpeciesMongoDAO implements SpeciesDAO {

    private final MongoCollection<Document> collection;

    public SpeciesMongoDAO(MongoDatabase database) {
        this.collection = database.getCollection("especie");
    }

    @Override
    public void insert(Species newSpecies) throws SQLException {
        try {
            Integer speciesId = newSpecies.getId() != null ? newSpecies.getId() : nextSpeciesId();
            Document doc = new Document("_id", speciesId)
                    .append("nome_especie", newSpecies.getName());

            collection.insertOne(doc);
        } catch (MongoException e) {
            throw new SQLException("Erro ao inserir especie no MongoDB", e);
        }
    }

    @Override
    public void update(Species species) throws SQLException {
        try {
            Bson filter = Filters.eq("_id", species.getId());
            Bson update = Updates.set("nome_especie", species.getName());
            collection.updateOne(filter, update);
        } catch (MongoException e) {
            throw new SQLException("Erro ao atualizar especie no MongoDB", e);
        }
    }

    @Override
    public void remove(Integer speciesId) throws SQLException {
        try {
            collection.deleteOne(Filters.eq("_id", speciesId));
        } catch (MongoException e) {
            throw new SQLException("Erro ao remover especie no MongoDB", e);
        }
    }

    @Override
    public Species findById(Integer speciesId) throws SQLException {
        try {
            Document doc = collection.find(Filters.eq("_id", speciesId)).first();
            if (doc == null) {
                return null;
            }
            return fromDocument(doc);
        } catch (MongoException e) {
            throw new SQLException("Erro ao buscar especie por ID no MongoDB", e);
        }
    }

    @Override
    public List<Species> listAll() throws SQLException {
        List<Species> species = new ArrayList<>();

        try (MongoCursor<Document> cursor = collection.find().iterator()) {
            while (cursor.hasNext()) {
                species.add(fromDocument(cursor.next()));
            }
        } catch (MongoException e) {
            throw new SQLException("Erro ao listar especies no MongoDB", e);
        }

        return species;
    }

    private Integer nextSpeciesId() {
        Document lastSpecies = collection.find()
                .sort(Sorts.descending("_id"))
                .first();

        if (lastSpecies == null || lastSpecies.getInteger("_id") == null) {
            return 1;
        }
        return lastSpecies.getInteger("_id") + 1;
    }

    private Species fromDocument(Document doc) {
        return new Species(
                doc.getInteger("_id"),
                doc.getString("nome_especie")
        );
    }
}
