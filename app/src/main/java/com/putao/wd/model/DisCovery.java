package com.putao.wd.model;

import java.io.Serializable;

/**
 * 发现类
 * Created by Administrator on 2016/4/5.
 */
public class DisCovery implements Serializable {
    private String id;
    private String title;
    private String video_img;//视频图片
    private String video_url;
    private String tag;
    private String time;
    private String create_time;
    private String subtitle;
    private String modified_time;
    private String sort;
    private String is_delete;


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

    public String getVideo_img() {
        return video_img;
    }

    public void setVideo_img(String video_img) {
        this.video_img = video_img;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getModified_time() {
        return modified_time;
    }

    public void setModified_time(String modified_time) {
        this.modified_time = modified_time;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getIs_delete() {
        return is_delete;
    }

    public void setIs_delete(String is_delete) {
        this.is_delete = is_delete;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    @Override
    public String toString() {
        return "DisCovery{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", video_img='" + video_img + '\'' +
                ", video_url='" + video_url + '\'' +
                ", tag='" + tag + '\'' +
                ", time='" + time + '\'' +
                ", create_time='" + create_time + '\'' +
                ", subtitle='" + subtitle + '\'' +
                ", modified_time='" + modified_time + '\'' +
                ", sort='" + sort + '\'' +
                ", is_delete='" + is_delete + '\'' +
                '}';
    }
}
