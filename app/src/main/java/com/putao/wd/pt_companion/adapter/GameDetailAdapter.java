package com.putao.wd.pt_companion.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.model.ServiceMessageContent;
import com.putao.wd.model.ServiceMessageList;
import com.sunnybear.library.util.DateUtils;
import com.sunnybear.library.view.image.ImageDraweeView;
import com.sunnybear.library.view.recycler.BasicViewHolder;
import com.sunnybear.library.view.recycler.adapter.BasicAdapter;
import com.sunnybear.library.view.recycler.adapter.LoadMoreAdapter;

import java.text.ParseException;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/1/13.
 */
public class GameDetailAdapter extends BasicAdapter<ServiceMessageList, GameDetailAdapter.GameDetailHolder> {


    public GameDetailAdapter(Context context, List<ServiceMessageList> serviceMessageList) {
        super(context, serviceMessageList);
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
    public void onBindItem(final GameDetailAdapter.GameDetailHolder holder, final ServiceMessageList serviceMessageList, final int position) {
        ServiceMessageContent serviceMessageContent = serviceMessageList.getContent_lists().get(0);
        holder.iv_sign.setImageURL(serviceMessageContent.getCover_pic());
        holder.tv_title.setText(serviceMessageContent.getTitle());
        holder.tv_content.setText(serviceMessageContent.getSub_title());
        String date = "";
        date = DateUtils.timeCalculate(serviceMessageList.getRelease_time());
        holder.tv_time.setVisibility(View.VISIBLE);
        holder.tv_time.setText("───    " + date + "    ───");
        /*if (serviceMessageList.isShowData()) {
        } else
            holder.tv_time.setVisibility(View.GONE);*/
    }

    static class GameDetailHolder extends BasicViewHolder {
        @Bind(R.id.iv_sign)
        ImageDraweeView iv_sign;
        @Bind(R.id.tv_title)
        TextView tv_title;
        @Bind(R.id.tv_content)
        TextView tv_content;
        @Bind(R.id.tv_time)
        TextView tv_time;

        public GameDetailHolder(View itemView) {
            super(itemView);
        }
    }
}

