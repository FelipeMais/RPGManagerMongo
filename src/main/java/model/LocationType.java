package model;

import org.bson.Document;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LocationType {
    private Integer id;
    private String name;
    private  String description;

    public LocationType(String name, String description) {
        this(null, name, description);
    }

    public LocationType(Integer id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public static LocationType fromResultSet(ResultSet result) throws SQLException {
        return new LocationType(
                result.getInt(1),
                result.getString(2),
                result.getString(3)
        );
    }

    public static LocationType fromDocument(Document doc) {
        return new LocationType(
                doc.getInteger("_id"),
                doc.getString("nome_tipo_local"),
                doc.getString("descricao")
        );
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
