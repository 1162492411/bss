package com.zhd.enums;

import com.baomidou.mybatisplus.enums.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.io.Serializable;

/**
 * 行程状态枚举类
 */
@Getter
@AllArgsConstructor
public enum JourneyStatusEnum implements IEnum {

    UNKNOWN(0,"未知"),
    STARTED(1,"已开始"),
    PAYED(2,"已支付"),
    END(3,"已结束");

    private Integer code;

    private String status;

    public static JourneyStatusEnum getByCode(int code){
        if(code <= 0) return UNKNOWN;
        for (JourneyStatusEnum journeyStatusEnum : values()){
            if(code == journeyStatusEnum.getCode()) return journeyStatusEnum;
        }
        return UNKNOWN;
    }

    @Override
    public Serializable getValue() {
        return code;
    }

    @Override
    public String toString() {
        return status;
    }
}
