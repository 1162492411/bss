package com.zhd.pojo;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import java.io.Serializable;

/**
 * <p>
 * 消费记录表
 * </p>
 *
 * @author zyg
 * @since 2018-02-05
 */
public class Consumption implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 消费记录编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 行程编号
     */
    @TableField("o_id")
    private Integer oId;
    /**
     * 用户编号
     */
    @TableField("u_id")
    private String uId;
    /**
     * 应付金额
     */
    @TableField("con_amount")
    private BigDecimal conAmount;
    /**
     * 实付金额
     */
    @TableField("pay_amount")
    private BigDecimal payAmount;
    /**
     * 使用的优惠券编号
     */
    @TableField("c_id")
    private Integer cId;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getoId() {
        return oId;
    }

    public void setoId(Integer oId) {
        this.oId = oId;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public BigDecimal getConAmount() {
        return conAmount;
    }

    public void setConAmount(BigDecimal conAmount) {
        this.conAmount = conAmount;
    }

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    public Integer getcId() {
        return cId;
    }

    public void setcId(Integer cId) {
        this.cId = cId;
    }

    @Override
    public String toString() {
        return "Consumption{" +
        ", id=" + id +
        ", oId=" + oId +
        ", uId=" + uId +
        ", conAmount=" + conAmount +
        ", payAmount=" + payAmount +
        ", cId=" + cId +
        "}";
    }
}
