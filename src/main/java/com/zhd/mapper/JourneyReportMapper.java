package com.zhd.mapper;

import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 行程报表 Mapper 接口
 * </p>
 *
 * @author zyg
 * @since 2018-02-05
 */
public interface JourneyReportMapper {

    List<Map<String, Object>> countByHour(@Param("begin") String beginDay, @Param("end") String endDay, @Param("cityIds")List<Integer> cityIds);//在统计周期内统计每小时使用总数

    List<Map<String, Object>> countByDay(@Param("begin") String beginDay, @Param("end") String endDay,@Param("cityIds")List<Integer> cityIds);//在统计周期内统计每日使用总数

    List<Map<String, Object>> countByMonth(@Param("begin") String beginMonth, @Param("end") String endMonth, @Param("cityIds")List<Integer> cityIds);//在统计周期内统计每月使用总数

    List<Map<String, Integer>> countByDistrictDay(int parentCode, String beginDay, String endDay);//在统计周期内统计指定区域的子区域内每日使用总数

    List<Map<String, Integer>> countByDistrictWeek(int parentCode, String beginWeek, String endWeek);//在统计周期内统计指定区域的子区域的每周使用总数

    List<Map<String, Integer>> countByDistrictMonth(int parentCode, String beginMonth, String endMonth);//在统计周期内统计指定区域的子区域的每月使用总数


}
