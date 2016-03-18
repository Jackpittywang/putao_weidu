package com.putao.wd.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/3/17.
 */
public class PicClickResult implements Serializable {
    private int clickIndex;
    private ArrayList<PicList> picList;

    public int getClickIndex() {
        return clickIndex;
    }

    public void setClickIndex(int clickIndex) {
        this.clickIndex = clickIndex;
    }

    public ArrayList<PicList> getPicLists() {
        return picList;
    }

    public void setPicLists(ArrayList<PicList> picLists) {
        this.picList = picLists;
    }

    @Override
    public String toString() {
        return "PicClickResult{" +
                "clickIndex=" + clickIndex +
                ", picLists=" + picList +
                '}';
    }
}