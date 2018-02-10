package com.zhd.pojo;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import java.io.Serializable;

/**
 * <p>
 * 调度任务表
 * </p>
 *
 * @author zyg
 * @since 2018-02-05
 */
public class Task implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 任务编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 任务名称
     */
    private String name;
    /**
     * 任务类型
     */
    private Integer type;
    /**
     * 任务处理人
     */
    @TableField("u_id")
    private String uId;
    /**
     * 任务状态
     */
    private Integer status;
    /**
     * 任务开始时间
     */
    @TableField("start_time")
    private Date startTime;
    /**
     * 任务结束时间
     */
    @TableField("end_time")
    private Date endTime;
    /**
     * 车辆编号
     */
    @TableField("b_id")
    private Integer bId;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getbId() {
        return bId;
    }

    public void setbId(Integer bId) {
        this.bId = bId;
    }

    @Override
    public String toString() {
        return "Task{" +
        ", id=" + id +
        ", name=" + name +
        ", type=" + type +
        ", uId=" + uId +
        ", status=" + status +
        ", startTime=" + startTime +
        ", endTime=" + endTime +
        ", bId=" + bId +
        "}";
    }
}
