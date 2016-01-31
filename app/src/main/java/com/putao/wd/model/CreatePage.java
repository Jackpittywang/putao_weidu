package com.putao.wd.model;

import java.io.Serializable;

/**
 * 分页
 * Created by guchenkai on 2015/12/18.
 */
public class CreatePage implements Serializable {
    private int totalPage;
    private int currentPage;

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    @Override
    public String toString() {
        return "CreatePage{" +
                "totalPage=" + totalPage +
                ", currentPage=" + currentPage +
                '}';
    }
}
