package com.zhd.service;

import com.zhd.mapper.AreaMapper;
import com.zhd.pojo.Area;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 区域服务类
 * Created by Mo on 2017/9/21.
 */
@Service
public class AreaService {
    @Autowired
    private AreaMapper areaMapper;

    /**
     * 按主键查询区域信息
     * @param id 区域的主键
     * @return 区域信息
     */
    public Area searchById(Integer id){
        return areaMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询符合条件的区域的总数
     * @param area 指定条件的区域信息
     * @return 符合条件的区域的总数
     */
    public int selectCount(Area area){
        return areaMapper.selectCount(area);
    }

    /**
     * 返回分页的符合条件的区域信息
     * @param start 数据库中区域的起始行数
     * @param area 指定条件的区域信息
     * @return 分页后的符合条件的区域信息集合
     */
    public List<Area> selectAreas(Integer start, Area area){
        return areaMapper.selectAreas(start,area);
    }

    /**
     * 返回新增的区域的主键
     * @param area 新增的区域信息
     * @return 数据库影响行数是否大于0
     */
    public int insert(Area area){
        return areaMapper.insert(area);
    }

    /**
     * 返回删除指定区域的结果
     * @param area 待删除的区域
     * @return 是否已删除指定区域
     */
    public boolean delete(Area area){
        return areaMapper.deleteByPrimaryKey(area.getId()) > 0;
    }

    /**
     * 返回修改指定区域的结果
     * @param area 待修改的区域信息
     * @return 是否已修改指定区域
     */
    public boolean update(Area area){
        return areaMapper.updateByPrimaryKeySelective(area) > 0;
    }

}
