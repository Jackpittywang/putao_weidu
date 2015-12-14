# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\Android\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
#保持注解类不被混淆
-keepattributes *Annotation*
#保持不被混淆
-keep public class * extends android.app.Activity
-keep pubilc class * extends android.app.Application
-keep pubilc class * extends android.app.Service
#mode配置
-keep class com.putao.wd.dto.** { *; }
-keep class com.putao.wd.model.** { *; }
-keepclasseswithmembers class * implements android.os.Parcelable {
    static android.os.Parcelable$Creator CREATOR;
}
-keepclasseswithmembers class * implemnts java.io.Serializable {
    public *;
}
#保持自定义控件类不被混淆
-keepclasseswithmembers class * {
    public <init>(android.content.Context);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}
-keepclasseswithmembers class * {
    public <init>(android.context.Context,android.util.AttributeSet,int);
}
-keepclassmembers class * extends android.app.Activity {
    public void *(android.view.View);
}
# 保持自定义控件类不被混淆
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}
# OkHttp配置
-dontwarn com.squareup.okhttp.**
-keep class com.squareup.okhttp.** { *; }
-dontwarn okio.**
#retrofit配置
-dontwarn retrofit.**
-keep class retrofit.** { *; }
-keepattributes Signature
-keepattributes Exceptions
# fastjson配置
-dontwarn com.alibaba.fastjson.**
-keep class com.alibaba.fastjson.** { *; }
-keepattributes Signature
# Gson配置
-keepattributes Signature
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }
-keep class com.google.gson.** { *; }
-keep class com.antew.redditinpictures.library.imgur.** { *; }
-keep class com.antew.redditinpictures.library.reddit.** { *; }
# LeakCanary配置
#-keep class org.eclipse.mat.** { *; }
#-keep class com.squareup.leakcanary.** { *; }
# GreenDao配置
-keep class de.greenrobot.dao.** { *; }
-keepclassmembers class * extends de.greenrobot.dao.AbstractDao {
    public static java.lang.String TABLENAME;
}
-keep class **$Properties
#bugly配置
-keep public class com.tencent.bugly.** { *; }
#Baidu地图配置
-keep class com.baidu.** { *; }
-keep class vi.com.** { *; }
-dontwarn com.baidu.**