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
 * 标签列表适配器
 * Created by Administrator on 2016/5/6.
 */
public class LabelAdapter extends LoadMoreAdapter<DisCovery, LabelAdapter.LabelViewHolder> {

    public LabelAdapter(Context context, List<DisCovery> disCoveries) {
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

    @Override
    public void onBindItem(LabelViewHolder holder, DisCovery disCovery, int position) {
        holder.tv_title.setText("这里显示的是标签列表的标题，限制显示两行");
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
}
