package com.zhd.service.impl;

import com.alibaba.fastjson.util.TypeUtils;
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
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
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
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean isAdmin(String id) {
        return userMapper.selectById(id).getType().equals(UserTypeEnum.ADMIN.getCode());
    }

    @Override
    public boolean isStaff(String id) {
        return userMapper.selectById(id).getType().equals(UserTypeEnum.STAFF.getCode());
    }

    @Override
    public boolean checkDepositBalance(String id) throws NoEnoughDepositException, NoSuchUserException {
        User user = userMapper.selectOne(User.builder().id(id).status(UserStatusEnum.NORMAL.getCode()).build());
        if(user == null){
            throw new NoSuchUserException();
        }
        else if(user.getDepositBalance().intValue() < Constants.STANDARD_DEPOSIT.intValue()) {
            throw new NoEnoughDepositException();
        }
        else{
            return true;
        }
    }

    @Override
    public boolean checkAccountBalance(String id) throws NoSuchUserException, NoEnoughAccountBalanceException {
        User user = findUser(id);
        if(user.getAccountBalance().doubleValue() < 0) {
            throw new NoEnoughAccountBalanceException();
        }
        else{
            return true;
        }
    }

    @Override
    public boolean checkCredit(String id) throws Exception {
        User user = findUser(id);
        if(user.getCredit() < 0){
            throw new Exception(Constants.TIP_NO_ENOUGH_CREDIT);
        }else{
            return true;
        }
    }

    @Override
    public User findUser(String id){
        User user = userMapper.selectById(id);
        if(user == null) {
            return null;
        }
        else{
            return user;
        }
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
    public boolean rechargeVip(String id, Integer vipTime, BigDecimal amount) throws NoSuchUserException, NoEnoughAccountBalanceException {
        User user = findUser(id);
        LocalDateTime localDateTime;
        try{
            localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.valueOf(user.getMonthlyTime())), ZoneId.of("Asia/Shanghai")).plusMonths(vipTime);
        }catch (Exception e){
            localDateTime = LocalDateTime.ofInstant(Instant.now(), ZoneId.of("Asia/Shanghai")).plusMonths(vipTime);
        }
        return reduceAccount(id, amount) && userMapper.updateById(User.builder().id(id).monthlyTime(TypeUtils.castToString(localDateTime.toEpochSecond(ZoneOffset.ofHours(8)))).build()) > 0;
    }

    @Override
    public boolean refundDeposit(String id, BigDecimal amount) {
        if(userMapper.selectById(id).getDepositBalance().doubleValue() >= Constants.STANDARD_DEPOSIT.doubleValue()){
            return userMapper.refundDeposit(id, amount) > 0;
        }else{
            return false;
        }
    }

    @Override
    public boolean reduceAccount(String id, BigDecimal amount) throws NoEnoughAccountBalanceException {
        if(userMapper.reduceAccount(id,amount) > 0){
          BigDecimal accountBalance = userMapper.selectById(id).getAccountBalance();
          if(accountBalance.doubleValue() > 0){
//              userMapper.increaseCredit(id);
              return true;
          }else{
              throw new NoEnoughAccountBalanceException();
          }
        }
        return false;
    }

    @Override
    public void reduceCredit(String id,int count) throws NoSuchUserException {
        User user = findUser(id);
        int currentCredit = user.getCredit();
        if(currentCredit - count <= 0){
            this.userMapper.updateById(User.builder().id(id).credit(0).build());
        }else{
            this.userMapper.updateById(User.builder().id(id).credit( currentCredit - count).build());
        }
    }
}


