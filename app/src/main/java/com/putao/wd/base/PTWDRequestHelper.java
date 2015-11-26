package com.putao.wd.base;

import com.putao.wd.PTWDConstants;
import com.sunnybear.library.model.http.request.FormEncodingRequestBuilder;
import com.sunnybear.library.util.PreferenceUtils;

/**
 * 继承固定请求参数
 * Created by guchenkai on 2015/11/26.
 */
public class PTWDRequestHelper {

    public static FormEncodingRequestBuilder start() {
        return FormEncodingRequestBuilder.newInstance()
                .addParam(PTWDConstants.REQUEST_KEY_UID, PreferenceUtils.getValue(PTWDConstants.PREFERENCE_KEY_UID, ""))
                .addParam(PTWDConstants.REQUEST_KEY_TOKEN, PreferenceUtils.getValue(PTWDConstants.PREFERENCE_KEY_TOKEN, ""));
    }
}
