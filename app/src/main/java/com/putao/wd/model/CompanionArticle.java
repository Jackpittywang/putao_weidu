package com.putao.wd.model;

import java.io.Serializable;
import java.util.Random;

/**
 * Created by zhanghao on 2015/12/8.
 */
public class CompanionArticle implements Serializable {
    private String article_id;//文章id
    private String article_icon;//文章配图
    private String article_title;//文章标题
    private String article_contents;//文章内容H5
    private int type;//文章类型
    private int like_count;//点赞数
    private int comments_count;//评论数
    private int is_like;//是否点赞0未点赞，1已点赞
    private int sub_status;//是否收藏0未收藏，1已收藏
    private int comment_list;//评论列表
}
