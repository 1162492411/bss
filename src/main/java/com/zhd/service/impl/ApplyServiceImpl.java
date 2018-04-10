package com.zhd.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zhd.mapper.ApplyMapper;
import com.zhd.pojo.Apply;
import com.zhd.service.IApplyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 申请表 服务实现类
 * </p>
 *
 * @author zyg
 * @since 2018-04-10
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ApplyServiceImpl extends ServiceImpl<ApplyMapper, Apply> implements IApplyService {


}
