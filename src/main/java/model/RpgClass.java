package model;

public class RpgClass {
    private Integer idClass;
    private String className;
    private String description;

    public RpgClass(Integer idClass, String className, String description) {
        this.idClass = idClass;
        this.className = className;
        this.description = description;
    }

    public Integer getIdClass() {
        return idClass;
    }

    public String getClassName() {
        return className;
    }

    public String getDescription() {
        return description;
    }
}
