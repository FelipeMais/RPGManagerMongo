package model;

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
    private Integer charisma;
    private Integer wisdom;
    private Integer level;

    public CharacterSheet(Integer id, Integer classId, Integer speciesId, Integer maxHitPoints, Integer maxManaPoints, Integer strength, Integer dexterity, Integer constitution, Integer intelligence, Integer charisma, Integer wisdom, Integer level) {
        this.id = id;
        this.classId = classId;
        this.speciesId = speciesId;
        this.maxHitPoints = maxHitPoints;
        this.maxManaPoints = maxManaPoints;
        this.strength = strength;
        this.dexterity = dexterity;
        this.constitution = constitution;
        this.intelligence = intelligence;
        this.charisma = charisma;
        this.wisdom = wisdom;
        this.level = level;
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

    public Integer getCharisma() {
        return charisma;
    }

    public Integer getWisdom() {
        return wisdom;
    }

    public Integer getLevel() {
        return level;
    }
}
