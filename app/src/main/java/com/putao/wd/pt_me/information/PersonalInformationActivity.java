package com.putao.wd.pt_me.information;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
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
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
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

    @OnClick({R.id.iv_personinfo_headicon,R.id.tv_personinfo_nickname,R.id.tv_personinfo_resume})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_personinfo_headicon://头像
                mSelectPopupWindow.show(ll_main);
                break;
            case R.id.tv_personinfo_nickname://昵称
                startActivity(NicknameActivity.class);
                break;
            case R.id.tv_personinfo_resume://简历
                startActivity(ResumeActivity.class);
                break;


        }
    }

}
