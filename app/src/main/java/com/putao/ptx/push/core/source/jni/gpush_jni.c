#include "gpush.h"
#include <string.h>
#include <jni.h>
#include "com_putao_ptx_push_core_GPush.h"
#include <stdio.h>
#include "helper.h"


#define  LOGE(...)  __android_log_print(ANDROID_LOG_ERROR,LOG_TAG,__VA_ARGS__)
#define  LOGW(...)  __android_log_print(ANDROID_LOG_WARN,LOG_TAG,__VA_ARGS__)
#define  LOGD(...)  __android_log_print(ANDROID_LOG_DEBUG,LOG_TAG,__VA_ARGS__)
#define  LOGI(...)  __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__)

JavaVM * g_vm;
jobject g_obj;
jmethodID g_mid;
jclass g_clazz;

void CHECK(const char* msg, jclass toCheck) {
    if (toCheck == NULL) {
        LOGE("%s: Error retrieving jclass", msg);
    }
}

void notify_cb(int type){
    LOGE("notify message is: %d", type);
}

void msg_cb(const char *appid, const unsigned char *payload, unsigned int payload_size)
{
    fflush(stdout);

    char cp_payload[payload_size+1];
    cp_payload[payload_size] = 0;
    memcpy(cp_payload, payload, payload_size);
    //LOGD("message %s", cp_payload);

    JNIEnv * env;
    // double check it's all ok
    int getEnvStat = (*g_vm)->GetEnv((JavaVM*)g_vm, (void *)&env, JNI_VERSION_1_6);
    if (getEnvStat == JNI_EDETACHED) {
        //LOGW("GetEnv: not attached\n");
        if ((*g_vm)->AttachCurrentThread((JavaVM*)g_vm, (JNIEnv **) &env, NULL) != 0) {
            //LOGW("Failed to attach\n");
        }
    } else if (getEnvStat == JNI_OK) {
        //LOGW("OK");
    } else if (getEnvStat == JNI_EVERSION) {
        LOGW("GetEnv: version not supported\n");
    }
    //LOGD("Call Void Method ");
    jstring jappid = (*env)->NewStringUTF(env, appid);
    jstring jpayload = (*env)->NewStringUTF(env, cp_payload);
    (*env)->CallVoidMethod(env, g_obj, g_mid, jappid, jpayload);
    //(*g_env)->CallStaticVoidMethod(g_env, g_clazz, g_mid, val);
    //LOGD("Call ExceptionCheck Method");
    if ((*env)->ExceptionCheck(env)) {
        (*env)->ExceptionDescribe(env);
    }
    (*g_vm)->DetachCurrentThread(g_vm);

}

//int PTGPush_Initial(const char *http_url, const char *platform, const char *device_token);
jint Java_com_putao_ptx_push_core_GPush_initial(JNIEnv* env,
                                            jobject thiz,
                                            jstring jhttp_url,
                                            jstring jplatform,
                                            jstring jdevice_token) {
    const char *http_url = (*env)->GetStringUTFChars(env, jhttp_url, 0);
    const char *platform = (*env)->GetStringUTFChars(env, jplatform, 0);
    const char *device_token = (*env)->GetStringUTFChars(env, jdevice_token, 0);
    LOGD("GPUSH inital called,  url is:%s, platform is:%s, device is:%s", http_url, platform, device_token);
    int result = PTGPush_Initial(http_url, platform, device_token);
    (*env)->ReleaseStringUTFChars(env, jhttp_url, http_url);
    (*env)->ReleaseStringUTFChars(env, jplatform, platform);
    (*env)->ReleaseStringUTFChars(env, jdevice_token, device_token);
    return result;
}

jint Java_com_putao_ptx_push_core_GPush_uninitial(JNIEnv* env, jobject thiz) {
    int result = PTGPush_Final();
    return result;
}

jint Java_com_putao_ptx_push_core_GPush_login(JNIEnv* env,
                                  jobject thiz,
                                  jstring jsecret_key,
                                  jstring jsecret_token) {
    const char *secret_key = (*env)->GetStringUTFChars(env, jsecret_key, 0);
    const char *secret_token = (*env)->GetStringUTFChars(env, jsecret_token, 0);
    int result = PTGPush_login(secret_key, secret_token);
    PTGPush_setCallBack(msg_cb, notify_cb);
    (*env)->ReleaseStringUTFChars(env, jsecret_key, secret_key);
    (*env)->ReleaseStringUTFChars(env, jsecret_token, secret_token);
    return result;
}

jint
Java_com_putao_ptx_push_core_GPush_reg(JNIEnv* env, jobject thiz, jstring jappId) {
    const char *appId = (*env)->GetStringUTFChars(env, jappId, 0);
    int result = PTGPush_register(appId);
    (*env)->ReleaseStringUTFChars(env, jappId, appId);
    return result;
}

jint
Java_com_putao_ptx_push_core_GPush_unreg(JNIEnv* env, jobject thiz, jstring jappId) {
    const char *appId = (*env)->GetStringUTFChars(env, jappId, 0);
    int result = PTGPush_unRegister(appId);
    (*env)->ReleaseStringUTFChars(env, jappId, appId);
    return result;
}

jint JNI_OnLoad(JavaVM* vm, void* reserved)
{
    g_vm = vm;
    JNIEnv* env;
    (*vm)->GetEnv((JavaVM*)g_vm, (void *)&env, JNI_VERSION_1_6);
    if (env == NULL) {
        LOGD("Failed to get the environment using GetEnv()");
        return -1;
    }

    jclass localClass = (*env)->FindClass(env, "com/putao/ptx/push/core/GPushCallback");
    if (localClass == NULL) {
        LOGD("Failed to find class\n");
    }

    g_clazz = (jclass)((*env)->NewGlobalRef(env, localClass));
    CHECK("GPushCallback", g_clazz);
    (*env)->DeleteLocalRef(env, localClass);

    //init class object
    const jmethodID init_method = (*env)->GetMethodID(env, g_clazz, "<init>", "()V");

    // create reference to the object
    const jobject obj = (*env)->NewObject(env, g_clazz, init_method);
    g_obj = (jobject)(*env)->NewGlobalRef(env, obj);

    (*env)->DeleteLocalRef(env, obj);

    g_mid = (*env)->GetMethodID(env, g_clazz, "callback",
                                "(Ljava/lang/String;Ljava/lang/String;)V");
    if (g_mid == NULL) {
        LOGW("Unable to get method ref\n");
    }

    return JNI_VERSION_1_6;
}

void JNICALL JNI_OnUnload(JavaVM* vm, void* reserved){
    if(g_vm != NULL){
        JNIEnv* env;
        (*g_vm)->GetEnv((JavaVM*)g_vm, (void *)&env, JNI_VERSION_1_6);
        if (env != NULL) {
            (*env)->DeleteGlobalRef(env, g_clazz);
            (*env)->DeleteGlobalRef(env, g_obj);
            (*env)->DeleteGlobalRef(env, g_mid);
            g_mid = 0;
        }
    }
}


