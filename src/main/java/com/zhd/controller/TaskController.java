package com.zhd.controller;


import com.alibaba.fastjson.util.TypeUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.zhd.convert.TaskConvert;
import com.zhd.enums.TaskStatusEnum;
import com.zhd.pojo.Bicycle;
import com.zhd.pojo.JSONResponse;
import com.zhd.pojo.Task;
import com.zhd.service.IBicycleService;
import com.zhd.service.ITaskService;
import com.zhd.service.IUserService;
import com.zhd.util.Constants;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * <p>
 * 调度任务表 前端控制器
 * </p>
 *
 * @author zyg
 * @since 2018-02-05
 */
@RestController
@RequestMapping("/tasks")
public class TaskController extends BaseController {

    @Autowired
    private ITaskService taskService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IBicycleService bicycleService;

    @GetMapping("list/{current}")
    public JSONResponse list(@PathVariable("current") int pageNum, Page<Task> page) {
        try {
            if (pageNum <= 0) throw new IllegalArgumentException(Constants.ILLEGAL_ARGUMENTS);
            return renderSuccess(TaskConvert.convertToVOPageInfo(taskService.selectPage(page, new EntityWrapper<Task>().orderBy("status"))));
        } catch (Exception e) {
            return renderError(e.getMessage());
        }
    }

    @PostMapping
    public JSONResponse insert(@RequestBody @Validated(Task.Insert.class) Task record, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                return renderError(bindingResult.getFieldError().getDefaultMessage());
            } else {
                record.setStartTime(TypeUtils.castToString(System.currentTimeMillis()));
                if (StringUtils.isNotEmpty(record.getUser())) {
                    record.setStatus(TaskStatusEnum.WAIT_COMPLETE.getCode());
                } else {
                    record.setStatus(TaskStatusEnum.WAIT_SOMEONE.getCode());
                }
                bicycleService.updateById(Bicycle.builder().id(record.getBicycle()).status(calculateBicycleStatusAfterInsertTask(record.getType())).build());


                return taskService.insert(record) ? renderSuccess(record) : renderError();
            }
        } catch (Exception e) {
            return renderError(e.getMessage());
        }
    }

    @PutMapping
    public JSONResponse update(@RequestBody @Validated(Task.Update.class) Task record, BindingResult bindingResult, HttpSession session) {
        System.out.println("received-->" + record);
        try {
            if (bindingResult.hasErrors()) {
                return renderError(bindingResult.getFieldError().getDefaultMessage());
            } else {
                String currentUserId = String.valueOf(session.getAttribute("userid"));
                if (StringUtils.isNotEmpty(currentUserId) && (userService.isAdmin(currentUserId) || currentUserId.equals(taskService.selectById(record.getId()).getUser()))) {
                    record.setStartTime(TypeUtils.castToString(System.currentTimeMillis()));
                    return taskService.updateById(record) ? renderSuccess(record) : renderError(Constants.UNKNOWN_EXCEPTION);
                } else {
                    return renderError(Constants.TIP_NO_PERMISSION);
                }
            }
        } catch (Exception e) {
            return renderError(e.getMessage());
        }
    }

    @PostMapping("done")
    public JSONResponse done(@RequestBody @Validated(Task.Update.class) Task record, BindingResult bindingResult, HttpSession session) {
        try {
            if (bindingResult.hasErrors()) {
                return renderError(bindingResult.getFieldError().getDefaultMessage());
            } else {
                String currentUserId = String.valueOf(session.getAttribute("userid"));
                if (StringUtils.isNotEmpty(currentUserId) && (userService.isAdmin(currentUserId) || currentUserId.equals(taskService.selectById(record.getId()).getUser()))) {
                    record.setEndTime(TypeUtils.castToString(System.currentTimeMillis()));
                    return taskService.updateById(record) ? renderSuccess(record) : renderError(Constants.UNKNOWN_EXCEPTION);
                } else {
                    return renderError(Constants.TIP_NO_PERMISSION);
                }
            }
        } catch (Exception e) {
            return renderError(e.getMessage());
        }
    }

    @DeleteMapping()
    public JSONResponse delete(@RequestBody @Validated(Task.Delete.class) Task record, BindingResult bindingResult, HttpSession session) {
        try {
            if (bindingResult.hasErrors()) {
                return renderError(bindingResult.getFieldError().getDefaultMessage());
            } else if ((record = taskService.selectById(record.getId())) != null) {
                String currentUserId = String.valueOf(session.getAttribute("userid"));
                if (StringUtils.isNotEmpty(currentUserId) && userService.isAdmin(currentUserId)) {
                    return taskService.deleteById(record.getId()) ? renderSuccess(record) : renderError();
                } else {
                    return renderError(Constants.UNKNOWN_EXCEPTION);
                }
            } else {
                return renderError(Constants.TIP_EMPTY_DATA);
            }
        } catch (Exception e) {
            return renderError(e.getMessage());
        }
    }

    /**
     * 根据任务类型返回车辆状态
     * @param type 任务的类型
     * @return 车辆的状态
     */
    private int calculateBicycleStatusAfterInsertTask(int type){
        switch(type){
            case 1 : return 3;
            case 2 : return 4;
            case 3 : return 5;
            default : return 0;
        }
    }

    /**
     * 完成任务后计算车辆状态
     * @param type 任务待类型
     * @return 车辆的状态
     */
    private int calculateBicycleStatusAfterDoneTask(int type){
//        switch(type){
//
//        }
        return 0;
    }



}

