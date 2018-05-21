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

import javax.xml.crypto.Data;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
        for (int i = 1; i <= 800; i++) {
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
        for (int i = 1; i <= 100; i++) {
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
        for (int i = 1; i <= 10; i++) {
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
            bicycle.setServiceTime(0L);
            bicycle.setInvestmentTime(TypeUtils.castToString(DataUtil.generateRandomStartTime().toEpochSecond(ZoneOffset.ofHours(8))));
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
        List<User> userList = userService.selectList(new EntityWrapper<User>().eq("type","1").eq("deposit_balance",0));
        for (int i = 0; i < userList.size() ; i++) {
            if(RandomUtils.nextInt(0,100) > 10){
                System.out.println("---第" + (++count) + "次----------------");
                User user = userList.get(RandomUtils.nextInt(0, userList.size()));
                Deposit deposit = Deposit.builder().type(DepositTypeEnum.IN.getCode()).amount(Constants.STANDARD_DEPOSIT).build();
                mockMvc.perform(post("/deposit").contentType(MediaType.APPLICATION_JSON).content(JSON.toJSONString(deposit)).sessionAttr("userid",user.getId())).andDo(print()).andReturn();
            }
        }
    }

    /**
     * 随机选择缴纳过押金的普通用户充值账户余额
     */
    @Test
    public void batchRecharge() throws Exception{
        int count = 0;
        List<User> userList = userService.selectList(new EntityWrapper<User>().eq("type","1").ne("deposit_balance",0));
        for (int i = 0; i < userList.size(); i++) {
            if(RandomUtils.nextInt(0,100) > 5){
                System.out.println("---第" + (++count) + "次----------------");
                User user = userList.get(RandomUtils.nextInt(0,userList.size()));
                int type = RandomUtils.nextInt(1,4);
                BigDecimal amount = BigDecimal.valueOf(RandomUtils.nextInt(10,100));
                Recharge recharge = Recharge.builder().userId(user.getId()).type(type).amount(amount).build();
                mockMvc.perform(post("/recharge").contentType(MediaType.APPLICATION_JSON).content(JSON.toJSONString(recharge))).andDo(print()).andReturn();
            }
        }
    }

    /**
     * 随机选择缴纳过押金的普通账户借车还车
     */
    @Test
    public void batchJourney() throws Exception{
        List<User> userList = userService.selectList(new EntityWrapper<User>().eq("deposit_balance",99.0).ge("account_balance",0).eq("type",1));
        List<Bicycle> bicycleList = bicycleService.selectList(new EntityWrapper<Bicycle>().eq("status",1).isNotNull("city_id"));
        for (int i = 0; i < 400; i++) {
            Bicycle selectedBicycle = bicycleList.get(RandomUtils.nextInt(0, bicycleList.size()));
            User selectedUser = userList.get(RandomUtils.nextInt(0, userList.size()));
            //borrow bicycle
            Bicycle borrowBicycle = Bicycle.builder().build();
            String borrowContent = JSON.toJSONString(borrowBicycle);
            mockMvc.perform(get("/bicycles/borrow/" + selectedBicycle.getId()).contentType(MediaType.APPLICATION_JSON).content(borrowContent).sessionAttr("userid",selectedUser.getId())).andDo(print()).andReturn();
            //return bicycle
//            City currentCity = cityService.selectById(selectedBicycle.getCityId());
//            City parentCity = cityService.selectById(currentCity.getParentId());
//            List<City> cities = cityService.selectList(new EntityWrapper<City>().eq("parent_id", parentCity.getParentId()));
//            List<Integer> cityIds = new ArrayList<>();
//            for (City city : cities) {
//                cityIds.add(city.getId());
//            }
//            List<Area> areas = areaService.selectList(new EntityWrapper<Area>().in("city_id", cityIds));
            List<Area> areas = areaService.selectList(new EntityWrapper<Area>().eq("city_id",selectedBicycle.getCityId()));
            Area area = areas.get(RandomUtils.nextInt(0, areas.size()));
            BigDecimal endLocationX = BigDecimal.valueOf(RandomUtils.nextDouble(area.getWestPoint().doubleValue(), area.getEastPoint().doubleValue()));
            BigDecimal endLocationY = BigDecimal.valueOf(RandomUtils.nextDouble(area.getSouthPoint().doubleValue(), area.getNorthPoint().doubleValue()));
            Journey formerJourney = journeyService.getContinuedJourneys(selectedUser.getId()).get(0);
            LocalDateTime startTimeValue =  LocalDateTime.ofInstant(Instant.ofEpochSecond(Long.parseLong(formerJourney.getStartTime())), ZoneId.of("Asia/Shanghai"));
            long rideTimeValue = RandomUtils.nextLong(0, 4800);
            LocalDateTime endTimeValue = startTimeValue.plusSeconds(rideTimeValue);
            String endTime = TypeUtils.castToString(endTimeValue.toEpochSecond(ZoneOffset.ofHours(8)));
            Journey journey = Journey.builder().endLocationX(endLocationX).endLocationY(endLocationY).endTime(endTime).build();
            String returnContent = JSON.toJSONString(journey);
            mockMvc.perform(post("/bicycles/return/" + selectedBicycle.getId()).contentType(MediaType.APPLICATION_JSON).content(returnContent).sessionAttr("userid",selectedUser.getId())).andDo(print()).andReturn();
        }

    }

    /**
     * 随机生成车辆维护任务并完成
     */
    @Test
    public void batchTask() throws Exception{
        List<Bicycle> bicycleList = bicycleService.selectAllSimple();
        List<User> staffList = userService.getAllStaff();
        for (int i = 0; i < 30; i++) {
            Task task = new Task();
            LocalDateTime startTime = DataUtil.generateRandomStartTime();
            task.setStartTime(TypeUtils.castToString(startTime.toEpochSecond(ZoneOffset.ofHours(8))));
            task.setBicycle(bicycleList.get(RandomUtils.nextInt(0, bicycleList.size())).getId());
            int taskType = RandomUtils.nextInt(1,3);
//            int taskType = 3;
            task.setType(taskType);
            task.setUser(staffList.get(RandomUtils.nextInt(0,staffList.size())).getId());
            task.setStatus(3);
            task.setEndTime(TypeUtils.castToString(startTime.plusHours(RandomUtils.nextLong(0L,72L)).toEpochSecond(ZoneOffset.ofHours(8))));
            task.setName(TaskTypeEnum.getByCode(taskType).getType() + task.getBicycle());
//            Bicycle bicycle = new Bicycle();
//            bicycle.setId(task.getBicycle());
//            bicycle.setStatus(BicycleStatusEnum.WAIT_DELETE.getCode());
//            bicycleService.updateById(bicycle);
            taskService.insert(task);
        }
    }



    /**
     * 保存各种生成的基础模拟数据到数据库
     */
    @Test
    public void insertBasicMockData(){
        cityService.insertBatch(mockCity());
        userService.insertBatch(mockUsers());
        supplierService.insertBatch(mockSuppliers());
        //停车点需手动添加
        bicycleService.insertBatch(mockBicycles());
    }

    @Test
    public void test(){
        List<Journey> journeyList = journeyService.selectList(new EntityWrapper<Journey>().le("end_time",1496246400));
        int count = 0;
        for (Journey journey : journeyList) {
            System.out.println("第" + (++count) + "次");
            LocalDateTime startTimeValue = LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.valueOf(journey.getStartTime())), ZoneId.of("Asia/Shanghai")).plusMonths(8);
            LocalDateTime endTimeValue = LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.valueOf(journey.getEndTime())), ZoneId.of("Asia/Shanghai")).plusMonths(8);
            System.out.println("startTime-->" + startTimeValue + "---endTime-->" + endTimeValue);
            journey.setStartTime(TypeUtils.castToString(startTimeValue.toEpochSecond(ZoneOffset.ofHours(8))));
            journey.setEndTime(TypeUtils.castToString(endTimeValue.toEpochSecond(ZoneOffset.ofHours(8))));
            journeyService.updateById(journey);
        }

        for (int i = 0; i < 200; i++) {
            System.out.println(DataUtil.generateRandomStartTimeString());
        }

    }


    @Test
    public void fixJourney(){
        List<Journey> journeyList = journeyService.selectList(new EntityWrapper<>());
        for(Journey journey : journeyList){
            Area startArea = areaService.findArea(journey.getStartLocationX(), journey.getStartLocationY());
            Area endArea = areaService.findArea(journey.getEndLocationX(), journey.getEndLocationY());
            if(startArea != null && endArea != null){
                journey.setStartArea(startArea.getId());
                journey.setEndArea(endArea.getId());
                journeyService.updateById(journey);
            }
        }
    }


    @Test
    public void fixTask(){
        List<Task> taskList = taskService.selectList(null);
        List<Integer> areaList = Arrays.asList(31,32,33,34,35,36,37,38,39,40,41,42,43,44);//河南所有区
        for(Task task : taskList){
            taskService.updateById(Task.builder().id(task.getId()).area(areaList.get(RandomUtils.nextInt(0, areaList.size()))).build());
        }
    }

}
