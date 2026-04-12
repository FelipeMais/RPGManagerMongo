package model;

public class CombatAction {
    private Integer id;
    private Integer combatId;
    private Integer combatActionId;
    private Integer targetId;
    private Integer actorId;
    private Integer itemId;
    private Integer magicId;
    private Integer resultValue;

    public CombatAction(Integer id, Integer combatId, Integer combatActionId, Integer targetId, Integer actorId, Integer itemId, Integer magicId, Integer resultValue) {
        this.id = id;
        this.combatId = combatId;
        this.combatActionId = combatActionId;
        this.targetId = targetId;
        this.actorId = actorId;
        this.itemId = itemId;
        this.magicId = magicId;
        this.resultValue = resultValue;
    }

    public Integer getId() {
        return id;
    }

    public Integer getCombatId() {
        return combatId;
    }

    public Integer getCombatActionId() {
        return combatActionId;
    }

    public Integer getTargetId() {
        return targetId;
    }

    public Integer getActorId() {
        return actorId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public Integer getMagicId() {
        return magicId;
    }

    public Integer getResultValue() {
        return resultValue;
    }
}
