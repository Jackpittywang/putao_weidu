package com.sunnybear.library.view.emoji;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.widget.EditText;

import com.sunnybear.library.BasicApplication;

import java.util.Map;

/**
 * Emoji表情文本输入
 * Created by guchenkai on 2015/11/24.
 */
public class EmojiEditText extends EditText {
    private Map<String, String> emojis;

    public EmojiEditText(Context context) {
        this(context, null, 0);
    }

    public EmojiEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EmojiEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        emojis = BasicApplication.getEmojis();
    }

    public void setEmojiText(CharSequence emoji) {
        if (TextUtils.isEmpty(emoji))
            return;
        Bitmap bitmap = BitmapFactory.decodeFile(emojis.get(emoji));
        ImageSpan span = new ImageSpan(getContext(), bitmap);
        SpannableString spannable = new SpannableString(emoji);
        spannable.setSpan(span, 0, emoji.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        super.append(spannable);
    }
}
