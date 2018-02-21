package com.zhd.enums;

/**
 * Created by Mo on 2018/2/14.
 */

import com.baomidou.mybatisplus.enums.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

/**
 * 充值类型枚举类
 */
@Getter
@AllArgsConstructor
public enum RechargeTypeEnum implements IEnum{

    UNKNOWN(0,"未知"),

    VIRTUAL(1,"虚拟"),

    CARD(2," 银行卡"),

    ALIPAY(3,"支付宝"),

    WEIXIN(4,"微信");

    private int code;

    private String type;

    public static RechargeTypeEnum getByCode(int code){
        if(code <= 0) return UNKNOWN;
        for (RechargeTypeEnum rechargeTypeEnum : values()) {
            if(code == rechargeTypeEnum.getCode()) return rechargeTypeEnum;
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
