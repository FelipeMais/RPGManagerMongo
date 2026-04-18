package model;

public class Ability {
    private Integer id;
    private String name;
    private String description;
    private String baseAttribute;

    public Ability(Integer id, String name, String description, String baseAttribute) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.baseAttribute = baseAttribute;
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

    public String getBaseAttribute() {
        return baseAttribute;
    }
}
