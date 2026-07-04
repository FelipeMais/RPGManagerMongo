package dao.postgres;

import contracts.AttributeDAO;
import model.Attribute;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AttributeSqlDAO implements AttributeDAO {

    private final Connection connection;

    public AttributeSqlDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Attribute newAttribute) throws SQLException {
        PreparedStatement st = connection.prepareStatement(
                "INSERT INTO qualidades(nome_qualidade) VALUES (?)"
        );
        st.setString(1, newAttribute.getName());
        st.execute();
        st.close();
    }

    @Override
    public void update(Attribute attribute) throws SQLException {
        PreparedStatement st = connection.prepareStatement(
                "UPDATE qualidades SET nome_qualidade = ? WHERE id_qualidade = ?"
        );
        st.setString(1, attribute.getName());
        st.setInt(2, attribute.getId());
        st.executeUpdate();
        st.close();
    }

    @Override
    public void remove(Integer attributeId) throws SQLException {
        PreparedStatement st = connection.prepareStatement("DELETE FROM qualidades WHERE id_qualidade = ?");
        st.setInt(1, attributeId);
        st.execute();
        st.close();
    }

    @Override
    public Attribute findById(Integer attributeId) throws SQLException {
        List<Attribute> list = new ArrayList<>();
        PreparedStatement st = connection.prepareStatement("SELECT * FROM qualidades WHERE id_qualidade = ?");
        st.setInt(1, attributeId);
        ResultSet result = st.executeQuery();
        while (result.next()) {
            list.add(Attribute.fromResultSet(result));
        }
        st.close();
        if (!list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<Attribute> listAll() throws SQLException {
        List<Attribute> list = new ArrayList<>();
        Statement st = connection.createStatement();
        ResultSet result = st.executeQuery("SELECT * FROM qualidades");
        while (result.next()) {
            list.add(Attribute.fromResultSet(result));
        }
        st.close();
        return list;
    }
}
