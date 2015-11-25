package com.sunnybear.library.view.emoji;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.util.ArrayMap;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Emoji表情文本输入
 * Created by guchenkai on 2015/11/24.
 */
public class EmojiEditText extends EditText {
    private ArrayMap<String, Integer> emojis;

    public EmojiEditText(Context context) {
        super(context);
    }

    public EmojiEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EmojiEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setEmojiText(CharSequence emoji) {
        if (TextUtils.isEmpty(emoji))
            return;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),emojis.get(emoji));
        ImageSpan span = new ImageSpan(getContext(), bitmap);
        SpannableString spannable = new SpannableString(emoji);
        spannable.setSpan(span, 0, emoji.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        super.append(spannable);
    }
}
