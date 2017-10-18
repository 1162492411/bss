package com.zhd.controller;


import com.zhd.pojo.Page;
import com.zhd.pojo.User;
import com.zhd.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 用户控制器
 * Created by Mo on 2017/9/19.
 */
@Controller
@RequestMapping("")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户登录
     * @param user 登录信息
     * @param request 请求
     * @param model model
     * @return 返回的页面
     */
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public @ResponseBody String login(@RequestBody User user, HttpServletRequest request, Model model){
        System.out.println("用户登录时收到信息" + user);
        if( (user = userService.selectDetailUser(user)) != null){
            HttpSession session = request.getSession();
            session.setAttribute("userid", user.getId());
            System.out.println("检索出用户" + user);
            System.out.println("session中的值是" + session.getAttribute("userid"));
            model.addAttribute("userid", user.getId());
            return "home";
        }
        else
            return "login";
    }


    /**
     * 用户注销
     * TODO:二次完善
     */

    /**
     * 用户修改密码
     * TODO:二次完善
     */

    /**
     * 添加用户
     */
    @RequestMapping(value = "users" , method = RequestMethod.POST)
    public @ResponseBody Boolean add(@RequestBody User record){
        return userService.insert(record.defaultAddUser());
    }

    /**
     * 更新用户基本信息
     * 可更新的字段 : name/credit
     */
    @RequestMapping(value = "users", method = RequestMethod.PUT)
    public @ResponseBody Boolean update(@RequestBody User record){
        return userService.update(record);
    }

    /**
     * 封禁用户
     */
    @RequestMapping(value = "users/block", method = RequestMethod.POST)
    public @ResponseBody Boolean block(@RequestBody User record){
        if(!userService.selectById(record.getId()).getStatus())
            return userService.update(record);
        else return false;
    }

    /**
     * 删除用户
     */
    @RequestMapping(value = "users", method = RequestMethod.DELETE)
    public @ResponseBody Boolean delete(@RequestBody User record){
        return userService.delete(record);
    }

    /**
     * 查看用户列表
     * @param record 条件用户
     * @param result 存储结果的分页对象
     * @return 分页数据
     */
    @RequestMapping("users")
    public @ResponseBody Page defaults(User record, Page result){
        return users(1, record, result);
    }

    /**
     * 分页查看用户列表
     * @param page 前台请求的分页数
     * @param record 条件用户
     * @param result 存储结果的分页对象
     * @return 分页数据
     */
    @RequestMapping("users/{page}")
    public @ResponseBody Page users(@PathVariable int page, User record, Page result){
        return result.setDatas(userService.selectUsers(result.setTotalCount(userService.selectCount(record)).setCurrentPage(page).getStart(),record)).setKeys(record.getKeys()).setNames(record.getNames());
    }

}
