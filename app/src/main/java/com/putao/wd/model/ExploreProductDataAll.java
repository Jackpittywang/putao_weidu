package com.putao.wd.model;

import java.io.Serializable;

/**
 * 全局数据--数据的图片地址--探索号产品--成长日记首页
 * Created by guchenkai on 2015/12/8.
 */
public class ExploreProductDataAll implements Serializable {
    private String id;//链接跳转详情图
    private String content;//文字介绍

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "ExploreProductDataAll{" +
                "id='" + id + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
