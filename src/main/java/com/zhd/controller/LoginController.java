package com.zhd.controller;

import com.zhd.exceptions.NoSuchUserException;
import com.zhd.pojo.User;
import com.zhd.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 用户登录注销控制器
 */
@Controller
public class LoginController {

    @Autowired
    private IUserService userService;

    /**
     * 用户登录
     * @param user 登录信息
     * @return 返回的页面
     */
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public @ResponseBody String login(@RequestBody User user, HttpSession session) throws NoSuchUserException {
        System.out.println("用户登录时收到信息" + user);
        if(userService.findUser(user.getId()).getPassword().equals(user.getPassword())){
            session.setAttribute("userid", user.getId());
            return "home";
        }
        else
            return "login";
    }

    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public String logout(HttpSession session){
        if(session.getAttribute("userid") != null) session.removeAttribute("userid");
        return "login";
    }

}
