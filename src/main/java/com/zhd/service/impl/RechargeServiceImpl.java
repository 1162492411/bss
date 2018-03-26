package com.zhd.service.impl;

import com.zhd.pojo.Recharge;
import com.zhd.mapper.RechargeMapper;
import com.zhd.service.IRechargeService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zhd.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 充值记录表 服务实现类
 * </p>
 *
 * @author zyg
 * @since 2018-02-05
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class RechargeServiceImpl extends ServiceImpl<RechargeMapper, Recharge> implements IRechargeService {

    @Autowired
    private RechargeMapper rechargeMapper;
    @Autowired
    private IUserService userService;

    @Override
    public boolean checkRecharge(Integer id) {
        return rechargeMapper.selectById(id) != null;
    }

    @Override
    public boolean recharge(Recharge record) {
        if(rechargeMapper.insert(record) > 0){
            return userService.rechargeAccount(record.getUserId(), record.getAmount());
        }
        return false;
    }
}
