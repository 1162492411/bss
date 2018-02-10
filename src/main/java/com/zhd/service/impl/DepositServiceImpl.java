package com.zhd.service.impl;

import com.zhd.pojo.Deposit;
import com.zhd.mapper.DepositMapper;
import com.zhd.service.IDepositService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 押金记录表 服务实现类
 * </p>
 *
 * @author zyg
 * @since 2018-02-05
 */
@Service
public class DepositServiceImpl extends ServiceImpl<DepositMapper, Deposit> implements IDepositService {

}
