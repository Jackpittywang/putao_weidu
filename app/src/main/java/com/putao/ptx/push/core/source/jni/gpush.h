#ifndef _pt_gpush_h_
#define _pt_gpush_h_
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

/* 参数不正确 */
#define GE_INVALID_PARAM        -100
/* 连接时，向gate_server获取服务器地址错误 */
#define GE_GET_SERVERADDR       -101
/* 连接时服务器错误 */
#define GE_CONNECT_SERVER   -102
/* 分配内存错误 */
#define GE_MALLOC               -103
/* 未知错误 */
#define GE_UNKNOW               -104
/* 不能初始化线程*/
#define GE_INITTHREAD           -105

enum {
	NTF_CONNECT_ERROR = 1,
	NTF_LOGIN_ERROR,
	NTF_REGISTER_ERROR,
	NTF_DISCONNECT,
};
	
/*
 * 当接收到消息时，回调通知用户.
 * @param 1 const char * appid. app 唯一标识,参数表示接收推送消息的app.
 * @param 2 const unsigned char *payload.标识发向app 的数据.
 * @param 3 const unsigned int payload_size.标识发向app 的数据长度.
 */
typedef void (*onMessageCB)(const char *, const unsigned char *, unsigned int);
/*
 * 当出现某些情况时，回调通知用户
 * @param type 错误类型 NTF_CONNECT_ERROR/...
 */
typedef void (*onNotifyCB)(int type);
	
/* -----------------------------------------------	*\
 * 以下函数可以在任何任何时段调用
\* -----------------------------------------------	*/

/**
 * 设置接收/通知回调
 */
void PTGPush_setCallBack(onMessageCB messagecb, onNotifyCB notifycb);

/* -----------------------------------------------	*\
 * 以下带返回值的函数，返回0为成功；否者失败，返回值即为错误码
 \* -----------------------------------------------	*/

/**
 * 修改计时器，计时器用于在间隔时间内检测网络是否有用
 * 仅在初始化成功后有效
 * @param tm 间隔时间，单位毫秒，<0停掉计时器，＝0使用默认值
 */
void PTGPush_modifyTimer(int tm);

/**
 * 初始化并获取连接信息
 * @param http_url url，如"http://10.1.11.171:10090/mqtt_server"
 * @param platfrom 平台android ios
 * @param device_token 设备号 长度16的字符串 如“0123456789abcdef”
 */
int PTGPush_Initial(const char *http_url, const char *platform, const char *device_token);

/**
 * 连接并登录服务器
 * @param secret_key
 * @param secret_token
 */
int PTGPush_login(const char *secret_key, const char *secret_token);

/**
 * 注册主题
 * @param app_id 主题
 */
int PTGPush_register(const char *app_id);

/**
 * 注销主题
 * @param app_id 主题
 */
int PTGPush_unRegister(const char *app_id);

/**清理环境*/
int PTGPush_Final();

#ifdef __cplusplus
}
#endif
#endif		// _pt_gpush_h_
