package com.zhd.service;

import com.zhd.mapper.JourneyMapper;
import com.zhd.pojo.Journey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Created by Mo on 2017/10/19.
 */
@Service
public class JourneyService {
    @Autowired
    private JourneyMapper journeyMapper;

    /**
     * 按主键查询行程信息
     * @param id 行程的主键
     * @return 行程信息
     */
    public Journey searchById(Integer id){
        return journeyMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询符合条件的行程的总数
     * @param record 指定条件的行程信息
     * @return 符合条件的行程的总数
     */
    public int selectCount(Journey record){
        return journeyMapper.selectCount(record);
    }

    /**
     * 返回分页的符合条件的行程信息
     * @param start 数据库中行程的起始行数
     * @param record 指定条件的行程信息
     * @return 分页后的符合条件的行程信息集合
     */
    public List<Journey> selectJourneys(Integer start, Journey record){
        return journeyMapper.selectJourneys(start,record);
    }

    /**
     * 返回是否成功新增行程
     * @param record 新增的行程信息
     * @return 数据库影响行数是否大于0
     */
    public boolean insert(Journey record){
        return journeyMapper.insertSelective(record) > 0;
    }

    /**
     * 返回删除指定行程的结果
     * @param record 待删除的行程
     * @return 是否已删除指定行程
     */
    public boolean delete(Journey record){
        return journeyMapper.deleteByPrimaryKey(record.getId()) > 0;
    }

    /**
     * 返回修改指定行程的结果
     * @param record 待修改的行程信息
     * @return 是否已修改指定行程
     */
    public boolean update(Journey record){
        return journeyMapper.updateByPrimaryKeySelective(record) > 0;
    }

}
