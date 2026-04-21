package model;

import model.relationship.InventoryItem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Character {

    private Integer id;
    private Integer playerId;
    private Integer sheetId;
    private Integer currentLocationId;
    private String name;
    private Integer hitPoints;
    private Integer manaPoints;
    private String history;
    private List<InventoryItem> inventory;

    public Character(Integer playerId, Integer sheetId, Integer currentLocationId, String name, Integer hitPoints, Integer manaPoints, String history) {
        this(null, playerId, sheetId, currentLocationId, name, hitPoints, manaPoints, history, new ArrayList<>());
    }

    public Character(Integer playerId, Integer sheetId, Integer currentLocationId, String name, Integer hitPoints, Integer manaPoints, String history, List<InventoryItem> inventory) {
        this(null, playerId, sheetId, currentLocationId, name, hitPoints, manaPoints, history, inventory);
    }

    public Character(Integer id, Integer playerId, Integer sheetId, Integer currentLocationId, String name, Integer hitPoints, Integer manaPoints, String history) {
        this(id, playerId, sheetId, currentLocationId, name, hitPoints, manaPoints, history, new ArrayList<>());
    }

    public Character(Integer id, Integer playerId, Integer sheetId, Integer currentLocationId, String name, Integer hitPoints, Integer manaPoints, String history, List<InventoryItem> inventory) {
        this.id = id;
        this.playerId = playerId;
        this.sheetId = sheetId;
        this.currentLocationId = currentLocationId;
        this.name = name;
        this.hitPoints = hitPoints;
        this.manaPoints = manaPoints;
        this.history = history;
        this.inventory = inventory;
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
                result.getString(8),
                new ArrayList<>()
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

    public List<InventoryItem> getInventory() {
        return inventory;
    }

    public void setInventory(List<InventoryItem> inventory) {
        this.inventory = inventory;
    }
}
