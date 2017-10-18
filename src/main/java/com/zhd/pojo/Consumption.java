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

    public Consumption setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getOid() {
        return oid;
    }

    public Consumption setOid(Integer oid) {
        this.oid = oid;
        return this;
    }

    public String getUid() {
        return uid;
    }

    public Consumption setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
        return this;
    }

    public BigDecimal getConAmount() {
        return conAmount;
    }

    public Consumption setConAmount(BigDecimal conAmount) {
        this.conAmount = conAmount;
        return this;
    }

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public Consumption setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
        return this;
    }

    public Integer getCid() {
        return cid;
    }

    public Consumption setCid(Integer cid) {
        this.cid = cid;
        return this;
    }


    @Override
    public String toString() {
        return "Consumption{" +
                "id=" + id +
                ", oid=" + oid +
                ", uid='" + uid + '\'' +
                ", conAmount=" + conAmount +
                ", payAmount=" + payAmount +
                ", cid=" + cid +
                '}';
    }
}