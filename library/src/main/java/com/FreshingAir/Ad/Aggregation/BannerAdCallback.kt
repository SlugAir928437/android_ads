package com.FreshingAir.Ad.Aggregation

import android.content.Context
import android.view.ViewGroup
import androidx.annotation.Keep

/**
 * 本地广告 SDK 的 Banner（横幅）广告回调统一出口。
 *
 * 各广告实现在 Banner 渲染完成、把广告 View 加入使用方传入的容器后调用
 * [onBannerAdLoaded]；加载 / 渲染失败时调用 [onBannerAdError]。
 * 由使用方页面通过 [LocalAdBridge] 接管，未注册回调时不做任何处理。
 *
 * 入口类不做混淆/裁剪：类与成员均标注 `@Keep`（也可改用 proguard 规则保留）。
 */
@Keep
object BannerAdCallback {

    /**
     * Banner 渲染完成，参数为（上下文，已填充广告 View 的容器）。
     *
     * 广告 View 已由 SDK 自行加入容器，使用方一般无需再处理，通常在此时调整容器高度即可。
     */
    @Keep
    @JvmStatic
    fun onBannerAdLoaded(context: Context, container: ViewGroup) {
        LocalAdBridge.onBannerAdLoaded?.invoke(context, container)
    }

    /**
     * Banner 加载 / 渲染失败。
     *
     * @param errorCode 平台错误码，平台未提供时传 0
     * @param errorMsg  平台错误信息，平台未提供时传 null
     */
    @Keep
    @JvmStatic
    fun onBannerAdError(context: Context, errorCode: Int, errorMsg: String?) {
        LocalAdBridge.onBannerAdError?.invoke(context, errorCode, errorMsg ?: "")
    }
}
