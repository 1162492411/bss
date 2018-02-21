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
 * 押金记录表
 * </p>
 *
 * @author zyg
 * @since 2018-02-05
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Deposit implements Serializable, BaseModel{

    private static final long serialVersionUID = 1L;

    /**
     * 记录编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    @NotNull(groups = {Update.class, Delete.class})
    private Integer id;
    /**
     * 押金金额
     */
    @NotNull(groups = {Insert.class, Update.class})
    private BigDecimal amount;

    /**
     * 操作类型
     */
    @NotNull(groups = {Insert.class, Update.class})
    private Integer type;

    /**
     * 用户编号
     */
    @TableField("u_id")
    @NotNull(groups = {Insert.class, Update.class})
    private String uId;

    /**
     * 押金状态
     */
    @NotNull(groups = {Insert.class, Update.class})
    private Integer status;

    /**
     * 开始时间
     */
    @TableField("start_time")
    @NotNull(groups = {Insert.class, Update.class})
    private Date startTime;

    /**
     * 结束时间
     */
    @NotNull(groups = {Update.class})
    @TableField("end_time")
    private Date endTime;

}
