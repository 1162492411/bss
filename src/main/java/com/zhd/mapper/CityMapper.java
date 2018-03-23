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

    @Select("select id,name from city where level='1'")
    List<City> getAllSimpleProvinces();//获取所有省份的简略信息

    @Select("select id,name,code,center_x, center_y from city where parent_id = #{parentId}")
    List<City> getAllSimpleCityByParent(@Param("parentId")Integer parentId);//获取所有父类的子行政区划的简略信息

    @Select("select id,name,code,center_x ,center_y from city where code = #{code}")
    City selectSimpleByCode(@Param("code")Integer code);//根据区划编码选择区划信息

}

//1. 注解sql不支持引用其他sql（如<include refid='Base_Column_List'>）,
//2.注解sql中select的字段可以自动映射，如数据库字段center_x能够自动映射成实体属性centerX，该过程与xml中的resultmap无关