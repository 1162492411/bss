package com.zhd.util;

import com.baomidou.mybatisplus.plugins.Page;
import com.zhd.pojo.PageInfo;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * 分页工具类
 */
@Component
public class PageUtil {

    /**
     * 复制分页对象的信息到另一个分页对象(不复制records)
     * @param originPage 源分页对象
     * @param destPage 目的分页对象
     * @return 复制后的分页对象
     */
    public static PageInfo copyPage(Page originPage, PageInfo destPage){
        destPage.setCurrent(originPage.getCurrent());
        destPage.setSize(originPage.getSize());
        destPage.setTotal(originPage.getTotal());
        destPage.setRecordsSize(originPage.getRecords().size());
        destPage.setPages(originPage.getPages());
        return destPage;
    }

    /**
     * 克隆分页对象待信息到另一个对象
     * @param originPage 源分页对象
     * @param destPage 目的分页对象
     * @return 复制后的分页对象
     */
    public static PageInfo clonePage(Page originPage, PageInfo destPage){
        copyPage(originPage,destPage);
        destPage.setRecords(originPage.getRecords());
        return destPage;
    }
}
