package com.FreshingAir.Ad.Aggregation

import android.content.Context
import android.view.ViewGroup
import androidx.annotation.Keep

/**
 * 本地广告 SDK 与 APK 页面之间的桥接。
 *
 * 本地广告 SDK（`com.FreshingAir.Ad.Aggregation.ads.*`）在广告生命周期节点上回调
 * [SplashAdCallback] / [InterstitialAdCallback] / [BannerAdCallback] / [RewardVideoAdCallback]
 * 的静态方法，再由本桥接转发给当前正在展示广告的页面。
 *
 * 入口类不做混淆/裁剪：类与成员均标注 `@Keep`（也可改用 proguard 规则保留）。
 */
@Keep
object LocalAdBridge {

    // ------------------------------ 开屏广告 ------------------------------

    /** 广告 View 渲染完成，参数为（上下文，广告 View） */
    @Keep
    @JvmStatic
    var onSplashAdLoaded: ((Context, ViewGroup) -> Unit)? = null

    /** 广告结束（关闭 / 加载失败），可以进入下一页 */
    @Keep
    @JvmStatic
    var onSplashAdFinished: ((Context) -> Unit)? = null

    // ------------------------------ 视频广告（激励视频） ------------------------------

    /** 视频广告加载成功，可以展示 */
    @Keep
    @JvmStatic
    var onRewardVideoAdLoaded: ((Context) -> Unit)? = null

    /** 视频广告开始播放 */
    @Keep
    @JvmStatic
    var onRewardVideoAdShow: ((Context) -> Unit)? = null

    /** 视频广告激励发放，参数为（上下文，激励名称，激励数量） */
    @Keep
    @JvmStatic
    var onRewardVideoAdRewarded: ((Context, String, Int) -> Unit)? = null

    /** 视频广告关闭 */
    @Keep
    @JvmStatic
    var onRewardVideoAdClose: ((Context) -> Unit)? = null

    /** 视频广告加载 / 播放失败，参数为（上下文，错误码，错误信息） */
    @Keep
    @JvmStatic
    var onRewardVideoAdError: ((Context, Int, String) -> Unit)? = null

    // ------------------------------ 插屏广告 ------------------------------

    /** 插屏加载成功 */
    @Keep
    @JvmStatic
    var onInterstitialAdLoaded: ((Context) -> Unit)? = null

    /** 插屏开始展示（曝光） */
    @Keep
    @JvmStatic
    var onInterstitialAdShow: ((Context) -> Unit)? = null

    /** 插屏关闭 */
    @Keep
    @JvmStatic
    var onInterstitialAdClose: ((Context) -> Unit)? = null

    /** 插屏加载 / 展示失败，参数为（上下文，错误码，错误信息） */
    @Keep
    @JvmStatic
    var onInterstitialAdError: ((Context, Int, String) -> Unit)? = null

    // ------------------------------ Banner 广告 ------------------------------

    /** Banner 渲染完成，参数为（上下文，已填充广告 View 的容器） */
    @Keep
    @JvmStatic
    var onBannerAdLoaded: ((Context, ViewGroup) -> Unit)? = null

    /** Banner 加载 / 渲染失败，参数为（上下文，错误码，错误信息） */
    @Keep
    @JvmStatic
    var onBannerAdError: ((Context, Int, String) -> Unit)? = null

    /** 清空开屏、视频广告、插屏与 Banner 的全部回调，避免 Activity 销毁后 SDK 仍持有旧引用 */
    @Keep
    @JvmStatic
    fun clear() {
        onSplashAdLoaded = null
        onSplashAdFinished = null
        clearRewardVideoAd()
        clearInterstitialAd()
        clearBannerAd()
    }

    /** 仅清空视频广告（激励视频）的回调 */
    @Keep
    @JvmStatic
    fun clearRewardVideoAd() {
        onRewardVideoAdLoaded = null
        onRewardVideoAdShow = null
        onRewardVideoAdRewarded = null
        onRewardVideoAdClose = null
        onRewardVideoAdError = null
    }

    /** 仅清空插屏广告的回调 */
    @Keep
    @JvmStatic
    fun clearInterstitialAd() {
        onInterstitialAdLoaded = null
        onInterstitialAdShow = null
        onInterstitialAdClose = null
        onInterstitialAdError = null
    }

    /** 仅清空 Banner 广告的回调 */
    @Keep
    @JvmStatic
    fun clearBannerAd() {
        onBannerAdLoaded = null
        onBannerAdError = null
    }
}
