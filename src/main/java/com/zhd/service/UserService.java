package com.zhd.service;

import com.zhd.mapper.UserMapper;
import com.zhd.pojo.User;
import com.zhd.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Mo on 2017/10/2.
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User selectById(String id){
        return userMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询用户的简略信息
     * @param record 待查找的用户
     * @return 查询到的简略的用户信息
     */
    public User selectSimpleUser(User record){
        return userMapper.selectSimpleUser(record);
    }

    /**
     * 查询用户的详细信息
     * @param record 待查找的用户
     * @return 查询到的详细的用户信息
     */
    public User selectDetailUser(User record){
        return userMapper.selectDetailUser(record);
    }


    /**
     * 查询符合条件的用户的总数
     * @param record 指定条件的用户信息
     * @return 符合条件的用户的总数
     */
    public int selectCount(User record){
        return userMapper.selectCount(record);
    }

    /**
     * 返回分页的符合条件的用户信息
     * @param start 数据库中用户的起始行数
     * @param record 指定条件的用户信息
     * @return 分页后的符合条件的用户信息集合
     */
    public List<User> selectUsers(Integer start, User record){
        return userMapper.selectUsers(start,record);
    }

    /**
     * 返回新增的用户的主键
     * @param record 新增的用户信息
     * @return 新增的用户的主键
     */
    public boolean insert(User record){
        return userMapper.insertSelective(record) > 0;
    }

    /**
     * 返回删除指定用户的结果
     * @param record 待删除的用户
     * @return 是否已删除指定用户
     */
    public boolean delete(User record){
        return userMapper.deleteByPrimaryKey(record.getId()) > 0;
    }

    /**
     * 返回修改指定用户的结果
     * @param record 待修改的用户信息
     * @return 是否已修改指定用户
     */
    public boolean update(User record){
        return userMapper.updateByPrimaryKeySelective(record) > 0;
    }
    
}
