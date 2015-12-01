package com.putao.wd.dto;

import java.io.Serializable;

/**
 * Created by wango on 2015/12/1.
 */
public class MessageNotifyItem implements Serializable {
    private String id;
    private String iconUrl;
    private String title;
    private String date;
    private String intro;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "ProductItem{" +
                "id='" + id + '\'' +
                ", iconUrl='" + iconUrl + '\'' +
                ", title='" + title + '\'' +
                ", intro='" + intro + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
