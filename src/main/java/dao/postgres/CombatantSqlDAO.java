package dao.postgres;

import contracts.CombatantDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CombatantSqlDAO implements CombatantDAO {
    private final Connection connection;

    public CombatantSqlDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Integer combatId, Integer characterId) throws SQLException {
        PreparedStatement st = connection.prepareStatement(
                "INSERT INTO combatentes(id_combate, id_personagem) VALUES (?, ?)"
        );
        st.setInt(1, combatId);
        st.setInt(2, characterId);
        st.executeUpdate();
        st.close();
    }

    @Override
    public void remove(Integer combatId, Integer characterId) throws SQLException {
        PreparedStatement st = connection.prepareStatement(
                "DELETE FROM combatentes WHERE id_combate = ? AND id_personagem = ?"
        );
        st.setInt(1, combatId);
        st.setInt(2, characterId);
        st.executeUpdate();
        st.close();
    }

    @Override
    public void removeByCombatId(Integer combatId) throws SQLException {
        PreparedStatement st = connection.prepareStatement("DELETE FROM combatentes WHERE id_combate = ?");
        st.setInt(1, combatId);
        st.executeUpdate();
        st.close();
    }

    @Override
    public List<Integer> findCharacterIdsByCombatId(Integer combatId) throws SQLException {
        List<Integer> characterIds = new ArrayList<>();
        PreparedStatement st = connection.prepareStatement(
                "SELECT id_personagem FROM combatentes WHERE id_combate = ? ORDER BY id_personagem"
        );
        st.setInt(1, combatId);
        ResultSet result = st.executeQuery();
        while (result.next()) {
            characterIds.add(result.getInt(1));
        }
        st.close();
        return characterIds;
    }
}
