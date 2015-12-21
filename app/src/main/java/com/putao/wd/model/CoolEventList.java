package com.putao.wd.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by yanghx on 2015/12/20.
 */
public class CoolEventList implements Serializable {
    private ArrayList<CoolList> user_list;

    public ArrayList<CoolList> getUser_list() {
        return user_list;
    }

    public void setUser_list(ArrayList<CoolList> user_list) {
        this.user_list = user_list;
    }

    @Override
    public String toString() {
        return "Cool{" +
                "user_list=" + user_list +
                '}';
    }

}
