package com.putao.wd.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 首页7条
 * Created by zhanghao on 2015/1/21
 */
public class ExploreIndexs extends CacheTime implements Serializable {
    private ArrayList<ExploreIndex> exploreIndexes;

    public ArrayList<ExploreIndex> getExploreIndexes() {
        return exploreIndexes;
    }

    public void setExploreIndexes(ArrayList<ExploreIndex> exploreIndexes) {
        this.exploreIndexes = exploreIndexes;
    }

    @Override
    public String toString() {
        return "ExploreIndexs{" +
                "exploreIndexes=" + exploreIndexes +
                '}';
    }
}

