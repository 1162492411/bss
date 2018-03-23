package com.zhd.controllerTests;

import com.zhd.BssTestEnvironment;
import org.junit.Test;

public class ServiceTest extends BssTestEnvironment {


    @Test
    public void test(){
        System.out.println(cityService.getSimpleByCode(410000));
    }

}
