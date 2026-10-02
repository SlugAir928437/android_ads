package com.FreshingAir.Ad.Aggregation

import android.content.Context
import android.view.ViewGroup
import androidx.annotation.Keep
import bin.mt.annotations.MTProtector

/**
 * 本地广告 SDK 与 APK 页面之间的桥接。
 *
 * 本地广告 SDK（`com.FreshingAir.Ad.Aggregation.ads.*`）在广告生命周期节点上回调
 * [com.FreshingAir.Ad.Aggregation.SplashAdCallback] / [com.FreshingAir.Ad.Aggregation.RewardVideoAdCallback]
 * 的静态方法，再由本桥接转发给当前正在展示广告的页面。
 */
@Keep
@MTProtector
object LocalAdBridge {

    // ------------------------------ 开屏广告 ------------------------------

    /** 广告 View 渲染完成，参数为（上下文，广告 View） */
    @JvmStatic
    var onSplashAdLoaded: ((Context, ViewGroup) -> Unit)? = null

    /** 广告结束（关闭 / 加载失败），可以进入下一页 */
    @JvmStatic
    var onSplashAdFinished: ((Context) -> Unit)? = null

    // ------------------------------ 视频广告（激励视频） ------------------------------

    /** 视频广告加载成功，可以展示 */
    @JvmStatic
    var onRewardVideoAdLoaded: ((Context) -> Unit)? = null

    /** 视频广告开始播放 */
    @JvmStatic
    var onRewardVideoAdShow: ((Context) -> Unit)? = null

    /** 视频广告激励发放，参数为（上下文，激励名称，激励数量） */
    @JvmStatic
    var onRewardVideoAdRewarded: ((Context, String, Int) -> Unit)? = null

    /** 视频广告关闭 */
    @JvmStatic
    var onRewardVideoAdClose: ((Context) -> Unit)? = null

    /** 视频广告加载 / 播放失败，参数为（上下文，错误码，错误信息） */
    @JvmStatic
    var onRewardVideoAdError: ((Context, Int, String) -> Unit)? = null

    /** 清空开屏与视频广告的全部回调，避免 Activity 销毁后 SDK 仍持有旧引用 */
    @JvmStatic
    @MTProtector
    fun clear() {
        onSplashAdLoaded = null
        onSplashAdFinished = null
        clearRewardVideoAd()
    }

    /** 仅清空视频广告（激励视频）的回调 */
    @JvmStatic
    @MTProtector
    fun clearRewardVideoAd() {
        onRewardVideoAdLoaded = null
        onRewardVideoAdShow = null
        onRewardVideoAdRewarded = null
        onRewardVideoAdClose = null
        onRewardVideoAdError = null
    }
}
