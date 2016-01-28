package com.putao.wd.model;

import java.io.Serializable;
import java.util.List;

/**
 * 成长日记首页
 * Created by zhanghao on 2015/12/8.
 */
public class Create implements Serializable {
    private String id;
    private String uid;
    private int type;
    private String title;
    private String cover;
    private String descrip;
    private String content;
    private int schedule_curr;
    private int schedule_count;
    private int display;
    private int created_at;
    private int updated_at;
    private String tag;
    private CreateVote vote;
    private int vote_status;
    private CreateComment comment;
    private String avatar;
    private String nickname;
    private int follow_status;

    public String  getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
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

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getSchedule_curr() {
        return schedule_curr;
    }

    public void setSchedule_curr(int schedule_curr) {
        this.schedule_curr = schedule_curr;
    }

    public int getSchedule_count() {
        return schedule_count;
    }

    public void setSchedule_count(int schedule_count) {
        this.schedule_count = schedule_count;
    }

    public int getDisplay() {
        return display;
    }

    public void setDisplay(int display) {
        this.display = display;
    }

    public CreateVote getVote() {
        return vote;
    }

    public void setVote(CreateVote vote) {
        this.vote = vote;
    }

    public int getVote_status() {
        return vote_status;
    }

    public void setVote_status(int vote_status) {
        this.vote_status = vote_status;
    }

    public CreateComment getComment() {
        return comment;
    }

    public void setComment(CreateComment comment) {
        this.comment = comment;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getFollow_status() {
        return follow_status;
    }

    public void setFollow_status(int follow_status) {
        this.follow_status = follow_status;
    }

    @Override
    public String toString() {
        return "Create{" +
                "id='" + id + '\'' +
                ", uid='" + uid + '\'' +
                ", type=" + type +
                ", title='" + title + '\'' +
                ", cover='" + cover + '\'' +
                ", descrip='" + descrip + '\'' +
                ", content='" + content + '\'' +
                ", schedule_curr=" + schedule_curr +
                ", schedule_count=" + schedule_count +
                ", display=" + display +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                ", tag='" + tag + '\'' +
                ", vote=" + vote +
                ", vote_status=" + vote_status +
                ", comment=" + comment +
                ", avatar='" + avatar + '\'' +
                ", nickname='" + nickname + '\'' +
                ", follow_status=" + follow_status +
                '}';
    }
}
