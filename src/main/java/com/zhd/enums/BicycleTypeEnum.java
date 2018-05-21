package com.zhd.enums;

import com.baomidou.mybatisplus.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;

/**
 *  车辆类型枚举类
 */
@AllArgsConstructor
@Getter
public enum BicycleTypeEnum implements IEnum{

    UNKNOWN(0,"未知类型"),

    FIRST_GENERATION_CLASSICS(1,"一代经典版"),

    FIRST_GENERATION_LITE(2,"一代轻骑版");

    private int code;
    private String type;

    public static BicycleTypeEnum getByCode(int code){
        if(code <= 0) return UNKNOWN;
        for (BicycleTypeEnum bicycleTypeEnum : values()) {
            if(code == bicycleTypeEnum.getCode()) return bicycleTypeEnum;
        }
        return UNKNOWN;
    }

    public static int getByType(String type){
        if(StringUtils.isNotBlank(type)){
            switch (type){
                case "一代经典版" : return FIRST_GENERATION_CLASSICS.getCode();
                case "一代轻骑版" : return FIRST_GENERATION_LITE.getCode();
            }
        }
        return  -1;
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
