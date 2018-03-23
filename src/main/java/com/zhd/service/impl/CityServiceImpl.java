package com.zhd.service.impl;

import com.zhd.pojo.City;
import com.zhd.mapper.CityMapper;
import com.zhd.service.ICityService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
