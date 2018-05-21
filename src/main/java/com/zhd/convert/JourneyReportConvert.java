package com.zhd.convert;

import com.zhd.util.DataUtil;
import java.time.LocalDate;
import java.util.*;
import static com.zhd.util.ChartUtil.commonConvertColumn;
import static com.zhd.util.ChartUtil.commonConvertLine;
import static com.zhd.util.ChartUtil.commonConvertPie;
import static com.zhd.util.Constants.CHART_TYPE_COLUMN;
import static com.zhd.util.Constants.CHART_TYPE_LINE;
import static com.zhd.util.Constants.CHART_TYPE_PIE;

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
     * @param groupType 分组依据
     */
    public static Map<String, Object> convertOverview(List<Map<String, Object>> dataList, String cityName, LocalDate startDate, LocalDate endDate, String chartType, int groupType) {
        switch(groupType){
            case 0 : return convertOverviewByHour(dataList, cityName, chartType);
            case 1 : return convertOverviewByDay(dataList,cityName,startDate,endDate, chartType);
            case 2 : return convertOverviewByMonth(dataList,cityName,startDate,endDate, chartType);
            case 3 : return convertOverviewByCity(dataList, cityName, chartType);
            default: return Collections.EMPTY_MAP;
        }
    }

    /**
     * 将使用概况(按小时分组)转化
     *
     * @param dataList  统计数据
     * @param cityName  区划名称
     * @param chartType 图表类型
     * @return
     */
    private static Map<String, Object> convertOverviewByHour(List<Map<String, Object>> dataList, String cityName,String chartType) {
        switch (chartType) {
            case CHART_TYPE_PIE :
                return commonConvertPie(dataList, cityName);
            case CHART_TYPE_COLUMN :
                return commonConvertColumn(dataList, DEFAULT_HOURS_XAXIS, DataUtil.generateZeroLongListList(1, DEFAULT_HOURS_XAXIS.size()), cityName, SINGLE_KEYS, Arrays.asList("每小时使用情况"));
            default :
                return commonConvertLine(dataList, DEFAULT_HOURS_XAXIS, DataUtil.generateZeroLongListList(1, DEFAULT_HOURS_XAXIS.size()), cityName, SINGLE_KEYS, Arrays.asList("每小时使用情况"));
        }
    }

    /**
     * 将使用概况(按天分组)转化
     *
     * @param dataList  统计数据
     * @param cityName  区划名称
     * @param chartType 图表类型
     * @return
     */
    private static Map<String, Object> convertOverviewByDay(List<Map<String, Object>> dataList, String cityName, LocalDate startDate, LocalDate endDate, String chartType) {
        List<String> xAxis = DataUtil.generateDays(startDate, endDate);
        switch (chartType) {
            case CHART_TYPE_PIE :
                return commonConvertPie(dataList, cityName);
            case CHART_TYPE_COLUMN :
                return commonConvertColumn(dataList, xAxis, DataUtil.generateZeroLongListList(1, xAxis.size()), cityName, SINGLE_KEYS, Arrays.asList("每日使用情况"));
            default :
                return commonConvertLine(dataList, xAxis, DataUtil.generateZeroLongListList(1, xAxis.size()), cityName, SINGLE_KEYS, Arrays.asList("每日使用情况"));
        }
    }

    /**
     * 将使用概况(按月分组)转化
     *
     * @param dataList  统计数据
     * @param cityName  区划名称
     * @param chartType 图表类型
     * @return
     */
    private static Map<String, Object> convertOverviewByMonth(List<Map<String, Object>> dataList, String cityName, LocalDate startDate, LocalDate endDate, String chartType) {
        List<String> xAxis = DataUtil.generateMonths(startDate, endDate);
        switch (chartType) {
            case CHART_TYPE_PIE :
                return commonConvertPie(dataList, cityName);
            case CHART_TYPE_COLUMN :
                return commonConvertColumn(dataList, xAxis, DataUtil.generateZeroLongListList(1, xAxis.size()), cityName, SINGLE_KEYS, Arrays.asList("每月使用情况"));
            default :
                return commonConvertLine(dataList, xAxis, DataUtil.generateZeroLongListList(1, xAxis.size()), cityName, SINGLE_KEYS, Arrays.asList("每月使用情况"));
        }
    }

    /**
     * 将使用概况(按子区划分组)转化
     *
     * @param dataList  统计数据
     * @param cityName  区划名称
     * @param chartType 图表类型
     * @return
     */
    private static Map<String, Object> convertOverviewByCity(List<Map<String, Object>> dataList, String cityName,String chartType) {
        List<String> xAxis = new ArrayList<>();
        for (Map<String, Object> dataMap : dataList) {
            String countKey = String.valueOf(dataMap.get("countKey"));
            if (!xAxis.contains(countKey)) {
                xAxis.add(countKey);
            }
        }
        switch (chartType) {
            case CHART_TYPE_PIE :
                return commonConvertPie(dataList, cityName);
            case CHART_TYPE_COLUMN :
                return commonConvertColumn(dataList, xAxis, DataUtil.generateZeroLongListList(1, xAxis.size()), cityName, SINGLE_KEYS, Arrays.asList("各区划使用情况"));
            default :
                return commonConvertLine(dataList, xAxis, DataUtil.generateZeroLongListList(1, xAxis.size()), cityName, SINGLE_KEYS, Arrays.asList("各区划使用情况"));
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
            case CHART_TYPE_PIE:
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
            case CHART_TYPE_PIE:
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
        switch(timeType){
            case 0 : return convertFlowByHour(dataList, cityName, chartType);
            default : return convertFlowByDayOrMonth(dataList, cityName, chartType);
        }
    }

    /**
     * 将流动情况(按小时分组)转化
     * @param dataList 统计数据
     * @param cityName 区划名称
     * @param chartType 图表类型
     */
    private static Map<String, Object> convertFlowByHour(List<Map<String, Object>> dataList, String cityName, String chartType){
//        switch(chartType){
//            case CHART_TYPE_COLUMN :
//                return commonConvertLine(dataList, DEFAULT_HOURS_XAXIS, DataUtil.generateZeroBigDecimalListList(FLOW_KEYS.size(), DEFAULT_HOURS_XAXIS.size()), cityName, FLOW_KEYS, FLOW_NAMES);
//            default :
                return commonConvertColumn(dataList, DEFAULT_HOURS_XAXIS, DataUtil.generateZeroBigDecimalListList(FLOW_KEYS.size(), DEFAULT_HOURS_XAXIS.size()), cityName, FLOW_KEYS, FLOW_NAMES);
//        }
    }

    /**
     * 将流动情况(按天/月分组)转化
     * @param dataList 统计数据
     * @param cityName 区划名称
     * @param chartType 图表类型
     */
    private static Map<String, Object> convertFlowByDayOrMonth(List<Map<String, Object>> dataList, String cityName, String chartType){
        switch(chartType){
            case CHART_TYPE_LINE :
                return commonConvertLine(dataList, DEFAULT_HOURS_XAXIS, DataUtil.generateZeroBigDecimalListList(FLOW_KEYS.size(), DEFAULT_HOURS_XAXIS.size()), cityName, FLOW_KEYS, FLOW_NAMES);
            default :
                return commonConvertColumn(dataList, DEFAULT_HOURS_XAXIS, DataUtil.generateZeroBigDecimalListList(FLOW_KEYS.size(), DEFAULT_HOURS_XAXIS.size()), cityName, FLOW_KEYS, FLOW_NAMES);
        }
    }




}
