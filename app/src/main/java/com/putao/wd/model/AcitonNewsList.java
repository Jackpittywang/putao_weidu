package com.putao.wd.model;

import java.util.List;

/**
 * 活动列表
 * Created by guchenkai on 2015/12/18.
 */
public class AcitonNewsList extends Page {
    private List<ActionNews> getEventList;

    public List<ActionNews> getGetEventList() {
        return getEventList;
    }

    public void setGetEventList(List<ActionNews> getEventList) {
        this.getEventList = getEventList;
    }

    @Override
    public String toString() {
        return "AcitonNewsList{" +
                "getEventList=" + getEventList +
                '}';
    }
}
