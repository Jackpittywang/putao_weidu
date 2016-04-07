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
public class GameStepListAdapter extends BasicAdapter<GameList, GameStepListAdapter.GameStepDetailHolder> implements StickyRecyclerHeadersAdapter<GameStepListAdapter.GameListHolder> {
    private Context mContext;
    private List<GameList> mGameLists;
    public static final String EVENT_START_TO_DETAIL = "event_start_to_detail";

    public GameStepListAdapter(Context context, List<GameList> gameLists) {
        super(context, gameLists);
        mContext = context;
        mGameLists = gameLists;
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.activity_game_step_list_item;
    }

    @Override
    public GameStepListAdapter.GameStepDetailHolder getViewHolder(View itemView, int viewType) {
        return new GameStepDetailHolder(itemView);
    }

    @Override
    public void onBindItem(final GameStepListAdapter.GameStepDetailHolder holder, final GameList companion, final int position) {
//        checkShowChild(holder, companion);
//        holder.rv_content.setAdapter(new GameStepDetailAdapter(mContext, mGameLists));
        /*holder.rv_content.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(Serializable serializable, int position) {
                EventBusHelper.post("", EVENT_START_TO_DETAIL);
            }
        });
        holder.rl_game_step.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                companion.setIsShowChild(!companion.isShowChild());
                checkShowChild(holder, companion);
            }
        });*/
    }

  /*  private void checkShowChild(GameStepListAdapter.GameListHolder holder, GameList companion) {
        if (companion.isShowChild()) {
            holder.tv_intro.setVisibility(View.VISIBLE);
            holder.rv_content.setVisibility(View.VISIBLE);
        } else {
            holder.tv_intro.setVisibility(View.GONE);
            holder.rv_content.setVisibility(View.GONE);
        }
    }*/

    @Override
    public long getHeaderId(int position) {
        return position / 6;
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_game_step_list_head_item, parent, false);
        return new GameStepListAdapter.GameListHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(GameListHolder holder, int position) {
        holder.tv_game_step.setText("第" + position / 6 + "关");
    }

    static class GameListHolder extends BasicViewHolder {
        @Bind(R.id.rv_content)
        BasicRecyclerView rv_content;
        @Bind(R.id.tv_intro)
        TextView tv_intro;
        @Bind(R.id.rl_game_step)
        RelativeLayout rl_game_step;
        @Bind(R.id.tv_game_step)
        TextView tv_game_step;

        public GameListHolder(View itemView) {
            super(itemView);
        }
    }

    static class GameStepDetailHolder extends BasicViewHolder {
        @Bind(R.id.tv_game_step)
        TextView tv_game_step;
        @Bind(R.id.iv_game_step)
        ImageView iv_game_step;

        public GameStepDetailHolder(View itemView) {
            super(itemView);
        }
    }
}
