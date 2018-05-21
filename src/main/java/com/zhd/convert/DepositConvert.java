package com.zhd.convert;

import com.baomidou.mybatisplus.plugins.Page;
import com.zhd.enums.DepositTypeEnum;
import com.zhd.pojo.Deposit;
import com.zhd.pojo.PageInfo;
import com.zhd.util.Constants;
import com.zhd.util.PageUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 押金转换类
 */
public class DepositConvert {

    /**
     * 将押金信息转化为前台需要的格式
     * @param deposit
     * @return
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     */
    public static Map convertToVO(Deposit deposit) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if(deposit == null) throw new NullPointerException(Constants.TIP_EMPTY_DATA);
        Map map = BeanUtils.describe(deposit);
        map.remove("class");
        map.put("type", DepositTypeEnum.getByCode(deposit.getType()));
        return map;
    }

    public static PageInfo<Map> convertToVOPageInfo(Page<Deposit> depositPage) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if(depositPage == null || CollectionUtils.isEmpty(depositPage.getRecords())) throw new NullPointerException(Constants.TIP_EMPTY_DATA);
        PageInfo<Map> resultPage = new PageInfo<>();
        List<Map> resultList = new ArrayList<>();
        for (Deposit deposit :depositPage.getRecords()) {
            resultList.add(convertToVO(deposit));
        }
        resultPage.setRecords(resultList);
        PageUtil.copyPage(depositPage,resultPage);
        resultPage.setKeys(Constants.DEPOSIT_KEYS);
        resultPage.setNames(Constants.DEPOSIT_NAMES);
        return resultPage;
    }

}
