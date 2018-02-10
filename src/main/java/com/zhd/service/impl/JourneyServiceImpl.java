package com.zhd.service.impl;

import com.zhd.pojo.Journey;
import com.zhd.mapper.JourneyMapper;
import com.zhd.service.IJourneyService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
