package com.putao.wd.pt_companion;


import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.putao.wd.R;
import com.putao.wd.account.AccountConstants;
import com.putao.wd.account.AccountHelper;
import com.putao.wd.account.YouMengHelper;
import com.putao.wd.album.activity.PhotoAlbumActivity;
import com.putao.wd.album.model.ImageInfo;
import com.putao.wd.api.CompanionApi;
import com.putao.wd.api.ExploreApi;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.model.ArticleDetailActs;
import com.putao.wd.model.ArticleDetailComment;
import com.putao.wd.model.Companion;
import com.putao.wd.model.Property;
import com.putao.wd.model.ServiceMessageList;
import com.putao.wd.pt_companion.adapter.ArticleDetailForActivitiesAdapter;
import com.putao.wd.share.SharePopupWindow;
import com.putao.wd.start.action.ActionsDetailActivity;
import com.putao.wd.start.comment.CommentActivity;
import com.putao.wd.webview.PutaoParse;
import com.sunnybear.library.controller.eventbus.EventBusHelper;
import com.sunnybear.library.controller.eventbus.Subcriber;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.ToastUtils;
import com.sunnybear.library.view.BasicWebView;
import com.sunnybear.library.view.SwitchButton;
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
    public static final String COOL_COUNT = "like_count";
    private static final String COMMENT_COUNT = "comment_count";
    public static final String EVENT_COUNT_COOL = "event_count_cool";

    @Bind(R.id.wv_load)
    BasicWebView wv_load;
/*    @Bind(R.id.rv_content)
    LoadMoreRecyclerView rv_content;*/
    @Bind(R.id.ll_cool)
    LinearLayout ll_cool;//点赞数
    @Bind(R.id.tv_count_cool)
    TextView tv_count_cool;
    @Bind(R.id.sb_cool_icon)
    SwitchButton sb_cool_icon;
    @Bind(R.id.ll_comment)
    LinearLayout ll_comment;//评论数
    @Bind(R.id.tv_count_comment)
    TextView tv_count_comment;
    /* @Bind(R.id.iv_upload_pic)
     ImageDraweeView iv_upload_pic;*/
  /*  @Bind(R.id.sv_load)
    NestScrollView sv_load;*/
//    private ArticleDetailForActivitiesAdapter mArtivleDetailActsAdapter;
    private ArrayList<ArticleDetailActs> objects;
    private String link_url;
    ServiceMessageList messageList;
    ViewGroup.LayoutParams mRvLayoutParams;
    private SharePopupWindow mSharePopupWindow;
    private boolean is_Like;//是否赞过
    private Property property;
    private String article_id, service_id;//文章id,服务号id


    @Override
    protected int getLayoutId() {
        return R.layout.activity_article_for_activities;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
        messageList = (ServiceMessageList) args.getSerializable(AccountConstants.Bundle.BUNDLE_COMPANION_SERVICE_MESSAGE_LIST);
        link_url = messageList.getContent_lists().get(0).getLink_url();
        article_id = messageList.getContent_lists().get(0).getArticle_id();
        service_id = args.getString(AccountConstants.Bundle.BUNDLE_SERVICE_ID);
        mSharePopupWindow = new SharePopupWindow(mContext);
        wv_load.loadUrl("http://wap.baidu.com");
/*        mRvLayoutParams = rv_content.getLayoutParams();
        mArtivleDetailActsAdapter = new ArticleDetailForActivitiesAdapter(mContext, null);
        rv_content.setAdapter(mArtivleDetailActsAdapter);*/
        initData();
        /**
         *  查询当前文章是否可以被评论、点赞数、评论数
         * */
        getArticleProperty(link_url);
        addListener();
    }

    private void addListener() {
        /*wv_load.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                sv_load.scrollTo(0, 0);
            }
        });*/
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
//        mArtivleDetailActsAdapter.replaceAll(objects);
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
                property = result;
                sb_cool_icon.setClickable(false);
                sb_cool_icon.setState(result.is_like());
                tv_count_cool.setText(result.getLike_count() == 0 ? "赞" : result.getLike_count() + "");
                tv_count_comment.setText(result.getComments_count() == 0 ? "评论" : result.getComments_count() + "");
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
//        rv_content.setLayoutParams(mRvLayoutParams);
    }

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
                if (!property.is_like()) {
                    //使用EventBus提交点赞
                    networkRequest(CompanionApi.addCompanyFirstLike(article_id, service_id),
                            new SimpleFastJsonCallback<String>(String.class, loading) {
                                @Override
                                public void onSuccess(String url, String result) {
                                    mDiskFileCacheHelper.put(COOL_COUNT + article_id, "true");
                                    sb_cool_icon.setState(true);
                                    property.setIs_like(true);
                                    tv_count_cool.setText(property.getLike_count() + 1 + "");
                                    EventBusHelper.post("", EVENT_COUNT_COOL);
                                }
                            });
                } else ToastUtils.showToastShort(mContext, "您已经点过赞了");
                break;
            case R.id.ll_comment://评论
                Bundle bundle = new Bundle();
                bundle.putString(CommentForArticleActivity.EVENT_COUNT_ARTICLEID, article_id);
                bundle.putSerializable(AccountConstants.Bundle.BUNDLE_SERVICE_ID, service_id);
                YouMengHelper.onEvent(mContext, YouMengHelper.AccompanyHome_app_detail_back);
                startActivity(CommentForArticleActivity.class, bundle);
                break;
        }

    }
}
