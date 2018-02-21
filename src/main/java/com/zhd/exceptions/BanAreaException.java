package com.zhd.exceptions;

import com.zhd.util.Constants;

/**
 * Created by Mo on 2018/2/19.
 */
public class BanAreaException extends Exception{

    @Override
    public String getMessage() {
        return Constants.TIP_BAN_AREA;
    }
}
