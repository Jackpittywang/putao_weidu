package com.putao.wd.model;

import com.sunnybear.library.view.select.Tag;

import java.io.Serializable;
import java.util.List;

/**
 * 规格
 * Created by guchenkai on 2015/12/16.
 */
public class Norms implements Serializable {
    private String spec_item_id;
    private String title;
    private List<Tag> tags;

    public String getSpec_item_id() {
        return spec_item_id;
    }

    public void setSpec_item_id(String spec_item_id) {
        this.spec_item_id = spec_item_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "Norms{" +
                "spec_item_id='" + spec_item_id + '\'' +
                ", title='" + title + '\'' +
                ", tags=" + tags +
                '}';
    }
}
