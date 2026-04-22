package model.relationship;

import model.Skill;

public class CharacterSheetSkill {
    private Integer sheetId;
    private Integer skillId;
    private Integer value;
    private Skill skill;

    public CharacterSheetSkill(Integer sheetId, Integer skillId, Integer value){
        this(sheetId, skillId, value, null);
    }

    public CharacterSheetSkill(Integer sheetId, Integer skillId, Integer value, Skill skill) {
        this.sheetId = sheetId;
        this.skillId = skillId;
        this.value = value;
        this.skill = skill;
    }

    public Integer getSheetId() {
        return sheetId;
    }

    public Integer getSkillId() {
        return skillId;
    }

    public Integer getValue() {
        return value;
    }

    public Skill getSkill() {
        return skill;
    }
}
