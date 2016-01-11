package com.putao.wd.home;

import android.os.Bundle;
import android.view.View;

import com.putao.wd.R;
import com.putao.wd.base.PTWDFragment;
import com.sunnybear.library.controller.eventbus.Subcriber;

/**
 * 陪伴
 * Created by guchenkai on 2015/11/25.
 */
public class PutaoCompanyFragment extends PTWDFragment implements View.OnClickListener {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_company;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {

    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }


    @Override
    public void onClick(View v) {

    }
}
