package model.relationship;

import java.util.Date;

public class CharacterController {
    private Integer characterId;
    private Integer playerId;
    private Integer controllerTypeId;
    private Date possessionDate;

    public CharacterController(Integer characterId, Integer playerId, Integer controllerTypeId, Date possessionDate) {
        this.characterId = characterId;
        this.playerId = playerId;
        this.controllerTypeId = controllerTypeId;
        this.possessionDate = possessionDate;
    }

    public Integer getCharacterId() {
        return characterId;
    }

    public Integer getPlayerId() {
        return playerId;
    }

    public Integer getControllerTypeId() {
        return controllerTypeId;
    }

    public Date getPossessionDate() {
        return possessionDate;
    }
}
