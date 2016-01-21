package com.putao.wd.model;

import java.io.Serializable;

/**
 * 广告banner列表
 * Created by zhanghao on 2015/1/21
 */
public class ExploreBanner implements Serializable {
    private String type;//banner类型  IMAGE或VIDEO
    private String url;//banner地址
    private String cover_pic;//如果是VIDEO的banner贴图

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCover_pic() {
        return cover_pic;
    }

    public void setCover_pic(String cover_pic) {
        this.cover_pic = cover_pic;
    }
}

