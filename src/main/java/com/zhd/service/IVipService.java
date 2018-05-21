package com.zhd.service;

import com.baomidou.mybatisplus.service.IService;
import com.zhd.exceptions.NoEnoughAccountBalanceException;
import com.zhd.exceptions.NoSuchUserException;
import com.zhd.pojo.Vip;

/**
 * <p>
 * 包月表 服务类
 * </p>
 *
 * @author zyg
 * @since 2018-05-20
 */
public interface IVipService extends IService<Vip> {

    boolean recharge(Vip vip) throws NoSuchUserException, NoEnoughAccountBalanceException;
}
