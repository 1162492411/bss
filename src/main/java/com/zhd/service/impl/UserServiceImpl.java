package com.zhd.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zhd.enums.UserStatusEnum;
import com.zhd.enums.UserTypeEnum;
import com.zhd.exceptions.NoEnoughAccountBalanceException;
import com.zhd.exceptions.NoEnoughDepositException;
import com.zhd.exceptions.NoSuchUserException;
import com.zhd.pojo.User;
import com.zhd.mapper.UserMapper;
import com.zhd.service.IUserService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zhd.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author zyg
 * @since 2018-02-05
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean isAdmin(String id) {
        return userMapper.selectById(id).getType().equals(UserTypeEnum.ADMIN.getCode());
    }

    @Override
    public boolean checkDepositBalance(String id) throws NoEnoughDepositException, NoSuchUserException {
        User user = userMapper.selectOne(User.builder().id(id).status(UserStatusEnum.NORMAL.getCode()).build());
        if(user == null) throw new NoSuchUserException();
        else if(user.getDepositBalance().intValue() < Constants.STANDARD_DEPOSIT.intValue()) throw new NoEnoughDepositException();
        else return false;
    }

    @Override
    public boolean checkAccountBalance(String id) throws NoSuchUserException, NoEnoughAccountBalanceException {
        User user = findUser(id);
        if(user.getAccountBalance().doubleValue() < 0) throw new NoEnoughAccountBalanceException();
        else return false;
    }

    @Override
    public User findUser(String id) throws NoSuchUserException {
        User user = userMapper.selectById(id);
        if(user == null) throw new NoSuchUserException();
        else return user;
    }

    @Override
    public List<User> getAllStaff() {
        return userMapper.selectList(new EntityWrapper<User>().setSqlSelect("id,name").eq("type",UserTypeEnum.STAFF.getCode()).eq("status",UserStatusEnum.NORMAL.getCode()));
    }

    @Override
    public boolean rechargeAccount(String id, BigDecimal amount) {
        return userMapper.rechargeAccount(id,amount) > 0;
    }

    @Override
    public boolean rechargeDeposit(String id, BigDecimal amount) {
        return userMapper.rechargeDeposit(id, amount) > 0;
    }

    @Override
    public boolean refundDeposit(String id, BigDecimal amount) {
        return userMapper.refundDeposit(id, amount) > 0;
    }

    @Override
    public boolean reduceAccount(String id, BigDecimal amount) {
        return userMapper.reduceAccount(id, amount) > 0;
    }
}


