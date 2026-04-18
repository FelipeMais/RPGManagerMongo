package dao;

import contracts.LocationDAO;
import model.Location;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class LocationSqlDAO implements LocationDAO {

    private final Connection connection;

    public LocationSqlDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Location newLocation) throws SQLException {
        PreparedStatement st = connection.prepareStatement(
                "INSERT INTO local(local_pai, id_tipo_local, nome_local, descricao) VALUES (?, ?, ?, ?)"
        );
        setNullableInteger(st, 1, newLocation.getParentId());
        setNullableInteger(st, 2, newLocation.getLocationTypeId());
        st.setString(3, newLocation.getName());
        st.setString(4, newLocation.getDescription());
        st.execute();
        st.close();
    }

    @Override
    public void update(Location location) throws SQLException {
        PreparedStatement st = connection.prepareStatement(
                "UPDATE local SET local_pai = ?, id_tipo_local = ?, nome_local = ?, descricao = ? WHERE id_local = ?"
        );
        setNullableInteger(st, 1, location.getParentId());
        setNullableInteger(st, 2, location.getLocationTypeId());
        st.setString(3, location.getName());
        st.setString(4, location.getDescription());
        st.setInt(5, location.getId());
        st.executeUpdate();
        st.close();
    }

    @Override
    public void remove(Integer locationId) throws SQLException {
        PreparedStatement st = connection.prepareStatement("DELETE FROM local WHERE id_local = ?");
        st.setInt(1, locationId);
        st.execute();
        st.close();
    }

    @Override
    public Location findById(Integer locationId) throws SQLException {
        List<Location> list = new ArrayList<>();
        PreparedStatement st = connection.prepareStatement("SELECT local_pai, id_local, id_tipo_local, nome_local, descricao FROM local WHERE id_local = ?");
        st.setInt(1, locationId);
        ResultSet result = st.executeQuery();
        while (result.next()) {
            list.add(Location.fromResultSet(result));
        }
        st.close();
        if (!list.isEmpty()) {
            return list.getFirst();
        }
        return null;
    }

    @Override
    public List<Location> listAll() throws SQLException {
        List<Location> list = new ArrayList<>();
        Statement st = connection.createStatement();
        ResultSet result = st.executeQuery("SELECT local_pai, id_local, id_tipo_local, nome_local, descricao FROM local");
        while (result.next()) {
            list.add(Location.fromResultSet(result));
        }
        st.close();
        return list;
    }

    private void setNullableInteger(PreparedStatement st, int parameterIndex, Integer value) throws SQLException {
        if (value == null) {
            st.setNull(parameterIndex, java.sql.Types.INTEGER);
            return;
        }
        st.setInt(parameterIndex, value);
    }
}
