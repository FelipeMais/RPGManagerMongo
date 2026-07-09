package dao.mongo;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.Updates;
import contracts.CombatActionTypeDAO;
import model.CombatActionType;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CombatActionTypeMongoDAO implements CombatActionTypeDAO {

    private final MongoCollection<Document> collection;

    public CombatActionTypeMongoDAO(MongoDatabase database) {
        this.collection = database.getCollection("tipoacaocombate");
    }

    @Override
    public void insert(CombatActionType newCombatActionType) throws SQLException {
        try {
            Integer actionTypeId = newCombatActionType.getId() != null ? newCombatActionType.getId() : nextActionTypeId();

            Document doc = new Document("_id", actionTypeId)
                    .append("nome_acao_combate", newCombatActionType.getName());

            collection.insertOne(doc);
        } catch (MongoException e) {
            throw new SQLException("Erro ao inserir tipo de ação de combate no MongoDB", e);
        }
    }

    @Override
    public void update(CombatActionType combatActionType) throws SQLException {
        try {
            Bson filter = Filters.eq("_id", combatActionType.getId());
            Bson updates = Updates.set("nome_acao_combate", combatActionType.getName());

            collection.updateOne(filter, updates);
        } catch (MongoException e) {
            throw new SQLException("Erro ao atualizar tipo de ação de combate no MongoDB", e);
        }
    }

    @Override
    public void remove(Integer combatActionTypeId) throws SQLException {
        try {
            collection.deleteOne(Filters.eq("_id", combatActionTypeId));
        } catch (MongoException e) {
            throw new SQLException("Erro ao remover tipo de ação de combate no MongoDB", e);
        }
    }

    @Override
    public CombatActionType findById(Integer combatActionTypeId) throws SQLException {
        try {
            Document doc = collection.find(Filters.eq("_id", combatActionTypeId)).first();
            if (doc == null) {
                return null;
            }
            return fromDocument(doc);
        } catch (MongoException e) {
            throw new SQLException("Erro ao buscar tipo de ação de combate por ID no MongoDB", e);
        }
    }

    @Override
    public List<CombatActionType> listAll() throws SQLException {
        List<CombatActionType> combatActionTypes = new ArrayList<>();

        try (MongoCursor<Document> cursor = collection.find()
                .sort(Sorts.ascending("nome_acao_combate"))
                .iterator()) {

            while (cursor.hasNext()) {
                combatActionTypes.add(fromDocument(cursor.next()));
            }
        } catch (MongoException e) {
            throw new SQLException("Erro ao listar tipos de ação de combate no MongoDB", e);
        }

        return combatActionTypes;
    }

    private Integer nextActionTypeId() {
        Document lastActionType = collection.find()
                .sort(Sorts.descending("_id"))
                .first();

        if (lastActionType == null || lastActionType.getInteger("_id") == null) {
            return 1;
        }
        return lastActionType.getInteger("_id") + 1;
    }

    private CombatActionType fromDocument(Document doc) {
        return new CombatActionType(
                doc.getInteger("_id"),
                doc.getString("nome_acao_combate")
        );
    }
}