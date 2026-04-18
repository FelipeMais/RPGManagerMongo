package model.relationship;

import model.Attribute;

public class MagicAttribute {
    private Integer magicId;
    private Integer attributeId;
    private Integer value;
    private Attribute attribute;

    public MagicAttribute(Integer magicId, Integer attributeId, Integer value) {
        this(magicId, attributeId, value, null);
    }

    public MagicAttribute(Integer magicId, Integer attributeId, Integer value, Attribute attribute) {
        this.magicId = magicId;
        this.attributeId = attributeId;
        this.value = value;
        this.attribute = attribute;
    }

    public Integer getMagicId() {
        return magicId;
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
