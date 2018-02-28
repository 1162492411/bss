package com.zhd.pojo;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.zhd.enums.BicycleStatusEnum;
import com.zhd.enums.BicycleTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>
 * 车辆表
 * </p>
 *
 * @author zyg
 * @since 2018-02-05
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Bicycle implements Serializable, BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 车辆编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    @NotNull(groups = {Update.class, Delete.class})
    private Integer id;
    /**
     * 车辆状态
     */
    @NotNull(groups = {Insert.class,Update.class})
    private Integer status;
    /**
     * 车辆类型
     */
    @NotNull(groups = {Insert.class,Update.class})
    private Integer type;
    /**
     * 当前位置X坐标
     */
    @TableField("location_x")
    @NotNull(groups = {Insert.class, Update.class})
    private BigDecimal locationX;
    /**
     * 当前位置Y坐标
     */
    @TableField("location_y")
    @NotNull(groups = {Insert.class,Update.class})
    private BigDecimal locationY;
    /**
     * 生产批次
     */
    @NotBlank(groups = {Insert.class, Update.class})
    private String batch;
    /**
     * 供应商
     */
    @NotNull(groups = {Insert.class, Update.class})
    private Integer supplier;
    /**
     * 总使用时间
     */
    @TableField("service_time")
    @NotNull(groups = Update.class)
    private String serviceTime;
    /**
     * 投产时间
     */
    @TableField("investment_time")
    private String investmentTime;
    /**
     * 行驶里程
     */
    @NotNull(groups = Update.class)
    private Integer mileage;
}
