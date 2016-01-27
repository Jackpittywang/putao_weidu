package com.putao.wd.model;

import java.util.List;

/**
 * 更多内容
 * Created by yanghx on 2016/1/12.
 */
public class HomeExploreMores extends Page {

    private List<HomeExploreMore> list;

    public List<HomeExploreMore> getList() {
        return list;
    }

    public void setList(List<HomeExploreMore> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "HomeExploreMores{" +
                "list=" + list +
                '}';
    }
}
