package com.zhd.parameterized;

import com.zhd.controller.AreaController;
import lombok.AllArgsConstructor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.Collection;
import static org.junit.Assert.*;

/**
 * 参数化测试，但仍未能对MockMvc进行参数化测试
 */
@RunWith(Parameterized.class)
@AllArgsConstructor
public class AreaControllerTest {
    private int pageNum;
    private int actualCurrent;


    @Test
    public void test() throws Exception {
        System.out.println("此次测试输入数据为" + pageNum + ",实际数据为" + actualCurrent);
        assertEquals(pageNum,actualCurrent);

    }

    @Parameters
    public static Collection prepareData(){
        return Arrays.asList(new Integer[][]{
                {1,1},
                {2,2},
                {3,3}
        });
    }







}
