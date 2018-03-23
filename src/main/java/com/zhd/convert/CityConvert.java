package com.zhd.convert;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhd.util.Constants;

/**
 * 行政区划的转换类
 */
public class CityConvert {

    /**
     * 将获取到的输入提示进行修剪
     * @param jsonArray
     * @return
     */
    public static JSONArray trimInputTips(JSONArray jsonArray){
        JSONArray resultArray = new JSONArray();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            JSONObject resultObject = new JSONObject();
            resultObject.put("id", jsonObject.getString("location"));
            resultObject.put("name", jsonObject.getString("name"));
            resultArray.add(resultObject);
        }
        return resultArray;
    }

}
