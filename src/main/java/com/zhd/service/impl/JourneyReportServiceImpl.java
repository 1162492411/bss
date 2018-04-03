package com.zhd.service.impl;

import com.zhd.mapper.JourneyReportMapper;
import com.zhd.service.ICityService;
import com.zhd.service.IJourneyReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class JourneyReportServiceImpl implements IJourneyReportService {

    @Autowired
    private JourneyReportMapper journeyReportMapper;
    @Autowired
    private ICityService cityService;

    @Override
    public List<Map<String, Object>> countByHour(String beginDay, String endDay, Integer cityId) {
        return journeyReportMapper.countByHour(beginDay, endDay, cityService.getAllChildren(cityId));
    }

    @Override
    public List<Map<String, Object>> countByDay(String beginDay, String endDay, Integer cityId) {
        return journeyReportMapper.countByDay(beginDay, endDay, cityService.getAllChildren(cityId));
    }

    @Override
    public List<Map<String, Object>> countByMonth(String beginMonth, String endMonth, Integer cityId) {
        return journeyReportMapper.countByMonth(beginMonth,endMonth, cityService.getAllChildren(cityId));
    }

    @Override
    public List<Map<String, Integer>> countByDistrictDay(int parentCode, String beginDay, String endDay) {
        return null;
    }

    @Override
    public List<Map<String, Integer>> countByDistrictMonth(int parentCode, String beginMonth, String endMonth) {
        return null;
    }
}
