package com.zhd.service;

import java.util.List;
import java.util.Map;

/**
 * 行程报表服务类
 */
public interface IJourneyReportService {

    List<Map<String, Object>> countByHour(String beginDay, String endDay, Integer cityId);//在统计周期内统计每小时使用总数

    List<Map<String, Object>> countByDay(String beginDay, String endDay, Integer cityId);//在统计周期内统计每日使用总数

    List<Map<String, Object>> countByMonth(String beginMonth, String endMonth, Integer cityId);//在统计周期内统计每月使用总数

    List<Map<String, Integer>> countByDistrictDay(int parentCode, String beginDay, String endDay);//在统计周期内统计指定区域的子区域内每日使用总数

    List<Map<String, Integer>> countByDistrictMonth(int parentCode, String beginMonth, String endMonth);//在统计周期内统计指定区域的子区域的每月使用总数




}
