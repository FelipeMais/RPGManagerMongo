package model;

public class Character {

    private Integer id;
    private Integer recordId;
    private String name;
    private String description;
    private String history;

    private Integer hitPoints;
    private Integer manaPoints;

    public Character(Integer id, Integer recordId, String name, String description, String history, Integer hitPoints, Integer manaPoints) {
        this.id = id;
        this.recordId = recordId;
        this.name = name;
        this.description = description;
        this.history = history;
        this.hitPoints = hitPoints;
        this.manaPoints = manaPoints;
    }

    public Integer getId() {
        return id;
    }

    public Integer getRecordId() {
        return recordId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getHistory() {
        return history;
    }

    public Integer getHitPoints() {
        return hitPoints;
    }

    public Integer getManaPoints() {
        return manaPoints;
    }
}
