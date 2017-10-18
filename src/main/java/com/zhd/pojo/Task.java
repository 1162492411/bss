package com.zhd.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class Task {
    private Integer id;

    private String name;

    private Boolean type;

    private String uid;

    private BigDecimal startLocationX;

    private BigDecimal startLocationY;

    private BigDecimal endLocationX;

    private BigDecimal endLocationY;

    private String bids;

    private Boolean status;

    private Date startTime;

    private Date endTime;

    public Integer getId() {
        return id;
    }

    public Task setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Task setName(String name) {
        this.name = name == null ? null : name.trim();
        return this;
    }

    public Boolean getType() {
        return type;
    }

    public Task setType(Boolean type) {
        this.type = type;
        return this;
    }

    public String getUid() {
        return uid;
    }

    public Task setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
        return this;
    }

    public BigDecimal getStartLocationX() {
        return startLocationX;
    }

    public Task setStartLocationX(BigDecimal startLocationX) {
        this.startLocationX = startLocationX;
        return this;
    }

    public BigDecimal getStartLocationY() {
        return startLocationY;
    }

    public Task setStartLocationY(BigDecimal startLocationY) {
        this.startLocationY = startLocationY;
        return this;
    }

    public BigDecimal getEndLocationX() {
        return endLocationX;
    }

    public Task setEndLocationX(BigDecimal endLocationX) {
        this.endLocationX = endLocationX;
        return this;
    }

    public BigDecimal getEndLocationY() {
        return endLocationY;
    }

    public Task setEndLocationY(BigDecimal endLocationY) {
        this.endLocationY = endLocationY;
        return this;
    }

    public String getBids() {
        return bids;
    }

    public Task setBids(String bids) {
        this.bids = bids == null ? null : bids.trim();
        return this;
    }

    public Boolean getStatus() {
        return status;
    }

    public Task setStatus(Boolean status) {
        this.status = status;
        return this;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Task setStartTime(Date startTime) {
        this.startTime = startTime;
        return this;
    }

    public Date getEndTime() {
        return endTime;
    }

    public Task setEndTime(Date endTime) {
        this.endTime = endTime;
        return this;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", uid='" + uid + '\'' +
                ", startLocationX=" + startLocationX +
                ", startLocationY=" + startLocationY +
                ", endLocationX=" + endLocationX +
                ", endLocationY=" + endLocationY +
                ", bids='" + bids + '\'' +
                ", status=" + status +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}