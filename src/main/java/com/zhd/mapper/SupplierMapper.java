package com.zhd.mapper;

import com.zhd.pojo.Supplier;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 供应商表 Mapper 接口
 * </p>
 *
 * @author zyg
 * @since 2018-02-05
 */
public interface SupplierMapper extends BaseMapper<Supplier> {

    List<Map> selectAllSupplier();

}
