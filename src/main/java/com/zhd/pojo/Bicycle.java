package com.zhd.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class Bicycle {
    private Integer id;

    private Byte status;

    private Byte type;

    private BigDecimal locationX;

    private BigDecimal locationY;

    private String batch;

    private Short sid;

    private Integer serviceTime;

    private Date investmentTime;

    private Integer mileage;

    public Integer getId() {
        return id;
    }

    public Bicycle setId(Integer id) {
        this.id = id;
        return this;
    }

    public Byte getStatus() {
        return status;
    }

    public Bicycle setStatus(Byte status) {
        this.status = status;
        return this;
    }

    public Byte getType() {
        return type;
    }

    public Bicycle setType(Byte type) {
        this.type = type;
        return this;
    }

    public BigDecimal getLocationX() {
        return locationX;
    }

    public Bicycle setLocationX(BigDecimal locationX) {
        this.locationX = locationX;
        return this;
    }

    public BigDecimal getLocationY() {
        return locationY;
    }

    public Bicycle setLocationY(BigDecimal locationY) {
        this.locationY = locationY;
        return this;
    }

    public String getBatch() {
        return batch;
    }

    public Bicycle setBatch(String batch) {
        this.batch = batch;
        return this;
    }

    public Short getSid() {
        return sid;
    }

    public Bicycle setSid(Short sid) {
        this.sid = sid;
        return this;
    }

    public Integer getServiceTime() {
        return serviceTime;
    }

    public Bicycle setServiceTime(Integer serviceTime) {
        this.serviceTime = serviceTime;
        return this;
    }

    public Date getInvestmentTime() {
        return investmentTime;
    }

    public Bicycle setInvestmentTime(Date investmentTime) {
        this.investmentTime = investmentTime;
        return this;
    }

    public Integer getMileage() {
        return mileage;
    }

    public Bicycle setMileage(Integer mileage) {
        this.mileage = mileage;
        return this;
    }

    public static String[] getKeys(){
        return new String[]{"id", "status", "type","batch","serviceTime","mileage","investmentTime"};
    }

    public static String[] getNames(){
        return new String[]{"编号","状态","类型","生产批次","总使用时间","总行驶里程","投产时间"};
    }

    @Override
    public String toString() {
        return "Bicycle{" +
                "id=" + id +
                ", status=" + status +
                ", type=" + type +
                ", locationX=" + locationX +
                ", locationY=" + locationY +
                ", batch=" + batch +
                ", sid=" + sid +
                ", serviceTime=" + serviceTime +
                ", investmentTime=" + investmentTime +
                ", mileage=" + mileage +
                '}';
    }
}