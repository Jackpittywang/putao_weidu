package com.putao.wd.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/5/8.
 */
public class Campaign implements Serializable {
    private ResourceTag tag_info;
    private List<Resources> resources;

    public ResourceTag getTag_info() {
        return tag_info;
    }

    public void setTag_info(ResourceTag tag_info) {
        this.tag_info = tag_info;
    }

    public List<Resources> getResources() {
        return resources;
    }

    public void setResources(List<Resources> resources) {
        this.resources = resources;
    }

    @Override
    public String toString() {
        return "Campaign{" +
                "tag_info=" + tag_info +
                ", resources=" + resources +
                '}';
    }
}
