package com.zhd.service;

import com.zhd.pojo.Area;
import com.baomidou.mybatisplus.service.IService;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 区域表 服务类
 * </p>
 *
 * @author zyg
 * @since 2018-02-05
 */
public interface IAreaService extends IService<Area> {

    Area findArea(BigDecimal locationX, BigDecimal locationY);


    List<Integer> selectByCityIds(List<Integer> id);//根据cityIds批量选择其所有area

}
