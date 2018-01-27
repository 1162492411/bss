package com.zhd.controller;

import com.zhd.pojo.Page;
import com.zhd.pojo.Supplier;
import com.zhd.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 供应商控制器
 * Created by Mo on 2017/9/23.
 */
@RestController
@RequestMapping("suppliers")
public class SupplierController {
    @Autowired
    private SupplierService supplierService;

    /**
     * 添加供应商信息
     * @param record  待添加的供应商信息
     * @return 添加后的供应商信息/null
     */
    @RequestMapping(method = RequestMethod.POST)
    public boolean add(@RequestBody Supplier record){
        return supplierService.insert(record);
    }

    /**
     * 删除供应商信息
     * @param record  待删除的供应商信息
     * @return 是否已删除供应商
     */
    @RequestMapping(method = RequestMethod.DELETE)
    public boolean delete(@RequestBody Supplier record){
        return supplierService.delete(record);
    }

    /**
     * 修改供应商信息
     * @param record  待修改的供应商信息
     * @return 修改后的供应商信息/null
     */
    @RequestMapping(method = RequestMethod.PUT)
    public boolean update(@RequestBody Supplier record){
        return supplierService.update(record);
    }

    /**
     * 默认-查看第一页供应商信息
     * @param record  指定条件的供应商
     * @param result 包含分页后的供应商信息的分页对象
     * @return 包含分页后的供应商信息的分页对象
     */
    @RequestMapping(method = RequestMethod.GET)
    public Page defaults(Supplier record, Page result){
        return suppliers(1,record,result);
    }

    /**
     * 分页查看供应商信息
     * @param page 前台传入的页数(从1开始)
     * @param record  指定条件的供应商
     * @param result 包含分页后的供应商信息的分页对象
     * @return 包含分页后的供应商信息的分页对象
     */
    @RequestMapping(value = "{page}", method = RequestMethod.GET)
    public Page suppliers(@PathVariable int page, Supplier record, Page result){
        System.out.println("接收到参数" + page + record);
        return result.setDatas(supplierService.selectSuppliers(result.setTotalCount(supplierService.selectCount(record)).setCurrentPage(page).getStart(),record)).setKeys(Supplier.getKeys()).setNames(Supplier.getNames());

    }

    /**
     * 返回所有供应商的简略信息
     * @param result 包含供应商信息的分页对象
     * @return 包含供应商信息的分页对象
     */
    @RequestMapping("/all")
    public Page all(Page result){
        return result.setDatas(supplierService.selectAll());
    }


}
