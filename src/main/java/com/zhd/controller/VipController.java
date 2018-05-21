package com.zhd.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.util.TypeUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.zhd.convert.VipConvert;
import com.zhd.exceptions.NotLoginException;
import com.zhd.pojo.*;
import com.zhd.service.IVipService;
import com.zhd.util.Constants;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneOffset;

/**
 * <p>
 * 包月记录表 前端控制器
 * </p>
 *
 * @author zyg
 * @since 2018-05-20
 */
@RestController
@RequestMapping("/vip")
public class VipController extends BaseController{
    private static final Logger logger = LoggerFactory.getLogger(VipController.class);
    @Autowired
    private IVipService vipService;

    /**
     * 查看筛选后的包月记录 - Web端
     * @param pageNum 要查看的页码
     * @param page 分页类
     * @return
     */
    @GetMapping("list/{keyword}/{current}")
    public JSONResponse searchList(@PathVariable("keyword")String keyword, @PathVariable("current") int pageNum, Page<Vip> page) {
        try {
            if(pageNum <= 0) {
                throw new IllegalArgumentException(Constants.ILLEGAL_ARGUMENTS);
            }
            if(StringUtils.isNotBlank(keyword)){
                return renderSuccess(VipConvert.convertToVOPageInfo(vipService.selectPage(page, new EntityWrapper<Vip>().like("user_id", keyword))));
            }else{
                return renderSuccess(VipConvert.convertToVOPageInfo(vipService.selectPage(page, new EntityWrapper<>())));
            }
        } catch (Exception e) {
            return renderError(e.getMessage());
        }
    }

    /**
     * 查看包月记录 - Web端
     * @param pageNum 要查看的页码
     * @param page 分页类
     * @return
     */
    @GetMapping("list/{current}")
    public JSONResponse list(@PathVariable("current") int pageNum, Page<Vip> page) {
        try {
            if(pageNum <= 0) {
                throw new IllegalArgumentException(Constants.ILLEGAL_ARGUMENTS);
            }
            return renderSuccess(VipConvert.convertToVOPageInfo(vipService.selectPage(page, new EntityWrapper<>())));
        } catch (Exception e) {
            return renderError(e.getMessage());
        }
    }


    /**
     * 查看包月记录 - 用户版
     * @param pageNum 要查看的页码
     * @param page 分页类
     * @param session session
     * @return
     */
    @GetMapping("user/list/{current}")
    public JSONResponse userList(@PathVariable("current") int pageNum, Page<Vip> page, HttpSession session) {
        try {
            if(pageNum <= 0) {
                throw new IllegalArgumentException(Constants.ILLEGAL_ARGUMENTS);
            }
            return renderSuccess(VipConvert.convertToVOPageInfo(vipService.selectPage(page, new EntityWrapper<Vip>().eq("user_id", String.valueOf(session.getAttribute("userid"))))));
        } catch (Exception e) {
            return renderError(e.getMessage());
        }
    }

    /**
     * 为账户续费包月时间
     * @param record 包月信息
     * @param bindingResult
     * @return 包月后的提示信息
     */
    @PostMapping
    public JSONResponse insert(@RequestBody @Validated(Vip.Insert.class)Vip record, BindingResult bindingResult, HttpSession session) {
        try {
            if (bindingResult.hasErrors()) {
                return renderError(bindingResult.getFieldError().getDefaultMessage());
            } else {
                String userid = String.valueOf(session.getAttribute("userid"));
                if(StringUtils.isEmpty(userid)){
                    throw new NotLoginException();
                }
                record.setAmount(BigDecimal.valueOf(record.getVipTime() * Constants.VIP_COUNT));
                record.setUserId(userid);
                record.setOperateTime(TypeUtils.castToString( System.currentTimeMillis() / 1000));
                return vipService.recharge(record) ? renderSuccess() : renderError();
            }
        } catch (Exception e) {
            return renderError(e.getMessage());
        }
    }

}

