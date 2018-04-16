package com.zhd.enums;

import com.baomidou.mybatisplus.enums.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.io.Serializable;

/**
 * 申请状态枚举类
 */
@Getter
@AllArgsConstructor
public enum ApplyStatusEnum implements IEnum {

    UNKNOWN(0,"未知"),
    HAS_SUBMIT(1,"已提交"),
    DONE(2,"处理完成");

    private int code;
    private String status;

    public static ApplyStatusEnum getByCode(int code){
        if(code <= 0){
            return UNKNOWN;
        }
        for (ApplyStatusEnum applyStatusEnum: values()) {
            if(code == applyStatusEnum.getCode()) {
                return  applyStatusEnum;
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
        return this.status;
    }


}
