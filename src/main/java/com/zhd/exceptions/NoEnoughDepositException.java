package com.zhd.exceptions;

import com.zhd.util.Constants;

/**
 * 账户押金不足异常
 */
public class NoEnoughDepositException extends Exception {
    @Override
    public String getMessage() {
        return Constants.TIP_NO_ENOUGH_DEPOSIT;
    }
}
