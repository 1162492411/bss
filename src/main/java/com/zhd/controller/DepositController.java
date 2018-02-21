package com.zhd.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.zhd.convert.AreaConvert;
import com.zhd.convert.DepositConvert;
import com.zhd.pojo.Area;
import com.zhd.pojo.BaseModel;
import com.zhd.pojo.Deposit;
import com.zhd.pojo.JSONResponse;
import com.zhd.service.IDepositService;
import com.zhd.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

/**
 * <p>
 * 押金记录表 前端控制器
 * </p>
 *
 * @author zyg
 * @since 2018-02-05
 */
@RestController
@RequestMapping("/deposit")
public class DepositController extends BaseController{

    @Autowired
    private IDepositService depositService;

    @GetMapping("{id}")
    public JSONResponse get(Area record){
        try{
            return renderSuccess(DepositConvert.convertToVO(depositService.selectById(record.getId())));
        }catch (Exception e){
            return renderError(e.getMessage());
        }
    }

    @GetMapping("list/{current}")
    public JSONResponse list(@PathVariable("current") int pageNum, Page<Deposit> page) {
        try {
            if(pageNum <= 0) throw new IllegalArgumentException(Constants.ILLEGAL_ARGUMENTS);
            return renderSuccess(DepositConvert.convertToVOPageInfo(depositService.selectPage(page)));
        } catch (Exception e) {
            return renderError(e.getMessage());
        }
    }

    @PostMapping
    public JSONResponse insert(@RequestBody @Validated(Deposit.Insert.class)Deposit deposit, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                return renderError(bindingResult.getFieldError().getDefaultMessage());
            } else {
                return depositService.insert(deposit) ? renderSuccess(deposit) : renderError();
            }
        } catch (Exception e) {
            return renderError(e.getMessage());
        }
    }

    @PutMapping
    public JSONResponse update(@RequestBody @Validated(Deposit.Update.class)Deposit deposit, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                return renderError(bindingResult.getFieldError().getDefaultMessage());
            } else {
                return depositService.updateById(deposit) ? renderSuccess(deposit) : renderError();
            }
        } catch (Exception e) {
            return renderError(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public JSONResponse delete(@Validated(Deposit.Delete.class)Deposit deposit, BindingResult bindingResult){
        try{
            if(bindingResult.hasErrors()){
                return renderError(bindingResult.getFieldError().getDefaultMessage());
            }
            else if( (deposit = depositService.selectById(deposit.getId()) )  != null){
                return depositService.deleteById(deposit.getId()) ? renderSuccess(deposit) : renderError();
            }
            else{
                return renderError(Constants.TIP_EMPTY_DATA);
            }
        }catch (Exception e){
            return renderError(e.getMessage());
        }
    }


}

