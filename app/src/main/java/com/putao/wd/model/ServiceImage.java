package com.putao.wd.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/5/12.
 */
public class ServiceImage implements Serializable {
    private String thumb;//缩略图
    private String pic;//大图

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }
}
