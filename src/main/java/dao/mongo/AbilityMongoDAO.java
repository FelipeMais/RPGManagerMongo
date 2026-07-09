package dao.mongo;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.Updates;
import contracts.AbilityDAO;
import model.Ability;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AbilityMongoDAO implements AbilityDAO {

    private final MongoCollection<Document> collection;

    public AbilityMongoDAO(MongoDatabase database) {
        this.collection = database.getCollection("habilidades");
    }

    @Override
    public void insert(Ability newAbility) throws SQLException {
        try {
            Integer abilityId = newAbility.getId() != null ? newAbility.getId() : nextAbilityId();
            Document doc = new Document("_id", abilityId)
                    .append("nome_habilidade", newAbility.getName())
                    .append("descr_habilidade", newAbility.getDescription())
                    .append("atributo_base", newAbility.getBaseAttribute());

            collection.insertOne(doc);
        } catch (MongoException e) {
            throw new SQLException("Erro ao inserir habilidade no MongoDB", e);
        }
    }

    @Override
    public void update(Ability ability) throws SQLException {
        try {
            Bson filter = Filters.eq("_id", ability.getId());
            Bson updates = Updates.combine(
                    Updates.set("nome_habilidade", ability.getName()),
                    Updates.set("descr_habilidade", ability.getDescription()),
                    Updates.set("atributo_base", ability.getBaseAttribute())
            );

            collection.updateOne(filter, updates);
        } catch (MongoException e) {
            throw new SQLException("Erro ao atualizar habilidade no MongoDB", e);
        }
    }

    @Override
    public void remove(Integer abilityId) throws SQLException {
        try {
            collection.deleteOne(Filters.eq("_id", abilityId));
        } catch (MongoException e) {
            throw new SQLException("Erro ao remover habilidade no MongoDB", e);
        }
    }

    @Override
    public Ability findById(Integer abilityId) throws SQLException {
        try {
            Document doc = collection.find(Filters.eq("_id", abilityId)).first();
            if (doc == null) {
                return null;
            }
            return fromDocument(doc);
        } catch (MongoException e) {
            throw new SQLException("Erro ao buscar habilidade por ID no MongoDB", e);
        }
    }

    @Override
    public List<Ability> listAll() throws SQLException {
        List<Ability> abilities = new ArrayList<>();

        try (MongoCursor<Document> cursor = collection.find().iterator()) {
            while (cursor.hasNext()) {
                abilities.add(fromDocument(cursor.next()));
            }
        } catch (MongoException e) {
            throw new SQLException("Erro ao listar habilidades no MongoDB", e);
        }

        return abilities;
    }

    private Integer nextAbilityId() {
        Document lastAbility = collection.find()
                .sort(Sorts.descending("_id"))
                .first();

        if (lastAbility == null || lastAbility.getInteger("_id") == null) {
            return 1;
        }
        return lastAbility.getInteger("_id") + 1;
    }

    private Ability fromDocument(Document doc) {
        return new Ability(
                doc.getInteger("_id"),
                doc.getString("nome_habilidade"),
                doc.getString("descr_habilidade"),
                doc.getString("atributo_base")
        );
    }
}