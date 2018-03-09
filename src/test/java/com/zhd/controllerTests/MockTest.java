package com.zhd.controllerTests;

import static com.alibaba.fastjson.JSON.*;
import com.zhd.BssTestEnvironment;
import com.zhd.pojo.User;
import org.junit.Test;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

/**
 * mockMVc测试类，用于测试各种接口
 */
public class MockTest extends BssTestEnvironment {

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
    public void testRecharges() throws Exception{
        mockMvc.perform(get("/rechargeAccount/list/1").sessionAttr("userid","23456789011")).andDo(print()).andReturn();
    }




//    @Test
//    public void testBorrowBicycle() throws Exception{
//        mockMvc.perform(post("/bicycles/borrow/2").sessionAttr("userid","23456789011")).andDo(print()).andReturn();
//    }

}
