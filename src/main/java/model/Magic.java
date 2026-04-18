package model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Magic {
    private Integer id;
    private String name;
    private String description;
    private Integer manaCost;
    private Integer minLevel;
    private String dices;

    public Magic(String name, String description, Integer manaCost, Integer minLevel, String dices) {
        this(null, name, description, manaCost, minLevel, dices);
    }

    public Magic(Integer id, String name, String description, Integer manaCost, Integer minLevel, String dices) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.manaCost = manaCost;
        this.minLevel = minLevel;
        this.dices = dices;
    }

    public static Magic fromResultSet(ResultSet result) throws SQLException {
        return new Magic(
                result.getInt(1),
                result.getString(2),
                result.getString(3),
                result.getInt(4),
                result.getInt(5),
                result.getString(6));
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
