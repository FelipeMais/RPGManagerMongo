package dao;

import contracts.CombatActionTypeDAO;
import model.CombatActionType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CombatActionTypeSqlDAO implements CombatActionTypeDAO {
    private final Connection connection;

    public CombatActionTypeSqlDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(CombatActionType newCombatActionType) throws SQLException {
        PreparedStatement st = connection.prepareStatement(
                "INSERT INTO tipoacaocombate(nome_acao_combate) VALUES (?)"
        );
        st.setString(1, newCombatActionType.getName());
        st.executeUpdate();
        st.close();
    }

    @Override
    public void update(CombatActionType combatActionType) throws SQLException {
        PreparedStatement st = connection.prepareStatement(
                "UPDATE tipoacaocombate SET nome_acao_combate = ? WHERE id_tipo_acao_combate = ?"
        );
        st.setString(1, combatActionType.getName());
        st.setInt(2, combatActionType.getId());
        st.executeUpdate();
        st.close();
    }

    @Override
    public void remove(Integer combatActionTypeId) throws SQLException {
        PreparedStatement st = connection.prepareStatement(
                "DELETE FROM tipoacaocombate WHERE id_tipo_acao_combate = ?"
        );
        st.setInt(1, combatActionTypeId);
        st.executeUpdate();
        st.close();
    }

    @Override
    public CombatActionType findById(Integer combatActionTypeId) throws SQLException {
        PreparedStatement st = connection.prepareStatement(
                "SELECT id_tipo_acao_combate, nome_acao_combate FROM tipoacaocombate WHERE id_tipo_acao_combate = ?"
        );
        st.setInt(1, combatActionTypeId);
        ResultSet result = st.executeQuery();
        CombatActionType combatActionType = null;
        if (result.next()) {
            combatActionType = CombatActionType.fromResultSet(result);
        }
        st.close();
        return combatActionType;
    }

    @Override
    public List<CombatActionType> listAll() throws SQLException {
        List<CombatActionType> combatActionTypes = new ArrayList<>();
        Statement st = connection.createStatement();
        ResultSet result = st.executeQuery(
                "SELECT id_tipo_acao_combate, nome_acao_combate FROM tipoacaocombate ORDER BY nome_acao_combate"
        );
        while (result.next()) {
            combatActionTypes.add(CombatActionType.fromResultSet(result));
        }
        st.close();
        return combatActionTypes;
    }
}
