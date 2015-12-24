package com.putao.wd.model;

import java.io.Serializable;

/**
 * 提问
 * Created by Administrator on 2015/12/24.
 */
public class UserQuestion implements Serializable {
    private Question question;
    private Reply reply;


    class Question{
        private String qa_id;//question ID
        private String message;//提问内容
        private String create_time;//提问时间
    }
    class Reply{
//        private String qa_id;//question ID
//        private String message;//提问内容
//        private String create_time;//提问时间
    }
}
