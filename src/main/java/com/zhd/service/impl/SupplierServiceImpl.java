package com.zhd.service.impl;

import com.zhd.pojo.Supplier;
import com.zhd.mapper.SupplierMapper;
import com.zhd.service.ISupplierService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 供应商表 服务实现类
 * </p>
 *
 * @author zyg
 * @since 2018-02-05
 */
@Service
public class SupplierServiceImpl extends ServiceImpl<SupplierMapper, Supplier> implements ISupplierService {

    @Autowired
    private SupplierMapper supplierMapper;

    @Override
    public boolean checkSupplier(Integer id) {
        return supplierMapper.selectById(id) != null;
    }
}
