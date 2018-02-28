package com.zhd.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.zhd.convert.SupplierConvert;
import com.zhd.pojo.*;
import com.zhd.service.ISupplierService;
import com.zhd.util.Constants;
import com.zhd.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 供应商表 前端控制器
 * </p>
 *
 * @author zyg
 * @since 2018-02-05
 */
@RestController
@RequestMapping("/suppliers")
public class SupplierController extends BaseController{
    @Autowired
    private ISupplierService supplierService;

    @GetMapping("list/{current}")
    public JSONResponse list(@PathVariable("current") Integer pageNum, Page<Supplier> page) {
        try {
            if(pageNum <= 0) throw new IllegalArgumentException(Constants.ILLEGAL_ARGUMENTS);
            return renderSuccess(SupplierConvert.convertToPageInfo(supplierService.selectPage(page)));
        } catch (Exception e) {
            return renderError(e.getMessage());
        }
    }

    @GetMapping("all")
    public JSONResponse all(){
        try{
            return renderSuccess(supplierService.selectAllSupplier());
        }catch (Exception e){
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

    @DeleteMapping("")
    public JSONResponse delete(@RequestBody @Validated(Supplier.Delete.class) Supplier record, BindingResult bindingResult){
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

