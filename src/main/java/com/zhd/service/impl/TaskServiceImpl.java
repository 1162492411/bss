package com.zhd.service.impl;

import com.zhd.pojo.Task;
import com.zhd.mapper.TaskMapper;
import com.zhd.service.ITaskService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 调度任务表 服务实现类
 * </p>
 *
 * @author zyg
 * @since 2018-02-05
 */
@Service
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task> implements ITaskService {

    @Autowired
    private TaskMapper taskMapper;

    @Override
    public boolean checkTask(Integer id) {
        return taskMapper.selectById(id) != null;
    }
}
