package com.putao.wd.pt_discovery.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.model.DisCovery;
import com.putao.wd.model.DiscoveryTag;
import com.putao.wd.model.Resources;
import com.sunnybear.library.view.image.ImageDraweeView;
import com.sunnybear.library.view.recycler.BasicViewHolder;
import com.sunnybear.library.view.recycler.adapter.LoadMoreAdapter;

import java.util.List;

import butterknife.Bind;

/**
 * 发现中活动类适配器
 * Created by Administrator on 2016/5/5.
 */
public class CampaignAdapter extends LoadMoreAdapter<Resources, CampaignAdapter.CampaignViewHolder> {
    private String mainTitle;
    private List<DiscoveryTag> listTagDatas;
    public CampaignAdapter(Context context, List<Resources> resources) {
        super(context, resources);
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
    public void onBindItem(CampaignViewHolder holder, Resources resources, int position) {
        holder.tv_campign.setText(resources.getTitle());
        holder.iv_campign.setImageURL(resources.getIcon());
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
    public void setMainTitleNotify(String mainTitle){
        this.mainTitle = mainTitle;
        notifyDataSetChanged();
    }

}
