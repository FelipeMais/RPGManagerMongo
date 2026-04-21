package contracts;

import model.Ability;

import java.sql.SQLException;
import java.util.List;

public interface AbilityDAO {
    void insert(Ability newAbility) throws SQLException;
    void update(Ability ability) throws SQLException;
    void remove(Integer abilityId) throws SQLException;
    Ability findById(Integer abilityId) throws SQLException;
    List<Ability> listAll() throws SQLException;
}
