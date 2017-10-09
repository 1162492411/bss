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

    @RequestMapping
    public String toLogin(){
        System.out.println("进入了登录");
        return "login";
    }

    /**
     * 用户登录
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

    @RequestMapping("home")
    public  String toHome(Model model){
        model.addAttribute("userid","home1111");
        return "home";
    }

    @RequestMapping("userModule/users")
    public String toUsers(){
        return "userModule/users";
    }

    @RequestMapping("users")
    public @ResponseBody Page defaults(User user, Page result){
        return users(1, user, result);
    }

    @RequestMapping("users/{page}")
    public @ResponseBody Page users(@PathVariable int page, User user, Page result){
        return result.setDatas(userService.selectUsers(result.setTotalCount(userService.selectCount(user)).setCurrentPage(page).getStart(),user));
    }


    /**
     * 用户注销
     */

    /**
     * 添加用户
     */

    /**
     * 删除用户
     */

    /**
     * 更新用户基本信息
     * 可更新的字段 : name/avatar
     */

    /**
     * 更新用户密码
     */

    /**
     * 封禁用户
     */

    /**
     * 分页查看用户信息
     */

    /**
     * 分页查看符合条件的用户信息
     */
}
