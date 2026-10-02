package com.FreshingAir.Ad.Aggregation

import android.content.Context
import androidx.annotation.Keep
import bin.mt.annotations.MTProtector

/**
 * 本地广告 SDK 的激励视频（视频广告）回调统一出口。
 *
 * 各广告实现（穿山甲 / 广点通 / 百度 / 快手 / 快手 / 华为 / AdMob …）在激励视频加载成功、
 * 开始播放、发放奖励、关闭或失败时调用这里，由使用方页面通过 [LocalAdBridge] 接管。
 * 未注册回调时不做任何处理。
 */
@Keep
@MTProtector
object RewardVideoAdCallback {

    /** 广告加载成功，可以展示（多数平台在加载成功后会立即展示）。 */
    @JvmStatic
    @MTProtector
    fun onRewardAdLoaded(context: Context) {
        LocalAdBridge.onRewardVideoAdLoaded?.invoke(context)
    }

    /** 广告开始播放。 */
    @JvmStatic
    @MTProtector
    fun onRewardAdShow(context: Context) {
        LocalAdBridge.onRewardVideoAdShow?.invoke(context)
    }

    /**
     * 激励发放回调。
     *
     * @param rewardName   激励名称，平台未提供时传 null
     * @param rewardAmount 激励数量，平台未提供时传 0
     */
    @JvmStatic
    @MTProtector
    fun onRewardAdRewarded(context: Context, rewardName: String?, rewardAmount: Int) {
        LocalAdBridge.onRewardVideoAdRewarded?.invoke(context, rewardName ?: "", rewardAmount)
    }

    /** 广告关闭（无论是完整观看后关闭，还是中途关闭）。 */
    @JvmStatic
    @MTProtector
    fun onRewardAdClose(context: Context) {
        LocalAdBridge.onRewardVideoAdClose?.invoke(context)
    }

    /**
     * 广告加载或播放失败。
     *
     * @param errorCode 平台错误码，平台未提供时传 0
     * @param errorMsg  平台错误信息，平台未提供时传 null
     */
    @JvmStatic
    @MTProtector
    fun onRewardAdError(context: Context, errorCode: Int, errorMsg: String?) {
        LocalAdBridge.onRewardVideoAdError?.invoke(context, errorCode, errorMsg ?: "")
    }
}
