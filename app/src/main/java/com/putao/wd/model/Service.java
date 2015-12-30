package com.putao.wd.model;


import java.io.Serializable;
import java.util.List;

/**
 * 售后
 * Created by guchenkai on 2015/12/9.
 */
public class Service implements Serializable {

    private int   currentPage;
    private int   totalPage;
    private List<ServiceList> data;

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<ServiceList> getData() {
        return data;
    }

    public void setData(List<ServiceList> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Service{" +
                "currentPage=" + currentPage +
                ", totalPage=" + totalPage +
                ", data=" + data +
                '}';
    }

}
