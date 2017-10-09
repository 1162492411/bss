package com.zhd.pojo;

import java.math.BigDecimal;

public class Consumption {
    private Integer id;

    private Integer oid;

    private String uid;

    private BigDecimal conAmount;

    private BigDecimal payAmount;

    private Integer cid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOid() {
        return oid;
    }

    public void setOid(Integer oid) {
        this.oid = oid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }

    public BigDecimal getConAmount() {
        return conAmount;
    }

    public void setConAmount(BigDecimal conAmount) {
        this.conAmount = conAmount;
    }

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }
}