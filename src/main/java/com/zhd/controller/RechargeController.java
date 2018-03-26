package com.zhd.controller;


import com.alibaba.fastjson.util.TypeUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.zhd.convert.RechargeConvert;
import com.zhd.pojo.*;
import com.zhd.service.IRechargeService;
import com.zhd.service.IUserService;
import com.zhd.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

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

    /**
     * 查看充值记录
     * @param pageNum 要查看的页码
     * @param page 分页类
     * @param session session
     * @return
     */
    @GetMapping("list/{current}")
    public JSONResponse list(@PathVariable("current") int pageNum, Page<Recharge> page, HttpSession session) {
        try {
            if(pageNum <= 0) {
                throw new IllegalArgumentException(Constants.ILLEGAL_ARGUMENTS);
            }
            return renderSuccess(RechargeConvert.convertToVOPageInfo(rechargeService.selectPage(page, new EntityWrapper<Recharge>().eq("user_id", String.valueOf(session.getAttribute("userid"))))));
        } catch (Exception e) {
            return renderError(e.getMessage());
        }
    }

    /**
     * 充值账户余额
     * @param record 充值信息
     * @param bindingResult
     * @return 充值后的提示信息
     */
    @PostMapping
    public JSONResponse insert(@RequestBody @Validated(Recharge.Insert.class)Recharge record, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                return renderError(bindingResult.getFieldError().getDefaultMessage());
            } else {
                record.setRechargeTime(TypeUtils.castToString(System.currentTimeMillis()));
                return rechargeService.recharge(record) ? renderSuccess() : renderError();
            }
        } catch (Exception e) {
            return renderError(e.getMessage());
        }
    }

}

