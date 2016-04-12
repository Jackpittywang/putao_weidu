package com.putao.wd.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by zhanghao on 2015/12/8.
 */
public class ServiceMessage implements Serializable {
    private ArrayList<ServiceMessageList> lists;

    public ArrayList<ServiceMessageList> getLists() {
        return lists;
    }

    public void setLists(ArrayList<ServiceMessageList> lists) {
        this.lists = lists;
    }
}
