package contracts;

import java.sql.SQLException;
import java.util.List;

public interface CombatantDAO {
    void insert(Integer combatId, Integer characterId) throws SQLException;
    void remove(Integer combatId, Integer characterId) throws SQLException;
    void removeByCombatId(Integer combatId) throws SQLException;
    List<Integer> findCharacterIdsByCombatId(Integer combatId) throws SQLException;
}
