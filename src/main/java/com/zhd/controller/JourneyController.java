package com.zhd.controller;

import com.zhd.pojo.Journey;
import com.zhd.pojo.Page;
import com.zhd.service.JourneyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 行程记录控制器
 * Created by Mo on 2017/9/23.
 */
@RestController
@RequestMapping("journeys")
public class JourneyController {
    @Autowired
    private JourneyService journeyService;

    /**
     * 添加行程信息
     * @param record  待添加的行程信息
     * @return 是否已添加行程
     */
    @RequestMapping(method = RequestMethod.POST)
    public boolean add(@RequestBody Journey record){
        return journeyService.insert(record);
    }

    /**
     * 删除行程信息
     * @param record  待删除的行程信息
     * @return 是否已删除行程
     */
    @RequestMapping(method = RequestMethod.DELETE)
    public boolean delete(@RequestBody Journey record){
        return journeyService.delete(record);
    }

    /**
     * 修改行程信息
     * @param record  待修改的行程信息
     * @return 是否已修改行程
     */
    @RequestMapping(method = RequestMethod.PUT)
    public boolean update(@RequestBody Journey record){
        return journeyService.update(record);
    }

    /**
     * 默认-查看第一页行程信息
     * @param record  指定条件的行程
     * @param result 包含分页后的行程信息的分页对象
     * @return 包含分页后的行程信息的分页对象
     */
    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody
    Page defaults(Journey record, Page result){
        return Journeys(1,record,result);
    }

    /**
     * 分页查看行程信息
     * @param page 前台传入的页数(从1开始)
     * @param record  指定条件的行程
     * @param result 包含分页后的行程信息的分页对象
     * @return 包含分页后的行程信息的分页对象
     */
    @RequestMapping(value = "{page}", method = RequestMethod.GET)
    public @ResponseBody Page Journeys(@PathVariable int page, Journey record, Page result){
        System.out.println("接收到参数" + page + record);
        return result.setDatas(journeyService.selectJourneys(result.setTotalCount(journeyService.selectCount(record)).setCurrentPage(page).getStart(),record)).setKeys(record.getKeys()).setNames(record.getNames());
    }
  

}
