package com.zhd.mapper;

import com.zhd.pojo.Area;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AreaMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Area record);

    int updateByPrimaryKeySelective(Area record);

    Area selectByPrimaryKey(Integer id);

    int selectCount(@Param("record") Area area);

    List<Area> selectAreas(@Param("start")Integer start, @Param("record")Area area);

}