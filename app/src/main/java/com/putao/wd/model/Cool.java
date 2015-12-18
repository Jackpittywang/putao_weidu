package com.putao.wd.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 赞列表
 * Created by yanghx on 2015/12/18.
 */
public class Cool implements Serializable {
    private ArrayList<CoolList> user_list;
    private int visitorLikes;

    public ArrayList<CoolList> getUser_list() {
        return user_list;
    }

    public void setUser_list(ArrayList<CoolList> user_list) {
        this.user_list = user_list;
    }

    public int getVisitorLikes() {
        return visitorLikes;
    }

    public void setVisitorLikes(int visitorLikes) {
        this.visitorLikes = visitorLikes;
    }

    @Override
    public String toString() {
        return "Cool{" +
                "user_list=" + user_list +
                ", visitorLikes=" + visitorLikes +
                '}';
    }
}
