package dao;

import contracts.SpeciesDAO;
import model.Species;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SpeciesSqlDAO implements SpeciesDAO {

    private final Connection connection;

    public SpeciesSqlDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Species newSpecies) throws SQLException {
        PreparedStatement st = connection.prepareStatement(
                "INSERT INTO especie(nome_especie) VALUES (?)"
        );
        st.setString(1, newSpecies.getName());
        st.execute();
        st.close();
    }

    @Override
    public void update(Species species) throws SQLException {
        PreparedStatement st = connection.prepareStatement(
                "UPDATE especie SET nome_especie = ? WHERE id_especie = ?"
        );
        st.setString(1, species.getName());
        st.setInt(2, species.getId());
        st.executeUpdate();
        st.close();
    }

    @Override
    public void remove(Integer speciesId) throws SQLException {
        PreparedStatement st = connection.prepareStatement("DELETE FROM especie WHERE id_especie = ?");
        st.setInt(1, speciesId);
        st.execute();
        st.close();
    }

    @Override
    public Species findById(Integer speciesId) throws SQLException {
        List<Species> list = new ArrayList<>();
        PreparedStatement st = connection.prepareStatement("SELECT * FROM especie WHERE id_especie = ?");
        st.setInt(1, speciesId);
        ResultSet result = st.executeQuery();
        while (result.next()) {
            list.add(Species.fromResultSet(result));
        }
        st.close();
        if (!list.isEmpty()) {
            return list.getFirst();
        }
        return null;
    }

    @Override
    public List<Species> listAll() throws SQLException {
        List<Species> list = new ArrayList<>();
        Statement st = connection.createStatement();
        ResultSet result = st.executeQuery("SELECT * FROM especie");
        while (result.next()) {
            list.add(Species.fromResultSet(result));
        }
        st.close();
        return list;
    }
}
