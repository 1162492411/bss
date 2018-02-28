package com.zhd.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 页面控制器，负责页面跳转
 */
@Controller
public class PageController {

    @RequestMapping("")
    public String Default(){
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

    /**
     * 前往--订单模块-行程管理
     */
    @RequestMapping("orderModule/journeys")
    public void toJourneys(){}


    /**
     * 前往--任务模块-车辆分布
     */
    @RequestMapping("taskModule/map")
    public void toMap(){}

    /**
     * 前往--任务模块-任务管理
     */
    @RequestMapping("taskModule/tasks")
    public void toTasks(){}
}
