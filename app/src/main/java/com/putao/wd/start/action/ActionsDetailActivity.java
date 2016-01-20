package com.putao.wd.start.action;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.putao.wd.GlobalApplication;
import com.putao.wd.R;
import com.putao.wd.account.AccountHelper;
import com.putao.wd.api.StartApi;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.map.MapActivity;
import com.putao.wd.model.ActionDetail;
import com.putao.wd.model.RegUser;
import com.putao.wd.model.UserInfo;
import com.putao.wd.share.OnShareClickListener;
import com.putao.wd.share.SharePopupWindow;
import com.putao.wd.start.apply.ApplyActivity;
import com.putao.wd.start.apply.ApplyListActivity;
import com.putao.wd.start.browse.PictrueBrowseActivity;
import com.putao.wd.start.comment.CommentActivity;
import com.putao.wd.start.praise.PraiseListActivity;
import com.putao.wd.user.LoginActivity;
import com.sunnybear.library.controller.eventbus.Subcriber;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.ListUtils;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.view.BasicWebView;
import com.sunnybear.library.view.SwitchButton;
import com.sunnybear.library.view.image.ImageDraweeView;
import com.sunnybear.library.view.recycler.adapter.BasicAdapter;
import com.sunnybear.library.view.recycler.BasicRecyclerView;
import com.sunnybear.library.view.recycler.BasicViewHolder;
import com.sunnybear.library.view.select.TitleBar;
import com.sunnybear.library.view.select.TitleItem;
import com.sunnybear.library.view.sticky.StickyHeaderLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;


/**
 * 活动详情
 * Created by wango on 2015/12/4.
 */
public class ActionsDetailActivity extends PTWDActivity<GlobalApplication> implements View.OnClickListener, TitleBar.OnTitleItemSelectedListener {
    public static final String BUNDLE_ACTION_ID = "action_id";
    private boolean mIsUserLike;

    @Bind(R.id.sticky_layout)
    StickyHeaderLayout sticky_layout;
    @Bind(R.id.iv_actionssdetail_header)
    ImageDraweeView iv_actionssdetail_header;
    @Bind(R.id.tv_actionsdetail_status)
    TextView tv_actionsdetail_status;
    @Bind(R.id.tv_actionsdetail_title)
    TextView tv_actionsdetail_title;
    @Bind(R.id.tv_actionsdetail_resume)
    TextView tv_actionsdetail_resume;
    @Bind(R.id.stickyHeaderLayout_scrollable)
    BasicWebView wb_html_content;
    @Bind(R.id.tv_count_cool)
    TextView tv_count_cool;
    @Bind(R.id.tv_count_comment)
    TextView tv_count_comment;
    @Bind(R.id.stickyHeaderLayout_sticky)
    TitleBar ll_title;
    @Bind(R.id.rv_actionsdetail_applyusers)
    BasicRecyclerView rv_actionsdetail_applyusers;
    @Bind(R.id.tv_actionsdetail_applycount)
    TextView tv_actionsdetail_applycount;
    @Bind(R.id.tv_join)
    TextView tv_join;
    @Bind(R.id.tv_personinfo_address)
    TextView tv_personinfo_address;
    private SharePopupWindow mSharePopupWindow;//分享弹框
    @Bind(R.id.ll_main)
    RelativeLayout ll_main;
    @Bind(R.id.sb_cool_icon)
    SwitchButton sb_cool_icon;
    @Bind(R.id.ll_cool)
    LinearLayout ll_cool;

    private ActionDetail actionDetail;
    private UserIconAdapter adapter;
    private String action_id;
    //H5请求URL:http://static.uzu.wang/weidu_event/view/active_info.html?id=1&device=m&nav=0
    //id和nav值分别对应活动ID和活动类型
    private final String BASE_HTML_URL = "http://static.uzu.wang/weidu_event/view/active_info.html?id=";
    private final String HTML_Mid = "&device=m&nav=";
    private int action_type;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_actions_detail;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();

        sticky_layout.canScrollView();
        mSharePopupWindow = new SharePopupWindow(mContext);
        adapter = new UserIconAdapter(mContext, null);
        rv_actionsdetail_applyusers.setAdapter(adapter);
        action_id = args.getString(BUNDLE_ACTION_ID);
        sb_cool_icon.setClickable(false);
        ll_cool.setClickable(false);//设置没有加载完成点赞按钮不可以点击
        networkRequest(StartApi.getActionDetail(action_id, sb_cool_icon.getState()), new SimpleFastJsonCallback<ActionDetail>(ActionDetail.class, loading) {
            @Override
            public void onSuccess(String url, ActionDetail result) {
                actionDetail = result;
                mIsUserLike = result.is_user_like();
                ll_cool.setClickable(true);//释放点赞按钮点击
                sb_cool_icon.setState(mIsUserLike); //设置是不是被赞的按钮样式
                iv_actionssdetail_header.setImageURL(result.getBanner_url());
                tv_actionsdetail_status.setText(result.getStatus());
                tv_actionsdetail_title.setText(result.getLabel());
                tv_actionsdetail_resume.setText(result.getTitle());
                tv_count_cool.setText(result.getCount_cool() != 0 ? result.getCount_cool() + "" : "赞");
                tv_count_comment.setText(result.getCount_comment() != 0 ? result.getCount_comment() + "" : "评论");
                tv_personinfo_address.setText(result.getLocation());
                List<RegUser> reg_user = result.getReg_user();
                if (reg_user.size() > 4)
                    reg_user = ListUtils.cutOutList(reg_user, 0, 4);
                Logger.i("reg_user = " + reg_user.toString());
                adapter.replaceAll(reg_user);
                tv_actionsdetail_applycount.setText(result.getRegistration_number() + "");
                loadHtml(action_id, action_type);
                setJoinStyle(actionDetail);
                loading.dismiss();
            }
        });
        addListener();
    }

    /**
     * @param action_id   活动ID
     * @param action_type 活动分类
     */
    private void loadHtml(String action_id, int action_type) {
        String url = BASE_HTML_URL + action_id + HTML_Mid + action_type;
        wb_html_content.loadUrl(url);
    }

    private void addListener() {
        ll_title.setOnTitleItemSelectedListener(this);
        wb_html_content.setOnWebViewLoadUrlCallback(new BasicWebView.OnWebViewLoadUrlCallback() {
            @Override
            public void onParsePutaoUrl(String scheme, JSONObject result) {
                switch (scheme) {
                    case GlobalApplication.Scheme.VIEWPIC:
                        int clickIndex = result.getInteger("clickIndex");
                        List<String> picList = new ArrayList<>();
                        JSONArray array = result.getJSONArray("picList");
                        for (int i = 0; i < array.size(); i++) {
                            JSONObject object = (JSONObject) array.get(i);
                            picList.add(object.getString("src"));
                        }
                        Bundle bundle = new Bundle();
                        bundle.putInt(PictrueBrowseActivity.BUNDLE_CLICK_INDEX, clickIndex);
                        bundle.putSerializable(PictrueBrowseActivity.BUNDLE_PICTRUES, (Serializable) picList);
                        startActivity(PictrueBrowseActivity.class, bundle);
                        break;
                }
            }

            @Override
            public void onWebPageLoaderFinish(String url) {

            }
        });
        mSharePopupWindow.setOnShareClickListener(new OnShareClickListener() {
            @Override
            public void onWechat() {

            }

            @Override
            public void onWechatFriend() {

            }
        });
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @OnClick({R.id.ll_join_list, R.id.ll_cool, R.id.ll_comment, R.id.tv_join, R.id.tv_personinfo_address})
    @Override
    public void onClick(View v) {
        if (!AccountHelper.isLogin()) {
            Bundle bundle = new Bundle();
            toLoginActivity(v, bundle);
            return;
        }
        switch (v.getId()) {
            case R.id.ll_join_list://报名列表
                startActivity(ApplyListActivity.class, args);
                break;
            case R.id.ll_cool://点赞
                if (mIsUserLike) {
                    startActivity(PraiseListActivity.class, args);
                } else {
                    UserInfo userInfo = AccountHelper.getCurrentUserInfo();
                    networkRequest(StartApi.coolAdd(action_id, userInfo.getNick_name(), "EVENT",
                            AccountHelper.getCurrentUid(), userInfo.getHead_img()),
                            new SimpleFastJsonCallback<String>(String.class, loading) {
                                @Override
                                public void onSuccess(String url, String result) {
                                    sb_cool_icon.setState(true);
                                    tv_count_cool.setText((Integer.parseInt(tv_count_cool.getText().toString()) + 1) + "");
                                    mIsUserLike = true;
                                    loading.dismiss();
                                }
                            });
                }
                break;
            case R.id.ll_comment://评论
                startActivity(CommentActivity.class, args);
                break;
            case R.id.tv_join://我要参加
                startActivity(ApplyActivity.class, args);
                break;
            case R.id.tv_personinfo_address://地址
                startActivity(MapActivity.class, args);
                break;
        }
    }

    /**
     * put进目标activity供登录后跳转
     */
    private void toLoginActivity(View v, Bundle bundle) {
        bundle.putString(BUNDLE_ACTION_ID, action_id);
        bundle.putSerializable(LoginActivity.TERMINAL_ACTIVITY, ActionsDetailActivity.class);
        startActivity(LoginActivity.class, bundle);
    }

    @Override
    public void onTitleItemSelected(TitleItem item, int position) {
        switch (item.getId()) {
            case R.id.ll_action_instruct://活动说明
                action_type = 0;
                break;
            case R.id.ll_action_location://活动现场
                action_type = 1;
                break;
            case R.id.ll_finish://精彩回顾

                break;
        }
        loadHtml(action_id, action_type);
    }

    @Override
    public void onRightAction() {
        mSharePopupWindow.show(ll_main);
    }

    /**
     * 我要参加按钮的显示控制
     */
    private void setJoinStyle(ActionDetail actionDetail) {
//        actionDetail.getStatus();  //ONGOING  LOOKBACK
        switch (actionDetail.getStatus()) {
            case "进行中":
                tv_join.setVisibility(View.VISIBLE);
                if (actionDetail.isParticipate()) {
                    tv_join.setEnabled(false);
                    tv_join.setBackgroundResource(R.color.text_color_646464);
                    tv_join.setText("已报名");
                }
                break;
            case "截止":
                tv_join.setVisibility(View.GONE);
                break;
            case "回顾":
                tv_join.setVisibility(View.GONE);
                break;
        }
    }

    //赞或取消赞时更新此页显示
    @Subcriber(tag = CommentActivity.EVENT_COUNT_COOL)
    public void eventClickComment(boolean isCool) {
        if (isCool) {
            actionDetail.setCount_cool(actionDetail.getCount_cool() + 1);
            tv_count_comment.setText(actionDetail.getCount_cool() + "");
        } else {
            actionDetail.setCount_cool(actionDetail.getCount_cool() - 1);
            tv_count_comment.setText(actionDetail.getCount_cool() + "");
        }
    }

    //添加或删除评论时更新此页显示
    @Subcriber(tag = CommentActivity.EVENT_COUNT_COMMENT)
    public void eventClickCoool(boolean isComment) {
        if (isComment) {
            actionDetail.setCount_comment(actionDetail.getCount_comment() + 1);
            tv_count_comment.setText(actionDetail.getCount_comment() + "");
        } else {
            actionDetail.setCount_comment(actionDetail.getCount_comment() - 1);
            tv_count_comment.setText(actionDetail.getCount_comment() + "");
        }
    }


    /**
     * 报名列表适配器
     */
    static class UserIconAdapter extends BasicAdapter<RegUser, UserIconViewHolder> {

        public UserIconAdapter(Context context, List<RegUser> regUsers) {
            super(context, regUsers);
        }

        @Override
        public int getLayoutId(int viewType) {
            return R.layout.activity_actions_detail_item;
        }

        @Override
        public UserIconViewHolder getViewHolder(View itemView, int viewType) {
            return new UserIconViewHolder(itemView);
        }

        @Override
        public void onBindItem(UserIconViewHolder holder, RegUser regUser, int position) {
            holder.iv_user_icon.setImageURL(regUser.getUser_profile_photo());
        }
    }

    static class UserIconViewHolder extends BasicViewHolder {
        @Bind(R.id.iv_user_icon)
        ImageDraweeView iv_user_icon;

        public UserIconViewHolder(View itemView) {
            super(itemView);
        }
    }
}
