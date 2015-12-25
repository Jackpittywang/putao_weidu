package com.putao.wd.model;

import java.io.Serializable;

/**
 * 回复详情
 * Created by Administrator on 2015/12/25.
 */
public class ReplyDetail implements Serializable {
    private String content;//评论内容
    private String user_profile_photo;//用户头像
    private String user_name;//用户名
    private String modified_time;//评论时间
    private String user_id;//用户id

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUser_profile_photo() {
        return user_profile_photo;
    }

    public void setUser_profile_photo(String user_profile_photo) {
        this.user_profile_photo = user_profile_photo;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getModified_time() {
        return modified_time;
    }

    public void setModified_time(String modified_time) {
        this.modified_time = modified_time;
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
                ", user_profile_photo='" + user_profile_photo + '\'' +
                ", user_name='" + user_name + '\'' +
                ", modified_time='" + modified_time + '\'' +
                ", user_id='" + user_id + '\'' +
                '}';
    }
}
