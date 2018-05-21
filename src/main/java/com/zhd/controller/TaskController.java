package com.zhd.controller;


import com.alibaba.fastjson.util.TypeUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.zhd.convert.TaskConvert;
import com.zhd.enums.BicycleStatusEnum;
import com.zhd.enums.TaskStatusEnum;
import com.zhd.enums.TaskTypeEnum;
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
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

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

    @GetMapping("list/{keyword}/{current}")
    public JSONResponse searchList(@PathVariable("keyword")String keyword, @PathVariable("current") int pageNum, Page<Task> page) {
        try {
            if (pageNum <= 0) throw new IllegalArgumentException(Constants.ILLEGAL_ARGUMENTS);
            if(StringUtils.isNotBlank(keyword)){
                 //按状态查询
                int resultStatus = TaskStatusEnum.getByStatus(keyword);
                if(resultStatus != -1){
                    return renderSuccess(TaskConvert.convertToVOPageInfo(taskService.selectPage(page, new EntityWrapper<Task>().eq("status", resultStatus).orderBy("status"))));
                }
                //按类型查询
                int resultType = TaskTypeEnum.getByType(keyword);
                if(resultType != -1){
                    return renderSuccess(TaskConvert.convertToVOPageInfo(taskService.selectPage(page, new EntityWrapper<Task>().eq("type", resultType).orderBy("status"))));
                }
                //按车辆或用户模糊查询
                return renderSuccess(TaskConvert.convertToVOPageInfo(taskService.selectPage(page, new EntityWrapper<Task>().like("bicycle", keyword).or().like("user", keyword).orderBy("status"))));
            }else{
                return renderSuccess(TaskConvert.convertToVOPageInfo(taskService.selectPage(page, new EntityWrapper<Task>().orderBy("status"))));
            }
        } catch (Exception e) {
            return renderError(e.getMessage());
        }
    }

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
                record.setStartTime(TypeUtils.castToString(System.currentTimeMillis() / 1000));
                if(StringUtils.isBlank(record.getName())){
                    record.setName(TaskTypeEnum.getByCode(record.getType()).toString() + record.getBicycle());
                }
                if (StringUtils.isNotBlank(record.getUser())) {
                    record.setStatus(TaskStatusEnum.WAIT_COMPLETE.getCode());
                } else {
                    record.setStatus(TaskStatusEnum.WAIT_SOMEONE.getCode());
                }
                bicycleService.updateById(Bicycle.builder().id(record.getBicycle()).status(calculateStatusAfterInsert(record.getType())).build());
                return taskService.insert(record) ? renderSuccess(record) : renderError();
            }
        } catch (Exception e) {
            return renderError(e.getMessage());
        }
    }

    @PutMapping
    public JSONResponse dispatch(@RequestBody @Validated(Task.Update.class) Task record, BindingResult bindingResult, HttpSession session) {
        try {
            if (bindingResult.hasErrors() || StringUtils.isBlank(record.getUser())) {
                return renderError(bindingResult.getFieldError().getDefaultMessage());
            } else {
                record.setStatus(TaskStatusEnum.WAIT_COMPLETE.getCode());
                String currentUserId = String.valueOf(session.getAttribute("userid"));
                if (StringUtils.isNotEmpty(currentUserId) && (userService.isAdmin(currentUserId) || currentUserId.equals(taskService.selectById(record.getId()).getUser()))) {
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
                    record.setEndTime(TypeUtils.castToString(System.currentTimeMillis() / 1000));
                    String startTime = taskService.selectById(record.getId()).getStartTime();
                    LocalDateTime startTimeValue = LocalDateTime.ofInstant(Instant.ofEpochSecond(Long.parseLong(startTime)), ZoneId.of("Asia/Shanghai"));
                    long taskTimeValue = ChronoUnit.SECONDS.between(startTimeValue, Instant.now());
                    record.setTaskTime(TypeUtils.castToString(taskTimeValue));
                    record.setStatus(TaskStatusEnum.DONE.getCode());
                    bicycleService.updateById(Bicycle.builder().id(record.getBicycle()).status(calculateStatusAfterDone(record.getType())).build());
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
                    int bicycleId = taskService.selectById(record.getId()).getBicycle();
                    bicycleService.updateById(Bicycle.builder().id(bicycleId).status(BicycleStatusEnum.UNUSED.getCode()).build());
                    return taskService.deleteById(record.getId()) ? renderSuccess(record) : renderError(Constants.UNKNOWN_EXCEPTION);
                } else {
                    return renderError(Constants.TIP_NO_PERMISSION);
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
     *
     * @param type 任务的类型
     * @return 车辆的状态
     */
    private int calculateStatusAfterInsert(int type) {
        if (type == TaskTypeEnum.MOVE.getCode()) return BicycleStatusEnum.WAIT_MOVE.getCode();
        else if (type == TaskTypeEnum.REPAIR.getCode()) return BicycleStatusEnum.WAIT_REPAIR.getCode();
        else if (type == TaskTypeEnum.DISABLE.getCode()) return BicycleStatusEnum.WAIT_SCRAP.getCode();
        else return BicycleStatusEnum.UNKNOWN.getCode();
    }

    /**
     * 完成任务后计算车辆状态
     *
     * @param type 任务待类型
     * @return 车辆的状态
     */
    private int calculateStatusAfterDone(int type) {
        if (type == TaskTypeEnum.DISABLE.getCode()) return BicycleStatusEnum.WAIT_DELETE.getCode();
        else return BicycleStatusEnum.UNUSED.getCode();
    }


}

