package com.zhd.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.util.TypeUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.zhd.convert.ApplyConvert;
import com.zhd.convert.AreaConvert;
import com.zhd.enums.ApplyStatusEnum;
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
@RestController
@RequestMapping("apply")
public class ApplyController extends BaseController {

    @Autowired
    private IApplyService applyService;
    @Autowired
    private IUserService userService;

    @PostMapping
    public JSONResponse insert(@RequestBody @Validated(Apply.Insert.class) Apply apply, BindingResult bindingResult, HttpSession session){
        System.out.println("receive apply-->" + JSON.toJSONString(apply));
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
    public JSONResponse done(@RequestBody @Validated(Apply.Update.class) Apply apply, BindingResult bindingResult, HttpSession session){
        try{
            if(bindingResult.hasErrors()){
                return renderError(bindingResult.getFieldError().getDefaultMessage());
            }else{
                String userid = String.valueOf(session.getAttribute("userid"));
                if (StringUtils.isBlank(userid)) {
                    throw new NotLoginException();
                }
                if(userService.isAdmin(userid) || userService.isStaff(userid)){
                    transType(apply);
                    return renderSuccess(applyService.doneApply(apply));
                }else{
                    return renderError(Constants.TIP_NO_PERMISSION);
                }
            }
        }catch (Exception e){
            return renderError();
        }
    }

    /**
     * 将前台传递的类型转化为int
     * @param apply
     */
    private void transType(Apply apply){
        String applyString = apply.getType();
        if(StringUtils.isBlank(applyString)){
            apply.setType(ApplyTypeEnum.UNKNOWN.getCode());
        }else if(ApplyTypeEnum.REFUND_DEPOSIT.getType().equals(applyString)){
            apply.setType(ApplyTypeEnum.REFUND_DEPOSIT.getCode());
        }else if(ApplyTypeEnum.REFUND_ACCOUNT.getType().equals(applyString)){
            apply.setType(ApplyTypeEnum.REFUND_ACCOUNT.getCode());
        }
    }

    @GetMapping("list/{keyword}/{current}")
    public JSONResponse searchList(@PathVariable("keyword")String keyword, @PathVariable("current") int pageNum, Page<Apply> page) {
        try {
            if(pageNum <= 0){
                throw new IllegalArgumentException(Constants.ILLEGAL_ARGUMENTS);
            }
            if(StringUtils.isNotBlank(keyword)){
                //按类型查询
                String resultType = ApplyTypeEnum.getByType(keyword);
                if(resultType != null){
                    return renderSuccess(ApplyConvert.convertToVOPageInfo(applyService.selectPage(page, new EntityWrapper<Apply>().eq("type", resultType).orderBy("status"))));
                }
                //按状态查询
                int resultStatus = ApplyStatusEnum.getByStatus(keyword);
                if(resultStatus != -1){
                    return renderSuccess(ApplyConvert.convertToVOPageInfo(applyService.selectPage(page, new EntityWrapper<Apply>().eq("status", resultStatus).orderBy("status"))));
                }
                //按用户/处理人查询
                return renderSuccess(ApplyConvert.convertToVOPageInfo(applyService.selectPage(page, new EntityWrapper<Apply>().like("user_id",keyword).or().like("operator_id", keyword).orderBy("status"))));
            }else{
                return renderSuccess(ApplyConvert.convertToVOPageInfo(applyService.selectPage(page, new EntityWrapper<Apply>().orderBy("status"))));
            }
        } catch (Exception e) {
            return renderError(e.getMessage());
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


}
