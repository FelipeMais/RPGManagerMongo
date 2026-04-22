package dao;

import contracts.CombatActionDAO;
import model.CombatAction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class CombatActionSqlDAO implements CombatActionDAO {
    private final Connection connection;

    public CombatActionSqlDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Integer insert(CombatAction combatAction) throws SQLException {
        PreparedStatement st = connection.prepareStatement(
                "INSERT INTO acaocombate(id_combate, id_tipo_acao_combate, id_agente, id_alvo, id_item_usado, id_magia_usada, ordem_turno, valor_resultado) VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS
        );
        st.setInt(1, combatAction.getCombatId());
        st.setInt(2, combatAction.getCombatActionTypeId());
        st.setInt(3, combatAction.getActorId());
        setNullableInteger(st, 4, combatAction.getTargetId());
        setNullableInteger(st, 5, combatAction.getItemId());
        setNullableInteger(st, 6, combatAction.getMagicId());
        st.setInt(7, combatAction.getTurnOrder());
        st.setInt(8, combatAction.getResultValue());
        st.executeUpdate();

        ResultSet generatedKeys = st.getGeneratedKeys();
        if (!generatedKeys.next()) {
            generatedKeys.close();
            st.close();
            throw new SQLException("Nao foi possivel obter o ID do registro de combate.");
        }

        Integer actionId = generatedKeys.getInt(1);
        generatedKeys.close();
        st.close();
        return actionId;
    }

    @Override
    public CombatAction findById(Integer combatActionId) throws SQLException {
        PreparedStatement st = connection.prepareStatement(
                "SELECT id_action, id_combate, id_tipo_acao_combate, id_agente, id_alvo, id_item_usado, id_magia_usada, ordem_turno, valor_resultado FROM acaocombate WHERE id_action = ?"
        );
        st.setInt(1, combatActionId);
        ResultSet result = st.executeQuery();
        CombatAction combatAction = null;
        if (result.next()) {
            combatAction = CombatAction.fromResultSet(result);
        }
        st.close();
        return combatAction;
    }

    @Override
    public List<CombatAction> listByCombatId(Integer combatId) throws SQLException {
        List<CombatAction> combatActions = new ArrayList<>();
        PreparedStatement st = connection.prepareStatement(
                "SELECT id_action, id_combate, id_tipo_acao_combate, id_agente, id_alvo, id_item_usado, id_magia_usada, ordem_turno, valor_resultado FROM acaocombate WHERE id_combate = ? ORDER BY ordem_turno, id_action"
        );
        st.setInt(1, combatId);
        ResultSet result = st.executeQuery();
        while (result.next()) {
            combatActions.add(CombatAction.fromResultSet(result));
        }
        st.close();
        return combatActions;
    }

    private void setNullableInteger(PreparedStatement st, int parameterIndex, Integer value) throws SQLException {
        if (value == null) {
            st.setNull(parameterIndex, Types.INTEGER);
            return;
        }
        st.setInt(parameterIndex, value);
    }
}
