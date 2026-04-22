package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Combat {
    private Integer id;
    private Integer locationId;
    private Timestamp date;
    private String summary;
    private List<Integer> combatantIds;

    public Combat(Integer locationId, Timestamp date, String summary, List<Integer> combatantIds) {
        this(null, locationId, date, summary, combatantIds);
    }

    public Combat(Integer id, Integer locationId, Timestamp date, String summary, List<Integer> combatantIds) {
        this.id = id;
        this.locationId = locationId;
        this.date = date;
        this.summary = summary;
        this.combatantIds = combatantIds != null ? combatantIds : new ArrayList<>();
    }

    public static Combat fromResultSet(ResultSet result) throws SQLException {
        return new Combat(
                result.getInt(1),
                result.getInt(2),
                result.getTimestamp(3),
                result.getString(4),
                new ArrayList<>()
        );
    }

    public Integer getId() {
        return id;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public Timestamp getDate() {
        return date;
    }

    public String getSummary() {
        return summary;
    }

    public List<Integer> getCombatantIds() {
        return combatantIds;
    }
}
