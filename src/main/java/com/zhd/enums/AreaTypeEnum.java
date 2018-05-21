package com.zhd.enums;

import com.baomidou.mybatisplus.enums.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;

@AllArgsConstructor
@Getter
public enum AreaTypeEnum implements IEnum{

    UNKNOWN(0,"未知"),
    NORMAL(1,"普通区"),
    RED(2,"红包区"),
    BAN(3,"禁停区");

    private int code;
    private String type;

    public static AreaTypeEnum getByCode(int code){
        if(code <= 0) return UNKNOWN;
        for (AreaTypeEnum areaTypeEnum: values()) {
            if(code == areaTypeEnum.getCode())  return  areaTypeEnum;
        }
        return UNKNOWN;
    }

    public static int getByType(String type){
        if(StringUtils.isNotEmpty(type)){
            switch(type){
                case "普通区" : return NORMAL.getCode();
                case "红包区" : return RED.getCode();
                case "禁停区" : return BAN.getCode();
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
