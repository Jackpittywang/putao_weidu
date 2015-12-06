package com.putao.wd.home.apply;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.facebook.imageformat.ImageFileExtensionMap;
import com.putao.wd.GlobalApplication;
import com.putao.wd.R;
import com.putao.wd.base.PTWDActivity;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 我要参加
 * Created by wango on 2015/12/4.
 *
 */
public class ApplyActivity extends PTWDActivity<GlobalApplication> implements View.OnClickListener {
    @Bind(R.id.iv_parentrelation_selecticon)
    ImageView iv_parentrelation_selecticon;//家长身份
    @Bind(R.id.iv_child_age)
    ImageView iv_child_age;//孩子年龄

    @Override
    protected int getLayoutId() {
        return R.layout.activity_apply;
    }

    @Override
    protected void onViewCreateFinish(Bundle saveInstanceState) {
            addNavgation();
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @OnClick({R.id.iv_parentrelation_selecticon,R.id.iv_child_age})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_parentrelation_selecticon://家长身份

                break;
            case R.id.iv_child_age://孩子年龄

                break;
        }
    }
}
