package com.putao.wd.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/5/12.
 */
public class CompainServiceMessage implements Serializable{
    private List<CompainServiceMessage> lists;

    private String id;//推送的数据包id，等同于last_pull_id
    private String type;//推送的数据类型
    private String release_time;//数据包推送的时间
    private List<ContentLists> content_lists;//第一个为默认第一篇文章
    private String message;//我是文章消息，字数不限。
    private ServiceImage image;//图片
    private ServiceReply reply;//回复


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<ContentLists> getContent_lists() {
        return content_lists;
    }

    public void setContent_lists(List<ContentLists> content_lists) {
        this.content_lists = content_lists;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ServiceImage getImage() {
        return image;
    }

    public void setImage(ServiceImage image) {
        this.image = image;
    }

    public List<CompainServiceMessage> getLists() {
        return lists;
    }

    public void setLists(List<CompainServiceMessage> lists) {
        this.lists = lists;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRelease_time() {
        return release_time;
    }

    public void setRelease_time(String release_time) {
        this.release_time = release_time;
    }

    public ServiceReply getReply() {
        return reply;
    }

    public void setReply(ServiceReply reply) {
        this.reply = reply;
    }
}
