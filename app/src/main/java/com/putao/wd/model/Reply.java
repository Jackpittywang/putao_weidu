package com.putao.wd.model;

import java.util.List;

/**
 * 通知--回复
 * Created by Administrator on 2015/12/25.
 */
public class Reply extends Page {
    private List<ReplyDetail> reply;
    private int total_page;
    private int current_page;

    public List<ReplyDetail> getReply() {
        return reply;
    }

    public void setReply(List<ReplyDetail> reply) {
        this.reply = reply;
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
                "data=" + reply +
                ", total_page=" + total_page +
                ", current_page=" + current_page +
                '}';
    }
}
