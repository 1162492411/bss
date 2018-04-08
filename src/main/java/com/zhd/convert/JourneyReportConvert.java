package com.zhd.convert;

import com.zhd.pojo.Serie;
import com.zhd.util.DataUtil;
import org.apache.commons.collections.CollectionUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

/**
 * 行程报表转换类
 */
public class JourneyReportConvert {
    private static final List<String> DEFAULT_HOURS_XAXIS = Arrays.asList("00","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23");
    private static final List<String> DEFAULT_RIDETIME_XAXIS = Arrays.asList("30分钟内","30-60分钟","60-90分钟","90-120分钟","120分钟以上","异常数据");
    private static final List<String> FLOW_KEYS = Arrays.asList("countIn","countInner","countOut");
    private static final List<String> FLOW_NAMES = Arrays.asList("流入","区域内流动","流出");


    /**
     * 将使用概况(按小时)转化
     * @param dataList 统计数据
     * @param cityNames 区划名称
     * @param chartType 图表类型
     */
    public static Map<String, List> convertOverviewByHour(List<Map<String, Object>> dataList, List<String> cityNames, String chartType){
        switch(chartType){
            case "pie" : return commonConvertPie(dataList,cityNames);
            default : return commonConvertLineSingleValue(dataList, DEFAULT_HOURS_XAXIS, DataUtil.generateZeroLongList(DEFAULT_HOURS_XAXIS.size()),cityNames);
        }
    }

    /**
     *  将使用概况(按天)转化
     * @param dataList 统计数据
     * @param cityNames 区划名称
     * @param startDate 起始时间
     * @param endDate 终止时间
     */
    public static Map<String, List> convertOverviewByDay(List<Map<String, Object>> dataList, List<String> cityNames, LocalDate startDate, LocalDate endDate, String chartType){
        switch(chartType){
            case "pie" : return commonConvertPie(dataList,cityNames);
            default : List<String> xAxis = DataUtil.generateDays(startDate, endDate);
                return commonConvertLineSingleValue(dataList, xAxis, DataUtil.generateZeroLongList(xAxis.size()),cityNames);
        }
    }

    /**
     *  将使用概况(按月)转化
     * @param dataList 统计数据
     * @param cityNames 区划名称
     * @param startDate 起始时间
     * @param endDate 终止时间
     */
    public static Map<String, List> convertOverviewByMonth(List<Map<String, Object>> dataList, List<String> cityNames, LocalDate startDate, LocalDate endDate, String chartType){
        switch(chartType){
            case "pie" : return commonConvertPie(dataList, cityNames);
            default :
                List<String> xAxis = DataUtil.generateMonths(startDate, endDate);
                return commonConvertLineSingleValue(dataList, xAxis, DataUtil.generateZeroLongList(xAxis.size()),cityNames);
        }
    }


    /**
     * 将骑行时间统计情况转化
     * @param dataList 统计数据
     * @param cityNames 区划名称
     */
    public static Map<String,List> convertRideTime(List<Map<String,Object>> dataList, List<String> cityNames, String chartType){
        switch(chartType){
            case "pie" : return commonConvertPie(dataList, cityNames);
            default : return commonConvertLineSingleValue(dataList, DEFAULT_RIDETIME_XAXIS, DataUtil.generateZeroLongList(DEFAULT_RIDETIME_XAXIS.size()), cityNames);
        }
    }

    /**
     * 将骑行距离统计情况转化
     * @param dataList 统计数据
     * @param cityNames 区划名称
     */
    public static Map<String,List> convertRideDistance(List<Map<String,Object>> dataList, List<String> cityNames, String chartType){
        switch(chartType){
            case "pie" : return commonConvertPie(dataList, cityNames);
            default:
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
                return commonConvertLineSingleValue(dataList, xAxis, DataUtil.generateZeroLongList(xAxis.size()), cityNames);
        }
    }


    //todo : 待扩展为按时/天/月统计
    public static Map<String, Object> convertFlow(List<Map<String,Object>> dataList, List<String> cityNames, String chartType){
        switch(chartType){
            default :
                List<String> resultX = new ArrayList<>();
                for (Map<String,Object> dataMap : dataList) {
                    String countKey = String.valueOf(dataMap.get("countKey"));
                    if(! resultX.contains(countKey)){
                        resultX.add(countKey);
                    }
                }
                return commonConvertColumnMultiValue(dataList,resultX, DataUtil.generateZeroBigDecimalListList(FLOW_KEYS.size(),resultX.size()),cityNames, FLOW_KEYS, FLOW_NAMES);
        }
    }

    /**
     *  公共转化类,将统计数据转化为适合直线图的单值的格式
     * @param dataList 统计数据
     * @param xAxis X轴数据
     * @param yAxis Y轴数据
     * @param cityNames 行政区划的名称
     * @return
     */
    public static Map<String, List> commonConvertLineSingleValue(List<Map<String, Object>> dataList, List<String> xAxis, List<Long> yAxis, List<String> cityNames){
        Map<String, List> result = new HashMap<>(2);
        result.put("xAxis", xAxis);
        result.put("yAxis", yAxis);
        result.put("name", cityNames);
        result.put("chartType", Arrays.asList("line"));
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
     *  公共转化类,将统计数据转化为适合直线图的多值的格式
     * @param dataList 统计数据
     * @param xAxis X轴数据
     * @param yAxis Y轴数据
     * @param cityNames 行政区划的名称
     * @param keys 各键的值
     * @return
     */
    public static Map<String, Object> commonConvertColumnMultiValue(List<Map<String, Object>> dataList, List<String> xAxis, List<List<BigDecimal>> yAxis, List<String> cityNames, List<String> keys, List<String> names){
        Map<String, Object> result = new HashMap<>(2);
        result.put("xAxis", xAxis);
        result.put("chartType", Arrays.asList("column"));
        result.put("name", cityNames);
        List<Serie> series = new ArrayList();
        if(CollectionUtils.isNotEmpty(dataList)){
                for (int i = 0; i < dataList.size(); i++) {
                    Map<String,Object> dataMap = dataList.get(i);
                    String key = String.valueOf(dataMap.get("countKey"));
                    int index = ((List)result.get("xAxis")).indexOf(key);
                    for (int j = 0; j < keys.size(); j++) {
                        List currentList = yAxis.get(j);
                        BigDecimal value = BigDecimal.valueOf(Long.parseLong(String.valueOf(dataMap.get(keys.get(j)))));
                        if(index != -1){
                            currentList.set(index, value);
                        }
                    }
                }
        }
        for (int i = 0; i < keys.size(); i++) {
            series.add(Serie.builder().name(names.get(i)).data(yAxis.get(i)).build());
        }
        result.put("seriesData", series);
        return result;
    }

    /**
     *  公共转化类,将统计数据转化为适合饼图的格式
     * @param dataList 统计数据
     * @param cityNames 行政区划的名称
     * @return
     */
    public static Map<String, List> commonConvertPie(List<Map<String, Object>> dataList, List<String> cityNames){
        Map<String, List> result = new HashMap<>(2);
        result.put("chartType", Arrays.asList("pie"));
        result.put("name", cityNames);
        result.put("seriesData",Collections.EMPTY_LIST);
        result.put("title", Arrays.asList(cityNames.get(0) + "统计图"));
        if(CollectionUtils.isNotEmpty(dataList)){
            List seriesList = new ArrayList();
            for (int i = 0; i < dataList.size(); i++) {
                Map<String,Object> dataMap = dataList.get(i);
                String key = String.valueOf(dataMap.get("countKey"));
                Long value = (Long) dataMap.get("countValue");
                Map<String,Object> resultMap = new HashMap<>();
                resultMap.put("name", key);
                resultMap.put("y", value);
                seriesList.add(resultMap);
            }
            result.put("seriesData", seriesList);
            return result;
        }else{
            return result;
        }
    }


}
