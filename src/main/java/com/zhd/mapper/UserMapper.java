package com.zhd.mapper;

import com.zhd.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(String id);

    int insertSelective(User record);

    int updateByPrimaryKeySelective(User record);

    User selectByPrimaryKey(String id);

    User selectSimpleUser(User record);

    User selectDetailUser(User record);

    int selectCount(@Param("record") User record);

    List<User> selectUsers(@Param("start")Integer start, @Param("record")User record);

}