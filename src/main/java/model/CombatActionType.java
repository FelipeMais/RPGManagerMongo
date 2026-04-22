package model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CombatActionType {
    private Integer id;
    private String name;

    public CombatActionType(String name) {
        this(null, name);
    }

    public CombatActionType(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public static CombatActionType fromResultSet(ResultSet result) throws SQLException {
        return new CombatActionType(result.getInt(1), result.getString(2));
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
