package model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Location {
    private Integer parentId;
    private Integer id;
    private Integer locationTypeId;
    private String name;
    private String description;

    public Location(Integer parentId, Integer locationTypeId, String name, String description) {
        this(parentId, null, locationTypeId, name, description);
    }

    public Location(Integer parentId, Integer id, Integer locationTypeId, String name, String description) {
        this.parentId = parentId;
        this.id = id;
        this.locationTypeId = locationTypeId;
        this.name = name;
        this.description = description;
    }

    public static Location fromResultSet(ResultSet result) throws SQLException {
        Integer parentId = result.getInt(1);
        if (result.wasNull()) {
            parentId = null;
        }

        Integer locationTypeId = result.getInt(3);
        if (result.wasNull()) {
            locationTypeId = null;
        }

        return new Location(
                parentId,
                result.getInt(2),
                locationTypeId,
                result.getString(4),
                result.getString(5)
        );
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
