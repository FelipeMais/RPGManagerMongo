package contracts;

import model.Item;

import java.sql.SQLException;
import java.util.List;

public interface ItemDAO {
    void insert(Item newItem) throws SQLException;
    void update(Item item) throws SQLException;
    void remove(Integer itemId) throws SQLException;
    Item findById(Integer idItem) throws SQLException;
    List<Item> listAll() throws SQLException;
} 
