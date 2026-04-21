package dao;

import contracts.InventoryDAO;
import model.relationship.InventoryItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InventorySqlDAO implements InventoryDAO {
    private final Connection connection;

    public InventorySqlDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(InventoryItem inventoryItem) throws SQLException {
        PreparedStatement st = connection.prepareStatement(
                "INSERT INTO inventario(id_item, id_personagem, quantidade) VALUES (?, ?, ?)"
        );
        st.setInt(1, inventoryItem.getItemId());
        st.setInt(2, inventoryItem.getCharacterId());
        st.setInt(3, inventoryItem.getQuantity());
        st.executeUpdate();
        st.close();
    }

    @Override
    public void update(InventoryItem inventoryItem) throws SQLException {
        PreparedStatement st = connection.prepareStatement(
                "UPDATE inventario SET quantidade = ? WHERE id_item = ? AND id_personagem = ?"
        );
        st.setInt(1, inventoryItem.getQuantity());
        st.setInt(2, inventoryItem.getItemId());
        st.setInt(3, inventoryItem.getCharacterId());
        st.executeUpdate();
        st.close();
    }

    @Override
    public void remove(Integer characterId, Integer itemId) throws SQLException {
        PreparedStatement st = connection.prepareStatement(
                "DELETE FROM inventario WHERE id_personagem = ? AND id_item = ?"
        );
        st.setInt(1, characterId);
        st.setInt(2, itemId);
        st.executeUpdate();
        st.close();
    }

    @Override
    public void removeByCharacterId(Integer characterId) throws SQLException {
        PreparedStatement st = connection.prepareStatement("DELETE FROM inventario WHERE id_personagem = ?");
        st.setInt(1, characterId);
        st.executeUpdate();
        st.close();
    }

    @Override
    public InventoryItem findByCharacterIdAndItemId(Integer characterId, Integer itemId) throws SQLException {
        PreparedStatement st = connection.prepareStatement(
                "SELECT inv.id_item, inv.id_personagem, inv.quantidade, i.nome_item, i.descricao, i.peso, i.valor_monetario " +
                        "FROM inventario inv INNER JOIN itens i ON i.id_item = inv.id_item " +
                        "WHERE inv.id_personagem = ? AND inv.id_item = ?"
        );
        st.setInt(1, characterId);
        st.setInt(2, itemId);
        ResultSet result = st.executeQuery();
        InventoryItem inventoryItem = null;
        if (result.next()) {
            inventoryItem = InventoryItem.fromResultSet(result);
        }
        st.close();
        return inventoryItem;
    }

    @Override
    public List<InventoryItem> findByCharacterId(Integer characterId) throws SQLException {
        List<InventoryItem> inventoryItems = new ArrayList<>();
        PreparedStatement st = connection.prepareStatement(
                "SELECT inv.id_item, inv.id_personagem, inv.quantidade, i.nome_item, i.descricao, i.peso, i.valor_monetario " +
                        "FROM inventario inv INNER JOIN itens i ON i.id_item = inv.id_item " +
                        "WHERE inv.id_personagem = ? ORDER BY i.nome_item"
        );
        st.setInt(1, characterId);
        ResultSet result = st.executeQuery();
        while (result.next()) {
            inventoryItems.add(InventoryItem.fromResultSet(result));
        }
        st.close();
        return inventoryItems;
    }
}
