package contracts;

import model.Location;

import java.sql.SQLException;
import java.util.List;

public interface LocationDAO {
    void insert(Location newLocation) throws SQLException;
    void update(Location location) throws SQLException;
    void remove(Integer locationId) throws SQLException;
    Location findById(Integer locationId) throws SQLException;
    List<Location> listAll() throws SQLException;
}
