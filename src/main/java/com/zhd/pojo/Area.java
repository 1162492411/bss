package com.zhd.pojo;

import java.math.BigDecimal;

public class Area {
    private Integer id;

    private String name;

    private BigDecimal northPoint;

    private BigDecimal southPoint;

    private BigDecimal westPoint;

    private BigDecimal eastPoint;

    private Byte type;

    public Integer getId() {
        return id;
    }

    public Area setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Area setName(String name) {
        this.name = name == null ? null : name.trim();
        return this;
    }

    public BigDecimal getNorthPoint() {
        return northPoint;
    }

    public Area setNorthPoint(BigDecimal northPoint) {
        this.northPoint = northPoint;
        return this;
    }

    public BigDecimal getSouthPoint() {
        return southPoint;
    }

    public Area setSouthPoint(BigDecimal southPoint) {
        this.southPoint = southPoint;
        return this;
    }

    public BigDecimal getWestPoint() {
        return westPoint;
    }

    public Area setWestPoint(BigDecimal westPoint) {
        this.westPoint = westPoint;
        return this;
    }

    public BigDecimal getEastPoint() {
        return eastPoint;
    }

    public Area setEastPoint(BigDecimal eastPoint) {
        this.eastPoint = eastPoint;
        return this;
    }

    public Byte getType() {
        return type;
    }

    public Area setType(Byte type) {
        this.type = type;
        return this;
    }

    public String[] getKeys(){
        return new String[]{"id", "name", "northPoint", "southPoint", "westPoint", "eastPoint", "type"};
    }

    public String[] getNames(){
        return new String[]{"编号","区域名","区域最北部","区域最南部","区域最西部","区域最东部","类型"};
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