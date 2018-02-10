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

    UNKNOWN(Byte.valueOf("0"),"异常状态"),

    UNUSED(Byte.valueOf("1"),"空闲中"),

    USED(Byte.valueOf("2"),"使用中"),

    WAIT_MOVE(Byte.valueOf("3"),"待移动"),

    WAIT_REPAIR(Byte.valueOf("4"),"待维修"),

    WAIT_SCRAP(Byte.valueOf("5"),"待报废");

    private byte code;
    private String status;

    public static BicycleStatusEnum getByCode(byte code){
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
