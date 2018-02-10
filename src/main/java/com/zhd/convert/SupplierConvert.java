package com.zhd.convert;

import com.zhd.pojo.Supplier;
import com.zhd.util.Constants;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 供应商转换类
 */
public class SupplierConvert {

    /**
     * 将供应商列表转化为key = supplier.id , value = supplier的map
     * @param supplierList 供应商列表
     * @return
     */
    public static Map<Integer,Supplier> convertSupplierListToMap(List<Supplier> supplierList){
        if(CollectionUtils.isEmpty(supplierList)) throw new NullPointerException(Constants.TIP_EMPTY_DATA);
        Map<Integer,Supplier> resultMap = new HashMap<>();
        for (Supplier supplier : supplierList) {
            resultMap.put(supplier.getId(),supplier);
        }
        return resultMap;
    }

}
