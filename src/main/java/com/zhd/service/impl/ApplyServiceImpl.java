package com.zhd.service.impl;

import com.alibaba.fastjson.util.TypeUtils;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zhd.enums.ApplyStatusEnum;
import com.zhd.exceptions.NoEnoughAccountBalanceException;
import com.zhd.mapper.ApplyMapper;
import com.zhd.pojo.Apply;
import com.zhd.service.IApplyService;
import com.zhd.service.IUserService;
import com.zhd.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import static com.zhd.enums.ApplyTypeEnum.*;
import static com.zhd.util.Constants.*;

/**
 * <p>
 * 申请表 服务实现类
 * </p>
 *
 * @author zyg
 * @since 2018-04-10
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ApplyServiceImpl extends ServiceImpl<ApplyMapper, Apply> implements IApplyService {

    @Autowired
    private ApplyMapper applyMapper;
    @Autowired
    private IUserService userService;

    @Override
    public String doneApply(Apply apply) throws NoEnoughAccountBalanceException {
        String result;
        if(REFUND_DEPOSIT.getCode().equals(apply.getType())){
            if(userService.refundDeposit(apply.getUserId(), STANDARD_DEPOSIT)){
                apply.setStatus(ApplyStatusEnum.DONE.getCode());
                apply.setEndTime(TypeUtils.castToString(System.currentTimeMillis()));
                result = applyMapper.updateById(apply) > 0 ? TIP_REFUND_DEPOSIT_SUCCESS : TIP_REFUND_DEPOSIT_ERROR;
            }else{
                result = TIP_REFUND_DEPOSIT_ERROR;
            }
        }else if(REFUND_ACCOUNT.getCode().equals(apply.getType())){
            if(userService.reduceAccount(apply.getUserId(),apply.getAmount())){
                apply.setStatus(ApplyStatusEnum.DONE.getCode());
                apply.setEndTime(TypeUtils.castToString(System.currentTimeMillis()));
                result = applyMapper.updateById(apply) > 0 ? TIP_REFUND_ACCOUNT_SUCCESS : TIP_REFUND_ACCOUNT_ERROR;
            }else{
                result = TIP_REFUND_ACCOUNT_ERROR;
            }
        }else{
            result = TIP_UNKNOWN_APPLY_TYPE;
        }
        return result;
    }
}
