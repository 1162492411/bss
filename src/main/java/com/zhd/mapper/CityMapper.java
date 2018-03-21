package com.zhd.mapper;

import com.zhd.pojo.City;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import javax.annotation.security.PermitAll;
import java.util.List;

/**
 * <p>
 * 全国行政区划 Mapper 接口
 * </p>
 *
 * @author zyg
 * @since 2018-03-20
 */
public interface CityMapper extends BaseMapper<City> {

    @Select("select id,name from city where level='2'")
    List<City> getAllSimpleProvinces();//获取所有省份的简略信息

    @Select("select id,name,code from city where parent_id = #{parentId}")
    List<City> getAllsimpleCityByParent(@Param("parentId")Integer parentId);//获取所有父类的子行政区划的简略信息



}
