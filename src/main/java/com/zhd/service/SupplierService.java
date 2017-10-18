package com.zhd.service;

import com.zhd.mapper.SupplierMapper;
import com.zhd.pojo.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Mo on 2017/10/18.
 */
@Service
public class SupplierService {
    @Autowired
    private SupplierMapper supplierMapper;

    /**
     * 按主键查询供应商信息
     * @param id 供应商的主键
     * @return 供应商信息
     */
    public Supplier searchById(Short id){
        return supplierMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询符合条件的供应商的总数
     * @param record 指定条件的供应商信息
     * @return 符合条件的供应商的总数
     */
    public int selectCount(Supplier record){
        return supplierMapper.selectCount(record);
    }

    /**
     * 返回分页的符合条件的供应商信息
     * @param start 数据库中供应商的起始行数
     * @param record 指定条件的供应商信息
     * @return 分页后的符合条件的供应商信息集合
     */
    public List<Supplier> selectSuppliers(Integer start, Supplier record){
        return supplierMapper.selectSuppliers(start,record);
    }

    /**
     * 返回是否成功新增供应商
     * @param record 新增的供应商信息
     * @return 数据库影响行数是否大于0
     */
    public boolean insert(Supplier record){
        return supplierMapper.insertSelective(record) > 0;
    }

    /**
     * 返回删除指定供应商的结果
     * @param record 待删除的供应商
     * @return 是否已删除指定供应商
     */
    public boolean delete(Supplier record){
        return supplierMapper.deleteByPrimaryKey(record.getId()) > 0;
    }

    /**
     * 返回修改指定供应商的结果
     * @param record 待修改的供应商信息
     * @return 是否已修改指定供应商
     */
    public boolean update(Supplier record){
        return supplierMapper.updateByPrimaryKeySelective(record) > 0;
    }
    
}
