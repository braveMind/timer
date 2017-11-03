package com.jun.timer.dto;

import java.io.Serializable;

/**
 * Created by xunaiyang on 2017/7/11.
 */
public class Page implements Cloneable,Serializable {

    private static int DEFAULT_PAGESIZE = 15;

    private static int DEFAULT_PAGE = 1;

    private int pageNo = DEFAULT_PAGE;

    private int pageSize = DEFAULT_PAGESIZE;

    private int totalCount = 0;

    private int totalPageCount = 1;

    public Page() {
    }

    public Page(Integer pageNo) {
        this.pageNo = pageNo;
        this.pageSize = DEFAULT_PAGESIZE;
    }

    public Page(Integer pageNo, Integer pageSize) {
        this.pageNo = pageNo;
        this.pageSize = pageSize == null ? DEFAULT_PAGESIZE : pageSize;
    }

    public Page(Integer pageNo, Integer pageSize, Integer totalCount) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
    }

    /**
     * 总页数.
     *
     */
    public int getTotalPageCount() {
        calculateTotalPageCount();
        return totalPageCount;
    }

    public void calculateTotalPageCount() {
        totalPageCount = totalCount / pageSize;
        if (totalCount % pageSize > 0) {
            totalPageCount++;
        }

        // 校正页码
        if (pageNo > totalPageCount) {
            pageNo = totalPageCount;
        }
        if (pageNo < 1) {
            pageNo = 1;
        }
    }

    /**
     * 每页的记录数量.
     *
     */
    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * 当前页的页号,序号从1开始.
     *
     */
    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int page) {
        this.pageNo = page;
    }

    /**
     * 总记录数量.
     *
     */
    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
        // 计算总页数
        calculateTotalPageCount();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Page clone = (Page) super.clone();
        return clone;
    }

}