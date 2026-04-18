package model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RpgClass {
    private Integer idClass;
    private String className;
    private String description;

    public RpgClass(Integer idClass, String className, String description) {
        this.idClass = idClass;
        this.className = className;
        this.description = description;
    }

    public static RpgClass fromResultSet(ResultSet result) throws SQLException {
        return new RpgClass(
                result.getInt(1),
                result.getString(2),
                result.getString(3)
        );
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
