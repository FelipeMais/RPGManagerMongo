package contracts;

import model.Combat;

import java.sql.SQLException;
import java.util.List;

public interface CombatDAO {
    Integer insert(Combat newCombat) throws SQLException;
    void update(Combat combat) throws SQLException;
    void remove(Integer combatId) throws SQLException;
    Combat findById(Integer combatId) throws SQLException;
    List<Combat> listAll() throws SQLException;
}
