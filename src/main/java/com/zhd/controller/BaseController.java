package com.zhd.controller;

import com.zhd.enums.ResponseCodeEnum;
import com.zhd.pojo.JSONResponse;
import com.zhd.util.Constants;
import org.apache.commons.lang.StringUtils;

import java.time.Instant;
import java.util.Date;

/**
 * 基础控制器，负责渲染响应结果
 */
public class BaseController {


    /**
     * 渲染失败数据
     * @return data 响应结果
     */
    protected JSONResponse renderError() {
        return renderResult(ResponseCodeEnum.FAILURE.getCode(), ResponseCodeEnum.FAILURE.getMsg(), Constants.UNKNOWN_EXCEPTION);
    }

    /**
     * 渲染失败数据（带数据）
     * @param msg 需要返回的数据
     * @return data 响应结果
     */
    protected JSONResponse renderError(String msg) {
        return renderResult(ResponseCodeEnum.FAILURE.getCode(), ResponseCodeEnum.FAILURE.getMsg(), msg != null ? msg : StringUtils.EMPTY);
    }

    /**
     * 渲染成功数据
     * @return data 响应结果
     */
    protected JSONResponse renderSuccess() {
        return renderResult(ResponseCodeEnum.SUCCESS.getCode(),ResponseCodeEnum.SUCCESS.getMsg(), StringUtils.EMPTY);
    }

    /**
     * 渲染成功数据（带数据）
     * @param result 需要返回的对象
     * @return data 响应结果
     */
    protected JSONResponse renderSuccess(Object result) {
        if(result == null) throw new NullPointerException(Constants.TIP_EMPTY_DATA);
        return renderResult(ResponseCodeEnum.SUCCESS.getCode(),ResponseCodeEnum.SUCCESS.getMsg(), result);
    }

    /**
     * 渲染响应结果
     * @param code 响应码
     * @param msg 响应消息
     * @param result 响应数据
     * @return 响应结果
     */
    private JSONResponse renderResult(String code, String msg, Object result){
        return JSONResponse.builder().code(code).message(msg).result(result).build();
    }

}


//todo: 请求的幂等

