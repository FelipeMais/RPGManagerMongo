package model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CombatAction {
    private Integer id;
    private Integer combatId;
    private Integer combatActionTypeId;
    private Integer actorId;
    private Integer targetId;
    private Integer itemId;
    private Integer magicId;
    private Integer turnOrder;
    private Integer resultValue;

    public CombatAction(Integer combatId, Integer combatActionTypeId, Integer actorId, Integer targetId, Integer itemId, Integer magicId, Integer turnOrder, Integer resultValue) {
        this(null, combatId, combatActionTypeId, actorId, targetId, itemId, magicId, turnOrder, resultValue);
    }

    public CombatAction(Integer id, Integer combatId, Integer combatActionTypeId, Integer actorId, Integer targetId, Integer itemId, Integer magicId, Integer turnOrder, Integer resultValue) {
        this.id = id;
        this.combatId = combatId;
        this.combatActionTypeId = combatActionTypeId;
        this.actorId = actorId;
        this.targetId = targetId;
        this.itemId = itemId;
        this.magicId = magicId;
        this.turnOrder = turnOrder;
        this.resultValue = resultValue;
    }

    public static CombatAction fromResultSet(ResultSet result) throws SQLException {
        Integer targetId = result.getInt(5);
        if (result.wasNull()) {
            targetId = null;
        }

        Integer itemId = result.getInt(6);
        if (result.wasNull()) {
            itemId = null;
        }

        Integer magicId = result.getInt(7);
        if (result.wasNull()) {
            magicId = null;
        }

        return new CombatAction(
                result.getInt(1),
                result.getInt(2),
                result.getInt(3),
                result.getInt(4),
                targetId,
                itemId,
                magicId,
                result.getInt(8),
                result.getInt(9)
        );
    }

    public Integer getId() {
        return id;
    }

    public Integer getCombatId() {
        return combatId;
    }

    public Integer getCombatActionTypeId() {
        return combatActionTypeId;
    }

    public Integer getActorId() {
        return actorId;
    }

    public Integer getTargetId() {
        return targetId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public Integer getMagicId() {
        return magicId;
    }

    public Integer getTurnOrder() {
        return turnOrder;
    }

    public Integer getResultValue() {
        return resultValue;
    }
}
