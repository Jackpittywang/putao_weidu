package com.putao.wd.dto;

import java.io.Serializable;

/**
 * 广告banner列表
 * Created by guchenkai on 2015/12/3.
 */
public class Banner implements Serializable {
    private String img_url;
    private int numbering;

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public int getNumbering() {
        return numbering;
    }

    public void setNumbering(int numbering) {
        this.numbering = numbering;
    }

    @Override
    public String toString() {
        return "Banner{" +
                "img_url='" + img_url + '\'' +
                ", numbering=" + numbering +
                '}';
    }
}
