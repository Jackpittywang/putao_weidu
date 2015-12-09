package com.putao.wd;

import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.widget.TextView;

import com.putao.wd.util.HtmlUtils;
import com.sunnybear.library.controller.BasicFragmentActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;


/**
 * Created by guchenkai on 2015/12/8.
 */
public class TestActivity extends BasicFragmentActivity {
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

    private List<HtmlInfo> htmlInfos = new ArrayList<>();

    private List<SpannableStringBuilder> builders = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_test;
    }

    @Override
    protected void onViewCreateFinish(Bundle saveInstanceState) {
        builders = HtmlUtils.getTexts(html);
        tv_1.setText(builders.get(0));
        tv_2.setText(builders.get(1));
        tv_3.setText(builders.get(2));
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }
}
