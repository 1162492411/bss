package com.zhd.mapper;

import com.zhd.pojo.Bicycle;

public interface BicycleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Bicycle record);

    int insertSelective(Bicycle record);

    Bicycle selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Bicycle record);

    int updateByPrimaryKey(Bicycle record);
}