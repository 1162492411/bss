package com.zhd.controller;

import com.alibaba.fastjson.util.TypeUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.zhd.convert.ApplyConvert;
import com.zhd.convert.AreaConvert;
import com.zhd.enums.ApplyTypeEnum;
import com.zhd.exceptions.NoSuchUserException;
import com.zhd.exceptions.NotLoginException;
import com.zhd.pojo.Apply;
import com.zhd.pojo.Area;
import com.zhd.pojo.JSONResponse;
import com.zhd.pojo.User;
import com.zhd.service.IApplyService;
import com.zhd.service.IUserService;
import com.zhd.util.Constants;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <p>
 * 申请表 前端控制器
 * </p>
 *
 * @author zyg
 * @since 2018-04-10
 */
@Controller
@RequestMapping("apply")
public class ApplyController extends BaseController {

    @Autowired
    private IApplyService applyService;
    @Autowired
    private IUserService userService;

    @PostMapping
    public JSONResponse insert(@Validated(Apply.Insert.class) Apply apply, BindingResult bindingResult, HttpSession session){
        try {
            if (bindingResult.hasErrors()) {
                return renderError(bindingResult.getFieldError().getDefaultMessage());
            } else {
                String userid = String.valueOf(session.getAttribute("userid"));
                if (StringUtils.isBlank(userid)) {
                    throw new NotLoginException();
                }
                apply.setUserId(userid);
                apply.setStartTime(TypeUtils.castToString(System.currentTimeMillis()));
                List<User> allStaffs = userService.getAllStaff();
                apply.setOperatorId(allStaffs.get(RandomUtils.nextInt(0, allStaffs.size())).getId());
                return applyService.insert(apply) ? renderSuccess(apply) : renderError();
            }
        } catch (Exception e) {
            return renderError(e.getMessage());
        }
    }
    @PutMapping
    public JSONResponse done(@Validated(Apply.Update.class) Apply apply, BindingResult bindingResult, HttpSession session){
        try{
            if(bindingResult.hasErrors()){
                return renderError(bindingResult.getFieldError().getDefaultMessage());
            }else{
                String userid = String.valueOf(session.getAttribute("userid"));
                if (StringUtils.isBlank(userid)) {
                    throw new NotLoginException();
                }
                if(userService.isAdmin(userid)){
                    if(apply.getType().equals(ApplyTypeEnum.REFUND_DEPOSIT.getCode())){
                        return userService.refundDeposit(apply.getUserId(), Constants.STANDARD_DEPOSIT) ? renderSuccess(Constants.TIP_REFUND_DEPOSIT_SUCCESS) : renderError(Constants.TIP_REFUND_DEPOSIT_ERROR);
                    }else if(apply.getType().equals(ApplyTypeEnum.REFUND_ACCOUNT.getCode())){
                        return userService.reduceAccount(apply.getUserId(),apply.getAmount()) ? renderSuccess(Constants.TIP_REFUND_ACCOUNT_SUCCESS) : renderError(Constants.TIP_REFUND_ACCOUNT_ERROR);
                    }else{
                        return renderError("申请类型不正确");
                    }
                }else{
                    return renderError(Constants.TIP_NO_PERMISSION);
                }
            }
        }catch (Exception e){
            return renderError();
        }
    }

    @GetMapping("list/{current}")
    public JSONResponse oldList(@PathVariable("current") int pageNum, Page<Apply> page) {
        try {
            if(pageNum <= 0){
                throw new IllegalArgumentException(Constants.ILLEGAL_ARGUMENTS);
            }
            return renderSuccess(ApplyConvert.convertToVOPageInfo(applyService.selectPage(page, new EntityWrapper<Apply>().orderBy("status"))));
        } catch (Exception e) {
            return renderError(e.getMessage());
        }
    }



    //todo : 查看申请列表

}
