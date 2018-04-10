package com.zhd.pojo;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 用户申请
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Apply implements Serializable, BaseModel {
    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 申请类型
     */
    private Integer type;

    /**
     * 申请状态
     */
    private Integer status;

    /**
     * 申请操作对象
     */
    private String object;

    /**
     * 申请人
     */
    @TableField("user_id")
    private Integer userId;
    /**
     * 申请描述
     */
    private String description;

    /**
     * 申请提交时间
     */
    @TableField("start_time")
    private String startTime;

    /**
     * 申请完成时间
     */
    @TableField("end_time")
    private String endTime;

    /**
     * 申请处理人
     */
    @TableField("operator_id")
    private String operatorId;

}
