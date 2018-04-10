package com.zhd.enums;

import com.baomidou.mybatisplus.enums.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

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

    @Override
    public Serializable getValue() {
        return this.code;
    }

    @Override
    public String toString() {
        return this.type;
    }
}
