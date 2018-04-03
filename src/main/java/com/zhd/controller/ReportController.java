package com.zhd.controller;

import com.zhd.convert.JourneyReportConvert;
import com.zhd.pojo.JSONResponse;
import com.zhd.service.ICityService;
import com.zhd.service.IJourneyReportService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 * 报表控制器
 * @Author zyg
     */
@RestController
@RequestMapping("report")
public class ReportController extends BaseController {

    @Autowired
    private IJourneyReportService journeyReportService;
    @Autowired
    private ICityService cityService;

    @RequestMapping(value = "overview", method = RequestMethod.POST)
    public JSONResponse overview(@RequestBody Map<String,Object> params){
        try{
            Integer type = (Integer)params.get("type");
            Object cityIdValue = params.get("cityId");
            Integer cityId = 0;
            String cityName = "";
            if(cityIdValue != null){
                cityId = (Integer) cityIdValue;
                cityName = cityService.selectById(cityId).getName();
            }
            String startDate = String.valueOf(params.get("startDate"));
            String endDate = String.valueOf(params.get("endDate"));
            if(StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)){
                LocalDate start = LocalDate.parse(startDate, DateTimeFormatter.ISO_LOCAL_DATE);
                LocalDate end = LocalDate.parse(endDate, DateTimeFormatter.ISO_LOCAL_DATE);
                if(end.isAfter(start)){
                    switch(type){
                        case 0 : return renderSuccess(JourneyReportConvert.convertOverviewByHour(journeyReportService.countByHour(startDate,endDate,cityId),cityName));
                        case 1 : return renderSuccess(JourneyReportConvert.convertOverview(journeyReportService.countByDay(startDate,endDate,cityId),cityName));
                        case 2 : return renderSuccess(JourneyReportConvert.convertOverview(journeyReportService.countByMonth(startDate, endDate,cityId), cityName));
                        default: return renderError();
                    }
                }else{
                    return renderError();
                }
            }else{
                return renderError();
            }
        }catch (Exception e){
            return renderError(e.getMessage());
        }
    }


}
