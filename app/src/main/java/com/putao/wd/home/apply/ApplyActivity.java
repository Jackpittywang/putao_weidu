package com.putao.wd.home.apply;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.imageformat.ImageFileExtensionMap;
import com.putao.wd.GlobalApplication;
import com.putao.wd.R;
import com.putao.wd.base.PTWDActivity;
import com.sunnybear.library.view.CleanableEditText;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 我要参加
 * Created by wango on 2015/12/4.
 *
 */
public class ApplyActivity extends PTWDActivity<GlobalApplication> implements View.OnClickListener {
    @Bind(R.id.tv_parent_relation)
    TextView tv_parent_relation;//家长身份
    @Bind(R.id.et_phone)
    CleanableEditText et_phone;//手机号码
    @Bind(R.id.et_nickname)
    EditText et_nickname;//孩子昵称
    @Bind(R.id.tv_childage)
    EditText tv_childage;//孩子年龄
    @Bind(R.id.et_activitytimes)
    EditText et_activitytimes;//活动场次
    @Bind(R.id.et_QQorWX)
    EditText et_QQorWX;//QQ/微信
    @Bind(R.id.et_parentname)
    EditText et_parentname;//家长姓名

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
