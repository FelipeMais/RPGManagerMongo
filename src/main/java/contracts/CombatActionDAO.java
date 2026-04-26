package contracts;

import model.CombatAction;
import model.dto.AcaoCombateDTO;

import java.sql.SQLException;
import java.util.List;

public interface CombatActionDAO {
    Integer insert(CombatAction combatAction) throws SQLException;
    CombatAction findById(Integer combatActionId) throws SQLException;
    List<CombatAction> listByCombatId(Integer combatId) throws SQLException;
    List<AcaoCombateDTO> listReportByActorIdAndType(Integer actorId, Integer actionTypeId) throws SQLException;
}
