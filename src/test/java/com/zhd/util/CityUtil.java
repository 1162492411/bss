package com.zhd.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhd.pojo.City;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 行政区划工具类，用来将从阿里行政区划接口取到的json信息转化为实体类。
 */
public class CityUtil {

    private static String apiPath = "http://restapi.amap.com/v3/config/district?key=d8c7b9bbe2d7d3811c75733a465ff91b";//阿里行政区划接口根路径(含Key)
    private static int idValue = 1;

    public static JSONObject getResponse(String keywords, String code,int subdistrict) {
        String url = apiPath;
        if (StringUtils.isNotBlank(keywords) && StringUtils.isNotBlank(code)) {
            url += "&keywords=" + keywords + "&filter=" + code;
        }
        if(subdistrict != 0){
            url += "&subdistrict=" + subdistrict;
        }
        try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
            HttpGet httpGet = new HttpGet(url);
            HttpResponse response = client.execute(httpGet);
            Pair<Integer, String> pair = new ImmutablePair<>(response.getStatusLine().getStatusCode(),
                    response.getStatusLine().getStatusCode() == 200 ?
                            EntityUtils.toString(response.getEntity()) :
                            response.getStatusLine().getStatusCode() + " " + response.getStatusLine().getReasonPhrase());

            if (pair.getLeft() != 200) {
                throw new Exception(pair.getLeft() + "," + pair.getRight());
            }
            JSONObject result = JSON.parseObject(pair.getRight());
            if (result.get("status").equals("1")) {
                return JSONObject.parseObject(JSON.toJSONString(result.getJSONArray("districts").get(0)));
            }
        } catch (Exception e) {
            return new JSONObject();
        }
        return new JSONObject();
    }

    /**
     * 从jsonobject中获取有效信息
     * @param jsonObject
     * @return [centerY=39.90420913696289, adcode=110000, centerX=116.40739440917969, name=北京市]
     */
    public static City convertToCity(JSONObject jsonObject,Integer parentId){
        City city = new City();
        city.setId(++idValue);
        city.setParentId(parentId);
        city.setName(jsonObject.getString("name"));
        city.setCode(jsonObject.getInteger("adcode"));
        String[] location = jsonObject.getString("center").replaceAll(" ","").split(",");
        if(location.length >= 2){
            city.setCenterX(BigDecimal.valueOf(Float.parseFloat(location[0])));
            city.setCenterY(BigDecimal.valueOf(Float.parseFloat(location[1])));
        }
        Integer level;
        switch(jsonObject.getString("level")){
            case "country" : level = 0;break;
            case "province" : level = 1; break;
            case "city" : level = 2; break;
            case "district" : level = 3; break;
            case "street" : level = 4; break;
            default: level = -1;
        }
        city.setLevel(level);
        return city;
    }

    //获取所有省份的所有市 县/区 镇的信息
    public static List<City> getAllDistrictsByProvince(){
        List<City> results = new ArrayList<>();//存储所有最终信息
        JSONObject countryObject = getResponse(null, null,3);
        JSONArray provinceArray = countryObject.getJSONArray("districts");//获取省份列表
        int countryId = 1;
        for(int i = 0; i < provinceArray.size(); i++){
            JSONObject provinceObject = provinceArray.getJSONObject(i);//获取某省的信息
            City province = convertToCity(provinceObject, countryId);
            results.add(province);//将该省信息加入最终信息中
            JSONArray cityArray = provinceObject.getJSONArray("districts");//获取该省的城市列表
            for (int j = 0; j < cityArray.size(); j++) {
                JSONObject cityObject = cityArray.getJSONObject(j);//获取该省某城市的信息
                City city = convertToCity(cityObject, province.getId());
                results.add(city);//将该城市信息加入最终信息中
                JSONArray districtArray = cityObject.getJSONArray("districts");//获取该省某城市的区/县列表
                for (int k = 0; k < districtArray.size(); k++) {
                    JSONObject districtObject = districtArray.getJSONObject(k);
                    if(!districtObject.getString("level").equals("street")){
                        City district = convertToCity(districtObject, city.getId());
                        results.add(district);//将该区/县信息加入最终信息中(排除某些城市的子行政区划直接为街道的情况)
                    }
                }
            }
        }
        return results;
    }

}



