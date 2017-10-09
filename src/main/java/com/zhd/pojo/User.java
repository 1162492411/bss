package com.zhd.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class User {
    private String id;

    private String name;

    private String password;

    private Byte type;

    private String avatar;

    private Byte credit;

    private Boolean status;

    private BigDecimal accountBalance;

    private BigDecimal redBalance;

    private Integer coupons;

    private Date dates;

    private String salt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar == null ? null : avatar.trim();
    }

    public Byte getCredit() {
        return credit;
    }

    public void setCredit(Byte credit) {
        this.credit = credit;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public BigDecimal getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(BigDecimal accountBalance) {
        this.accountBalance = accountBalance;
    }

    public BigDecimal getRedBalance() {
        return redBalance;
    }

    public void setRedBalance(BigDecimal redBalance) {
        this.redBalance = redBalance;
    }

    public Integer getCoupons() {
        return coupons;
    }

    public void setCoupons(Integer coupons) {
        this.coupons = coupons;
    }

    public Date getDates() {
        return dates;
    }

    public void setDates(Date dates) {
        this.dates = dates;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt == null ? null : salt.trim();
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", type=" + type +
                ", avatar='" + avatar + '\'' +
                ", credit=" + credit +
                ", status=" + status +
                ", accountBalance=" + accountBalance +
                ", redBalance=" + redBalance +
                ", coupons=" + coupons +
                ", dates=" + dates +
                '}';
    }
}