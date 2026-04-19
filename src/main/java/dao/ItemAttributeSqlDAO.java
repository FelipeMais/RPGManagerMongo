package dao;

import contracts.ItemAttributeDAO;
import model.Attribute;
import model.relationship.ItemAttribute;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemAttributeSqlDAO implements ItemAttributeDAO {

    private final Connection connection;

    public ItemAttributeSqlDAO(Connection connection) {this.connection = connection;}

    @Override
    public void insert(ItemAttribute itemAttribute) throws SQLException {
        PreparedStatement st = connection.prepareStatement(
                "INSERT INTO itemcaracteristicas(id_qualidade, id_item, valor) VALUES (?, ?, ?)"
        );
        st.setInt(1, itemAttribute.getAttributeId());
        st.setInt(2, itemAttribute.getItemId());
        st.setInt(3, itemAttribute.getValue());
        st.execute();
        st.close();
    }

    @Override
    public void removeByItemId(Integer itemId) throws SQLException {
        PreparedStatement st = connection.prepareStatement(
                "DELETE FROM itemcaracteristicas WHERE id_item = ?"
        );
        st.setInt(1, itemId);
        st.execute();
        st.close();
    }

    @Override
    public List<ItemAttribute> findByItemId(Integer itemId) throws SQLException {
        List<ItemAttribute> attributes = new ArrayList<>();
        PreparedStatement st = connection.prepareStatement(
                "SELECT ic.id_item, ic.id_qualidade, ic.valor, q.nome_qualidade " +
                        "FROM itemcaracteristicas ic " +
                        "INNER JOIN qualidades q ON q.id_qualidade = ic.id_qualidade " +
                        "WHERE ic.id_item = ? ORDER BY ic.id_qualidade"
        );
        st.setInt(1, itemId);
        ResultSet result = st.executeQuery();
        while (result.next()) {
            Attribute attribute = new Attribute(result.getInt(2), result.getString(4));
            attributes.add(new ItemAttribute(
                    result.getInt(1),
                    result.getInt(2),
                    result.getInt(3),
                    attribute
            ));
        }
        st.close();
        return attributes;
    }
}
