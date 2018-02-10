package com.zhd.pojo;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 优惠券类型表
 * </p>
 *
 * @author zyg
 * @since 2018-02-05
 */
@TableName("coupon_type")
public class CouponType implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 优惠券类型编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 优惠券类型名称
     */
    private String name;
    /**
     * 抵扣金额
     */
    @TableField("par_value")
    private BigDecimal parValue;
    /**
     * 能够使用的最低金额
     */
    @TableField("condition_amount")
    private BigDecimal conditionAmount;
    /**
     * 生效期
     */
    @TableField("start_time")
    private Date startTime;
    /**
     * 失效期
     */
    @TableField("end_time")
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
        this.name = name;
    }

    public BigDecimal getParValue() {
        return parValue;
    }

    public void setParValue(BigDecimal parValue) {
        this.parValue = parValue;
    }

    public BigDecimal getConditionAmount() {
        return conditionAmount;
    }

    public void setConditionAmount(BigDecimal conditionAmount) {
        this.conditionAmount = conditionAmount;
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

    @Override
    public String toString() {
        return "CouponType{" +
        ", id=" + id +
        ", name=" + name +
        ", parValue=" + parValue +
        ", conditionAmount=" + conditionAmount +
        ", startTime=" + startTime +
        ", endTime=" + endTime +
        "}";
    }
}
