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
import java.util.Arrays;
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
            Integer statisticalType = (Integer)params.get("statisticalType");
            Integer timeType = (Integer)params.get("timeType");
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
                    switch(statisticalType){
                        case 0 :
                            switch(timeType){
                                case 0 : return renderSuccess(JourneyReportConvert.convertOverviewByHour(journeyReportService.countByHour(startDate,endDate,cityId), Arrays.asList(cityName)));
                                case 1 : return renderSuccess(JourneyReportConvert.convertOverviewByDay(journeyReportService.countByDay(startDate, endDate, cityId),Arrays.asList(cityName), start ,end));
                                case 2 : return renderSuccess(JourneyReportConvert.convertOverviewByMonth(journeyReportService.countByMonth(startDate, endDate, cityId),Arrays.asList(cityName), start ,end));
                                default: return renderError();
                            }
                        case 1 :  return renderSuccess(JourneyReportConvert.convertRideTime(journeyReportService.countRideTime(startDate, endDate, cityId), Arrays.asList(cityName)));
                        case 2 : return renderSuccess(JourneyReportConvert.convertRideDistance(journeyReportService.countRideDistance(startDate,endDate, cityId), Arrays.asList(cityName)));
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
