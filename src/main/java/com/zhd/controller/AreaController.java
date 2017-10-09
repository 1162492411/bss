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
     * @param area 待添加的区域信息
     * @return 添加后的区域信息/null
     */
    @RequestMapping(method = RequestMethod.POST)
    public Area add(@RequestBody Area area){
        //TODO:此处需确定自增的key是否以添加到area中
        if(areaService.insert(area) > 0)
            return area;
        else return null;
    }

    /**
     * 删除区域信息
     * @param area 待删除的区域信息
     * @return 是否已删除区域
     */
    @RequestMapping(method = RequestMethod.DELETE)
    public boolean delete(@RequestBody Area area){
        return areaService.delete(area);
    }

    /**
     * 修改区域信息
     * @param area 待修改的区域信息
     * @return 修改后的区域信息/null
     */
    @RequestMapping(method = RequestMethod.PUT)
    public Area update(@RequestBody Area area){
        if(areaService.update(area)) return area;
        else return null;
    }

    /**
     * 默认-查看第一页区域信息
     * @param area 指定条件的区域
     * @param result 包含分页后的区域信息的分页对象
     * @return 包含分页后的区域信息的分页对象
     */
    @RequestMapping(method = RequestMethod.GET)
    public Page defaults(Area area,Page result){
        return areas(1,area,result);
    }

    /**
     * 分页查看区域信息
     * @param page 前台传入的页数(从1开始)
     * @param area 指定条件的区域
     * @param result 包含分页后的区域信息的分页对象
     * @return 包含分页后的区域信息的分页对象
     */
    @RequestMapping(value = "{page}", method = RequestMethod.GET)
    public Page areas(@PathVariable int page, Area area, Page result){
        System.out.println("接收到参数" + page + area);
        return result.setDatas(areaService.selectAreas(result.setTotalCount(areaService.selectCount(area)).setCurrentPage(page).getStart(),area));
    }

}
