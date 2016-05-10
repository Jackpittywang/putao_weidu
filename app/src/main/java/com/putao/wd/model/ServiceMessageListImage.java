package com.putao.wd.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhanghao on 2015/12/8.
 */
public class ServiceMessageListImage implements Serializable {
    private String thumb;
    private String pic;

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}
