package com.putao.wd.model;

import java.io.Serializable;

/**
 * 发票
 * Created by wango on 2015/12/20.
 */
public class Invoice implements Serializable {
    private String Id;//发票id
    private String Uid;//用户uid
    private String Type;//发票类型
    private String Title;//发票抬头,如：葡萄科技
    private String Content;//发票内容，如:1.明细

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "Id='" + Id + '\'' +
                ", Uid='" + Uid + '\'' +
                ", Type='" + Type + '\'' +
                ", Title='" + Title + '\'' +
                ", Content='" + Content + '\'' +
                '}';
    }
}
