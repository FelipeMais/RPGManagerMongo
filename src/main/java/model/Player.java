package model;

import java.util.Date;

public class Player {
    private Integer id;
    private String name;
    private Date entryDate;

    public Player(Integer id, String name, Date entryDate) {
        this.id = id;
        this.name = name;
        this.entryDate = entryDate;
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
}
