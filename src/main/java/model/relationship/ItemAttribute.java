package model.relationship;

import model.Attribute;

public class ItemAttribute {
    private Integer itemId;
    private Integer attributeId;
    private Integer value;
    private Attribute attribute;

    public ItemAttribute(Integer itemId, Integer attributeId, Integer value){
        this(itemId, attributeId, value, null);
    }

    public ItemAttribute(Integer itemId, Integer attributeId, Integer value, Attribute attribute){
        this.itemId = itemId;
        this.attributeId = attributeId;
        this.value = value;
        this.attribute = attribute;
    }

    public Integer getItemId() {
        return itemId;
    }

    public Integer getAttributeId() {
        return attributeId;
    }

    public Integer getValue() {
        return value;
    }

    public Attribute getAttribute() {
        return attribute;
    }
}
