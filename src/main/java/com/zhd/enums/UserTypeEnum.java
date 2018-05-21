package com.zhd.enums;

import com.baomidou.mybatisplus.enums.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;

/**
 * 用户类型枚举类
 */
@AllArgsConstructor
@Getter
public enum UserTypeEnum implements IEnum{

    UNKNOWN(0,"未知用户"),

    NORMAL(1,"普通用户"),

    STAFF(2,"员工"),

    ADMIN(3,"管理员");

    private int code;
    private String type;

    public static UserTypeEnum getByCode(int code){
        if(code <= 0) {
            return UNKNOWN;
        }
        for (UserTypeEnum userTypeEnum : values()) {
            if(code == userTypeEnum.getCode()) {
                return userTypeEnum;
            }
        }
        return UNKNOWN;
    }

    public static int getByType(String type){
        if(StringUtils.isNotBlank(type)){
            switch(type){
                case "普通用户" : return NORMAL.getCode();
                case "员工" : return STAFF.getCode();
                case "管理员" : return ADMIN.getCode();
            }
        }
        return -1;
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
