package com.zhd.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.zhd.convert.AreaConvert;
import com.zhd.enums.AreaTypeEnum;
import com.zhd.pojo.Area;
import com.zhd.pojo.JSONResponse;
import com.zhd.service.IAreaService;
import com.zhd.util.Constants;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 区域表 前端控制器
 * </p>
 *
 * @author zyg
 * @since 2018-02-05
 */
@RestController
@RequestMapping("/areas")
public class AreaController extends BaseController {

    @Autowired
    private IAreaService areaService;

    @GetMapping("list/{keyword}/{current}")
    public JSONResponse searchList(@PathVariable("keyword")String keyword,  @PathVariable("current") int pageNum, Page<Area> page) {
        System.out.println("searchAreaList-->" + keyword + pageNum);
        try {
            if(pageNum <= 0){
                throw new IllegalArgumentException(Constants.ILLEGAL_ARGUMENTS);
            }
            if(StringUtils.isNotBlank(keyword)){
                EntityWrapper<Area> entityWrapper = new EntityWrapper<>();
                int resultType = AreaTypeEnum.getByType(keyword);
                if(resultType != -1){
                    entityWrapper.eq("type", resultType);
                }else{
                    entityWrapper.like("name", keyword);
                }
                return renderSuccess(AreaConvert.convertToVOPageInfo(areaService.selectPage(page, entityWrapper.orderBy("type"))));
            }
            else{
                return renderSuccess(AreaConvert.convertToVOPageInfo(areaService.selectPage(page, new EntityWrapper<Area>().orderBy("type"))));
            }
        } catch (Exception e) {
            return renderError(e.getMessage());
        }
    }

    @GetMapping("list/{current}")
    public JSONResponse oldList(@PathVariable("current") int pageNum, Page<Area> page) {
        try {
            if(pageNum <= 0){
                throw new IllegalArgumentException(Constants.ILLEGAL_ARGUMENTS);
            }
            return renderSuccess(AreaConvert.convertToVOPageInfo(areaService.selectPage(page, new EntityWrapper<Area>().orderBy("type"))));
        } catch (Exception e) {
            return renderError(e.getMessage());
        }
    }

    @PostMapping
    public JSONResponse insert(@RequestBody @Validated(Area.Insert.class)Area record, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                return renderError(bindingResult.getFieldError().getDefaultMessage());
            } else {
                 return areaService.insert(record) ? renderSuccess(record) : renderError();
            }
        } catch (Exception e) {
            return renderError(e.getMessage());
        }
    }

    @PutMapping
    public JSONResponse update(@RequestBody @Validated(Area.Update.class) Area record, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                return renderError(bindingResult.getFieldError().getDefaultMessage());
            } else {
                return areaService.updateById(record) ? renderSuccess(record) : renderError();
            }
        } catch (Exception e) {
            return renderError(e.getMessage());
        }
    }

    @DeleteMapping("")
    public JSONResponse delete(@RequestBody @Validated(Area.Delete.class) Area record, BindingResult bindingResult){
        try{
            if(bindingResult.hasErrors()){
                return renderError(bindingResult.getFieldError().getDefaultMessage());
            }
            else if( (record = areaService.selectById(record.getId()) )  != null){
                return areaService.deleteById(record.getId()) ? renderSuccess(record) : renderError();
            }
            else{
                return renderError(Constants.TIP_EMPTY_DATA);
            }
        }catch (Exception e){
            return renderError(e.getMessage());
        }
    }

}

