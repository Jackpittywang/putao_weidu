package com.putao.wd.model;

import java.io.Serializable;

/**
 * 商城首页--首页广告栏
 * Created by guchenkai on 2015/12/8.
 */
public class StoreBanner implements Serializable {
    private String id;//BannerId
    private String item_id;//商品id
    private String title;//Banner标题
    private String url;//商品url链接
    private String image;//图片资源链接

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "StoreBanner{" +
                "id='" + id + '\'' +
                ", item_id='" + item_id + '\'' +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
