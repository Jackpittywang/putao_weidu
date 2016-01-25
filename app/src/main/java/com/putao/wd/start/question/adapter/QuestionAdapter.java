package com.putao.wd.start.question.adapter;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.account.AccountHelper;
import com.putao.wd.model.Question;
import com.sunnybear.library.util.DateUtils;
import com.sunnybear.library.view.emoji.EmojiTextView;
import com.sunnybear.library.view.image.ImageDraweeView;
import com.sunnybear.library.view.recycler.adapter.BasicAdapter;
import com.sunnybear.library.view.recycler.BasicViewHolder;

import java.util.List;

import butterknife.Bind;

/**
 * 点赞列表适配器
 * Created by wango on 2015/12/4.
 */
public class QuestionAdapter extends BasicAdapter<Question, BasicViewHolder> {
    private static final int TYPE_ASK = 1;
    private static final int TYPE_ANSWER = 2;

    public QuestionAdapter(Context context, List<Question> questions) {
        super(context, questions);
    }

    @Override
    public int getItemViewType(int position) {
        Question question = getItem(position);
        //判断question状态
        switch (question.getType()) {
            case 0:
                return TYPE_ASK;
            case 1:
                return TYPE_ANSWER;
        }
        return -1;
    }

    @Override
    public int getLayoutId(int viewType) {
        switch (viewType) {
            case TYPE_ASK:
                return R.layout.activity_question_item_ask;
            case TYPE_ANSWER:
                return R.layout.activity_question_item_answer;
        }
        return -1;
    }

    @Override
    public BasicViewHolder getViewHolder(View itemView, int viewType) {
        switch (viewType) {
            case TYPE_ASK:
                return new QuestionAskViewHolder(itemView);
            case TYPE_ANSWER:
                return new QuestionAnswerViewHolder(itemView);
        }
        return null;
    }

    @Override
    public void onBindItem(BasicViewHolder holder, Question question, int position) {
        if (holder instanceof QuestionAskViewHolder) {//提问
            QuestionAskViewHolder askViewHolder = (QuestionAskViewHolder) holder;
            askViewHolder.question_item_ask_time.setText(DateUtils.secondToDate(Integer.parseInt(question.getCreate_time()), "───yyyy.MM.dd───"));
            askViewHolder.question_item_ask_context.setText(question.getMessage());
            askViewHolder.question_item_ask_icon.setImageURL(AccountHelper.getCurrentUserInfo().getHead_img());
        } else if (holder instanceof QuestionAnswerViewHolder) {//回答
            QuestionAnswerViewHolder answerViewHolder = (QuestionAnswerViewHolder) holder;
            answerViewHolder.question_item_ask_time.setText(DateUtils.secondToDate(Integer.parseInt(question.getCreate_time()), "───yyyy.MM.dd───"));
            answerViewHolder.question_item_answer_context.setText(question.getMessage());
        }
    }


    /**
     *
     */
    static class QuestionAskViewHolder extends BasicViewHolder {
        @Bind(R.id.question_item_ask_time)
        TextView question_item_ask_time;

        @Bind(R.id.question_item_ask_context)
        EmojiTextView question_item_ask_context;

        @Bind(R.id.question_item_ask_icon)
        ImageDraweeView question_item_ask_icon;

        public QuestionAskViewHolder(View itemView) {
            super(itemView);
        }
    }
    static class QuestionAnswerViewHolder extends BasicViewHolder {
        @Bind(R.id.question_item_ask_time)
        TextView question_item_ask_time;

        @Bind(R.id.question_item_answer_context)
        EmojiTextView question_item_answer_context;

        @Bind(R.id.question_item_answer_icon)
        ImageDraweeView question_item_answer_icon;


        public QuestionAnswerViewHolder(View itemView) {
            super(itemView);
        }
    }
}
