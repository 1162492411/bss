package com.zhd.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.zhd.convert.BicycleConvert;
import com.zhd.enums.BicycleStatusEnum;
import com.zhd.exceptions.NoSuchBicycleException;
import com.zhd.exceptions.NotLoginException;
import com.zhd.exceptions.NotUseableBicycleException;
import com.zhd.pojo.*;
import com.zhd.service.*;
import com.zhd.util.Constants;
import com.zhd.util.ConsumptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.Instant;
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
    @Autowired
    private IUserService userService;
    @Autowired
    private IJourneyService journeyService;
    @Autowired
    private IAreaService areaService;

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
    public JSONResponse insert(@RequestBody @Validated(Bicycle.Insert.class) Bicycle record, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                return renderError(bindingResult.getFieldError().getDefaultMessage());
            } else {
                return bicycleService.insert(record) ? renderSuccess(record) : renderError();
            }
        } catch (Exception e) {
            return renderError(e.getMessage());
        }
    }

    @PutMapping
    public JSONResponse update(@RequestBody @Validated(Bicycle.Update.class) Bicycle record, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                return renderError(bindingResult.getFieldError().getDefaultMessage());
            } else {
                return bicycleService.updateById(record) ? renderSuccess(record) : renderError();
            }
        } catch (Exception e) {
            return renderError(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public JSONResponse delete(@Validated(Bicycle.Delete.class) Bicycle record, BindingResult bindingResult){
        try{
            if(bindingResult.hasErrors()){
                return renderError(bindingResult.getFieldError().getDefaultMessage());
            }
            else if( (record = bicycleService.selectById(record.getId())) != null){
                return bicycleService.deleteById(record.getId()) ? renderSuccess(record) : renderError();
            }
            else{
                return renderError(Constants.TIP_EMPTY_DATA);
            }
        }catch (Exception e){
            return renderError(e.getMessage());
        }
    }

    @RequestMapping("borrow/{id}")
    public JSONResponse borrowBicycle(Bicycle bicycle, HttpSession session){
        try{
            //check user && deposit
            String userid = String.valueOf(session.getAttribute("userid"));
            if(userid.equals("null")) throw new NotLoginException();
            userService.checkDeposit(userid);
            //check bicycle
            bicycle = bicycleService.selectById(bicycle.getId());
            if(bicycle == null) throw new NoSuchBicycleException();
            if(bicycle.getStatus() != BicycleStatusEnum.UNUSED.getCode()) throw new NotUseableBicycleException();
            //borrowBicycle
            bicycleService.borrowBicycle(bicycle.getId());
            Journey journey = Journey.builder().bId(bicycle.getId()).uId(userid).startTime(Instant.now().getEpochSecond()).build();
            journeyService.insert(journey);
            return renderSuccess(journey);
        }catch (Exception e){
            return renderError(e.getMessage());
        }
    }

    //不确定bicycleId取参时是否会自动注入到journey,不确定bicycle取参时路径中的名字与方法参数中的名字不同时是否需要为@PathVariable设值
    @RequestMapping("return/{bicycleId}")
    public JSONResponse returnBicycle(@PathVariable Integer bicycleId, @RequestBody Journey journey, HttpSession session){
        try{
            //check user
            String userid = String.valueOf(session.getAttribute("userid"));
            if(userid.equals("null")) throw new NotLoginException();
            User user = userService.findUser(userid);
            //prepare returnBicycle
            Area area = areaService.findArea(journey.getEndLocationX(),journey.getEndLocationY());
            journey.setRideTime(journey.getEndTime() - journey.getStartTime());
            journey.setAmount(ConsumptionUtil.calcute(journey.getRideTime(),area.getType(), user.getDates()));
            //returnBicycle
            bicycleService.returnBicycle(bicycleId);
            journeyService.updateById(journey);
        }catch (Exception e){
            renderError(e.getMessage());
        }
        return renderError();
    }


}

