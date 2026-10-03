package com.FreshingAir.ADExample

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.FreshingAir.Ad.Aggregation.Init
import com.FreshingAir.Ad.Aggregation.LocalAdBridge
import com.FreshingAir.Ad.Aggregation.loadAdByType
import com.FreshingAir.ADExample.databinding.ActivityMainBinding

/**
 * 主页：选择平台后加载开屏 / 信息流 / 视频广告，并在下方日志区观察回调时序与 SDK 初始化状态。
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var splashOption: PlatformOption = AdDemo.splashPlatforms.first()
    private var feedOption: PlatformOption = AdDemo.feedPlatforms.first()
    private var rewardVideoOption: PlatformOption = AdDemo.rewardVideoPlatforms.first()
    private var interstitialOption: PlatformOption = AdDemo.interstitialPlatforms.first()
    private var bannerOption: PlatformOption = AdDemo.bannerPlatforms.first()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        bindSpinner(binding.spinnerSplashPlatform, AdDemo.splashPlatforms) { splashOption = it }
        bindSpinner(binding.spinnerFeedPlatform, AdDemo.feedPlatforms) { feedOption = it }
        bindSpinner(binding.spinnerRewardVideoPlatform, AdDemo.rewardVideoPlatforms) { rewardVideoOption = it }
        bindSpinner(binding.spinnerInterstitialPlatform, AdDemo.interstitialPlatforms) { interstitialOption = it }
        bindSpinner(binding.spinnerBannerPlatform, AdDemo.bannerPlatforms) { bannerOption = it }

        binding.btnSplashDemo.setOnClickListener { openSplashPage(splashOption) }
        binding.btnAllAds.setOnClickListener { openAllAdsPage() }
        binding.btnFeedLoad.setOnClickListener { loadFeedAd() }
        binding.btnFeedClear.setOnClickListener {
            binding.feedAdContainer.removeAllViews()
            AdLog.append("已清空信息流容器")
            refreshLog()
        }
        binding.btnRewardVideoLoad.setOnClickListener { loadRewardVideoAd() }
        binding.btnInterstitialLoad.setOnClickListener { loadInterstitialAd() }
        binding.btnBannerLoad.setOnClickListener { loadBannerAd() }
        binding.btnBannerClear.setOnClickListener {
            binding.bannerAdContainer.removeAllViews()
            AdLog.append("已清空 Banner 容器")
            refreshLog()
        }
        binding.btnLogClear.setOnClickListener {
            AdLog.clear()
            refreshLog()
        }

        registerRewardVideoCallbacks()
        registerInterstitialCallbacks()
        registerBannerCallbacks()
        refreshLog()
    }

    override fun onResume() {
        super.onResume()
        // 启动页返回时其 onDestroy 会晚于本页 onResume 执行，可能再次 clear()，
        // 因此这里只做一次注册，真正加载前还会再注册一次，保证回调有效
        registerRewardVideoCallbacks()
        registerInterstitialCallbacks()
        registerBannerCallbacks()
        // 从启动页返回时刷新，可以看到开屏广告的回调时序
        refreshLog()
    }

    /** 注册视频广告（激励视频）回调；广告结果统一转发到日志区。 */
    private fun registerRewardVideoCallbacks() {
        LocalAdBridge.onRewardVideoAdLoaded = { AdLog.append("视频广告：加载成功"); refreshLog() }
        LocalAdBridge.onRewardVideoAdShow = { AdLog.append("视频广告：开始播放"); refreshLog() }
        LocalAdBridge.onRewardVideoAdRewarded = { _, name, amount ->
            AdLog.append("视频广告：发放奖励 name=$name amount=$amount")
            refreshLog()
        }
        LocalAdBridge.onRewardVideoAdClose = { AdLog.append("视频广告：关闭"); refreshLog() }
        LocalAdBridge.onRewardVideoAdError = { _, code, msg ->
            AdLog.append("视频广告：失败 code=$code msg=$msg")
            refreshLog()
        }
    }

    /** 注册插屏广告回调；广告结果统一转发到日志区。 */
    private fun registerInterstitialCallbacks() {
        LocalAdBridge.onInterstitialAdLoaded = { AdLog.append("插屏广告：加载成功"); refreshLog() }
        LocalAdBridge.onInterstitialAdShow = { AdLog.append("插屏广告：开始展示"); refreshLog() }
        LocalAdBridge.onInterstitialAdClose = { AdLog.append("插屏广告：关闭"); refreshLog() }
        LocalAdBridge.onInterstitialAdError = { _, code, msg ->
            AdLog.append("插屏广告：失败 code=$code msg=$msg")
            refreshLog()
        }
    }

    /** 注册 Banner 广告回调；广告结果统一转发到日志区。 */
    private fun registerBannerCallbacks() {
        LocalAdBridge.onBannerAdLoaded = { _, container ->
            AdLog.append("Banner 广告：渲染完成，容器子 View=${container.childCount}")
            refreshLog()
        }
        LocalAdBridge.onBannerAdError = { _, code, msg ->
            AdLog.append("Banner 广告：失败 code=$code msg=$msg")
            refreshLog()
        }
    }

    /** 加载插屏广告：加载成功后由 SDK 自动全屏展示，回调结果见日志区。 */
    private fun loadInterstitialAd() {
        // 加载前重新注册，避免启动页 onDestroy 的 clear() 影响本次请求的回调
        registerInterstitialCallbacks()
        val option = interstitialOption
        AdLog.append("请求插屏广告：${option.label}（${option.platform}）")
        refreshLog()
        try {
            loadAdByType.loadInterstitialAd(this, option.platform)
        } catch (t: Throwable) {
            AdLog.append("插屏广告加载异常：${t.javaClass.simpleName} ${t.message}")
        }
        binding.root.postDelayed({ logInitState(); refreshLog() }, INIT_STATE_DELAY)
    }

    /** 加载 Banner 广告：广告 View 由 SDK 自行加入容器。 */
    private fun loadBannerAd() {
        registerBannerCallbacks()
        binding.bannerAdContainer.removeAllViews()
        val option = bannerOption
        AdLog.append("请求 Banner 广告：${option.label}（${option.platform}）")
        refreshLog()
        try {
            loadAdByType.loadBannerAd(this, binding.bannerAdContainer, option.platform)
        } catch (t: Throwable) {
            AdLog.append("Banner 广告加载异常：${t.javaClass.simpleName} ${t.message}")
        }
        binding.root.postDelayed({ logInitState(); refreshLog() }, INIT_STATE_DELAY)
    }

    /** 进入启动页并指定平台；启动页结束广告后会自动回到本页。 */
    private fun openSplashPage(option: PlatformOption) {
        AdLog.append("进入启动页：${option.label}（${option.platform}）")
        refreshLog()
        startActivity(
            Intent(this, SplashActivity::class.java)
                .putExtra(AdDemo.EXTRA_PLATFORM, option.platform.name)
        )
    }

    /** 进入全广告页：该页进入后会自动按 SDK 轮流加载所有形式的广告，页面只保留广告容器。 */
    private fun openAllAdsPage() {
        AdLog.append("进入全广告页：进入后自动轮流加载全部广告形式")
        refreshLog()
        startActivity(Intent(this, AllAdsActivity::class.java))
    }

    /** 加载信息流广告：广告 View 由 SDK 自行加入容器，示例只需提供容器。 */
    private fun loadFeedAd() {
        binding.feedAdContainer.removeAllViews()
        val option = feedOption
        AdLog.append("请求信息流广告：${option.label}（${option.platform}）")
        refreshLog()
        try {
            loadAdByType.loadFeedAd(this, binding.feedAdContainer, option.platform)
        } catch (t: Throwable) {
            AdLog.append("信息流加载异常：${t.javaClass.simpleName} ${t.message}")
        }
        // SDK 初始化与渲染均为异步，稍后刷新一次，便于看到平台初始化状态
        binding.root.postDelayed({ logInitState(); refreshLog() }, INIT_STATE_DELAY)
    }

    /** 加载视频广告（激励视频）：加载成功后由 SDK 自动全屏播放，回调结果见日志区。 */
    private fun loadRewardVideoAd() {
        // 加载前重新注册，避免启动页 onDestroy 的 clear() 影响本次请求的回调
        registerRewardVideoCallbacks()
        val option = rewardVideoOption
        AdLog.append("请求视频广告：${option.label}（${option.platform}）")
        refreshLog()
        try {
            loadAdByType.loadRewardVideoAd(this, option.platform)
        } catch (t: Throwable) {
            AdLog.append("视频广告加载异常：${t.javaClass.simpleName} ${t.message}")
        }
        // SDK 初始化与广告加载均为异步，稍后刷新一次，便于看到平台初始化状态
        binding.root.postDelayed({ logInitState(); refreshLog() }, INIT_STATE_DELAY)
    }

    /** 读取 Init 的初始化状态表，确认目标平台是否已经初始化。 */
    private fun logInitState() {
        val loaded = Init.adSDKisLoaded.filterValues { it }.keys.map { it.name }
        AdLog.append("已初始化平台：" + if (loaded.isEmpty()) "无" else loaded.joinToString("、"))
    }

    /** 刷新日志区并滚动到底部。 */
    private fun refreshLog() {
        val lines = AdLog.snapshot()
        binding.tvLog.text = if (lines.isEmpty()) "暂无日志" else lines.joinToString("\n")
        binding.logScroll.post { binding.logScroll.fullScroll(View.FOCUS_DOWN) }
    }

    private fun bindSpinner(
        spinner: Spinner,
        options: List<PlatformOption>,
        onSelected: (PlatformOption) -> Unit
    ) {
        spinner.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            options
        ).apply { setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) }
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                onSelected(options[position])
            }

            override fun onNothingSelected(parent: AdapterView<*>?) = Unit
        }
    }

    private companion object {
        const val INIT_STATE_DELAY = 1500L
    }
}
