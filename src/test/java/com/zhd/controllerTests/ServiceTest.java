package com.zhd.controllerTests;

import com.zhd.BssTestEnvironment;
import com.zhd.convert.JourneyReportConvert;
import com.zhd.mapper.JourneyReportMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ServiceTest extends BssTestEnvironment {


    @Test
    public void test(){
//        System.out.println(JourneyReportConvert.convertOverviewByHour(journeyReportMapper.countByHour("2017-06-01","2017-06-15",0)));
        System.out.println(journeyReportService.countByHour("2017-01-01","2018-01-01",0));

    }

    //todo : 测试转换后的数据能否在前台被显示


}
