package com.zhd.controller;


import com.alibaba.fastjson.util.TypeUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.zhd.convert.BicycleConvert;
import com.zhd.enums.BicycleStatusEnum;
import com.zhd.enums.BicycleTypeEnum;
import com.zhd.enums.JourneyStatusEnum;
import com.zhd.exceptions.NotLoginException;
import com.zhd.pojo.*;
import com.zhd.service.*;
import com.zhd.util.Constants;
import com.zhd.util.ConsumptionUtil;
import com.zhd.util.LocationUtils;
import com.zhd.util.DataUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
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
@RequestMapping("/bicycles")
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

    @GetMapping("all")
    public JSONResponse all(){
        try{
            return renderSuccess(BicycleConvert.convertSimple(bicycleService.selectAllSimple()));
        }catch (Exception e){
            return renderError(e.getMessage());
        }
    }

    @GetMapping("list/{keyword}/{current}")
    public JSONResponse searchList(@PathVariable("keyword")String keyword,  @PathVariable("current") int pageNum, Page<Bicycle> page) {
        try {
            if(pageNum <= 0) {
                throw new IllegalArgumentException(Constants.ILLEGAL_ARGUMENTS);
            }
            if(StringUtils.isNotBlank(keyword)){
                //按车辆状态查询
                int resultStatus = BicycleStatusEnum.getByStatus(keyword);
                if(resultStatus > 0){
                    page = bicycleService.selectPage(page, new EntityWrapper<Bicycle>().eq("status", resultStatus).orderBy("status", false));
                }
                //按车辆类型查询
                int resultType = BicycleTypeEnum.getByType(keyword);
                if(resultType > 0){
                    page = bicycleService.selectPage(page, new EntityWrapper<Bicycle>().eq("type", resultType).orderBy("status", false));
                }
                //按供应商查询
                Supplier searchSupplier = supplierService.selectOne(new EntityWrapper<Supplier>().like("name", keyword).or().like("brand", keyword));
                if(searchSupplier != null){
                    page = bicycleService.selectPage(page, new EntityWrapper<Bicycle>().eq("supplier", searchSupplier.getId()).orderBy("status", false));
                }
            }else{
                page = bicycleService.selectPage(page, new EntityWrapper<Bicycle>().like("id", keyword).orderBy("status", false));
            }
            List<Supplier> supplierList = supplierService.selectList(null);
            return renderSuccess(BicycleConvert.convertToVOPageInfo(page,supplierList));
        } catch (Exception e) {
            return renderError(e.getMessage());
        }
    }

    @GetMapping("list/{current}")
    public JSONResponse list(@PathVariable("current") Integer pageNum, Page<Bicycle> page) {
        try {
            if(pageNum <= 0) {
                throw new IllegalArgumentException(Constants.ILLEGAL_ARGUMENTS);
            }
            page = bicycleService.selectPage(page, new EntityWrapper<Bicycle>().orderBy("status", false));
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
                record.setInvestmentTime(TypeUtils.castToString(System.currentTimeMillis() / 1000));
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

    @DeleteMapping("")
    public JSONResponse delete(@RequestBody @Validated(Bicycle.Delete.class) Bicycle record, BindingResult bindingResult){
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
    public JSONResponse borrowBicycle(Bicycle borrowBicycle, HttpSession session){
        System.out.println("borrowBicyle is-->" + borrowBicycle);
        try{
            //check user && deposit
            String userid = String.valueOf(session.getAttribute("userid"));
            if(Constants.NULL_USER_ID.equals(userid)) {
                throw new NotLoginException();
            }
            //检测是否存在未结束行程
            if(journeyService.getContinuedJourneys(userid).size() > 0) {
                return renderError(Constants.TIP_HAS_NO_END_JOURNEY);
            }
            Journey journey = bicycleService.borrowBicycle(borrowBicycle, userid);
            return journey != null ? renderSuccess(journey) : renderError(Constants.TIP_BORROW_BICYCLE_ERROR);
        }catch (Exception e){
            return renderError(e.getMessage());
        }
    }

    @RequestMapping("return/{bicycleid}")
    public JSONResponse returnBicycle(@RequestBody @Validated(Journey.Update.class) Journey journey, HttpSession session){
        try{
            //check user
            String userid = String.valueOf(session.getAttribute("userid"));
            if(Constants.NULL_USER_ID.equals(userid)){
                throw new NotLoginException();
            }
            User user = userService.findUser(userid);
            //行程的原始信息从数据库中查询，保证安全.若存在多个未结束行程，则选取第一个，即离当前时间最近的行程
            Journey formerJourney = journeyService.getContinuedJourneys(userid).get(0);
            journey.setId(formerJourney.getId());
            journey.setBicycleId(formerJourney.getBicycleId());
            journey.setStartTime(formerJourney.getStartTime());
            //prepare returnBicycle
            Area area = areaService.findArea(journey.getEndLocationX(),journey.getEndLocationY());
            journey.setEndArea(area== null? area.getId() : -1);
            long startTime = Long.parseLong(formerJourney.getStartTime());
            long endTime = Long.parseLong(journey.getEndTime());
            journey.setUserId(userid);
            long rideTimeValue = endTime - startTime;
            journey.setRideTime(rideTimeValue);
            journey.setPath(DataUtil.generateRandomPath(formerJourney.getStartLocationX().doubleValue(),formerJourney.getStartLocationY().doubleValue(),journey.getEndLocationX().doubleValue(), journey.getEndLocationY().doubleValue()));
            journey.setDistance(LocationUtils.getJourneyDistance(journey.getPath()));
            journey.setAmount(ConsumptionUtil.calculate(journey.getRideTime(), area == null ? -1 : area.getType(), user.getMonthlyTime()));
            journey.setStatus(JourneyStatusEnum.END.getCode());
            //returnBicycle
            bicycleService.returnBicycle(userid,journey);
            return renderSuccess(Constants.TIP_RETURN_BICYCLE_SUCCESS);
        }catch (Exception e){
            return renderError(e.getMessage());
        }
    }


}

