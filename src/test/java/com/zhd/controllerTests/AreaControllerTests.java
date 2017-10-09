package com.zhd.controllerTests;

import com.zhd.BssApplicationTests;
import org.junit.Test;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


/**
 * Created by Mo on 2017/9/25.
 */
public class AreaControllerTests extends BssApplicationTests {

    @Test
    public void testHello() throws Exception {
        MvcResult mvcResult =mockMvc.perform(MockMvcRequestBuilders.get("/areas"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();


    }

}
