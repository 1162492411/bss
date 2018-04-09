package com.zhd.service;

import java.util.List;
import java.util.Map;

/**
 * 行程报表服务类
 */
public interface IJourneyReportService {

    List<Map<String, Object>> countUseCount(String beginDay, String endDay, Integer cityId, Integer timeType);//统计使用总数

    List<Map<String, Object>> countUseCountByCity(String beginDay, String endDay, Integer cityId);//统计某区域内的所有子区域的使用总数

    List<Map<String,Object>> countRideTime(String begin, String end, Integer cityId);//统计骑行时间分布情况

    List<Map<String,Object>> countRideDistance(String begin, String end, Integer cityId);//统计骑行距离分布情况

    List<Map<String, Object>> countFlow(String begin, String end, Integer cityId, Integer timeType);//统计车辆流动情况




}
