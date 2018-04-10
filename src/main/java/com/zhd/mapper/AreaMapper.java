package com.zhd.mapper;

import com.zhd.pojo.Area;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 区域表 Mapper 接口
 * </p>
 *
 * @author zyg
 * @since 2018-02-05
 */
public interface AreaMapper extends BaseMapper<Area> {

    @Select("select id,name,type from area where ${x} between west_point and east_point and ${y} between south_point and north_point")
    Area findArea(@Param("x") BigDecimal locationX, @Param("y") BigDecimal locationY);

    Boolean insertCollection(@Param("areas") List<Area> list);


}
