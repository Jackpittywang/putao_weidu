package com.putao.wd.pt_companion.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.model.GameList;
import com.sunnybear.library.view.recycler.BasicRecyclerView;
import com.sunnybear.library.view.recycler.BasicViewHolder;
import com.sunnybear.library.view.recycler.adapter.BasicAdapter;
import com.sunnybear.library.view.recycler.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;

import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/1/13.
 */
public class GameDetailAdapter extends BasicAdapter<GameList, GameDetailAdapter.GameDetailHolder> {

    public GameDetailAdapter(Context context, List<GameList> gameLists) {
        super(context, gameLists);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.activity_game_step_detail_item;
    }

    @Override
    public GameDetailAdapter.GameDetailHolder getViewHolder(View itemView, int viewType) {
        return new GameDetailHolder(itemView);
    }

    @Override
    public void onBindItem(final GameDetailAdapter.GameDetailHolder holder, final GameList companion, final int position) {

    }

    static class GameDetailHolder extends BasicViewHolder {
        @Bind(R.id.rv_content)
        BasicRecyclerView rv_content;
        @Bind(R.id.tv_intro)
        TextView tv_intro;
        @Bind(R.id.rl_game_step)
        RelativeLayout rl_game_step;
        @Bind(R.id.tv_game_step)
        TextView tv_game_step;

        public GameDetailHolder(View itemView) {
            super(itemView);
        }
    }
}

