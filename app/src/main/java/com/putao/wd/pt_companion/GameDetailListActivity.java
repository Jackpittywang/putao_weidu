package com.putao.wd.pt_companion;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.putao.wd.GlobalApplication;
import com.putao.wd.R;
import com.putao.wd.account.AccountConstants;
import com.putao.wd.account.YouMengHelper;
import com.putao.wd.api.CompanionApi;
import com.putao.wd.api.ExploreApi;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.db.CompanionDBManager;
import com.putao.wd.db.entity.CompanionDB;
import com.putao.wd.model.Companion;
import com.putao.wd.model.ServiceMenu;
import com.putao.wd.model.ServiceMessage;
import com.putao.wd.model.ServiceMessageContent;
import com.putao.wd.model.ServiceMessageList;
import com.putao.wd.model.ServiceSendData;
import com.putao.wd.pt_companion.adapter.GameDetailAdapter;
import com.putao.wd.util.RedDotUtils;
import com.putao.wd.webview.BaseWebViewActivity;
import com.sunnybear.library.controller.eventbus.EventBusHelper;
import com.sunnybear.library.controller.eventbus.Subcriber;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.util.DensityUtil;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.util.ToastUtils;
import com.sunnybear.library.view.PullToRefreshLayout;
import com.sunnybear.library.view.recycler.BasicRecyclerView;
import com.sunnybear.library.view.recycler.listener.OnItemClickListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;

/**
 * 游戏详情页
 * Created by zhanghao on 2016/04/05.
 */
public class GameDetailListActivity extends PTWDActivity<GlobalApplication> implements OnClickListener {
    @Bind(R.id.rv_content)
    BasicRecyclerView rv_content;
    @Bind(R.id.ptl_refresh)
    PullToRefreshLayout ptl_refresh;

   /* @Bind(R.id.ll_bottom_menus)
    LinearLayout ll_bottom_menus;
    @Bind(R.id.ll_comment_edit)
    LinearLayout ll_comment_edit;
    @Bind(R.id.tv_menu_first)
    TextView tv_menu_first;
    @Bind(R.id.tv_menu_second)
    TextView tv_menu_second;
    @Bind(R.id.tv_menu_third)
    TextView tv_menu_third;
    @Bind(R.id.vLineLeft)
    View vLineLeft;
    @Bind(R.id.vLineRight)
    View vLineRight;*/

    @Bind(R.id.rl_customemenu)
    RelativeLayout rl_customemenu;

    @Bind(R.id.ll_custommenu)
    LinearLayout ll_custommenu;
    @Bind(R.id.ll_comment_edit)
    LinearLayout ll_comment_edit;
    @Bind(R.id.iv_menu)
    ImageView iv_menu;
    @Bind(R.id.iv_send)
    ImageView iv_send;
    @Bind(R.id.vp_emojis)
    ViewPager vp_emojis;
    @Bind(R.id.v_split)
    View v_split;
    @Bind(R.id.et_msg)
    EditText et_msg;


    private boolean isLoadMore = false;
    private GameDetailAdapter mGameDetailAdapter;
    private int mPosition;
    private int mPage;
    private ArrayList<String> list = new ArrayList<>();
    private ArrayList<ServiceSendData> mServiceSendData;
    private Companion mCompanion;
    private String mServiceId;
    private ArrayList<ServiceMessageList> lists;
    private TranslateAnimation showAnim;
    private TranslateAnimation hintAnim;
    private PopMenus popupWindow_custommenu;
    private Animation.AnimationListener mShowMenuListener;
    private Animation.AnimationListener mShowSendListener;
    private String msg;
    private CompanionDBManager mDataBaseManager;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_game_detail_list;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {
        addNavigation();
        mDataBaseManager = (CompanionDBManager) mApp.getDataBaseManager(CompanionDBManager.class);
        mCompanion = (Companion) args.getSerializable(AccountConstants.Bundle.BUNDLE_COMPANION);
        if (null != mCompanion) {
            setMainTitle(mCompanion.getService_name());
            mServiceId = mCompanion.getService_id();
        } else {
            mServiceId = args.getString(AccountConstants.Bundle.BUNDLE_COMPANION_BIND_SERVICE);
            navigation_bar.setRightClickable(false);
            setMainTitleFromNetwork();
        }
        mGameDetailAdapter = new GameDetailAdapter(mContext, null);
        rv_content.setAdapter(mGameDetailAdapter);
        lists = new ArrayList<>();
        initAnim();
        initData();
        initBottomMenu();
        addListener();

    }


    private void setMainTitleFromNetwork() {
        networkRequest(CompanionApi.getServiceInfo(mServiceId),
                new SimpleFastJsonCallback<String>(String.class, loading) {
                    @Override
                    public void onSuccess(String url, String result) {
                        com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(result);
                        setMainTitle(jsonObject.getString("service_name"));
                        mCompanion = new Companion();
                        mCompanion.setService_id(jsonObject.getString("service_id"));
                        mCompanion.setService_name(jsonObject.getString("service_name"));
                        mCompanion.setService_icon(jsonObject.getString("service_icon"));
                        mCompanion.setIs_relation(jsonObject.getInteger("is_relation"));
                        mCompanion.setIs_unbunding(jsonObject.getBoolean("is_unbunding"));
                        mCompanion.setService_description(jsonObject.getString("service_description"));
                        navigation_bar.setRightClickable(true);
                        loading.dismiss();
                    }

                    @Override
                    public void onFailure(String url, int statusCode, String msg) {
                        super.onFailure(url, statusCode, msg);
                        ptl_refresh.refreshComplete();
                    }
                }, false);
    }

    /**
     * 下拉刷新 以及 最初的初始化
     */
    private void initData() {
        CompanionDBManager dataBaseManager = (CompanionDBManager) mApp.getDataBaseManager(CompanionDBManager.class);
        List<CompanionDB> downloadArticles = dataBaseManager.getDownloadArticles(mServiceId);
        if (null != downloadArticles) {
            for (CompanionDB companionDB : downloadArticles) {
                ServiceMessageList serviceMessageList = new ServiceMessageList();
                String content_lists = companionDB.getContent_lists();
                String message = companionDB.getMessage();

                if (!TextUtils.isEmpty(content_lists))
                    serviceMessageList.setContent_lists(JSON.parseArray(content_lists, ServiceMessageContent.class));
                if (!TextUtils.isEmpty(message)) {
                    serviceMessageList.setMessage(message);
                }
                serviceMessageList.setRelease_time(Integer.parseInt(companionDB.getRelease_time()));
                serviceMessageList.setType(companionDB.getType());
                serviceMessageList.setId(companionDB.getId());
                lists.add(serviceMessageList);
            }
            mGameDetailAdapter.replaceAll(lists);
            if (lists.size() > 0)
                rv_content.scrollToPosition(lists.size() - 1);
//            mGameDetailAdapter.replaceAll(JSONArray.parseArray(JSONArray.toJSONString(downloadArticles), ServiceMessageList.class));
        }
//        mPage = 1;
        if (null == mCompanion)
            return;
        ArrayList<String> notDownloadIds = dataBaseManager.getNotDownloadIds(mServiceId);
        List<ServiceSendData> serviceSendDatas = listToServiceListData(notDownloadIds);
        mCompanion.setNotDownloadIds(null);
        if (null != serviceSendDatas && serviceSendDatas.size() > 0)
            networkRequest(CompanionApi.getServiceLists(com.alibaba.fastjson.JSONObject.toJSONString(serviceSendDatas), mServiceId),
                    new SimpleFastJsonCallback<ServiceMessage>(ServiceMessage.class, loading) {
                        @Override
                        public void onSuccess(String url, ServiceMessage result) {
                            isLoadMore = false;
                            lists = result.getLists();
                            if (null != lists && lists.size() > 0) {
                                CompanionDBManager dataBaseManager = (CompanionDBManager) mApp.getDataBaseManager(CompanionDBManager.class);
                                for (ServiceMessageList serviceMessageList : lists) {
                                    dataBaseManager.updataDownloadFinish(serviceMessageList);
                                }
                                EventBusHelper.post("", AccountConstants.EventBus.EVENT_REFRESH_COMPANION);
//                            lists = setIsSameDate(lists);
                                mGameDetailAdapter.addAll(lists);
                                rv_content.scrollToPosition(mGameDetailAdapter.getItemCount() - 1);
                            }
                            ptl_refresh.refreshComplete();
//                            checkLoadMoreComplete(lists);
                            loading.dismiss();
                        }

                        @Override
                        public void onFailure(String url, int statusCode, String msg) {
                            super.onFailure(url, statusCode, msg);
                            ptl_refresh.refreshComplete();
                        }
                    }, false);
    }


    private List<ServiceSendData> listToServiceListData(ArrayList<String> notDownloadIds) {
        if (null == mServiceSendData)
            mServiceSendData = new ArrayList<ServiceSendData>();
        if (null != notDownloadIds)
            for (String str : notDownloadIds) {
                mServiceSendData.add(new ServiceSendData(str));
            }
        return mServiceSendData;
    }

    /**
     * 设置是否为同一天
     *
     * @param result
     * @return
     */
   /* private ArrayList<ServiceMessageList> setIsSameDate(ArrayList<ServiceMessageList> result) {

        // 如果是 下拉刷新或重新进入则将 list（存用户的临时集合） 集合清空
        if (!isLoadMore) {
            list.clear();
        }

        for (int position = 0; position < result.size(); position++) {
            ServiceMessageList blackboard = result.get(position);

            if (!list.contains(getTimeDate(blackboard))) {
                blackboard.setIsShowData(true);
                list.add(getTimeDate(blackboard));
            } else {
                blackboard.setIsShowData(false);
            }
        }
        return result;
    }*/

    /**
     * 取将时间装换为天数
     */
    private String getTimeDate(ServiceMessageList serviceMessageList) {
        long time = Integer.valueOf(serviceMessageList.getRelease_time()) * 1000L;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        return dateFormat.format(time);
    }

    private void checkLoadMoreComplete(ArrayList<ServiceMessageList> result) {
       /* if (null == result)
            rv_content.noMoreLoading();
        else
            mPage++;*/
    }

    private void addListener() {
        ptl_refresh.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ptl_refresh.refreshComplete();
            }
        });
        /*rv_content.setOnLoadMoreListener(new LoadMoreRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                rv_content.loadMoreComplete();
                rv_content.noMoreLoading();
            }
        });*/
        rv_content.setOnItemClickListener(new OnItemClickListener<ServiceMessageList>() {
            @Override
            public void onItemClick(ServiceMessageList serviceMessageList, int position) {

               /* Bundle bundle = new Bundle();
                bundle.putSerializable(AccountConstants.Bundle.BUNDLE_COMPANION_SERVICE_MESSAGE_LIST, serviceMessageList);
                bundle.putString(AccountConstants.Bundle.BUNDLE_SERVICE_ID, mServiceId);
                bundle.putString(AccountConstants.Bundle.BUNDLE_SERVICE_NAME, mCompanion.getService_name());
                bundle.putString(BaseWebViewActivity.URL, serviceMessageList.getContent_lists().get(0).getLink_url());
                startActivity(ArticleDetailForActivitiesActivity.class, bundle);*/
            }
        });
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @OnClick({R.id.iv_send, R.id.iv_menu, R.id.tv_send})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_send:
                iv_send.setEnabled(false);
                vp_emojis.setVisibility(View.GONE);
                hintAnim.setAnimationListener(mShowSendListener);
                ll_comment_edit.startAnimation(hintAnim);
                break;
            case R.id.iv_menu:
                iv_menu.setEnabled(false);
                hintAnim.setAnimationListener(mShowMenuListener);
                rl_customemenu.startAnimation(hintAnim);
                break;
            case R.id.tv_send://点击发送
                sendAsk();
                break;
        }
    }

    private void sendAsk() {
        msg = et_msg.getText().toString();
        if (msg.trim().isEmpty()) {
            ToastUtils.showToastShort(mContext, "不能发送空消息");
            return;
        }
        ServiceMessageList serviceMessageList = new ServiceMessageList();
        serviceMessageList.setRelease_time((int) (System.currentTimeMillis() / 1000));
        serviceMessageList.setMessage(msg);
        serviceMessageList.setType("upload_text");
        mGameDetailAdapter.add(serviceMessageList);
        mDataBaseManager.insertUploadText(mServiceId, msg);
        rv_content.smoothScrollToPosition(mGameDetailAdapter.getItemCount() - 1);
        networkRequest(CompanionApi.sendServiceQuiz(mServiceId, msg, 1),
                new SimpleFastJsonCallback<String>(String.class, loading) {
                    @Override
                    public void onSuccess(String url, String result) {
                        if (!TextUtils.isEmpty(result)) {
                            JSONObject jsonObject = JSONObject.parseObject(result);
                            String message = (String) jsonObject.get("message");
                            if (!TextUtils.isEmpty(message)) return;
                            ServiceMessageList serviceMessageList = new ServiceMessageList();
                            serviceMessageList.setRelease_time((int) (System.currentTimeMillis() / 1000));
                            serviceMessageList.setType("text");
                            serviceMessageList.setMessage(message);
                            mGameDetailAdapter.add(serviceMessageList);
//                            mDataBaseManager.insertUploadText(mServiceId, msg);
                        }
                    }

                    @Override
                    public void onFailure(String url, int statusCode, String msg) {
                        super.onFailure(url, statusCode, msg);
                        ToastUtils.showToastShort(mContext, "发送失败，请检查您的网络");
                    }
                });
    }

    @Override
    public void onLeftAction() {
        super.onLeftAction();
        YouMengHelper.onEvent(mContext, YouMengHelper.Activity_list_back);
        finish();
    }

    @Override
    public void onRightAction() {
        super.onRightAction();
        Bundle bundle = new Bundle();
        bundle.putSerializable(AccountConstants.Bundle.BUNDLE_COMPANION, mCompanion);
        startActivity(OfficialAccountsActivity.class, bundle);
    }


    private void initBottomMenu() {
//        final TextView[] menuViews = {tv_menu_first, tv_menu_second, tv_menu_third};
        networkRequestCache(CompanionApi.getServicemenu(mServiceId),
                new SimpleFastJsonCallback<ArrayList<ServiceMenu>>(ServiceMenu.class, loading) {
                    @Override
                    public void onSuccess(String url, ArrayList<ServiceMenu> result) {
                        if (result != null && result.size() > 0) {
                            cacheData(CompanionApi.getServicemenu(mServiceId).urlString() + mServiceId, result);
                            setCustomMenu(result);
                            rl_customemenu.setVisibility(View.VISIBLE);
                            ll_comment_edit.setVisibility(View.GONE);
                        } else {
                            setNoMenu();
                        }
                    }

                    @Override
                    public void onFailure(String url, int statusCode, String msg) {
                        setNoMenu();
                    }
                }, CompanionApi.getServicemenu(mServiceId).urlString() + mServiceId, 60 * 1000);
    }

    private void setNoMenu() {
        v_split.setVisibility(View.GONE);
        rl_customemenu.setVisibility(View.GONE);
        ll_comment_edit.setVisibility(View.VISIBLE);
        iv_send.setVisibility(View.GONE);
    }

    private void setCustomMenu(ArrayList<ServiceMenu> result) {
        ll_custommenu.removeAllViews();
        for (final ServiceMenu serviceMenu : result) {
            LinearLayout layout = (LinearLayout) ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.item_custommenu, null);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1.0f);
            layout.setLayoutParams(lp);
            final TextView tv_custommenu_name = (TextView) layout.findViewById(R.id.tv_custommenu_name);
            tv_custommenu_name.setText(serviceMenu.getName());
            if (null != serviceMenu.getSub_button() && serviceMenu.getSub_button().size() > 0) // 显示三角
            {
                tv_custommenu_name.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_up_black, 0);
                layout.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        popupWindow_custommenu = new PopMenus(getApplicationContext(), serviceMenu.getSub_button(), v.getWidth() + 10, 0) {
                            @Override
                            void secondMenuClick(ServiceMenu serviceMenu, int position) {
                                startToWebViewActivity(serviceMenu);

                            }
                        };
                        popupWindow_custommenu.showAtLocation(v);
                    }
                });
            } else // 隐藏三角
            {
                tv_custommenu_name.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                layout.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        startToWebViewActivity(serviceMenu);
                    }
                });
            }
            ll_custommenu.addView(layout);
        }
    }

    private void startToWebViewActivity(ServiceMenu serviceMenu) {
        switch (serviceMenu.getType()) {
            case "view":
                Bundle bundle = new Bundle();
                bundle.putSerializable(BaseWebViewActivity.TITLE, serviceMenu.getName());
                bundle.putSerializable(BaseWebViewActivity.URL, serviceMenu.getUrl());
                startActivity(BaseWebViewActivity.class, bundle);
                break;

        }
    }

    /*  private void addMenuListener(final TextView menuView, final int position) {
          menuView.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  switch (position) {
                      case 0:
                          YouMengHelper.onEvent(mContext, YouMengHelper.Activity_list_menu, "菜单1");
                          game_detail_list_panel.setVisibility(View.VISIBLE);
                          game_detail_list_panel2.setVisibility(View.GONE);
                          game_detail_list_panel3.setVisibility(View.GONE);
                          break;
                      case 1:
                          YouMengHelper.onEvent(mContext, YouMengHelper.Activity_list_menu, "菜单2");
                          game_detail_list_panel.setVisibility(View.GONE);
                          game_detail_list_panel2.setVisibility(View.VISIBLE);
                          game_detail_list_panel3.setVisibility(View.GONE);
                          break;
                      case 2:
                          YouMengHelper.onEvent(mContext, YouMengHelper.Activity_list_menu, "菜单3");
                          game_detail_list_panel.setVisibility(View.GONE);
                          game_detail_list_panel2.setVisibility(View.GONE);
                          game_detail_list_panel3.setVisibility(View.VISIBLE);
                          break;
                  }
  //                ServiceMenu menu = (ServiceMenu) v.getTag();
  //                if (ServiceMenu.TYPE_VIEW.equals(menu.getType()) && !StringUtils.isEmpty(menu.getUrl())) {
  //                    //跳转web
  //                    Intent intent = new Intent(mContext, BaseWebViewActivity.class);
  //                    intent.putExtra(BaseWebViewActivity.TITLE, menu.getName());
  //                    intent.putExtra(BaseWebViewActivity.URL, menu.getUrl());
  //                    intent.putExtra(BaseWebViewActivity.SERVICE_ID, mCompanion.getService_id());
  //                    startActivity(intent);
  //                } else if (ServiceMenu.TYPE_CLICK.equals(menu.getType())) {
  //                    //TODO
  //                }
              }
          });
      }*/
    private void initAnim() {
        showAnim = new TranslateAnimation(0, 0, DensityUtil.dp2px(mContext, 50), 0);
        showAnim.setDuration(100);
        hintAnim = new TranslateAnimation(0, 0, 0, DensityUtil.dp2px(mContext, 50));
        hintAnim.setDuration(100);
        mShowSendListener = new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                iv_menu.setEnabled(true);
                ll_comment_edit.setVisibility(View.GONE);
                rl_customemenu.setVisibility(View.VISIBLE);
                rl_customemenu.startAnimation(showAnim);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        };
        mShowMenuListener = new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                iv_send.setEnabled(true);
                rl_customemenu.setVisibility(View.GONE);
                ll_comment_edit.setVisibility(View.VISIBLE);
                ll_comment_edit.startAnimation(showAnim);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        };
    }

    @Subcriber(tag = AccountConstants.EventBus.EVENT_GAME_START_ACTIVITY)
    private void startAct(ServiceMessageContent serviceMessageContent) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(BaseWebViewActivity.TITLE, serviceMessageContent.getTitle());
        bundle.putSerializable(BaseWebViewActivity.URL, serviceMessageContent.getLink_url());
        startActivity(BaseWebViewActivity.class, bundle);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        popupWindow_custommenu = null;
    }
}


