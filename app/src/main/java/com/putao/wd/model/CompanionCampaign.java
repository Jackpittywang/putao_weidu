package com.putao.wd.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/4/6.
 */
public class CompanionCampaign implements Serializable {

    private String type;
    private int id;
    private String time;
    private String icon;
    private String title;
    private String subtitle;
    private int timeState;

    private boolean showDate;

    private Tag tags;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isShowDate() {
        return showDate;
    }

    public void setShowDate(boolean showDate) {
        this.showDate = showDate;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public Tag getTags() {
        return tags;
    }

    public void setTags(Tag tags) {
        this.tags = tags;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getTimeState() {
        return timeState;
    }

    public void setTimeState(int timeState) {
        this.timeState = timeState;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "CompanionCampaign{" +
                "icon='" + icon + '\'' +
                ", type='" + type + '\'' +
                ", id=" + id +
                ", time='" + time + '\'' +
                ", title='" + title + '\'' +
                ", subtitle='" + subtitle + '\'' +
                ", timeState=" + timeState +
                ", showDate=" + showDate +
                ", tags=" + tags +
                '}';
    }
}
