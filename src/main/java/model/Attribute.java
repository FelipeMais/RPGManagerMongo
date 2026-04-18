package model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Attribute {
    private Integer id;
    private String name;

    public Attribute(String name) {
        this(null, name);
    }

    public Attribute(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public static Attribute fromResultSet(ResultSet result) throws SQLException {
        return new Attribute(
                result.getInt(1),
                result.getString(2)
        );
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
