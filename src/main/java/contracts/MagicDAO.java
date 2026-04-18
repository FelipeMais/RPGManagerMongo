package contracts;

import model.Magic;

import java.sql.SQLException;
import java.util.List;

public interface MagicDAO {
    void insert(Magic newMagic) throws SQLException;
    void update(Magic magic) throws SQLException;
    void remove(Integer magicId) throws SQLException;
    Magic findById(Integer idMagia) throws SQLException;
    List<Magic> listAll() throws SQLException;
}
