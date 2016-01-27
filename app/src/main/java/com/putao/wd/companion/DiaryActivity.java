package com.putao.wd.companion;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.putao.wd.R;
import com.putao.wd.api.ExploreApi;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.home.adapter.ExploreAdapter;
import com.putao.wd.model.Diary;
import com.putao.wd.model.DiaryApp;
import com.putao.wd.model.Diarys;
import com.putao.wd.model.ExploreProduct;
import com.putao.wd.model.ExploreProductPlot;
import com.putao.wd.share.OnShareClickListener;
import com.putao.wd.share.SharePopupWindow;
import com.sunnybear.library.controller.eventbus.Subcriber;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.view.recycler.LoadMoreRecyclerView;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * 陪伴日志
 * Created by zhanghao on 2016/1/14.
 */
public class DiaryActivity extends PTWDActivity {
    @Bind(R.id.rv_content)
    LoadMoreRecyclerView rv_content;
    @Bind(R.id.ll_date_empty)
    LinearLayout ll_date_empty;

    public static String DIARY_APP = "diary_app";

    private ExploreAdapter adapter;
    private DiaryApp mDiaryApp;

    private SharePopupWindow mSharePopupWindow;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_companion_diary;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {
        addNavigation();
        mDiaryApp = (DiaryApp) args.getSerializable(DIARY_APP);
        navigation_bar.setMainTitle(mDiaryApp.getProduct_name());
        adapter = new ExploreAdapter(mContext, null);
        rv_content.setAdapter(adapter);
        mSharePopupWindow = new SharePopupWindow(mContext);
        mSharePopupWindow.setOnShareClickListener(new OnShareClickListener() {
            @Override
            public void onWechat() {

            }

            @Override
            public void onWechatFriend() {

            }
        });
        getDiaryIndex();
    }


    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    /**
     * 获取陪伴成长日志
     */
    private void getDiaryIndex() {
        networkRequest(ExploreApi.getDiaryData(String.valueOf(mDiaryApp.getProduct_id())),
                new SimpleFastJsonCallback<Diarys>(Diarys.class, loading) {
                    @Override
                    public void onSuccess(String url, Diarys result) {
                        if (result != null && result.getData().size()> 0) {
                            adapter.addAll(result.getData());
                            ll_date_empty.setVisibility(View.GONE);
                            rv_content.setVisibility(View.VISIBLE);
                        } else {
                            ll_date_empty.setVisibility(View.VISIBLE);
                            rv_content.setVisibility(View.GONE);
                        }
                        loading.dismiss();
                    }
                });
    }

    @Subcriber(tag = ExploreAdapter.EVENT_DISPLAY)
    public void eventDisplay(ExploreProduct exploreProduct) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(DisPlayActivity.BUNDLE_DISPLAY_DETAILS, exploreProduct);
        startActivity(DisPlayActivity.class, bundle);
    }

    @Subcriber(tag = ExploreAdapter.EVENT_DISPLAY)
    public void eventDisplay(ExploreProductPlot plot) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(PlotActivity.BUNDLE_DISPLAY_PLOT, plot);
        startActivity(PlotActivity.class, bundle);
    }

}
