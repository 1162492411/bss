package com.zhd.enums;

import com.baomidou.mybatisplus.enums.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public enum ApplyTypeEnum implements IEnum {

    UNKNOWN("0","未知"),
    REFUND_DEPOSIT("1","提取押金"),
    REFUND_ACCOUNT("2","提取余额");

    private String code;
    private String type;

    public static ApplyTypeEnum getByCode(String code){
        if(code.equals("0")){
            return UNKNOWN;
        }
        for (ApplyTypeEnum applyTypeEnum: values()) {
            if(code.equals(applyTypeEnum.getCode())) {
                return  applyTypeEnum;
            }
        }
        return UNKNOWN;
    }

    public static String getByType(String type){
        if(StringUtils.isNotEmpty(type)){
            switch(type){
                case "提取押金" : return REFUND_DEPOSIT.getCode();
                case "提取余额" : return REFUND_ACCOUNT.getCode();
            }
        }
        return null;
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
