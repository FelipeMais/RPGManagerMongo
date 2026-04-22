package model.dto;

public class AcaoCombateDTO {
    private Integer combatId;
    private Integer turnOrder;
    private String actionTypeName;
    private String targetName;
    private String itemName;
    private String magicName;
    private Integer resultValue;

    public AcaoCombateDTO(Integer combatId, Integer turnOrder, String actionTypeName, String targetName, String itemName, String magicName, Integer resultValue) {
        this.combatId = combatId;
        this.turnOrder = turnOrder;
        this.actionTypeName = actionTypeName;
        this.targetName = targetName;
        this.itemName = itemName;
        this.magicName = magicName;
        this.resultValue = resultValue;
    }

    public Integer getCombatId() {
        return combatId;
    }

    public Integer getTurnOrder() {
        return turnOrder;
    }

    public String getActionTypeName() {
        return actionTypeName;
    }

    public String getTargetName() {
        return targetName;
    }

    public String getItemName() {
        return itemName;
    }

    public String getMagicName() {
        return magicName;
    }

    public Integer getResultValue() {
        return resultValue;
    }
}
