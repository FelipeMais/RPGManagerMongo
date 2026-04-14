package dao.contracts;

import model.Magic;

import java.sql.SQLException;
import java.util.List;

public interface MagicDAO {
    public void insert(Magic newMagic) throws SQLException;
    public void remove(Integer magicId) throws SQLException;
    public Magic findById(Integer idMagia);
    public List<Magic> listAll();
    //...adicionar outros métodos especificos
}
