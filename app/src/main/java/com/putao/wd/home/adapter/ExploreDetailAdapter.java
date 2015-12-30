package com.putao.wd.home.adapter;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.model.ExploreProductDetail;
import com.putao.wd.util.HtmlUtils;
import com.sunnybear.library.view.recycler.BasicAdapter;
import com.sunnybear.library.view.recycler.BasicViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 探索号炫耀详情
 * Created by guchenkai on 2015/12/30.
 */
public class ExploreDetailAdapter extends BasicAdapter<ExploreProductDetail, ExploreDetailAdapter.ExploreDetailViewHolder> {
    private List<SpannableStringBuilder> builders;

    public ExploreDetailAdapter(Context context, List<ExploreProductDetail> details) {
        super(context, details);
        builders = new ArrayList<>();
    }

    @Override
    public int getLayoutId(int viewpe) {
        return R.layout.fragment_explore_detail_item;
    }

    @Override
    public ExploreDetailViewHolder getViewHolder(View itemView, int viewType) {
        return new ExploreDetailViewHolder(itemView);
    }

    @Override
    public void onBindItem(ExploreDetailViewHolder holder, ExploreProductDetail detail, int position) {
        if (detail.getData() == null && detail.getHtml() == null) return;
        List<SpannableStringBuilder> builders = HtmlUtils.getTexts(replaceHtml(detail.getData(), detail.getHtml()));
        holder.tv_1.setText(builders.get(0));
        holder.tv_2.setText(builders.get(1));
        holder.tv_3.setText(builders.get(2));
    }

    /**
     * 替换html中的<param>标签
     *
     * @param data
     * @param html
     * @return
     */
    private String replaceHtml(List<String> data, String html) {
        boolean hasNull = false;
        for (String str : data) {
            if (str == null)
                hasNull = true;
        }
        if (hasNull)
            for (int i = 0; i < data.size(); i++) {
                if (i < data.size() - 1)
                    html = html.replace("<param" + i + ">", data.get(i + 1));
            }
        else
            for (int i = 0; i < data.size(); i++) {
                html = html.replace("<param" + i + ">", data.get(i));
            }
        return html;
    }

    /**
     *
     */
    static class ExploreDetailViewHolder extends BasicViewHolder {
        @Bind(R.id.tv_1)
        TextView tv_1;
        @Bind(R.id.tv_2)
        TextView tv_2;
        @Bind(R.id.tv_3)
        TextView tv_3;

        public ExploreDetailViewHolder(View itemView) {
            super(itemView);
            itemView.setBackgroundResource(R.color.color_EBEBEB);
        }
    }
}
