package com.FreshingAir.Ad.Aggregation.ads;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.FreshingAir.Ad.Aggregation.AdPlatform;
import com.FreshingAir.Ad.Aggregation.BannerAdCallback;
import com.FreshingAir.Ad.Aggregation.BuildConfig;
import com.FreshingAir.Ad.Aggregation.Init;
import com.FreshingAir.Ad.Aggregation.InterstitialAdCallback;
import com.FreshingAir.Ad.Aggregation.R;
import com.FreshingAir.Ad.Aggregation.RewardVideoAdCallback;
import com.FreshingAir.Ad.Aggregation.SplashAdCallback;
import com.FreshingAir.Ad.Aggregation.utils.Id;
import com.mcto.sspsdk.IQYNative;
import com.mcto.sspsdk.IQyBanner;
import com.mcto.sspsdk.IQyFullScreenAd;
import com.mcto.sspsdk.IQyRewardVideoAd;
import com.mcto.sspsdk.IQySplash;
import com.mcto.sspsdk.QyAdSlot;
import com.mcto.sspsdk.QyBannerStyle;
import com.mcto.sspsdk.QyClient;
import com.mcto.sspsdk.QyCustomMade;
import com.mcto.sspsdk.QySdk;
import com.mcto.sspsdk.QySdkConfig;

/**
 * 爱奇艺联盟广告（iQiYi iadsdk V2.5.001）接入实现。
 *
 * <p>SDK 采用「模板渲染」模式，包名为 {@code com.mcto.sspsdk}，同一套 {@code IQYNative}
 * 提供开屏（loadSplashAd）、信息流 / Banner（loadBannerAd）、插屏（loadFullScreenAd）
 * 与激励视频（loadRewardVideoAd）四种请求入口。
 *
 * <p>注意事项：
 * <ul>
 *   <li>{@link QySdk#init} 必须在主线程调用，否则会抛
 *       {@code Wrong Thread! Please init QySdk in main thread.}；</li>
 *   <li>初始化时的 {@code QyCustomMade#getOaid()} 为必传项，缺失会明显影响广告转化效果，
 *       使用方可通过 {@link #setOaid(String)} 注入自己采集到的 OAID；</li>
 *   <li>SDK 的 Banner / 激励视频交互回调运行在子线程，本实现统一切回主线程后再对外转发。</li>
 * </ul>
 */
public class QiYiAd {

    private static final String TAG = "QiYiAd";

    private static final Handler MAIN_HANDLER = new Handler(Looper.getMainLooper());

    private static QyClient sQyClient;

    /**
     * 初始化爱奇艺联盟 SDK。
     *
     * @param appId 爱奇艺联盟申请的应用 AppId，传 null / 空串时使用 {@link Id.QiYiId#APP_ID}
     * @param oaid  设备 OAID，传 null / 空串时使用 {@link Id.QiYiId#OAID}（均为空时将导致广告效果下降）
     */
    public static void InitQiYiSDK(Context context, @Nullable String appId, @Nullable String oaid) {
        if (Boolean.TRUE.equals(Init.adSDKisLoaded.get(AdPlatform.QIYI))) {
            return;
        }
        final String realAppId = isEmpty(appId) ? Id.QiYiId.APP_ID : appId.trim();
        if (!isEmpty(oaid)) {
            Id.QiYiId.OAID = oaid.trim();
        }
        try {
            QySdkConfig config = QySdkConfig.newAdConfig()
                    .appId(realAppId)
                    .appName(context.getString(R.string.app_name))
                    .debug(BuildConfig.DEBUG)
                    .qyCustomMade(new QiYiCustomMade(Id.QiYiId.OAID))
                    .build();
            // 同步初始化：务必在主线程调用
            sQyClient = QySdk.init(context, config);
            Init.adSDKisLoaded.put(AdPlatform.QIYI, true);
            Log.i(TAG, "爱奇艺联盟 SDK 初始化完成，版本 " + QySdk.SDK_VERSION);
        } catch (Throwable t) {
            Log.e(TAG, "爱奇艺联盟 SDK 初始化失败", t);
        }
    }

    /**
     * 更新 OAID。由于 OAID 为异步获取（MSA OAID SDK），使用方拿到后可在下一次请求前调用。
     */
    public static void setOaid(@Nullable String oaid) {
        Id.QiYiId.OAID = oaid == null ? "" : oaid.trim();
    }

    /**
     * 加载开屏广告。
     *
     * <p>加载成功后优先把广告 View 交给页面（{@link SplashAdCallback#onSplashAdLoaded}）；
     * 若广告 View 不是容器类型，则直接加入传入的 {@code splashContainer}。
     * 用户跳过 / 倒计时结束时回调 {@link SplashAdCallback#goToMainActivity}。
     *
     * @param codeId 爱奇艺后台的开屏广告位 ID
     */
    public static void QiYiSplashAd(@NonNull Context context, @Nullable String codeId,
                                    @NonNull ViewGroup splashContainer) {
        final IQYNative adNative = createAdNative(context);
        if (adNative == null) {
            SplashAdCallback.goToMainActivity(context);
            return;
        }
        QyAdSlot slot = QyAdSlot.newQySplashAdSlot()
                .codeId(codeId)
                .supportPreRequest(true)
                .timeout(3000)
                .setCountdownStyle(QyAdSlot.QY_COUNTDOWN_STYLE_PROGRESS)
                .build();

        adNative.loadSplashAd(slot, new IQYNative.SplashAdListener() {
            @Override
            public void onError(int code, String msg) {
                Log.i(TAG, "开屏广告请求失败 code=" + code + " msg=" + msg);
                SplashAdCallback.goToMainActivity(context);
            }

            @Override
            public void onTimeout() {
                Log.i(TAG, "开屏广告请求超时");
                SplashAdCallback.goToMainActivity(context);
            }

            @Override
            public void onSplashAdLoad(IQySplash splashAd) {
                if (splashAd == null || splashAd.getSplashView() == null) {
                    SplashAdCallback.goToMainActivity(context);
                    return;
                }
                View splashView = splashAd.getSplashView();
                // 建议先注册监听再展示，避免收不到曝光 / 点击事件
                splashAd.setSplashInteractionListener(new IQySplash.IAdInteractionListener() {
                    @Override
                    public void onAdClick() {
                        // 点击后由广告内容自行处理跳转，跳过 / 倒计时结束时再进入首页
                        Log.i(TAG, "开屏广告被点击");
                    }

                    @Override
                    public void onAdShow() {
                        Log.i(TAG, "开屏广告展示");
                    }

                    @Override
                    public void onAdSkip() {
                        SplashAdCallback.goToMainActivity(context);
                    }

                    @Override
                    public void onAdTimeOver() {
                        SplashAdCallback.goToMainActivity(context);
                    }
                });
                if (splashView instanceof ViewGroup) {
                    SplashAdCallback.onSplashAdLoaded(context, (ViewGroup) splashView);
                } else {
                    splashContainer.removeAllViews();
                    splashContainer.addView(splashView, new FrameLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT));
                }
            }
        });
    }

    /**
     * 加载信息流广告（模板渲染，整宽大图样式）。
     * 广告 View 由 SDK 自行加入传入的 {@code feedAdContainer}。
     *
     * @param codeId 爱奇艺后台的信息流广告位 ID
     */
    public static void QiYiFeedAd(@NonNull Context context, @Nullable String codeId,
                                  @NonNull ViewGroup feedAdContainer) {
        loadBannerLikeAd(context, codeId, QyBannerStyle.QYBANNER_FULL, feedAdContainer, false);
    }

    /**
     * 加载 Banner（横幅）广告（模板渲染，左图右文条带样式）。
     * 渲染完成后回调 {@link BannerAdCallback#onBannerAdLoaded}。
     *
     * @param codeId 爱奇艺后台的 Banner 广告位 ID
     */
    public static void QiYiBannerAd(@NonNull Context context, @Nullable String codeId,
                                    @NonNull ViewGroup bannerAdContainer) {
        loadBannerLikeAd(context, codeId, QyBannerStyle.QYBANNER_STRIP, bannerAdContainer, true);
    }

    /** 信息流与 Banner 共用同一套请求接口，仅模板样式与是否对外回调不同。 */
    private static void loadBannerLikeAd(@NonNull Context context, @Nullable String codeId,
                                         @NonNull QyBannerStyle style,
                                         @NonNull ViewGroup container, boolean notifyBannerCallback) {
        final IQYNative adNative = createAdNative(context);
        if (adNative == null) {
            if (notifyBannerCallback) {
                BannerAdCallback.onBannerAdError(context, -1, "爱奇艺联盟 SDK 未初始化");
            }
            return;
        }
        QyAdSlot slot = QyAdSlot.newQyBannerAdSlot()
                .codeId(codeId)
                .bannerStyle(style)
                .isMute(true)
                .build();

        adNative.loadBannerAd(slot, new IQYNative.BannerAdListener() {
            @Override
            public void onError(int code, String msg) {
                Log.i(TAG, "Banner/信息流广告请求失败 code=" + code + " msg=" + msg);
                if (notifyBannerCallback) {
                    BannerAdCallback.onBannerAdError(context, code, msg);
                }
            }

            @Override
            public void onBannerAdLoad(IQyBanner ad) {
                if (ad == null || ad.getBannerView() == null) {
                    if (notifyBannerCallback) {
                        BannerAdCallback.onBannerAdError(context, -1, "广告数据为空");
                    }
                    return;
                }
                // 注意：以下交互回调在子线程中，仅做日志，避免直接触碰 UI
                ad.setBannerInteractionListener(new IQyBanner.IAdInteractionListener() {
                    @Override
                    public void onAdShow() {
                        Log.i(TAG, "Banner 广告展示");
                    }

                    @Override
                    public void onAdClick() {
                        Log.i(TAG, "Banner 广告被点击");
                    }

                    @Override
                    public void onAdClose() {
                        Log.i(TAG, "Banner 广告关闭（负反馈）");
                    }

                    @Override
                    public void onRenderSuccess() {
                        Log.i(TAG, "Banner 广告渲染成功");
                    }

                    @Override
                    public void onAdStart() {
                    }

                    @Override
                    public void onAdStop() {
                    }

                    @Override
                    public void onAdComplete() {
                    }

                    @Override
                    public void onAdPlayError() {
                    }
                });
                container.removeAllViews();
                container.addView(ad.getBannerView(), new FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
                if (notifyBannerCallback) {
                    BannerAdCallback.onBannerAdLoaded(context, container);
                }
            }
        });
    }

    /**
     * 加载插屏广告。注册交互监听后由 SDK 立即全屏展示。
     *
     * @param codeId 爱奇艺后台的插屏广告位 ID
     */
    public static void QiYiInterstitialAd(@NonNull Activity activity, @Nullable String codeId) {
        final IQYNative adNative = createAdNative(activity);
        if (adNative == null) {
            InterstitialAdCallback.onInterstitialAdError(activity, -1, "爱奇艺联盟 SDK 未初始化");
            return;
        }
        QyAdSlot slot = QyAdSlot.newQyAdSlot()
                .codeId(codeId)
                .isMute(true)
                .build();

        adNative.loadFullScreenAd(slot, new IQYNative.FullScreenAdListener() {
            @Override
            public void onError(int code, String msg) {
                Log.i(TAG, "插屏广告请求失败 code=" + code + " msg=" + msg);
                InterstitialAdCallback.onInterstitialAdError(activity, code, msg);
            }

            @Override
            public void onFullScreenAdLoad(IQyFullScreenAd fullscreenAd) {
                if (fullscreenAd == null) {
                    InterstitialAdCallback.onInterstitialAdError(activity, -1, "广告数据为空");
                    return;
                }
                fullscreenAd.setAdInteractionListener(new IQyFullScreenAd.AdInteractionListener() {
                    @Override
                    public void onAdShow() {
                        InterstitialAdCallback.onInterstitialAdShow(activity);
                    }

                    @Override
                    public void onAdClick() {
                        Log.i(TAG, "插屏广告被点击");
                    }

                    @Override
                    public void onError(int code, String msg) {
                        Log.i(TAG, "插屏广告展示失败 code=" + code + " msg=" + msg);
                        InterstitialAdCallback.onInterstitialAdError(activity, code, msg);
                    }

                    @Override
                    public void onAdClose() {
                        InterstitialAdCallback.onInterstitialAdClose(activity);
                    }

                    @Override
                    public void onVideoComplete() {
                        Log.i(TAG, "插屏视频播放完成");
                    }
                });
                // 监听注册完毕后再展示（先通知加载成功，再全屏展示）
                InterstitialAdCallback.onInterstitialAdLoaded(activity);
                fullscreenAd.showAd(activity);
            }
        });
    }

    /**
     * 加载激励视频广告。注册交互监听后由 SDK 立即全屏播放。
     *
     * @param codeId 爱奇艺后台的激励视频广告位 ID
     */
    public static void QiYiRewardVideoAd(@NonNull Activity activity, @Nullable String codeId) {
        final IQYNative adNative = createAdNative(activity);
        if (adNative == null) {
            RewardVideoAdCallback.onRewardAdError(activity, -1, "爱奇艺联盟 SDK 未初始化");
            return;
        }
        QyAdSlot slot = QyAdSlot.newQyAwardAdSlot()
                .codeId(codeId)
                .rewardVideoAdOrientation(IQyRewardVideoAd.ORIENTATION_PORTRAIT)
                .isMute(true)
                .build();

        adNative.loadRewardVideoAd(slot, new IQYNative.RewardVideoAdListener() {
            @Override
            public void onError(int code, String msg) {
                Log.i(TAG, "激励视频请求失败 code=" + code + " msg=" + msg);
                RewardVideoAdCallback.onRewardAdError(activity, code, msg);
            }

            @Override
            public void onRewardVideoAdLoad(IQyRewardVideoAd rewardVideoAd) {
                if (rewardVideoAd == null) {
                    RewardVideoAdCallback.onRewardAdError(activity, -1, "广告数据为空");
                    return;
                }
                // 注意：以下交互回调在子线程中，统一切回主线程后再对外转发
                rewardVideoAd.setRewardVideoAdInteractionListener(new IQyRewardVideoAd.IAdInteractionListener() {
                    @Override
                    public void onAdShow() {
                        runOnMain(() -> RewardVideoAdCallback.onRewardAdShow(activity));
                    }

                    @Override
                    public void onAdClick() {
                        Log.i(TAG, "激励视频被点击");
                    }

                    @Override
                    public void onRewardVerify(java.util.HashMap<String, Object> result) {
                        // 仅在 SDK 完成发奖校验后回调，使用方在此下发奖励
                        runOnMain(() -> RewardVideoAdCallback.onRewardAdRewarded(activity, "", 0));
                    }

                    @Override
                    public void onAdClose() {
                        runOnMain(() -> RewardVideoAdCallback.onRewardAdClose(activity));
                    }

                    @Override
                    public void onVideoComplete() {
                        Log.i(TAG, "激励视频播放完成");
                    }

                    @Override
                    public void onVideoError(int errorCode, String msg) {
                        Log.i(TAG, "激励视频播放失败 code=" + errorCode + " msg=" + msg);
                        runOnMain(() -> RewardVideoAdCallback.onRewardAdError(activity, errorCode, msg));
                    }

                    @Override
                    public void onAdNextShow() {
                        Log.i(TAG, "激励视频：再看一条");
                    }
                });
                // 监听注册完毕后（框架在加载成功即自动展示），再对外通知已就绪
                RewardVideoAdCallback.onRewardAdLoaded(activity);
                rewardVideoAd.showRewardVideoAd(activity);
            }
        });
    }

    /** 基于已初始化的 QyClient 创建广告请求实例；未初始化时返回 null 并打日志。 */
    private static IQYNative createAdNative(Context context) {
        try {
            QyClient client = sQyClient != null ? sQyClient : QySdk.getAdClient();
            if (client == null) {
                Log.e(TAG, "爱奇艺联盟 SDK 尚未初始化，请先调用 initSDKByAdPlatform");
                return null;
            }
            return client.createAdNative(context);
        } catch (Throwable t) {
            Log.e(TAG, "创建爱奇艺广告请求实例失败", t);
            return null;
        }
    }

    private static void runOnMain(Runnable action) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            action.run();
        } else {
            MAIN_HANDLER.post(action);
        }
    }

    private static boolean isEmpty(@Nullable String value) {
        return value == null || value.trim().isEmpty();
    }

    /**
     * 爱奇艺隐私配置：仅暴露 OAID（必填）与已安装应用列表开关，
     * 其余设备标识沿用 SDK 默认行为，避免重复采集。
     */
    private static class QiYiCustomMade extends QyCustomMade {

        private final String mOaid;

        QiYiCustomMade(@Nullable String oaid) {
            mOaid = oaid == null ? "" : oaid;
        }

        @Override
        public String getOaid() {
            return mOaid;
        }
    }
}
