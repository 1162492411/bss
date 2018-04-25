package com.zhd.service;

import com.baomidou.mybatisplus.service.IService;
import com.zhd.exceptions.NoEnoughAccountBalanceException;
import com.zhd.pojo.Apply;

/**
 * <p>
 * 申请表 服务类
 * </p>
 *
 * @author zyg
 * @since 2018-04-10
 */
public interface IApplyService extends IService<Apply> {

    String doneApply(Apply apply) throws NoEnoughAccountBalanceException;

}
