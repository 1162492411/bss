package com.zhd.convert;

import com.baomidou.mybatisplus.plugins.Page;
import com.zhd.enums.AreaTypeEnum;
import com.zhd.pojo.Area;
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
 * 区域转换类
 */
//@Component
public class AreaConvert{

    /**
     * 将区域信息转化为带有区域类型信息的格式
     * @param area
     * @return
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     */
    public static Map convertToVO(Area area) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if(area == null) {
            throw new NullPointerException(Constants.TIP_EMPTY_DATA);
        }
        Map map = BeanUtils.describe(area);
        map.remove("class");
        map.put("type", AreaTypeEnum.getByCode(area.getType()));
        return map;
    }

    /**
     * 将含区域对象的Page转换为前端需要的格式
     * @param areaPage
     * @return
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     */
    public static PageInfo<Map> convertToVOPageInfo(Page<Area> areaPage) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if(areaPage == null || CollectionUtils.isEmpty(areaPage.getRecords())) throw new NullPointerException(Constants.TIP_EMPTY_DATA);
        PageInfo<Map> resultPage = new PageInfo<>();
        List<Map> resultList = new ArrayList<>();
        for (Area area :areaPage.getRecords()) {
            resultList.add(convertToVO(area));
        }
        resultPage.setRecords(resultList);
        PageUtil.copyPage(areaPage,resultPage);
        resultPage.setKeys(Constants.AREA_KEYS);
        resultPage.setNames(Constants.AREA_NAMES);
        return resultPage;
    }


}
