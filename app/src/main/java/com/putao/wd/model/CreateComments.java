package com.putao.wd.model;

import java.io.Serializable;
import java.util.List;

/**
 * 成长日记首页
 * Created by zhanghao on 2015/12/8.
 */
public class CreateComments extends CreatePage {
    List<CreateComment> data;

    public List<CreateComment> getData() {
        return data;
    }

    public void setData(List<CreateComment> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "CreateComments{" +
                "data=" + data +
                '}';
    }
}
