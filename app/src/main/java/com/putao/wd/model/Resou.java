package com.putao.wd.model;

import android.graphics.drawable.Drawable;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/5/5.
 */
public class Resou implements Serializable {
    public String pic;
    public boolean isBig;
    public boolean isTop;
    public String title;
    public List<String> tags;

    public Resou(boolean isBig, boolean isTop, String pic, List<String> tags, String title) {
        this.isBig = isBig;
        this.isTop = isTop;
        this.pic = pic;
        this.tags = tags;
        this.title = title;
    }
}
