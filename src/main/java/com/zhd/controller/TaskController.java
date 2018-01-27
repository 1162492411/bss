package com.zhd.controller;

import com.zhd.pojo.Task;
import com.zhd.pojo.Page;
import com.zhd.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;

/**
 * 调度任务控制器
 * Created by Mo on 2017/9/23.
 */
@RestController
@RequestMapping("tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;

    /**
     * 添加任务信息
     * @param record 待添加的任务信息
     * @return 是否成功添加任务信息
     */
    @RequestMapping(method = RequestMethod.POST)
    public boolean add(@RequestBody Task record){
        record.setStartTime(LocalDateTime.now().toString());
        return taskService.insert(record);
    }

    /**
     * 删除任务信息
     * @param record 待删除的任务信息
     * @return 是否已删除任务
     */
    @RequestMapping(method = RequestMethod.DELETE)
    public boolean delete(@RequestBody Task record){
        return taskService.delete(record);
    }

    /**
     * 修改任务信息
     * @param record 待修改的任务信息
     * @return 是否成功修改任务信息
     */
    @RequestMapping(method = RequestMethod.PUT)
    public boolean update(@RequestBody Task record){
        if(record.getStatus()) record.setEndTime(LocalDateTime.now().toString());
        return taskService.update(record);
    }

    /**
     * 默认-查看第一页任务信息
     * @param record 指定条件的任务
     * @param result 包含分页后的任务信息的分页对象
     * @return 包含分页后的任务信息的分页对象
     */
    @RequestMapping(method = RequestMethod.GET)
    public Page defaults(Task record, Page result){
        return tasks(1,record,result);
    }

    /**
     * 分页查看任务信息
     * @param page 前台传入的页数(从1开始)
     * @param record 指定条件的任务
     * @param result 包含分页后的任务信息的分页对象
     * @return 包含分页后的任务信息的分页对象
     */
    @RequestMapping(value = "{page}", method = RequestMethod.GET)
    public Page tasks(@PathVariable int page, Task record, Page result){
        HashMap hashMap = new HashMap();
        return result.setDatas(taskService.selectTasks(result.setTotalCount(taskService.selectCount(record)).setCurrentPage(page).getStart(),record)).setKeys(Task.getKeys()).setNames(Task.getNames());
    }

}
