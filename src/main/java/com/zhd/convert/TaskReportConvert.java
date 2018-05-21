package com.zhd.convert;

import com.zhd.util.DataUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.*;

import static com.zhd.util.ChartUtil.commonConvertColumn;
import static com.zhd.util.ChartUtil.commonConvertLine;
import static com.zhd.util.ChartUtil.commonConvertPie;
import static com.zhd.util.Constants.CHART_TYPE_PIE;

/**
 * 任务报表转换类
 */
public class TaskReportConvert {
    private static final Logger logger = LoggerFactory.getLogger(TaskReportConvert.class);

//    private static final List<String> DEFAULT_TASKTIME_XAXIS = Arrays.asList("12小时内", "12-24小时", "24-36小时", "36-48小时", "48-60小时", "60-72小时","72小时以上","异常数据");
    private static final List<String> SINGLE_KEYS = Arrays.asList("countValue");

    /**
     * 将任务耗时统计情况转化
     *
     * @param dataList 统计数据
     * @param cityName 区划名称
     */
    public static Map<String, Object> convertTaskTime(List<Map<String, Object>> dataList, String cityName, String chartType) {
        switch (chartType) {
            case CHART_TYPE_PIE:
                return commonConvertPie(dataList, cityName);
            default:{
                List<String> xAxis = new ArrayList<>();
                for (Map<String, Object> dataMap : dataList) {
                    String countKey = String.valueOf(dataMap.get("countKey"));
                    if (!xAxis.contains(countKey)) {
                        xAxis.add(countKey);
                    }
                }
                return commonConvertColumn(dataList, xAxis, DataUtil.generateZeroLongListList(1, xAxis.size()), cityName, SINGLE_KEYS, Arrays.asList("任务耗时分布"));
            }
        }
    }

    /**
     * 将任务概况(按子区划分组)转化
     *
     * @param dataList  统计数据
     * @param cityName  区划名称
     * @param chartType 图表类型
     * @return
     */
    private static Map<String, Object> convertByCity(List<Map<String, Object>> dataList, String cityName,String chartType) {
        logger.debug("convertCity receive dataList{}", dataList);
        switch (chartType) {
            case CHART_TYPE_PIE:
                return commonConvertPie(dataList, cityName);
            default:{
                List<String> xAxis = new ArrayList<>();
                for (Map<String, Object> dataMap : dataList) {
                    String countKey = String.valueOf(dataMap.get("countKey"));
                    if (!xAxis.contains(countKey)) {
                        xAxis.add(countKey);
                    }
                }
                logger.debug("xAxis is{}", xAxis);
                return commonConvertColumn(dataList, xAxis, DataUtil.generateZeroLongListList(1, xAxis.size()), cityName, SINGLE_KEYS, Arrays.asList("子区划任务分布"));
            }
        }
    }

    /**
     * 将任务总数分布转化
     *
     * @param dataList  统计数据
     * @param cityName  区划名称
     * @param chartType 图表类型
     * @return
     */
    private static Map<String, Object> convertByCount(List<Map<String, Object>> dataList, String cityName, String chartType) {
        switch (chartType) {
            case CHART_TYPE_PIE:
                return commonConvertPie(dataList, cityName);
            default:{
                List<String> xAxis = new ArrayList<>();
                for (Map<String, Object> dataMap : dataList) {
                    String countKey = String.valueOf(dataMap.get("countKey"));
                    if (!xAxis.contains(countKey)) {
                        xAxis.add(countKey);
                    }
                }
                return commonConvertColumn(dataList, xAxis, DataUtil.generateZeroLongListList(1, xAxis.size()), cityName, SINGLE_KEYS, Arrays.asList("任务总数分布"));
            }
        }
    }

    /**
     * 按任务总数统计转化
     * @param dataList 统计数据
     * @param cityName 区划名称
     * @param chartType 图表类型
     * @param groupType 分组依据
     * @return
     */
    public static Map<String, Object> convertTaskCount(List<Map<String, Object>> dataList, String cityName, String chartType, Integer groupType) {
        if(groupType == 1){
            return convertByCount(dataList, cityName, chartType);
        }else if(groupType == 2){
            return convertByCity(dataList, cityName, chartType);
        }else{
            return Collections.EMPTY_MAP;
        }
    }





}
