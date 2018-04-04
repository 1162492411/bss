package com.zhd.convert;

import com.zhd.util.DataUtil;
import org.apache.commons.collections.CollectionUtils;

import java.time.LocalDate;
import java.util.*;

/**
 * 行程报表转换类
 */
public class JourneyReportConvert {
    private static final List<String> DEFAULT_HOURS_XAXIS = Arrays.asList("00","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23");
    private static final List<String> DEFAULT_RIDETIME_XAXIS = Arrays.asList("30分钟内","30-60分钟","60-90分钟","90-120分钟","120分钟以上","异常数据");

    /**
     * 将使用概况(按小时)转化
     * @param dataList 统计数据
     * @param cityNames 区划名称
     */
    public static Map<String, List> convertOverviewByHour(List<Map<String, Object>> dataList, List<String> cityNames){
        return commonConvertLine(dataList, DEFAULT_HOURS_XAXIS, DataUtil.generateZeroLongList(DEFAULT_HOURS_XAXIS.size()),cityNames);
    }

    /**
     *  将使用概况(按天)转化
     * @param dataList 统计数据
     * @param cityNames 区划名称
     * @param startDate 起始时间
     * @param endDate 终止时间
     */
    public static Map<String, List> convertOverviewByDay(List<Map<String, Object>> dataList, List<String> cityNames, LocalDate startDate, LocalDate endDate){
        List<String> xAxis = DataUtil.generateDays(startDate, endDate);
        return commonConvertLine(dataList, xAxis, DataUtil.generateZeroLongList(xAxis.size()),cityNames);
    }

    /**
     *  将使用概况(按月)转化
     * @param dataList 统计数据
     * @param cityNames 区划名称
     * @param startDate 起始时间
     * @param endDate 终止时间
     */
    public static Map<String, List> convertOverviewByMonth(List<Map<String, Object>> dataList, List<String> cityNames, LocalDate startDate, LocalDate endDate){
        List<String> xAxis = DataUtil.generateMonths(startDate, endDate);
        return commonConvertLine(dataList, xAxis, DataUtil.generateZeroLongList(xAxis.size()),cityNames);
    }

    /**
     * 将骑行时间统计情况转化
     * @param dataList 统计数据
     * @param cityNames 区划名称
     */
    public static Map<String,List> convertRideTime(List<Map<String,Object>> dataList, List<String> cityNames){
        Map<String,List> result = commonConvertLine(dataList, DEFAULT_RIDETIME_XAXIS, DataUtil.generateZeroLongList(DEFAULT_RIDETIME_XAXIS.size()), cityNames);
        return result;
    }

    /**
     * 将骑行距离统计情况转化
     * @param dataList 统计数据
     * @param cityNames 区划名称
     */
    public static Map<String,List> convertRideDistance(List<Map<String,Object>> dataList, List<String> cityNames){
        if(CollectionUtils.isNotEmpty(dataList)){
            List<Integer> resultX = new ArrayList<>();
            for (Map<String,Object> dataMap : dataList) {
                Integer countKey = Integer.valueOf(String.valueOf(dataMap.get("countKey")));
                if(! resultX.contains(countKey)){
                    resultX.add(countKey);
                }
            }
            Collections.sort(resultX, Comparator.comparingInt(o -> o));
            List<String> xAxis = new ArrayList<>(resultX.size());
            for (int i = 0; i < resultX.size(); i++) {
                xAxis.add(resultX.get(i).toString());
            }
            return commonConvertLine(dataList, xAxis, DataUtil.generateZeroLongList(xAxis.size()), cityNames);
        }else{
            return Collections.EMPTY_MAP;
        }
    }

    /**
     *  公共转化类,将统计数据转化为适合直线图的格式
     * @param dataList 统计数据
     * @param xAxis X轴数据
     * @param yAxis Y轴数据
     * @param cityNames 行政区划的名称
     * @return
     */
    public static Map<String, List> commonConvertLine(List<Map<String, Object>> dataList, List<String> xAxis, List<Long> yAxis, List<String> cityNames){
        Map<String, List> result = new HashMap<>(2);
        result.put("xAxis", xAxis);
        result.put("yAxis", yAxis);
        result.put("name", cityNames);
        if(CollectionUtils.isNotEmpty(dataList)){
            for (int i = 0; i < dataList.size(); i++) {
                Map<String,Object> dataMap = dataList.get(i);
                String key = String.valueOf(dataMap.get("countKey"));
                Long value = (Long) dataMap.get("countValue");
                List yAxisList = result.get("yAxis");
                int index = result.get("xAxis").indexOf(key);
                if(index != -1){
                    yAxisList.set(index, value);
                }
            }
            return result;
        }else{
            return result;
        }
    }

    /**
     *  公共转化类,将统计数据转化为适合饼图的格式
     * @param dataList 统计数据
     * @param cityNames 行政区划的名称
     * @return
     */
    //todo : 该方法未完成
    public static Map<String, List> commonConvertPie(List<Map<String, Object>> dataList, List<String> cityNames){
        Map<String, List> result = new HashMap<>(2);
        result.put("chartType", Arrays.asList("pie"));
        result.put("name", cityNames);
        if(CollectionUtils.isNotEmpty(dataList)){
            for (int i = 0; i < dataList.size(); i++) {
                Map<String,Object> dataMap = dataList.get(i);
                String key = String.valueOf(dataMap.get("countKey"));
                Long value = (Long) dataMap.get("countValue");
                List yAxisList = result.get("yAxis");
                int index = result.get("xAxis").indexOf(key);
                if(index != -1){
                    yAxisList.set(index, value);
                }
            }
            return result;
        }else{
            return result;
        }
    }


}
