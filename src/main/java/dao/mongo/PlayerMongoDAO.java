package dao.mongo;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.Updates;
import contracts.PlayerDAO;
import model.Player;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PlayerMongoDAO implements PlayerDAO {

    private final MongoCollection<Document> collection;

    public PlayerMongoDAO(MongoDatabase database) {
        this.collection = database.getCollection("jogador");
    }

    @Override
    public void insert(Player newPlayer) throws SQLException {
        try {
            Integer playerId = newPlayer.getId() != null ? newPlayer.getId() : nextPlayerId();
            Document doc = new Document("_id", playerId)
                    .append("nome_jogador", newPlayer.getName())
                    .append("data_entrada", newPlayer.getEntryDate())
                    .append("ativo", newPlayer.getActive());

            collection.insertOne(doc);
        } catch (MongoException e) {
            throw new SQLException("Erro ao inserir jogador no MongoDB", e);
        }
    }

    @Override
    public void update(Player player) throws SQLException {
        try {
            Bson filter = Filters.eq("_id", player.getId());
            Bson updates = Updates.combine(
                    Updates.set("nome_jogador", player.getName()),
                    Updates.set("data_entrada", player.getEntryDate()),
                    Updates.set("ativo", player.getActive())
            );

            collection.updateOne(filter, updates);
        } catch (MongoException e) {
            throw new SQLException("Erro ao atualizar jogador no MongoDB", e);
        }
    }

    @Override
    public void remove(Integer playerId) throws SQLException {
        try {
            collection.deleteOne(Filters.eq("_id", playerId));
        } catch (MongoException e) {
            throw new SQLException("Erro ao remover jogador no MongoDB", e);
        }
    }

    @Override
    public Player findById(Integer playerId) throws SQLException {
        try {
            Document doc = collection.find(Filters.eq("_id", playerId)).first();
            if (doc == null) {
                return null;
            }
            return fromDocument(doc);
        } catch (MongoException e) {
            throw new SQLException("Erro ao buscar jogador por ID no MongoDB", e);
        }
    }

    @Override
    public List<Player> listAll() throws SQLException {
        List<Player> players = new ArrayList<>();

        try (MongoCursor<Document> cursor = collection.find().iterator()) {
            while (cursor.hasNext()) {
                players.add(fromDocument(cursor.next()));
            }
        } catch (MongoException e) {
            throw new SQLException("Erro ao listar jogadores no MongoDB", e);
        }

        return players;
    }

    private Integer nextPlayerId() {
        Document lastPlayer = collection.find()
                .sort(Sorts.descending("_id"))
                .first();

        if (lastPlayer == null || lastPlayer.getInteger("_id") == null) {
            return 1;
        }
        return lastPlayer.getInteger("_id") + 1;
    }

    private Player fromDocument(Document doc) {
        return new Player(
                doc.getInteger("_id"),
                doc.getString("nome_jogador"),
                toTimestamp(doc.get("data_entrada")),
                doc.getBoolean("ativo")
        );
    }

    private Timestamp toTimestamp(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Timestamp) {
            return (Timestamp) value;
        }
        if (value instanceof Date) {
            return new Timestamp(((Date) value).getTime());
        }
        return Timestamp.valueOf(value.toString());
    }
}
