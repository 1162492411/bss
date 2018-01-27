package com.zhd.mapper;


import com.zhd.pojo.Supplier;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface SupplierMapper {

    int deleteByPrimaryKey(Short id);

    int insertSelective(Supplier record);

    int updateByPrimaryKeySelective(Supplier record);

    Supplier selectByPrimaryKey(Short id);

    int selectCount(@Param("record") Supplier supplier);

    List<Supplier> selectSuppliers(@Param("start")Integer start, @Param("record")Supplier supplier);

    List<Supplier> selectAll();

}