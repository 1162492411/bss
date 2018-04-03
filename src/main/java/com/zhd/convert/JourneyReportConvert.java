package com.zhd.convert;

import org.apache.commons.collections.CollectionUtils;

import java.util.*;

/**
 * 行程报表转换类
 */
public class JourneyReportConvert {

    /**
     * 将使用概况(日/月)转化
     * @param dataList
     * @return
     */
    public static Map<String, List> convertOverviewByHour(List<Map<String, Object>> dataList, String cityName){
        List<String> hours = Arrays.asList("00","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23");
        Map<String, List> result = new HashMap<>(2);
        result.put("xAxis", hours);
        List<Long> yAxis = new ArrayList<>(hours.size());
        for (int i = 0; i < hours.size(); i++) {
            yAxis.add(0L);
        }
        result.put("yAxis", yAxis);
        result.put("name", Arrays.asList(cityName));
        if(CollectionUtils.isNotEmpty(dataList)){
            for (int i = 0; i < dataList.size(); i++) {
                Map<String,Object> dataMap = dataList.get(i);
                Integer key = Integer.valueOf(String.valueOf(dataMap.get("countKey")));
                Long value = (Long) dataMap.get("countValue");
                result.get("yAxis").set(key,value);
            }
            return result;
        }else{
            return result;
        }
    }

    /**
     * 将使用概况(日/月)转化
     * @param dataList
     * @return
     */
    public static Map<String, List<java.io.Serializable>> convertOverview(List<Map<String, Object>> dataList, String cityName){
        Map<String, List<java.io.Serializable>> result = new HashMap<>(2);
        result.put("xAxis", new ArrayList<>());
        result.put("yAxis", new ArrayList<>());
        result.put("name", Arrays.asList(cityName));
        if(CollectionUtils.isNotEmpty(dataList)){
            for (int i = 0; i < dataList.size(); i++) {
                Map<String,Object> dataMap = dataList.get(i);
                String key = String.valueOf(dataMap.get("countKey"));
                Long value = (Long) dataMap.get("countValue");
                result.get("xAxis").add(key);
                result.get("yAxis").add(value);
            }
            return result;
        }else{
            return null;
        }
    }



}
