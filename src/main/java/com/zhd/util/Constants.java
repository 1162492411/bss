package com.zhd.util;

import java.math.BigDecimal;

/**
 * 常量类，存储常量
 */
public class Constants {

    public static final String TIP_EMPTY_DATA = "数据为空";

    public static final String UNKNOWN_DATA = "未知";

    public static final String ILLEGAL_ARGUMENTS = "参数错误";

    public static final String UNKNOWN_EXCEPTION = "未知异常";

    public static final String TIP_NOT_LOGIN = "用户未登录";

    public static final String TIP_NO_SUCH_USER = "不存在该用户";

    public static final String TIP_NO_SUCH_BICYCLE = "不存在该车辆";

    public static final String TIP_NOT_USEABLE_BICYCLE = "车辆不可用";

    public static final BigDecimal STANDARD_DEPOSIT = BigDecimal.valueOf(99);//标准版押金

    public static final String TIP_NO_ENOUGH_DEPOSIT = "押金不足,请缴纳押金!";

    public static final String TIP_RIDE_TIME_OUT = "骑行时间过长!";

    public static final String TIP_BAN_AREA = "停留在了禁停区";

}
