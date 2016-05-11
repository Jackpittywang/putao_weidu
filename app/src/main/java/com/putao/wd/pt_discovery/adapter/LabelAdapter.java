package com.putao.wd.pt_discovery.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.model.DisCovery;
import com.putao.wd.model.DiscoveryTag;
import com.putao.wd.model.ResourceBannerAndTag;
import com.putao.wd.model.Resources;
import com.sunnybear.library.util.StringUtils;
import com.sunnybear.library.view.image.ImageDraweeView;
import com.sunnybear.library.view.recycler.BasicViewHolder;
import com.sunnybear.library.view.recycler.adapter.LoadMoreAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 标签列表适配器
 * Created by Administrator on 2016/5/6.
 */
public class LabelAdapter extends LoadMoreAdapter<Resources, LabelAdapter.LabelViewHolder> {
    private String mainTitle;
    private List<DiscoveryTag> listTagDatas;

    public LabelAdapter(Context context, List<Resources> disCoveries) {
        super(context, disCoveries);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.activity_discovery_label_special_item;
    }

    @Override
    public LabelViewHolder getViewHolder(View itemView, int viewType) {
        return new LabelViewHolder(itemView);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindItem(LabelViewHolder holder, Resources disCovery, int position) {
        holder.tv_title.setText(disCovery.getTitle());
        holder.iv_icon.setImageURL(disCovery.getIcon());
        String[] tags = new String[]{};
        if (disCovery.getTag() != null) {
            tags = disCovery.getTag().split("#");
        }

        if(listTagDatas == null)
            listTagDatas = new ArrayList<>();

        listTagDatas.clear();
        for (int i = 1; i < tags.length; i++) {
            DiscoveryTag discoveryTag = new DiscoveryTag();
            discoveryTag.setTitle(tags[i]);
            if (StringUtils.equals(tags[i], mainTitle))
                discoveryTag.setIsEqualsTag(true);
            else
                discoveryTag.setIsEqualsTag(false);
            listTagDatas.add(discoveryTag);
        }


        switch (listTagDatas.size()) {
            case 0:
                holder.tv_labelOne.setVisibility(View.GONE);
                holder.tv_labelSecond.setVisibility(View.GONE);
                holder.tv_labelThird.setVisibility(View.GONE);
                break;
            case 1:
                holder.tv_labelOne.setVisibility(View.VISIBLE);
                holder.tv_labelSecond.setVisibility(View.GONE);
                holder.tv_labelThird.setVisibility(View.GONE);
                setTextColorGround(holder.tv_labelOne, 0);
                break;
            case 2:
                holder.tv_labelOne.setVisibility(View.VISIBLE);
                holder.tv_labelSecond.setVisibility(View.VISIBLE);
                holder.tv_labelThird.setVisibility(View.GONE);
                setTextColorGround(holder.tv_labelOne,0);
                setTextColorGround(holder.tv_labelSecond,1);
                break;
            case 3:
                holder.tv_labelOne.setVisibility(View.VISIBLE);
                holder.tv_labelSecond.setVisibility(View.VISIBLE);
                holder.tv_labelThird.setVisibility(View.VISIBLE);
                setTextColorGround(holder.tv_labelOne,0);
                setTextColorGround(holder.tv_labelSecond,1);
                setTextColorGround(holder.tv_labelThird,2);
                break;
        }

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void setTextColorGround(TextView holderTextView,int position){
        holderTextView.setText(listTagDatas.get(position).getTitle());
        holderTextView.setTextColor(listTagDatas.get(position).isEqualsTag() ? context.getResources().getColor(R.color.colorAccent) : context.getResources().getColor(R.color.color_C2C2C2));
        holderTextView.setBackground(listTagDatas.get(position).isEqualsTag() ? context.getResources().getDrawable(R.drawable.tv_get_label_shape) : context.getResources().getDrawable(R.drawable.comment_shape));
    }

    static class LabelViewHolder extends BasicViewHolder {
        @Bind(R.id.tv_title)
        TextView tv_title;
        @Bind(R.id.tv_labelOne)
        TextView tv_labelOne;
        @Bind(R.id.tv_labelSecond)
        TextView tv_labelSecond;
        @Bind(R.id.tv_labelThird)
        TextView tv_labelThird;
        @Bind(R.id.iv_icon)
        ImageDraweeView iv_icon;

        public LabelViewHolder(View itemView) {
            super(itemView);
        }
    }

    public void setMainTitleNotify(String mainTitle){
            this.mainTitle = mainTitle;
            notifyDataSetChanged();
    }
}
