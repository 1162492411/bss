package com.zhd.enums;

/**
 * Created by Mo on 2018/2/16.
 */

import com.baomidou.mybatisplus.enums.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

/**
 * 押金操作类型枚举类
 */
@Getter
@AllArgsConstructor
public enum DepositTypeEnum implements IEnum{

    UNKNOWN(0,"未知"),

    IN(1,"存入"),

    OUT(2,"取出");

    private int code;
    private String type;

    public static DepositTypeEnum getByCode(int code){
        if(code <= 0) return UNKNOWN;
        for (DepositTypeEnum depositTypeEnum : values()){
            if(code == depositTypeEnum.getCode()) return depositTypeEnum;
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
