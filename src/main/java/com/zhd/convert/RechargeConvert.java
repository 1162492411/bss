package com.zhd.convert;

/**
 * Created by Mo on 2018/2/14.
 */

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.plugins.Page;
import com.zhd.enums.RechargeTypeEnum;
import com.zhd.pojo.Area;
import com.zhd.pojo.PageInfo;
import com.zhd.pojo.Recharge;
import com.zhd.util.Constants;
import com.zhd.util.PageUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 充值记录转换器
 */
public class RechargeConvert {
    /**
     * 将充值记录转化为带有区域类型信息的格式
     * @param recharge 源充值记录
     * @return
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     */
    public static Map convertToVO(Recharge recharge) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if(recharge == null) throw new NullPointerException(Constants.TIP_EMPTY_DATA);
        Map map = BeanUtils.describe(recharge);
        map.remove("class");
        map.put("type", RechargeTypeEnum.getByCode(recharge.getType()));
        System.out.println(map);
        return map;
    }

    /**
     * 将含充值记录的Page转换为前端需要的格式
     * @param rechargePage
     * @return
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     */
    public static PageInfo<Map> convertToVOPageInfo(Page<Recharge> rechargePage) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if(rechargePage == null || CollectionUtils.isEmpty(rechargePage.getRecords())) throw new NullPointerException(Constants.TIP_EMPTY_DATA);
        PageInfo<Map> resultPage = new PageInfo<>();
        List<Map> resultList = new ArrayList<>();
        for (Recharge recharge :rechargePage.getRecords()) {
            resultList.add(convertToVO(recharge));
        }
        resultPage.setRecords(resultList);
        PageUtil.copyPage(rechargePage,resultPage);
        resultPage.setKeys(Constants.RECHARGE_KEYS);
        resultPage.setNames(Constants.RECHARGE_NAMES);
        return resultPage;
    }
}