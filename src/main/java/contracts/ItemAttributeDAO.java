package contracts;

import model.relationship.ItemAttribute;

import java.sql.SQLException;
import java.util.List;

public interface ItemAttributeDAO {
    void insert(ItemAttribute itemAttribute) throws SQLException;
    void removeByItemId(Integer itemId) throws SQLException;
    List<ItemAttribute> findByItemId(Integer itemId) throws SQLException;
    
}
