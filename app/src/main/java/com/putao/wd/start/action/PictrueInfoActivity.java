package com.putao.wd.start.action;

import android.graphics.Color;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.putao.wd.GlobalApplication;
import com.putao.wd.R;
import com.putao.wd.base.PTWDActivity;

import butterknife.Bind;

/**
 * 图片信息
 * Created by wango on 2015/12/4.
 *
 */
public class PictrueInfoActivity extends PTWDActivity<GlobalApplication> implements View.OnClickListener {

    @Bind(R.id.tv_picture_info)
    TextView tv_picture_info;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_pictrue_info;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
        tv_picture_info.setMovementMethod(ScrollingMovementMethod.getInstance());
        setMainTitleColor(Color.WHITE);
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @Override
    public void onClick(View v) {

    }
}
