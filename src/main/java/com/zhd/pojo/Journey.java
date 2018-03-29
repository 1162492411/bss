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
 * 行程记录表
 * </p>
 *
 * @author zyg
 * @since 2018-02-05
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Journey implements Serializable, BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 记录编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    @NotNull(groups = {Delete.class})
    private Integer id;
    /**
     * 用户ID
     */
    @TableField("user_id")
    @NotNull(groups = {Insert.class})
    private String userId;
    /**
     * 车辆ID
     */
    @TableField("bicycle_id")
    @NotNull(groups = {Insert.class})
    private Integer bicycleId;
    /**
     * 开始时间
     */
    @TableField("start_time")
    @NotNull(groups = {Insert.class})
    private String startTime;
    /**
     * 结束时间
     */
    @TableField("end_time")
    @NotNull(groups = {Update.class})
    private String endTime;
    /**
     * 骑行时间
     */
    @TableField("ride_time")
    private String rideTime;
    /**
     * 骑行距离
     */
    private Integer distance;
    /**
     * 骑行花费
     */

    private BigDecimal amount;

    /**
     * 起始位置X
     */
    @TableField("start_location_x")
    private BigDecimal startLocationX;

    /**
     * 起始位置Y
     */
    @TableField("start_location_y")
    private BigDecimal startLocationY;

    /**
     * 终止位置X
     */
    @NotNull(groups = Update.class)
    @TableField("end_location_x")
    private BigDecimal endLocationX;

    /**
     * 终止位置Y
     */
    @NotNull(groups = Update.class)
    @TableField("end_location_y")
    private BigDecimal endLocationY;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 轨迹
     */
    private String path;


}
