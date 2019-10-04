package com.zhd.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 页面控制器，负责页面跳转
 */
@Controller
public class PageController {

    @RequestMapping("")
    public String defaultPage(){
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
     * 前往--车辆模块-添加区域
     */
    @RequestMapping("bicycleModule/addArea")
    public void toAddArea(){}

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
     * 前往--订单模块-充值记录
     */
    @RequestMapping("orderModule/recharges")
    public void toRecharges(){}

    /**
     * 前往--订单模块-押金记录
     */
    @RequestMapping("orderModule/deposits")
    public void toDeposits(){}

    /**
     * 前往--订单模块-包月记录
     */
    @RequestMapping("orderModule/vips")
    public void toVips(){}

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

    /**
     * 前往--任务模块-申请处理
     */
    @RequestMapping("taskModule/applies")
    public void toApplies(){}

    /**
     * 前往--报表模块-行程概况
     */
    @RequestMapping("reportModule/journeyOverview")
    public void toJourneyOverview(){}

    /**
     * 前往--报表模块-任务概况
     */
    @RequestMapping("reportModule/taskOverview")
    public void toTaskOverview(){}

    /**
     * 前往--用户管理-WS
     */
    @RequestMapping("userModule/ws")
    public void toUserModuleWs(){}
}
