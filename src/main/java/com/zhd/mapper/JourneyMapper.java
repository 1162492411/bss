package com.zhd.mapper;

import com.zhd.pojo.Journey;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface JourneyMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(Journey record);

    int updateByPrimaryKeySelective(Journey record);

    Journey selectByPrimaryKey(Integer id);

    int selectCount(@Param("record")Journey journey);

    List<Journey> selectJourneys(@Param("start")Integer start, @Param("record")Journey journey);
}