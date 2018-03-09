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
    @TableField("user_id")
    @NotNull(groups = {Insert.class ,Update.class})
    private String userId;
    /**
     * 充值渠道
     */
    @NotNull(groups = {Insert.class, Update.class})
    private Integer type;
    /**
     * 充值时间
     */
    @TableField("recharge_time")
    private String rechargeTime;
    /**
     * 充值金额
     */
    @NotNull(groups = {Insert.class, Update.class})
    private BigDecimal amount;
    /**
     * 支付方订单编号
     */
    @TableField("pay_id")
    private String payId;
    /**
     * 支付结果
     */
    @TableField("pay_status")
    private Integer paStatus;

}
