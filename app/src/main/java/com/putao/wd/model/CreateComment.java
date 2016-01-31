package com.putao.wd.model;

import java.io.Serializable;
import java.util.List;

/**
 * 成长日记首页
 * Created by zhanghao on 2015/12/8.
 */
public class CreateComment implements Serializable {
    private String id;
    private String create_id;
    private String uid;
    private String content;
    private String comment_source;
    private int type;
    private int status;
    private int created_at;
    private int updated_at;
    private String username;
    private String nickname;
    private String real_avatar;
    private int like_count;
    private boolean like_status;
    private int comment_reply_count;
    private List<String> reply;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreate_id() {
        return create_id;
    }

    public void setCreate_id(String create_id) {
        this.create_id = create_id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getComment_source() {
        return comment_source;
    }

    public void setComment_source(String comment_source) {
        this.comment_source = comment_source;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCreated_at() {
        return created_at;
    }

    public void setCreated_at(int created_at) {
        this.created_at = created_at;
    }

    public int getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(int updated_at) {
        this.updated_at = updated_at;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getReal_avatar() {
        return real_avatar;
    }

    public void setReal_avatar(String real_avatar) {
        this.real_avatar = real_avatar;
    }

    public int getLike_count() {
        return like_count;
    }

    public void setLike_count(int like_count) {
        this.like_count = like_count;
    }

    public boolean isLike_status() {
        return like_status;
    }

    public void setLike_status(boolean like_status) {
        this.like_status = like_status;
    }

    public int getComment_reply_count() {
        return comment_reply_count;
    }

    public void setComment_reply_count(int comment_reply_count) {
        this.comment_reply_count = comment_reply_count;
    }

    public List<String> getReply() {
        return reply;
    }

    public void setReply(List<String> reply) {
        this.reply = reply;
    }

    @Override
    public String toString() {
        return "CreateComment{" +
                "id='" + id + '\'' +
                ", create_id='" + create_id + '\'' +
                ", uid='" + uid + '\'' +
                ", content='" + content + '\'' +
                ", comment_source='" + comment_source + '\'' +
                ", type=" + type +
                ", status=" + status +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                ", username='" + username + '\'' +
                ", nickname='" + nickname + '\'' +
                ", real_avatar='" + real_avatar + '\'' +
                ", like_count=" + like_count +
                ", like_status=" + like_status +
                ", comment_reply_count=" + comment_reply_count +
                ", reply=" + reply +
                '}';
    }
}
