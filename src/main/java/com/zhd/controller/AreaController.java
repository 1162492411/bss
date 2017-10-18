package com.zhd.controller;

import com.zhd.pojo.Area;
import com.zhd.pojo.Page;
import com.zhd.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 区域控制器
 * Created by Mo on 2017/9/23.
 */
@RestController
@RequestMapping("areas")
public class AreaController {
    @Autowired
    private AreaService areaService;

    /**
     * 添加区域信息
     * @param record  待添加的区域信息
     * @return 添加后的区域信息/null
     */
    @RequestMapping(method = RequestMethod.POST)
    public boolean add(@RequestBody Area record){
        return areaService.insert(record);
    }

    /**
     * 删除区域信息
     * @param record  待删除的区域信息
     * @return 是否已删除区域
     */
    @RequestMapping(method = RequestMethod.DELETE)
    public boolean delete(@RequestBody Area record){
        return areaService.delete(record);
    }

    /**
     * 修改区域信息
     * @param record  待修改的区域信息
     * @return 修改后的区域信息/null
     */
    @RequestMapping(method = RequestMethod.PUT)
    public boolean update(@RequestBody Area record){
        return areaService.update(record);
    }

    /**
     * 默认-查看第一页区域信息
     * @param record  指定条件的区域
     * @param result 包含分页后的区域信息的分页对象
     * @return 包含分页后的区域信息的分页对象
     */
    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody Page defaults(Area record,Page result){
        return areas(1,record,result);
    }

    /**
     * 分页查看区域信息
     * @param page 前台传入的页数(从1开始)
     * @param record  指定条件的区域
     * @param result 包含分页后的区域信息的分页对象
     * @return 包含分页后的区域信息的分页对象
     */
    @RequestMapping(value = "{page}", method = RequestMethod.GET)
    public @ResponseBody Page areas(@PathVariable int page, Area record, Page result){
        System.out.println("接收到参数" + page + record);
        return result.setDatas(areaService.selectAreas(result.setTotalCount(areaService.selectCount(record)).setCurrentPage(page).getStart(),record)).setKeys(record.getKeys()).setNames(record.getNames());
    }

}
