package model.relationship;

public class MagicAttribute {
    private Integer magicId;
    private Integer qualityId;
    private Integer value;

    public MagicAttribute(Integer magicId, Integer qualityId, Integer value) {
        this.magicId = magicId;
        this.qualityId = qualityId;
        this.value = value;
    }

    public Integer getMagicId() {
        return magicId;
    }

    public Integer getQualityId() {
        return qualityId;
    }

    public Integer getValue() {
        return value;
    }
}
