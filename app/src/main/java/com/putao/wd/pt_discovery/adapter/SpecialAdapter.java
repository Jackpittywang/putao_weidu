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
 * Created by Administrator on 2016/5/5.
 */
public class SpecialAdapter extends LoadMoreAdapter<DisCovery, SpecialAdapter.SpecialViewHolder> {
    public SpecialAdapter(Context context, List<DisCovery> disCoveries) {
        super(context, disCoveries);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.activity_campign_special_item;
    }

    @Override
    public SpecialViewHolder getViewHolder(View itemView, int viewType) {
        return new SpecialViewHolder(itemView);
    }

    @Override
    public void onBindItem(SpecialViewHolder holder, DisCovery disCovery, int position) {
        holder.tv_special.setText("专题专题专题专题专题专题专题专题专题专题专题专题专题专题专题专题专题专题专题专题" + position);
    }

    static class SpecialViewHolder extends BasicViewHolder {
        @Bind(R.id.iv_icon)
        ImageDraweeView iv_campign;
        @Bind(R.id.tv_title)
        TextView tv_special;

        public SpecialViewHolder(View itemView) {
            super(itemView);
        }
    }
}
