package com.zhd.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.zhd.convert.AreaConvert;
import com.zhd.pojo.Area;
import com.zhd.pojo.BaseModel;
import com.zhd.pojo.JSONResponse;
import com.zhd.pojo.Journey;
import com.zhd.service.IJourneyService;
import com.zhd.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

/**
 * <p>
 * 行程记录表 前端控制器
 * </p>
 *
 * @author zyg
 * @since 2018-02-05
 */
@RestController
@RequestMapping("/journey")
public class JourneyController extends BaseController{

    @Autowired
    private IJourneyService journeyService;

    @GetMapping("{id}")
    public JSONResponse get(Journey record){
        try{
            return renderSuccess(journeyService.selectById(record.getId()));
        }catch (Exception e){
            return renderError(e.getMessage());
        }
    }

    @GetMapping("list/{current}")
    public JSONResponse list(@PathVariable("current") int pageNum, Page<Journey> page) {
        try {
            if(pageNum <= 0) throw new IllegalArgumentException(Constants.ILLEGAL_ARGUMENTS);
            return renderSuccess(journeyService.selectPage(page));
        } catch (Exception e) {
            return renderError(e.getMessage());
        }
    }

    @PostMapping
    public JSONResponse insert(@RequestBody @Validated(Journey.Insert.class)Journey record, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                return renderError(bindingResult.getFieldError().getDefaultMessage());
            } else {
                return journeyService.insert(record) ? renderSuccess(record) : renderError();
            }
        } catch (Exception e) {
            return renderError(e.getMessage());
        }
    }

    @PutMapping
    public JSONResponse update(@RequestBody @Validated(Journey.Update.class)Journey record, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                return renderError(bindingResult.getFieldError().getDefaultMessage());
            } else {
                return journeyService.updateById(record) ? renderSuccess(record) : renderError();
            }
        } catch (Exception e) {
            return renderError(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public JSONResponse delete(@Validated(Journey.Delete.class)Journey record, BindingResult bindingResult){
        try{
            if(bindingResult.hasErrors()){
                return renderError(bindingResult.getFieldError().getDefaultMessage());
            }
            else if( (record = journeyService.selectById(record.getId()) )  != null){
                return journeyService.deleteById(record.getId()) ? renderSuccess(record) : renderError();
            }
            else{
                return renderError(Constants.TIP_EMPTY_DATA);
            }
        }catch (Exception e){
            return renderError(e.getMessage());
        }
    }
}

