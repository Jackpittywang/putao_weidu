package com.sunnybear.library.view.emoji;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.util.ArrayMap;
import android.util.AttributeSet;
import android.widget.TextView;

import com.sunnybear.library.util.StringUtils;

import java.util.Set;
import java.util.regex.Pattern;

/**
 * 支持自定义表情的TextView
 * Created by guchenkai on 2015/11/24.
 */
public class EmojiTextView extends TextView {
    private Html.ImageGetter mImageGetter;
    private ArrayMap<String, Integer> emojis;

    public EmojiTextView(Context context) {
        this(context, null, 0);
    }

    public EmojiTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EmojiTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mImageGetter = new Html.ImageGetter() {
            @Override
            public Drawable getDrawable(String source) {
                if (!StringUtils.isNumeric(source))
                    throw new IllegalArgumentException("没有对应的表情");
                Drawable emoji = getResources().getDrawable(Integer.parseInt(source));
                emoji.setBounds(0, 0, emoji.getIntrinsicWidth(), emoji.getIntrinsicHeight());
                return emoji;
            }
        };
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        if (!isEmoji(text)) {
            super.setText(text, type);
            return;
        }
        text = parseEmoji(text.toString());
        CharSequence sequence = Html.fromHtml(text.toString(), mImageGetter, null);
        super.setText(sequence);
    }

    /**
     * 解析表情
     *
     * @param text 文本
     * @return String
     */
    private String parseEmoji(String text) {
        Set<String> keys = emojis.keySet();
        for (String key : keys) {
            text = text.replace(key, "<img src='" + emojis.get(key) + "'/>");
        }
        return text.replaceAll("\\[", "").replaceAll("\\]", "");
    }

    /**
     * 判断文本中是否带有表情
     *
     * @param text 文本
     * @return boolean
     */
    private boolean isEmoji(CharSequence text) {
        return Pattern
                .compile(".*?\\[(.*?)\\].*?",
                        Pattern.CASE_INSENSITIVE | Pattern.DOTALL)
                .matcher(text).find();
    }
}
