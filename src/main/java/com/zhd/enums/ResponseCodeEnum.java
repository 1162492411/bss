package com.zhd.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * HTTP响应枚举类
 */
@AllArgsConstructor
public enum ResponseCodeEnum {
    FAILURE("0", "失败"),

    SUCCESS("1", "成功");

    @Getter
    private String code;
    @Getter
    private String msg;
}
