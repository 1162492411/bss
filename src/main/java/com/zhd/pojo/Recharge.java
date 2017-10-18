package com.zhd.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class Recharge {
    private Integer id;

    private String uid;

    private Boolean type;

    private Date rechargeTime;

    private BigDecimal amount;

    private String pid;

    private String purl;

    private Byte status;

    public Integer getId() {
        return id;
    }

    public Recharge setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getUid() {
        return uid;
    }

    public Recharge setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
        return this;
    }

    public Boolean getType() {
        return type;
    }

    public Recharge setType(Boolean type) {
        this.type = type;
        return this;
    }

    public Date getRechargeTime() {
        return rechargeTime;
    }

    public Recharge setRechargeTime(Date rechargeTime) {
        this.rechargeTime = rechargeTime;
        return this;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Recharge setAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public String getPid() {
        return pid;
    }

    public Recharge setPid(String pid) {
        this.pid = pid == null ? null : pid.trim();
        return this;
    }

    public String getPurl() {
        return purl;
    }

    public Recharge setPurl(String purl) {
        this.purl = purl == null ? null : purl.trim();
        return this;
    }

    public Byte getStatus() {
        return status;
    }

    public Recharge setStatus(Byte status) {
        this.status = status;
        return this;
    }

    @Override
    public String toString() {
        return "Recharge{" +
                "id=" + id +
                ", uid='" + uid + '\'' +
                ", type=" + type +
                ", rechargeTime=" + rechargeTime +
                ", amount=" + amount +
                ", pid='" + pid + '\'' +
                ", purl='" + purl + '\'' +
                ", status=" + status +
                '}';
    }
}