package com.zhd.enums;

/**
 * Created by Mo on 2018/2/16.
 */

import com.baomidou.mybatisplus.enums.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

/**
 * 押金状态枚举类
 */
@Getter
@AllArgsConstructor
public enum DepositStatusEnum implements IEnum{

    UNKNOWN(0,"异常"),

    UN_HANDLED(1,"未执行"),

    HANDLING(2,"执行中"),

    HANDLED(3,"已完成");

    private int code;
    private String status;

    public static DepositStatusEnum getByCode(int code){
        if(code <= 0) return UNKNOWN;
        for (DepositStatusEnum depositStatusEnum : values()){
            if(code == depositStatusEnum.getCode()) return depositStatusEnum;
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
