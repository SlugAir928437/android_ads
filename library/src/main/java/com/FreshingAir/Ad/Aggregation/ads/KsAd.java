package com.FreshingAir.Ad.Aggregation.ads;

import androidx.annotation.Keep;

import android.util.Log;
import com.FreshingAir.Ad.Aggregation.BuildConfig;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;

import com.FreshingAir.Ad.Aggregation.AdPlatform;
import com.FreshingAir.Ad.Aggregation.Init;
import com.FreshingAir.Ad.Aggregation.RewardVideoAdCallback;
import com.FreshingAir.Ad.Aggregation.InterstitialAdCallback;
import com.FreshingAir.Ad.Aggregation.BannerAdCallback;
import com.kwad.sdk.api.KsAdSDK;
import com.kwad.sdk.api.KsFeedAd;
import com.kwad.sdk.api.KsInterstitialAd;
import com.kwad.sdk.api.KsBannerAd;
import com.kwad.sdk.api.KsRewardVideoAd;
import com.kwad.sdk.api.KsVideoPlayConfig;
import com.kwad.sdk.api.SdkConfig;
import com.kwad.sdk.api.KsLoadManager;
import com.kwad.sdk.api.KsScene;
import com.kwad.sdk.api.KsSplashScreenAd;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.FreshingAir.Ad.Aggregation.SplashAdCallback;

import java.util.List;
import java.util.Map;


public class KsAd {

    private static final String TAG = "快手广告 SDK";
    
    public static void initKSSDK(Context context,String appId,String appName) {
        KsAdSDK.init(context, new SdkConfig.Builder()
                .appId(appId) // 测试aapId，请联系快⼿平台申请正式AppId，必填
                .appName(appName)// 测试appName，请填写您应⽤的名称，⾮必填
                .showNotification(true) // 是否展示下载通知栏
                .debug(BuildConfig.DEBUG) // 是否开启sdk 调试⽇志  可选
                .build());
        KsAdSDK.start();
        Init.adSDKisLoaded.put(AdPlatform.KS, true);
    }
    
    public static void KSSplashAd(Context context,long posId,ViewGroup container){
        KsLoadManager adRequestManager = KsAdSDK.getLoadManager();
        KsScene scene = new KsScene
                //是否需要开屏⼩窗展示，默认为false, 设置false后将不会回调 onShowMiniWindow
                // .needShowMiniWindow(true)
                .Builder(posId).build(); // 此为测试posId，请联系快⼿平台申请正式posId
        if (adRequestManager != null) {
            adRequestManager.loadSplashScreenAd(scene, new
                    KsLoadManager.SplashScreenAdListener() {
                        @Override
                        public void onError(int code, String msg) {
                            Log.e(TAG ,"开屏⼴告请求失败，错误信息："+ code +"("+ msg+")");
                            SplashAdCallback.goToMainActivity(context);
                        }
                        
                        @Override
                        public void onRequestResult(int adNumber) {
                            Log.i(TAG, "开屏⼴告⼴告请求填充 " + adNumber);
                        }

                        @Override
                        public void onSplashScreenAdLoad(@Nullable KsSplashScreenAd splashScreenAd) {
                            //SplashAd.ksSplashScreenAd 为静态变量， 保存splashScreenAd⽤户⼩窗模式 SplashAd.ksSplashScreenAd = splashScreenAd;
                            //你可以选择View接⼊或者Frament接⼊ addFragment(KsSplashScreenAd splashScreenAd)
                            if(splashScreenAd != null) {
                                View view = splashScreenAd.getView(context, new KsSplashScreenAd.SplashScreenAdInteractionListener() {
                                            @Override
                                            
                                            public void onAdClicked() {
                                                Log.i(TAG, "开屏⼴告点击");
                                                //onAdClick 会吊起h5或者应⽤商店。 不直接跳转，等返回后再跳转。 mGotoMainActivity = true;
                                                //点击不出发显示miniWindow SplashAd.ksSplashScreenAd = null;
                                            }

                                            @Override
                                            
                                            public void onAdShowError(int code, String extra) {
                                                Log.i(TAG, "开屏⼴告显示错误 " + code + " extra " + extra);
                                                SplashAdCallback.goToMainActivity(context);
                                                //出错不出发显示miniWindown SplashAd.ksSplashScreenAd = null;
                                            }

                                            @Override
                                            
                                            public void onAdShowEnd() {
                                                Log.i(TAG, "开屏⼴告展示完毕");
                                                SplashAdCallback.goToMainActivity(context);
                                            }

                                            @Override
                                            public void onAdShowStart() {
                                                Log.i(TAG, "开屏⼴告开始展示");
                                                SplashAdCallback.onSplashAdLoaded(context, container);
                                            }

                                            @Override
                                            
                                            public void onSkippedAd() {
                                                Log.i(TAG, "⽤户跳过开屏⼴告");
                                                SplashAdCallback.goToMainActivity(context);
                                            }

                                            @Override
                                            public void onDownloadTipsDialogShow() {

                                            }

                                            @Override
                                            public void onDownloadTipsDialogDismiss() {

                                            }

                                            @Override
                                            public void onDownloadTipsDialogCancel() {

                                            }
                                        });
                                container.removeAllViews();
                                view.setLayoutParams(new
                                        ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                        ViewGroup.LayoutParams.MATCH_PARENT));
                                container.addView(view);
                            }
                        }
                    });
        }
    }
    
    public static void KsFeedAd(long posId, @NonNull ViewGroup adContainer){
        adContainer.removeAllViews();
        KsScene scene = new KsScene.Builder(posId)
                .adNum(1).build(); // 此为测试posId，请联系快⼿平台申请正式posId
        KsAdSDK.getLoadManager().loadConfigFeedAd(scene, new
                KsLoadManager.FeedAdListener() {
                    @Override
                    public void onError(int i, String s) {
                        Log.e("快手广告","广告加载失败，错误信息："+s+"("+i+")");
                    }

                    @Override
                    
                    public void onFeedAdLoad(@Nullable List<KsFeedAd> list) {
                        if(list != null) {
                            KsFeedAd ksFeedAd = list.get(0);
                            ksFeedAd.setAdInteractionListener(new KsFeedAd.AdInteractionListener() {
                                @Override
                                
                                public void onAdClicked() {

                                }

                                @Override
                                public void onAdShow() {

                                }

                                @Override
                                public void onDislikeClicked() {

                                }

                                @Override
                                public void onDownloadTipsDialogShow() {

                                }

                                @Override
                                public void onDownloadTipsDialogDismiss() {

                                }
                            });
                            ksFeedAd.render(new KsFeedAd.AdRenderListener() {
                                @Override
                                public void onAdRenderSuccess(View view) {
                                    adContainer.addView(view);
                                }

                                @Override
                                public void onAdRenderFailed(int i, String s) {
                                    Log.e("快手广告","广告渲染失败，错误信息："+s+"("+i+")");
                                }
                            });
                        }
                    }
                });
    }

    /**
     * 加载并展示快手激励视频广告。
     *
     * @param context 需要是 Activity（激励视频展示依赖 Activity）
     * @param posId   激励视频广告位 ID（long）
     */
    
    public static void KsRewardVideoAd(Context context, long posId){
        Activity activity = context instanceof Activity ? (Activity) context : null;
        if (activity == null) {
            RewardVideoAdCallback.onRewardAdError(context, -1, "激励视频展示需要 Activity 上下文");
            return;
        }
        KsLoadManager adRequestManager = KsAdSDK.getLoadManager();
        if (adRequestManager == null) {
            RewardVideoAdCallback.onRewardAdError(context, -1, "SDK 未初始化");
            return;
        }
        KsScene scene = new KsScene.Builder(posId).build();
        adRequestManager.loadRewardVideoAd(scene, new KsLoadManager.RewardVideoAdListener() {
            @Override
            public void onError(int i, String s) {
                Log.e(TAG, "激励视频加载失败，错误信息：" + s + "(" + i + ")");
                RewardVideoAdCallback.onRewardAdError(context, i, s);
            }

            @Override
            public void onRewardVideoResult(@Nullable List<KsRewardVideoAd> list) {
            }

            @Override
            
            public void onRewardVideoAdLoad(@Nullable List<KsRewardVideoAd> list) {
                if (list == null || list.isEmpty()) {
                    RewardVideoAdCallback.onRewardAdError(context, -1, "未获取到激励视频广告");
                    return;
                }
                KsRewardVideoAd rewardVideoAd = list.get(0);
                RewardVideoAdCallback.onRewardAdLoaded(context);
                rewardVideoAd.setRewardAdInteractionListener(new KsRewardVideoAd.RewardAdInteractionListener() {
                    @Override
                    public void onAdClicked() {
                    }

                    @Override
                    
                    public void onPageDismiss() {
                        Log.i(TAG, "激励视频关闭");
                        RewardVideoAdCallback.onRewardAdClose(context);
                    }

                    @Override
                    
                    public void onVideoPlayError(int code, int extra) {
                        Log.e(TAG, "激励视频播放失败，错误信息：" + extra + "(" + code + ")");
                        RewardVideoAdCallback.onRewardAdError(context, code, "视频播放错误:" + extra);
                    }

                    @Override
                    public void onVideoPlayEnd() {
                    }

                    @Override
                    public void onVideoSkipToEnd(long l) {
                    }

                    @Override
                    
                    public void onVideoPlayStart() {
                        Log.i(TAG, "激励视频开始播放");
                        RewardVideoAdCallback.onRewardAdShow(context);
                    }

                    @Override
                    public void onRewardVerify() {
                        // 兼容旧版回调，奖励以 onRewardVerify(Map) 为准，避免重复发放
                    }

                    @Override
                    
                    public void onRewardVerify(Map<String, Object> map) {
                        Log.i(TAG, "激励视频发放奖励");
                        RewardVideoAdCallback.onRewardAdRewarded(context, null, 0);
                    }

                    @Override
                    public void onRewardStepVerify(int i, int i1) {
                    }

                    @Override
                    public void onExtraRewardVerify(int i) {
                    }
                });
                KsVideoPlayConfig config = new KsVideoPlayConfig.Builder().videoSoundEnable(true).build();
                rewardVideoAd.showRewardVideoAd(activity, config);
            }
        });
    }

    /**
     * 快手插屏广告（long 广告位）。
     * 加载成功后回调 loaded，并自动调用 showInterstitialAd 展示。
     *
     * @param activity 展示插屏所需的 Activity
     * @param adId     插屏广告位 ID（long）
     */
    
    public static void KsInterstitialAd(Activity activity, long adId) {
        if (activity == null) {
            InterstitialAdCallback.onInterstitialAdError(null, -1, "插屏展示需要 Activity 上下文");
            return;
        }
        KsLoadManager adRequestManager = KsAdSDK.getLoadManager();
        if (adRequestManager == null) {
            InterstitialAdCallback.onInterstitialAdError(activity, -1, "SDK 未初始化");
            return;
        }
        KsScene scene = new KsScene.Builder(adId).build();
        adRequestManager.loadInterstitialAd(scene, new KsLoadManager.InterstitialAdListener() {
            @Override
            public void onError(int code, String msg) {
                Log.e(TAG, "插屏广告加载失败，错误信息：" + msg + "(" + code + ")");
                InterstitialAdCallback.onInterstitialAdError(activity, code, msg);
            }

            @Override
            public void onRequestResult(int adNumber) {
                Log.i(TAG, "插屏广告请求填充 " + adNumber);
            }

            @Override
            
            public void onInterstitialAdLoad(@Nullable List<KsInterstitialAd> list) {
                if (list == null || list.isEmpty()) {
                    InterstitialAdCallback.onInterstitialAdError(activity, -1, "未获取到插屏广告");
                    return;
                }
                KsInterstitialAd ad = list.get(0);
                ad.setAdInteractionListener(new KsInterstitialAd.AdInteractionListener() {
                    @Override
                    public void onAdClicked() {
                    }

                    @Override
                    
                    public void onAdShow() {
                        Log.i(TAG, "插屏广告展示");
                        InterstitialAdCallback.onInterstitialAdShow(activity);
                    }

                    @Override
                    
                    public void onAdClosed() {
                        Log.i(TAG, "插屏广告关闭");
                        InterstitialAdCallback.onInterstitialAdClose(activity);
                    }

                    @Override
                    
                    public void onPageDismiss() {
                        Log.i(TAG, "插屏广告页面关闭");
                        InterstitialAdCallback.onInterstitialAdClose(activity);
                    }

                    @Override
                    public void onVideoPlayError(int code, int extra) {
                        Log.e(TAG, "插屏广告播放失败，错误信息：" + extra + "(" + code + ")");
                        InterstitialAdCallback.onInterstitialAdError(activity, code, "视频播放错误:" + extra);
                    }

                    @Override
                    public void onVideoPlayEnd() {
                    }

                    @Override
                    public void onVideoPlayStart() {
                    }

                    @Override
                    public void onSkippedAd() {
                    }
                });
                InterstitialAdCallback.onInterstitialAdLoaded(activity);
                ad.showInterstitialAd(activity,
                        new KsVideoPlayConfig.Builder().videoSoundEnable(true).build());
            }
        });
    }

    /**
     * 快手插屏广告（String 广告位，内部转换为 long）。
     */
    
    public static void KsInterstitialAd(Activity activity, String adId) {
        if (activity == null) {
            InterstitialAdCallback.onInterstitialAdError(null, -1, "插屏展示需要 Activity 上下文");
            return;
        }
        if (adId == null || adId.isEmpty()) {
            InterstitialAdCallback.onInterstitialAdError(activity, -1, "广告位 ID 为空");
            return;
        }
        try {
            KsInterstitialAd(activity, Long.parseLong(adId));
        } catch (NumberFormatException e) {
            Log.e(TAG, "插屏广告位 ID 非法：" + adId);
            InterstitialAdCallback.onInterstitialAdError(activity, -1, "广告位 ID 非法：" + adId);
        }
    }

    /**
     * 快手 Banner 广告（long 广告位）。
     *
     * @param activity  展示 Banner 所需的 Activity
     * @param adId      Banner 广告位 ID（long）
     * @param container 承载 Banner 的容器
     */
    
    public static void KsBannerAd(Activity activity, long adId, ViewGroup container) {
        if (activity == null || container == null) {
            BannerAdCallback.onBannerAdError(activity, -1, "Banner 展示需要 Activity 与容器");
            return;
        }
        KsLoadManager adRequestManager = KsAdSDK.getLoadManager();
        if (adRequestManager == null) {
            BannerAdCallback.onBannerAdError(activity, -1, "SDK 未初始化");
            return;
        }
        KsScene scene = new KsScene.Builder(adId).build();
        adRequestManager.loadBannerAd(scene, new KsLoadManager.BannerAdListener() {
            @Override
            public void onError(int code, String msg) {
                Log.e(TAG, "Banner 广告加载失败，错误信息：" + msg + "(" + code + ")");
                BannerAdCallback.onBannerAdError(activity, code, msg);
            }

            @Override
            
            public void onBannerAdLoad(KsBannerAd ad) {
                if (ad == null) {
                    BannerAdCallback.onBannerAdError(activity, -1, "未获取到 Banner 广告");
                    return;
                }
                View view = ad.getView(activity, new KsBannerAd.BannerAdInteractionListener() {
                    @Override
                    public void onAdClicked() {
                    }

                    @Override
                    public void onAdShow() {
                    }

                    @Override
                    public void onAdClose() {
                    }

                    @Override
                    public void onAdShowError(int code, String msg) {
                        Log.e(TAG, "Banner 广告展示失败，错误信息：" + msg + "(" + code + ")");
                        BannerAdCallback.onBannerAdError(activity, code, msg);
                    }
                }, null);
                container.removeAllViews();
                container.addView(view);
                Log.i(TAG, "Banner 广告加载成功");
                BannerAdCallback.onBannerAdLoaded(activity, container);
            }
        });
    }

    /**
     * 快手 Banner 广告（String 广告位，内部转换为 long）。
     */
    
    public static void KsBannerAd(Activity activity, String adId, ViewGroup container) {
        if (activity == null || container == null) {
            BannerAdCallback.onBannerAdError(activity, -1, "Banner 展示需要 Activity 与容器");
            return;
        }
        if (adId == null || adId.isEmpty()) {
            BannerAdCallback.onBannerAdError(activity, -1, "广告位 ID 为空");
            return;
        }
        try {
            KsBannerAd(activity, Long.parseLong(adId), container);
        } catch (NumberFormatException e) {
            Log.e(TAG, "Banner 广告位 ID 非法：" + adId);
            BannerAdCallback.onBannerAdError(activity, -1, "广告位 ID 非法：" + adId);
        }
    }
}
