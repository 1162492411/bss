package com.zhd.controllerTests;

import static com.alibaba.fastjson.JSON.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.util.TypeUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zhd.BssTestEnvironment;
import com.zhd.enums.DepositTypeEnum;
import com.zhd.enums.RechargeTypeEnum;
import com.zhd.pojo.*;
import com.zhd.service.IJourneyService;
import com.zhd.util.Constants;
import com.zhd.util.LocationUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

/**
 * mockMVc测试类，用于测试各种接口
 */
public class MockTest extends BssTestEnvironment {

    @Autowired
    private IJourneyService journeyService;

    @Test
    public void testLogin() throws Exception{
        User adminUser = User.builder().id("23456789011").password("789011").build();
        mockMvc.perform(post("/login").contentType(APPLICATION_JSON).content(toJSONString(adminUser))).andDo(print());
    }

    @Test
    public void testLogout() throws Exception{
        mockMvc.perform(get("/logout")).andDo(print()).andReturn();
    }

    @Test
    public void testRecharge() throws Exception{
        Recharge recharge = Recharge.builder().userId("23456789011").type(RechargeTypeEnum.ALIPAY.getCode()).amount(BigDecimal.valueOf(50)).build();
        mockMvc.perform(post("/recharge").contentType(MediaType.APPLICATION_JSON).content(JSON.toJSONString(recharge))).andDo(print()).andReturn();
    }

    @Test
    public void testRecharges() throws Exception{
        mockMvc.perform(get("/recharge/list/1").sessionAttr("userid","23456789011")).andDo(print()).andReturn();
    }

    @Test
    public void testInDeposit() throws Exception{
        Deposit deposit = Deposit.builder().type(DepositTypeEnum.IN.getCode()).amount(Constants.STANDARD_DEPOSIT).build();
        mockMvc.perform(post("/deposit").contentType(MediaType.APPLICATION_JSON).content(JSON.toJSONString(deposit)).sessionAttr("userid","23456789011")).andDo(print()).andReturn();
    }

    @Test
    public void testOutDeposit() throws Exception{
        Deposit deposit = Deposit.builder().type(DepositTypeEnum.OUT.getCode()).amount(Constants.STANDARD_DEPOSIT).build();
        String content = JSON.toJSONString(deposit);
        mockMvc.perform(post("/deposit").contentType(MediaType.APPLICATION_JSON).content(content).sessionAttr("userid","23456789011")).andDo(print()).andReturn();
    }

    @Test
    public void testBorrowBicycle() throws Exception{
        Bicycle bicycle = Bicycle.builder().build();
        String content = JSON.toJSONString(bicycle);
        mockMvc.perform(get("/bicycles/borrow/41").contentType(MediaType.APPLICATION_JSON).content(content).sessionAttr("userid","11223344651")).andDo(print()).andReturn();
    }

    @Test
    public void testReturnBicycle() throws Exception {
//        List<Area> areaList = areaService.selectList(new EntityWrapper<>());
//        Area area = areaList.get(RandomUtils.nextInt(0, areaList.size()));
        Area area = areaService.selectById(37);
        BigDecimal endLocationX = BigDecimal.valueOf(RandomUtils.nextDouble(area.getWestPoint().doubleValue(), area.getEastPoint().doubleValue()));
        BigDecimal endLocationY = BigDecimal.valueOf(RandomUtils.nextDouble(area.getSouthPoint().doubleValue(), area.getNorthPoint().doubleValue()));
        LocalDateTime startTimeValue = LocalDateTime.now();
        String startTime = TypeUtils.castToString(startTimeValue.toEpochSecond(ZoneOffset.ofHours(8)));
        long rideTimeValue = RandomUtils.nextLong(0, 3600);
        LocalDateTime endTimeValue = startTimeValue.plusSeconds(rideTimeValue);
        String endTime = TypeUtils.castToString(1000 * endTimeValue.toEpochSecond(ZoneOffset.ofHours(8)));
        Journey journey = Journey.builder().bicycleId(41).endLocationX(endLocationX).endLocationY(endLocationY).startTime(startTime).endTime(endTime).build();
        String content = JSON.toJSONString(journey);
        System.out.println(content);
        mockMvc.perform(post("/bicycles/return/41").contentType(MediaType.APPLICATION_JSON).content(content).sessionAttr("userid","11223344651")).andDo(print()).andReturn();
    }



    @Test
    public void test() throws Exception{
//        mockMvc.perform(post("/city/inputtips"))
    }










}
