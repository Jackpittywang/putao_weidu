package com.putao.wd.home;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.putao.wd.MainActivity;
import com.putao.wd.R;
import com.putao.wd.account.AccountHelper;
import com.putao.wd.api.ExploreApi;
import com.putao.wd.base.PTWDFragment;
import com.putao.wd.companion.DisPlayActivity;
import com.putao.wd.companion.PlotActivity;
import com.putao.wd.companion.manage.ManageActivity;
import com.putao.wd.home.adapter.ExploreAdapter;
import com.putao.wd.model.Explore;
import com.putao.wd.model.ExploreProduct;
import com.putao.wd.model.ExploreProductPlot;
import com.putao.wd.model.Management;
import com.putao.wd.qrcode.CaptureActivity;
import com.putao.wd.share.OnShareClickListener;
import com.putao.wd.share.SharePopupWindow;
import com.putao.wd.user.LoginActivity;
import com.sunnybear.library.eventbus.Subcriber;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.view.JumpLoadingLayout;
import com.sunnybear.library.view.recycler.LoadMoreRecyclerView;

import butterknife.Bind;
import butterknife.OnClick;

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
