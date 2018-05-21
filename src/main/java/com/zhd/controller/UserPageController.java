package com.zhd.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 用户版功能页面控制器
 */
@Controller
@RequestMapping("userVersion")
public class UserPageController {

    /**
     * 前往--用户版--充值
     */
    @RequestMapping("recharge")
    public void toRecharge(){}

    /**
     * 前往--用户版--充值记录
     */
    @RequestMapping("recharges")
    public void toRecharges(){}

    /**
     * 前往--用户版--押金管理
     */
    @RequestMapping("deposit")
    public void toDeposit(){}

    /**
     * 前往--用户版--提交申请
     */
    @RequestMapping("apply")
    public void toApply(){}

    /**
     * 前往--用户版--购买包月
     */
    @RequestMapping("vip")
    public void toVip(){}


}
