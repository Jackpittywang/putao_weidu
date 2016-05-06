package com.putao.wd.pt_discovery.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.model.DisCovery;
import com.sunnybear.library.view.image.ImageDraweeView;
import com.sunnybear.library.view.recycler.BasicViewHolder;
import com.sunnybear.library.view.recycler.adapter.LoadMoreAdapter;

import java.util.List;

import butterknife.Bind;

/**
 * 发现中活动类适配器
 * Created by Administrator on 2016/5/5.
 */
public class CampaignAdapter extends LoadMoreAdapter<DisCovery, CampaignAdapter.CampaignViewHolder> {

    public CampaignAdapter(Context context, List<DisCovery> disCoveries) {
        super(context, disCoveries);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.activity_campign_special_item;
    }

    @Override
    public CampaignViewHolder getViewHolder(View itemView, int viewType) {
        return new CampaignViewHolder(itemView);
    }

    @Override
    public void onBindItem(CampaignViewHolder holder, DisCovery disCovery, int position) {
        holder.tv_campign.setText("活动活动活动活活动活动活动活动活动活动活动活动活动活动活动活动动活动活动活动活动" + position);
    }

    static class CampaignViewHolder extends BasicViewHolder {
        @Bind(R.id.iv_icon)
        ImageDraweeView iv_campign;
        @Bind(R.id.tv_title)
        TextView tv_campign;

        public CampaignViewHolder(View itemView) {
            super(itemView);
        }
    }
}
