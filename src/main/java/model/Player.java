package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

public class Player {
    private Integer id;
    private String name;
    private Date entryDate;
    private Boolean active;

    public Player(String name, Date entryDate, Boolean active) {
        this(null, name, entryDate, active);
    }

    public Player(Integer id, String name, Date entryDate, Boolean active) {
        this.id = id;
        this.name = name;
        this.entryDate = entryDate;
        this.active = active;
    }

    public static Player fromResultSet(ResultSet result) throws SQLException {
        return new Player(
                result.getInt(1),
                result.getString(2),
                result.getTimestamp(3),
                result.getBoolean(4)
        );
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public Boolean getActive() {
        return active;
    }
}
