package com.zhd.convert;

/**
 * Created by Mo on 2018/2/14.
 */
import com.baomidou.mybatisplus.plugins.Page;
import com.zhd.pojo.PageInfo;
import com.zhd.pojo.Vip;
import com.zhd.util.Constants;
import com.zhd.util.PageUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 包月记录转换器
 */
public class VipConvert {
    /**
     * 将包月记录转化为带有区域类型信息的格式
     * @param vip 源包月记录
     * @return
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     */
    public static Map convertToVO(Vip vip) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if(vip == null) throw new NullPointerException(Constants.TIP_EMPTY_DATA);
        Map map = BeanUtils.describe(vip);
        map.remove("class");
        return map;
    }

    /**
     * 将含包月记录的Page转换为前端需要的格式
     * @param vipPage 源包月记录列表
     * @return
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     */
    public static PageInfo<Map> convertToVOPageInfo(Page<Vip> vipPage) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if(vipPage== null || CollectionUtils.isEmpty(vipPage.getRecords())) throw new NullPointerException(Constants.TIP_EMPTY_DATA);
        PageInfo<Map> resultPage = new PageInfo<>();
        List<Map> resultList = new ArrayList<>();
        for (Vip vip :vipPage.getRecords()) {
            resultList.add(convertToVO(vip));
        }
        resultPage.setRecords(resultList);
        PageUtil.copyPage(vipPage,resultPage);
        resultPage.setKeys(Constants.VIP_KEYS);
        resultPage.setNames(Constants.VIP_NAMES);
        return resultPage;
    }
}