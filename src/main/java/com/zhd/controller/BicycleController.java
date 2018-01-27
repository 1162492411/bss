package com.zhd.controller;

import com.zhd.pojo.Page;
import com.zhd.pojo.Bicycle;
import com.zhd.service.BicycleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 车辆控制器
 * Created by Mo on 2017/9/23.
 */
@RestController
@RequestMapping("bicycles")
public class BicycleController {
    @Autowired
    private BicycleService bicycleService;
    
    /**
     * 添加车辆信息
     * @param record 待添加的车辆信息
     * @return 是否成功添加车辆信息
     */
    @RequestMapping(method = RequestMethod.POST)
    public boolean add(@RequestBody Bicycle record){
        return bicycleService.insert(record);
    }

    /**
     * 删除车辆信息
     * @param record 待删除的车辆信息
     * @return 是否已删除车辆
     */
    @RequestMapping(method = RequestMethod.DELETE)
    public boolean delete(@RequestBody Bicycle record){
        return bicycleService.delete(record);
    }

    /**
     * 修改车辆信息
     * @param record 待修改的车辆信息
     * @return 修改后的车辆信息/null
     */
    @RequestMapping(method = RequestMethod.PUT)
    public boolean update(@RequestBody Bicycle record){
        return bicycleService.update(record);
    }

    /**
     * 默认-查看第一页车辆信息
     * @param record 指定条件的车辆
     * @param result 包含分页后的车辆信息的分页对象
     * @return 包含分页后的车辆信息的分页对象
     */
    @RequestMapping(method = RequestMethod.GET)
    public Page defaults(Bicycle record, Page result){
        return Bicycles(1,record,result);
    }

    /**
     * 分页查看车辆信息
     * @param page 前台传入的页数(从1开始)
     * @param record 指定条件的车辆
     * @param result 包含分页后的车辆信息的分页对象
     * @return 包含分页后的车辆信息的分页对象
     */
    @RequestMapping(value = "{page}", method = RequestMethod.GET)
    public Page Bicycles(@PathVariable int page, Bicycle record, Page result){
        return result.setDatas(bicycleService.selectBicycles(result.setTotalCount(bicycleService.selectCount(record)).setCurrentPage(page).getStart(),record)).setKeys(Bicycle.getKeys()).setNames(Bicycle.getNames());
    }

    @RequestMapping("all")
    public Page AllSimpleBicycles(Page result){
        return result.setDatas(bicycleService.selectAll());
    }


}
