package com.putao.wd.start.question.adapter;

import android.content.Context;
import android.view.View;

import com.putao.wd.R;
import com.putao.wd.model.Question;
import com.sunnybear.library.view.recycler.BasicAdapter;
import com.sunnybear.library.view.recycler.BasicViewHolder;

import java.util.List;

/**
 * 点赞列表适配器
 * Created by wango on 2015/12/4.
 */
public class QuestionAdapter extends BasicAdapter<Question, BasicViewHolder> {
//    private static final int TYPE_ASK = 1;
//    private static final int TYPE_ANSWER = 2;

    public QuestionAdapter(Context context, List<Question> questions) {
        super(context, questions);
    }

//    @Override
//    public int getItemViewType(int position) {
//        Question question = getItem(position);
//        //判断questtion状态
//        switch (0) {
//            case 0:
//                return TYPE_ASK;
//            case 1:
//                return TYPE_ANSWER;
//        }
//        return -1;
//    }

    @Override
    public int getLayoutId(int viewType) {
//        switch (viewType) {
//            case TYPE_ASK:
//                return R.layout.activity_question_item_ask;
//            case TYPE_ANSWER:
//                return R.layout.activity_question_item_answer;
//        }
        return R.layout.activity_question_item_ask;
    }

    @Override
    public BasicViewHolder getViewHolder(View itemView, int viewType) {
//        switch (viewType) {
//            case TYPE_ASK:
                return new QuestionAskViewHolder(itemView);
//            case TYPE_ANSWER:
//                return new QuestionAnswerViewHolder(itemView);
//        }
//        return null;
    }

    @Override
    public void onBindItem(BasicViewHolder holder, Question question, int position) {
        if (holder instanceof QuestionAskViewHolder) {//提问
            QuestionAskViewHolder viewHolder = (QuestionAskViewHolder) holder;

        } else if (holder instanceof QuestionAnswerViewHolder) {//回答
            QuestionAnswerViewHolder viewHolder = (QuestionAnswerViewHolder) holder;

        }
    }

    /**
     *
     */
    static class QuestionAskViewHolder extends BasicViewHolder {

        public QuestionAskViewHolder(View itemView) {
            super(itemView);
        }
    }

    /**
     *
     */
    static class QuestionAnswerViewHolder extends BasicViewHolder {

        public QuestionAnswerViewHolder(View itemView) {
            super(itemView);
        }
    }
}
