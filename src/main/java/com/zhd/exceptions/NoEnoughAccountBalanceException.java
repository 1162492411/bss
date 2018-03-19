package com.zhd.exceptions;

import com.zhd.util.Constants;

/**
 * 账户余额不足异常
 */
public class NoEnoughAccountBalanceException extends Exception {

    @Override
    public String getMessage() {
        return Constants.TIP_NO_ACCOUNT_BALANCE;
    }
}
