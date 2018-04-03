package com.zhd.service;

import com.zhd.pojo.City;
import com.baomidou.mybatisplus.service.IService;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 全国行政区划 服务类
 * </p>
 *
 * @author zyg
 * @since 2018-03-20
 */
public interface ICityService extends IService<City> {

    List<City> getAllSimpleProvinces();//获取所有省份的简略信息

    List<City> getAllSimpleCityByParent(Integer parentId);//获取所有父类的子行政区划的简略信息

    City getSimpleByCode(Integer code);//根据区划编码选择区划的简略信息

    List<Integer> getAllChildren(Integer id);//根据主键获取该区划所有子区划

}
