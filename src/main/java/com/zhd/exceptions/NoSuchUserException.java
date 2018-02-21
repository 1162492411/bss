package com.zhd.exceptions;

import com.zhd.util.Constants;

/**
 * Created by Mo on 2018/2/18.
 */
public class NoSuchUserException extends Exception {

    @Override
    public String getMessage() {
        return Constants.TIP_NO_SUCH_USER;
    }
}
