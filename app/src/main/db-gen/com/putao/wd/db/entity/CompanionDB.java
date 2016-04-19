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

    public CompanionDB(String id, String service_id, String type, String release_time, String content_lists, String isDownload, String uid, String key) {
        this.id = id;
        this.type = type;
        this.release_time = release_time;
        this.content_lists = content_lists;
        this.isDownload = isDownload;
        this.service_id = service_id;
        this.uid = uid;
        this.key = key;
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
}
