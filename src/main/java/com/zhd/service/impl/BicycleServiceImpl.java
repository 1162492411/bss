package com.zhd.service.impl;

import com.zhd.enums.BicycleStatusEnum;
import com.zhd.pojo.Bicycle;
import com.zhd.mapper.BicycleMapper;
import com.zhd.service.IBicycleService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public boolean borrowBicycle(Integer id) {
        return bicycleMapper.updateById(Bicycle.builder().id(id).status(BicycleStatusEnum.USING.getCode()).build()) > 0;
    }

    @Override
    public boolean returnBicycle(Integer id) {
        return bicycleMapper.updateById(Bicycle.builder().id(id).status(BicycleStatusEnum.UNUSED.getCode()).build()) > 0;
    }
}
