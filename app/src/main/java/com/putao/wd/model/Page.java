package com.putao.wd.model;

import java.io.Serializable;

/**
 * 分页
 * Created by guchenkai on 2015/12/18.
 */
public class Page implements Serializable {
    private int total_page;
    private int current_page;

    public int getTotal_page() {
        return total_page;
    }

    public void setTotal_page(int total_page) {
        this.total_page = total_page;
    }

    public int getCurrent_page() {
        return current_page;
    }

    public void setCurrent_page(int current_page) {
        this.current_page = current_page;
    }
}
