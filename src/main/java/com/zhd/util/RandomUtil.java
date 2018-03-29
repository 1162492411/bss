package com.zhd.util;


import org.apache.commons.lang3.RandomUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 随机数据工具类
 */
public class RandomUtil {

    /**
     * 随机生成起始时间
     * @return
     */
    public static LocalDateTime generateRandomStartTime(){
        int randomYear = 2017;
        int randomMonth = RandomUtils.nextInt(1,12);
        int randomDay = RandomUtils.nextInt(1,27);
        int randomHour = RandomUtils.nextInt(7,22);
        int randomMinute  = RandomUtils.nextInt(1,60);
        int randomSecond = RandomUtils.nextInt(1,60);
        LocalDateTime localDateTime = LocalDateTime.of(randomYear,randomMonth,randomDay,randomHour,randomMinute, randomSecond);
        return localDateTime;
    }

    /**
     * 随机生成路径
     * @return
     */
    public static String generateRandomPath(double startPointX, double startPointY, double endPointX, double endPointY){
        double minX = Math.min(startPointX, endPointX);
        double maxX = Math.max(startPointX, endPointX);
        double minY = Math.min(startPointY, endPointY);
        double maxY = Math.max(startPointY, endPointY);
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < 5; i++) {
            stringBuffer.append(RandomUtils.nextDouble(minX, maxX)).append(",");
            stringBuffer.append(RandomUtils.nextDouble(minY, maxY)).append(",");
        }
        return stringBuffer.toString().substring(0,stringBuffer.length() - 1);
    }


}
