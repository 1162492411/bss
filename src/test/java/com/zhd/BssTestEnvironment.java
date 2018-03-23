package com.zhd;

import com.alibaba.fastjson.JSON;
import com.zhd.pojo.User;
import com.zhd.service.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class BssTestEnvironment {

	@Autowired
	public MockMvc mockMvc;
	@Autowired
	public IAreaService areaService;
	@Autowired
	public IBicycleService bicycleService;
	@Autowired
	public IDepositService depositService;
	@Autowired
	public IJourneyService journeyService;
	@Autowired
	public ISupplierService supplierService;
	@Autowired
	public ITaskService taskService;
	@Autowired
	public IUserService userService;
	@Autowired
	public ICityService cityService;

	@Test
	public void contextLoads() {
        System.out.println("成功启动测试环境");
    }

//    @Before
    public void adminLogin() throws Exception {
		User user = User.builder().id("23456789011").password("789011").build();
		mockMvc.perform(MockMvcRequestBuilders.post("/login").contentType(MediaType.APPLICATION_JSON).content(JSON.toJSONString(user))).andDo(MockMvcResultHandlers.print()).andReturn();
	}

	@Test
	public void findArea() throws Exception{
		System.out.println(areaService.findArea(BigDecimal.valueOf(34.81766153558023), BigDecimal.valueOf(113.51222709406188)));
	}

	@Test
	public void reduceAccountBalance() throws Exception{
		userService.reduceAccount("23456789011",BigDecimal.valueOf(1.00));
	}
}
