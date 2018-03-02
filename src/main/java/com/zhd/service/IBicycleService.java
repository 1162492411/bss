package com.zhd.service;

import com.zhd.pojo.Bicycle;
import com.baomidou.mybatisplus.service.IService;
import com.zhd.pojo.BicycleSupplier;

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

    boolean borrowBicycle(Integer id);//借车

    boolean returnBicycle(Integer id);//还车

    void insertBicycleSupplier(BicycleSupplier bicycleSupplier);

    BicycleSupplier selectBicycleSupplier(String batch);

    List<Bicycle> selectAllSimple();//查看所有车辆简略信息

}
