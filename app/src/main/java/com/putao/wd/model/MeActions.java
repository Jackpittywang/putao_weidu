package com.putao.wd.model;

import java.util.List;

/**
 * 我的参与活动
 * Created by guchenkai on 2015/12/24.
 */
public class MeActions extends Page {
    private List<String> eventList;

    public List<String> getEventList() {
        return eventList;
    }

    public void setEventList(List<String> eventList) {
        this.eventList = eventList;
    }

    @Override
    public String toString() {
        return "MeActions{" +
                "eventList=" + eventList +
                '}';
    }
}
