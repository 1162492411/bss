package com.zhd.enums;

import com.baomidou.mybatisplus.enums.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public enum TaskStatusEnum implements IEnum{

    UNKNOWN(0,"未知"),
    WAIT_SOMEONE(1,"待分配"),
    WAIT_COMPLETE(2,"待执行"),
    DONE(3,"已完成");

    private int code;
    private String status;

    public static TaskStatusEnum getByCode(int code){
        if(code <= 0) return UNKNOWN;
        for (TaskStatusEnum taskStatusEnum : values()) {
            if(code == taskStatusEnum.getCode()) return taskStatusEnum;
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
