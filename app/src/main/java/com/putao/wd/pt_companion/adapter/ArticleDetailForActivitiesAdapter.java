package com.putao.wd.pt_companion.adapter;

import android.content.Context;
import android.view.View;

import com.putao.wd.R;
import com.putao.wd.model.ArticleDetailActs;
import com.putao.wd.model.GameList;
import com.sunnybear.library.view.recycler.BasicViewHolder;
import com.sunnybear.library.view.recycler.adapter.LoadMoreAdapter;

import java.util.List;

/**
 * Created by Administrator on 2016/1/13.
 */
public class ArticleDetailForActivitiesAdapter extends LoadMoreAdapter<ArticleDetailActs, ArticleDetailForActivitiesAdapter.ArticleDetailActsHolder> {

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

    }

    static class ArticleDetailActsHolder extends BasicViewHolder {
        /*@Bind(R.id.rv_content)
        BasicRecyclerView rv_content;
        @Bind(R.id.tv_intro)
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

