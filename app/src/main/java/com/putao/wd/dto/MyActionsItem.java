package com.putao.wd.dto;

import java.io.Serializable;

/**
 * Created by wango on 2015/12/3.
 */
@Deprecated
public class MyActionsItem implements Serializable {
    private String id;
    private String title;
    private String introduction;
    private String actionIcon;
    private String status;

    public MyActionsItem() {
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntroduction() {
        return this.introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getActionIcon() {
        return this.actionIcon;
    }

    public void setActionIcon(String actionIcon) {
        this.actionIcon = actionIcon;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String toString() {
        return "ProductItem{id=\'" + this.id + '\'' + "title=\'" + this.title + '\'' + "introduction=\'" + this.introduction + '\'' + "actionIcon=\'" + this.actionIcon + '\'' + "status=\'" + this.status + '\'' + '}';
    }
}
