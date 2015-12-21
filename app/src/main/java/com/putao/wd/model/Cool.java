package com.putao.wd.model;

import java.util.ArrayList;

/**
 * 赞列表
 * Created by yanghx on 2015/12/18.
 */
public class Cool extends Page {
    private CoolEventList eventCoolList;

    public CoolEventList getEventCoolList() {
        return eventCoolList;
    }

    public void setEventCoolList(CoolEventList eventCoolList) {
        this.eventCoolList = eventCoolList;
    }

    @Override
    public String toString() {
        return "Cool{" +
                "eventCoolList=" + eventCoolList +
                '}';
    }
}
