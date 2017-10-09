package com.zhd.mapper;

import com.zhd.pojo.LoginRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LoginRecordMapper {

    int insert(LoginRecord record);

    int selectCount(@Param("record") LoginRecord record);

    List<LoginRecord> selectLoginRecords(@Param("start")int start, @Param("record") LoginRecord record);

}