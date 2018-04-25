package com.zhd.service;

import com.zhd.exceptions.NoEnoughAccountBalanceException;
import com.zhd.exceptions.NoEnoughDepositException;
import com.zhd.exceptions.NoSuchUserException;
import com.zhd.pojo.Deposit;
import com.zhd.pojo.User;
import com.baomidou.mybatisplus.service.IService;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author zyg
 * @since 2018-02-05
 */
public interface IUserService extends IService<User> {

    boolean isAdmin(String id);

    boolean isStaff(String id);

    boolean checkDepositBalance(String id) throws NoEnoughDepositException, NoSuchUserException;//检查用户押金

    boolean checkAccountBalance(String id) throws NoSuchUserException, NoEnoughAccountBalanceException;//检查用户账户余额

    boolean checkCredit(String id) throws Exception;//检查用户信用

    User findUser(String id) throws NoSuchUserException;//查找用户

    List<User> getAllStaff();//获取所有员工的简略信息

    boolean rechargeAccount(String id, BigDecimal amount);//用户充值账户余额

    boolean rechargeDeposit(String id, BigDecimal amount);//用户充值押金

    boolean refundDeposit(String id, BigDecimal amount);//用户退还押金

    boolean reduceAccount(String id, BigDecimal amount) throws NoEnoughAccountBalanceException; //扣钱接口

}
