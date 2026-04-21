package dao;

import contracts.AbilityDAO;
import model.Ability;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AbilitySqlDAO implements AbilityDAO {
    private final Connection connection;

    public AbilitySqlDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Ability newAbility) throws SQLException {
        PreparedStatement st = connection.prepareStatement(
                "INSERT INTO habilidades(nome_habilidade, descr_habilidade, atributo_base) VALUES (?, ?, ?)"
        );
        st.setString(1, newAbility.getName());
        st.setString(2, newAbility.getDescription());
        st.setString(3, newAbility.getBaseAttribute());
        st.executeUpdate();
        st.close();
    }

    @Override
    public void update(Ability ability) throws SQLException {
        PreparedStatement st = connection.prepareStatement(
                "UPDATE habilidades SET nome_habilidade = ?, descr_habilidade = ?, atributo_base = ? WHERE id_habilidade = ?"
        );
        st.setString(1, ability.getName());
        st.setString(2, ability.getDescription());
        st.setString(3, ability.getBaseAttribute());
        st.setInt(4, ability.getId());
        st.executeUpdate();
        st.close();
    }

    @Override
    public void remove(Integer abilityId) throws SQLException {
        PreparedStatement st = connection.prepareStatement("DELETE FROM habilidades WHERE id_habilidade = ?");
        st.setInt(1, abilityId);
        st.execute();
        st.close();
    }

    @Override
    public Ability findById(Integer abilityId) throws SQLException {
        List<Ability> list = new ArrayList<>();
        PreparedStatement st = connection.prepareStatement(
                "SELECT id_habilidade, nome_habilidade, descr_habilidade, atributo_base FROM habilidades WHERE id_habilidade = ?"
        );
        st.setInt(1, abilityId);
        ResultSet result = st.executeQuery();
        while (result.next()) {
            list.add(Ability.fromResultSet(result));
        }
        st.close();
        if (!list.isEmpty()) {
            return list.getFirst();
        }
        return null;
    }

    @Override
    public List<Ability> listAll() throws SQLException {
        List<Ability> list = new ArrayList<>();
        Statement st = connection.createStatement();
        ResultSet result = st.executeQuery(
                "SELECT id_habilidade, nome_habilidade, descr_habilidade, atributo_base FROM habilidades"
        );
        while (result.next()) {
            list.add(Ability.fromResultSet(result));
        }
        st.close();
        return list;
    }
}
