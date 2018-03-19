package com.zhd.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zhd.enums.BicycleStatusEnum;
import com.zhd.exceptions.*;
import com.zhd.mapper.SupplierMapper;
import com.zhd.pojo.Bicycle;
import com.zhd.mapper.BicycleMapper;
import com.zhd.pojo.Journey;
import com.zhd.service.IBicycleService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zhd.service.IJourneyService;
import com.zhd.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 车辆表 服务实现类
 * </p>
 *
 * @author zyg
 * @since 2018-02-05
 */
@Service
public class BicycleServiceImpl extends ServiceImpl<BicycleMapper, Bicycle> implements IBicycleService {

    @Autowired
    private BicycleMapper bicycleMapper;
    @Autowired
    private SupplierMapper supplierMapper;
    @Autowired
    private IUserService userService;
    @Autowired
    private IJourneyService journeyService;

    @Override
    public Boolean borrowBicycle(Bicycle bicycle, String userid) throws NoSuchUserException, NoEnoughDepositException, NoSuchBicycleException, NotUseableBicycleException, NoEnoughAccountBalanceException {
        userService.checkDepositBalance(userid);
        userService.checkAccountBalance(userid);
        //check bicycle
        bicycle = this.selectById(bicycle.getId());
        if (bicycle == null) {
            throw new NoSuchBicycleException();
        }
        if (bicycle.getStatus() != BicycleStatusEnum.UNUSED.getCode()) {
            throw new NotUseableBicycleException();
        }
        //borrowBicycle
        bicycle.setStatus(BicycleStatusEnum.USING.getCode());
        return bicycleMapper.updateById(bicycle) > 0;
    }

    @Override
    public boolean returnBicycle(Integer bicycleId, String userId, Journey journey) {
        return bicycleMapper.updateById(Bicycle.builder().id(bicycleId).status(BicycleStatusEnum.UNUSED.getCode()).build()) > 0 && journeyService.updateById(journey) && userService.reduceAccount(userId, journey.getAmount());
    }

    @Override
    public List<Bicycle> selectAllSimple() {
        return bicycleMapper.selectList(new EntityWrapper<Bicycle>().setSqlSelect("id,status,location_x,location_y"));
    }
}
