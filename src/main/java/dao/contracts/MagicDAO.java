package dao.contracts;

import model.Magic;

import java.sql.SQLException;
import java.util.List;

public interface MagicDAO {
    public void insert(Magic newMagic) throws SQLException;
    public void remove();
    public Magic findById();
    public List<Magic> listAll();
    //...adicionar outros métodos especificos
}
