package com.putao.wd.dto;

import java.io.Serializable;

/**
 * Created by wango on 2015/12/2.
 */
public class ControlProductItem implements Serializable {
    private String name;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    private String uri;

    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String selected;

    @Override
    public String toString() {
        return "ProductItem{" +
                "name='" + name + '\'' +
                "uri='" + uri + '\'' +
                ", selected='" + selected + '\'' +
                '}';
    }
}
