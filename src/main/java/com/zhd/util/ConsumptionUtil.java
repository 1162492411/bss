package com.zhd.util;

import com.zhd.enums.AreaTypeEnum;
import com.zhd.exceptions.BanAreaException;
import com.zhd.exceptions.RideTimeOutException;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * 计费工具类
 * Created by Mo on 2018/2/19.
 */
public class ConsumptionUtil {

    /**
     *
     * @param rideTime 骑行时间
     * @param areaType 区域类型
     * @param monthlyTime 包月截止时间
     * @return
     */
    public static BigDecimal calculate(String rideTime, int areaType, String monthlyTime) throws RideTimeOutException, BanAreaException {
        long time = Long.parseLong(rideTime);
        if(time < 0){
            throw new IllegalArgumentException(Constants.ILLEGAL_ARGUMENTS);
        }
//        int minutes =  Math.round(time / 1000 / 60);//format rideTime
        int minutes =  Math.round(time / 1000 / 60);//format rideTime
        double originalAmount = calcuteByRideTime(minutes);

        if(StringUtils.isNotBlank(monthlyTime) && Long.parseLong(monthlyTime) > System.currentTimeMillis() && minutes <= 120) {
            System.out.println("zero");
            return BigDecimal.ZERO;
        }

        if(areaType == -1){
            originalAmount += 1;
        }
        else if(areaType == AreaTypeEnum.BAN.getCode() || areaType == AreaTypeEnum.UNKNOWN.getCode()){
            System.out.println("ban area");
            throw new BanAreaException();
        }
        else if(areaType == AreaTypeEnum.RED.getCode()) {
            System.out.println("red area");
            return BigDecimal.ZERO;
        }
        System.out.println("originalAmount-->" + originalAmount);
        return BigDecimal.valueOf(originalAmount);
    }

    /**
     * 根据骑行时间计算应付金额
     * @param minutes 骑行分钟数
     * 计算规则 : 1小时内均收费1元，1-2小时部分每半小时收费1元，超出2小时部分每小时收费4元，超出24小时则抛出RideTimeOutException
     * @return 应付金额
     */
    private static double calcuteByRideTime(int minutes) throws RideTimeOutException {
        if(minutes >= 0 && minutes <= 60) return 1;
        else if(minutes > 60 && minutes <= 90) return 1.5;
        else if(minutes > 90 && minutes <= 120) return 2;
        else if(minutes > 120 && minutes <= 1440) return 2 + 4 * Math.round(minutes - 120);
        else throw new RideTimeOutException();
    }

}
