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
    private static final List<String> DEFAULT_HOURS_XAXIS = Arrays.asList("00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23");
    private static final List<String> DEFAULT_RIDETIME_XAXIS = Arrays.asList("30分钟内", "30-60分钟", "60-90分钟", "90-120分钟", "120分钟以上", "异常数据");
    private static final List<String> FLOW_KEYS = Arrays.asList("countIn", "countInner", "countOut");
    private static final List<String> FLOW_NAMES = Arrays.asList("流入", "区域内流动", "流出");
    private static final List<String> SINGLE_KEYS = Arrays.asList("countValue");


    /**
     * 将使用概况转化
     *
     * @param dataList  统计数据
     * @param cityName  区划名称
     * @param chartType 图表类型
     */
    public static Map<String, Object> convertOverview(List<Map<String, Object>> dataList, String cityName, LocalDate startDate, LocalDate endDate, String chartType, int timeType) {
        List<String> xAxis;
        switch (chartType) {
            case "pie":
                return commonConvertPie(dataList, cityName);
            default:
                switch (timeType) {
                    case 0:
                        return commonConvertLine(dataList, DEFAULT_HOURS_XAXIS, DataUtil.generateZeroLongListList(1, DEFAULT_HOURS_XAXIS.size()), cityName, SINGLE_KEYS, Arrays.asList("每小时使用情况"));
                    case 1 :
                        xAxis = DataUtil.generateDays(startDate, endDate);
                        return commonConvertLine(dataList, xAxis, DataUtil.generateZeroLongListList(1, xAxis.size()), cityName, SINGLE_KEYS, Arrays.asList("每日使用情况"));
                    case 2 :
                        xAxis = DataUtil.generateMonths(startDate, endDate);
                        return commonConvertLine(dataList, xAxis, DataUtil.generateZeroLongListList(1, xAxis.size()), cityName, SINGLE_KEYS, Arrays.asList("每月使用情况"));
                    default:
                       return Collections.EMPTY_MAP;
                }
        }
    }


    /**
     * 将骑行时间统计情况转化
     *
     * @param dataList 统计数据
     * @param cityName 区划名称
     */
    public static Map<String, Object> convertRideTime(List<Map<String, Object>> dataList, String cityName, String chartType) {
        switch (chartType) {
            case "pie":
                return commonConvertPie(dataList, cityName);
            default:
                return commonConvertLine(dataList, DEFAULT_RIDETIME_XAXIS, DataUtil.generateZeroLongListList(1, DEFAULT_RIDETIME_XAXIS.size()), cityName, SINGLE_KEYS, Arrays.asList("骑行时间情况"));
        }
    }

    /**
     * 将骑行距离统计情况转化
     *
     * @param dataList 统计数据
     * @param cityName 区划名称
     */
    public static Map<String, Object> convertRideDistance(List<Map<String, Object>> dataList, String cityName, String chartType) {
        switch (chartType) {
            case "pie":
                return commonConvertPie(dataList, cityName);
            default:
                List<Integer> resultX = new ArrayList<>();
                for (Map<String, Object> dataMap : dataList) {
                    Integer countKey = Integer.valueOf(String.valueOf(dataMap.get("countKey")));
                    if (!resultX.contains(countKey)) {
                        resultX.add(countKey);
                    }
                }
                Collections.sort(resultX, Comparator.comparingInt(o -> o));
                List<String> xAxis = new ArrayList<>(resultX.size());
                for (int i = 0; i < resultX.size(); i++) {
                    xAxis.add(resultX.get(i).toString());
                }
                return commonConvertLine(dataList, xAxis, DataUtil.generateZeroLongListList(1, xAxis.size()), cityName, SINGLE_KEYS, Arrays.asList("骑行距离"));
        }
    }

    /**
     * 将流动情况转化
     * @param dataList 统计数据
     * @param cityName 区划名称
     * @param chartType 图表类型
     * @param timeType 时间类型
     * @return
     */
    public static Map<String, Object> convertFlow(List<Map<String, Object>> dataList, String cityName, String chartType, int timeType) {
        List<String> resultX = new ArrayList<>();
        for (Map<String, Object> dataMap : dataList) {
            String countKey = String.valueOf(dataMap.get("countKey"));
            if (!resultX.contains(countKey)) {
                resultX.add(countKey);
            }
        }
        switch (chartType) {
            case "line" :
                switch (timeType) {
                    case 0:
                        return commonConvertLine(dataList, DEFAULT_HOURS_XAXIS, DataUtil.generateZeroBigDecimalListList(FLOW_KEYS.size(), DEFAULT_HOURS_XAXIS.size()), cityName, FLOW_KEYS, FLOW_NAMES);
                    default:
                        return commonConvertLine(dataList, resultX, DataUtil.generateZeroBigDecimalListList(FLOW_KEYS.size(), resultX.size()), cityName, FLOW_KEYS, FLOW_NAMES);
                }
            default:
                switch (timeType) {
                    case 0:
                        return commonConvertColumn(dataList, DEFAULT_HOURS_XAXIS, DataUtil.generateZeroBigDecimalListList(1, DEFAULT_HOURS_XAXIS.size()), cityName, FLOW_KEYS, FLOW_NAMES);
                    default:
                        return commonConvertColumn(dataList, resultX, DataUtil.generateZeroBigDecimalListList(FLOW_KEYS.size(), resultX.size()), cityName, FLOW_KEYS, FLOW_NAMES);
                }
        }
    }

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
                Long value = (Long) dataMap.get("countValue");
                Map<String, Object> resultMap = new HashMap<>();
                resultMap.put("name", key);
                resultMap.put("y", value);
                seriesList.add(resultMap);
            }
            result.put("seriesData", seriesList);
            return result;
        } else {
            return result;
        }
    }


}
