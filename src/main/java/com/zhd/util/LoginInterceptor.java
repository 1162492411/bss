package com.zhd.util;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 请求拦截类,解决跨域
 * Created by Mo on 2017/10/2.
 */
@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        System.out.println("preHandleStart.........");
//        response.setHeader("Access-Control-Allow-Headers", "X-Requested-With, accept, content-type");
//        response.setHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS, PATCH");
//        response.setHeader("Access-Control-Allow-Origin", "http://localhost:8002");
//        response.setHeader("Access-Control-Max-Age","86400");//设置最大有效时间
//        System.out.println("preHandleDone.......");
        return true;
    }


}
