package com.putao.wd.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhanghao on 2015/12/8.
 */
public class ServiceMessageList implements Serializable {
    private String id;
    private String type;
    private int release_time;
    //    private boolean isShowData;
    private List<ServiceMessageContent> content_lists;
    private String message;
    private ServiceMessageListImage image;
    private ServiceMessageListReply reply;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getRelease_time() {
        return release_time;
    }

    public void setRelease_time(int release_time) {
        this.release_time = release_time;
    }

    public List<ServiceMessageContent> getContent_lists() {
        return content_lists;
    }

    public void setContent_lists(List<ServiceMessageContent> content_lists) {
        this.content_lists = content_lists;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ServiceMessageListImage getImage() {
        return image;
    }

    public void setImage(ServiceMessageListImage image) {
        this.image = image;
    }

    public ServiceMessageListReply getReply() {
        return reply;
    }

    public void setReply(ServiceMessageListReply reply) {
        this.reply = reply;
    }
}
