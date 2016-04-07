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
//    private String video_url;
//    private String tag;
//    private String time;

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

//    public String getVideo_url() {
//        return video_url;
//    }
//
//    public void setVideo_url(String video_url) {
//        this.video_url = video_url;
//    }
//
//    public String getTag() {
//        return tag;
//    }
//
//    public void setTag(String tag) {
//        this.tag = tag;
//    }
//
//    public String getTime() {
//        return time;
//    }
//
//    public void setTime(String time) {
//        this.time = time;
//    }

    @Override
    public String toString() {
        return "DisCovery{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", video_img='" + video_img + '\'' +
//                ", video_url='" + video_url + '\'' +
//                ", tag='" + tag + '\'' +
//                ", time='" + time + '\'' +
                '}';
    }
}
