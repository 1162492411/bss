package com.zhd.controller;

import com.zhd.service.IApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 申请表 前端控制器
 * </p>
 *
 * @author zyg
 * @since 2018-04-10
 */
@Controller
@RequestMapping("apply")
public class ApplyController extends BaseController {

    @Autowired
    private IApplyService applyService;

    //todo : 查看申请列表/ 处理申请 / 完成申请 / 提交申请

}
