package com.putao.wd.start.question;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.putao.wd.GlobalApplication;
import com.putao.wd.R;
import com.putao.wd.account.AccountHelper;
import com.putao.wd.api.UserApi;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.start.comment.adapter.EmojiFragmentAdapter;
import com.putao.wd.start.question.adapter.QuestionAdapter;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.view.emoji.Emoji;
import com.sunnybear.library.view.emoji.EmojiEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 设置
 * create by wangou
 */
public class QuestionActivity extends PTWDActivity implements View.OnClickListener {
    @Bind(R.id.vp_emojis)
    ViewPager vp_emojis;
    @Bind(R.id.et_msg)
    EmojiEditText et_msg;
    private QuestionAdapter adapter;

    private Map<String, String> emojiMap;
    private List<Emoji> emojis;

    private boolean isShowEmoji = false;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_question;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
        emojiMap = GlobalApplication.getEmojis();
        emojis = new ArrayList<>();
        for (Map.Entry<String, String> entry : emojiMap.entrySet()) {
            emojis.add(new Emoji(entry.getKey(), entry.getValue()));
        }
        vp_emojis.setAdapter(new EmojiFragmentAdapter(getSupportFragmentManager(), emojis, 20));
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
                Logger.d("_____" + AccountHelper.getCurrentUserInfo().getNick_name());
                Logger.d("_____" + AccountHelper.getCurrentUserInfo().getHead_img());
                networkRequest(UserApi.questionAdd(et_msg.getText().toString(), AccountHelper.getCurrentUserInfo().getNick_name(), AccountHelper.getCurrentUserInfo().getHead_img()),
                        new SimpleFastJsonCallback<String>(String.class, loading) {
                            @Override
                            public void onSuccess(String url, String result) {
                                Logger.i("我的提问提交成功");
                                et_msg.setText("");
                                refreshQuestionList();
                            }
                        });
                break;
        }
    }

    /**
     * 刷新评论列表
     */
    private void refreshQuestionList() {
    }
}
