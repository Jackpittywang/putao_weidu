package com.putao.wd.model;

/**
 * 评论回复
 * Created by yanghx on 2015/12/15.
 */
public class CommentReply {
    private String user_id;//回复用户Id
    private String user_name;//回复用户昵称

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
        return "CommentReply{" +
                "user_id='" + user_id + '\'' +
                ", user_name='" + user_name + '\'' +
                '}';
    }
}
