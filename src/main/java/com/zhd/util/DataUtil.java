package com.zhd.util;


import com.alibaba.fastjson.util.TypeUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.RandomUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 随机数据工具类
 */
public class DataUtil {
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

    /**
     * 生成值为0的List<Integer>
     * @param size list的长度
     * @return
     */
    public static List<Integer> generateZeroIntegerList(int size){
        if(size <= 0) {
            return Collections.EMPTY_LIST;
        }else{
            List<Integer> result = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                result.add(i,0);
            }
            return result;
        }
    }

    /**
     * 生成值为0L的List<Long>
     * @param size list的长度
     * @return
     */
    public static List<Long> generateZeroLongList(int size){
        if(size <= 0) {
            return Collections.EMPTY_LIST;
        }else{
            List<Long> result = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                result.add(i,0L);
            }
            return result;
        }
    }

    /**
     * 生成List<List<Long>>
     * @param counts 内部List的个数
     * @param size list的长度
     * @return
     */
    public static List<List<BigDecimal>> generateZeroBigDecimalListList(int counts, int size){
        if(size <= 0) {
            return Collections.EMPTY_LIST;
        }else{
            List<List<BigDecimal>> resultList = new ArrayList<>(counts);
            for (int i = 0; i < counts; i++) {
                List<BigDecimal> currentList = new ArrayList<>(size);
                for (int j = 0; j < size; j++) {
                    currentList.add(j,BigDecimal.ZERO);
                }
                resultList.add(currentList);
            }
            return resultList;
        }
    }

    /**
     * 生成两个日期之间的所有日期的字符串形式
     * @param startDate 起始日期
     * @param endDate 终止日期
     */
    public static List<String> generateDays(LocalDate startDate, LocalDate endDate){
        if(startDate == null || endDate == null || startDate.isAfter(endDate)){
            return Collections.EMPTY_LIST;
        }
        int days = (int)(endDate.toEpochDay() - startDate.toEpochDay());
        List<String> result = new ArrayList<>(days);
        for (int i = 0; i <= days; i++) {
            LocalDate tempDay = startDate.plusDays(i);
            result.add(tempDay.toString());
        }
        return result;
    }

    /**
     * 生成两个日期之间的日期的月份的字符串形式
     * @param startDate 起始日期
     * @param endDate 终止日期
     */
    public static List<String> generateMonths(LocalDate startDate, LocalDate endDate){
        List<String> days = generateDays(startDate, endDate);
        if(CollectionUtils.isNotEmpty(days)){
            Set<String> months = new HashSet<>();
            for (String day : days) {
                months.add(day.substring(0,day.length() - 3));
            }
            return new ArrayList<>(months);
        }
        else{
            return Collections.emptyList();
        }
    }

}
