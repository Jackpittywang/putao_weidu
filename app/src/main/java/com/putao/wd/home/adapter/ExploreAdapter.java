package com.putao.wd.home.adapter;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.model.ExploreProduct;
import com.putao.wd.model.ExploreProductDetail;
import com.putao.wd.model.ExploreProductPlot;
import com.sunnybear.library.eventbus.EventBusHelper;
import com.sunnybear.library.util.DateUtils;
import com.sunnybear.library.util.Logger;
import com.sunnybear.library.view.image.ImageDraweeView;
import com.sunnybear.library.view.recycler.BasicRecyclerView;
import com.sunnybear.library.view.recycler.BasicViewHolder;
import com.sunnybear.library.view.recycler.LoadMoreAdapter;

import java.util.List;

import butterknife.Bind;

/**
 * 探索号适配器
 * Created by yanghx on 2015/12/9.
 */
public class ExploreAdapter extends LoadMoreAdapter<ExploreProduct, BasicViewHolder> {
    public static final String EVENT_DISPLAY = "display";

    private static final int TYPE_DETAIL = 1;
    private static final int TYPE_PLOT = 2;

    private final int TYPE_PICTURE_ONE = 1;
    private final int TYPE_PICTURE_FOUR = 4;
    private final int TYPE_PICTURE_NINE = 9;
    private final String DATE_PATTERN = "yyyy-MM-dd";
//    private List<SpannableStringBuilder> builders = new ArrayList<>();

    private ExploreDetailAdapter adapter;

    public ExploreAdapter(Context context, List<ExploreProduct> exploreProducts) {
        super(context, exploreProducts);
    }

    @Override
    public int getMultiItemViewType(int position) {
        ExploreProduct exploreProduct = getItem(position);
        switch (exploreProduct.getType()) {
            case 1:
                return TYPE_DETAIL;
            default:
                return TYPE_PLOT;
        }
    }

    @Override
    public int getLayoutId(int viewType) {
        switch (viewType) {
            case TYPE_DETAIL:
                return R.layout.fragment_explore_item;
            case TYPE_PLOT:
                return R.layout.fragment_explore_mixed_item;
            default:
                return 0;
        }
    }

    @Override
    public BasicViewHolder getViewHolder(View itemView, int viewType) {
        switch (viewType) {
            case TYPE_DETAIL:
                return new ExploerViewHolder(itemView);
            case TYPE_PLOT:
                return new ExploerMixedViewHolder(itemView);
            default:
                return null;
        }
    }

    @Override
    public void onBindItem(BasicViewHolder holder, final ExploreProduct exploreProduct, int position) {
        Logger.i("ExploreProduct === " + exploreProduct.toString());
        if (holder instanceof ExploerViewHolder) {
            ExploerViewHolder viewHolder = (ExploerViewHolder) holder;
            if (position != 0) {
                doCompare(holder, exploreProduct, position);
            } else {
                viewHolder.ll_explore_top.setVisibility(View.VISIBLE);
                viewHolder.tv_date.setText(exploreProduct.getTime());
                viewHolder.tv_introduct.setText(exploreProduct.getSummary());
                viewHolder.iv_user_icon.setImageURL(exploreProduct.getProduct_icon());
            }
            viewHolder.tv_skill_name.setText(exploreProduct.getProduct_name());
            viewHolder.iv_skill_icon.setImageURL(exploreProduct.getProduct_icon());

//            List<ArrayList<ExploreProductDetail>> lists = ListUtils.group(exploreProduct.getDetails(), 2);
            List<ExploreProductDetail> details = exploreProduct.getDetails();
            if (details.size() % 2 != 0)
                details.add(new ExploreProductDetail());
            adapter = new ExploreDetailAdapter(context, details);
            viewHolder.rv_display_data.setAdapter(adapter);
//            adapter = new ExploreDetailAdapter(context, exploreProduct.getDetails());
//            viewHolder.gv_display_data.setAdapter(adapter);
//            List<ExploreProductDetail> details = exploreProduct.getDetails();
//            if (details.size() == 2)
//                viewHolder.ll_content2.setVisibility(View.GONE);
//            if (null != details && details.size() > 0)
//                for (int i = 0; i < details.size(); i++) {
//                    ExploreProductDetail productDetail = details.get(i);
//                    builders = HtmlUtils.getTexts(replaceHtml(productDetail.getData(), productDetail.getHtml()));
//                    setContent(viewHolder, i, builders.size());
//                }

            viewHolder.tv_count_comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBusHelper.post(exploreProduct, EVENT_DISPLAY);
                }
            });
        } else if (holder instanceof ExploerMixedViewHolder) {
            ExploerMixedViewHolder viewHolder = (ExploerMixedViewHolder) holder;
            if (position != 0) {
                doCompare(holder, exploreProduct, position);
            } else {
                viewHolder.ll_explore_top.setVisibility(View.VISIBLE);
                viewHolder.tv_date.setText(exploreProduct.getTime());
            }
            viewHolder.tv_skill_name.setText(exploreProduct.getProduct_name());
            viewHolder.iv_skill_icon.setImageURL(exploreProduct.getProduct_icon());
            ExploreProductPlot plot = exploreProduct.getPlot();
            if (plot != null) {
                viewHolder.tv_content.setText(plot.getContent());
                setPlotImage(viewHolder, plot);
                showPiture(viewHolder, 1);
            }
            viewHolder.tv_count_comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBusHelper.post(EVENT_DISPLAY, EVENT_DISPLAY);
                }
            });
        }
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
     * 设置显示内容
     */
    private void setContent(ExploerViewHolder viewHolder, int index, int builderSize) {
//        switch (index) {
//            case 0:
//                viewHolder.tv_content_head1.setText(builders.get(0));
//                viewHolder.tv_content_center1.setText(builders.get(1));
//                if (builderSize == 2)
//                    viewHolder.tv_content_footer1.setVisibility(View.GONE);
//                else
//                    viewHolder.tv_content_footer1.setText(builders.get(2));
//                break;
//            case 1:
//                viewHolder.tv_content_head2.setText(builders.get(0));
//                viewHolder.tv_content_center2.setText(builders.get(1));
//                if (builderSize == 2)
//                    viewHolder.tv_content_footer2.setVisibility(View.GONE);
//                else
//                    viewHolder.tv_content_footer2.setText(builders.get(2));
//                break;
//            case 2:
//                viewHolder.tv_content_head3.setText(builders.get(0));
//                viewHolder.tv_content_center3.setText(builders.get(1));
//                if (builderSize == 2)
//                    viewHolder.tv_content_footer3.setVisibility(View.GONE);
//                else
//                    viewHolder.tv_content_footer3.setText(builders.get(2));
//                break;
//            case 3:
//                viewHolder.tv_content_head4.setText(builders.get(0));
//                viewHolder.tv_content_center4.setText(builders.get(1));
//                if (builderSize == 2)
//                    viewHolder.tv_content_footer4.setVisibility(View.GONE);
//                else
//                    viewHolder.tv_content_footer4.setText(builders.get(2));
//                break;
//        }
    }

    /**
     * 日期比较,控制头部显示
     */
    private void doCompare(BasicViewHolder holder, ExploreProduct exploreProduct, int position) {
        ExploreProduct preExplore = getItem(position - 1);
        int abs = DateUtils.getDaysUnAbs(preExplore.getTime(), exploreProduct.getTime());
        if (abs == 0) {
            if (holder instanceof ExploerViewHolder) {
                ((ExploerViewHolder) holder).ll_explore_top.setVisibility(View.GONE);
            } else if (holder instanceof ExploerMixedViewHolder) {
                ((ExploerMixedViewHolder) holder).ll_explore_top.setVisibility(View.GONE);
            }
        } else {
            if (holder instanceof ExploerViewHolder) {
                ExploerViewHolder viewHolder = (ExploerViewHolder) holder;
                viewHolder.ll_explore_top.setVisibility(View.VISIBLE);
                viewHolder.tv_date.setText(exploreProduct.getTime());
                viewHolder.tv_introduct.setText(exploreProduct.getSummary());
                viewHolder.iv_user_icon.setImageURL(exploreProduct.getProduct_icon());
            } else if (holder instanceof ExploerMixedViewHolder) {
                ExploerMixedViewHolder viewHolder = (ExploerMixedViewHolder) holder;
                viewHolder.ll_explore_top.setVisibility(View.VISIBLE);
                viewHolder.tv_date.setText(exploreProduct.getTime());
                viewHolder.tv_introduct.setText(exploreProduct.getSummary());
                viewHolder.iv_user_icon.setImageURL(exploreProduct.getProduct_icon());
            }
        }
    }

    /**
     * 设置plot中教育理念下面的显示图片
     */
    private void setPlotImage(ExploerMixedViewHolder viewHolder, ExploreProductPlot plot) {
        if (plot.getImg_list() == null) {
            viewHolder.iv_one_picture.setImageURL(plot.getImg_url());
        } else {
            viewHolder.iv_one_picture.setImageURL(plot.getImg_list());
        }
    }

    /**
     * 根据图片张数显示图片
     *
     * @param holder     ExploerMixedViewHolder类型ViewHolder
     * @param pictureNum 图片张数
     */
    private void showPiture(ExploerMixedViewHolder holder, int pictureNum) {
        switch (pictureNum) {
            case TYPE_PICTURE_ONE:
                holder.ll_picture78.setVisibility(View.GONE);
                holder.ll_picture369.setVisibility(View.INVISIBLE);
                holder.iv_picture9.setVisibility(View.GONE);
                holder.iv_one_picture.setVisibility(View.VISIBLE);
                holder.ll_picture1245.setVisibility(View.INVISIBLE);
                break;
            case TYPE_PICTURE_FOUR:
                holder.iv_one_picture.setVisibility(View.GONE);
                holder.ll_picture1245.setVisibility(View.VISIBLE);
                holder.ll_picture78.setVisibility(View.GONE);
                holder.ll_picture369.setVisibility(View.INVISIBLE);
                holder.iv_picture9.setVisibility(View.GONE);
                break;
            case TYPE_PICTURE_NINE:
                holder.iv_one_picture.setVisibility(View.GONE);
                holder.ll_picture78.setVisibility(View.VISIBLE);
                holder.ll_picture369.setVisibility(View.VISIBLE);
                holder.ll_picture1245.setVisibility(View.VISIBLE);
                holder.iv_picture9.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }

    /**
     * detail
     */
    static class ExploerViewHolder extends BasicViewHolder {
        @Bind(R.id.ll_explore_top)
        LinearLayout ll_explore_top;
        @Bind(R.id.tv_date)
        TextView tv_date;
        @Bind(R.id.iv_user_icon)
        ImageDraweeView iv_user_icon;
        @Bind(R.id.tv_introduct)
        TextView tv_introduct;
        @Bind(R.id.iv_skill_icon)
        ImageDraweeView iv_skill_icon;
        @Bind(R.id.tv_skill_name)
        TextView tv_skill_name;
        @Bind(R.id.rv_display_data)
        BasicRecyclerView rv_display_data;
        //        @Bind(R.id.ll_content1)
//        LinearLayout ll_content1;
//        @Bind(R.id.ll_content2)
//        LinearLayout ll_content2;
//        @Bind(R.id.tv_content_head1)
//        TextView tv_content_head1;
//        @Bind(R.id.tv_content_center1)
//        TextView tv_content_center1;
//        @Bind(R.id.tv_content_footer1)
//        TextView tv_content_footer1;
//        @Bind(R.id.tv_content_head2)
//        TextView tv_content_head2;
//        @Bind(R.id.tv_content_center2)
//        TextView tv_content_center2;
//        @Bind(R.id.tv_content_footer2)
//        TextView tv_content_footer2;
//        @Bind(R.id.tv_content_head3)
//        TextView tv_content_head3;
//        @Bind(R.id.tv_content_center3)
//        TextView tv_content_center3;
//        @Bind(R.id.tv_content_footer3)
//        TextView tv_content_footer3;
//        @Bind(R.id.tv_content_head4)
//        TextView tv_content_head4;
//        @Bind(R.id.tv_content_center4)
//        TextView tv_content_center4;
//        @Bind(R.id.tv_content_footer4)
//        TextView tv_content_footer4;
        @Bind(R.id.tv_count_comment)
        TextView tv_count_comment;

        public ExploerViewHolder(View itemView) {
            super(itemView);
        }
    }

    /**
     * plot
     */
    static class ExploerMixedViewHolder extends BasicViewHolder {
        @Bind(R.id.ll_explore_top)
        LinearLayout ll_explore_top;
        @Bind(R.id.tv_date)
        TextView tv_date;
        @Bind(R.id.iv_user_icon)
        ImageDraweeView iv_user_icon;
        @Bind(R.id.tv_introduct)
        TextView tv_introduct;
        @Bind(R.id.iv_skill_icon)
        ImageDraweeView iv_skill_icon;
        @Bind(R.id.iv_picture1)
        ImageDraweeView iv_picture1;
        @Bind(R.id.iv_picture2)
        ImageDraweeView iv_picture2;
        @Bind(R.id.iv_picture3)
        ImageDraweeView iv_picture3;
        @Bind(R.id.iv_picture4)
        ImageDraweeView iv_picture4;
        @Bind(R.id.iv_picture5)
        ImageDraweeView iv_picture5;
        @Bind(R.id.iv_picture6)
        ImageDraweeView iv_picture6;
        @Bind(R.id.iv_picture7)
        ImageDraweeView iv_picture7;
        @Bind(R.id.iv_picture8)
        ImageDraweeView iv_picture8;
        @Bind(R.id.iv_picture9)
        ImageDraweeView iv_picture9;
        @Bind(R.id.iv_one_picture)
        ImageDraweeView iv_one_picture;//第10张图特殊处理
        @Bind(R.id.tv_skill_name)
        TextView tv_skill_name;
        @Bind(R.id.tv_content)
        TextView tv_content;
        @Bind(R.id.ll_picture1245)
        LinearLayout ll_picture1245;
        @Bind(R.id.ll_picture78)
        LinearLayout ll_picture78;
        @Bind(R.id.ll_picture369)
        LinearLayout ll_picture369;
        @Bind(R.id.tv_count_comment)
        TextView tv_count_comment;

        public ExploerMixedViewHolder(View itemView) {
            super(itemView);
        }
    }
}





