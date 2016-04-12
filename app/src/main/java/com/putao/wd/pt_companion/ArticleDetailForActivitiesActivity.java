package com.putao.wd.pt_companion;


import android.content.Context;
import android.os.Bundle;
import android.text.ClipboardManager;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.putao.wd.R;
import com.putao.wd.account.AccountConstants;
import com.putao.wd.account.YouMengHelper;
import com.putao.wd.album.model.ImageInfo;
import com.putao.wd.api.CompanionApi;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.model.ArticleDetailActs;
import com.putao.wd.model.Property;
import com.putao.wd.model.ServiceMessageContent;
import com.putao.wd.model.ServiceMessageList;
import com.putao.wd.pt_companion.adapter.ArticleDetailForActivitiesAdapter;
import com.putao.wd.share.OnShareClickListener;
import com.putao.wd.share.SharePopupWindow;
import com.putao.wd.share.ShareTools;
import com.putao.wd.webview.PutaoParse;
import com.sunnybear.library.controller.eventbus.EventBusHelper;
import com.sunnybear.library.controller.eventbus.Subcriber;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.ToastUtils;
import com.sunnybear.library.view.BasicWebView;
import com.sunnybear.library.view.SwitchButton;

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
    private String article_id, service_id, type;//文章id,服务号id
    private String title;
    private String sub_title;
    private String cover_pic;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_article_for_activities;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
        messageList = (ServiceMessageList) args.getSerializable(AccountConstants.Bundle.BUNDLE_COMPANION_SERVICE_MESSAGE_LIST);
        service_id = args.getString(AccountConstants.Bundle.BUNDLE_SERVICE_ID);
        final ServiceMessageContent content_list = messageList.getContent_lists().get(0);
        title = content_list.getTitle();
        sub_title = content_list.getSub_title();
        cover_pic = content_list.getCover_pic();
        link_url = content_list.getLink_url();
        article_id = content_list.getArticle_id();
        type = messageList.getType();
        mSharePopupWindow = new SharePopupWindow(mContext);
        wv_load.loadUrl(link_url);
        setMainTitle(sub_title);
        initData();
        getArticleProperty(link_url);
        addListener();
    }

    private void addListener() {
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


        mSharePopupWindow.setOnShareClickListener(new OnShareClickListener() {
            @Override
            public void onWechat() {
                ShareTools.wechatWebShare(mContext, true, title, sub_title, cover_pic, link_url);
            }

            @Override
            public void onWechatFriend() {
                ShareTools.wechatWebShare(mContext, false, title, sub_title, cover_pic, link_url);
            }

            @Override
            public void onQQFriend() {
                ShareTools.OnQQZShare(mContext, true, title, sub_title, cover_pic, link_url);
            }

            @Override
            public void onQQZone() {
                ShareTools.OnQQZShare(mContext, false, title, sub_title, cover_pic, link_url);
            }

            public void onSinaWeibo() {
                ShareTools.OnWeiboShare(mContext, title, sub_title, link_url);
            }

            @Override
            public void onCopyUrl() {
                ClipboardManager copy = (ClipboardManager) mContext
                        .getSystemService(Context.CLIPBOARD_SERVICE);
                copy.setText(link_url);
                ToastUtils.showToastShort(mContext, "复制成功");
            }


            @Override
            public void onCollection(TextView textView, ImageView imageView) {
                if (!property.is_collect())
                    addCollect(textView, imageView);
                else
                    cancelCollection(textView, imageView);

            }
        });
    }

    private void addCollect(final TextView textView, final ImageView imageView) {
        networkRequest(CompanionApi.addCollects(article_id, link_url), new SimpleFastJsonCallback<String>(String.class, loading) {
            @Override
            public void onSuccess(String url, String result) {
                ToastUtils.showToastShort(mContext, "收藏成功");
                property.setIs_collect(true);
                imageView.setImageResource(R.drawable.icon_40_14);
                textView.setText("已收藏");
                loading.dismiss();
            }
        });
    }

    /**
     * 取消收藏
     */
    private void cancelCollection(final TextView textView, final ImageView imageView) {
        networkRequest(CompanionApi.cancelCollects(type, article_id), new SimpleFastJsonCallback<String>(String.class, loading) {
            @Override
            public void onSuccess(String url, String result) {
                ToastUtils.showToastShort(mContext, "取消收藏");
                property.setIs_collect(false);
                imageView.setImageResource(R.drawable.icon_40_13);
                textView.setText("收藏");
                loading.dismiss();
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
                if (result.getLike_count() != 0) {
                    tv_count_cool.setText(result.getLike_count() + "");
                } else {
                    tv_count_cool.setText("赞");
                }
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

    @OnClick({R.id.ll_cool, R.id.ll_comment, R.id.sb_cool_icon})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_cool://赞
                if (!property.is_like()) {
                    sb_cool_icon.setState(true);
                    property.setIs_like(true);
                    property.setLike_count(property.getLike_count() + 1);
                    ll_cool.setClickable(false);
                    tv_count_cool.setText(property.getLike_count() + "");
                    mDiskFileCacheHelper.put(COOL_COUNT + article_id, "true");
                    //使用EventBus提交点赞
                    networkRequest(CompanionApi.addCompanyFirstLike(article_id, service_id),
                            new SimpleFastJsonCallback<String>(String.class, loading) {
                                @Override
                                public void onSuccess(String url, String result) {
                                    EventBusHelper.post(true, EVENT_COUNT_COOL);
                                    loading.dismiss();
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
            case R.id.sb_cool_icon:
                //使用EventBus提交点赞
                networkRequest(CompanionApi.addCompanyFirstLike(article_id, service_id),
                        new SimpleFastJsonCallback<String>(String.class, loading) {
                            @Override
                            public void onSuccess(String url, String result) {
                                mDiskFileCacheHelper.put(COOL_COUNT + article_id, "true");
                                EventBusHelper.post(true, EVENT_COUNT_COOL);
                            }
                        });
                break;
        }

    }
}
