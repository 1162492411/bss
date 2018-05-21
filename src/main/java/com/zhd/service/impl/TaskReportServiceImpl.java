package com.zhd.service.impl;

import com.zhd.controller.ReportController;
import com.zhd.mapper.TaskMapper;
import com.zhd.mapper.TaskReportMapper;
import com.zhd.service.IAreaService;
import com.zhd.service.ICityService;
import com.zhd.service.ITaskReportService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by Mo on 2018/5/18.
 */
@Service
public class TaskReportServiceImpl implements ITaskReportService {
    private final static Logger logger = LoggerFactory.getLogger(TaskReportServiceImpl.class);
    @Autowired
    private TaskReportMapper taskReportMapper;
    @Autowired
    private ICityService cityService;
    @Autowired
    private IAreaService areaService;

    @Override
    public List<Map<String, Object>> countCount(String beginDay, String endDay, Integer cityId, Integer taskType, Integer groupType) {
        if(groupType == 1){
            return countByType(beginDay, endDay, cityId, taskType);
        }else if(groupType == 2){
            return countByCity(beginDay, endDay, cityId, taskType);
        }else{
            return Collections.EMPTY_LIST;
        }
    }

    private List<Map<String, Object>> countByType(String beginDay, String endDay, Integer cityId, Integer taskType) {
        List<Integer> areaIds = areaService.selectByCityIds(cityService.getAllChildren(cityId));
        if(CollectionUtils.isNotEmpty(areaIds)){
            return taskReportMapper.countByType(beginDay, endDay, areaIds, taskType);
        }else{
            return Collections.EMPTY_LIST;
        }
    }

    private List<Map<String, Object>> countByCity(String beginDay, String endDay, Integer cityId, Integer taskType) {
        List<Map<String, Object>> resultList = new ArrayList<>();
        List<Integer> cityIds = cityService.getNextLevelChildren(cityId);
        logger.debug("get cityIds -->" + cityIds);
        for (int i = 0; i < cityIds.size(); i++) {
            Map<String, Object> resultMap = new HashMap<>();
            int currentCityId = cityIds.get(i);
            String cityName = cityService.selectById(currentCityId).getName();
            List<Integer> childrenCity = cityService.getAllChildren(currentCityId);
            resultMap.put("countKey", cityName);
            if(CollectionUtils.isNotEmpty(childrenCity)){
                List<Integer> areas = areaService.selectByCityIds(childrenCity);
                if(CollectionUtils.isNotEmpty(areas)){
                    resultMap.put("countValue", taskReportMapper.countByCity(beginDay, endDay, areas, taskType));
                }else{
                    resultMap.put("countValue",0);
                }
            }else{
                resultMap.put("countValue",0);
            }
            resultList.add(resultMap);
        }
        logger.debug("will return countByCity data{}", resultList);
        return resultList;
    }

    @Override
    public List<Map<String, Object>> countByTime(String beginDay, String endDay, Integer cityId, Integer taskType) {
        List<Integer> areaIds = areaService.selectByCityIds(cityService.getAllChildren(cityId));
        if(CollectionUtils.isNotEmpty(areaIds)){
            return taskReportMapper.countByTime(beginDay, endDay, areaIds, taskType);
        }else{
            return Collections.EMPTY_LIST;
        }
    }
}
