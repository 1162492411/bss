package com.zhd.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhd.convert.CityConvert;
import com.zhd.pojo.JSONResponse;
import com.zhd.service.ICityService;
import com.zhd.util.Constants;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <p>
 * 全国行政区划 前端控制器
 * </p>
 *
 * @author zyg
 * @since 2018-03-20
 */
@RestController
@RequestMapping("/city")
public class CityController extends BaseController{
    @Autowired
    private ICityService cityService;

    @RequestMapping("search")
    public JSONResponse searchCityByCode(@RequestBody Integer code){
        try{
            return renderSuccess(cityService.getSimpleByCode(code));
        }catch(Exception e){
            return renderError(e.getMessage());
        }
    }


    @RequestMapping("inputtips")
    public JSONResponse getInputTips(@RequestBody Map<String,String> paramMap){
        try(CloseableHttpClient client = HttpClientBuilder.create().build()){
            String url = "http://restapi.amap.com/v3/assistant/inputtips?key=" + Constants.KEY_ALI_SERVICE_KEY;
            String keywords = paramMap.get("keywords");
            if(StringUtils.isNotBlank(keywords)){
                url += "&keywords=" + keywords;
            }
            String code = paramMap.get("code");
            if(StringUtils.isNotBlank(code)){
                url += "&city=" + code;
            }
            System.out.println("url-->" + url);
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
            System.out.println("result-->" + result);
            if (result.get("status").equals("1")) {
                return renderSuccess(CityConvert.trimInputTips(result.getJSONArray("tips")));
            }else{
                return renderError(Constants.TIP_ALI_ERROR);
            }
        }catch (Exception e){
            return renderError(Constants.TIP_ALI_ERROR);
        }
    }

}

