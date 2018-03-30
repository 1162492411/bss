package com.zhd.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 坐标辅助工具
 */
public class LocationUtils {
    private static double EARTH_RADIUS = 6378.137;

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    /**
     * 通过经纬度获取距离(单位：米)
     * @param lng1 起始点经度
     * @param lat1 起始点纬度
     * @param lng2 终止点经度
     * @param lat2 终止点纬度
     * @return
     */
    public static int getDistance(double lng1,double lat1, double lng2, double lat2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000d) / 10000d;
        s = s*1000;
        return Double.valueOf(s).intValue();
    }

    /**
     * 计算行程的距离
     * @param path 行程的轨迹
     * @return 行程的实际距离
     */
    public static int getJourneyDistance(String path){
        int distance = 0;
        try{
            String[] paths = path.split(",");
            List<Double> pathData = new ArrayList<>();
            for (int i = 0; i < paths.length; i ++) {
                pathData.add(Double.parseDouble(paths[i]));
            }
            for (int i = 0; i < pathData.size() ; i += 2) {
                distance += getDistance(pathData.get(i), pathData.get(i+1), pathData.get(i+2), pathData.get(i+3));
            }
        }catch (Exception e){
            return distance;
        }
        return distance;
    }

}    