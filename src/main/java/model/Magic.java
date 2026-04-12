package model;

public class Magic {
    private Integer id;
    private String name;
    private String description;
    private Integer manaCost;
    private Integer minLevel;
    private String dices;

    public Magic(Integer id, String name, String description, Integer manaCost, Integer minLevel, String dices) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.manaCost = manaCost;
        this.minLevel = minLevel;
        this.dices = dices;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Integer getManaCost() {
        return manaCost;
    }

    public Integer getMinLevel() {
        return minLevel;
    }

    public String getDices() {
        return dices;
    }
}
