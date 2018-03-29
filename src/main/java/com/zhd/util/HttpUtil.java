package com.zhd.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

/**
 * 网络工具，主要用于向高德发送接口
 */
public class HttpUtil {

    /**
     * 获取POST的请求结果中的有效信息
     * @param url 请求的URL
     * @param apiType 请求结果中有效信息的key
     * @param clazz 请求结果中有效信息的类型
     * @param <T>
     * @return
     */
    public static <T>T sendPOST(String url, String apiType, Class<T> clazz){
        try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
            HttpGet httpGet = new HttpGet(url);
            HttpResponse response = client.execute(httpGet);
            Pair<Integer, String> pair = new ImmutablePair<>(response.getStatusLine().getStatusCode(),
                    response.getStatusLine().getStatusCode() == 200 ?
                            EntityUtils.toString(response.getEntity()) :
                            response.getStatusLine().getStatusCode() + " " + response.getStatusLine().getReasonPhrase());

            if (pair.getLeft() != HttpStatus.SC_OK) {
                return null;
            }
            JSONObject result = JSON.parseObject(pair.getRight());
            if (result.get("status").equals("1")) {
                return result.getObject(apiType, clazz);
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }
}
