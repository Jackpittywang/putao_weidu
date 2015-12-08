package com.putao.wd.model;

import java.io.Serializable;

/**
 * 精彩回顾--活动详情
 * Created by guchenkai on 2015/12/8.
 */
public class WonderfulReview implements Serializable {
    private String type;//精彩回顾的类型，2种值
    private String src;//图片地址

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    @Override
    public String toString() {
        return "WonderfulReview{" +
                "type='" + type + '\'' +
                ", src='" + src + '\'' +
                '}';
    }
}
