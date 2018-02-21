package com.zhd.service.impl;

import com.zhd.enums.UserStatusEnum;
import com.zhd.exceptions.NoEnoughDepositException;
import com.zhd.exceptions.NoSuchUserException;
import com.zhd.pojo.User;
import com.zhd.mapper.UserMapper;
import com.zhd.service.IUserService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zhd.util.Constants;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
    public boolean checkUser(String id) throws NoSuchUserException {
        if(userMapper.selectOne(User.builder().id(id).status(UserStatusEnum.NORMAL.getCode()).build()) == null) throw new NoSuchUserException();
        else return true;
    }

    @Override
    public boolean checkDeposit(String id) throws NoEnoughDepositException, NoSuchUserException {
        User user = userMapper.selectOne(User.builder().id(id).status(UserStatusEnum.NORMAL.getCode()).build());
        if(user == null) throw new NoSuchUserException();
        if(user.getDepositBalance().intValue() < Constants.STANDARD_DEPOSIT.intValue()) throw new NoEnoughDepositException();
        else return false;
    }
}
