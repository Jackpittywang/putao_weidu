package com.putao.wd.model;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by yanghx on 2015/12/18.
 */
public class ServiceBack implements Serializable {
    private ArrayList<ServiceBackImage> images;

    public ArrayList<ServiceBackImage> getImages() {
        return images;
    }

    public void setImages(ArrayList<ServiceBackImage> images) {
        this.images = images;
    }
}
