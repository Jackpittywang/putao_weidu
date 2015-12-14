package com.putao.wd.model;

import java.io.Serializable;
import java.util.List;

/**
 * 数据的图片地址--探索号产品--成长日记首页
 * Created by guchenkai on 2015/12/8.
 */
public class ExploreProductDataList implements Serializable {
    private List<ExploreProductDataDaily> daily_list;//日常数据
    private List<ExploreProductDataPlot> plot_list;//剧情+教育
    private ExploreProductDataAll all;//全局数据

    public List<ExploreProductDataDaily> getDaily_list() {
        return daily_list;
    }

    public void setDaily_list(List<ExploreProductDataDaily> daily_list) {
        this.daily_list = daily_list;
    }

    public List<ExploreProductDataPlot> getPlot_list() {
        return plot_list;
    }

    public void setPlot_list(List<ExploreProductDataPlot> plot_list) {
        this.plot_list = plot_list;
    }

    public ExploreProductDataAll getAll() {
        return all;
    }

    public void setAll(ExploreProductDataAll all) {
        this.all = all;
    }

    @Override
    public String toString() {
        return "ExploreProductDataList{" +
                "daily_list=" + daily_list +
                ", plot_list=" + plot_list +
                ", all=" + all +
                '}';
    }
}
