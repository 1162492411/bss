package com.zhd.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 行政区划工具类，用来将从阿里行政区划接口取到的json信息转化为SQL入库。
 */
public class CityUtil {

    private String apiPath = "http://restapi.amap.com/v3/config/district?key=d8c7b9bbe2d7d3811c75733a465ff91b";//阿里行政区划接口根路径(含Key)

    public JSONObject getResponse(String keywords, String code,int subdistrict) {
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

    @Test
    public void testGet() {
        System.out.println(getResponse("河南", "410000",1));
    }

    /**
     * 从jsonobject中获取有效信息
     * @param object
     * @return [centerY=39.90420913696289, adcode=110000, centerX=116.40739440917969, name=北京市]
     */
    public Map<String,Object> getInfo(JSONObject object){
        Map<String, Object> map = new HashMap<>();
        JSONObject jsonObject = object;
        map.put("name",jsonObject.getString("name"));
        map.put("adcode", jsonObject.getInteger("adcode"));
        String[] location = jsonObject.getString("center").replaceAll(" ","").split(",");
        if(location.length >= 2){
            map.put("centerX", BigDecimal.valueOf(Float.parseFloat(location[0])));
            map.put("centerY", BigDecimal.valueOf(Float.parseFloat(location[1])));
        }
        String level;
        switch(jsonObject.getString("level")){
            case "country" : level = "0";break;
            case "province" : level = "1"; break;
            case "city" : level = "2"; break;
            case "district" : level = "3"; break;
            case "street" : level = "4"; break;
            default: level = "";
        }
        map.put("level", level);
        map.put("former", jsonObject.getString("level"));
        return map;
    }

    /**
     * 获取所有省级行政区划的信息
     * @return [{centerY=39.90420913696289, adcode=110000, centerX=116.40739440917969, name=北京市},.......]
     */
    public List<Map<String,Object>> getAllProvince() {
        JSONObject countryObject = getResponse(null, null,1);
        JSONArray provinces = countryObject.getJSONArray("districts");
        List<Map<String,Object>> provinceInfos = new ArrayList<>(provinces.size());//省份信息
        for (int i = 0; i < provinces.size(); i++) {
            provinceInfos.add(getInfo(provinces.getJSONObject(i)));
        }
        return provinceInfos;
    }

    /**
     * 获取某区域的所有下一级行政区划信息
     * @param city
     * @return [{centerY=36.09757614135742, adcode=410500, centerX=114.39239501953125,....]
     */
    public List<Map<String,Object>> getAllCityByParent(Map<String, Object> city){
        String name = String.valueOf(city.get("name"));
        String adcode = String.valueOf(city.get("adcode"));
        JSONObject response = getResponse(name,adcode,1);
        JSONArray cities = response.getJSONArray("districts");
        List<Map<String,Object>> infos = new ArrayList<>(cities.size());//城市信息
        for (int i = 0; i < cities.size(); i++) {
            infos.add(getInfo(cities.getJSONObject(i)));
        }
        return infos;
    }

    //通过省份获取该省所有市 县/区 镇的信息
    @Test
    public void getAllQusByProvince(){
        List<Map<String,Object>> results = new ArrayList<>();//存储所有最终信息

        JSONObject countryObject = getResponse(null, null,3);
        JSONArray provinceArray = countryObject.getJSONArray("districts");//获取省份列表
        for(int i = 0; i < provinceArray.size(); i++){
            JSONObject provinceObject = provinceArray.getJSONObject(i);//获取某省的信息
             results.add(getInfo(provinceObject));//todo :将该省信息加入最终信息中
//            String provinceName = String.valueOf(provinceObject.get("name"));
//            String provinceAdcode = String.valueOf(provinceObject.get("adcode"));
//            JSONArray cityArray = getResponse(provinceName, provinceAdcode,2).getJSONArray("districts");
            JSONArray cityArray = provinceObject.getJSONArray("districts");//获取该省的城市列表
            for (int j = 0; j < cityArray.size(); j++) {
                JSONObject cityObject = cityArray.getJSONObject(j);//获取该省某城市的信息
                results.add(getInfo(cityObject));//todo : 将该城市信息加入最终信息中
                JSONArray quArray = cityObject.getJSONArray("districts");//获取该省某城市的区/县列表
                for (int k = 0; k < quArray.size(); k++) {
                    JSONObject quObject = quArray.getJSONObject(k);
                    results.add(getInfo(quObject));//todo : 将该区/县信息加入最终信息中
                }
            }
        }

        System.out.println("generate result-->\n" + JSON.toJSONString(results));
    }





    @Test
    public void test(){

    }

}



