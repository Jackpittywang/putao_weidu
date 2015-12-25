package com.putao.wd.model;

import java.util.List;

/**
 * 通知--消息中心
 * Created by Administrator on 2015/12/25.
 */
public class Notify extends Page {
    private List<NotifyDetail> data;

    public List<NotifyDetail> getData() {
        return data;
    }

    public void setData(List<NotifyDetail> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Notify{" +
                "data=" + data +
                '}';
    }
}
