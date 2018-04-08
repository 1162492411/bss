package com.zhd.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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

    List<Map<String,Object>> countRideTime(@Param("begin")String beiginDay, @Param("end")String endDay , @Param("cityIds")List<Integer> cityIds);//统计某区域的所有子区域的统计周期内的骑行时间

    List<Map<String,Object>> countRideDistance(@Param("begin")String beiginDay, @Param("end")String endDay , @Param("cityIds")List<Integer> cityIds);//统计某区域的所有子区域的统计周期内的骑行距离

}
