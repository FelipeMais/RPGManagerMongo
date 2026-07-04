package dao.postgres;

import contracts.CombatDAO;
import model.Combat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CombatSqlDAO implements CombatDAO {
    private final Connection connection;
    private final CombatantSqlDAO combatantDAO;

    public CombatSqlDAO(Connection connection) {
        this.connection = connection;
        this.combatantDAO = new CombatantSqlDAO(connection);
    }

    @Override
    public Integer insert(Combat newCombat) throws SQLException {
        boolean autoCommit = connection.getAutoCommit();
        connection.setAutoCommit(false);
        try {
            PreparedStatement st = connection.prepareStatement(
                    "INSERT INTO combate(id_local, data, sumario) VALUES (?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            st.setInt(1, newCombat.getLocationId());
            st.setTimestamp(2, newCombat.getDate());
            st.setString(3, newCombat.getSummary());
            st.executeUpdate();

            ResultSet generatedKeys = st.getGeneratedKeys();
            if (!generatedKeys.next()) {
                generatedKeys.close();
                st.close();
                throw new SQLException("Nao foi possivel obter o ID do combate criado.");
            }

            Integer combatId = generatedKeys.getInt(1);
            generatedKeys.close();
            st.close();

            for (Integer characterId : newCombat.getCombatantIds()) {
                combatantDAO.insert(combatId, characterId);
            }

            connection.commit();
            return combatId;
        } catch (SQLException err) {
            connection.rollback();
            throw err;
        } finally {
            connection.setAutoCommit(autoCommit);
        }
    }

    @Override
    public void update(Combat combat) throws SQLException {
        PreparedStatement st = connection.prepareStatement(
                "UPDATE combate SET id_local = ?, data = ?, sumario = ? WHERE id_combate = ?"
        );
        st.setInt(1, combat.getLocationId());
        st.setTimestamp(2, combat.getDate());
        st.setString(3, combat.getSummary());
        st.setInt(4, combat.getId());
        st.executeUpdate();
        st.close();
    }

    @Override
    public void remove(Integer combatId) throws SQLException {
        boolean autoCommit = connection.getAutoCommit();
        connection.setAutoCommit(false);
        try {
            PreparedStatement deleteActions = connection.prepareStatement(
                    "DELETE FROM acaocombate WHERE id_combate = ?"
            );
            deleteActions.setInt(1, combatId);
            deleteActions.executeUpdate();
            deleteActions.close();

            combatantDAO.removeByCombatId(combatId);

            PreparedStatement deleteCombat = connection.prepareStatement(
                    "DELETE FROM combate WHERE id_combate = ?"
            );
            deleteCombat.setInt(1, combatId);
            deleteCombat.executeUpdate();
            deleteCombat.close();

            connection.commit();
        } catch (SQLException err) {
            connection.rollback();
            throw err;
        } finally {
            connection.setAutoCommit(autoCommit);
        }
    }

    @Override
    public Combat findById(Integer combatId) throws SQLException {
        PreparedStatement st = connection.prepareStatement(
                "SELECT id_combate, id_local, data, sumario FROM combate WHERE id_combate = ?"
        );
        st.setInt(1, combatId);
        ResultSet result = st.executeQuery();
        Combat combat = null;
        if (result.next()) {
            Combat rawCombat = Combat.fromResultSet(result);
            combat = new Combat(
                    rawCombat.getId(),
                    rawCombat.getLocationId(),
                    rawCombat.getDate(),
                    rawCombat.getSummary(),
                    combatantDAO.findCharacterIdsByCombatId(rawCombat.getId())
            );
        }
        st.close();
        return combat;
    }

    @Override
    public List<Combat> listAll() throws SQLException {
        List<Combat> combats = new ArrayList<>();
        Statement st = connection.createStatement();
        ResultSet result = st.executeQuery(
                "SELECT id_combate, id_local, data, sumario FROM combate ORDER BY id_combate"
        );
        while (result.next()) {
            Combat rawCombat = Combat.fromResultSet(result);
            combats.add(new Combat(
                    rawCombat.getId(),
                    rawCombat.getLocationId(),
                    rawCombat.getDate(),
                    rawCombat.getSummary(),
                    combatantDAO.findCharacterIdsByCombatId(rawCombat.getId())
            ));
        }
        st.close();
        return combats;
    }
}
