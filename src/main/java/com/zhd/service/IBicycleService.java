package com.zhd.service;

import com.zhd.exceptions.*;
import com.zhd.pojo.Bicycle;
import com.baomidou.mybatisplus.service.IService;
import com.zhd.pojo.Journey;

import java.util.List;

/**
 * <p>
 * 车辆表 服务类
 * </p>
 *
 * @author zyg
 * @since 2018-02-05
 */
public interface IBicycleService extends IService<Bicycle> {

    Boolean borrowBicycle(Bicycle bicycle, String userid) throws NoSuchUserException, NoEnoughDepositException, NoSuchBicycleException, NotUseableBicycleException, NoEnoughAccountBalanceException;//借车

    boolean returnBicycle(Integer bicycleId, String userId, Journey journey);//还车

    List<Bicycle> selectAllSimple();//查看所有车辆简略信息

}
