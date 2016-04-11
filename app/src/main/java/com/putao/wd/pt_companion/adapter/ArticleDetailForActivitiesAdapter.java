package com.putao.wd.pt_companion.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.model.ArticleDetailActs;
import com.sunnybear.library.controller.eventbus.EventBusHelper;
import com.sunnybear.library.view.image.ImageDraweeView;
import com.sunnybear.library.view.recycler.BasicViewHolder;
import com.sunnybear.library.view.recycler.adapter.LoadMoreAdapter;

import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/1/13.
 */
public class ArticleDetailForActivitiesAdapter extends LoadMoreAdapter<ArticleDetailActs, ArticleDetailForActivitiesAdapter.ArticleDetailActsHolder> {

    public final static String EVENT_REFRESH_HEIGHT = "event_refresh_height";

    public ArticleDetailForActivitiesAdapter(Context context, List<ArticleDetailActs> articleDetailActs) {
        super(context, articleDetailActs);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.activity_article_activities_item;
    }

    @Override
    public ArticleDetailForActivitiesAdapter.ArticleDetailActsHolder getViewHolder(View itemView, int viewType) {
        return new ArticleDetailActsHolder(itemView);
    }

    @Override
    public void onBindItem(final ArticleDetailForActivitiesAdapter.ArticleDetailActsHolder holder, final ArticleDetailActs companion, final int position) {

        refreshHeight(holder.ll_main, companion, position);

    }

    /**
     * 计算高度
     *
     * @param ll_main
     * @param companion
     * @param position
     */
    private void refreshHeight(final LinearLayout ll_main, final ArticleDetailActs companion, final int position) {
        final ViewTreeObserver viewTreeObserver = ll_main.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                final ViewGroup.LayoutParams layoutParams = ll_main.getLayoutParams();
                ll_main.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                companion.setHeight(ll_main.getHeight());
                ll_main.setLayoutParams(layoutParams);
                if (position == getItemCount() - 2)
                    EventBusHelper.post(EVENT_REFRESH_HEIGHT, EVENT_REFRESH_HEIGHT);
            }
        });
    }

    static class ArticleDetailActsHolder extends BasicViewHolder {
        /*@Bind(R.id.iv_pic)
        ImageDraweeView iv_pic;*/
        @Bind(R.id.tv_username)
        TextView tv_username;
        @Bind(R.id.iv_comment_icon)
        ImageDraweeView iv_comment_icon;
        @Bind(R.id.tv_comment_time)
        TextView tv_comment_time;
        @Bind(R.id.ll_main)
        LinearLayout ll_main;

        public ArticleDetailActsHolder(View itemView) {
            super(itemView);
        }
    }
}

