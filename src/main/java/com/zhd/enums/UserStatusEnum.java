package com.zhd.enums;

import com.baomidou.mybatisplus.enums.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@AllArgsConstructor
@Getter
public enum UserStatusEnum implements IEnum{

    UNKNOWN(0,"未知"),

    NORMAL(1,"正常"),

    TEMP_BAN(2,"封禁");

//    DISABLED(3,"停用");

    private int code;
    private String status;

    public static UserStatusEnum getByCode(int code){
        if(code <= 0) return UNKNOWN;
        for (UserStatusEnum userStatusEnum : values()) {
            if(code == userStatusEnum.getCode()) return userStatusEnum;
        }
        return UNKNOWN;
    }

    @Override
    public Serializable getValue() {
        return this.code;
    }

    @Override
    public String toString() {
        return this.status;
    }
}
