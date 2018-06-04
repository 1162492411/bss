package com.zhd.controller;

import com.alibaba.fastjson.util.TypeUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.zhd.convert.DepositConvert;
import com.zhd.enums.DepositTypeEnum;
import com.zhd.exceptions.NotLoginException;
import com.zhd.pojo.Area;
import com.zhd.pojo.Deposit;
import com.zhd.pojo.JSONResponse;
import com.zhd.service.IDepositService;
import com.zhd.service.IUserService;
import com.zhd.util.Constants;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

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
public class DepositController extends BaseController {

    @Autowired
    private IDepositService depositService;
    @Autowired
    private IUserService userService;

    @GetMapping("{id}")
    public JSONResponse get(Area record) {
        try {
            return renderSuccess(DepositConvert.convertToVO(depositService.selectById(record.getId())));
        } catch (Exception e) {
            return renderError(e.getMessage());
        }
    }

    @GetMapping("list/{keyword}/{current}")
    public JSONResponse searchList(@PathVariable("keyword")String keyword, @PathVariable("current") int pageNum, Page<Deposit> page) {
        try {
            if (pageNum <= 0) {
                throw new IllegalArgumentException(Constants.ILLEGAL_ARGUMENTS);
            }
            if(StringUtils.isNotBlank(keyword)){
                int resultType = DepositTypeEnum.getByType(keyword);
                if(resultType != -1){
                    return renderSuccess(DepositConvert.convertToVOPageInfo(depositService.selectPage(page, new EntityWrapper<Deposit>().eq("type", resultType))));
                }
                return renderSuccess(DepositConvert.convertToVOPageInfo(depositService.selectPage(page, new EntityWrapper<Deposit>().like("user_id", keyword))));
            }else{
                return renderSuccess(DepositConvert.convertToVOPageInfo(depositService.selectPage(page)));
            }
        } catch (Exception e) {
            return renderError(e.getMessage());
        }
    }

    @GetMapping("list/{current}")
    public JSONResponse list(@PathVariable("current") int pageNum, Page<Deposit> page) {
        try {
            if (pageNum <= 0) {
                throw new IllegalArgumentException(Constants.ILLEGAL_ARGUMENTS);
            }
            return renderSuccess(DepositConvert.convertToVOPageInfo(depositService.selectPage(page)));
        } catch (Exception e) {
            return renderError(e.getMessage());
        }
    }

    /**
     * 操作押金--充值/提取
     * @param deposit 操作信息
     * @param bindingResult
     * @param session
     * @return
     */
    @PostMapping
    public JSONResponse operateDeposit(@RequestBody @Validated(Deposit.Insert.class) Deposit deposit, BindingResult bindingResult, HttpSession session) {
        try {
            if (bindingResult.hasErrors()) {
                return renderError(bindingResult.getFieldError().getDefaultMessage());
            } else {
                String userid = String.valueOf(session.getAttribute("userid"));
                if (StringUtils.isBlank(userid)) {
                    throw new NotLoginException();
                }
                deposit.setUserId(userid);
                deposit.setOperateTime(TypeUtils.castToString(System.currentTimeMillis() / 1000));
                if (DepositTypeEnum.IN.getCode() == deposit.getType()) {
                    //交押金
                    if(userService.selectById(userid).getDepositBalance().intValue() >=  Constants.STANDARD_DEPOSIT.intValue()){
                        return renderError(Constants.TIP_ENOUGH_DEPOSIT);
                    }
                    depositService.recharge(deposit);
                    return renderSuccess(deposit);
                } else if (DepositTypeEnum.OUT.getCode() == deposit.getType()) {
                    //取押金--该部分实际不再使用
                    if(userService.selectById(userid).getDepositBalance().intValue() <= 0){
                        return renderError(Constants.TIP_NO_DEPOSIT);
                    }
                    depositService.refund(deposit);
                    return renderSuccess(deposit);
                } else {
                    return renderError(Constants.ILLEGAL_ARGUMENTS);
                }
            }
        } catch (Exception e) {
            return renderError(e.getMessage());
        }
    }

}

