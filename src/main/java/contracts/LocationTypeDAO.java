package contracts;

import model.LocationType;

import java.sql.SQLException;
import java.util.List;

public interface LocationTypeDAO {
    void insert(LocationType newLocationType) throws SQLException;
    void update(LocationType locationType) throws SQLException;
    void remove(Integer locationTypeId) throws SQLException;
    LocationType findById(Integer locationTypeId) throws SQLException;
    List<LocationType> listAll() throws SQLException;
}
