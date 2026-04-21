package model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Character {

    private Integer id;
    private Integer playerId;
    private Integer sheetId;
    private Integer currentLocationId;
    private String name;
    private Integer hitPoints;
    private Integer manaPoints;
    private String history;

    public Character(Integer playerId, Integer sheetId, Integer currentLocationId, String name, Integer hitPoints, Integer manaPoints, String history) {
        this(null, playerId, sheetId, currentLocationId, name, hitPoints, manaPoints, history);
    }

    public Character(Integer id, Integer playerId, Integer sheetId, Integer currentLocationId, String name, Integer hitPoints, Integer manaPoints, String history) {
        this.id = id;
        this.playerId = playerId;
        this.sheetId = sheetId;
        this.currentLocationId = currentLocationId;
        this.name = name;
        this.hitPoints = hitPoints;
        this.manaPoints = manaPoints;
        this.history = history;
    }

    public static Character fromResultSet(ResultSet result) throws SQLException {
        Integer playerId = result.getInt(2);
        if (result.wasNull()) {
            playerId = null;
        }

        Integer sheetId = result.getInt(3);
        if (result.wasNull()) {
            sheetId = null;
        }

        Integer currentLocationId = result.getInt(4);
        if (result.wasNull()) {
            currentLocationId = null;
        }

        return new Character(
                result.getInt(1),
                playerId,
                sheetId,
                currentLocationId,
                result.getString(5),
                result.getInt(6),
                result.getInt(7),
                result.getString(8)
        );
    }

    public Integer getId() {
        return id;
    }

    public Integer getPlayerId() {
        return playerId;
    }

    public Integer getSheetId() {
        return sheetId;
    }

    public Integer getCurrentLocationId() {
        return currentLocationId;
    }

    public String getName() {
        return name;
    }

    public Integer getHitPoints() {
        return hitPoints;
    }

    public Integer getManaPoints() {
        return manaPoints;
    }

    public String getHistory() {
        return history;
    }
}
