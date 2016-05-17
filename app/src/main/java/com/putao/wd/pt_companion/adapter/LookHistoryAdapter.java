package com.putao.wd.pt_companion.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.putao.wd.R;
import com.putao.wd.account.AccountHelper;
import com.putao.wd.model.ServiceMessageContent;
import com.putao.wd.model.ServiceMessageList;
import com.putao.wd.model.ServiceMessageListImage;
import com.putao.wd.model.ServiceMessageListReply;
import com.putao.wd.model.ServiceType;
import com.sunnybear.library.util.DateUtils;
import com.sunnybear.library.util.ImageUtils;
import com.sunnybear.library.util.StringUtils;
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
        return R.layout.activity_game_detail_list_article_item;
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
                holder.iv_sign.setImageURL(ImageUtils.getImageSizeUrl(serviceMessageContent.getCover_pic(), ImageUtils.ImageSizeURL.SIZE_360x360));
                holder.tv_title.setText(serviceMessageContent.getTitle());
                holder.tv_content.setText(serviceMessageContent.getSub_title());
                holder.iv_sign.setVisibility(View.VISIBLE);
                holder.tv_title.setVisibility(View.VISIBLE);
                holder.tv_content.setVisibility(View.VISIBLE);
                break;
            case "text"://文字
                holder.tv_title.setText(serviceMessageList.getMessage());
                holder.tv_content.setVisibility(View.GONE);
                holder.iv_sign.setVisibility(View.GONE);
                break;
            case "image"://图片
                ServiceMessageListImage image = serviceMessageList.getImage();
                String thumb = ImageUtils.getImageSizeUrl(image.getThumb(), ImageUtils.ImageSizeURL.SIZE_360x360);
                if (StringUtils.isEmpty(thumb)) {
                    holder.iv_sign.setImageURL(ImageUtils.getImageSizeUrl(image.getPic(), ImageUtils.ImageSizeURL.SIZE_360x360));
                } else {
                    holder.iv_sign.setImageURL(thumb);
                }
                holder.tv_title.setVisibility(View.GONE);
                holder.tv_content.setVisibility(View.GONE);
                break;
            case "reply"://回復
                ServiceMessageListReply reply = serviceMessageList.getReply();
                holder.tv_title.setText(reply.getQuestion());
                holder.tv_content.setText(reply.getAnswer());
                holder.iv_sign.setVisibility(View.GONE);
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
