package com.zhd.convert;

import com.baomidou.mybatisplus.plugins.Page;
import com.fasterxml.jackson.databind.ser.std.CollectionSerializer;
import com.zhd.enums.UserStatusEnum;
import com.zhd.enums.UserTypeEnum;
import com.zhd.pojo.PageInfo;
import com.zhd.pojo.User;
import com.zhd.util.Constants;
import com.zhd.util.PageUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *  用户转换类
 */
public class UserConvert {

    /**
     * 将user信息转化为前端需要的格式
     * @param user 源user信息
     * @return 转化后待user信息
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     */
    public static Map convertToVO(User user) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if(user == null) throw new NullPointerException(Constants.TIP_EMPTY_DATA);
        Map map = BeanUtils.describe(user);
        map.remove("class");
        map.put("type", UserTypeEnum.getByCode(user.getType()));
        map.put("status", UserStatusEnum.getByCode(user.getStatus()));
        return map;
    }

    /**
     * 将page转化为前端需要待格式
     * @param userPage 源page
     * @return 转化后的page
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     */
    public static PageInfo<Map> convertToVOPageInfo(Page<User> userPage) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if(userPage == null || CollectionUtils.isEmpty(userPage.getRecords())) throw new NullPointerException(Constants.TIP_EMPTY_DATA);
        PageInfo<Map> resultPage = new PageInfo<>();
        List<Map> resultList = new ArrayList<>();
        for (User user : userPage.getRecords()) {
            resultList.add(convertToVO(user));
        }
        resultPage.setRecords(resultList);
        PageUtil.copyPage(userPage, resultPage);
        resultPage.setKeys(Constants.USER_KEYS);
        resultPage.setNames(Constants.USER_NAMES);
        return resultPage;
    }


}
