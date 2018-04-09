package com.zhd.service.impl;

import com.zhd.mapper.JourneyReportMapper;
import com.zhd.service.ICityService;
import com.zhd.service.IJourneyReportService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class JourneyReportServiceImpl implements IJourneyReportService {

    @Autowired
    private JourneyReportMapper journeyReportMapper;
    @Autowired
    private ICityService cityService;

    @Override
    public List<Map<String, Object>> countUseCount(String begin, String end, Integer cityId, Integer timeType) {
        switch(timeType){
            case 0 :
                return journeyReportMapper.countByHour(begin, end, cityService.getAllChildren(cityId));
            case 1 :
                return journeyReportMapper.countByDay(begin, end, cityService.getAllChildren(cityId));
            case 2 :
                return journeyReportMapper.countByMonth(begin,end, cityService.getAllChildren(cityId));
            default :
                return Collections.EMPTY_LIST;
        }
    }

    @Override
    public List<Map<String, Object>> countRideTime(String begin, String end, Integer cityId) {
        return journeyReportMapper.countRideTime(begin, end, cityService.getAllChildren(cityId));
    }

    @Override
    public List<Map<String, Object>> countRideDistance(String begin, String end, Integer cityId) {
        return journeyReportMapper.countRideDistance(begin,end, cityService.getAllChildren(cityId));
    }

    @Override
    public List<Map<String, Object>> countFlow(String begin, String end, Integer cityId, Integer timeType) {
        switch(timeType){
            case 0 :
                return journeyReportMapper.countFlowByHour(begin, end, cityService.getAllChildren(cityId));
            case 1 :
                return journeyReportMapper.countFlowByDay(begin, end, cityService.getAllChildren(cityId));
            case 2 :
                return journeyReportMapper.countFlowByMonth(begin, end, cityService.getAllChildren(cityId));
            default :
                return Collections.EMPTY_LIST;
        }
    }


}
