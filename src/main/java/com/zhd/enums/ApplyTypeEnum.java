package com.zhd.enums;

import com.baomidou.mybatisplus.enums.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public enum ApplyTypeEnum implements IEnum {

    UNKNOWN(0,"未知"),
    REFUND_DEPOSIT(1,"提取押金"),
    REFUND_ACCOUNT(2,"提取余额"),
    EXCEPTION_JOURNEY(3,"提交异常行程");

    private int code;
    private String type;

    public static ApplyTypeEnum getByCode(int code){
        if(code <= 0){
            return UNKNOWN;
        }
        for (ApplyTypeEnum applyTypeEnum: values()) {
            if(code == applyTypeEnum.getCode()) {
                return  applyTypeEnum;
            }
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
