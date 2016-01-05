package com.putao.wd.start.question;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RelativeLayout;

import com.putao.wd.GlobalApplication;
import com.putao.wd.R;
import com.putao.wd.account.AccountHelper;
import com.putao.wd.api.UserApi;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.model.Question;
import com.putao.wd.model.UserInfo;
import com.putao.wd.start.comment.EmojiFragment;
import com.putao.wd.start.comment.adapter.EmojiFragmentAdapter;
import com.putao.wd.start.question.adapter.QuestionAdapter;
import com.sunnybear.library.eventbus.Subcriber;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.view.emoji.Emoji;
import com.sunnybear.library.view.emoji.EmojiEditText;
import com.sunnybear.library.view.recycler.BasicRecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 我的提问
 * create by wangou
 */
public class QuestionActivity extends PTWDActivity implements View.OnClickListener {
    @Bind(R.id.vp_emojis)
    ViewPager vp_emojis;
    @Bind(R.id.et_msg)
    EmojiEditText et_msg;

    @Bind(R.id.rl_no_question)
    RelativeLayout rl_no_question;
    @Bind(R.id.rv_messages)
    BasicRecyclerView rv_messages;

    private QuestionAdapter adapter;

    private Map<String, String> emojiMap;
    private List<Emoji> emojis;

    private boolean isShowEmoji = false;

    private UserInfo userInfo;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_question;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
        adapter = new QuestionAdapter(this, null);
        userInfo = AccountHelper.getCurrentUserInfo();
        emojiMap = GlobalApplication.getEmojis();
        emojis = new ArrayList<>();
        for (Map.Entry<String, String> entry : emojiMap.entrySet()) {
            emojis.add(new Emoji(entry.getKey(), entry.getValue()));
        }
        vp_emojis.setAdapter(new EmojiFragmentAdapter(getSupportFragmentManager(), emojis, 20));
        rv_messages.setAdapter(adapter);
        getQuestionList();
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @OnClick({R.id.tv_emojis, R.id.tv_send})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_emojis://点击表情栏
                isShowEmoji = isShowEmoji ? false : true;
                vp_emojis.setVisibility(isShowEmoji ? View.VISIBLE : View.GONE);
                break;
            case R.id.tv_send://点击发送
                String msg = et_msg.getText().toString();
                networkRequest(UserApi.questionAdd(et_msg.getText().toString(), userInfo.getNick_name(), userInfo.getHead_img()),
                        new SimpleFastJsonCallback<String>(String.class, loading) {
                            @Override
                            public void onSuccess(String url, String result) {
                                Logger.i("我的提问提交成功");
                                et_msg.setText("");
                                adapter.clear();
                                getQuestionList();
                            }
                        });
                et_msg.setText("");
                vp_emojis.setVisibility(View.GONE);
                break;
        }
    }

    /**
     * 获得提问列表
     */
    private void getQuestionList() {
        networkRequest(UserApi.getQuestionList(userInfo.getNick_name(), userInfo.getHead_img()),
                new SimpleFastJsonCallback<ArrayList<Question>>(Question.class, loading) {
                    @Override
                    public void onSuccess(String url, ArrayList<Question> result) {
                        Logger.d(result.toString());
                        if (result.size() > 0) {
                            Logger.d(result.size() + "");
                            rl_no_question.setVisibility(View.GONE);
                            rv_messages.setVisibility(View.VISIBLE);
                            adapter.addAll(result);
                            rv_messages.scrollToPosition(result.size() - 1);
                            loading.dismiss();
                        }
                    }
                });
    }

    @Subcriber(tag = EmojiFragment.EVENT_CLICK_EMOJI)
    public void eventClickEmoji(Emoji emoji) {
        et_msg.append(emoji.getName());
    }

    @Subcriber(tag = EmojiFragment.EVENT_DELETE_EMOJI)
    public void eventDeleteEmoji(Emoji emoji) {
        et_msg.delete();
    }
}
