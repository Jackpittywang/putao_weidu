package com.sunnybear.library.view.select;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.SparseIntArray;
import android.view.View;
import android.view.View.OnClickListener;

import com.sunnybear.library.R;
import com.sunnybear.library.util.Logger;

import java.util.ArrayList;
import java.util.List;


public class TagBar extends FlowLayout implements OnClickListener {
    private List<Tag> mTags = new ArrayList<>();

    private Drawable mTagItemBackgroundSel;
    private Drawable mTagItemBackgroundNor;
    private int mTextSelColor;
    private int mTextNorColor;
    private int mTextDisColor;//不可选择的文字颜色

    private TagItem mLastCheckItem = null;
    private SparseIntArray array;
    private TagItemCheckListener mTagItemCheckListener;

    public void setTagItemCheckListener(TagItemCheckListener tagItemCheckListener) {
        this.mTagItemCheckListener = tagItemCheckListener;
    }

    public TagBar(Context context) {
        this(context, null, 0);
    }

    public TagBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TagBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initStyleable(context, attrs);
        array = new SparseIntArray();
    }

    private void initStyleable(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TagBar);
        mTagItemBackgroundSel = array.getDrawable(R.styleable.TagBar_tag_background_sel);
        mTagItemBackgroundNor = array.getDrawable(R.styleable.TagBar_tag_background_nor);
        mTextSelColor = array.getColor(R.styleable.TagBar_tag_text_color_sel, -1);
        mTextNorColor = array.getColor(R.styleable.TagBar_tag_text_color_nor, -1);
        mTextDisColor = array.getColor(R.styleable.TagBar_tag_text_color_dis, -1);
    }

    private void inflateTagView(Tag tag) {
        TagItem localTagView = (TagItem) View.inflate(getContext(), R.layout.widget_tag, null);
        if (mTagItemBackgroundSel != null)
            localTagView.setTagItemBackgroundSel(mTagItemBackgroundSel);
        if (mTagItemBackgroundNor != null)
            localTagView.setTagItemBackgroundNor(mTagItemBackgroundNor);
        if (mTextSelColor != -1)
            localTagView.setTextSelColor(mTextSelColor);
        if (mTextNorColor != -1)
            localTagView.setTextNorColor(mTextNorColor);
        if (mTextDisColor != -1)
            localTagView.setTextDisColor(mTextDisColor);
        localTagView.setTags(tag);
        addView(localTagView);
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        if (visibility == VISIBLE) {
            Logger.d(getChildCount() + "");
            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
                array.put(child.getId(), i);
                if (!(child instanceof TagItem))
                    throw new RuntimeException("TagBar's child must be subclass of TagItem!");
                if (((TagItem) child).isState())
                    mLastCheckItem = (TagItem) child;
                if (((TagItem) child).getTags().isEnable())
                    child.setOnClickListener(this);
            }
        }
    }

    @Override
    public void onClick(View v) {
        checkTagItem((TagItem) v);
    }


    /**
     * 选择TagItem
     *
     * @param item 选中的TagItem
     */
    public void checkTagItem(TagItem item) {
        if (mLastCheckItem == item) {
            mLastCheckItem.setState(true);
            return;
        } else {
            if (mLastCheckItem != null)
                mLastCheckItem.setState(false);
            item.setState(true);
            mLastCheckItem = item;
        }
        int position = array.get(item.getId());
        if (mTagItemCheckListener != null)
            mTagItemCheckListener.onTagItemCheck(item.getTags(), position);
    }

    public void addTag(Tag tag) {
        mTags.add(tag);
        inflateTagView(tag);
    }

    public void addTags(List<Tag> lists) {
        for (int i = 0; i < lists.size(); i++) {
            addTag(lists.get(i));
        }
    }

    /**
     * TabItem选择监听器
     */
    public interface TagItemCheckListener {

        void onTagItemCheck(Tag tag, int position);
    }
}
