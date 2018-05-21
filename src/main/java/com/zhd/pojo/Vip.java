package com.zhd.pojo;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 包月记录表
 * </p>
 *
 * @author zyg
 * @since 2018-05-20
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Vip implements Serializable, BaseModel {
    private static final long serialVersionUID = 1L;

    /**
     * 包月记录编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 用户编号
     */
    @TableField("user_id")
    private String userId;
    /**
     * 购买包月数量
     */
    @NotNull(groups = {Insert.class})
    private Integer vipTime;
    /**
     * 操作时间
     */
    @TableField("operate_time")
    private String operateTime;
    /**
     * 操作金额
     */
    private BigDecimal amount;

}
