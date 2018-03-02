package com.zhd.convert;

import com.baomidou.mybatisplus.plugins.Page;
import com.zhd.enums.BicycleStatusEnum;
import com.zhd.enums.BicycleTypeEnum;
import com.zhd.pojo.Bicycle;
import com.zhd.pojo.PageInfo;
import com.zhd.pojo.Supplier;
import com.zhd.util.Constants;
import com.zhd.util.PageUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 车辆转换类，将车辆等信息转化为前端需要的格式
 */
public class BicycleConvert {

    /**
     * 将车辆信息中的供应商id转化为供应商名
     * @param bicycle 车辆信息
     * @param supplier 供应商信息
     * @return 带有供应商信息的车辆信息
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     */
    public static Map convertToVO(Bicycle bicycle, Supplier supplier) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if(bicycle == null) throw new NullPointerException(Constants.TIP_EMPTY_DATA);
        Map resultMap = BeanUtils.describe(bicycle);
        resultMap.remove("class");
        if(supplier == null) resultMap.put("supplier",Constants.UNKNOWN_DATA);
        else resultMap.put("supplier",supplier.getName() != null ? supplier.getName() : Constants.UNKNOWN_DATA);
        resultMap.put("status", BicycleStatusEnum.getByCode(bicycle.getStatus()));
        resultMap.put("type", BicycleTypeEnum.getByCode(bicycle.getType()));
        return resultMap;
    }

    /**
     * 将车辆信息列表中的所有车辆信息中的供应商id转化为供应商名
     * @param bicyclePage  含有车辆列表的Page类
     * @param supplierList 供应商列表
     * @return 带有供应商信息的车辆信息列表
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     */
    public static PageInfo<Map> convertToVOPageInfo(Page<Bicycle> bicyclePage, List<Supplier> supplierList) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if(bicyclePage == null || CollectionUtils.isEmpty(bicyclePage.getRecords())) throw new NullPointerException(Constants.TIP_EMPTY_DATA);
        PageInfo<Map> resultPage = new PageInfo<>();
        List<Map> resultList = new ArrayList<>();
        Map<Integer,Supplier> supplierMap = SupplierConvert.convertSupplierListToMap(supplierList);
        for (Bicycle bicycle : bicyclePage.getRecords()) {
            resultList.add(convertToVO(bicycle,supplierMap.get(bicycle.getSupplier())));
        }
        PageUtil.copyPage(bicyclePage,resultPage);
        resultPage.setRecords(resultList);
        resultPage.setNames(Constants.BICYCLE_NAMES);
        resultPage.setKeys(Constants.BICYCLE_KEYS);
        return resultPage;
    }

    public static List convertSimple(List<Bicycle> bicycleList) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if(CollectionUtils.isEmpty(bicycleList)) throw new NullPointerException(Constants.TIP_EMPTY_DATA);
        List<Map> resultList = new ArrayList<>();
        for (Bicycle bicycle : bicycleList) {
            Map map = BeanUtils.describe(bicycle);
            map.remove("class");
            map.put("status", BicycleStatusEnum.getByCode(bicycle.getStatus()));
            resultList.add(map);
        }
        return resultList;
    }



//todo:若某字段值为空，是不返回该字段给前台还是返回空值给前台


}
