package com.zhd.mapper;

import com.zhd.pojo.CouponType;

public interface CouponTypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CouponType record);

    int insertSelective(CouponType record);

    CouponType selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CouponType record);

    int updateByPrimaryKey(CouponType record);
}