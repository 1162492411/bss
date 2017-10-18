package com.zhd.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class User {
    private static Byte DEFAULT_TYPE = 0;
    private static String DEFAULT_AVATAR = "http://7xshpr.com1.z0.glb.clouddn.com/default_avatar.png";
    private static Byte DEFAULT_CREDIT = 100;

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

    public User setId(String id) {
        this.id = id == null ? null : id.trim();
        return this;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name == null ? null : name.trim();
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password == null ? null : password.trim();
        return this;
    }

    public Byte getType() {
        return type;
    }

    public User setType(Byte type) {
        this.type = type;
        return this;
    }

    public String getAvatar() {
        return avatar;
    }

    public User setAvatar(String avatar) {
        this.avatar = avatar == null ? null : avatar.trim();
        return this;
    }

    public Byte getCredit() {
        return credit;
    }

    public User setCredit(Byte credit) {
        this.credit = credit;
        return this;
    }

    public Boolean getStatus() {
        return status;
    }

    public User setStatus(Boolean status) {
        this.status = status;
        return this;
    }

    public BigDecimal getAccountBalance() {
        return accountBalance;
    }

    public User setAccountBalance(BigDecimal accountBalance) {
        this.accountBalance = accountBalance;
        return this;
    }

    public BigDecimal getRedBalance() {
        return redBalance;
    }

    public User setRedBalance(BigDecimal redBalance) {
        this.redBalance = redBalance;
        return this;
    }

    public Integer getCoupons() {
        return coupons;
    }

    public User setCoupons(Integer coupons) {
        this.coupons = coupons;
        return this;
    }

    public Date getDates() {
        return dates;
    }

    public User setDates(Date dates) {
        this.dates = dates;
        return this;
    }

    public String getSalt() {
        return salt;
    }

    public User setSalt(String salt) {
        this.salt = salt == null ? null : salt.trim();
        return this;
    }

    public User defaultAddUser(){
        this.type = DEFAULT_TYPE;
        this.avatar = DEFAULT_AVATAR;
        this.credit = DEFAULT_CREDIT;
        this.password = this.getId().substring(this.getId().length()-6);
        return this;
    }

    public String[] getKeys(){
        return new String[]{"id", "name", "credit", "status", "accountBalance", "redBalance", "coupons", "dates"};
    }

    public String[] getNames(){
        return new String[]{"编号","昵称","信用分","状态","账户余额","红包余额","优惠券","包月时间"};
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