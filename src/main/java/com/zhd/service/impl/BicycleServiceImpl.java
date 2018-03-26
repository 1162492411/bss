package com.zhd.service.impl;

import com.alibaba.fastjson.util.TypeUtils;
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
import com.zhd.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
@Transactional(rollbackFor = Exception.class)
public class BicycleServiceImpl extends ServiceImpl<BicycleMapper, Bicycle> implements IBicycleService {

    @Autowired
    private BicycleMapper bicycleMapper;

    @Autowired
    private IUserService userService;
    @Autowired
    private IJourneyService journeyService;

    @Override
    public Journey borrowBicycle(Bicycle bicycle, String userid) throws NoSuchUserException, NoEnoughDepositException, NoSuchBicycleException, NotUseableBicycleException, NoEnoughAccountBalanceException {
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
        bicycleMapper.updateById(bicycle);
        Journey journey = Journey.builder().bicycleId(bicycle.getId()).userId(userid).startTime(TypeUtils.castToString(System.currentTimeMillis())).startLocationX(bicycle.getLocationX()).startLocationY(bicycle.getLocationY()).build();
        journeyService.insert(journey);
        return journey;
    }

    @Override
    public boolean returnBicycle(Integer bicycleId, String userId, Journey journey) throws Exception {
        if(bicycleMapper.updateById(Bicycle.builder().id(bicycleId).locationX(journey.getEndLocationX()).locationY(journey.getEndLocationY()).status(BicycleStatusEnum.UNUSED.getCode()).build()) > 0 && journeyService.updateById(journey) && userService.reduceAccount(userId, journey.getAmount())){
            return true;
        }else{
            throw new Exception(Constants.TIP_RETURN_BICYCLE_ERROR);
        }
    }

    @Override
    public List<Bicycle> selectAllSimple() {
        return bicycleMapper.selectList(new EntityWrapper<Bicycle>().setSqlSelect("id,status,location_x,location_y"));
    }
}
