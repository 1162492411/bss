package com.zhd.service;

import com.zhd.mapper.BicycleMapper;
import com.zhd.pojo.Bicycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Mo on 2017/10/18.
 */
@Service
public class BicycleService {
    @Autowired
    private BicycleMapper bicycleMapper;

    /**
     * 按主键查询车辆信息
     * @param id 车辆的主键
     * @return 车辆信息
     */
    public Bicycle searchById(Long id){
        return bicycleMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询符合条件的车辆的总数
     * @param record 指定条件的车辆信息
     * @return 符合条件的车辆的总数
     */
    public int selectCount(Bicycle record){
        return bicycleMapper.selectCount(record);
    }

    /**
     * 返回分页的符合条件的车辆信息
     * @param start 数据库中车辆的起始行数
     * @param record 指定条件的车辆信息
     * @return 分页后的符合条件的车辆信息集合
     */
    public List<Bicycle> selectBicycles(Integer start, Bicycle record){
        return bicycleMapper.selectBicycles(start,record);
    }

    /**
     * 返回是否成功新增车辆
     * @param record 新增的车辆信息
     * @return 数据库影响行数是否大于0
     */
    public boolean insert(Bicycle record){
        return bicycleMapper.insertSelective(record) > 0;
    }

    /**
     * 返回删除指定车辆的结果
     * @param record 待删除的车辆
     * @return 是否已删除指定车辆
     */
    public boolean delete(Bicycle record){
        return bicycleMapper.deleteByPrimaryKey(record.getId()) > 0;
    }

    /**
     * 返回修改指定车辆的结果
     * @param record 待修改的车辆信息
     * @return 是否已修改指定车辆
     */
    public boolean update(Bicycle record){
        return bicycleMapper.updateByPrimaryKeySelective(record) > 0;
    }
    
    
}
