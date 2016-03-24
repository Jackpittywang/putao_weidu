package com.putao.wd.model;

import java.util.ArrayList;

/**
 * 更多内容
 * Created by yanghx on 2016/1/12.
 */
public class HomeExploreMores extends Page {

    private ArrayList<ExploreIndex> list;

    public ArrayList<ExploreIndex> getList() {
        return list;
    }

    public void setList(ArrayList<ExploreIndex> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "ExploreIndex{" +
                "list=" + list +
                '}';
    }
}
