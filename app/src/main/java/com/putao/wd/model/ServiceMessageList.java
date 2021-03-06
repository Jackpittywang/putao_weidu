package com.putao.wd.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhanghao on 2015/12/8.
 */
public class ServiceMessageList implements Serializable {
    private String id;
    private String type;
    private int release_time;
    private String message;
    private ServiceMessageListImage image;
    private ServiceMessageListReply reply;
    private int send_state;//0 发送成功 1 发送成功 2 发送失败
    private int isShowData;//0未记录  1 显示 2 不显示
    private List<ServiceMessageContent> content_lists;
    private String service_id;
    private long receiver_time;

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

    public ServiceMessageListImage getImage() {
        return image;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public int getSend_state() {
        return send_state;
    }

    public void setSend_state(int send_state) {
        this.send_state = send_state;
    }

    public int getIsShowData() {
        return isShowData;
    }

    public void setIsShowData(int isShowData) {
        this.isShowData = isShowData;
    }

    public String getService_id() {
        return service_id;
    }

    public void setService_id(String service_id) {
        this.service_id = service_id;
    }

    public long getReceiver_time() {
        return receiver_time;
    }

    public void setReceiver_time(long receiver_time) {
        this.receiver_time = receiver_time;
    }
}
