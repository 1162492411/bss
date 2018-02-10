package com.zhd.pojo;

import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.DataSource;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.databind.ser.Serializers;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aspectj.lang.annotation.DeclareAnnotation;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author zyg
 * @since 2018-02-05
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable,BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 用户编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    @NotNull(groups = {Update.class, Delete.class})
    private String id;
    /**
     * 昵称
     */
    @NotBlank(groups = {Insert.class, Update.class})
    private String name;
    /**
     * 密码
     */
    @NotBlank(groups = {Insert.class, Update.class})
    transient private String password;
    /**
     * 账户类型
     */
    @Range(min = 0, max = 3, groups = {Insert.class, Update.class})
    private Byte type;
    /**
     * 头像
     */
    @NotBlank(groups = Update.class)
    private String avatar;
    /**
     * 信用分
     */
    @Range(min = 0, groups = {Insert.class, Update.class})
    private Integer credit;
    /**
     * 状态
     */
    @NotNull(groups = {Insert.class, Update.class})
    private Integer status;
    /**
     * 账户余额
     */
    @Range(min = 0, groups = {Insert.class, Update.class})
    @TableField("account_balance")
    private BigDecimal accountBalance;
    /**
     * 红包余额
     */
    @Range( min = 0, groups = {Insert.class, Update.class})
    @TableField("red_balance")
    private BigDecimal redBalance;
    /**
     * 优惠券数量
     */
    @Range(min = 0, groups = {Insert.class, Update.class})
    private Integer coupons;
    /**
     * 包月截止时间
     */
    @NotNull(groups = {Insert.class, Update.class})
    private Date dates;
}
