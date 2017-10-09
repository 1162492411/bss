package com.zhd.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * 分页类，包含分页信息及数据信息
 * Created by Mo on 2017/9/24.
 */
public class Page implements Serializable{
    private static int DEFAULT_PAGE_SIZE = 10;//默认每页数量

    private int totalCount;//总数

    private int pageSize = DEFAULT_PAGE_SIZE;//每页数量

    private int totalPage;//总页数

    private int currentPage;//当前页数

    private List datas;//分页的数据

    private List keys;//键列表

    public int getTotalCount() {
        return totalCount;
    }

    public Page setTotalCount(int totalCount) {
        this.totalCount = totalCount;
        setTotalPage(this.totalCount);
        return this;
    }

    public int getPageSize() {
        return pageSize;
    }

    public Page setPageSize(int pageSize) {
        this.pageSize = pageSize;
        setTotalPage(this.totalCount);
        return this;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public Page setTotalPage(int totalCount) {
        if(totalCount == 0)
            this.totalPage = 0;
        else
            this.totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1 ;
        return this;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public Page setCurrentPage(int page) {
        if(this.totalPage == 0)
            this.currentPage = 0;
        else
            this.currentPage = page < 1 ? 1 : page > this.totalPage? this.totalPage : page;
        return this;
    }

    public List getDatas() {
        return datas;
    }

    public List getKeys() {
        return keys;
    }

    public Page setKeys(List keys) {
        this.keys = keys;
        return this;
    }

    public Page setDatas(List datas) {
        this.datas = datas;
        return this;
    }

    //获取合理的起始位置--用于dao/service层
    public int getStart(){
        return currentPage <= 0 ? 0 : (currentPage - 1) * pageSize;
    }
}
