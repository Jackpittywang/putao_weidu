package com.putao.wd.account;

/**
 * 常量
 * Created by guchenkai on 2015/10/16.
 */
public final class AccountConstants {

    /**
     * 参数常量
     */
    public static final class Parameter {
        public static final String PARAM_MOBILE = "mobile";                 //账户
        public static final String PARAM_PASSWD = "passwd";                 //登陆密码
        public static final String PARAM_PASSWD_ONCE = "passwd_once";       //密码
        public static final String PARAM_PASSWD_TWICE = "passwd_twice";     //密码确认
        public static final String PARAM_OLD_PASSWD = "old_passwd";         //旧密码
        public static final String PARAM_CLIENT_TYPE = "client_type";       //设备类型(1:ios,2:andriod,3:wp,4:other)
        public static final String PARAM_VERSION = "version";               //version
        public static final String PARAM_PLATFORM_ID = "platform_id";       //平台(putao:1)
        public static final String PARAM_DEVICE_ID = "device_id";           //设备id(IMEI)
        public static final String PARAM_CODE = "code";                     //手机验证码
        public static final String PARAM_GRAPH_CODE = "verify";             //图形验证码
        public static final String PARAM_APPID = "appid";                   //平台ID(1101:ios,1102:Android)
        public static final String PARAM_ACTION = "action";                 //验证码发送原因(register|forget|changephone|checkoldphone|login)
        public static final String PARAM_TOKEN = "token";                   //令牌
        public static final String PARAM_REFRESH_TOKEN = "refresh_token";   //刷新用的token
        public static final String PARAM_UID = "uid";                       //用户ID
        public static final String PARAM_NICK_NAME = "nick_name";           //昵称
        public static final String PARAM_SIGN = "sign";                     //签名
    }

    /**
     * Url常量
     */
    public static final class Url {
        public static final String URL_REGISTER = "api/register";           //注册
        public static final String URL_CHECK_MOBILE = "api/checkMobile";     //手机注册与否检测
        //        public static final String URL_SEND_VERIFY_CODE = "api/sendMsg";     //发送验证码
        public static final String URL_SEND_VERIFY_CODE = "/api/safeSendMsg";//发送验证码安全接口
        public static final String URL_FORGET = "api/forget";               //忘记密码(手机)
        public static final String URL_LOGIN = "user/login";                 //登录后的连接传送
        public static final String URL_SAFELOGIN = "api/safeLogin";         //安全登录
        public static final String URL_UPDATE_PASSWORD = "api/changePasswd";  //修改密码
        public static final String URL_UPDATE_TOKEN = "api/updateToken";    //更新token
        public static final String URL_CHECK_TOKEN = "api/checkToken";      //验证token
        public static final String URL_GET_NICK_NAME = "api/getNickName";   //获取昵称
        public static final String URL_SET_NICK_NAME = "api/setNickName";   //设置昵称
        public static final String URL_SEND_PHOTO_CODE = "api/verification";//发送图形验证码
    }

    /**
     * 验证码发送原因
     */
    public static final class Action {
        public static final String ACTION_REGISTER = "register";            //注册
        public static final String ACTION_FORGET = "forget";                //忘记密码
        public static final String ACTION_LOGIN = "login";                  //登录
        public static final String ACTION_CHANGEPHONE = "changephone";
        public static final String ACTION_CHECKOLDPHONE = "checkoldphone";
    }

    /**
     * 验证错误信息
     */
    public static final class ErrorMessage {
        public static final String check_password_error_length_msg = "密码长度必须大于6位且小于18位";
        public static final String check_password_error_diff_msg = "两次输入密码不一致";
        public static final String check_nick_name_error_length_msg = "昵称长度必须大于4位且小于24位";
        public static final String check_nick_name_error_format_msg = "昵称仅支持中英文和数字";
    }

    /**
     * EventBus常量
     */
    public static final class EventBus {
        public static final String EVENT_ALBUM_SELECT = "event_album_select";
        public static final String EVENT_REFRESH_COMPANION = "event_refresh_companion";
        public static final String EVENT_REFRESH_COMPANION_TAB = "event_refresh_companion_tab";
        public static final String EVENT_CANCEL_COMPANION_TAB = "event_cancel_companion_tab";
        public static final String EVENT_REFRESH_ME_TAB = "event_refresh_me_tab";
        public static final String EVENT_DELETE_COMPANION = "event_delete_companion";
        public static final String EVENT_DISCOVERY_CAROUSEL = "event_discovery_Carousel";
        public static final String EVENT_DISCOVERY_HOT_TAG = "event_discovery_hot_tag";
        public static final String EVENT_DISCOVERY_RESOURCE = "event_discovery_resource";
        public static final String EVENT_GAME_START_ACTIVITY = "event_game_start_activity";
        public static final String EVENT_DISCOVERY_RESOURCE_TAG = "event_discovery_resource_tag";
        public static final String EVENT_UPDATE_UPLOAD = "event_update_upload";
        public static final String EVENT_REFRESH_SUBSCRIBE = "event_refresh_subscribe";
        public static final String EVENT_COMPANION_POP_DISMISS = "event_companion_pop_dismiss";

    }

    /**
     * Bundle常量
     */
    public static final class Bundle {
        public static final String BUNDLE_SERVICE_ID = "bundle_service_id";
        public static final String BUNDLE_SERVICE_NAME = "bundle_service_name";
        public static final String BUNDLE_COMPANION = "bundle_companion";
        public static final String BUNDLE_COMPANION_NOT_DOWNLOAD = "bundle_companion_not_download";
        public static final String BUNDLE_COMPANION_SERVICE_MESSAGE_LIST = "bundle_companion_service_message_list";
        public static final String BUNDLE_COMPANION_BIND_SERVICE = "bundle_companion_bind_service";
        public static final String BUNDLE_COMPANION_COLLECTION = "bundle_companion_collection";
        public static final String BUNDLE_COMPANION_BIND = "bundle_companion_bind";
        public static final String BUNDLE_STORE_STATUS = "bundle_store_status";
        public static final String BUNDLE_STORE_ISHAVE = "bundle_store_ishave";
        public static final String BUNDLE_DISCOVRY_RESOURCE_TAG = "bundle_discovry_resource_tag";
        public static final String BUNDLE_DISCOVRY_RESOURCE_FIND = "bundle_discovry_resource_find";
        public static final String BUNDLE_DISCOVRY_RESOURCE_TAG_ID = "bundle_discovry_resource_tag_id";
        public static final String BUNDLE_DISCOVRY_RESOURCE_TAG_TITLE = "bundle_discovry_resource_tag_id_title";
        public static final String BUNDLE_DISCOVERY_ARTICLE = "bundle_discovery_article";
        public static final String BUNDLE_ARTICLE_CLICK = "bundle_article_click";
        public static final String BUNDLE_SERVICE_SUBSCR_STATE = "bundle_service_subscr_state";

    }

    public enum State{
        putaosubscr("0"),subscr("1"),service("");
        private final String state;
        private State(String state) {
            this.state = state;
        }
    }

}
