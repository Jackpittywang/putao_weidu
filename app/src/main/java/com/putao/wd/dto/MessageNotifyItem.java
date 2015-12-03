package com.putao.wd.dto;

import java.io.Serializable;

/**
 * 消息通知列表item
 * Created by wango on 2015/12/1.
 */
public class MessageNotifyItem implements Serializable {
    private String id;      //消息ID
    private String iconUrl;//头像url
    private String title;//标题
    private String date;//时间
    private String intro;//内容介绍


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
