package com.putao.wd.model;

import android.graphics.drawable.Drawable;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/5/4.
 */
public class DiscoveryResource implements Serializable {

    public List<String> headerPic;
    public List<String> hotTagPic;
    public List<Resou> resources;

    public DiscoveryResource(List<String> headerPic, List<String> hotTagPic, List<Resou> resources) {
        this.headerPic = headerPic;
        this.hotTagPic = hotTagPic;
        this.resources = resources;
    }
}
