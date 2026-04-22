package model;

import model.relationship.CharacterSheetSkill;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CharacterSheet {
    private Integer id;
    private Integer classId;
    private Integer speciesId;
    private Integer maxHitPoints;
    private Integer maxManaPoints;
    private Integer strength;
    private Integer dexterity;
    private Integer constitution;
    private Integer intelligence;
    private Integer wisdom;
    private Integer charisma;
    private Integer level;
    private List<CharacterSheetSkill> skills;

    public CharacterSheet(Integer classId, Integer speciesId, Integer maxHitPoints, Integer maxManaPoints, Integer strength, Integer dexterity, Integer constitution, Integer intelligence, Integer wisdom, Integer charisma, Integer level, List<CharacterSheetSkill> skills) {
        this(null, classId, speciesId, maxHitPoints, maxManaPoints, strength, dexterity, constitution, intelligence, wisdom, charisma, level, skills);
    }

    public CharacterSheet(Integer id, Integer classId, Integer speciesId, Integer maxHitPoints, Integer maxManaPoints, Integer strength, Integer dexterity, Integer constitution, Integer intelligence, Integer wisdom, Integer charisma, Integer level, List<CharacterSheetSkill> skills) {
        this.id = id;
        this.classId = classId;
        this.speciesId = speciesId;
        this.maxHitPoints = maxHitPoints;
        this.maxManaPoints = maxManaPoints;
        this.strength = strength;
        this.dexterity = dexterity;
        this.constitution = constitution;
        this.intelligence = intelligence;
        this.wisdom = wisdom;
        this.charisma = charisma;
        this.level = level;
        this.skills = skills;
    }

    public static CharacterSheet fromResultSet(ResultSet result) throws SQLException {
        Integer classId = result.getInt(2);
        if (result.wasNull()) {
            classId = null;
        }

        Integer speciesId = result.getInt(3);
        if (result.wasNull()) {
            speciesId = null;
        }

        return new CharacterSheet(
                result.getInt(1),
                classId,
                speciesId,
                result.getInt(4),
                result.getInt(5),
                result.getInt(6),
                result.getInt(7),
                result.getInt(8),
                result.getInt(9),
                result.getInt(10),
                result.getInt(11),
                result.getInt(12),
                new ArrayList<>()
        );
    }

    public Integer getId() {
        return id;
    }

    public Integer getClassId() {
        return classId;
    }

    public Integer getSpeciesId() {
        return speciesId;
    }

    public Integer getMaxHitPoints() {
        return maxHitPoints;
    }

    public Integer getMaxManaPoints() {
        return maxManaPoints;
    }

    public Integer getStrength() {
        return strength;
    }

    public Integer getDexterity() {
        return dexterity;
    }

    public Integer getConstitution() {
        return constitution;
    }

    public Integer getIntelligence() {
        return intelligence;
    }

    public Integer getWisdom() {
        return wisdom;
    }

    public Integer getCharisma() {
        return charisma;
    }

    public Integer getLevel() {
        return level;
    }

    public List<CharacterSheetSkill> getSkills() {
        return skills;
    }

    public void setSkills(List<CharacterSheetSkill> skills) {
        this.skills = skills;
    }
}
