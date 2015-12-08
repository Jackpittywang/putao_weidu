package com.putao.wd.dto;

import java.io.Serializable;

/**
 * 评论列表
 * Created by yanghx on 2015/12/8.
 */
@Deprecated
public class CommentItem implements Serializable {
    private String id;
    private String status;
    private String iconUrl;
    private String username;
    private String comment;
    private String time;
    private String comment_count;
    private String support_count;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSupport_count() {
        return support_count;
    }

    public void setSupport_count(String support_count) {
        this.support_count = support_count;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getComment_count() {
        return comment_count;
    }

    public void setComment_count(String comment_count) {
        this.comment_count = comment_count;
    }

    @Override
    public String toString() {
        return "CommentItem{" +
                "id='" + id + '\'' +
                ", status='" + status + '\'' +
                ", iconUrl='" + iconUrl + '\'' +
                ", username='" + username + '\'' +
                ", comment='" + comment + '\'' +
                ", time='" + time + '\'' +
                ", comment_count='" + comment_count + '\'' +
                ", support_count='" + support_count + '\'' +
                '}';
    }

}


