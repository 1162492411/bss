package com.zhd.pojo;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import java.io.Serializable;

/**
 * <p>
 * 行程记录表
 * </p>
 *
 * @author zyg
 * @since 2018-02-05
 */
public class Journey implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 记录编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 用户ID
     */
    @TableField("u_id")
    private String uId;
    /**
     * 车辆ID
     */
    @TableField("b_id")
    private Integer bId;
    /**
     * 起始时间
     */
    @TableField("start_time")
    private Date startTime;
    /**
     * 骑行时间
     */
    @TableField("ride_time")
    private Integer rideTime;
    /**
     * 骑行距离
     */
    private Integer distance;
    /**
     * 骑行花费
     */
    private BigDecimal amount;
    @TableField("end_time")
    private Date endTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public Integer getbId() {
        return bId;
    }

    public void setbId(Integer bId) {
        this.bId = bId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Integer getRideTime() {
        return rideTime;
    }

    public void setRideTime(Integer rideTime) {
        this.rideTime = rideTime;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "Journey{" +
        ", id=" + id +
        ", uId=" + uId +
        ", bId=" + bId +
        ", startTime=" + startTime +
        ", rideTime=" + rideTime +
        ", distance=" + distance +
        ", amount=" + amount +
        ", endTime=" + endTime +
        "}";
    }
}
