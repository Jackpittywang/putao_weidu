package com.putao.wd.me.information;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import com.putao.wd.GlobalApplication;
import com.putao.wd.R;
import com.putao.wd.base.PTWDActivity;
import com.sunnybear.library.view.CleanableEditText;

import butterknife.Bind;

/**
 * 简历
 * Created by wangou on 2015/12/4.
 */
public class ResumeActivity extends PTWDActivity<GlobalApplication> implements View.OnClickListener, TextWatcher {
    @Bind(R.id.et_resume)
    CleanableEditText et_resume;//简历
    @Bind(R.id.tv_rest_part)
    TextView tv_rest_part;//可输入简历的剩余字数

    @Override
    protected int getLayoutId() {
        return R.layout.activity_resume;
    }

    @Override
    protected void onViewCreateFinish(Bundle saveInstanceState) {
        addNavgation();
        et_resume.addTextChangedListener(this);
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
            tv_rest_part.setText((40-s.length())+"");
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
