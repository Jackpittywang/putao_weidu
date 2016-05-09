LOCAL_PATH := $(call my-dir)

#prepare libgpush_client.so
include $(CLEAR_VARS)
LOCAL_MODULE    := libgpush_client_pre
LOCAL_SRC_FILES := prebuilt/$(TARGET_ARCH_ABI)/libgpush_client.so
include $(PREBUILT_SHARED_LIBRARY)

#build android client libgpush.so 
include $(CLEAR_VARS)
LOCAL_MODULE    := gpush
LOCAL_SHARED_LIBRARIES := gpush_client_pre
LOCAL_SRC_FILES := gpush_jni.c
LOCAL_LDLIBS += -llog
include $(BUILD_SHARED_LIBRARY)


