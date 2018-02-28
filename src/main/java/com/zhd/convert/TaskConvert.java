package com.zhd.convert;

import com.baomidou.mybatisplus.plugins.Page;
import com.zhd.enums.TaskStatusEnum;
import com.zhd.enums.TaskTypeEnum;
import com.zhd.pojo.PageInfo;
import com.zhd.pojo.Task;
import com.zhd.util.Constants;
import com.zhd.util.PageUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 任务转换器
 */
public class TaskConvert {

    public static Map convertToVO(Task task) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if(task == null) throw new NullPointerException(Constants.TIP_EMPTY_DATA);
        Map map = BeanUtils.describe(task);
        map.remove("class");
        map.put("type", TaskTypeEnum.getByCode(task.getType()));
        map.put("status", TaskStatusEnum.getByCode(task.getStatus()));
        return map;
    }

    public static PageInfo<Map> convertToVOPageInfo(Page<Task> taskPage) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if(taskPage == null || CollectionUtils.isEmpty(taskPage.getRecords())) throw new NullPointerException(Constants.TIP_EMPTY_DATA);
        PageInfo<Map> resultPage = new PageInfo<>();
        List<Map> resultList = new ArrayList<>();
        for (Task task : taskPage.getRecords() ) {
            resultList.add(convertToVO(task));
        }
        resultPage.setRecords(resultList);
        PageUtil.copyPage(taskPage, resultPage);
        resultPage.setKeys(Constants.TASK_KEYS);
        resultPage.setNames(Constants.TASK_NAMES);
        return resultPage;
    }

}
