package contracts;

import model.CombatActionType;

import java.sql.SQLException;
import java.util.List;

public interface CombatActionTypeDAO {
    void insert(CombatActionType newCombatActionType) throws SQLException;
    void update(CombatActionType combatActionType) throws SQLException;
    void remove(Integer combatActionTypeId) throws SQLException;
    CombatActionType findById(Integer combatActionTypeId) throws SQLException;
    List<CombatActionType> listAll() throws SQLException;
}
