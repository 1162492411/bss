package com.zhd.enums;

import com.baomidou.mybatisplus.enums.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public enum TaskTypeEnum implements IEnum{

    UNKNOWN(0,"未知"),
    ADD(1,"投放"),
    MOVE(2,"移动"),
    REPAIR(3,"修理"),
    DISABLE(4,"报废");

    private int code;
    private String type;

    public static TaskTypeEnum getByCode(int code){
        for (TaskTypeEnum taskTypeEnum : values()) {
            if(code == taskTypeEnum.getCode()) return taskTypeEnum;
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
