package com.zhd.pojo;

public class CouponInfo {
    private Integer id;

    private Integer cid;

    private String uid;

    public Integer getId() {
        return id;
    }

    public CouponInfo setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getCid() {
        return cid;
    }

    public CouponInfo setCid(Integer cid) {
        this.cid = cid;
        return this;
    }

    public String getUid() {
        return uid;
    }

    public CouponInfo setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
        return this;
    }
}