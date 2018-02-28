package com.zhd.service;

import com.zhd.pojo.Supplier;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 供应商表 服务类
 * </p>
 *
 * @author zyg
 * @since 2018-02-05
 */
public interface ISupplierService extends IService<Supplier> {

    boolean checkSupplier(Integer id);

    List<Map> selectAllSupplier();

}
