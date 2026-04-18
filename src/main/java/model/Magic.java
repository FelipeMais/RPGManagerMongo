package model;

import model.relationship.MagicAttribute;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Magic {
    private Integer id;
    private String name;
    private String description;
    private Integer manaCost;
    private Integer minLevel;
    private String dices;
    private List<MagicAttribute> attributes;

    public Magic(String name, String description, Integer manaCost, Integer minLevel, String dices, List<MagicAttribute> attributes) {
        this(null, name, description, manaCost, minLevel, dices, attributes);
    }

    public Magic(Integer id, String name, String description, Integer manaCost, Integer minLevel, String dices, List<MagicAttribute> attributes) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.manaCost = manaCost;
        this.minLevel = minLevel;
        this.dices = dices;
        this.attributes = attributes;
    }

    public static Magic fromResultSet(ResultSet result) throws SQLException {
        return new Magic(
                result.getInt(1),
                result.getString(2),
                result.getString(3),
                result.getInt(4),
                result.getInt(5),
                result.getString(6), new ArrayList<>());
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

    public List<MagicAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<MagicAttribute> attributes) {
        this.attributes = attributes;
    }
}
