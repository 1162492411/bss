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

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Boolean getType() {
        return type;
    }

    public void setType(Boolean type) {
        this.type = type;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }

    public BigDecimal getStartLocationX() {
        return startLocationX;
    }

    public void setStartLocationX(BigDecimal startLocationX) {
        this.startLocationX = startLocationX;
    }

    public BigDecimal getStartLocationY() {
        return startLocationY;
    }

    public void setStartLocationY(BigDecimal startLocationY) {
        this.startLocationY = startLocationY;
    }

    public BigDecimal getEndLocationX() {
        return endLocationX;
    }

    public void setEndLocationX(BigDecimal endLocationX) {
        this.endLocationX = endLocationX;
    }

    public BigDecimal getEndLocationY() {
        return endLocationY;
    }

    public void setEndLocationY(BigDecimal endLocationY) {
        this.endLocationY = endLocationY;
    }

    public String getBids() {
        return bids;
    }

    public void setBids(String bids) {
        this.bids = bids == null ? null : bids.trim();
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}