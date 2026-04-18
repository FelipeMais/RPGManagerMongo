package model;

import java.util.Date;

public class Combat {
    private Integer id;
    private Integer locationId;
    private Date date;
    private String summary;

    public Combat(Integer id, Integer locationId, Date date, String summary) {
        this.id = id;
        this.locationId = locationId;
        this.date = date;
        this.summary = summary;
    }

    public Integer getId() {
        return id;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public Date getDate() {
        return date;
    }

    public String getSummary() {
        return summary;
    }
}
