package contracts;

import model.Species;

import java.sql.SQLException;
import java.util.List;

public interface SpeciesDAO {
    void insert(Species newSpecies) throws SQLException;
    void update(Species species) throws SQLException;
    void remove(Integer speciesId) throws SQLException;
    Species findById(Integer speciesId) throws SQLException;
    List<Species> listAll() throws SQLException;
}
