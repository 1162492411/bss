package com.zhd.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.zhd.convert.BicycleConvert;
import com.zhd.pojo.Bicycle;
import com.zhd.pojo.JSONResponse;
import com.zhd.pojo.Supplier;
import com.zhd.service.IBicycleService;
import com.zhd.service.ISupplierService;
import com.zhd.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * <p>
 * 车辆表 前端控制器
 * </p>
 *
 * @author zyg
 * @since 2018-02-05
 */
@RestController
@RequestMapping("/bicycle")
public class BicycleController extends BaseController{

    @Autowired
    private IBicycleService bicycleService;
    @Autowired
    private ISupplierService supplierService;

    @GetMapping("{id}")
    public JSONResponse get(Bicycle record){
        try{
            record = bicycleService.selectById(record.getId());
            Supplier supplier = supplierService.selectById(record.getSId());
            return renderSuccess(BicycleConvert.convertToVO(record, supplier));
        }catch (Exception e){
            return renderError(e.getMessage());
        }
    }

    @GetMapping("list/{current}")
    public JSONResponse list(@PathVariable("current") Integer pageNum, Page<Bicycle> page) {
        try {
            if(pageNum <= 0) throw new IllegalArgumentException(Constants.ILLEGAL_ARGUMENTS);
            page = bicycleService.selectPage(page);
            List<Supplier> supplierList = supplierService.selectList(null);
            return renderSuccess(BicycleConvert.convertToVOPageInfo(page,supplierList));
        } catch (Exception e) {
            return renderError(e.getMessage());
        }
    }

    @PostMapping
    public JSONResponse insert(@RequestBody @Validated(Supplier.Insert.class) Supplier record, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                return renderError(bindingResult.getFieldError().getDefaultMessage());
            } else {
                return supplierService.insert(record) ? renderSuccess(record) : renderError();
            }
        } catch (Exception e) {
            return renderError(e.getMessage());
        }
    }

    @PutMapping
    public JSONResponse update(@RequestBody @Validated(Supplier.Update.class) Supplier record, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                return renderError(bindingResult.getFieldError().getDefaultMessage());
            } else {
                return supplierService.updateById(record) ? renderSuccess(record) : renderError();
            }
        } catch (Exception e) {
            return renderError(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public JSONResponse delete(@Validated(Supplier.Delete.class) Supplier record, BindingResult bindingResult){
        try{
            if(bindingResult.hasErrors()){
                return renderError(bindingResult.getFieldError().getDefaultMessage());
            }
            else if( (record = supplierService.selectById(record.getId())) != null){
                return supplierService.deleteById(record.getId()) ? renderSuccess(record) : renderError();
            }
            else{
                return renderError(Constants.TIP_EMPTY_DATA);
            }
        }catch (Exception e){
            return renderError(e.getMessage());
        }
    }




}

