package com.zhd.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 页面控制器 : 控制页面的跳转
 * Created by Mo on 2017/10/16.
 */
@Controller
@RequestMapping("")
public class PageController {

    /**
     * 默认--登录界面
     * @return 登录页面
     */
    @RequestMapping
    public String toLogin(){
        return "login";
    }

    /**
     * 前往--管理平台主页
     */
    @RequestMapping("home")
    public void toHome() {}

    /**
     * 前往--用户模块-用户管理
     */
    @RequestMapping("userModule/users")
    public void toUsers(){}

    /**
     * 前往--车辆模块-区域管理
     */
    @RequestMapping("bicycleModule/areas")
    public void toAreas(){}

    /**
     * 前往--车辆模块-供应商管理
     */
    @RequestMapping("bicycleModule/suppliers")
    public void toSuppliers(){}

    /**
     * 前往--车辆模块-车辆管理
     */
    @RequestMapping("bicycleModule/bicycles")
    public void toBicycles(){}
}
