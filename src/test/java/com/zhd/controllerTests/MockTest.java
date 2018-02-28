package com.zhd.controllerTests;

import com.zhd.BssApplicationTests;
import com.zhd.mapper.SupplierMapper;
import com.zhd.service.IUserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

public class MockTest extends BssApplicationTests {

    @Autowired
    private SupplierMapper supplierMapper;
    @Autowired
    private IUserService userService;
    @Test
    public void getAllSupplier() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/suppliers/all")).andDo(MockMvcResultHandlers.print()).andReturn();
    }

    @Test
    public void gelAllStaffs(){
        System.out.println(userService.getAllStaff());
    }

}
