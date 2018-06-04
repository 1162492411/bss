package com.zhd.util;

import com.zhd.pojo.Serie;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * HighCharts工具类，用于将传入的数据转化为符合highCharts的图表的格式
 */
@Slf4j
public class ChartUtil {

    /**
     * 公共转化类,将统计数据转化为适合直线图的单值的格式
     *
     * @param dataList 统计数据
     * @param xAxis    X轴数据
     * @param yAxis    Y轴数据
     * @param cityName 行政区划的名称
     * @return
     */
    public static Map<String, Object> commonConvertLine(List<Map<String, Object>> dataList, List<String> xAxis, List<List> yAxis, String cityName, List<String> keys, List<String> names) {
        Map<String, Object> result = new HashMap<>(2);
        result.put("xAxis", xAxis);
        result.put("name", cityName);
        result.put("chartType", "line");
        List<Serie> series = new ArrayList<>(keys.size());
        if (CollectionUtils.isNotEmpty(dataList)) {
            for (int i = 0; i < dataList.size(); i++) {
                Map<String, Object> dataMap = dataList.get(i);
                String key = String.valueOf(dataMap.get("countKey"));
                int index = ((List) result.get("xAxis")).indexOf(key);
                for (int j = 0; j < keys.size(); j++) {
                    List currentList = yAxis.get(j);
                    BigDecimal value = BigDecimal.valueOf(Long.parseLong(String.valueOf(dataMap.get(keys.get(j)))));
                    if (index != -1) {
                        currentList.set(index, value);
                    }
                }
            }
        }
        List<String> colors = DataUtil.generateRandomColor(keys.size());
        for (int i = 0; i < keys.size(); i++) {
            series.add(Serie.builder().name(cityName + names.get(i)).data(yAxis.get(i)).color(colors.get(i)).build());
        }
        result.put("seriesData", series);
        return result;
    }

    /**
     * 公共转化类,将统计数据转化为适合柱状图的多值的格式
     *
     * @param dataList 统计数据
     * @param xAxis    X轴数据
     * @param yAxis    Y轴数据
     * @param cityName 行政区划的名称
     * @param keys     各键的值
     * @return
     */
    public static Map<String, Object> commonConvertColumn(List<Map<String, Object>> dataList, List<String> xAxis, List<List> yAxis, String cityName, List<String> keys, List<String> names) {
        Map<String, Object> result = new HashMap<>(2);
        result.put("xAxis", xAxis);
        result.put("chartType", "column");
        result.put("name", cityName);
        List<Serie> series = new ArrayList();
        if (CollectionUtils.isNotEmpty(dataList)) {
            for (int i = 0; i < dataList.size(); i++) {
                Map<String, Object> dataMap = dataList.get(i);
                String key = String.valueOf(dataMap.get("countKey"));
                int index = ((List) result.get("xAxis")).indexOf(key);
                for (int j = 0; j < keys.size(); j++) {
                    List currentList = yAxis.get(j);
                    BigDecimal value = BigDecimal.valueOf(Long.parseLong(String.valueOf(dataMap.get(keys.get(j)))));
                    if (index != -1) {
                        currentList.set(index, value);
                    }
                }
            }
        }
        List<String> colors = DataUtil.generateRandomColor(keys.size());
        for (int i = 0; i < keys.size(); i++) {
            series.add(Serie.builder().name(cityName +  names.get(i)).data(yAxis.get(i)).color(colors.get(i)).build());
        }
        result.put("seriesData", series);
        return result;
    }

    /**
     * 公共转化类,将统计数据转化为适合饼图的格式
     *
     * @param dataList 统计数据
     * @param cityName 行政区划的名称
     * @return
     */
    public static Map<String, Object> commonConvertPie(List<Map<String, Object>> dataList, String cityName) {
        Map<String, Object> result = new HashMap<>(2);
        result.put("chartType", "pie");
        result.put("seriesData", Collections.EMPTY_LIST);
        result.put("title", cityName + "统计图");
        if (CollectionUtils.isNotEmpty(dataList)) {
            List seriesList = new ArrayList();
            for (int i = 0; i < dataList.size(); i++) {
                Map<String, Object> dataMap = dataList.get(i);
                String key = String.valueOf(dataMap.get("countKey"));
                Object value = dataMap.get("countValue");
                Map<String, Object> resultMap = new HashMap<>();
                resultMap.put("name", key);
                resultMap.put("y", value);
                try{
                    Double valueDouble = Double.parseDouble(String.valueOf(value));
                    if(valueDouble != 0.0){
                        seriesList.add(resultMap);
                    }
                }catch (Exception e){
                    continue;//出现value值取值异常时忽略该条数据
                }
            }
            result.put("seriesData", seriesList);
            return result;
        } else {
            return result;
        }
    }
}
