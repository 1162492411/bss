package com.zhd.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.zhd.convert.AreaConvert;
import com.zhd.pojo.Area;
import com.zhd.pojo.JSONResponse;
import com.zhd.service.IAreaService;
import com.zhd.util.Constants;
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
@RequestMapping("/area")
public class AreaController extends BaseController {

    @Autowired
    private IAreaService areaService;

    @GetMapping("{id}")
    public JSONResponse get(Area record){
        try{
            return renderSuccess(AreaConvert.convertToVO(areaService.selectById(record.getId())));
        }catch (Exception e){
            return renderError(e.getMessage());
        }
    }

    @GetMapping("list/{current}")
    public JSONResponse list(@PathVariable("current") int pageNum, Page<Area> page) {
        try {
            if(pageNum <= 0) throw new IllegalArgumentException(Constants.ILLEGAL_ARGUMENTS);
            return renderSuccess(AreaConvert.convertToVOPageInfo(areaService.selectPage(page)));
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

    @DeleteMapping("{id}")
    public JSONResponse delete(@Validated(Area.Delete.class) Area record, BindingResult bindingResult){
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

//todo:insert update delete后一般是否需要将数据返回给前台？？返回前台传入的原始数据还是包装后的数据(如AreaVO)
