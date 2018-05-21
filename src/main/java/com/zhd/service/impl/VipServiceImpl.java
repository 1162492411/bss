package com.zhd.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zhd.exceptions.NoEnoughAccountBalanceException;
import com.zhd.exceptions.NoSuchUserException;
import com.zhd.mapper.VipMapper;
import com.zhd.pojo.Vip;
import com.zhd.service.IUserService;
import com.zhd.service.IVipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Mo on 2018/5/20.
 */
@Service
public class VipServiceImpl extends ServiceImpl<VipMapper, Vip> implements IVipService {

    @Autowired
    private VipMapper vipMapper;
    @Autowired
    private IUserService userService;


    @Override
    public boolean recharge(Vip vip) throws NoSuchUserException, NoEnoughAccountBalanceException {
       if(userService.rechargeVip(vip.getUserId(), vip.getVipTime(), vip.getAmount())){
           return vipMapper.insert(vip) > 0;
       }
        return false;
    }
}
