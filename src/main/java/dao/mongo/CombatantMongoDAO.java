package dao.mongo;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import contracts.CombatantDAO;
import org.bson.Document;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CombatantMongoDAO implements CombatantDAO {

    private final MongoCollection<Document> collection;

    public CombatantMongoDAO(MongoDatabase database) {
        this.collection = database.getCollection("combate");
    }

    @Override
    public void insert(Integer combatId, Integer characterId) throws SQLException {
        try {
            collection.updateOne(
                    Filters.eq("_id", combatId),
                    Updates.addToSet("combatentes", characterId)
            );
        } catch (MongoException e) {
            throw new SQLException("Erro ao inserir combatente no MongoDB", e);
        }
    }

    @Override
    public void remove(Integer combatId, Integer characterId) throws SQLException {
        try {
            collection.updateOne(
                    Filters.eq("_id", combatId),
                    Updates.pull("combatentes", characterId)
            );
        } catch (MongoException e) {
            throw new SQLException("Erro ao remover combatente no MongoDB", e);
        }
    }

    @Override
    public void removeByCombatId(Integer combatId) throws SQLException {
        try {
            collection.updateOne(
                    Filters.eq("_id", combatId),
                    Updates.set("combatentes", new ArrayList<Integer>())
            );
        } catch (MongoException e) {
            throw new SQLException("Erro ao remover todos os combatentes no MongoDB", e);
        }
    }

    @Override
    public List<Integer> findCharacterIdsByCombatId(Integer combatId) throws SQLException {
        try {
            Document combat = collection.find(Filters.eq("_id", combatId)).first();
            if (combat == null) {
                return new ArrayList<>();
            }

            List<Integer> characterIds = combat.getList("combatentes", Integer.class);
            return characterIds != null ? characterIds : new ArrayList<>();
        } catch (MongoException e) {
            throw new SQLException("Erro ao buscar combatentes no MongoDB", e);
        }
    }
}