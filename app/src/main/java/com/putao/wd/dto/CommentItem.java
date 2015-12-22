package com.putao.wd.dto;

import com.putao.wd.model.Explore;

import java.io.Serializable;
import java.util.List;

/**
 * 评论列表
 * Created by yanghx on 2015/12/8.
 */
@Deprecated
public class CommentItem implements Serializable {

    private List<Explore> data;

    public List<Explore> getData() {
        return data;
    }

    public void setData(List<Explore> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "CommentItem{" +
                "data=" + data +
                '}';
    }
}


