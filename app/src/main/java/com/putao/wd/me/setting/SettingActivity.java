package com.putao.wd.me.setting;

import android.os.Bundle;
import android.view.View;
import com.putao.wd.GlobalApplication;
import com.putao.wd.R;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.me.address.AboutUsActivity;

import butterknife.OnClick;

/*
**create by wangou
**设置
 */
public class SettingActivity extends PTWDActivity<GlobalApplication> implements View.OnClickListener {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void onViewCreateFinish(Bundle saveInstanceState) {
        addNavgation();
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    //-------|关于我们----------|修改密码
    @OnClick({R.id.si_about_us,R.id.si_modify_password})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.si_about_us:startActivity(AboutUsActivity.class);break;//关于我们
            //case R.id.si_modify_password:startActivity(AboutUsActivity.class);break;//修改密码
        }
    }

}
