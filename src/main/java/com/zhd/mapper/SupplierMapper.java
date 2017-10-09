package com.zhd.mapper;

import com.zhd.pojo.Supplier;

public interface SupplierMapper {
    int deleteByPrimaryKey(Short id);

    int insert(Supplier record);

    int insertSelective(Supplier record);

    Supplier selectByPrimaryKey(Short id);

    int updateByPrimaryKeySelective(Supplier record);

    int updateByPrimaryKey(Supplier record);
}