package com.zhd.pojo;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 登录记录表
 * </p>
 *
 * @author zyg
 * @since 2018-02-05
 */
@TableName("login_record")
public class LoginRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 记录编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 账户ID
     */
    @TableField("u_id")
    private String uId;
    /**
     * 登录时间
     */
    @TableField("login_time")
    private Date loginTime;
    /**
     * 登录IP
     */
    @TableField("login_IP")
    private String loginIp;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    @Override
    public String toString() {
        return "LoginRecord{" +
        ", id=" + id +
        ", uId=" + uId +
        ", loginTime=" + loginTime +
        ", loginIp=" + loginIp +
        "}";
    }
}
