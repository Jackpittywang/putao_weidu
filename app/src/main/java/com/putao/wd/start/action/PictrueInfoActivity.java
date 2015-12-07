package com.putao.wd.start.action;

import android.os.Bundle;
import android.view.View;
import com.putao.wd.GlobalApplication;
import com.putao.wd.R;
import com.putao.wd.base.PTWDActivity;

/**
 * 图片信息
 * Created by wango on 2015/12/4.
 *
 */
public class PictrueInfoActivity extends PTWDActivity<GlobalApplication> implements View.OnClickListener {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_pictrue_info;
    }

    @Override
    protected void onViewCreateFinish(Bundle saveInstanceState) {

    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @Override
    public void onClick(View v) {

    }
}
