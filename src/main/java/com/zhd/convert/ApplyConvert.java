package com.zhd.convert;

import com.baomidou.mybatisplus.plugins.Page;
import com.zhd.enums.ApplyStatusEnum;
import com.zhd.enums.ApplyTypeEnum;
import com.zhd.pojo.Apply;
import com.zhd.pojo.PageInfo;
import com.zhd.util.Constants;
import com.zhd.util.PageUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ApplyConvert {

    /**
     * 将申请信息转化
     * @return
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     */
    public static Map convertToVO(Apply record) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if(record == null) {
            throw new NullPointerException(Constants.TIP_EMPTY_DATA);
        }
        Map map = BeanUtils.describe(record);
        map.remove("class");
        map.put("type", ApplyTypeEnum.getByCode(record.getType()));
        map.put("status", ApplyStatusEnum.getByCode(record.getStatus()));
        return map;
    }

    /**
     * 将含区域对象的Page转换为前端需要的格式
     * @param applyPage
     * @return
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     */
    public static PageInfo<Map> convertToVOPageInfo(Page<Apply> applyPage) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if(applyPage == null || CollectionUtils.isEmpty(applyPage.getRecords())){
            throw new NullPointerException(Constants.TIP_EMPTY_DATA);
        }
        PageInfo<Map> resultPage = new PageInfo<>();
        List<Map> resultList = new ArrayList<>();
        for (Apply record :applyPage.getRecords()) {
            resultList.add(convertToVO(record));
        }
        resultPage.setRecords(resultList);
        PageUtil.copyPage(applyPage,resultPage);
        resultPage.setKeys(Constants.APPLY_KEYS);
        resultPage.setNames(Constants.APPLY_NAMES);
        return resultPage;
    }

}
