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

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }

    public LocalDateTime getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(LocalDateTime loginTime) {
        this.loginTime = loginTime;
    }

    public String getLoginIP() {
        return loginIP;
    }

    public void setLoginIP(String loginIP) {
        this.loginIP = loginIP == null ? null : loginIP.trim();
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