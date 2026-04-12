package model;

import java.math.BigDecimal;

public class Item {
    private Integer id;
    private String name;
    private Integer description;
    private BigDecimal weight;
    private BigDecimal monetaryValue;

    public Item(Integer id, String name, Integer description, BigDecimal weight, BigDecimal monetaryValue) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.weight = weight;
        this.monetaryValue = monetaryValue;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getDescription() {
        return description;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public BigDecimal getMonetaryValue() {
        return monetaryValue;
    }
}
