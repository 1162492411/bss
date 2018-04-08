package com.zhd.service.impl;

import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zhd.pojo.City;
import com.zhd.mapper.CityMapper;
import com.zhd.service.ICityService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 全国行政区划 服务实现类
 * </p>
 *
 * @author zyg
 * @since 2018-03-20
 */
@Service
public class CityServiceImpl extends ServiceImpl<CityMapper, City> implements ICityService {

    @Autowired
    private CityMapper cityMapper;

    @Override
    public List<City> getAllSimpleProvinces() {
        return cityMapper.getAllSimpleProvinces();
    }

    @Override
    public List<City> getAllSimpleCityByParent(Integer parentId) {
        return cityMapper.getAllSimpleCityByParent(parentId);
    }

    @Override
    public City getSimpleByCode(Integer code) {
        return cityMapper.selectSimpleByCode(code);
    }

    @Override
    public List<Integer> getAllChildren(Integer id) {
        City city = cityMapper.selectById(id);
        List<Integer> childrenIds = new ArrayList<>();
        if(city != null){
            String code = String.valueOf(city.getCode());
            int cityLevel = city.getLevel();
            List<City> cityList;
            if(cityLevel == 0){
                cityList = cityMapper.selectList(new EntityWrapper<City>().setSqlSelect("id").ne("id", city.getId()));
            }else if(cityLevel == 1){
                cityList = cityMapper.selectList(new EntityWrapper<City>().setSqlSelect("id").like(
                        true,"code", code.substring(0,3), SqlLike.RIGHT).ne("id",city.getId()));
            }else if(cityLevel == 2){
                cityList = cityMapper.selectList(new EntityWrapper<City>().setSqlSelect("id").like(
                        true,"code", code.substring(0,5), SqlLike.RIGHT).ne("id",city.getId()));
            }else{
                childrenIds.add(city.getId());
                return childrenIds;
            }
            for (int i = 0; i < cityList.size(); i++) {
                childrenIds.add(cityList.get(i).getId());
            }
        }
        return childrenIds;
    }
}
