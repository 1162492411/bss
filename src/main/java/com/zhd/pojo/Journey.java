package com.zhd.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class Journey {
    private Integer id;

    private String uid;

    private Long bid;

    private Date startTime;

    private Date endTime;

    private Integer rideTime;

    private Integer distance;

    private BigDecimal amount;

    public Integer getId() {
        return id;
    }

    public Journey setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getUid() {
        return uid;
    }

    public Journey setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
        return this;
    }

    public Long getBid() {
        return bid;
    }

    public Journey setBid(Long bid) {
        this.bid = bid;
        return this;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Journey setStartTime(Date startTime) {
        this.startTime = startTime;
        return this;
    }

    public Date getEndTime() {
        return endTime;
    }

    public Journey setEndTime(Date endTime) {
        this.endTime = endTime;
        return this;
    }

    public Integer getRideTime() {
        return rideTime;
    }

    public Journey setRideTime(Integer rideTime) {
        this.rideTime = rideTime;
        return this;
    }

    public Integer getDistance() {
        return distance;
    }

    public Journey setDistance(Integer distance) {
        this.distance = distance;
        return this;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Journey setAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public static String[] getKeys(){
        return new String[]{"id", "uid", "bid","startTime","endTime","rideTime","distance","amount"};
    }

    public static String[] getNames(){
        return new String[]{"行程编号","用户编号","车辆编号","起始时间","结束时间","骑行时间","骑行距离/m","骑行花费/元"};
    }

    @Override
    public String toString() {
        return "Journey{" +
                "id=" + id +
                ", uid='" + uid + '\'' +
                ", bid=" + bid +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", rideTime=" + rideTime +
                ", distance=" + distance +
                ", amount=" + amount +
                '}';
    }
}