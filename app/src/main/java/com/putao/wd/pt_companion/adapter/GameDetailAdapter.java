package com.putao.wd.pt_companion.adapter;

import android.content.Context;
import android.view.View;

import com.putao.wd.R;
import com.putao.wd.model.GameList;
import com.sunnybear.library.view.recycler.BasicViewHolder;
import com.sunnybear.library.view.recycler.adapter.LoadMoreAdapter;

import java.util.List;

/**
 * Created by Administrator on 2016/1/13.
 */
public class GameDetailAdapter extends LoadMoreAdapter<GameList, GameDetailAdapter.GameDetailHolder> {

    public GameDetailAdapter(Context context, List<GameList> gameLists) {
        super(context, gameLists);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.activity_game_detail_list_item;
    }

    @Override
    public GameDetailAdapter.GameDetailHolder getViewHolder(View itemView, int viewType) {
        return new GameDetailHolder(itemView);
    }

    @Override
    public void onBindItem(final GameDetailAdapter.GameDetailHolder holder, final GameList companion, final int position) {

    }

    static class GameDetailHolder extends BasicViewHolder {
        /*@Bind(R.id.rv_content)
        BasicRecyclerView rv_content;
        @Bind(R.id.tv_intro)
        TextView tv_intro;
        @Bind(R.id.rl_game_step)
        RelativeLayout rl_game_step;
        @Bind(R.id.tv_game_step)
        TextView tv_game_step;*/

        public GameDetailHolder(View itemView) {
            super(itemView);
        }
    }
}

