package com.zhd.util;


import org.apache.commons.lang3.RandomUtils;

import java.time.LocalDateTime;

/**
 * 测试数据工具类
 */
public class TestUtil {

    /**
     * 随机生成起始时间
     * @return
     */
    public static LocalDateTime generateRandomStartTime(){
        int randomYear = RandomUtils.nextInt(2010,2017);
        int randomMonth = RandomUtils.nextInt(1,12);
        int randomDay = RandomUtils.nextInt(1,31);
        int randomHour = RandomUtils.nextInt(7,22);
        int randomMinute  = RandomUtils.nextInt(1,60);
        int randomSecond = RandomUtils.nextInt(1,60);
        LocalDateTime localDateTime = LocalDateTime.of(randomYear,randomMonth,randomDay,randomHour,randomMinute, randomSecond);
        return localDateTime;
    }


}
