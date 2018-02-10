package com.zhd.pojo;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import java.io.Serializable;

/**
 * <p>
 * 充值记录表
 * </p>
 *
 * @author zyg
 * @since 2018-02-05
 */
public class Recharge implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 充值记录编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 用户编号
     */
    @TableField("u_id")
    private String uId;
    /**
     * 充值类型
     */
    private Integer type;
    /**
     * 充值时间
     */
    @TableField("recharge_time")
    private Date rechargeTime;
    /**
     * 充值金额
     */
    private BigDecimal amount;
    /**
     * 支付方订单编号
     */
    @TableField("p_id")
    private String pId;
    /**
     * 支付方回调URL
     */
    @TableField("p_url")
    private String pUrl;
    /**
     * 支付结果
     */
    @TableField("p_status")
    private Integer pStatus;


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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getRechargeTime() {
        return rechargeTime;
    }

    public void setRechargeTime(Date rechargeTime) {
        this.rechargeTime = rechargeTime;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getpUrl() {
        return pUrl;
    }

    public void setpUrl(String pUrl) {
        this.pUrl = pUrl;
    }

    public Integer getpStatus() {
        return pStatus;
    }

    public void setpStatus(Integer pStatus) {
        this.pStatus = pStatus;
    }

    @Override
    public String toString() {
        return "Recharge{" +
        ", id=" + id +
        ", uId=" + uId +
        ", type=" + type +
        ", rechargeTime=" + rechargeTime +
        ", amount=" + amount +
        ", pId=" + pId +
        ", pUrl=" + pUrl +
        ", pStatus=" + pStatus +
        "}";
    }
}
