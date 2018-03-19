package com.zhd.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zhd.enums.JourneyStatusEnum;
import com.zhd.pojo.Journey;
import com.zhd.mapper.JourneyMapper;
import com.zhd.service.IJourneyService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 行程记录表 服务实现类
 * </p>
 *
 * @author zyg
 * @since 2018-02-05
 */
@Service
public class JourneyServiceImpl extends ServiceImpl<JourneyMapper, Journey> implements IJourneyService {

    @Autowired
    private JourneyMapper journeyMapper;

    @Override
    public List<Journey> getContinuedJourneys(String userId) {
        return journeyMapper.selectList(new EntityWrapper<Journey>().eq("user_id",userId).ne("status",JourneyStatusEnum.END.getCode()));
    }

    @Override
    public Journey selectByUser(String userId) {
        return journeyMapper.selectByUser(userId).get(0);
    }
}
