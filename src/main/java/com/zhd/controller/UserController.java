package com.zhd.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.zhd.convert.UserConvert;
import com.zhd.enums.UserTypeEnum;
import com.zhd.pojo.JSONResponse;
import com.zhd.pojo.User;
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
 * 用户表 前端控制器
 * </p>
 *
 * @author zyg
 * @since 2018-02-05
 */
@RestController
@RequestMapping("/users")
public class UserController extends BaseController{
    @Autowired
    private IUserService userService;

    @GetMapping("list/{current}")
    public JSONResponse list(@PathVariable("current") int pageNum, Page<User> page) {
        try {
            if(pageNum <= 0) throw new IllegalArgumentException(Constants.ILLEGAL_ARGUMENTS);
            return renderSuccess(UserConvert.convertToVOPageInfo(userService.selectPage(page, new EntityWrapper<User>().setSqlSelect("id,name,type,status").ne("type", UserTypeEnum.MANAGER.getCode()))));
        } catch (Exception e) {
            return renderError(e.getMessage());
        }
    }

    @GetMapping("allStaffs")
    public JSONResponse allStaffs(){
        try{
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
                return userService.insert(record) ? renderSuccess(record) : renderError();
            }
        } catch (Exception e) {
            return renderError(e.getMessage());
        }
    }

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

