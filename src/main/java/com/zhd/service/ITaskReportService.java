package com.zhd.service;

import java.util.List;
import java.util.Map;

/**
 * Created by Mo on 2018/5/18.
 */
public interface ITaskReportService {

    List<Map<String, Object>> countCount(String beginDay, String endDay, Integer cityId, Integer taskType, Integer groupType);//统计任务总数

    List<Map<String, Object>> countByTime(String beginDay, String endDay, Integer cityId, Integer taskType);//按完成时间统计

}
