package com.zhd.pojo;

import java.math.BigDecimal;

public class Area {
    private Integer id;

    private String name;

    private BigDecimal northPoint;

    private BigDecimal southPoint;

    private BigDecimal westPoint;

    private BigDecimal eastPoint;

    private Boolean type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public BigDecimal getNorthPoint() {
        return northPoint;
    }

    public void setNorthPoint(BigDecimal northPoint) {
        this.northPoint = northPoint;
    }

    public BigDecimal getSouthPoint() {
        return southPoint;
    }

    public void setSouthPoint(BigDecimal southPoint) {
        this.southPoint = southPoint;
    }

    public BigDecimal getWestPoint() {
        return westPoint;
    }

    public void setWestPoint(BigDecimal westPoint) {
        this.westPoint = westPoint;
    }

    public BigDecimal getEastPoint() {
        return eastPoint;
    }

    public void setEastPoint(BigDecimal eastPoint) {
        this.eastPoint = eastPoint;
    }

    public Boolean getType() {
        return type;
    }

    public void setType(Boolean type) {
        this.type = type;
    }

    public Area() {
    }



    @Override
    public String toString() {
        return "Area{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", northPoint=" + northPoint +
                ", southPoint=" + southPoint +
                ", westPoint=" + westPoint +
                ", eastPoint=" + eastPoint +
                ", type=" + type +
                '}';
    }
}