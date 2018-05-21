package com.zhd.enums;

import com.baomidou.mybatisplus.enums.IEnum;
import com.zhd.util.DataUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang.StringUtils;

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

    public static int getByStatus(String status){
        if(StringUtils.isNotEmpty(status)){
            switch(status){
                case "已提交" : return HAS_SUBMIT.getCode();
                case "处理完成" : return DONE.getCode();
            }
        }
        return -1;
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
