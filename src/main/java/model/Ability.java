package model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Ability {
    private Integer id;
    private String name;
    private String description;
    private String baseAttribute;

    public Ability(String name, String description, String baseAttribute) {
        this(null, name, description, baseAttribute);
    }

    public Ability(Integer id, String name, String description, String baseAttribute) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.baseAttribute = baseAttribute;
    }

    public static Ability fromResultSet(ResultSet result) throws SQLException {
        return new Ability(
                result.getInt(1),
                result.getString(2),
                result.getString(3),
                result.getString(4)
        );
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
