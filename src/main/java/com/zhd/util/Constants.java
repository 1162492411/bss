package com.zhd.util;

import java.math.BigDecimal;

/**
 * 常量类，存储常量
 */
public class Constants {

    //通用信息
    public static final String TIP_EMPTY_DATA = "数据为空";

    public static final String UNKNOWN_DATA = "未知";

    public static final String ILLEGAL_ARGUMENTS = "参数错误";

    public static final String DB_EXCEPTION = "数据库出现异常";

    public static final String UNKNOWN_EXCEPTION = "未知异常";

    //user
    public static final String[] USER_KEYS = {"id","name","type","status"};
    public static final String[] USER_NAMES = {" 编号","名称","类型","状态"};
    public static final String TIP_NOT_LOGIN = "用户未登录";
    public static final String TIP_NO_SUCH_USER = "不存在该用户";
    public static final String TIP_NO_PERMISSION = "权限不足";

    //journey
    public static final String[] JOURNEY_KEYS = {"id","uId","bId","rideTime","distance","amount"};
    public static final String[] JOURNEY_NAMES = {"记录编号","用户编号","车辆编号","时间","距离","话费"};
    public static final String TIP_NO_SUCH_BICYCLE = "不存在该车辆";
    public static final String TIP_NOT_USEABLE_BICYCLE = "车辆不可用";
    public static final BigDecimal STANDARD_DEPOSIT = BigDecimal.valueOf(99);//标准版押金
    public static final String TIP_NO_ENOUGH_DEPOSIT = "押金不足,请缴纳押金!";
    public static final String TIP_RIDE_TIME_OUT = "骑行时间过长!";

    //area
    public static final String TIP_BAN_AREA = "停留在了禁停区";
    public static final String[] AREA_NAMES = {"编号","区域名","类型","区域最北部","区域最南部","区域最西部","区域最东部"};
    public static final String[] AREA_KEYS = {"id", "name", "type", "northPoint", "southPoint", "westPoint", "eastPoint"};

    //supplier
    public static final String[] SUPPLIER_KEYS = {"id","name","address"};
    public static final String[] SUPPLIER_NAMES = {"编号","名称","地址"};

    //bicycle
    public static final String[] BICYCLE_KEYS = {"id","status","type","serviceTime","investmentTime","mileage","batch","supplier"};
    public static final String[] BICYCLE_NAMES = {"编号","状态","类型","运行时间","投产时间","运行里程","生产批次","供应商"};

    //task
    public static final String[] TASK_KEYS = {"id","name","user","bicycle","type","status"};
    public static final String[] TASK_NAMES = {"任务编号","任务名","处理人","车辆","类型","状态"};

    //rechargeAccount
    public static final String[] RECHARGE_KEYS = {"id","userId","type","rechargeTime","amount","payId","payStatus"};
    public static final String[] RECHARGE_NAMES = {"记录编号","账户","渠道","时间","金额","订单号","充值结果"};

    //deposit
    public static final String TIP_ENOUGH_DEPOSIT = "已缴纳过押金";
    public static final String TIP_NO_DEPOSIT = "未缴纳押金";

}
