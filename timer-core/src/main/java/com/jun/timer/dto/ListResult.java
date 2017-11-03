package com.jun.timer.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xunaiyang on 2017/9/20.
 */
public class ListResult implements Serializable{

    private List data;
    private Integer total;

    public List getData() {
        return data;
    }

    public void setData(List data) {
        this.data = data;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public ListResult(List data, Integer total) {
        this.data = data;
        this.total = total;
    }
}
