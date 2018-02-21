package com.zhd.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * HTTP响应枚举类
 */
@Getter
@AllArgsConstructor
public enum ResponseCodeEnum {
    FAILURE("0", "失败"),

    SUCCESS("1", "成功");

    private String code;
    private String msg;
}
