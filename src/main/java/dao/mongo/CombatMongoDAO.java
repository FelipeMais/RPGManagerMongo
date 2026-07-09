package dao.mongo;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.Updates;
import contracts.CombatDAO;
import model.Combat;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CombatMongoDAO implements CombatDAO {

    private final MongoCollection<Document> collection;
    private final MongoCollection<Document> actionsCollection;
    private final CombatantMongoDAO combatantDAO;

    public CombatMongoDAO(MongoDatabase database) {
        this.collection = database.getCollection("combate");
        this.actionsCollection = database.getCollection("acaocombate");
        this.combatantDAO = new CombatantMongoDAO(database);
    }

    @Override
    public Integer insert(Combat newCombat) throws SQLException {
        try {
            Integer combatId = newCombat.getId() != null ? newCombat.getId() : nextCombatId();

            Document doc = new Document("_id", combatId)
                    .append("id_local", newCombat.getLocationId())
                    .append("data", newCombat.getDate())
                    .append("sumario", newCombat.getSummary())
                    .append("combatentes", newCombat.getCombatantIds() != null ? newCombat.getCombatantIds() : new ArrayList<Integer>());

            collection.insertOne(doc);
            return combatId;
        } catch (MongoException e) {
            throw new SQLException("Erro ao inserir combate no MongoDB", e);
        }
    }

    @Override
    public void update(Combat combat) throws SQLException {
        try {
            Bson filter = Filters.eq("_id", combat.getId());
            Bson updates = Updates.combine(
                    Updates.set("id_local", combat.getLocationId()),
                    Updates.set("data", combat.getDate()),
                    Updates.set("sumario", combat.getSummary())
            );

            collection.updateOne(filter, updates);
        } catch (MongoException e) {
            throw new SQLException("Erro ao atualizar combate no MongoDB", e);
        }
    }

    @Override
    public void remove(Integer combatId) throws SQLException {
        try {
            actionsCollection.deleteMany(Filters.eq("id_combate", combatId));

            collection.deleteOne(Filters.eq("_id", combatId));
        } catch (MongoException e) {
            throw new SQLException("Erro ao remover combate no MongoDB", e);
        }
    }

    @Override
    public Combat findById(Integer combatId) throws SQLException {
        try {
            Document doc = collection.find(Filters.eq("_id", combatId)).first();
            if (doc == null) return null;
            return fromDocument(doc);
        } catch (MongoException e) {
            throw new SQLException("Erro ao buscar combate por ID no MongoDB", e);
        }
    }

    @Override
    public List<Combat> listAll() throws SQLException {
        List<Combat> combats = new ArrayList<>();
        try (MongoCursor<Document> cursor = collection.find().sort(Sorts.ascending("_id")).iterator()) {
            while (cursor.hasNext()) {
                combats.add(fromDocument(cursor.next()));
            }
        } catch (MongoException e) {
            throw new SQLException("Erro ao listar todos os combates no MongoDB", e);
        }
        return combats;
    }

    private Integer nextCombatId() {
        Document lastCombat = collection.find().sort(Sorts.descending("_id")).first();
        if (lastCombat == null || lastCombat.getInteger("_id") == null) return 1;
        return lastCombat.getInteger("_id") + 1;
    }

    private Combat fromDocument(Document doc) {
        Integer id = doc.getInteger("_id");
        Integer locationId = doc.getInteger("id_local");
        Date date = doc.getDate("data");
        String summary = doc.getString("sumario");

        List<Integer> characterIds = doc.getList("combatentes", Integer.class);
        if (characterIds == null) characterIds = new ArrayList<>();

        java.sql.Timestamp timestamp = date != null ? new java.sql.Timestamp(date.getTime()) : null;

        return new Combat(id, locationId, timestamp, summary, characterIds);
    }
}