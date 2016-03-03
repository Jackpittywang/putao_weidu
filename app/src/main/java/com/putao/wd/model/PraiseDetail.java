package com.putao.wd.model;

import java.io.Serializable;

/**
 * 赞详情
 * Created by Administrator on 2015/12/25.
 */
public class PraiseDetail implements Serializable {
    private String content;//赞的内容
    private String comment_id;//内容ID
    private String user_id;
    private String create_time;
    private String nick_name;
    private String head_img;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getHead_img() {
        return head_img;
    }

    public void setHead_img(String head_img) {
        this.head_img = head_img;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getComment_id() {
        return comment_id;
    }

    public void setComment_id(String comment_id) {
        this.comment_id = comment_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "PraiseDetail{" +
                "content='" + content + '\'' +
                ", comment_id='" + comment_id + '\'' +
                ", user_id='" + user_id + '\'' +
                ", create_time='" + create_time + '\'' +
                ", nick_name='" + nick_name + '\'' +
                ", head_img='" + head_img + '\'' +
                '}';
    }
}
