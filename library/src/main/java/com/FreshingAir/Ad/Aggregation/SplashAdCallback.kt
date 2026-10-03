package com.FreshingAir.Ad.Aggregation

import android.content.Context
import android.view.ViewGroup
import androidx.annotation.Keep

/**
 * 本地广告 SDK 的广告回调统一出口。
 *
 * 各广告实现（穿山甲 / 广点通 / 华为 / 百度 / 快手 …）在开屏广告加载成功、
 * 关闭、失败或超时时调用这里，由使用方页面通过 [LocalAdBridge] 接管跳转。
 * 未注册回调时不做任何跳转。
 *
 * 入口类不做混淆/裁剪：类与成员均标注 `@Keep`（也可改用 proguard 规则保留）。
 */
@Keep
object SplashAdCallback {

    @Keep
    @JvmStatic
    fun onSplashAdLoaded(context: Context, view: ViewGroup) {
        LocalAdBridge.onSplashAdLoaded?.invoke(context, view)
    }

    @Keep
    @JvmStatic
    fun goToMainActivity(context: Context) {
        LocalAdBridge.onSplashAdFinished?.invoke(context)
    }
}
