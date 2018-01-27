package com.zhd.mapper;

import com.zhd.pojo.Task;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TaskMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(Task record);

    int updateByPrimaryKeySelective(Task record);

    Task selectByPrimaryKey(Integer id);

    int selectCount(@Param("record")Task record);

    List<Task> selectTasks(@Param("start")Integer start, @Param("record")Task record);

}