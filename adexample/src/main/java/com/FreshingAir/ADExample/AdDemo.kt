package com.FreshingAir.ADExample

import android.Manifest
import android.os.Build
import com.FreshingAir.Ad.Aggregation.AdPlatform

/** 下拉框条目：平台枚举 + 中文名。 */
data class PlatformOption(val platform: AdPlatform, val label: String) {
    override fun toString(): String = "$label（$platform）"
}

/**
 * 示例用到的平台清单与常量。
 *
 * 可选范围与 [com.FreshingAir.Ad.Aggregation.loadAdByType] 的分支保持一致：
 * 开屏除 InMobi、新浪移动联盟外都支持（横屏只有米盟、Taptap）；
 * 信息流只有下列平台有实现；
 * 视频广告（激励视频）覆盖 15 家平台，见 [rewardVideoPlatforms]。
 */
object AdDemo {

    /** 启动页接收平台名的 Intent key。 */
    const val EXTRA_PLATFORM = "extra_platform"

    /** 默认平台：穿山甲。 */
    val DEFAULT_SPLASH: AdPlatform = AdPlatform.CSJ

    /** 支持开屏（竖屏）的平台。 */
    val splashPlatforms: List<PlatformOption> = listOf(
        PlatformOption(AdPlatform.CSJ, "穿山甲"),
        PlatformOption(AdPlatform.GDT, "广点通"),
        PlatformOption(AdPlatform.BAIDU, "百度"),
        PlatformOption(AdPlatform.KS, "快手"),
        PlatformOption(AdPlatform.SIGMOB, "Sigmob"),
        PlatformOption(AdPlatform.MIMO, "米盟"),
        PlatformOption(AdPlatform.MS, "美数"),
        PlatformOption(AdPlatform.OCTOPUS, "章鱼"),
        PlatformOption(AdPlatform.JD, "京东"),
        PlatformOption(AdPlatform.TAPTAP, "Taptap"),
        PlatformOption(AdPlatform.OSET, "OSET"),
        PlatformOption(AdPlatform.QIMING, "启明"),
        PlatformOption(AdPlatform.HW, "华为"),
        PlatformOption(AdPlatform.BEIZI, "倍孜"),
        PlatformOption(AdPlatform.ADMOB, "AdMob"),
        PlatformOption(AdPlatform.TANX, "阿里 Tanx"),
        PlatformOption(AdPlatform.UMENG, "友盟+ U-AppWin")
    )

    /** 支持信息流的平台（InMobi、新浪移动联盟只有信息流，没有开屏）。 */
    val feedPlatforms: List<PlatformOption> = listOf(
        PlatformOption(AdPlatform.CSJ, "穿山甲"),
        PlatformOption(AdPlatform.GDT, "广点通"),
        PlatformOption(AdPlatform.BAIDU, "百度"),
        PlatformOption(AdPlatform.KS, "快手"),
        PlatformOption(AdPlatform.SIGMOB, "Sigmob"),
        PlatformOption(AdPlatform.MS, "美数"),
        PlatformOption(AdPlatform.OCTOPUS, "章鱼"),
        PlatformOption(AdPlatform.TAPTAP, "Taptap"),
        PlatformOption(AdPlatform.INMOBI, "InMobi"),
        PlatformOption(AdPlatform.UMENG, "友盟+ U-AppWin"),
        PlatformOption(AdPlatform.SINA, "新浪移动联盟")
    )

    /** 支持视频广告（激励视频）的平台。 */
    val rewardVideoPlatforms: List<PlatformOption> = listOf(
        PlatformOption(AdPlatform.CSJ, "穿山甲"),
        PlatformOption(AdPlatform.GDT, "广点通"),
        PlatformOption(AdPlatform.BAIDU, "百度"),
        PlatformOption(AdPlatform.KS, "快手"),
        PlatformOption(AdPlatform.SIGMOB, "Sigmob"),
        PlatformOption(AdPlatform.MS, "美数"),
        PlatformOption(AdPlatform.OCTOPUS, "章鱼"),
        PlatformOption(AdPlatform.TAPTAP, "Taptap"),
        PlatformOption(AdPlatform.MIMO, "米盟"),
        PlatformOption(AdPlatform.OSET, "OSET"),
        PlatformOption(AdPlatform.QIMING, "启明"),
        PlatformOption(AdPlatform.BEIZI, "倍孜"),
        PlatformOption(AdPlatform.HW, "华为"),
        PlatformOption(AdPlatform.ADMOB, "AdMob"),
        PlatformOption(AdPlatform.UMENG, "友盟+ U-AppWin")
    )

    /**
     * 启动时需要申请的运行时权限（权限本身已由 library 清单合并，无需在示例清单中重复声明）。
     *
     * 缺少设备标识 / 位置信息会明显降低部分平台的填充率；Android 13 起还需申请通知权限。
     */
    fun runtimePermissions(): List<String> = buildList {
        add(Manifest.permission.READ_PHONE_STATE)
        add(Manifest.permission.ACCESS_FINE_LOCATION)
        add(Manifest.permission.ACCESS_COARSE_LOCATION)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            add(Manifest.permission.POST_NOTIFICATIONS)
        }
    }
}
