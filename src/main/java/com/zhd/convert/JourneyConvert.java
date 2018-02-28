package com.zhd.convert;

import com.baomidou.mybatisplus.plugins.Page;
import com.zhd.pojo.Journey;
import com.zhd.pojo.PageInfo;
import com.zhd.util.Constants;
import com.zhd.util.PageUtil;
import java.util.Map;

/**
 * 行程转换器
 */
public class JourneyConvert {

    public static PageInfo<Map> convertToVOPageIngo(Page<Journey> journeyPage){
        PageInfo<Map> resultPage = new PageInfo<>();
        PageUtil.clonePage(journeyPage, resultPage);
        resultPage.setKeys(Constants.JOURNEY_KEYS);
        resultPage.setNames(Constants.JOURNEY_NAMES);
        return resultPage;
    }
}
