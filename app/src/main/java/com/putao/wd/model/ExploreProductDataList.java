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


}
