package com.putao.wd.util;

import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;

import com.sunnybear.library.util.StringUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Html解析工具
 * Created by guchenkai on 2015/12/9.
 */
public class HtmlUtils {

    /**
     * 解析Html到Html具体信息
     *
     * @param html Html
     * @return tml具体信息
     */
    private static List<HtmlInfo> parse(String html) {
        List<HtmlInfo> infos = new ArrayList<>();
        Document document = Jsoup.parse(html);
        Elements elements = document.body().getAllElements();
        for (int i = 1; i < elements.size(); i++) {
            Element element = elements.get(i);
            HtmlInfo info = new HtmlInfo();
            if (StringUtils.equals("font", element.tagName())) {
                Attributes attributes = element.attributes();
                String title = element.childNode(0).toString().replace("\\n", "\n");
                SpannableStringBuilder builder = new SpannableStringBuilder(title);
                builder.setSpan(new ForegroundColorSpan(Color.parseColor("#" + attributes.get("color"))), 0, title.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                builder.setSpan(new AbsoluteSizeSpan(Integer.parseInt(attributes.get("size")), true), 0, title.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                if (attributes.hasKey("bold"))
                    builder.setSpan(new StyleSpan(Typeface.BOLD), 0, title.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                info.setBuilder(builder);
                if (i + 1 < elements.size()) {
                    Element nextElement = elements.get(i + 1);
                    if (StringUtils.equals("br", nextElement.tagName()))
                        info.setIsLineFeed(true);
                }
                if (i == elements.size() - 1)
                    info.setIsLineFeed(true);
                infos.add(info);
            }
        }
        return infos;
    }

    /**
     * 解析Html到SpannableStringBuilder
     *
     * @param html Html
     * @return SpannableStringBuilder集合
     */
    public static List<SpannableStringBuilder> getTexts(String html) {
        List<HtmlInfo> infos = parse(html);
        List<SpannableStringBuilder> builders = new ArrayList<>();
        SpannableStringBuilder builder = new SpannableStringBuilder();
        for (HtmlInfo info : infos) {
            builder.append(info.getBuilder());
            if (info.isLineFeed()) {
                builders.add(builder);
                builder = new SpannableStringBuilder();
            }
        }
        return builders;
    }
}
