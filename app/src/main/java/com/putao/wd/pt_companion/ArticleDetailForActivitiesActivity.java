package com.putao.wd.pt_companion;


import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.account.AccountConstants;
import com.putao.wd.album.activity.PhotoAlbumActivity;
import com.putao.wd.album.model.ImageInfo;
import com.putao.wd.api.CompanionApi;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.model.ArticleDetailActs;
import com.putao.wd.model.Companion;
import com.putao.wd.pt_companion.adapter.ArticleDetailForActivitiesAdapter;
import com.putao.wd.share.SharePopupWindow;
import com.sunnybear.library.controller.eventbus.Subcriber;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.view.BasicWebView;
import com.sunnybear.library.view.image.ImageDraweeView;
import com.sunnybear.library.view.recycler.LoadMoreRecyclerView;
import com.sunnybear.library.view.scroll.NestScrollView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by zhanghao on 2016/4/6.
 */
public class ArticleDetailForActivitiesActivity extends PTWDActivity implements View.OnClickListener {

    @Bind(R.id.wv_load)
    BasicWebView wv_load;
    @Bind(R.id.rv_content)
    LoadMoreRecyclerView rv_content;
    @Bind(R.id.tv_title)
    TextView tv_title;
    @Bind(R.id.iv_upload_pic)
    ImageDraweeView iv_upload_pic;
    @Bind(R.id.sv_load)
    NestScrollView sv_load;
    private ArticleDetailForActivitiesAdapter mArtivleDetailActsAdapter;
    private ArrayList<ArticleDetailActs> objects;
    ViewGroup.LayoutParams mRvLayoutParams;
    private SharePopupWindow mSharePopupWindow;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_article_for_activities;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
        iv_upload_pic.setVisibility(View.VISIBLE);
        mSharePopupWindow = new SharePopupWindow(mContext);
        wv_load.loadUrl("http://wap.baidu.com");
        mRvLayoutParams = rv_content.getLayoutParams();
        mArtivleDetailActsAdapter = new ArticleDetailForActivitiesAdapter(mContext, null);
        rv_content.setAdapter(mArtivleDetailActsAdapter);
        initData();
        addListener();
    }

    private void addListener() {
        wv_load.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                sv_load.scrollTo(0, 0);
            }
        });
    }

    private void initData() {
        networkRequest(CompanionApi.getCompany(),
                new SimpleFastJsonCallback<ArrayList<Companion>>(Companion.class, loading) {
                    @Override
                    public void onSuccess(String url, ArrayList<Companion> result) {
                        loading.dismiss();
                    }

                    @Override
                    public void onFailure(String url, int statusCode, String msg) {
                        super.onFailure(url, statusCode, msg);
                    }
                }, false);

        objects = new ArrayList<>();
        objects.add(new ArticleDetailActs());
        objects.add(new ArticleDetailActs());
        objects.add(new ArticleDetailActs());
        objects.add(new ArticleDetailActs());
        objects.add(new ArticleDetailActs());
        objects.add(new ArticleDetailActs());
        mArtivleDetailActsAdapter.replaceAll(objects);
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }


    /**
     * 重新设置recycleview高度
     *
     * @param str
     */
    @Subcriber(tag = ArticleDetailForActivitiesAdapter.EVENT_REFRESH_HEIGHT)
    private void setHeight(String str) {
        mRvLayoutParams.height = 0;
        for (ArticleDetailActs act : objects) {
            mRvLayoutParams.height += act.getHeight();
        }
        rv_content.setLayoutParams(mRvLayoutParams);
    }

    @OnClick(R.id.iv_upload_pic)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_upload_pic:
                startActivity(PhotoAlbumActivity.class);
                break;
        }
    }

    @Override
    public void onRightAction() {
        super.onRightAction();
        mSharePopupWindow.show(navigation_bar);
    }

    @Subcriber(tag = AccountConstants.EventBus.EVENT_ALBUM_SELECT)
    private void setPic(List<ImageInfo> selectPhotos) {
        String uri = !TextUtils.isEmpty(selectPhotos.get(0).THUMB_DATA) ? selectPhotos.get(0).THUMB_DATA : selectPhotos.get(0)._DATA;
        iv_upload_pic.setImageURL(Uri.parse("file://" + uri).toString());
    }
}
