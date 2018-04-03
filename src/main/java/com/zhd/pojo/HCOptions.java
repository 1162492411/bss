package com.zhd.pojo;

import lombok.Builder;
import lombok.Data;

/**
 * HighCharts的Options对象的值
 */
@Data
@Builder
public class HCOptions {

    private String title;//标题

    private String subtitle;//副标题

    

}
