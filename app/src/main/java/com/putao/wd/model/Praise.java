package com.putao.wd.model;

import java.util.List;

/**
 * 通知--赞
 * Created by Administrator on 2015/12/25.
 */
public class Praise extends Page {
    private List<PraiseDetail> like;
    private int total_page;
    private int current_page;

    public List<PraiseDetail> getLike() {
        return like;
    }

    public void setLike(List<PraiseDetail> like) {
        this.like = like;
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
                "like=" + like +
                ", total_page=" + total_page +
                ", current_page=" + current_page +
                '}';
    }
}
