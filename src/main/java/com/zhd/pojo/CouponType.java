package com.zhd.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class CouponType {
    private Integer id;

    private String name;

    private BigDecimal parValue;

    private BigDecimal conditionAmount;

    private Date startTime;

    private Date endTime;

    public Integer getId() {
        return id;
    }

    public CouponType setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public CouponType setName(String name) {
        this.name = name == null ? null : name.trim();
        return this;
    }

    public BigDecimal getParValue() {
        return parValue;
    }

    public CouponType setParValue(BigDecimal parValue) {
        this.parValue = parValue;
        return this;
    }

    public BigDecimal getConditionAmount() {
        return conditionAmount;
    }

    public CouponType setConditionAmount(BigDecimal conditionAmount) {
        this.conditionAmount = conditionAmount;
        return this;
    }

    public Date getStartTime() {
        return startTime;
    }

    public CouponType setStartTime(Date startTime) {
        this.startTime = startTime;
        return this;
    }

    public Date getEndTime() {
        return endTime;
    }

    public CouponType setEndTime(Date endTime) {
        this.endTime = endTime;
        return this;
    }
}