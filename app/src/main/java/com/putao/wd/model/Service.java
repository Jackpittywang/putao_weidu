package com.putao.wd.model;


import java.util.List;

/**
 * 售后
 * Created by guchenkai on 2015/12/9.
 */
public class Service extends Page {

    private List<ServiceList> data;

    public List<ServiceList> getData() {
        return data;
    }

    public void setData(List<ServiceList> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Service{" +
                "data=" + data +
                '}';
    }
}
