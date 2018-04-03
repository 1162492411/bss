package com.zhd.util;

import com.alibaba.fastjson.JSONObject;

import java.math.BigDecimal;

/**
 * 阿里逆地理编码工具
 */
public class RegeoUtil {

    private static String apiPath = "http://restapi.amap.com/v3/geocode/regeo?key=" + Constants.KEY_ALI_SERVICE_KEY;//阿里逆地理编码接口根路径(含Key)

    /**
     * 获取高德逆地理编码的有效返回信息
     *
     * @param pointX 查询的点的经度
     * @param pointY 查询的点的纬度
     * @return 包含地理信息的有效信息
     */
    public static JSONObject getCityByLocation(BigDecimal pointX, BigDecimal pointY) {
        String url = apiPath;
        if (!BigDecimal.ZERO.equals(pointX) && !BigDecimal.ZERO.equals(pointY)) {
            url += "&location=" + pointX.setScale(6, BigDecimal.ROUND_DOWN) + "," + pointY.setScale(6, BigDecimal.ROUND_DOWN);
        }
        return HttpUtil.sendPOST(url,Constants.KEY_AMAP_REGEO_RESPONSE_KEY,JSONObject.class);
    }


}
