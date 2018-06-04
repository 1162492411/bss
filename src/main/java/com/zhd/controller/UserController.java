package com.zhd.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.zhd.convert.UserConvert;
import com.zhd.enums.UserStatusEnum;
import com.zhd.enums.UserTypeEnum;
import com.zhd.exceptions.NotLoginException;
import com.zhd.pojo.JSONResponse;
import com.zhd.pojo.User;
import com.zhd.service.IUserService;
import com.zhd.util.Constants;
import jdk.nashorn.internal.runtime.logging.Logger;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author zyg
 * @since 2018-02-05
 */
@RestController
@RequestMapping("/users")
@Slf4j
public class UserController extends BaseController{
    @Autowired
    private IUserService userService;


    @GetMapping("list/{keyword}/{current}")
    public JSONResponse searchList(@PathVariable("keyword")String keyword, @PathVariable("current") int pageNum, Page<User> page) {
        try {
            if(pageNum <= 0) throw new IllegalArgumentException(Constants.ILLEGAL_ARGUMENTS);
            if(StringUtils.isNotBlank(keyword)){
                //按状态查询
                int resultStatus = UserStatusEnum.getByStatus(keyword);
                if(resultStatus > 0){
                    return renderSuccess(UserConvert.convertToVOPageInfo(userService.selectPage(page, new EntityWrapper<User>().setSqlSelect("id,name,type,status,monthly_time").eq("status", resultStatus).ne("type", UserTypeEnum.ADMIN.getCode()).orderBy("status", false))));
                }
                //按账户类型查询
                int resultType = UserTypeEnum.getByType(keyword);
                if(resultType > 0){
                    return renderSuccess(UserConvert.convertToVOPageInfo(userService.selectPage(page, new EntityWrapper<User>().setSqlSelect("id,name,type,status,monthly_time").eq("type", resultType).ne("type", UserTypeEnum.ADMIN.getCode()).orderBy("status", false))));
                }
                //按名称查询
                return renderSuccess(UserConvert.convertToVOPageInfo(userService.selectPage(page, new EntityWrapper<User>().setSqlSelect("id,name,type,status,monthly_time").like("name", keyword).ne("type", UserTypeEnum.ADMIN.getCode()).orderBy("status", false))));
            }else{
                return renderSuccess(UserConvert.convertToVOPageInfo(userService.selectPage(page, new EntityWrapper<User>().setSqlSelect("id,name,type,status,monthly_time").ne("type", UserTypeEnum.ADMIN.getCode()).orderBy("status", false))));
            }
        } catch (Exception e) {
            return renderError(e.getMessage());
        }
    }

    @GetMapping("list/{current}")
    public JSONResponse list(@PathVariable("current") int pageNum, Page<User> page) {
        try {
            if(pageNum <= 0) throw new IllegalArgumentException(Constants.ILLEGAL_ARGUMENTS);
            return renderSuccess(UserConvert.convertToVOPageInfo(userService.selectPage(page, new EntityWrapper<User>().setSqlSelect("id,name,type,status,monthly_time").ne("type", UserTypeEnum.ADMIN.getCode()).orderBy("status", false))));
        } catch (Exception e) {
            return renderError(e.getMessage());
        }
    }

    @GetMapping("allStaffs")
    public JSONResponse allStaffs(HttpSession session){
        try{
            String userid = String.valueOf(session.getAttribute("userid"));
            System.out.println("get userid from session : " + userid);
            if(userid.equals("null")) throw new NotLoginException();
            userService.isAdmin(userid);
            return renderSuccess(userService.getAllStaff());
        }catch (Exception e){
            return renderError(e.getMessage());
        }
    }

    @PostMapping
    public JSONResponse insert(@RequestBody @Validated(User.Insert.class) User record, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                return renderError(bindingResult.getFieldError().getDefaultMessage());
            } else {
                User checkUser = userService.findUser(record.getId());
                if(checkUser == null){
                    record.setPassword(record.getId().substring(record.getId().length()- 6));
                    record.setAvatar(Constants.VALUE_USER_DEFAULT_AVATAR);
                    record.setCredit(Constants.VALUE_USER_DEFAULT_CREDIT);
                    record.setStatus(UserStatusEnum.NORMAL.getCode());
                    record.setAccountBalance(BigDecimal.ZERO);
                    record.setDepositBalance(BigDecimal.ZERO);
                    return userService.insert(record) ? renderSuccess(record) : renderError();
                }else{
                 return renderError(Constants.TIP_HAS_EXIST);
                }
            }
        } catch (Exception e) {
            return renderError(e.getMessage());
        }
    }

    /**
     * 修改账户信息-修改/封禁用户
     * @param record
     * @param bindingResult
     * @param session
     * @return
     */
    @PutMapping
    public JSONResponse update(@RequestBody @Validated(User.Update.class) User record, BindingResult bindingResult, HttpSession session) {
        try {
            if (bindingResult.hasErrors()) {
                return renderError(bindingResult.getFieldError().getDefaultMessage());
            } else {
                String currentUserId = String.valueOf(session.getAttribute("userid"));
                if(StringUtils.isNotEmpty(currentUserId) && userService.isAdmin(currentUserId)){
                    return userService.updateById(record) ? renderSuccess(record) : renderError();
                }
                else return renderError(Constants.TIP_NO_PERMISSION);
            }
        } catch (Exception e) {
            return renderError(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public JSONResponse delete(@Validated(User.Delete.class) User record, BindingResult bindingResult, HttpSession session){
        try{
            if(bindingResult.hasErrors()){
                return renderError(bindingResult.getFieldError().getDefaultMessage());
            }
            else if( (record = userService.selectById(record.getId()) )  != null){
                String currentUserId = String.valueOf(session.getAttribute("userid"));
                if(StringUtils.isNotEmpty(currentUserId) && userService.isAdmin(currentUserId)){
                    return userService.deleteById(record.getId()) ? renderSuccess(record) : renderError();
                }
                else return renderError(Constants.TIP_NO_PERMISSION);
            }
            else{
                return renderError(Constants.TIP_EMPTY_DATA);
            }
        }catch (Exception e){
            return renderError(e.getMessage());
        }
    }

}

