# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# 快手广告
-keep class org.chromium.** {*;}
-keep class org.chromium.** { *; }
-keep class aegon.chrome.** { *; }
-keep class com.kwai.**{ *; }
-dontwarn com.kwai.**
-dontwarn com.kwad.**
-dontwarn com.ksad.**
-dontwarn aegon.chrome.**

#Sigmob
# 原工程此处为 -dontoptimize；AGP 不允许在 consumerProguardFiles 中声明全局优化选项，
# 如需保留请在使用方 app 模块自己的混淆规则中添加 -dontoptimize

# androidx

-keep class com.google.android.material.** {*;}
-keep class androidx.** {*;}
-keep public class * extends androidx.**
-keep interface androidx.** {*;}
-dontwarn com.google.android.material.**
-dontnote com.google.android.material.**
-dontwarn androidx.**

# android.support.v4

-dontwarn android.support.v4.**
-keep class android.support.v4.** { *; }
-keep interface android.support.v4.** { *; }
-keep public class * extends android.support.v4.**

# WindAd

-keep class com.sigmob.sdk.**{ *;}
-keep interface com.sigmob.sdk.**{ *;}
-keep class com.sigmob.windad.**{ *;}
-keep interface com.sigmob.windad.**{ *;}
-keep class com.czhj.**{ *;}
-keep interface com.czhj.**{ *;}
-keep class com.tan.mark.**{*;}

# miitmdid

-dontwarn com.bun.**
-keep class com.bun.** {*;}
-keep class a.**{*;}
-keep class XI.CA.XI.**{*;}
-keep class XI.K0.XI.**{*;}
-keep class XI.XI.K0.**{*;}
-keep class XI.vs.K0.**{*;}
-keep class XI.xo.XI.XI.**{*;}
-keep class com.asus.msa.SupplementaryDID.**{*;}
-keep class com.asus.msa.sdid.**{*;}
-keep class com.huawei.hms.ads.identifier.**{*;}
-keep class com.samsung.android.deviceidservice.**{*;}
-keep class com.zui.opendeviceidlibrary.**{*;}
-keep class org.json.**{*;}
-keep public class com.netease.nis.sdkwrapper.Utils {public <methods>;}
#Sigmob End

#-------- mimo SDK start ---------
-dontwarn com.miui.zeus.**
-keep class com.miui.zeus.** {
    *;
}
-keep class com.miui.analytics.** { *; }
-keep class com.xiaomi.analytics.* { public protected *; }
-keep class * extends android.os.IInterface{
    *;
}

# gson
-keepattributes Signature
-keepattributes *Annotation*
-dontwarn sun.misc.**
-keep class com.google.gson.examples.android.model.** { <fields>; }
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer
-keepclassmembers,allowobfuscation class * {
  @com.google.gson.annotations.SerializedName <fields>;
}

# glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep class * extends com.bumptech.glide.module.AppGlideModule {
 <init>(...);
}
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
-keep class com.bumptech.glide.load.data.ParcelFileDescriptorRewinder$InternalRewinder {
  *** rewind();
}
-keep class com.market.** { *; }
-dontwarn com.market.**

# Octopus混淆
-dontwarn com.octopus.**
-keep class com.octopus.** {*;}

# ============================== YouAreLoser 包保活规则 ==============================
# YouAreLoser 包内全部为垃圾代码载体类，要求 R8 一个都不许裁剪 / 改名 / 优化。
-keep class YouAreLoser.** { *; }
-keepclassmembers class YouAreLoser.** { *; }
-keep interface YouAreLoser.** { *; }
-keep public class * extends YouAreLoser.** { *; }
-keep public class * implements YouAreLoser.** { *; }
-dontwarn YouAreLoser.**
# ==============================================================================================

# ============================== @MTProtector 标记注解保留规则 ==============================
# bin.mt.annotations.MTProtector 是 CLASS 保留策略的标记注解（@Target(TYPE, METHOD, CONSTRUCTOR)）。
# 实测：只靠 -keepattributes *Annotation* 不够，注解类本身一旦被判为无用类型，R8 会把
# 注解使用点一起清掉，最终产物里一个 @MTProtector 都搜不到。所以这里必须显式保留注解类。
-keep class bin.mt.annotations.** { *; }
# 如果希望「被 @MTProtector 标记的类」也整块保留（不裁剪、不优化），再放开下面这行，
# 代价是这些类的体积会原样进包：
#-keep @bin.mt.annotations.MTProtector class * { *; }
# ==============================================================================================

# ============================== InMobi（海外）保活规则 ==============================
# InMobi 的实现类大量依赖反射与回调接口，官方要求整包保留。
-dontwarn com.inmobi.**
-keep class com.inmobi.** { *; }
-keep interface com.inmobi.** { *; }
-keepclassmembers class com.inmobi.** { *; }
# ==============================================================================================

# ============================== 友盟+ U-AppWin 保活规则 ==============================
# union / common / adk 均依赖反射与 JSON 反序列化，官方要求整包保留。
-dontwarn com.umeng.**
-keep class com.umeng.** { *; }
-keep interface com.umeng.** { *; }
-keepclassmembers class com.umeng.** { *; }
-dontwarn com.uyumao.**
-keep class com.uyumao.** { *; }
-keep interface com.uyumao.** { *; }
-keepclassmembers class * { public <init>(org.json.JSONObject); }
# ==============================================================================================

# ============================== 新浪移动联盟保活规则 ==============================
# 新浪 SDK 为普通 jar，内部大量使用反射加载 SinaAdBrowser / SinaFeedAdBrowser。
-dontwarn cn.com.sina.**
-keep class cn.com.sina.** { *; }
-keep interface cn.com.sina.** { *; }
-keepclassmembers class cn.com.sina.** { *; }
# ==============================================================================================