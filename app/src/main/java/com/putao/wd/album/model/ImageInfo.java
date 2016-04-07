package com.putao.wd.album.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/4/6.
 */
public class ImageInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    public String _ID;
    public String _DATA;
    public long _SIZE;
    public String TITLE;//不带扩展名的文件名
    public String DATE_ADDED;
    public String DATE_MODIFIED;
    public String BUCKET_ID;
    public String BUCKET_DISPLAY_NAME;//文件夹名
    public String THUMB_DATA;//缩略图路径
    public boolean isSelect = false;

    @Override
    public String toString() {
        return "PhotoInfo{" +
                "_ID=" + _ID +
                ", _DATA=" + _DATA +
                ", _SIZE=" + _SIZE +
                ", TITLE=" + TITLE +
                ", DATE_ADDED=" + DATE_ADDED +
                ", DATE_MODIFIED=" + DATE_MODIFIED +
                ", BUCKET_ID=" + BUCKET_ID +
                ", BUCKET_DISPLAY_NAME=" + BUCKET_DISPLAY_NAME +
                ", THUMB_DATA=" + THUMB_DATA +
                '}';
    }
}
