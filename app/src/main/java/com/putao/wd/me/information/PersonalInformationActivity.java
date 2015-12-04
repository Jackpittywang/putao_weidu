package com.putao.wd.me.information;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.putao.wd.GlobalApplication;
import com.putao.wd.R;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.base.SelectPopupWindow;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 个人信息
 * Created by wangou on 2015/12/4.
 */
public class PersonalInformationActivity  extends PTWDActivity<GlobalApplication> implements View.OnClickListener {
    @Bind(R.id.ll_main)
    LinearLayout ll_main;//当前窗体

    private SelectPopupWindow mSelectPopupWindow;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_personal_information;
    }

    @Override
    protected void onViewCreateFinish(Bundle saveInstanceState) {
        addNavgation();
        mSelectPopupWindow=new SelectPopupWindow(this) {
            @Override
            public void onFirstClick(View v) {

            }

            @Override
            public void onSecondClick(View v) {

            }
        };
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @OnClick({R.id.si_personinfo_headicon,R.id.si_personinfo_nickname,R.id.si_personinfo_resume})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.si_personinfo_headicon:
                mSelectPopupWindow.show(ll_main);
                break;
            case R.id.si_personinfo_nickname:
                startActivity(NicknameActivity.class);
                break;
            case R.id.si_personinfo_resume:
                startActivity(ResumeActivity.class);
                break;


        }
    }

}
