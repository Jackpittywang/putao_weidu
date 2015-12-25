package com.putao.wd.model;

import java.util.List;

/**
 * 通知--赞
 * Created by Administrator on 2015/12/25.
 */
public class Praise extends Page {
    private List<PraiseDetail> likedList;
    private int total_page;
    private int current_page;

    public List<PraiseDetail> getLikedList() {
        return likedList;
    }

    public void setLikedList(List<PraiseDetail> likedList) {
        this.likedList = likedList;
    }

    @Override
    public int getTotal_page() {
        return total_page;
    }

    @Override
    public void setTotal_page(int total_page) {
        this.total_page = total_page;
    }

    @Override
    public int getCurrent_page() {
        return current_page;
    }

    @Override
    public void setCurrent_page(int current_page) {
        this.current_page = current_page;
    }

    @Override
    public String toString() {
        return "Praise{" +
                "likedList=" + likedList +
                ", total_page=" + total_page +
                ", current_page=" + current_page +
                '}';
    }
}
