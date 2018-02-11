package com.zhd.pojo;

import com.baomidou.mybatisplus.enums.IdType;
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
    @NotBlank(groups = {Insert.class, Update.class})
    private String name;
    /**
     * 任务类型
     */
    @NotNull(groups = {Insert.class, Update.class})
    private Integer type;
    /**
     * 任务处理人
     */
    @TableField("u_id")
    @NotBlank(groups = {Insert.class, Update.class})
    private String uId;
    /**
     * 任务状态
     */
    @NotNull(groups = {Update.class})
    private Integer status;
    /**
     * 任务开始时间
     */
    @TableField("start_time")
    @NotNull(groups = {Insert.class})
    private Date startTime;
    /**
     * 任务结束时间
     */
    @TableField("end_time")
    @NotNull(groups = {Update.class})
    private Date endTime;
    /**
     * 车辆编号
     */
    @TableField("b_id")
    @NotNull(groups = {Insert.class, Update.class})
    private Integer bId;

}
