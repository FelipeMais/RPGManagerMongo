package model;

public class Location {
    private Integer parentId;
    private Integer id;
    private Integer locationTypeId;
    private String name;
    private String description;

    public Location(Integer parentId, Integer id, Integer locationTypeId, String name, String description) {
        this.parentId = parentId;
        this.id = id;
        this.locationTypeId = locationTypeId;
        this.name = name;
        this.description = description;
    }

    public Integer getParentId() {
        return parentId;
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

    public Integer getLocationTypeId() {
        return locationTypeId;
    }
}
