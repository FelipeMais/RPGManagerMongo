package dao.postgres;

import contracts.ItemDAO;
import model.Item;
import model.relationship.ItemAttribute;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ItemSqlDAO implements ItemDAO {

    private final Connection connection;
    private final ItemAttributeSqlDAO itemAttributeDAO;

    public ItemSqlDAO(Connection connection) {
        this.connection = connection;
        this.itemAttributeDAO = new ItemAttributeSqlDAO(connection);
    }

    @Override
    public void insert(Item newItem) throws SQLException {
        boolean autoCommit = connection.getAutoCommit();
        connection.setAutoCommit(false);
        try {
            PreparedStatement st = connection.prepareStatement(
                    "INSERT INTO itens(nome_item, descricao, peso, valor_monetario) VALUES (?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            st.setString(1, newItem.getName());
            st.setString(2, newItem.getDescription());
            st.setBigDecimal(3, newItem.getWeight());
            st.setBigDecimal(4, newItem.getMonetaryValue());
            st.executeUpdate();

            ResultSet generatedKeys = st.getGeneratedKeys();
            if (!generatedKeys.next()) {
                throw new SQLException("Nao foi possivel obter o ID do item criado.");
            }

            Integer itemId = generatedKeys.getInt(1);
            persistAttributes(itemId, newItem.getAttributes());
            st.close();
            connection.commit();
        } catch (SQLException err) {
            connection.rollback();
            throw err;
        } finally {
            connection.setAutoCommit(autoCommit);
        }
    }

    @Override
    public void update(Item item) throws SQLException {
        boolean autoCommit = connection.getAutoCommit();
        connection.setAutoCommit(false);
        try {
            PreparedStatement st = connection.prepareStatement(
                    "UPDATE itens SET nome_item = ?, descricao = ?, peso = ?, valor_monetario = ? WHERE id_item = ?"
            );
            st.setString(1, item.getName());
            st.setString(2, item.getDescription());
            st.setBigDecimal(3, item.getWeight());
            st.setBigDecimal(4, item.getMonetaryValue());
            st.setInt(5, item.getId());
            st.executeUpdate();
            st.close();

            itemAttributeDAO.removeByItemId(item.getId());
            persistAttributes(item.getId(), item.getAttributes());
            connection.commit();
        } catch (SQLException err) {
            connection.rollback();
            throw err;
        } finally {
            connection.setAutoCommit(autoCommit);
        }
    }

    @Override
    public void remove(Integer itemId) throws SQLException {
        boolean autoCommit = connection.getAutoCommit();
        connection.setAutoCommit(false);
        try {
            itemAttributeDAO.removeByItemId(itemId);
            PreparedStatement st = connection.prepareStatement("DELETE FROM itens WHERE id_item = ?");
            st.setInt(1, itemId);
            st.execute();
            st.close();
            connection.commit();
        } catch (SQLException err) {
            connection.rollback();
            throw err;
        } finally {
            connection.setAutoCommit(autoCommit);
        }
    }

    @Override
    public Item findById(Integer itemId) throws SQLException {
        List<Item> list = new ArrayList<>();
        PreparedStatement st = connection.prepareStatement("SELECT * FROM itens WHERE id_item = ?");
        st.setInt(1, itemId);
        ResultSet result = st.executeQuery();
        while (result.next()) {
            Item item = Item.fromResultSet(result);
            item.setAttributes(itemAttributeDAO.findByItemId(item.getId()));
            list.add(item);
        }
        st.close();
        if (!list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<Item> listAll() throws SQLException {
        List<Item> list = new ArrayList<>();
        Statement st = connection.createStatement();
        ResultSet result = st.executeQuery("SELECT * FROM itens");
        while (result.next()) {
            Item item = Item.fromResultSet(result);
            item.setAttributes(itemAttributeDAO.findByItemId(item.getId()));
            list.add(item);
        }
        st.close();
        return list;
    }

    private void persistAttributes(Integer itemId, List<ItemAttribute> attributes) throws SQLException {
        if (attributes == null) {
            return;
        }
        for (ItemAttribute attribute : attributes) {
            itemAttributeDAO.insert(new ItemAttribute(
                    itemId,
                    attribute.getAttributeId(),
                    attribute.getValue(),
                    attribute.getAttribute()
            ));
        }
    }
}
