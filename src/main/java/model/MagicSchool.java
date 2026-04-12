package model;

public class MagicSchool {
    private Integer id;
    private String name;
    private String description;

    public MagicSchool(Integer id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
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
}
