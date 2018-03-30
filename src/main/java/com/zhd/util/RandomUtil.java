package com.zhd.util;


import com.alibaba.fastjson.util.TypeUtils;
import org.apache.commons.lang3.RandomUtils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 随机数据工具类
 */
public class RandomUtil {
    public static final double TOO_MIN_DIFF = 0.0001;

    /**
     * 随机生成起始时间
     * @return
     */
    public static LocalDateTime generateRandomStartTime(){
        int randomYear = 2017;
        int randomMonth = RandomUtils.nextInt(6,9);
        int randomDay = RandomUtils.nextInt(1,30);
        int randomHour = RandomUtils.nextInt(7,22);
        int randomMinute  = RandomUtils.nextInt(1,60);
        int randomSecond = RandomUtils.nextInt(1,60);
        LocalDateTime localDateTime = LocalDateTime.of(randomYear,randomMonth,randomDay,randomHour,randomMinute, randomSecond);
        return localDateTime;
    }

    /**
     * 返回随机时间的时间戳的字符串格式
     * @return
     */
    public static String generateRandomStartTimeString(){
        return TypeUtils.castToString(generateRandomStartTime().toEpochSecond(ZoneOffset.ofHours(8)));
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
        double diffX = maxX - minX;
        double diffY = maxY - minY;
        StringBuffer stringBuffer = new StringBuffer();

        for (int i = 0; i < 5; i++) {
            if(diffX <= TOO_MIN_DIFF && diffY < TOO_MIN_DIFF){
                stringBuffer.append(RandomUtils.nextDouble(minX, minX + 0.01)).append(",");
                stringBuffer.append(RandomUtils.nextDouble(minY, minY + 0.01)).append(",");
            }
            else{
                stringBuffer.append(RandomUtils.nextDouble(minX, maxX)).append(",");
                stringBuffer.append(RandomUtils.nextDouble(minY, maxY)).append(",");
            }
        }
        return stringBuffer.toString().substring(0,stringBuffer.length() - 1);
    }


}
