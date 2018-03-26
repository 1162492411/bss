package com.zhd.util;

import com.alibaba.fastjson.JSON;
import com.zhd.BssTestEnvironment;
import com.zhd.enums.UserStatusEnum;
import com.zhd.enums.UserTypeEnum;
import com.zhd.pojo.City;
import com.zhd.pojo.User;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 模拟数据工具类，用于生成各种模拟数据
 */
public class MockUtil extends BssTestEnvironment{

    /**
     * 生成行政区划数据
     */
    public List<City> mockCity(){
        return CityUtil.getAllDistrictsByProvince();
    }

    /**
     * 生成各类用户
     */
    public List<User> mockUsers(){
        List<User> users = new ArrayList<>();
        //mock normal user
        Long normalId = Long.parseLong("11223344556");
        for (int i = 1; i <= 100; i++) {
            normalId++;
            String id = normalId.toString();
            String name = normalId + "号用户";
            String password = normalId.toString().substring(id.length() - 6);
            int type = UserTypeEnum.NORMAL.getCode();
            String avatar = Constants.VALUE_USER_DEFAULT_AVATAR;
            int credit = Constants.VALUE_USER_DEFAULT_CREDIT;
            int status = UserStatusEnum.NORMAL.getCode();
            BigDecimal accountBalance = BigDecimal.ZERO;
            BigDecimal depositBalance = BigDecimal.ZERO;
            User user = User.builder().id(id).name(name).password(password).type(type).avatar(avatar).credit(credit).status(status).accountBalance(accountBalance).depositBalance(depositBalance).build();
            users.add(user);
        }
        //mock staff user
        Long staffId = Long.parseLong("20100102000");
        for (int i = 1; i <= 50; i++) {
            staffId++;
            String id = staffId.toString();
            String name = staffId + "号员工";
            String password = staffId.toString().substring(id.length() - 6);
            int type = UserTypeEnum.STAFF.getCode();
            String avatar = Constants.VALUE_USER_DEFAULT_AVATAR;
            int credit = Constants.VALUE_USER_DEFAULT_CREDIT;
            int status = UserStatusEnum.NORMAL.getCode();
            BigDecimal accountBalance = BigDecimal.ZERO;
            BigDecimal depositBalance = BigDecimal.ZERO;
            User user = User.builder().id(id).name(name).password(password).type(type).avatar(avatar).credit(credit).status(status).accountBalance(accountBalance).depositBalance(depositBalance).build();
            users.add(user);
        }
        //mock admin user
        Long adminId = Long.parseLong("10100102000");
        for (int i = 1; i <= 5; i++) {
            adminId++;
            String id = adminId.toString();
            String name = adminId + "号管理员";
            String password = adminId.toString().substring(id.length() - 6);
            int type = UserTypeEnum.ADMIN.getCode();
            String avatar = Constants.VALUE_USER_DEFAULT_AVATAR;
            int credit = Constants.VALUE_USER_DEFAULT_CREDIT;
            int status = UserStatusEnum.NORMAL.getCode();
            BigDecimal accountBalance = BigDecimal.ZERO;
            BigDecimal depositBalance = BigDecimal.ZERO;
            User user = User.builder().id(id).name(name).password(password).type(type).avatar(avatar).credit(credit).status(status).accountBalance(accountBalance).depositBalance(depositBalance).build();
            users.add(user);
        }
        return users;
    }

    @Test
    public void mockJourneys(){

    }

    /**
     * 保存各种生成的模拟数据到数据库
     */
    @Test
    public void insertMockData(){
        cityService.insertBatch(mockCity());
        userService.insertBatch(mockUsers());
    }


}
