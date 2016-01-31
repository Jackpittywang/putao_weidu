package com.putao.wd;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.Layout;
import android.widget.TextView;

import com.putao.wd.R;
import com.sunnybear.library.controller.BasicFragmentActivity;
import com.sunnybear.library.util.Logger;

import org.xml.sax.XMLReader;

import butterknife.Bind;
import io.vov.vitamio.utils.Log;

/**
 * Created by Administrator on 2016/1/29.
 */
public class TestHtmlActivity extends BasicFragmentActivity {
    private String html = "<img style=\"width: 11em; height: 12em; margin: 0px auto; box-sizing: border-box; background-color: rgb(49, 150, 179);\" src=\"http://statics.xiumi.us/stc/images/templates-assets/parts/001-header/t-b-31-img0-small.png\" class=\"tn-Powered-by-XIUMI\"/>"
            +"<img src=\"http://weidu.file.dev.putaocloud.com/file/bddaf79ea7be0b86c18ed679a703669a730675b6.png\" title=\"bddaf79ea7be0b86c18ed679a703669a730675b6.png\" alt=\"blob\"/></p><p style=\"white-space: normal; line-height: 20px; text-align: center;\">";
    @Bind(R.id.tv_text)
    TextView tv_text;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_html;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        Html.fromHtml(html, new Html.ImageGetter() {
            @Override
            public Drawable getDrawable(String source) {
                Logger.d(source);
                return null;
            }
        }, new Html.TagHandler() {
            @Override
            public void handleTag(boolean opening, String tag, Editable output, XMLReader xmlReader) {
                Logger.d(tag);
            }
        });
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }
}
