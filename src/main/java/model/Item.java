package model;

import model.relationship.ItemAttribute;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.types.Decimal128;

public class Item {
    private Integer id;
    private String name;
    private String description;
    private BigDecimal weight;
    private BigDecimal monetaryValue;
    private List<ItemAttribute> attributes;

    public Item(String name, String description, BigDecimal weight, BigDecimal monetaryValue, List<ItemAttribute> attributes) {
        this(null, name, description, weight, monetaryValue, attributes);
    }

    public Item(Integer id, String name, String description, BigDecimal weight, BigDecimal monetaryValue, List<ItemAttribute> attributes) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.weight = weight;
        this.monetaryValue = monetaryValue;
        this.attributes = attributes;
    }

    public static Item fromResultSet(ResultSet result) throws SQLException {
        return new Item(
                result.getInt(1),
                result.getString(2),
                result.getString(3),
                result.getBigDecimal(4),
                result.getBigDecimal(5),
                new ArrayList<>()
        );
    }

    public static Item fromDocument(Document doc) {
        if (doc == null) return null;

        return new Item(
                doc.getInteger("_id"),
                doc.getString("nome_item"),
                doc.getString("descricao"),
                toBigDecimal(doc.get("peso")),
                toBigDecimal(doc.get("valor_monetario")),
                new ArrayList<>()
        );
    }

    private static BigDecimal toBigDecimal(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof BigDecimal) {
            return (BigDecimal) value;
        }
        if (value instanceof Decimal128) {
            return ((Decimal128) value).bigDecimalValue();
        }
        if (value instanceof Number) {
            return new BigDecimal(value.toString());
        }
        return new BigDecimal(value.toString());
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

    public BigDecimal getWeight() {
        return weight;
    }

    public BigDecimal getMonetaryValue() {
        return monetaryValue;
    }

    public List<ItemAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<ItemAttribute> attributes) {
        this.attributes = attributes;
    }
}
