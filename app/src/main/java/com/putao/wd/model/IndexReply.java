package com.putao.wd.model;

/**
 * 创造回复
 * Created by Administrator on 2015/2/3
 */
public class IndexReply extends Page {
    private String user_id;
    private String user_name;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    @Override
    public String toString() {
        return "IndexReply{" +
                "user_id='" + user_id + '\'' +
                ", user_name='" + user_name + '\'' +
                '}';
    }
}
