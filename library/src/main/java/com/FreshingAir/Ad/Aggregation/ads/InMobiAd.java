package com.FreshingAir.Ad.Aggregation.ads;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;


import com.FreshingAir.Ad.Aggregation.AdPlatform;
import com.FreshingAir.Ad.Aggregation.BannerAdCallback;
import com.FreshingAir.Ad.Aggregation.Init;
import com.FreshingAir.Ad.Aggregation.InterstitialAdCallback;
import com.inmobi.ads.AdMetaInfo;
import com.inmobi.ads.InMobiAdRequestStatus;
import com.inmobi.ads.InMobiBanner;
import com.inmobi.ads.InMobiInterstitial;
import com.inmobi.ads.InMobiNative;
import com.inmobi.ads.exceptions.SdkNotInitializedException;
import com.inmobi.ads.listeners.BannerAdEventListener;
import com.inmobi.ads.listeners.InterstitialAdEventListener;
import com.inmobi.ads.listeners.NativeAdEventListener;
import com.inmobi.sdk.InMobiSdk;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * InMobi（海外）接入实现。
 *
 * 说明：InMobi Android SDK 只提供 Banner / 插屏 / 原生三种广告格式，没有开屏格式，
 * 因此本类只实现信息流（原生）加载，开屏由 {@code loadAdByType} 的默认分支直接进入主页。
 */
public class InMobiAd {

    /**
     * 初始化 InMobi SDK。
     *
     * @param accountId InMobi 后台的账号 ID（Account ID），为空时初始化会失败并回调
     */
    public static void InitInMobiSDK(Context context, String accountId) {
        JSONObject consent = new JSONObject();
        try {
            // InMobi 要求显式声明 GDPR 同意状态；无 GDPR 场景下传 false 即可
            consent.put(InMobiSdk.IM_GDPR_CONSENT_AVAILABLE, false);
        } catch (JSONException e) {
            Log.e("InMobiAd", "consent build failed:" + e.getMessage());
        }
        InMobiSdk.setLogLevel(InMobiSdk.LogLevel.DEBUG);
        InMobiSdk.init(context, accountId, consent, error -> {
            if (error == null) {
                Init.adSDKisLoaded.put(AdPlatform.INMOBI, true);
                Log.i("InMobiAd", "init success, version=" + InMobiSdk.getVersion());
            } else {
                // 账号 ID 为空时这里会返回 INVALID_ACCOUNT_ID，广告位无法拉取
                Log.e("InMobiAd", "init failed:" + error.getMessage());
            }
        });
    }

    private static InMobiNative inMobiNative;

    /**
     * 加载 InMobi 信息流（原生）广告并渲染进容器。
     *
     * @param placementId InMobi 后台创建的原生广告位 ID（long），必须大于 0
     */
    public static void InMobiFeedAd(@NonNull Activity activity, long placementId, @NonNull ViewGroup feedAdContainer) {
        if (placementId <= 0L) {
            Log.e("InMobiAd", "信息流广告位为空：InMobi 后台创建原生广告位后通过 loadFeedAd 的 feedAdId 传入");
            return;
        }
        try {
            inMobiNative = new InMobiNative(activity, placementId, new NativeAdEventListener() {

                @Override
                public void onAdLoadSucceeded(@NonNull InMobiNative ad, @NonNull AdMetaInfo info) {
                    super.onAdLoadSucceeded(ad, info);
                    Log.i("InMobiAd", "onAdLoadSucceeded, creativeId=" + info.getCreativeID());
                    View adView = ad.getPrimaryViewOfWidth(activity, null, feedAdContainer, feedAdContainer.getWidth());
                    if (adView != null) {
                        feedAdContainer.removeAllViews();
                        feedAdContainer.addView(adView);
                    }
                }

                @Override
                public void onAdLoadFailed(@NonNull InMobiNative ad, @NonNull InMobiAdRequestStatus status) {
                    super.onAdLoadFailed(ad, status);
                    Log.e("InMobiAd", "onAdLoadFailed:" + status.getStatusCode() + " / " + status.getMessage());
                }

                @Override
                public void onAdClicked(@NonNull InMobiNative ad) {
                    super.onAdClicked(ad);
                    Log.i("InMobiAd", "onAdClicked");
                }

                @Override
                public void onAdImpressed(@NonNull InMobiNative ad) {
                    super.onAdImpressed(ad);
                    Log.i("InMobiAd", "onAdImpressed");
                }
            });
            inMobiNative.load();
        } catch (SdkNotInitializedException e) {
            Log.e("InMobiAd", "SDK 未初始化:" + e.getMessage());
        }
    }

    /**
     * InMobi 插屏广告（long 广告位）。
     * 加载成功后回调 onInterstitialAdLoaded 并自动展示；
     * 展示回调 onInterstitialAdShow，关闭回调 onInterstitialAdClose，失败回调 onInterstitialAdError。
     *
     * @param activity    展示插屏所需的 Activity
     * @param placementId InMobi 后台创建的插屏广告位 ID（long），必须大于 0
     */
    
    public static void InMobiInterstitialAd(@NonNull Activity activity, long placementId) {
        if (activity == null) {
            InterstitialAdCallback.onInterstitialAdError(null, -1, "插屏展示需要 Activity 上下文");
            return;
        }
        if (placementId <= 0L) {
            Log.e("InMobiAd", "插屏广告位为空：InMobi 后台创建插屏广告位后通过 placementId 传入");
            InterstitialAdCallback.onInterstitialAdError(activity, -1, "插屏广告位为空");
            return;
        }
        try {
            final InMobiInterstitial interstitialAd = new InMobiInterstitial(activity, placementId,
                    new InterstitialAdEventListener() {

                        @Override
                        public void onAdLoadSucceeded(@NonNull InMobiInterstitial ad, @NonNull AdMetaInfo info) {
                            Log.i("InMobiAd", "插屏加载成功, creativeId=" + info.getCreativeID());
                            InterstitialAdCallback.onInterstitialAdLoaded(activity);
                            // 加载成功后自动展示
                            ad.show();
                        }

                        @Override
                        public void onAdLoadFailed(@NonNull InMobiInterstitial ad, @NonNull InMobiAdRequestStatus status) {
                            Log.e("InMobiAd", "插屏加载失败:" + status.getStatusCode() + " / " + status.getMessage());
                            InterstitialAdCallback.onInterstitialAdError(activity, -1, status.getMessage());
                        }

                        @Override
                        public void onAdDisplayed(@NonNull InMobiInterstitial ad) {
                            Log.i("InMobiAd", "插屏展示");
                            InterstitialAdCallback.onInterstitialAdShow(activity);
                        }

                        @Override
                        public void onAdDismissed(@NonNull InMobiInterstitial ad) {
                            Log.i("InMobiAd", "插屏关闭");
                            InterstitialAdCallback.onInterstitialAdClose(activity);
                        }

                        @Override
                        public void onAdClicked(@NonNull InMobiInterstitial ad, Map<Object, Object> map) {
                            Log.i("InMobiAd", "插屏点击");
                        }

                        @Override
                        public void onAdImpression(@NonNull InMobiInterstitial ad) {
                            Log.i("InMobiAd", "插屏曝光");
                        }

                        @Override
                        public void onAdDisplayFailed(@NonNull InMobiInterstitial ad) {
                            Log.e("InMobiAd", "插屏展示失败");
                            InterstitialAdCallback.onInterstitialAdError(activity, -1, "插屏展示失败");
                        }

                        @Override
                        public void onAdReceived(@NonNull InMobiInterstitial ad) {
                        }

                        @Override
                        public void onAdWillDisplay(@NonNull InMobiInterstitial ad) {
                        }

                        @Override
                        public void onUserLeftApplication(@NonNull InMobiInterstitial ad) {
                        }

                        @Override
                        public void onAdFetchFailed(@NonNull InMobiInterstitial ad, @NonNull InMobiAdRequestStatus status) {
                            Log.e("InMobiAd", "插屏获取失败:" + status.getStatusCode() + " / " + status.getMessage());
                            InterstitialAdCallback.onInterstitialAdError(activity, -1, status.getMessage());
                        }
                    });
            interstitialAd.load();
        } catch (SdkNotInitializedException e) {
            Log.e("InMobiAd", "SDK 未初始化:" + e.getMessage());
            InterstitialAdCallback.onInterstitialAdError(activity, -1, "SDK 未初始化");
        }
    }

    /**
     * InMobi 插屏广告（String 广告位，内部转换为 long）。
     */
    
    public static void InMobiInterstitialAd(@NonNull Activity activity, String adId) {
        if (adId == null || adId.trim().isEmpty()) {
            InterstitialAdCallback.onInterstitialAdError(activity, -1, "广告位 ID 为空");
            return;
        }
        long placementId;
        try {
            placementId = Long.parseLong(adId.trim());
        } catch (NumberFormatException e) {
            InterstitialAdCallback.onInterstitialAdError(activity, -1, "广告位 ID 非法：" + adId);
            return;
        }
        InMobiInterstitialAd(activity, placementId);
    }

    /**
     * InMobi Banner 广告（long 广告位）。
     * 先清空容器并加入 InMobiBanner，加载成功后回调 onBannerAdLoaded，失败回调 onBannerAdError。
     *
     * @param activity    展示广告的页面
     * @param placementId InMobi 后台创建的 Banner 广告位 ID（long），必须大于 0
     * @param container   Banner 容器，广告 View 会加入其中
     */
    
    public static void InMobiBannerAd(@NonNull Activity activity, long placementId, @NonNull ViewGroup container) {
        if (activity == null) {
            BannerAdCallback.onBannerAdError(null, -1, "Banner 展示需要 Activity 上下文");
            return;
        }
        if (container == null) {
            BannerAdCallback.onBannerAdError(activity, -1, "Banner 容器为空");
            return;
        }
        if (placementId <= 0L) {
            Log.e("InMobiAd", "Banner 广告位为空：InMobi 后台创建 Banner 广告位后通过 placementId 传入");
            BannerAdCallback.onBannerAdError(activity, -1, "Banner 广告位为空");
            return;
        }
        try {
            final InMobiBanner banner = new InMobiBanner(activity, placementId);
            banner.setBannerSize(320, 50);
            banner.setListener(new BannerAdEventListener() {

                @Override
                public void onAdLoadSucceeded(@NonNull InMobiBanner ad, @NonNull AdMetaInfo info) {
                    Log.i("InMobiAd", "Banner 加载成功, creativeId=" + info.getCreativeID());
                    BannerAdCallback.onBannerAdLoaded(activity, container);
                }

                @Override
                public void onAdLoadFailed(@NonNull InMobiBanner ad, @NonNull InMobiAdRequestStatus status) {
                    Log.e("InMobiAd", "Banner 加载失败:" + status.getStatusCode() + " / " + status.getMessage());
                    BannerAdCallback.onBannerAdError(activity, -1, status.getMessage());
                }

                @Override
                public void onAdFetchFailed(@NonNull InMobiBanner ad, @NonNull InMobiAdRequestStatus status) {
                    Log.e("InMobiAd", "Banner 获取失败:" + status.getStatusCode() + " / " + status.getMessage());
                    BannerAdCallback.onBannerAdError(activity, -1, status.getMessage());
                }

                @Override
                public void onAdDisplayed(@NonNull InMobiBanner ad) {
                }

                @Override
                public void onAdDismissed(@NonNull InMobiBanner ad) {
                }

                @Override
                public void onAdClicked(@NonNull InMobiBanner ad, Map<Object, Object> map) {
                }

                @Override
                public void onAdImpression(@NonNull InMobiBanner ad) {
                }

                @Override
                public void onUserLeftApplication(@NonNull InMobiBanner ad) {
                }

                @Override
                public void onRewardsUnlocked(@NonNull InMobiBanner ad, Map<Object, Object> map) {
                }
            });
            container.removeAllViews();
            container.addView(banner);
            banner.load();
        } catch (SdkNotInitializedException e) {
            Log.e("InMobiAd", "SDK 未初始化:" + e.getMessage());
            BannerAdCallback.onBannerAdError(activity, -1, "SDK 未初始化");
        }
    }

    /**
     * InMobi Banner 广告（String 广告位，内部转换为 long）。
     */
    
    public static void InMobiBannerAd(@NonNull Activity activity, String adId, @NonNull ViewGroup container) {
        if (adId == null || adId.trim().isEmpty()) {
            BannerAdCallback.onBannerAdError(activity, -1, "广告位 ID 为空");
            return;
        }
        long placementId;
        try {
            placementId = Long.parseLong(adId.trim());
        } catch (NumberFormatException e) {
            BannerAdCallback.onBannerAdError(activity, -1, "广告位 ID 非法：" + adId);
            return;
        }
        InMobiBannerAd(activity, placementId, container);
    }
}
