package com.putao.wd.dto;

import com.putao.wd.model.ExploreProduct;
import com.putao.wd.model.Page;

import java.util.List;

/**
 * 探索号bean文件
 * Created by yanghx on 2015/12/8.
 */
@Deprecated
public class ExploreItem extends Page {

   private List<ExploreProduct> data;

    public List<ExploreProduct> getData() {
        return data;
    }

    public void setData(List<ExploreProduct> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ExploreItem{" +
                "data=" + data +
                '}';
    }
}
