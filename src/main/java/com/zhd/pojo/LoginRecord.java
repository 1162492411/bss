package com.zhd.pojo;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class LoginRecord {
    private Integer id;

    private String uid;

    private LocalDateTime loginTime;

    private String loginIP;

    public Integer getId() {
        return id;
    }

    public LoginRecord setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getUid() {
        return uid;
    }

    public LoginRecord setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
        return this;
    }

    public LocalDateTime getLoginTime() {
        return loginTime;
    }

    public LoginRecord setLoginTime(LocalDateTime loginTime) {
        this.loginTime = loginTime;
        return this;
    }

    public String getLoginIP() {
        return loginIP;
    }

    public LoginRecord setLoginIP(String loginIP) {
        this.loginIP = loginIP == null ? null : loginIP.trim();
        return this;
    }

    public LoginRecord() {
    }

    public LoginRecord(String uid, LocalDateTime loginTime, String loginIP) {
        this.uid = uid;
        this.loginTime = loginTime;
        this.loginIP = loginIP;
    }

    @Override
    public String toString() {
        return "LoginRecord{" +
                "id=" + id +
                ", uid='" + uid + '\'' +
                ", loginTime=" + loginTime +
                ", loginIP='" + loginIP + '\'' +
                '}';
    }
}