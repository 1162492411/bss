package com.zhd.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.util.TypeUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zhd.BssTestEnvironment;
import com.zhd.enums.*;
import com.zhd.pojo.*;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import org.springframework.http.MediaType;

import java.math.BigDecimal;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

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

    /**
     * 生成供应商
     */
    public List<Supplier> mockSuppliers(){
        List<Supplier> supplierList = new ArrayList<>();
        supplierList.add(Supplier.builder().brand("富士康").name("鸿富锦精密电子（郑州）有限公司").address("郑州市航空港区振兴路东侧综合保税区").build());
        supplierList.add(Supplier.builder().brand("富士康").name("富士康科技集团(深圳龙华)工业园").address("深圳龙华").build());
        supplierList.add(Supplier.builder().brand("富士康").name("富士康鸿观科技园").address("深圳市龙华新区观光路1412号").build());
        supplierList.add(Supplier.builder().brand("富士康").name("阜宁富士康").address("江苏省阜宁县阜城镇").build());
        supplierList.add(Supplier.builder().brand("富士康").name("富士康科技集团（上海）工业园").address("上海市松江出口加工区南乐路1925号").build());
        supplierList.add(Supplier.builder().brand("富士康").name("富士康杭州科技园").address("钱塘江边杭州经济技术开发区").build());
        supplierList.add(Supplier.builder().brand("科林").name("天津科林自行车有限公司").address("天津市滨海新区大港津歧路（南）133号").build());
        supplierList.add(Supplier.builder().brand("科林").name("天津科林一厂").address("天津市").build());
        supplierList.add(Supplier.builder().brand("科林").name("天津科林二厂").address("天津市").build());
        supplierList.add(Supplier.builder().brand("科林").name("天津科林三厂").address("天津市").build());
        supplierList.add(Supplier.builder().brand("飞鸽").name("天津飞鸽车业发展有限公司").address("天津市静海经济开发区八号路与静霸联络线交口").build());
        supplierList.add(Supplier.builder().brand("飞鸽").name("天津飞鸽自行车一厂").address("天津").build());
        supplierList.add(Supplier.builder().brand("飞鸽").name("天津飞鸽自行车二厂").address("天津").build());
        supplierList.add(Supplier.builder().brand("飞鸽").name("天津飞鸽自行车三厂").address("天津").build());
        supplierList.add(Supplier.builder().brand("飞鸽").name("天津飞鸽自行车四厂").address("天津").build());
        supplierList.add(Supplier.builder().brand("飞鸽").name("天津飞鸽自行车五厂").address("天津").build());
        supplierList.add(Supplier.builder().brand("飞鸽").name("天津飞鸽自行车六厂").address("天津").build());
        supplierList.add(Supplier.builder().brand("凤凰").name("上海凤凰自行车有限公司").address("上海市长宁区福泉北路518号6座凤凰中心").build());
        supplierList.add(Supplier.builder().brand("凤凰").name("上海凤凰自行车一厂").address("上海市长宁区福泉北路518号6座凤凰中心").build());
        supplierList.add(Supplier.builder().brand("凤凰").name("上海凤凰自行车二厂").address("上海市长宁区福泉北路518号6座凤凰中心").build());
        supplierList.add(Supplier.builder().brand("凤凰").name("上海凤凰自行车三厂").address("上海市长宁区福泉北路518号6座凤凰中心").build());
        supplierList.add(Supplier.builder().brand("凤凰").name("上海凤凰自行车四厂").address("上海市长宁区福泉北路518号6座凤凰中心").build());
        supplierList.add(Supplier.builder().brand("凤凰").name("上海凤凰自行车五厂").address("上海市长宁区福泉北路518号6座凤凰中心").build());
        return supplierList;
    }

    /**
     * 生成车辆
     */
    public List<Bicycle> mockBicycles(){
        List<Area> areaList = areaService.selectList(new EntityWrapper<>());
        List<Bicycle> bicycleList = new ArrayList<>(100);
        List<Supplier> supplierList = supplierService.selectList(new EntityWrapper<>());
        for (int i = 0; i < 500; i++) {
            Bicycle bicycle = new Bicycle();
            bicycle.setStatus(BicycleStatusEnum.UNUSED.getCode());
            int type = RandomUtils.nextInt(0,100);
            if(type <= 80){
                bicycle.setType(BicycleTypeEnum.FIRST_GENERATION_LITE.getCode());
            }else{
                bicycle.setType(BicycleTypeEnum.FIRST_GENERATION_CLASSICS.getCode());
            }
            Area area = areaList.get(RandomUtils.nextInt(0,areaList.size()));
            bicycle.setLocationX(BigDecimal.valueOf(RandomUtils.nextDouble(area.getWestPoint().doubleValue(), area.getEastPoint().doubleValue())));
            bicycle.setLocationY(BigDecimal.valueOf(RandomUtils.nextDouble(area.getSouthPoint().doubleValue(), area.getNorthPoint().doubleValue())));
            bicycle.setBatch("1702010010" + (999 - i));
            Supplier supplier = supplierList.get(RandomUtils.nextInt(0, supplierList.size()));
            bicycle.setSupplier(supplier.getId());
            bicycle.setServiceTime("0");
            bicycle.setInvestmentTime(TypeUtils.castToString(RandomUtil.generateRandomStartTime().toEpochSecond(ZoneOffset.ofHours(8))));
            bicycle.setMileage(0);
            System.out.println(JSON.toJSONString(bicycle));
            bicycleList.add(bicycle);
        }
        return bicycleList;
    }

    /**
     * 随机选择普通用户账户充值押金
     */
    @Test
    public void batchInDeposit() throws Exception {
        int count = 0;
        List<User> userList = userService.selectList(new EntityWrapper<User>().eq("type","1"));
        for (int i = 0; i < userList.size() ; i++) {
            if(RandomUtils.nextInt(0,100) > 80){
                System.out.println("---第" + (++count) + "次----------------");
                User user = userList.get(RandomUtils.nextInt(0, userList.size()));
                Deposit deposit = Deposit.builder().type(DepositTypeEnum.IN.getCode()).amount(Constants.STANDARD_DEPOSIT).build();
                mockMvc.perform(post("/deposit").contentType(MediaType.APPLICATION_JSON).content(JSON.toJSONString(deposit)).sessionAttr("userid",user.getId())).andDo(print()).andReturn();
            }
        }
    }

    /**
     * 随机选择普通用户账户充值账户余额
     */
    @Test
    public void batchRecharge() throws Exception{
        int count = 0;
        List<User> userList = userService.selectList(new EntityWrapper<User>().eq("type","1"));
        for (int i = 0; i < userList.size(); i++) {
            if(RandomUtils.nextInt(0,100) > 60){
                System.out.println("---第" + (++count) + "次----------------");
                User user = userList.get(RandomUtils.nextInt(0,userList.size()));
                int type = RandomUtils.nextInt(1,4);
                BigDecimal amount = BigDecimal.valueOf(RandomUtils.nextInt(10,100));
                Recharge recharge = Recharge.builder().userId(user.getId()).type(type).amount(amount).build();
                mockMvc.perform(post("/recharge").contentType(MediaType.APPLICATION_JSON).content(JSON.toJSONString(recharge))).andDo(print()).andReturn();
            }
        }
    }


    @Test
    public void testRandomPath(){
        List<Area> areaList = areaService.selectList(new EntityWrapper<>());
        for (int i = 0; i < 10; i++) {
            Area areaA = areaList.get(RandomUtils.nextInt(0, areaList.size()));
            Area areaB = areaList.get(RandomUtils.nextInt(0, areaList.size()));
            if(areaA.getId() != areaB.getId()) {
                double startPointX = RandomUtils.nextDouble(areaA.getWestPoint().doubleValue(), areaA.getEastPoint().doubleValue());
                double startPointY = RandomUtils.nextDouble(areaA.getSouthPoint().doubleValue(), areaA.getNorthPoint().doubleValue());
                double endPointX = RandomUtils.nextDouble(areaB.getWestPoint().doubleValue(), areaB.getEastPoint().doubleValue());
                double endPointY = RandomUtils.nextDouble(areaB.getSouthPoint().doubleValue(), areaB.getNorthPoint().doubleValue());
                System.out.println("resultPath-->" + RandomUtil.generateRandomPath(startPointX, startPointY, endPointX, endPointY));
            }
        }
    }

    /**
     * 保存各种生成的模拟数据到数据库
     */
    @Test
    public void insertMockData(){
        cityService.insertBatch(mockCity());
        userService.insertBatch(mockUsers());
        supplierService.insertBatch(mockSuppliers());
        //停车点需手动添加
        bicycleService.insertBatch(mockBicycles());
    }




}
