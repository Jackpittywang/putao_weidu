package com.putao.wd.home;

import android.os.Bundle;

import com.putao.wd.R;
import com.putao.wd.base.PTWDFragment;

/**
 * 陪伴
 * Created by guchenkai on 2015/11/25.
 */
public class PutaoCompanyFragment extends PTWDFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_company;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {

    }

    @Override
    public void onStart() {
        super.onStart();
        initTips();
    }

    private void initTips() {

    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }


}
