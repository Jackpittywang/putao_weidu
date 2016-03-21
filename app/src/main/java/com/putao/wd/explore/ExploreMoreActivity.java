package com.putao.wd.explore;

import android.os.Bundle;

import com.putao.wd.R;
import com.putao.wd.api.ExploreApi;
import com.putao.wd.base.PTWDActivity;
import com.putao.wd.created.CreateBasicDetailActivity;
import com.putao.wd.created.CreateCommentActivity;
import com.putao.wd.explore.adapter.MoreAdapter;
import com.putao.wd.model.Create;
import com.putao.wd.model.HomeExploreMore;
import com.putao.wd.model.HomeExploreMores;
import com.putao.wd.start.action.ActionsDetailActivity;
import com.putao.wd.start.comment.CommentActivity;
import com.putao.wd.start.comment.EmojiFragment;
import com.sunnybear.library.controller.eventbus.Subcriber;
import com.sunnybear.library.model.http.callback.SimpleFastJsonCallback;
import com.sunnybear.library.view.PullToRefreshLayout;
import com.sunnybear.library.view.emoji.Emoji;
import com.sunnybear.library.view.recycler.LoadMoreRecyclerView;
import com.sunnybear.library.view.recycler.listener.OnItemClickListener;
import com.umeng.analytics.MobclickAgent;

import java.io.Serializable;

import butterknife.Bind;

/**
 * 更多内容
 * Created by yanghx on 2016/1/12.
 */
public class ExploreMoreActivity extends PTWDActivity {

    public static final String KEY_TAB = "key_title";

    //    @Bind(R.id.tb_bar)
//    DynamicTitleBar tb_bar;
    @Bind(R.id.ptl_refresh)
    PullToRefreshLayout ptl_refresh;
    @Bind(R.id.rv_more)
    LoadMoreRecyclerView rv_more;
    private int mPage;

    //    private String[] titles = new String[]{"牛人说", "玩物志", "葡萄+"};
    private MoreAdapter adapter;
    public static final String EVENT_COMMENT = "event_comment";
    public static final String EVENT_COOL = "event_cool";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_more;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();
/*        int tab = args.getInt(KEY_TAB);
        tb_bar.addTitles(Arrays.asList(titles), tab);
        tb_bar.setOnTitleItemSelectedListener(new TitleBar.OnTitleItemSelectedListener() {
            @Override
            public void onTitleItemSelected(TitleItem item, int position) {
                ToastUtils.showToastLong(mContext, "点击了第" + position + "项");
            }
        });*/
        adapter = new MoreAdapter(mContext, null);
        rv_more.setAdapter(adapter);
        refreshList();
        refresh();
        addListener();
    }

    /**
     * 刷新方法
     */
    private void refresh() {
        ptl_refresh.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshList();
            }
        });
    }

    private void refreshList() {
        mPage = 1;
        networkRequest(ExploreApi.getMoreArticleList(mPage),
                new SimpleFastJsonCallback<HomeExploreMores>(HomeExploreMores.class, loading) {
                    @Override
                    public void onSuccess(String url, HomeExploreMores result) {
                        if (null != result.getList() && result.getList().size() > 0)
                            adapter.replaceAll(result.getList());
                        if (result.getTotal_page() > 1)
                            mPage++;
                        else if (result.getTotal_page() == 1)
                            rv_more.noMoreLoading();
                        loading.dismiss();
                        ptl_refresh.refreshComplete();
                    }
                });
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    /**
     * 添加监听器
     */

    private void addListener() {
        rv_more.setOnItemClickListener(new OnItemClickListener<HomeExploreMore>() {

            @Override
            public void onItemClick(HomeExploreMore homeExploreMore, int position) {
                Bundle bundle = new Bundle();
                bundle.putString(ExploreMoreDetailActivity.ARTICLE_ID, homeExploreMore.getArticle_id());
                bundle.putInt(ExploreMoreDetailActivity.POSITION, position);
                startActivity(ExploreMoreDetailActivity.class, bundle);
            }
        });
        rv_more.setOnLoadMoreListener(new LoadMoreRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                networkRequest(ExploreApi.getMoreArticleList(mPage),
                        new SimpleFastJsonCallback<HomeExploreMores>(HomeExploreMores.class, loading) {
                            @Override
                            public void onSuccess(String url, HomeExploreMores result) {
                                if (result.getCurrent_page() < result.getTotal_page()) {
                                    adapter.addAll(result.getList());
                                    mPage++;
                                    loading.dismiss();
                                    rv_more.loadMoreComplete();
                                } else
                                    rv_more.noMoreLoading();
                            }
                        });
            }
        });
    }

    @Subcriber(tag = EVENT_COMMENT)
    public void eventComment(String article_id) {
        Bundle bundle = new Bundle();
        bundle.putString(ActionsDetailActivity.BUNDLE_ACTION_ID, article_id);
        startActivity(CommentActivity.class, bundle);
    }

    @Subcriber(tag = EVENT_COOL)
    public void eventCool(final String article_id) {
        networkRequest(ExploreApi.addLike(article_id),
                new SimpleFastJsonCallback<String>(String.class, loading) {
                    @Override
                    public void onSuccess(String url, String result) {
                        mDiskFileCacheHelper.put(ExploreDetailFragment.COOL + article_id, true);
                        loading.dismiss();
                    }
                });
    }

    @Subcriber(tag = CommentActivity.EVENT_ADD_CREAT_COMMENT)
    public void eventAddCommentCount(int position) {
        HomeExploreMore item = adapter.getItem(position);
        item.setCount_comments(item.getCount_comments() + 1);
        adapter.notifyItemChanged(position);
    }

    @Subcriber(tag = CommentActivity.EVENT_DELETE_CREAT_COMMENT)
    public void evenDeleteCommentCount(int position) {
        HomeExploreMore item = adapter.getItem(position);
        item.setCount_comments(item.getCount_comments() - 1);
        adapter.notifyItemChanged(position);
    }

    @Subcriber(tag = ExploreMoreDetailActivity.EVENT_ADD_MORE_DETAIL_COOL)
    public void eventAddCoolCount(int position) {
        HomeExploreMore item = adapter.getItem(position);
        item.setCount_likes(item.getCount_likes() + 1);
        item.setIs_like(true);
        adapter.notifyItemChanged(position);
    }

}
