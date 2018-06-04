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

    public static final String UNKNOWN_EXCEPTION = "未知异常!!!";

    public static final String NULL_USER_ID = "null";

    //user
    public static final String[] USER_KEYS = {"id","name","type","status","monthlyTime"};
    public static final String[] USER_NAMES = {" 编号","名称","类型","状态","包月截止期"};
    public static final String TIP_NOT_LOGIN = "用户未登录";
    public static final String TIP_HAS_EXIST = "用户已存在";
    public static final String TIP_NO_SUCH_USER = "不存在该用户";
    public static final String TIP_NO_PERMISSION = "权限不足";
    public static final String TIP_NO_ACCOUNT_BALANCE = "账户余额不足";
    public static final String TIP_NO_ENOUGH_CREDIT = "账户信用分不足";
    public static final String VALUE_USER_DEFAULT_AVATAR = "http://7xshpr.com1.z0.glb.clouddn.com/default_avatar.png";
    public static final Integer VALUE_USER_DEFAULT_CREDIT = 100;

    //journey
    public static final String[] JOURNEY_KEYS = {"id","userId","bicycleId","rideTime","distance","amount"};
    public static final String[] JOURNEY_NAMES = {"记录编号","用户编号","车辆编号","时间","距离","花费"};
    public static final String TIP_NO_SUCH_BICYCLE = "不存在该车辆";
    public static final String TIP_NOT_USEABLE_BICYCLE = "车辆不可用";
    public static final BigDecimal STANDARD_DEPOSIT = BigDecimal.valueOf(99);//标准版押金
    public static final String TIP_NO_ENOUGH_DEPOSIT = "押金不足,请缴纳押金!";
    public static final String TIP_RIDE_TIME_OUT = "骑行时间过长!";
    public static final String TIP_HAS_NO_END_JOURNEY = "存在未完成行程";

    //area
    public static final String TIP_BAN_AREA = "停留在了禁停区";
    public static final String[] AREA_NAMES = {"编号","区域名","类型","区域最北部","区域最南部","区域最西部","区域最东部"};
    public static final String[] AREA_KEYS = {"id", "name", "type", "northPoint", "southPoint", "westPoint", "eastPoint"};

    //supplier
    public static final String[] SUPPLIER_KEYS = {"id","name","brand","address"};
    public static final String[] SUPPLIER_NAMES = {"编号","名称","品牌","地址"};

    //bicycle
    public static final String[] BICYCLE_KEYS = {"id","status","type","serviceTime","investmentTime","mileage","batch","supplier"};
    public static final String[] BICYCLE_NAMES = {"编号","状态","类型","运行时间","投产时间","运行里程","生产批次","供应商"};
    public static final String TIP_BORROW_BICYCLE_ERROR = "借车失败";
    public static final String TIP_RETURN_BICYCLE_SUCCESS = "还车成功";
    public static final String TIP_RETURN_BICYCLE_ERROR = "还车失败";

    //task
    public static final String[] TASK_KEYS = {"id","name","user","bicycle","type","status","taskTime"};
    public static final String[] TASK_NAMES = {"任务编号","任务名","处理人","车辆","类型","状态","耗时"};

    //rechargeAccount
    public static final String[] RECHARGE_KEYS = {"id","userId","type","rechargeTime","amount"};
    public static final String[] RECHARGE_NAMES = {"记录编号","账户","渠道","时间","金额"};
    public static final String TIP_RECHARGE_ACCOUNT_ERROR = "充值账户失败";
    public static final String TIP_REFUND_ACCOUNT_SUCCESS = "提取余额成功";
    public static final String TIP_REFUND_ACCOUNT_ERROR = "提取余额失败";

    //deposit
    public static final String[] DEPOSIT_KEYS = {"id","userId","type","operateTime","amount"};
    public static final String[] DEPOSIT_NAMES = {"记录编号","账户","操作类型","时间","金额"};
    public static final String TIP_ENOUGH_DEPOSIT = "已缴纳过押金";
    public static final String TIP_NO_DEPOSIT = "未缴纳押金";
    public static final String TIP_RECHARGE_DEPOSIT_ERROR = "缴纳押金失败";
    public static final String TIP_REFUND_DEPOSIT_SUCCESS = "提取押金成功";
    public static final String TIP_REFUND_DEPOSIT_ERROR = "提取押金失败";

    //vip
    public static final String[] VIP_KEYS = {"id","userId","vipTime","operateTime","amount"};
    public static final String[] VIP_NAMES = {"记录编号","账户","月数","操作时间","金额"};
    public static final int VIP_COUNT = 15;//每月包月的金额

    //apply
    public static final String[] APPLY_KEYS = {"id","type","status","amount","userId","description","startTime","endTime","operatorId"};
    public static final String[] APPLY_NAMES = {"编号","类型","状态","金额","用户","描述","开始时间","结束时间","操作人"};
    public static final String TIP_UNKNOWN_APPLY_TYPE = "未知的申请类型";

    //ali
    public static final String TIP_ALI_ERROR = "连接阿里接口失败！请联系服务员";
    public static final String KEY_ALI_SERVICE_KEY = "d8c7b9bbe2d7d3811c75733a465ff91b";//阿里Web服务的key(行政区划查询，逆地理编码)
    public static final String KEY_AMAP_REGEO_RESPONSE_KEY = "regeocode";//高德逆地理编码返回信息中有效信息的key
    public static final int VALUE_DEFAULT_CITY_ID = 1;//默认的行政区划ID
    public static final double VALUE_DEFAULT_LOCATION_X = 116.40739440917969000;
    public static final double VALUE_DEFAULT_LOCATION_Y = 39.90420913696289000;

    //highcharts
    public static final String CHART_TYPE_LINE = "line";//直线图
    public static final String CHART_TYPE_COLUMN = "column";//柱状图
    public static final String CHART_TYPE_PIE = "pie";//饼图

}
