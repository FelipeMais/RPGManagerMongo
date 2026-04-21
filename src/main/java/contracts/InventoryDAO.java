package contracts;

import model.relationship.InventoryItem;

import java.sql.SQLException;
import java.util.List;

public interface InventoryDAO {
    void insert(InventoryItem inventoryItem) throws SQLException;
    void update(InventoryItem inventoryItem) throws SQLException;
    void remove(Integer characterId, Integer itemId) throws SQLException;
    void removeByCharacterId(Integer characterId) throws SQLException;
    InventoryItem findByCharacterIdAndItemId(Integer characterId, Integer itemId) throws SQLException;
    List<InventoryItem> findByCharacterId(Integer characterId) throws SQLException;
}
