package com.zhd.service;

import com.zhd.mapper.TaskMapper;
import com.zhd.pojo.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Mo on 2017/10/25.
 */
@Service
public class TaskService {
    @Autowired
    private TaskMapper taskMapper;

    /**
     * 按主键查询任务信息
     * @param id 任务的主键
     * @return 任务信息
     */
    public Task searchById(Integer id){
        return taskMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询符合条件的任务的总数
     * @param record 指定条件的任务信息
     * @return 符合条件的任务的总数
     */
    public int selectCount(Task record){
        return taskMapper.selectCount(record);
    }

    /**
     * 返回分页的符合条件的任务信息
     * @param start 数据库中任务的起始行数
     * @param record 指定条件的任务信息
     * @return 分页后的符合条件的任务信息集合
     */
    public List<Task> selectTasks(Integer start, Task record){
        return taskMapper.selectTasks(start,record);
    }

    /**
     * 返回是否成功新增任务
     * @param record 新增的任务信息
     * @return 数据库影响行数是否大于0
     */
    public boolean insert(Task record){
        return taskMapper.insertSelective(record) > 0;
    }

    /**
     * 返回删除指定任务的结果
     * @param record 待删除的任务
     * @return 是否已删除指定任务
     */
    public boolean delete(Task record){
        return taskMapper.deleteByPrimaryKey(record.getId()) > 0;
    }

    /**
     * 返回修改指定任务的结果
     * @param record 待修改的任务信息
     * @return 是否已修改指定任务
     */
    public boolean update(Task record){
        return taskMapper.updateByPrimaryKeySelective(record) > 0;
    }


}
