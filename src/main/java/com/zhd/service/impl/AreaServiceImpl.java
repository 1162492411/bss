package com.zhd.service.impl;

import com.zhd.pojo.Area;
import com.zhd.mapper.AreaMapper;
import com.zhd.service.IAreaService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * <p>
 * 区域表 服务实现类
 * </p>
 *
 * @author zyg
 * @since 2018-02-05
 */
@Service
public class AreaServiceImpl extends ServiceImpl<AreaMapper, Area> implements IAreaService {

    @Autowired
    private AreaMapper areaMapper;

    @Override
    public Area findArea(BigDecimal locationX, BigDecimal locationY) {
        return areaMapper.findArea(locationX,locationY);
    }
}
