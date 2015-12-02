package com.putao.wd.user;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.putao.wd.R;
import com.putao.wd.base.PTWDFragment;
import com.putao.wd.base.SelectPopupWindow;
import com.sunnybear.library.view.CleanableEditText;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 完善用户信息
 * Created by guchenkai on 2015/11/29.
 */
public class CompleteFragment extends PTWDFragment implements View.OnClickListener {
    @Bind(R.id.ll_main)
    LinearLayout ll_main;
    @Bind(R.id.et_nickname)
    CleanableEditText et_nickname;
    @Bind(R.id.et_intro)
    CleanableEditText et_intro;

    private SelectPopupWindow mSelectPopupWindow;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_complete;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {
        addNavgation();
        mSelectPopupWindow=new SelectPopupWindow(mActivity) {
            @Override
            public void onFirstClick(View v) {

            }

            @Override
            public void onSecondClick(View v) {

            }
        };
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @OnClick(R.id.iv_select_icon)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_select_icon://选择用户头像
                mSelectPopupWindow.show(ll_main);
                break;
        }
    }
}
