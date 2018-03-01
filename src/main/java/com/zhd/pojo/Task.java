package com.zhd.pojo;

import com.baomidou.mybatisplus.enums.IdType;

import java.util.ArrayList;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashMap;
import java.util.TreeMap;

/**
 * <p>
 * 调度任务表
 * </p>
 *
 * @author zyg
 * @since 2018-02-05
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Task implements Serializable,BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 任务编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    @NotNull(groups = {Update.class, Delete.class})
    private Integer id;
    /**
     * 任务名称
     */
    private String name;
    /**
     * 任务类型
     */
    @NotNull(groups = {Insert.class, Update.class})
    private Integer type;
    /**
     * 任务处理人
     */
    private String user;
    /**
     * 任务状态
     */
    private Integer status;

    /**
     * 任务开始时间
     */
    @TableField("start_time")
    private String startTime;

    /**
     * 任务结束时间
     */
    @TableField("end_time")
    private String endTime;

    /**
     * 车辆编号
     */
    @NotNull(groups = {Insert.class, Update.class})
    private Integer bicycle;

}
