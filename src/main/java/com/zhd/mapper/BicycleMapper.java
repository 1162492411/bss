package com.zhd.mapper;

import com.zhd.pojo.Bicycle;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * 车辆表 Mapper 接口
 * </p>
 *
 * @author zyg
 * @since 2018-02-05
 */
public interface BicycleMapper extends BaseMapper<Bicycle> {

    /**
     * 成功还车后更新车辆的使用信息
     * @param bicycle
     * @return
     */
    @Update("update bicycle set service_time = service_time + #{bicycle.serviceTime}, mileage = mileage + #{bicycle.mileage} where id= #{bicycle.id}")
    boolean updateInfo(@Param("bicycle")Bicycle bicycle);

}
