package com.putao.wd.account;

import android.content.Context;

import com.umeng.analytics.MobclickAgent;


/**
 * 友盟Api
 * Created by guchenkai on 2015/11/2.
 */
public class YouMengHelper {

/**
 * @brief 定义所有友盟统计 相关页面、统计事件名称
 */


/***********************************************************************************/
/********************************   探索 Tabbar   ***********************************/

    /**
     * @brief 探索页面 进入探索文章详情页点击次数
     */
    public static final String ChoiceHome_home_detail = "ChoiceHome_home_detail";

    /**
     * @brief 探索详情页面 评论按钮点击的次数
     */
    public static final String ChoiceHome_detail_comment = "ChoiceHome_detail_comment";

    /**
     * @brief * 探索文章详情页关闭和切换 点击关闭按钮关闭/下拉探索文章详情页的次数 method = {"按钮点击","下拉"}
     */
    public static final String ChoiceHome_detail_close = "ChoiceHome_detail_close";

    /**
     * @brief * 探索文章分享页面 探索文章分享按钮点击次数 platform = {"新浪微博","微信好友","微信朋友圈","QQ空间","QQ好友"}
     */
    public static final String ChoiceHome_detail_share = "ChoiceHome_detail_share";

    /**
     * 点击查看视频的次数
     */
    public static final String DiscoverHome_watch = "DiscoverHome_watch";


/***********************************************************************************/


/***********************************************************************************/
/********************************   创造 Tabbar   ***********************************/

    /**
     * @brief * 创造页面 进入下级界面的次数 place = {"妙想","创造","精选"}
     */
    public static final String CreatorHome_next = "CreatorHome_next";

    /**
     * @brief 创造文章详情页面 评论按钮点击的次数
     */
    public static final String CreatorHome_originate_detail_comment = "CreatorHome_originate_detail_comment";

    /**
     * @brief 创造文章详情页面 点关注的次数
     */
    public static final String CreatorHome_originate_detail_interested = "CreatorHome_originate_detail_interested";

    /**
     * @brief * 创造文章详情页关闭和切换 点击关闭按钮/下拉关闭探索文章详情页的次数 method = {"按钮点击","下拉"}
     */
    public static final String CreatorHome_originate_detail_close = "CreatorHome_originate_detail_close";

    /**
     * @brief * 创造文章分享页面 创造文章分享按钮点击次数 platform = {"新浪微博","微信好友","微信朋友圈","QQ空间","QQ好友"}
     */
    public static final String CreatorHome_originate_detail_share = "CreatorHome_originate_detail_share";

    /**
     * @brief 妙想文章详情页面 评论按钮点击的次数
     */
    public static final String CreatorHome_conceit_detail_comment = "CreatorHome_conceit_detail_comment";

    /**
     * @brief 妙想文章详情页面 点关注的次数
     */
    public static final String CreatorHome_conceit_detail_interested = "CreatorHome_conceit_detail_interested";

    /**
     * @brief * 妙想文章详情页关闭和切换 点击关闭按钮/下拉关闭探索文章详情页的次数 method = {"按钮点击","下拉"}
     */
    public static final String CreatorHome_conceit_detail_close = "CreatorHome_conceit_detail_close";

    /**
     * @brief * 妙想文章分享页面 妙想文章分享按钮点击次数 platform = {"新浪微博","微信好友","微信朋友圈","QQ空间","QQ好友"}
     */
    public static final String CreatorHome_conceit_detail_share = "CreatorHome_conceit_detail_share";

    /**
     * @brief 精选列表页面 点击进入商品详情页的次数
     */
    public static final String CreatorHome_mall_detail = "CreatorHome_mall_detail";

    /**
     * @brief 商品详情页 点击加入购物车的成功次数
     */
    public static final String CreatorHome_mall_detail_add_success = "CreatorHome_mall_detail_add_success";

    /**
     * @brief 商品详情页 点击加入购物车的失败次数
     */
    public static final String CreatorHome_mall_detail_add_fail = "CreatorHome_mall_detail_add_fail";


    /**
     * @brief 商品详情页 点击购物车的次数
     */
    public static final String CreatorHome_conceit_detail_cart = "CreatorHome_conceit_detail_cart";

    /**
     * @brief 商品详情页 点击返回按钮次数
     */
    public static final String CreatorHome_conceit_detail_back = "CreatorHome_conceit_detail_back";

    /**
     * 点击进入商品详情页的次数
     */
    public static final String MallHome_detail = "MallHome_detail";

    /**
     * 加入购物车点击次数
     */
    public static final String MallHome_detail_add = "MallHome_detail_add";

    /**
     * 购物车点击次数
     */
    public static final String MallHome_detail_shopper = "MallHome_detail_shopper";

    /**
     * 返回按钮点击次数
     */
    public static final String MallHome_detail_back = "MallHome_detail_back";

    /**
     * 分享按钮点击次数
     */
    public static final String MallHome_detail_share = "MallHome_detail_share";

    /**
     * 点击“返回”按钮次数
     */
    public static final String Shopper_back = "Shopper_back";

    /**
     * 点击“编辑”的次数
     */
    public static final String Shopper_edit = "Shopper_edit";

    /**
     * 点击“去结算”的次数
     */
    public static final String Shopper_cacul = "Shopper_cacul";

    /**
     * 点击“返回”按钮次数
     */
    public static final String Order_fillout = "Order_fillout";

    /**
     * 点击“取消”按钮次数
     */
    public static final String Cashier_cancel = "Cashier_cancel";


/***********************************************************************************/


/***********************************************************************************/
/********************************   陪伴 Tabbar   ***********************************/

    /**
     * @brief 陪伴页面 启动扫一扫的次数
     */
    public static final String AccompanyHome_scan = "AccompanyHome_scan";

    /**
     * @brief 陪伴页面 启动扫一扫的次数
     */
    public static final String Scan_action = "Scan_action";

    /**
     * @brief 陪伴页面 点击管理按钮的次数
     */
    public static final String AccompanyHome_control = "AccompanyHome_control";

    /**
     * @brief 陪伴页面 点击进入多元智能理论的次数
     */
    public static final String AccompanyHome_theory = "AccompanyHome_theory";

    /**
     * @brief * 智能应用 游戏的点击次数 game = {"淘淘向右走","班得瑞的奇幻花园","旋转吧魔方","麦斯丝","Hello编程","哈尼海洋","涂涂世界"}
     */
    public static final String AccompanyHome_app_game = "AccompanyHome_app_game";

    /**
     * @brief * 管理页面 管理条目的点击次数 item = {"受控设备","受控产品","每日使用次数","每次使用时间"}
     */
    public static final String AccompanyHome_control_item = "AccompanyHome_control_item";

    /**
     * @brief 陪伴内容详情页 选择题答题的次数
     */
    public static final String AccompanyHome_app_detail_choice = "AccompanyHome_app_detail_choice";

    /**
     * @brief 陪伴内容详情页 点击返回按钮次数
     */
    public static final String AccompanyHome_app_detail_back = "AccompanyHome_app_detail_back";

    /**
     * @brief * 陪伴内容分享页面 陪伴内容分享按钮点击次数 platform = {"新浪微博","微信好友","微信朋友圈","QQ空间","QQ好友"}
     */
    public static final String AccompanyHome_app_detail_share = "AccompanyHome_app_detail_share";

    /**
     * 葡萄公众号账号页 订阅号“取消关联”点击次数
     */
    public static final String Activity_menu_dessociate = "Activity_menu_dessociate";
    /**
     * 葡萄活动“文章详情”点击次数
     */
    public static final String Activity_list_detail = "Activity_list_detail";

    /**
     * 陪伴列表页面 点击菜单次数 menu={“菜单一”、“菜单二”，“菜单三”}
     */
    public static final String Activity_list_menu = "Activity_list_menu";

    /**
     * 陪伴列表页面  点击返回按钮次数
     */
    public static final String Activity_list_back = "Activity_list_back";

    /**
     * 陪伴葡萄活动文章详情页面 点击返回按钮次数
     */
    public static final String Activity_detail_back = "Activity_detail_back";

    /**
     * 陪伴葡萄活动文章详情页面  点击赞、回复的次数
     */
    public static final String Activity_detail_action = "Activity_detail_action";


    /**
     * TODO
     * “立即停止孩子游戏”点击次数
     */
    public static final String Activity_menu_detail_stop = "Activity_menu_detail_stop";

    /**
     * “我要提问”点击次数
     */
    public static final String Activity_menu_should_ask = "Activity_menu_should_ask";

//    /**
//     * "稍后关联"点击次数
//     */
//    public static final String Activity_home_associate_later = "Activity_home_associate_later";
//
//    /**
//     * “葡萄订阅”点击次数
//     */
//    public static final String Activity_home_destination = "Activity_home_destination";
//
//    /**
//     * "葡萄订阅-订阅号按钮"点击次数
//     */
//    public static final String Activity_home_putaoSubscription_subscriberClick = "Activity_home_putaoSubscription_subscriberClick";
//
//    /**
//     * "葡萄订阅-订阅号点击进入详情"点击次数
//     */
//    public static final String Activity_home_putaoSubscription_detailClick = "Activity_home_putaoSubscription_detailClick";
//
//    /**
//     * 关联产品（从未关联产品陪伴页面关联产品、从产品列表页右上角+号关联产品、从产品列表页未关联产品介绍点击关联）
//     */
//    public static final String Assocaite_product = "Assocaite_product";
//
//    /**
//     * 订阅(从葡萄订阅-订阅号点击订阅、从发现文章详情订阅号点击订阅）
//     */
//    public static final String Subscribe_product = "Subscribe_product";








    /**
     * “稍后关联”点击次数
     */
    public static final String Activity_home_associate_later = "Activity_home_associate_later";

    /**
     * “葡萄订阅”点击次数
     */
    public static final String Activity_home_destination = "Activity_home_destination";

    /**
     * “葡萄订阅-订阅号按钮”点击次数
     */
    public static final String Activity_home_putaoSubscription_subscriberClick = "Activity_home_putaoSubscription_subscriberClick";

    /**
     *“葡萄订阅-订阅号点击进入详情”点击总次数
     */
    public static final String Activity_home_putaoSubscription_detailClick = "Activity_home_putaoSubscription_detailClick";

    /**
     * 从未关联产品陪伴页点击关联//从产品列表页右上角+号点击关联//从产品列表页未关联产品介绍点击关联
     */
    public static final String Assocaite_product = "Assocaite_product";

    /**
     * //从葡萄订阅-订阅号点击订阅//从发现文章详情订阅号点击订阅
     */
    public static final String Subscribe_product = "Subscribe_product";


/***********************************************************************************/


/***********************************************************************************/
/********************************   我 Tabbar   ************************************/

    /**
     * @brief * 我的页面 内容条目的点击次数 item = {"设置","孩子信息","我的订单","售后","收货地址","我的关注","消息中心","葡萄籽"}
     */
    public static final String UserHome_cell_item = "UserHome_cell_item";

    /**
     * @brief 收货地址页面 点击新增收货地址的次数
     */
    public static final String UserHome_address_add = "UserHome_address_add";

    /**
     * @brief 收货地址页面 点击编辑的次数
     */
    public static final String UserHome_address_edit = "UserHome_address_edit";

    /**
     * @brief 收货地址页面 点击返回按钮次数
     */
    public static final String UserHome_address_back = "UserHome_address_back";

    /**
     * @brief 地址管理页面 点击删除地址的次数
     */
    public static final String UserHome_address_edit_delet = "UserHome_address_edit_delet";

    /**
     * @brief 地址管理页面 点击设为默认收货地址的次数
     */
    public static final String UserHome_address_edit_default = "UserHome_address_edit_default";

    /**
     * @brief 地址管理页面 点击保存按钮次数
     */
    public static final String UserHome_address_edit_save = "UserHome_address_edit_save";

    /**
     * @brief 地址管理页面 点击返回按钮次数
     */
    public static final String UserHome_address_edit_cancel = "UserHome_address_edit_cancel";

    /**
     * @brief 我的关注页面 点击文章详情的次数
     */
    public static final String UserHome_interested_detail = "UserHome_interested_detail";

    /**
     * @brief 我的关注页面 点击返回按钮的次数
     */
    public static final String UserHome_interested_back = "UserHome_interested_back";

    /**
     * @brief 消息中心页面 点击回复按钮的次数
     */
    public static final String UserHome_infocenter_reply = "UserHome_infocenter_reply";

    /**
     * @brief 消息中心页面 点击赞按钮的次数
     */
    public static final String UserHome_infocenter_good = "UserHome_infocenter_good";

    /**
     * @brief 消息中心页面 点击提醒按钮次数
     */
    public static final String UserHome_infocenter_remind = "UserHome_infocenter_remind";

    /**
     * @brief 消息中心页面 点击通知按钮次数
     */
    public static final String UserHome_infocenter_notice = "UserHome_infocenter_notice";

    /**
     * @brief * 葡萄籽页面 葡萄籽条目的点击次数 item = {"我要提问","综合","淘淘向右走","班得瑞的奇幻花园","旋转吧魔方","麦斯丝","Hello编程","哈尼海洋","涂涂世界"}
     */
    public static final String UserHome_qa_item = "UserHome_qa_item";


    /*********************************************注册、找回密码**************************************************/
    /**
     * 点击注册、找回密码按钮次数
     */
    public static final String Login_action = "Login_action";


    /*********************************************陪伴、我、精品、发现**************************************************/
    /**
     * 点击陪伴、我、精品、发现的按钮次数
     */
    public static final String Tabbar_pressed = "Tabbar_pressed";

    /**
     * TODO
     * "清除缓存"点击次数
     */
    public static final String UserHome_setup_flush = "UserHome_setup_flush";

    /**
     * "退出登录"点击次数
     */
    public static final String UserHome_setup_logout = "UserHome_setup_logout";

    /**
     * 点击进入收藏文章详情次数
     */
    public static final String UserHome_collect_detail = "UserHome_collect_detail";

    /**
     * "取消收藏"点击次数
     */
    public static final String UserHome_collect_delete = "UserHome_collect_delete";

    /****************************************************发现********************************************************/
//    /**
//     * “发现-视频”、"发现-找资源"点击次数
//     */
//    public static final String DiscoverHome_title = "DiscoverHome_title";
//
//    /**
//     * “发现-找资源-广告位"点击次数
//     */
//    public static final String DiscoverHome_banner = "DiscoverHome_banner";
//
//    /**
//     * "发现-找资源-活动/专题"点击次数
//     */
//    public static final String DiscoverHome_column = "DiscoverHome_column";
//
//    /**
//     * "发现-找资源-tag"点击次数
//     */
//    public static final String DiscoverHome_tag = "DiscoverHome_tag";
//
//    /**
//     * "发现-找资源-文章"点击次数
//     */
//    public static final String DiscoverHome_article = "DiscoverHome_article";


    /**
     * “发现-视频”点击次数
     */
    public static final String DiscoverHome_title = "DiscoverHome_title";

//    /**
//     * “发现-找资源”点击次数
//     */
//    public static final String DiscoverHome_title = "DiscoverHome_title";

    /**
     * “发现-找资源-广告位”点击次数
     */
    public static final String DiscoverHome_banner = "DiscoverHome_banner";

    /**
     * “发现-找资源-活动”点击次数
     */
    public static final String DiscoverHome_column = "DiscoverHome_column";

//    /**
//     * “发现-找资源-专题”点击次数
//     */
//    public static final String DiscoverHome_column = "DiscoverHome_column";

    /**
     * “发现-找资源-tag”点击次数（需分tag统计）
     */
    public static final String DiscoverHome_tag = "DiscoverHome_tag";

    /**
     * “发现-找资源-文章”点击次数（需分tag统计）
     */
    public static final String DiscoverHome_article = "DiscoverHome_article";

    /**
     * “发现-推荐”点击次数
     */
    public static final String DiscoverHome_recommend = "DiscoverHome_recommend";

    /**
     * “发现-标签-文章list”点击次数
     */
    public static final String DiscoverHome_tag_list = "DiscoverHome_tag_list";



    /**
     * 记录打点数据
     */
    public static void onEvent(Context context, String name, String tag) {
        MobclickAgent.onEvent(context, name, tag);
//        YouMengHelper.onEvent(context, name, tag);
    }

    public static void onEvent(Context context, String name) {
        MobclickAgent.onEvent(context, name);
//        YouMengHelper.onEvent(context, name);
    }

}
