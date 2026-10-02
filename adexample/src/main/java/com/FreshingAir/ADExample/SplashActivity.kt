package com.FreshingAir.ADExample

import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.FreshingAir.Ad.Aggregation.AdPlatform
import com.FreshingAir.Ad.Aggregation.LocalAdBridge
import com.FreshingAir.Ad.Aggregation.loadAdByType
import com.FreshingAir.ADExample.databinding.ActivitySplashBinding
import com.huawei.hms.ads.splash.SplashView

/**
 * 启动页（launcher）：申请运行时权限 → 加载开屏广告 → 广告结束（关闭 / 失败 / 超时）后进入主页。
 *
 * 页面只做两件事：把 SDK 回调回来的广告 View 挂到容器上，以及在广告结束时跳转。
 * 全部回调都经由 [LocalAdBridge]，因此这里注册一次即可。
 */
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private lateinit var platform: AdPlatform

    /** 防止“广告结束”与“用户跳过/超时”重复触发跳转。 */
    private var leftSplash = false

    /** 兜底：广告长时间没有回调时也要进入主页，避免卡在启动页。 */
    private val timeoutRunnable = Runnable { leaveSplash("等待广告超时") }

    private val permissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result ->
            val granted = result.values.count { it }
            AdLog.append("运行时权限申请完成：$granted/${result.size} 已授予（未授予也会继续加载广告）")
            loadSplashAd()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        platform = resolvePlatform(intent.getStringExtra(AdDemo.EXTRA_PLATFORM))
        binding.tvPlatform.text = getString(R.string.splash_platform, platform.name)
        binding.tvSkip.setOnClickListener { leaveSplash("用户点击跳过") }

        // 注册桥接回调：onSplashAdLoaded 拿广告 View，onSplashAdFinished 表示广告结束
        LocalAdBridge.onSplashAdLoaded = { _, adView -> attachAdView(adView) }
        LocalAdBridge.onSplashAdFinished = { leaveSplash("广告结束回调") }

        AdLog.append("启动页打开，目标平台：${platform.name}")
        requestPermissionsThenLoad()
    }

    /** 平台名以枚举名传递，非法值回退到默认平台。 */
    private fun resolvePlatform(name: String?): AdPlatform {
        if (name.isNullOrEmpty()) return AdDemo.DEFAULT_SPLASH
        // 注意：loadAdByType.getAdPlatform(String) 未覆盖 TANX，示例统一用枚举名解析
        return runCatching { AdPlatform.valueOf(name) }.getOrDefault(AdDemo.DEFAULT_SPLASH)
    }

    /** 先申请运行时权限，无论授予与否都继续加载广告。 */
    private fun requestPermissionsThenLoad() {
        val missing = AdDemo.runtimePermissions().filter {
            ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
        }
        if (missing.isEmpty()) {
            AdLog.append("运行时权限已全部授予")
            loadSplashAd()
            return
        }
        AdLog.append("申请运行时权限：" + missing.joinToString("、") { it.substringAfterLast('.') })
        permissionLauncher.launch(missing.toTypedArray())
    }

    private fun loadSplashAd() {
        val container = resolveContainer() ?: return leaveSplash("广告容器创建失败")
        AdLog.append("请求开屏广告：${platform.name}（${orientationName()}）")
        binding.root.postDelayed(timeoutRunnable, SPLASH_TIMEOUT)
        try {
            // 容器为普通 ViewGroup；若平台未实现开屏（或方向不支持），SDK 会直接回调 onSplashAdFinished
            loadAdByType.loadSplashAd(this, platform, container)
        } catch (t: Throwable) {
            AdLog.append("开屏加载异常：${t.javaClass.simpleName} ${t.message}")
            leaveSplash("加载异常")
        }
    }

    /**
     * 华为的开屏容器必须是 SplashView，且只能由布局文件声明，
     * 因此这里单独 inflate `layout/ad_splash_hw.xml` 后加入容器；其余平台直接使用根布局。
     */
    private fun resolveContainer(): ViewGroup? {
        if (platform != AdPlatform.HW) return binding.adContainer
        return try {
            val hwContainer = layoutInflater
                .inflate(R.layout.ad_splash_hw, binding.adContainer, false) as SplashView
            binding.adContainer.addView(
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

    /** 广告 View 渲染完成：隐藏占位、取消超时，并挂到容器上。 */
    private fun attachAdView(adView: ViewGroup) {
        AdLog.append("开屏广告渲染完成：${adView.javaClass.simpleName}，挂载到容器")
        binding.loadingPanel.visibility = View.GONE
        binding.root.removeCallbacks(timeoutRunnable)
        // 华为平台回调回来的就是已经加入容器的 SplashView，重复 addView 会抛异常
        if (adView.parent != null) return
        binding.adContainer.addView(
            adView,
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
    }

    private fun leaveSplash(reason: String) {
        if (leftSplash || isFinishing || isDestroyed) return
        leftSplash = true
        AdLog.append("开屏结束（$reason），进入主页")
        binding.root.removeCallbacks(timeoutRunnable)
        // CLEAR_TOP + SINGLE_TOP：主页已存在时复用，避免栈里堆多个 MainActivity
        startActivity(
            Intent(this, MainActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        )
        finish()
    }

    private fun orientationName(): String =
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) "横屏" else "竖屏"

    override fun onDestroy() {
        binding.root.removeCallbacks(timeoutRunnable)
        // 页面销毁时清空回调，避免 SDK 继续持有已销毁页面的引用
        LocalAdBridge.clear()
        super.onDestroy()
    }

    private companion object {
        const val SPLASH_TIMEOUT = 12_000L
    }
}
