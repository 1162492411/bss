package com.zhd.service;

import com.zhd.exceptions.NoEnoughDepositException;
import com.zhd.exceptions.NoSuchUserException;
import com.zhd.pojo.User;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author zyg
 * @since 2018-02-05
 */
public interface IUserService extends IService<User> {

    boolean checkUser(String id) throws NoSuchUserException;//检查用户有效性

    boolean checkDeposit(String id) throws NoEnoughDepositException, NoSuchUserException;//检查用户押金

    User findUser(String id) throws NoSuchUserException;//查找用户

}
