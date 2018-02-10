package com.zhd.enums;

import com.baomidou.mybatisplus.enums.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

/**
 * 用户类型枚举类
 */
@AllArgsConstructor
@Getter
public enum UserTypeEnum implements IEnum{

    UNKNOWN(Byte.valueOf("0"),"未知用户"),

    NORMAL(Byte.valueOf("1"),"普通用户"),

    STAFF(Byte.valueOf("2"),"员工"),

    MANAGER(Byte.valueOf("3"),"管理员");

    private byte code;
    private String type;

    public static UserTypeEnum getByCode(byte code){
        for (UserTypeEnum userTypeEnum : values()) {
            if(code == userTypeEnum.getCode()) return userTypeEnum;
        }
        return UNKNOWN;
    }

    @Override
    public Serializable getValue() {
        return this.code;
    }

    @Override
    public String toString() {
        return this.type;
    }
}
