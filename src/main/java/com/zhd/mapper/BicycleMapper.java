package com.zhd.mapper;

import com.zhd.pojo.Bicycle;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface BicycleMapper {

    int deleteByPrimaryKey(Integer id);

    int insertSelective(Bicycle record);

    int updateByPrimaryKeySelective(Bicycle record);

    Bicycle selectByPrimaryKey(Integer id);

    int selectCount(@Param("record")Bicycle record);

    List<Bicycle> selectBicycles(@Param("start")Integer start, @Param("record")Bicycle record);

    List<Bicycle> selectAll();

}