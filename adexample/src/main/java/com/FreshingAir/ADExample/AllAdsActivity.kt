package com.FreshingAir.ADExample

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.FreshingAir.Ad.Aggregation.AdPlatform
import com.FreshingAir.Ad.Aggregation.LocalAdBridge
import com.FreshingAir.Ad.Aggregation.loadAdByType
import com.FreshingAir.ADExample.databinding.ActivityAllAdsBinding
import com.huawei.hms.ads.splash.SplashView

/**
 * 全广告页：进入即自动加载「所有形式」的广告（开屏 / 信息流 / 插屏 / Banner / 视频广告）。
 *
 * 请求策略——**每个 SDK 轮流加载**：
 * 所有请求先进入一个队列（[buildLoadPlan]），由 [dispatchNext] 逐个派发，
 * 每派发一个请求后间隔 [LOAD_INTERVAL_MS] 再派发下一个，避免同一时刻向所有 SDK
 * 集中发起请求（请求过于频繁容易被平台限流、也会互相抢曝光）。
 * 每个 SDK 轮到自己时，会依次加载它支持的全部广告形式。
 *
 * 开屏广告为全屏广告，派发后会独占容器并阻塞队列（[splashBusy]），
 * 等它关闭 / 失败 / 超时后再继续下一个请求，避免前一条开屏被后一条覆盖。
 *
 * 页面只保留广告容器（见 `layout/activity_all_ads.xml`），回调时序同步输出到
 * Logcat（tag = AdExample）。运行时权限已由启动页申请，此处不再重复申请。
 */
class AllAdsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAllAdsBinding

    private val handler = Handler(Looper.getMainLooper())

    /** 待派发的请求队列：进入页面时一次性构建，之后按顺序轮流派发。 */
    private val queue = ArrayDeque<LoadStep>()

    /** 开屏广告是否正在展示（展示期间挂起队列，保证同一时刻只有一个请求在跑）。 */
    private var splashBusy = false

    private val dispatchRunnable = Runnable { dispatchNext() }

    /** 开屏广告长时间没有结束回调时的兜底，避免队列被永久挂起。 */
    private val splashTimeoutRunnable = Runnable {
        AdLog.append("开屏广告等待超时，继续下一个请求")
        finishSplash()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllAdsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        registerCallbacks()

        buildLoadPlan().forEach { queue.addLast(it) }
        AdLog.append("全广告页打开，共 ${queue.size} 个请求，按 SDK 轮流加载（间隔 ${LOAD_INTERVAL_MS}ms）")
        dispatchNext()
    }

    // ------------------------------ 回调注册 ------------------------------

    /** 注册开屏 / 插屏 / Banner / 视频广告回调；信息流广告由 SDK 直接渲染进容器，无回调。 */
    private fun registerCallbacks() {
        LocalAdBridge.onSplashAdLoaded = { _, adView -> attachSplashView(adView) }
        LocalAdBridge.onSplashAdFinished = { finishSplash() }

        LocalAdBridge.onBannerAdLoaded = { _, container ->
            AdLog.append("Banner 广告：渲染完成，容器子 View=${container.childCount}")
        }
        LocalAdBridge.onBannerAdError = { _, code, msg ->
            AdLog.append("Banner 广告：失败 code=$code msg=$msg")
        }

        LocalAdBridge.onInterstitialAdLoaded = { AdLog.append("插屏广告：加载成功") }
        LocalAdBridge.onInterstitialAdShow = { AdLog.append("插屏广告：开始展示") }
        LocalAdBridge.onInterstitialAdClose = { AdLog.append("插屏广告：关闭") }
        LocalAdBridge.onInterstitialAdError = { _, code, msg ->
            AdLog.append("插屏广告：失败 code=$code msg=$msg")
        }

        LocalAdBridge.onRewardVideoAdLoaded = { AdLog.append("视频广告：加载成功") }
        LocalAdBridge.onRewardVideoAdShow = { AdLog.append("视频广告：开始播放") }
        LocalAdBridge.onRewardVideoAdRewarded = { _, name, amount ->
            AdLog.append("视频广告：发放奖励 name=$name amount=$amount")
        }
        LocalAdBridge.onRewardVideoAdClose = { AdLog.append("视频广告：关闭") }
        LocalAdBridge.onRewardVideoAdError = { _, code, msg ->
            AdLog.append("视频广告：失败 code=$code msg=$msg")
        }
    }

    // ------------------------------ 请求队列 ------------------------------

    /**
     * 构建加载计划：外层遍历 SDK（[AdPlatform] 枚举顺序），内层按固定形式顺序，
     * 命中即入队。即「每个 SDK 轮流加载它支持的全部广告形式」。
     *
     * 是否支持某种形式以 [AdDemo] 中的平台清单为准，避免对未实现的平台发起无效请求。
     */
    private fun buildLoadPlan(): List<LoadStep> {
        val steps = ArrayList<LoadStep>()
        for (platform in AdPlatform.values()) {
            val supported = supportedFormats(platform)
            if (supported.isEmpty()) continue
            for (format in FORMAT_ORDER) {
                if (format in supported) steps.add(LoadStep(format, platform))
            }
        }
        return steps
    }

    /** 该 SDK 支持哪些广告形式。 */
    private fun supportedFormats(platform: AdPlatform): Set<AllAdFormat> = buildSet {
        if (AdDemo.splashPlatforms.any { it.platform == platform }) add(AllAdFormat.SPLASH)
        if (AdDemo.feedPlatforms.any { it.platform == platform }) add(AllAdFormat.FEED)
        if (AdDemo.interstitialPlatforms.any { it.platform == platform }) add(AllAdFormat.INTERSTITIAL)
        if (AdDemo.bannerPlatforms.any { it.platform == platform }) add(AllAdFormat.BANNER)
        if (AdDemo.rewardVideoPlatforms.any { it.platform == platform }) add(AllAdFormat.REWARD_VIDEO)
    }

    /** 派发下一个请求；若开屏广告正在展示或队列已空则不再继续。 */
    private fun dispatchNext() {
        if (isFinishing || isDestroyed) return
        if (splashBusy) {
            // 开屏广告还在展示：稍后再试，保证同一时刻只有一个请求在跑
            handler.postDelayed(dispatchRunnable, SPLASH_WAIT_MS)
            return
        }
        val step = queue.removeFirstOrNull()
        if (step == null) {
            AdLog.append("全部广告请求已派发完毕")
            return
        }
        runStep(step)
        handler.postDelayed(dispatchRunnable, LOAD_INTERVAL_MS)
    }

    /** 执行单个（广告形式 × 平台）请求。 */
    private fun runStep(step: LoadStep) {
        AdLog.append("【${step.platform.name}】请求${step.format.label}广告")
        try {
            when (step.format) {
                AllAdFormat.SPLASH -> loadSplash(step.platform)
                AllAdFormat.FEED -> {
                    binding.feedAdContainer.removeAllViews()
                    loadAdByType.loadFeedAd(this, binding.feedAdContainer, step.platform)
                }
                AllAdFormat.INTERSTITIAL -> loadAdByType.loadInterstitialAd(this, step.platform)
                AllAdFormat.BANNER -> {
                    binding.bannerAdContainer.removeAllViews()
                    loadAdByType.loadBannerAd(this, binding.bannerAdContainer, step.platform)
                }
                AllAdFormat.REWARD_VIDEO -> loadAdByType.loadRewardVideoAd(this, step.platform)
            }
        } catch (t: Throwable) {
            AdLog.append("【${step.platform.name}】${step.format.label}加载异常：${t.javaClass.simpleName} ${t.message}")
        }
    }

    // ------------------------------ 开屏广告 ------------------------------

    /**
     * 加载开屏广告。华为（HW）要求容器必须是 `SplashView`，这里临时 inflate
     * `layout/ad_splash_hw.xml` 加入容器；其余平台直接使用全屏覆盖容器。
     */
    private fun loadSplash(platform: AdPlatform) {
        val container = prepareSplashContainer(platform) ?: return
        markSplashBusy()
        loadAdByType.loadSplashAd(this, platform, container)
    }

    private fun prepareSplashContainer(platform: AdPlatform): ViewGroup? {
        binding.splashAdContainer.removeAllViews()
        binding.splashAdContainer.visibility = View.VISIBLE
        if (platform != AdPlatform.HW) return binding.splashAdContainer
        return try {
            val hwContainer = layoutInflater
                .inflate(R.layout.ad_splash_hw, binding.splashAdContainer, false) as SplashView
            binding.splashAdContainer.addView(
                hwContainer,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            hwContainer
        } catch (t: Throwable) {
            AdLog.append("创建华为 SplashView 容器失败：${t.message}")
            null
        }
    }

    /** 广告 View 渲染完成：显示覆盖层并挂载（华为回调回来的已在容器内，避免重复 addView）。 */
    private fun attachSplashView(adView: ViewGroup) {
        AdLog.append("开屏广告渲染完成：${adView.javaClass.simpleName}")
        binding.splashAdContainer.visibility = View.VISIBLE
        if (adView.parent != null) return
        binding.splashAdContainer.addView(
            adView,
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
    }

    /** 标记开屏开始展示，并设置兜底超时。 */
    private fun markSplashBusy() {
        splashBusy = true
        handler.removeCallbacks(splashTimeoutRunnable)
        handler.postDelayed(splashTimeoutRunnable, SPLASH_TIMEOUT_MS)
    }

    /** 开屏结束（关闭 / 失败 / 超时）：清空覆盖层并放行队列。 */
    private fun finishSplash() {
        handler.removeCallbacks(splashTimeoutRunnable)
        splashBusy = false
        binding.splashAdContainer.removeAllViews()
        binding.splashAdContainer.visibility = View.GONE
    }

    override fun onDestroy() {
        handler.removeCallbacks(dispatchRunnable)
        handler.removeCallbacks(splashTimeoutRunnable)
        // 页面销毁时清空回调，避免 SDK 继续持有已销毁页面的引用
        LocalAdBridge.clear()
        super.onDestroy()
    }

    /** 一次加载请求：某种广告形式 × 某个平台。 */
    private data class LoadStep(val format: AllAdFormat, val platform: AdPlatform)

    /** 本页涉及的广告形式。 */
    private enum class AllAdFormat(val label: String) {
        SPLASH("开屏"),
        FEED("信息流"),
        INTERSTITIAL("插屏"),
        BANNER("Banner"),
        REWARD_VIDEO("视频广告")
    }

    private companion object {
        /** 相邻两次请求的最小间隔，避免请求过于频繁。 */
        const val LOAD_INTERVAL_MS = 1_000L

        /** 开屏展示期间检查队列的轮询间隔。 */
        const val SPLASH_WAIT_MS = 500L

        /** 开屏广告最长等待时间，超过则强行放行队列。 */
        const val SPLASH_TIMEOUT_MS = 12_000L

        /** 同一 SDK 内的形式加载顺序。 */
        val FORMAT_ORDER = listOf(
            AllAdFormat.SPLASH,
            AllAdFormat.FEED,
            AllAdFormat.INTERSTITIAL,
            AllAdFormat.BANNER,
            AllAdFormat.REWARD_VIDEO
        )
    }
}
