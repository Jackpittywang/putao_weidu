package com.putao.wd.me.personalinfo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.putao.wd.GlobalApplication;
import com.putao.wd.R;
import com.putao.wd.base.PTWDActivity;
import com.sunnybear.library.view.tab.TitleBar;

import butterknife.OnClick;

/**
 * 个人信息
 * Created by wangou on 2015/12/4.
 */
public class PersonalInformationActivity  extends PTWDActivity<GlobalApplication> implements View.OnClickListener {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_personal_information;
    }

    @Override
    protected void onViewCreateFinish(Bundle saveInstanceState) {

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

                break;
            case R.id.si_personinfo_nickname:
                startActivity(NicknameActivity.class);
                break;
            case R.id.si_personinfo_resume:

                break;


        }
    }

}
