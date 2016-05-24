package com.putao.wd.pt_companion.adapter;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;

import com.putao.wd.R;
import com.sunnybear.library.util.ImageUtils;
import com.sunnybear.library.util.StringUtils;
import com.sunnybear.library.view.image.ImageDraweeView;
import com.sunnybear.library.view.recycler.BasicViewHolder;
import com.sunnybear.library.view.recycler.adapter.BasicAdapter;

import java.text.ParseException;
import java.util.List;

import butterknife.Bind;

/**
 * Created by riven_chris on 16/5/24.
 * 评论图片
 */
public class ArticleCommentImagesAdapter extends BasicAdapter<String, ArticleCommentImagesAdapter.CommentImagesViewHolder> {

    public ArticleCommentImagesAdapter(Context context, List<String> strings) {
        super(context, strings);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.layout_article_comment_images_item;
    }

    @Override
    public CommentImagesViewHolder getViewHolder(View itemView, int viewType) {
        return new CommentImagesViewHolder(itemView);
    }

    @Override
    public void onBindItem(CommentImagesViewHolder holder, String s, int position) throws ParseException {
        String pic = ImageUtils.getImageSizeUrl(s, ImageUtils.ImageSizeURL.SIZE_240x240);
        if (ImageUtils.isImage(pic) && !StringUtils.isEmpty(pic)) {
            holder.iv_comment_pic.resize(240, 240);
            holder.iv_comment_pic.setImageURL(pic);
        } else {
            holder.iv_comment_pic.setImageURI(null);
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size() > 9 ? 9 : mItems.size();
    }

    public static class CommentImagesViewHolder extends BasicViewHolder {
        @Bind(R.id.iv_comment_pic)
        ImageDraweeView iv_comment_pic;

        public CommentImagesViewHolder(View itemView) {
            super(itemView);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) iv_comment_pic.getLayoutParams();
            params.height = 260;
            iv_comment_pic.setLayoutParams(params);
            iv_comment_pic.requestLayout();
        }
    }
}
