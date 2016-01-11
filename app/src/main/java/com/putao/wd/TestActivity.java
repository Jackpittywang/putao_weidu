package com.putao.wd;

import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.TextView;

import com.putao.wd.util.HtmlUtils;
import com.putao.wd.video.VideoPlayerActivity;
import com.sunnybear.library.controller.BasicFragmentActivity;
import com.sunnybear.library.view.emoji.EmojiTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by guchenkai on 2015/12/8.
 */
@Deprecated
public class TestActivity extends BasicFragmentActivity implements View.OnClickListener {
    String html = "<font color=646464 size=14>昨日30分钟拼完</font>" +
            "<br>" +
            "<font color=313131 size=30>100</font><font color=313131 size=14>个</font><font color=646464 size=14>\\n七巧板</font>" +
            "<br>" +
            "<font color=959595 size=12>平均速度</font><font color=646464 size=12 bold>超越了90%</font><font color=959595 size=12>的孩子</font>";
    @Bind(R.id.tv_1)
    TextView tv_1;
    @Bind(R.id.tv_2)
    TextView tv_2;
    @Bind(R.id.tv_3)
    TextView tv_3;
    @Bind(R.id.tv_emoji)
    EmojiTextView tv_emoji;

//    private List<HtmlInfo> htmlInfos = new ArrayList<>();

    private List<SpannableStringBuilder> builders = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.test_activity_test;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        builders = HtmlUtils.getTexts(html);
        tv_1.setText(builders.get(0));
        tv_2.setText(builders.get(1));
        tv_3.setText(builders.get(2));

        tv_emoji.setText("[呲牙][呲牙][呲牙]呲牙[大笑][萌]");
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @OnClick(R.id.ll_show)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_show:
//                loading.show();
//                SuperTask.with(this)
//                        .assign(new SuperTask.TaskDescription<String>() {
//                            @Override
//                            public String onBackground() {
//                                return "你个傻叉";
//                            }
//                        })
//                        .finish(new SuperTask.FinishListener<String>() {
//                            @Override
//                            public void onFinish(@Nullable String result) {
//                                Logger.d(result);
//                            }
//                        }).execute();
                Bundle bundle = new Bundle();
//                bundle.putString(VideoPlayerActivity.BUNDLE_VIDEO_URL, "http://mp4.28mtv.com/mp41/景甜-心上人[68mtv.com].mp4");
                bundle.putString(VideoPlayerActivity.BUNDLE_VIDEO_URL,
                        "http://mp4.28mtv.com/mp41/景甜-心上人[68mtv.com].mp4");
                startActivity(VideoPlayerActivity.class, bundle);
                break;
        }
    }
}
