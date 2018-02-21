package com.zhd.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.zhd.convert.AreaConvert;
import com.zhd.convert.RechargeConvert;
import com.zhd.pojo.Area;
import com.zhd.pojo.BaseModel;
import com.zhd.pojo.JSONResponse;
import com.zhd.pojo.Recharge;
import com.zhd.service.IAreaService;
import com.zhd.service.IRechargeService;
import com.zhd.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

/**
 * <p>
 * 充值记录表 前端控制器
 * </p>
 *
 * @author zyg
 * @since 2018-02-05
 */
@RestController
@RequestMapping("/recharge")
public class RechargeController extends BaseController{
    @Autowired
    private IRechargeService rechargeService;

    @GetMapping("{id}")
    public JSONResponse get(Recharge record){
        try{
            return renderSuccess(RechargeConvert.convertToVO(rechargeService.selectById(record.getId())));
        }catch (Exception e){
            return renderError(e.getMessage());
        }
    }

    @GetMapping("list/{current}")
    public JSONResponse list(@PathVariable("current") int pageNum, Page<Recharge> page) {
        try {
            if(pageNum <= 0) throw new IllegalArgumentException(Constants.ILLEGAL_ARGUMENTS);
            return renderSuccess(RechargeConvert.convertToVOPageInfo(rechargeService.selectPage(page)));
        } catch (Exception e) {
            return renderError(e.getMessage());
        }
    }

    @PostMapping
    public JSONResponse insert(@RequestBody @Validated(Recharge.Insert.class)Recharge record, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                return renderError(bindingResult.getFieldError().getDefaultMessage());
            } else {
                 return rechargeService.insert(record) ? renderSuccess(record) : renderError();
            }
        } catch (Exception e) {
            return renderError(e.getMessage());
        }
    }

    @PutMapping
    public JSONResponse update(@RequestBody @Validated(Recharge.Update.class)Recharge record, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                return renderError(bindingResult.getFieldError().getDefaultMessage());
            } else {
                return rechargeService.updateById(record) ? renderSuccess(record) : renderError();
            }
        } catch (Exception e) {
            return renderError(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public JSONResponse delete(@Validated(Recharge.Delete.class)Recharge record, BindingResult bindingResult){
        try{
            if(bindingResult.hasErrors()){
                return renderError(bindingResult.getFieldError().getDefaultMessage());
            }
            else if( (record = rechargeService.selectById(record.getId()) )  != null){
                return rechargeService.deleteById(record.getId()) ? renderSuccess(record) : renderError();
            }
            else{
                return renderError(Constants.TIP_EMPTY_DATA);
            }
        }catch (Exception e){
            return renderError(e.getMessage());
        }
    }


}

