package dao.mongo;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import contracts.ItemDAO;
import model.Attribute;
import model.Item; // Ajuste para o pacote real da sua classe Item

import model.relationship.ItemAttribute;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemMongoDAO implements ItemDAO {

    private final MongoCollection<Document> collection;

    public ItemMongoDAO(MongoDatabase database) {
        // Usa a coleção "itens"
        this.collection = database.getCollection("itens");
    }

    @Override
    public void insert(Item newItem) throws SQLException {
        try {
            // Criando o documento principal do Item
            // Forçamos o campo "_id" a ser o Integer que veio do seu modelo
            Document doc = new Document("_id", newItem.getId())
                    .append("nome_item", newItem.getName())
                    .append("descricao", newItem.getDescription())
                    .append("peso", newItem.getWeight())
                    .append("valor_monetario", newItem.getMonetaryValue());


            List<Document> qualidadesDocs = new ArrayList<>();
            for (ItemAttribute a : newItem.getAttributes()) {
                qualidadesDocs.add(new Document("qualidade_id", a.getItemId())
                                        .append("nome_qualidade", a.getAttribute().getName())
                                        .append("valor", a.getValue()));
            }
            doc.append("qualidades", qualidadesDocs);


            collection.insertOne(doc);

        } catch (MongoException e) {
            // Se der erro no Mongo, encapsulamos num SQLException para respeitar a interface
            throw new SQLException("Erro ao inserir item no MongoDB", e);
        }
    }

    @Override
    public void update(Item item) throws SQLException {
        try {
            // Busca o item pelo Integer ID
            Bson filter = Filters.eq("_id", item.getId());

            // Define quais campos serão atualizados
            Bson updates = Updates.combine(
                    Updates.set("nome_item", item.getName()),
                    Updates.set("descricao", item.getDescription()),
                    Updates.set("peso", item.getWeight()),
                    Updates.set("valor_monetario", item.getMonetaryValue())
                    // Updates.set("qualidades", novaListaDeQualidadesDocs) -> Se for atualizar a lista
            );

            collection.updateOne(filter, updates);

        } catch (MongoException e) {
            throw new SQLException("Erro ao atualizar item no MongoDB", e);
        }
    }

    @Override
    public void remove(Integer itemId) throws SQLException {
        try {
            Bson filter = Filters.eq("_id", itemId);
            collection.deleteOne(filter);

        } catch (MongoException e) {
            throw new SQLException("Erro ao remover item no MongoDB", e);
        }
    }

    @Override
    public Item findById(Integer idItem) throws SQLException {
        try {
            Bson filter = Filters.eq("_id", idItem);
            Document doc = collection.find(filter).first();

            if (doc == null) {
                return null;
            }

            return Item.fromDocument(doc);

        } catch (MongoException e) {
            throw new SQLException("Erro ao buscar item por ID no MongoDB", e);
        }
    }

    @Override
    public List<Item> listAll() throws SQLException {
        List<Item> itens = new ArrayList<>();

        try (MongoCursor<Document> cursor = collection.find().iterator()) {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                itens.add(Item.fromDocument(doc));
            }
        } catch (MongoException e) {
            throw new SQLException("Erro ao listar todos os itens no MongoDB", e);
        }

        return itens;
    }

}