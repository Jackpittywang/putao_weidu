package com.putao.wd.pt_companion;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.putao.wd.R;
import com.putao.wd.model.GameList;
import com.putao.wd.pt_companion.adapter.GameStepListAdapter;
import com.sunnybear.library.controller.BasicFragmentActivity;
import com.sunnybear.library.controller.eventbus.Subcriber;
import com.sunnybear.library.view.recycler.BasicRecyclerView;
import com.sunnybear.library.view.recycler.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;
import com.sunnybear.library.view.recycler.stickyheadersrecyclerview.StickyRecyclerHeadersTouchListener;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 游戏步骤选择页
 * Created by zhanghao on 2016/04/05.
 */
@Deprecated
public class GameStepListActivity extends BasicFragmentActivity implements View.OnClickListener {
    private GameStepListAdapter mGameStepListAdapter;
    @Bind(R.id.rv_content)
    BasicRecyclerView rv_content;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_game_step_list;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {
        ArrayList<GameList> gameLists = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            gameLists.add(new GameList());
        }
        mGameStepListAdapter = new GameStepListAdapter(mContext, gameLists);
        rv_content.setAdapter(mGameStepListAdapter);


        // Set layout manager
        int orientation = getLayoutManagerOrientation(getResources().getConfiguration().orientation);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this, orientation, false);
        rv_content.setLayoutManager(layoutManager);

        // Add the sticky headers decoration
        final StickyRecyclerHeadersDecoration headersDecor = new StickyRecyclerHeadersDecoration(mGameStepListAdapter);
        rv_content.addItemDecoration(headersDecor);

        // Add decoration for dividers between list items
        rv_content.addItemDecoration(new DividerDecoration(this));

        // Add touch listeners
        StickyRecyclerHeadersTouchListener touchListener =
                new StickyRecyclerHeadersTouchListener(rv_content, headersDecor);
        touchListener.setOnHeaderClickListener(
                new StickyRecyclerHeadersTouchListener.OnHeaderClickListener() {
                    @Override
                    public void onHeaderClick(View header, int position, long headerId) {
                        Toast.makeText(mContext, "Header position: " + position + ", id: " + headerId,
                                Toast.LENGTH_SHORT).show();
                    }
                });
        rv_content.addOnItemTouchListener(touchListener);
        rv_content.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                startActivity(GameStepDetailActivity.class);
//                mGameStepListAdapter.notifyItemRemoved(position);
//                mGameStepListAdapter.remove(mGameStepListAdapter.getItem(position));
            }
        }));
        mGameStepListAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                headersDecor.invalidateHeaders();
            }
        });

    }

    private int getLayoutManagerOrientation(int activityOrientation) {
        if (activityOrientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            return LinearLayoutManager.VERTICAL;
        } else {
            return LinearLayoutManager.HORIZONTAL;
        }
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @Subcriber(tag = GameStepListAdapter.EVENT_START_TO_DETAIL)
    private void startDetail(String str) {
        startActivity(GameStepDetailActivity.class);
    }
    @OnClick(R.id.iv_back)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }

    static class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {
        private OnItemClickListener mListener;

        public interface OnItemClickListener {
            void onItemClick(View view, int position);
        }

        GestureDetector mGestureDetector;

        public RecyclerItemClickListener(Context context, OnItemClickListener listener) {
            mListener = listener;
            mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
            View childView = view.findChildViewUnder(e.getX(), e.getY());
            if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
                mListener.onItemClick(childView, view.getChildAdapterPosition(childView));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView view, MotionEvent motionEvent) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
            // do nothing
        }
    }

}


