package contracts;

import model.RpgClass;

import java.sql.SQLException;
import java.util.List;

public interface RpgClassDAO {
    void insert(RpgClass newRpgClass) throws SQLException;
    void update(RpgClass rpgClass) throws SQLException;
    void remove(Integer rpgClassId) throws SQLException;
    RpgClass findById(Integer rpgClassId) throws SQLException;
    List<RpgClass> listAll() throws SQLException;
}
