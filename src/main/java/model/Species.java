package model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Species {
    private Integer id;
    private String name;

    public Species(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public static Species fromResultSet(ResultSet result) throws SQLException {
        return new Species(
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
