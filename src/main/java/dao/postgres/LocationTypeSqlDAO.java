package dao.postgres;

import contracts.LocationTypeDAO;
import model.LocationType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class LocationTypeSqlDAO implements LocationTypeDAO {

    private final Connection connection;

    public LocationTypeSqlDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(LocationType newLocationType) throws SQLException {
        PreparedStatement st = connection.prepareStatement(
                "INSERT INTO tipolocal(nome_tipo_local, descricao) VALUES (?, ?)"
        );
        st.setString(1, newLocationType.getName());
        st.setString(2, newLocationType.getDescription());
        st.execute();
        st.close();
    }

    @Override
    public void update(LocationType locationType) throws SQLException {
        PreparedStatement st = connection.prepareStatement(
                "UPDATE tipolocal SET nome_tipo_local = ?, descricao = ? WHERE id_tipo_local = ?"
        );
        st.setString(1, locationType.getName());
        st.setString(2, locationType.getDescription());
        st.setInt(3, locationType.getId());
        st.executeUpdate();
        st.close();
    }

    @Override
    public void remove(Integer locationTypeId) throws SQLException {
        PreparedStatement st = connection.prepareStatement("DELETE FROM tipolocal WHERE id_tipo_local = ?");
        st.setInt(1, locationTypeId);
        st.execute();
        st.close();
    }

    @Override
    public LocationType findById(Integer locationTypeId) throws SQLException {
        List<LocationType> list = new ArrayList<>();
        PreparedStatement st = connection.prepareStatement("SELECT id_tipo_local, nome_tipo_local, descricao FROM tipolocal WHERE id_tipo_local = ?");
        st.setInt(1, locationTypeId);
        ResultSet result = st.executeQuery();
        while (result.next()) {
            list.add(LocationType.fromResultSet(result));
        }
        st.close();
        if (!list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<LocationType> listAll() throws SQLException {
        List<LocationType> list = new ArrayList<>();
        Statement st = connection.createStatement();
        ResultSet result = st.executeQuery("SELECT id_tipo_local, nome_tipo_local, descricao FROM tipolocal");
        while (result.next()) {
            list.add(LocationType.fromResultSet(result));
        }
        st.close();
        return list;
    }
}
