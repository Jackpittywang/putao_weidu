package com.putao.wd.model;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by yanghx on 2015/12/18.
 */
public class ServiceBackImage implements Serializable {
    private Bitmap bitmap;
    private String URL;
    private String sha1;

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getURL() {
        return URL;
    }

    public String getSha1() {
        return sha1;
    }

    public void setSha1(String sha1) {
        this.sha1 = sha1;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    @Override
    public String toString() {
        return "ServiceBackImage{" +
                "bitmap=" + bitmap +
                ", URL='" + URL + '\'' +
                ", sha1='" + sha1 + '\'' +
                '}';
    }
}
