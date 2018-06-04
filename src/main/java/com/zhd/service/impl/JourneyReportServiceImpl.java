package com.zhd.service.impl;

import com.zhd.mapper.JourneyReportMapper;
import com.zhd.service.IAreaService;
import com.zhd.service.ICityService;
import com.zhd.service.IJourneyReportService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class JourneyReportServiceImpl implements IJourneyReportService {

    @Autowired
    private JourneyReportMapper journeyReportMapper;
    @Autowired
    private ICityService cityService;
    @Autowired
    private IAreaService areaService;

    @Override
    public List<Map<String, Object>> countUseCount(String begin, String end, Integer cityId, Integer timeType) {
        switch (timeType) {
            case 0:
                return journeyReportMapper.countByHour(begin, end, areaService.selectByCityIds(cityService.getAllChildren(cityId)));
            case 1:
                return journeyReportMapper.countByDay(begin, end, areaService.selectByCityIds(cityService.getAllChildren(cityId)));
            case 2:
                return journeyReportMapper.countByMonth(begin, end, areaService.selectByCityIds(cityService.getAllChildren(cityId)));
            case 3 :
                return countUseCountByCity(begin, end, cityId);
            default:
                return Collections.EMPTY_LIST;
        }
    }

    @Override
    public List<Map<String, Object>> countUseCountByCity(String beginDay, String endDay, Integer cityId) {
        List<Map<String, Object>> resultList = new ArrayList<>();
        List<Integer> cityIds = cityService.getNextLevelChildren(cityId);
        for (int i = 0; i < cityIds.size(); i++) {
            Map<String, Object> resultMap = new HashMap<>();
            int currentCityId = cityIds.get(i);
            String cityName = cityService.selectById(currentCityId).getName();
            List<Integer> childrenCity = cityService.getAllChildren(currentCityId);
            resultMap.put("countKey", cityName);
            if(CollectionUtils.isNotEmpty(childrenCity)){
                System.out.println("childrenCity--" + childrenCity);
                List<Integer> areas = areaService.selectByCityIds(childrenCity);
                if(CollectionUtils.isNotEmpty(areas)){
                    resultMap.put("countValue", journeyReportMapper.countByCity(beginDay, endDay, areas));
                }else{
                    resultMap.put("countValue",0);
                }
            }else{
                resultMap.put("countValue",0);
            }
            resultList.add(resultMap);
        }
        return resultList;
    }

    @Override
    public List<Map<String, Object>> countRideTime(String begin, String end, Integer cityId) {
        return journeyReportMapper.countRideTime(begin, end, areaService.selectByCityIds(cityService.getAllChildren(cityId)));
    }

    @Override
    public List<Map<String, Object>> countRideDistance(String begin, String end, Integer cityId) {
        return journeyReportMapper.countRideDistance(begin, end, areaService.selectByCityIds(cityService.getAllChildren(cityId)));
    }

    @Override
    public List<Map<String, Object>> countFlow(String begin, String end, Integer cityId, Integer timeType) {
        switch (timeType) {
            case 0:
                return journeyReportMapper.countFlowByHour(begin, end, areaService.selectByCityIds(cityService.getAllChildren(cityId)));
            case 1:
                return journeyReportMapper.countFlowByDay(begin, end, areaService.selectByCityIds(cityService.getAllChildren(cityId)));
            case 2:
                return journeyReportMapper.countFlowByMonth(begin, end, areaService.selectByCityIds(cityService.getAllChildren(cityId)));
            default:
                return Collections.EMPTY_LIST;
        }
    }

}
