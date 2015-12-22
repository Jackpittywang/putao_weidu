package com.putao.wd.dto;

import com.putao.wd.model.Page;

import java.util.List;

/**
 * 探索号bean文件
 * Created by yanghx on 2015/12/8.
 */
@Deprecated
public class ExploreItem extends Page {

   private List<CommentItem> data;

    public List<CommentItem> getData() {
        return data;
    }

    public void setData(List<CommentItem> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ExploreItem{" +
                "data=" + data +
                '}';
    }
}
