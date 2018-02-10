package com.zhd.pojo;

import com.baomidou.mybatisplus.plugins.Page;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Page的增强类,显示了records的总数,添加类设置pages的方法(复制信息用，见PageUti)
 * @param <T>
 */
public class PageInfo<T> extends Page<T> {

    @Setter
    @Getter
    private int recordsSize;

    @Setter
    private int pages;

}
