package com.zhd.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 任务报表 Mapper 接口
 * </p>
 *
 * @author zyg
 * @since 2018-05-18
 */
public interface TaskReportMapper {

    List<Map<String, Object>> countByType(@Param("begin")String begin, @Param("end")String endDay , @Param("areaIds")List<Integer> areaIds, @Param("type") Integer type);//统计各类型任务的总数

    Integer countByCity(@Param("begin")String begin, @Param("end")String endDay , @Param("areaIds")List<Integer> areaIds, @Param("type") Integer type);//统计各子区划的任务总总数

    List<Map<String, Object>> countByTime(@Param("begin")String begin, @Param("end")String endDay , @Param("areaIds")List<Integer> areaIds, @Param("type") Integer type);//统计完成时间



}
