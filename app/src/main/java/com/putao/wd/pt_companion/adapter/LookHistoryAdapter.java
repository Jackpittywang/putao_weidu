package com.putao.wd.pt_companion.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.model.ServiceMessageContent;
import com.putao.wd.model.ServiceMessageList;
import com.putao.wd.model.ServiceType;
import com.sunnybear.library.util.DateUtils;
import com.sunnybear.library.view.image.ImageDraweeView;
import com.sunnybear.library.view.recycler.BasicViewHolder;
import com.sunnybear.library.view.recycler.adapter.LoadMoreAdapter;

import java.util.List;

import butterknife.Bind;

/**
 * 查看历史文章适配器
 * Created by Administrator on 2016/4/18.
 */
public class LookHistoryAdapter extends LoadMoreAdapter<ServiceMessageList, LookHistoryAdapter.LookViewHolder> {


    public LookHistoryAdapter(Context context, List<ServiceMessageList> serviceMessageLists) {
        super(context, serviceMessageLists);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.activity_game_detail_list_item;
    }

    @Override
    public LookViewHolder getViewHolder(View itemView, int viewType) {
        return new LookViewHolder(itemView);
    }

    @Override
    public void onBindItem(LookViewHolder holder, ServiceMessageList serviceMessageList, int position) {
        switch (serviceMessageList.getType()) {
            case "article":
                ServiceMessageContent serviceMessageContent = serviceMessageList.getContent_lists().get(0);
                holder.iv_sign.setImageURL(serviceMessageContent.getCover_pic());
                holder.tv_title.setText(serviceMessageContent.getTitle());
                holder.tv_content.setText(serviceMessageContent.getSub_title());
                break;
            case "text"://文字
                holder.tv_title.setText(serviceMessageList.getMessage());
                holder.tv_content.setVisibility(View.GONE);
                break;
            case "image"://图片
                ServiceType image = serviceMessageList.getImage();
                holder.iv_sign.setImageURL(image.getPic());
                holder.tv_title.setVisibility(View.GONE);
                holder.tv_content.setVisibility(View.GONE);
                break;
            case "reply"://回復
                ServiceType reply = serviceMessageList.getReply();
                holder.tv_title.setText(reply.getQuestion());
                holder.tv_content.setText(reply.getAnswer());
                break;
        }
        String date = "";
        date = DateUtils.timeCalculate(serviceMessageList.getRelease_time());
        holder.tv_time.setVisibility(View.VISIBLE);
        holder.tv_time.setText("───  " + date + "  ───");
    }

    static class LookViewHolder extends BasicViewHolder {
        @Bind(R.id.iv_sign)
        ImageDraweeView iv_sign;
        @Bind(R.id.tv_title)
        TextView tv_title;
        @Bind(R.id.tv_content)
        TextView tv_content;
        @Bind(R.id.tv_time)
        TextView tv_time;

        public LookViewHolder(View itemView) {
            super(itemView);
        }
    }
}
