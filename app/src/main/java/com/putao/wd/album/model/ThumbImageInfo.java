package com.putao.wd.album.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/4/6.
 */
public class ThumbImageInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    public String _ID;
    public String THUMB_ID;
    public String THUMB_DATA;
    public boolean isSelect = false;

    @Override
    public String toString() {
        return "ThumbImageInfo{" +
                "THUMB_ID=" + THUMB_ID +
                ", THUMB_DATA=" + THUMB_DATA +
                ", _ID=" + _ID +
                '}';
    }
}
