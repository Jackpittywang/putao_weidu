package com.putao.wd.pt_companion.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.model.ArticleDetailActs;
import com.putao.wd.model.GameList;
import com.sunnybear.library.controller.eventbus.EventBusHelper;
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
        final ViewTreeObserver viewTreeObserver = holder.ll_main.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                final ViewGroup.LayoutParams layoutParams = holder.ll_main.getLayoutParams();
                holder.ll_main.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                companion.setHeight(holder.ll_main.getHeight());
                holder.ll_main.setLayoutParams(layoutParams);
                if (position == getItemCount() - 2)
                    EventBusHelper.post(EVENT_REFRESH_HEIGHT, EVENT_REFRESH_HEIGHT);
            }
        });
    }

    static class ArticleDetailActsHolder extends BasicViewHolder {
        @Bind(R.id.tv_title)
        TextView tv_title;
        @Bind(R.id.ll_main)
        LinearLayout ll_main;
        /*@Bind(R.id.tv_intro)
        TextView tv_intro;
        @Bind(R.id.rl_game_step)
        RelativeLayout rl_game_step;
        @Bind(R.id.tv_game_step)
        TextView tv_game_step;*/

        public ArticleDetailActsHolder(View itemView) {
            super(itemView);
        }
    }
}

