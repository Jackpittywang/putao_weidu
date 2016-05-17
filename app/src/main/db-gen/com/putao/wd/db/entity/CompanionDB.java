package com.putao.wd.db.entity;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 

import com.sunnybear.library.model.db.DBEntity;

/**
 * Entity mapped to table "putao_wd_city".
 */
public class CompanionDB extends DBEntity {

    private String id;
    private String type;
    private String release_time;
    private String content_lists;
    private String isDownload;
    private String service_id;
    private String uid;
    private String key;
    private String message;
    private String image;
    private String reply;
    private String is_upload_finish;
    private String receiver_time;

    public CompanionDB(String id, String service_id, String type, String release_time, String content_lists, String isDownload, String uid, String key, String message, String image, String reply, String is_upload_finish, String receiver_time) {
        this.id = id;
        this.service_id = service_id;
        this.type = type;
        this.release_time = release_time;
        this.content_lists = content_lists;
        this.isDownload = isDownload;
        this.uid = uid;
        this.key = key;
        this.message = message;
        this.image = image;
        this.reply = reply;
        this.is_upload_finish = is_upload_finish;
        this.receiver_time = receiver_time;
    }

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

    public String getRelease_time() {
        return release_time;
    }

    public void setRelease_time(String release_time) {
        this.release_time = release_time;
    }

    public String getContent_lists() {
        return content_lists;
    }

    public void setContent_lists(String content_lists) {
        this.content_lists = content_lists;
    }

    public String getIsDownload() {
        return isDownload;
    }

    public void setIsDownload(String isDownload) {
        this.isDownload = isDownload;
    }

    public String getService_id() {
        return service_id;
    }

    public void setService_id(String service_id) {
        this.service_id = service_id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public String getIs_upload_finish() {
        return is_upload_finish;
    }

    public void setIs_upload_finish(String is_upload_finish) {
        this.is_upload_finish = is_upload_finish;
    }

    public String getReceiver_time() {
        return receiver_time;
    }

    public void setReceiver_time(String receiver_time) {
        this.receiver_time = receiver_time;
    }
}
