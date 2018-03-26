package com.zhd.service.impl;

import com.zhd.pojo.Deposit;
import com.zhd.mapper.DepositMapper;
import com.zhd.service.IDepositService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zhd.service.IUserService;
import com.zhd.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 押金记录表 服务实现类
 * </p>
 *
 * @author zyg
 * @since 2018-02-05
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class DepositServiceImpl extends ServiceImpl<DepositMapper, Deposit> implements IDepositService {

    @Autowired
    private DepositMapper depositMapper;
    @Autowired
    private IUserService userService;

    @Override
    public boolean recharge(Deposit deposit) throws Exception {
        if(userService.rechargeDeposit(deposit.getUserId(), Constants.STANDARD_DEPOSIT) && depositMapper.insert(deposit) > 0){
            return true;
        }else{
            throw new Exception(Constants.TIP_RECHARGE_DEPOSIT_ERROR);
        }
    }

    @Override
    public boolean refund(Deposit deposit) throws Exception {
        if(userService.refundDeposit(deposit.getUserId(), Constants.STANDARD_DEPOSIT) && depositMapper.insert(deposit) > 0){
            return true;
        }else{
            throw new Exception(Constants.TIP_REFUND_DEPOSIT_ERROR);
        }
    }
}
