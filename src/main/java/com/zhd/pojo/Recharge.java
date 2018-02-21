package com.zhd.pojo;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>
 * 充值记录表
 * </p>
 *
 * @author zyg
 * @since 2018-02-05
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Recharge implements Serializable, BaseModel{

    private static final long serialVersionUID = 1L;

    /**
     * 充值记录编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    @NotNull(groups = {Update.class, Delete.class})
    private Integer id;
    /**
     * 用户编号
     */
    @TableField("u_id")
    @NotNull(groups = {Insert.class ,Update.class})
    private String uId;
    /**
     * 充值类型
     */
    @NotNull(groups = {Insert.class, Update.class})
    private Integer type;
    /**
     * 充值时间
     */
    @TableField("recharge_time")
    @NotNull(groups = {Insert.class, Update.class})
    private Date rechargeTime;
    /**
     * 充值金额
     */
    @NotNull(groups = {Insert.class, Update.class})
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

}
