package com.putao.wd.pt_companion;


import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.putao.wd.R;
import com.putao.wd.account.AccountConstants;
import com.putao.wd.album.activity.PhotoAlbumActivity;
import com.putao.wd.album.model.ImageInfo;
import com.putao.wd.api.CompanionApi;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.model.ArticleDetailActs;
import com.putao.wd.model.Companion;
import com.putao.wd.model.Property;
import com.putao.wd.pt_companion.adapter.ArticleDetailForActivitiesAdapter;
import com.putao.wd.share.SharePopupWindow;
import com.putao.wd.webview.PutaoParse;
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
public class ArticleDetailForActivitiesActivity extends PTWDActivity implements OnClickListener {

    @Bind(R.id.wv_load)
    BasicWebView wv_load;
    @Bind(R.id.rv_content)
    LoadMoreRecyclerView rv_content;
    @Bind(R.id.ll_cool)
    LinearLayout ll_cool;//点赞数
    @Bind(R.id.tv_count_cool)
    TextView tv_count_cool;
    @Bind(R.id.ll_comment)
    LinearLayout ll_comment;//评论数
    @Bind(R.id.tv_count_comment)
    TextView tv_count_comment;
    /* @Bind(R.id.iv_upload_pic)
     ImageDraweeView iv_upload_pic;*/
    @Bind(R.id.sv_load)
    NestScrollView sv_load;
    private ArticleDetailForActivitiesAdapter mArtivleDetailActsAdapter;
    private ArrayList<ArticleDetailActs> objects;
    ViewGroup.LayoutParams mRvLayoutParams;
    private SharePopupWindow mSharePopupWindow;
    private static final String COOL_COUNT = "like_count";
    private boolean is_Like;//是否赞过

    @Override
    protected int getLayoutId() {
        return R.layout.activity_article_for_activities;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
//        iv_upload_pic.setVisibility(View.VISIBLE);
        mSharePopupWindow = new SharePopupWindow(mContext);
        wv_load.loadUrl("http://wap.baidu.com");
        mRvLayoutParams = rv_content.getLayoutParams();
//        String cache_count = mDiskFileCacheHelper.getAsString(ArticleDetailForActivitiesActivity.COOL_COUNT + mExploreIndex.getArticle_id());
//        mExploreIndex.setCount_likes(TextUtils.isEmpty(cache_count) ? mExploreIndex.getCount_likes() : Integer.parseInt(cache_count));
//        isCool = !TextUtils.isEmpty(AccountHelper.getCurrentUid()) ? mExploreIndex.is_like() : null != mDiskFileCacheHelper.getAsString(ExploreDetailFragment.COOL + mExploreIndex.getArticle_id());
//        sb_cool_icon.setState(isCool);
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
        wv_load.setOnWebViewLoadUrlCallback(new BasicWebView.OnWebViewLoadUrlCallback() {
            @Override
            public void onParsePutaoUrl(String scheme, JSONObject result) {
                if (PutaoParse.PAGE_SETTING.equals(scheme)) {
                    // putao://pageSetting/{isCommnet:1. zanNumber:5, commentNumber:10}
                } else {
                    PutaoParse.parseUrl(mContext, scheme, result);
                }
            }

            @Override
            public void onWebPageLoaderFinish(String url) {

            }
        });
    }

    private void initData() {
        objects = new ArrayList<>();
        objects.add(new ArticleDetailActs());
        objects.add(new ArticleDetailActs());
        objects.add(new ArticleDetailActs());
        objects.add(new ArticleDetailActs());
        objects.add(new ArticleDetailActs());
        objects.add(new ArticleDetailActs());
        mArtivleDetailActsAdapter.replaceAll(objects);

        /**
         *  查询当前文章是否可以被评论、点赞数、评论数
         * */
        getArticleProperty("");
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }


    /**
     * 查询当前文章是否可以被评论、点赞数、评论数
     */
    private void getArticleProperty(String url) {
        networkRequest(CompanionApi.getProperty(url), new SimpleFastJsonCallback<Property>(Property.class, loading) {
            @Override
            public void onSuccess(String url, Property result) {
                boolean is_like = result.is_like();
                int comments_count = result.getComments_count();
                int like_count = result.getLike_count();
//                if (is_like == 0) {//是否已赞(0：未赞，1：已赞)
//
//                } else {
//
//                }
                tv_count_cool.setText(like_count == 0 ? "赞" : like_count + "");
                tv_count_comment.setText(comments_count == 0 ? "评论" : comments_count + "");
            }
        });
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

   /* @OnClick(R.id.iv_upload_pic)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_upload_pic:
                startActivity(PhotoAlbumActivity.class);
                break;
        }
    }*/

    @Override
    public void onRightAction() {
        super.onRightAction();
        mSharePopupWindow.show(navigation_bar);
    }

    @Subcriber(tag = AccountConstants.EventBus.EVENT_ALBUM_SELECT)
    private void setPic(List<ImageInfo> selectPhotos) {
        String uri = !TextUtils.isEmpty(selectPhotos.get(0).THUMB_DATA) ? selectPhotos.get(0).THUMB_DATA : selectPhotos.get(0)._DATA;
//        iv_upload_pic.setImageURL(Uri.parse("file://" + uri).toString());
    }

    @OnClick({R.id.ll_cool, R.id.ll_comment})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_cool://赞
                break;
            case R.id.ll_comment://评论
                startActivity(CommentForArticleActivity.class);
                break;
        }

    }
}
