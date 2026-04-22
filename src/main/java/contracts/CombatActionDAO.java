package contracts;

import model.CombatAction;

import java.sql.SQLException;
import java.util.List;

public interface CombatActionDAO {
    Integer insert(CombatAction combatAction) throws SQLException;
    CombatAction findById(Integer combatActionId) throws SQLException;
    List<CombatAction> listByCombatId(Integer combatId) throws SQLException;
}
