package com.zhd.controllerTests;

import com.zhd.mapper.AreaMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * Created by Mo on 2017/9/25.
 */
@RunWith(SpringRunner.class)
//@WebMvcTest(AreaController.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@AutoConfigureRestDocs("target/snippets")
public class AreaControllerTests  {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AreaMapper areaMapper;

    @Test
    public void testHello() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/area/list/{page}",1))
                .andExpect(status().isOk())
                .andDo(document("home"))
                .andReturn();
    }



}
