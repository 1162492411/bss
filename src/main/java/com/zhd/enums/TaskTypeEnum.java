package com.zhd.enums;

import com.baomidou.mybatisplus.enums.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public enum TaskTypeEnum implements IEnum{

    UNKNOWN(0,"未知"),
    MOVE(1,"移动"),
    REPAIR(2,"修理"),
    DISABLE(3,"报废");

    private int code;
    private String type;

    public static TaskTypeEnum getByCode(int code){
        if(code <= 0) {
            return UNKNOWN;
        }
        for (TaskTypeEnum taskTypeEnum : values()) {
            if(code == taskTypeEnum.getCode()) {
                return taskTypeEnum;
            }
        }
        return UNKNOWN;
    }

    public static int getByType(String type){
        if(StringUtils.isNotEmpty(type)){
            switch(type){
                case "移动" : return MOVE.getCode();
                case "修理" : return REPAIR.getCode();
                case "报废" : return DISABLE.getCode();
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
        return this.type;
    }
}
