package com.putao.wd.dto;

import java.io.Serializable;

/**
 * 探索号bean文件
 * Created by yanghx on 2015/12/8.
 */
@Deprecated
public class ExploreItem implements Serializable {

    private String id;
    private String status;
    private String iconUrl;
    private String skill_name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getSkill_name() {
        return skill_name;
    }

    public void setSkill_name(String skill_name) {
        this.skill_name = skill_name;
    }

    @Override
    public String toString() {
        return "ExploreItem{" +
                "id='" + id + '\'' +
                ", status='" + status + '\'' +
                ", iconUrl='" + iconUrl + '\'' +
                ", skill_name='" + skill_name + '\'' +
                '}';
    }

}
