package com.zhd.enums;

import com.baomidou.mybatisplus.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

/**
 * 车辆状态枚举类
 */
@AllArgsConstructor
@Getter
public enum BicycleStatusEnum implements IEnum{

    UNKNOWN(0,"未知"),

    UNUSED(1,"空闲中"),

    USING(2,"使用中"),

    WAIT_MOVE(3,"待移动"),

    WAIT_REPAIR(4,"待维修"),

    WAIT_SCRAP(5,"待报废");

    private int code;
    private String status;

    public static BicycleStatusEnum getByCode(int code){
        if(code <= 0) return UNKNOWN;
        for (BicycleStatusEnum bicycleStatusEnum : values()) {
            if(code == bicycleStatusEnum.getCode()) return bicycleStatusEnum;
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
