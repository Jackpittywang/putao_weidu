package com.putao.wd.model;

import java.util.List;

/**
 * 通知--提醒
 * Created by Administrator on 2015/12/25.
 */
public class Remind extends Page {
    private List<RemindDetail> list;

    public List<RemindDetail> getList() {
        return list;
    }

    public void setList(List<RemindDetail> data) {
        this.list = data;
    }

    @Override
    public String toString() {
        return "Remind{" +
                "list=" + list +
                '}';
    }
}
