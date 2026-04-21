package model.relationship;

import model.Item;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class InventoryItem {
    private Integer itemId;
    private Integer characterId;
    private Integer quantity;
    private Item item;

    public InventoryItem(Integer itemId, Integer characterId, Integer quantity, Item item) {
        this.itemId = itemId;
        this.characterId = characterId;
        this.quantity = quantity;
        this.item = item;
    }

    public static InventoryItem fromResultSet(ResultSet result) throws SQLException {
        Item item = new Item(
                result.getInt(1),
                result.getString(4),
                result.getString(5),
                result.getBigDecimal(6),
                result.getBigDecimal(7),
                new ArrayList<>()
        );

        return new InventoryItem(
                result.getInt(1),
                result.getInt(2),
                result.getInt(3),
                item
        );
    }

    public Integer getItemId() {
        return itemId;
    }

    public Integer getCharacterId() {
        return characterId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Item getItem() {
        return item;
    }
}
