package com.zhd.service;

import com.zhd.mapper.LoginRecordMapper;
import com.zhd.pojo.LoginRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Mo on 2017/10/3.
 */
@Service
public class LoginRecordService {
    @Autowired
    private LoginRecordMapper loginRecordMapper;

    public boolean insert(LoginRecord record){
        return loginRecordMapper.insert(record) > 0;
    }

    public int selectCount(LoginRecord record){
        return loginRecordMapper.selectCount(record);
    }

    public List<LoginRecord> selectLoginRecords(int start, LoginRecord record){
        return loginRecordMapper.selectLoginRecords(start, record);
    }

}
