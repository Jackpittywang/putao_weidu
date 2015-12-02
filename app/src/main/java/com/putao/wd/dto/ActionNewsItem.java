package com.putao.wd.dto;

import java.io.Serializable;

/**
 * 活动和新闻列表
 * Created by guchenkai on 2015/12/2.
 */
public class ActionNewsItem implements Serializable {
    private String id;
    private String title;
    private String intro;
    private String address;
    private String time;
    private String joinCount;
    private String imgUrl;
    private boolean isAction;
    private boolean isFinish;
    private String type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getJoinCount() {
        return joinCount;
    }

    public void setJoinCount(String joinCount) {
        this.joinCount = joinCount;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public boolean isAction() {
        return isAction;
    }

    public void setIsAction(boolean isAction) {
        this.isAction = isAction;
    }

    public boolean isFinish() {
        return isFinish;
    }

    public void setIsFinish(boolean isFinish) {
        this.isFinish = isFinish;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "ActionNewsItem{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", intro='" + intro + '\'' +
                ", address='" + address + '\'' +
                ", time='" + time + '\'' +
                ", joinCount='" + joinCount + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", isAction=" + isAction +
                ", isFinish=" + isFinish +
                ", type='" + type + '\'' +
                '}';
    }
}
