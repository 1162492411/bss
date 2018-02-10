package com.zhd.pojo;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 优惠券信息表
 * </p>
 *
 * @author zyg
 * @since 2018-02-05
 */
@TableName("coupon_info")
public class CouponInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 记录编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 优惠券类型编号
     */
    @TableField("c_d")
    private Integer cD;
    /**
     * 用户编号
     */
    @TableField("u_id")
    private String uId;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getcD() {
        return cD;
    }

    public void setcD(Integer cD) {
        this.cD = cD;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    @Override
    public String toString() {
        return "CouponInfo{" +
        ", id=" + id +
        ", cD=" + cD +
        ", uId=" + uId +
        "}";
    }
}
