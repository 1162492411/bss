package com.zhd.mapper;

import com.zhd.pojo.Bicycle;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.zhd.pojo.BicycleDe;
import com.zhd.pojo.BicycleSupplier;
import com.zhd.pojo.Supplier;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 车辆表 Mapper 接口
 * </p>
 *
 * @author zyg
 * @since 2018-02-05
 */
public interface BicycleMapper extends BaseMapper<Bicycle> {

    Integer insertBicycleDe(BicycleDe bicycleDe);

    BicycleDe selectBicycleDe(@Param("keyBatch") String batch);

    Integer insertBicycleSupplier(BicycleSupplier bicycleSupplier);

    Integer insertSupp(Supplier supplier);

    BicycleSupplier selectBicycleSupplier(String batch);

    BicycleSupplier selectByBatch(String batch);
}
