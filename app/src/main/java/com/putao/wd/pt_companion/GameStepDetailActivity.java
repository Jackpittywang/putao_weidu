package com.putao.wd.pt_companion;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

import com.putao.wd.R;
import com.putao.wd.base.PTWDActivity;
import com.sunnybear.library.controller.BasicFragmentActivity;
import com.sunnybear.library.view.BasicWebView;

import butterknife.Bind;

/**
 * 游戏详情页
 * Created by zhanghao on 2016/04/05.
 */
public class GameStepDetailActivity extends BasicFragmentActivity {
    @Bind(R.id.wv_content)
    BasicWebView wv_content;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_game_step_detail;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {
        SpannableString ss = new SpannableString("淘淘向右走" + "关卡地图");
        ss.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.text_color_gray)), ss.length() - 4, ss.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

}


