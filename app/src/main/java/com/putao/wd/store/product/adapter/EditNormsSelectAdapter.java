package com.putao.wd.store.product.adapter;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.model.Norms;
import com.sunnybear.library.eventbus.EventBusHelper;
import com.sunnybear.library.util.StringUtils;
import com.sunnybear.library.view.AmountSelectLayout;
import com.sunnybear.library.view.recycler.BasicAdapter;
import com.sunnybear.library.view.recycler.BasicViewHolder;
import com.sunnybear.library.view.select.Tag;
import com.sunnybear.library.view.select.TagBar;

import java.util.List;

import butterknife.Bind;

/**
 * 编辑产品规格选择适配器
 * Created by wangou on 2015/11/30.
 */
public class EditNormsSelectAdapter extends BasicAdapter<Norms, EditNormsSelectAdapter.NormsSelectViewHolder> {
    public static final String EVENT_SEL_TAG = "sel_tag";
    public static final String EVENT_DEFAULT_TAG = "default_tag";

    public EditNormsSelectAdapter(Context context, List<Norms> normses) {
        super(context, normses);
    }


    @Override
    public int getLayoutId(int viewType) {
        return R.layout.popup_shopping_car_item_norms;
    }

    @Override
    public NormsSelectViewHolder getViewHolder(View itemView, int viewType) {
        return new NormsSelectViewHolder(itemView);
    }

    @Override
    public void onBindItem(NormsSelectViewHolder viewHolder, Norms norms, int position) {
            viewHolder.tv_title.setText(norms.getTitle());
            viewHolder.tb_tag.addTags(norms.getTags(), 0);
            EventBusHelper.post(viewHolder.tb_tag.getTag(0), EVENT_DEFAULT_TAG);//传递默认选中值
            viewHolder.tb_tag.setonTagItemCheckListener(new TagBar.OnTagItemCheckListener() {
                @Override
                public void onTagItemCheck(Tag tag, int position) {
                    EventBusHelper.post(tag, EVENT_SEL_TAG);
                }
            });

    }

    /**
     * 规格选择
     */
    static class NormsSelectViewHolder extends BasicViewHolder {
        @Bind(R.id.tv_title)
        TextView tv_title;
        @Bind(R.id.tb_tag)
        TagBar tb_tag;

        public NormsSelectViewHolder(View itemView) {
            super(itemView);
        }
    }

}
