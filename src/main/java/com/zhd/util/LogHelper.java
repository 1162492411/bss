package com.zhd.util;

import com.zhd.pojo.LoginRecord;
import com.zhd.pojo.User;
import com.zhd.service.LoginRecordService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Created by Mo on 2017/10/3.
 */
@Aspect
public class LogHelper {
    @Autowired
    private LoginRecordService loginRecordService;

    //用户登录后记录日志
    @AfterReturning(value = "execution(* com.zhd.controller.UserController.login(..))")
    public void afterLogin(JoinPoint joinPoint){
        User u = (User)joinPoint.getArgs()[0];
        HttpServletRequest request = (HttpServletRequest)joinPoint.getArgs()[1];
        LoginRecord log = new LoginRecord(u.getId(), LocalDateTime.now(),getIp(request));
        System.out.println("即将保存登录日志" + log.toString());
        boolean result =  loginRecordService.insert(log);
        System.out.println("service返回了" + result);
        System.out.println("此时登录日志是" + log);
    }

    //获取真实ip
    private static String getIp(HttpServletRequest request){
        String ip = request.getHeader("x-forwarded-for");
        if(null == ip || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
            ip = request.getHeader("Proxy-Client-IP");
        if(null == ip || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
            ip = request.getHeader("WL-Proxy-Client-IP");
        if(null == ip || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
            ip = request.getHeader("HTTP_CLIENT_IP");
        if(null == ip || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        if(null == ip || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
            ip = request.getRemoteAddr();
        return "0:0:0:0:0:0:0:1".equals(ip)? "127.0.0.1" : ip ;
    }

}
