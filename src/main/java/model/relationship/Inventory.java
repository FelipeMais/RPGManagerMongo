package model.relationship;

import model.Item;

public class Inventory {
    private Integer characterId;
    private Integer itemId;
    private Integer value;
    private Item item;

    public Inventory(Integer characterId, Integer itemId, Integer value) {
        this(characterId, itemId, value, null);
    }

    public Inventory(Integer characterId, Integer itemId, Integer value, Item item) {
        this.characterId = characterId;
        this.itemId = itemId;
        this.value = value;
        this.item = item;
    }

    public Integer getCharacterId() {
        return characterId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public Integer getValue() {
        return value;
    }

    public Item getItem() {
        return item;
    }
}
