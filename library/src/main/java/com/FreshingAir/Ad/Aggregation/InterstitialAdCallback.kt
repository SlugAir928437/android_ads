package com.FreshingAir.Ad.Aggregation

import android.content.Context
import androidx.annotation.Keep

/**
 * 本地广告 SDK 的插屏广告回调统一出口。
 *
 * 各广告实现（穿山甲 / 广点通 / 百度 / 快手 / 华为 / AdMob …）在插屏加载成功、
 * 展示、关闭或失败时调用这里，由使用方页面通过 [LocalAdBridge] 接管。
 * 未注册回调时不做任何处理。
 *
 * 入口类不做混淆/裁剪：类与成员均标注 `@Keep`（也可改用 proguard 规则保留）。
 */
@Keep
object InterstitialAdCallback {

    /** 插屏加载成功（多数平台在加载成功后随即自动展示）。 */
    @Keep
    @JvmStatic
    fun onInterstitialAdLoaded(context: Context) {
        LocalAdBridge.onInterstitialAdLoaded?.invoke(context)
    }

    /** 插屏开始展示（曝光）。 */
    @Keep
    @JvmStatic
    fun onInterstitialAdShow(context: Context) {
        LocalAdBridge.onInterstitialAdShow?.invoke(context)
    }

    /** 插屏关闭（用户看完 / 跳过 / 点击后返回）。 */
    @Keep
    @JvmStatic
    fun onInterstitialAdClose(context: Context) {
        LocalAdBridge.onInterstitialAdClose?.invoke(context)
    }

    /**
     * 插屏加载或展示失败。
     *
     * @param errorCode 平台错误码，平台未提供时传 0
     * @param errorMsg  平台错误信息，平台未提供时传 null
     */
    @Keep
    @JvmStatic
    fun onInterstitialAdError(context: Context, errorCode: Int, errorMsg: String?) {
        LocalAdBridge.onInterstitialAdError?.invoke(context, errorCode, errorMsg ?: "")
    }
}
