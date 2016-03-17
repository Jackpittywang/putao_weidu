package com.putao.wd.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/3/17.
 */
public class PicList implements Serializable {
    private String src;
    private String text = "";

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
