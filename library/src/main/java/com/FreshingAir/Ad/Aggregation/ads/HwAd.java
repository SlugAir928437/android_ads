package com.FreshingAir.Ad.Aggregation.ads;

import androidx.annotation.Keep;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

import com.FreshingAir.Ad.Aggregation.AdPlatform;
import com.FreshingAir.Ad.Aggregation.BannerAdCallback;
import com.FreshingAir.Ad.Aggregation.Init;
import com.FreshingAir.Ad.Aggregation.InterstitialAdCallback;
import com.FreshingAir.Ad.Aggregation.RewardVideoAdCallback;
import com.FreshingAir.Ad.Aggregation.SplashAdCallback;
import com.huawei.hms.ads.AdListener;
import com.huawei.hms.ads.AdParam;
import com.huawei.hms.ads.AudioFocusType;
import com.huawei.hms.ads.BannerAdSize;
import com.huawei.hms.ads.BiddingParam;
import com.huawei.hms.ads.HwAds;
import com.huawei.hms.ads.InterstitialAd;
import com.huawei.hms.ads.MediaMuteListener;
import com.huawei.hms.ads.banner.BannerView;
import com.huawei.hms.ads.instreamad.InstreamAd;
import com.huawei.hms.ads.instreamad.InstreamAdLoadListener;
import com.huawei.hms.ads.instreamad.InstreamAdLoader;
import com.huawei.hms.ads.instreamad.InstreamMediaStateListener;
import com.huawei.hms.ads.instreamad.InstreamView;
import com.huawei.hms.ads.nativead.NativeAdLoader;
import com.huawei.hms.ads.nativead.NativeView;
import com.huawei.hms.ads.reward.Reward;
import com.huawei.hms.ads.reward.RewardAd;
import com.huawei.hms.ads.reward.RewardAdLoadListener;
import com.huawei.hms.ads.reward.RewardAdStatusListener;
import com.huawei.hms.ads.splash.SplashAdDisplayListener;
import com.huawei.hms.ads.splash.SplashView;

import java.util.List;


public class HwAd {

    private static final String TAG = "华为广告 SDK";
    public static void InitHwSDK(Context context) {
        HwAds.init(context);
        Init.adSDKisLoaded.put(AdPlatform.HW, true);
    }
    public static void HwSplashAd(Context context, String slotId, @NonNull SplashView splashView) {
        // "testq6zq98hecj"为测试专用的广告位ID, App正式发布时需要改为正式的广告位ID
        AdParam adParam = new AdParam.Builder().build();

        SplashView.SplashAdLoadListener splashAdLoadListener = new SplashView.SplashAdLoadListener() {
            @Override
            public void onAdLoaded() {
                // 广告加载成功时调用
                Log.i(TAG, "广告加载成功");
            }

            @Override
            
            public void onAdFailedToLoad(int errorCode) {
                // 广告加载失败时调用, 跳转至App主界面
                Log.e(TAG, "广告加载失败，错误码：" + errorCode);
                SplashAdCallback.goToMainActivity(context);
            }

            @Override
            public void onAdDismissed() {
                Log.i(TAG, "广告关闭");
                // 广告展示完毕时调用, 跳转至App主界面
                SplashAdCallback.goToMainActivity(context);
            }
        };

        int orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;

        // 设置视频类开屏广告的音频焦点类型
        splashView.setAudioFocusType(AudioFocusType.NOT_GAIN_AUDIO_FOCUS_WHEN_MUTE);
        // 加载广告
        splashView.load(slotId, orientation, adParam, splashAdLoadListener);

        SplashAdDisplayListener adDisplayListener = new SplashAdDisplayListener() {
            @Override
            public void onAdShowed() {
                SplashAdCallback.onSplashAdLoaded(context, splashView);
                Log.i(TAG, "广告曝光");
                // 广告显示时调用
            }

            @Override
            
            public void onAdClick() {
                // 广告被点击时调用
                Log.i(TAG, "广告被点击");
            }
        };
        splashView.setAdDisplayListener(adDisplayListener);
    }
    public static boolean HwExSplashAd(Context context){
        Hw_ExSplashServiceConnection serviceConnection = new Hw_ExSplashServiceConnection(context);
        Intent intent = new Intent("com.huawei.hms.ads.EXSPLASH_SERVICE");
        intent.setPackage("com.huawei.hwid");
        boolean result = context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
        Log.i(TAG, "bindService result: " + result);
        return result;
    }
    /**
     * 兼容旧入口：统一转发到新的 Banner 契约方法。
     */
    
    public static void HwBannerAd(String adId, BannerView bannerView, FrameLayout adFrameLayout) {
        Context context = bannerView != null ? bannerView.getContext()
                : (adFrameLayout != null ? adFrameLayout.getContext() : null);
        Activity activity = context instanceof Activity ? (Activity) context : null;
        if (activity == null) {
            BannerAdCallback.onBannerAdError(context, -1, "Banner 展示需要 Activity 上下文");
            return;
        }
        HwBannerAd(activity, adId, adFrameLayout);
    }

    /**
     * 华为 Banner 广告（String 广告位）。
     * 先清空容器并加入横幅 View，加载成功后回调 onBannerAdLoaded，失败回调 onBannerAdError。
     *
     * @param activity  展示广告的页面
     * @param adId      Banner 广告位 ID
     * @param container Banner 容器，广告 View 会加入其中
     */
    
    public static void HwBannerAd(Activity activity, String adId, ViewGroup container) {
        if (activity == null) {
            BannerAdCallback.onBannerAdError(null, -1, "Banner 展示需要 Activity 上下文");
            return;
        }
        if (adId == null || adId.trim().isEmpty()) {
            BannerAdCallback.onBannerAdError(activity, -1, "广告位 ID 为空");
            return;
        }
        if (container == null) {
            BannerAdCallback.onBannerAdError(activity, -1, "Banner 容器为空");
            return;
        }

        // "testw6vs28auh3"为测试专用的广告位ID，App正式发布时需要改为正式的广告位ID
        final BannerView bannerView = new BannerView(activity);
        // 设置广告位ID和广告尺寸（中国大陆区域暂只支持 360*57 与 360*144）
        bannerView.setAdId(adId);
        bannerView.setBannerAdSize(BannerAdSize.BANNER_SIZE_360_57);
        // 设置轮播时间间隔为60秒
        bannerView.setBannerRefresh(60);
        container.removeAllViews();
        container.addView(bannerView);

        bannerView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // 广告加载成功时调用
                Log.i(TAG, "Banner 广告加载成功");
                BannerAdCallback.onBannerAdLoaded(activity, container);
            }

            @Override
            
            public void onAdFailed(int errorCode) {
                // 广告加载失败时调用
                Log.e(TAG, "Banner 广告加载失败，错误码：" + errorCode);
                bannerView.destroy();
                BannerAdCallback.onBannerAdError(activity, errorCode, null);
            }

            @Override
            public void onAdOpened() {
                // 广告打开时调用
            }

            @Override
            public void onAdClicked() {
                // 广告点击时调用
            }

            @Override
            public void onAdLeave() {
                // 广告离开应用时调用
            }

            @Override
            public void onAdClosed() {
                // 广告关闭时调用
                bannerView.destroy();
            }

            @Override
            public void onAdImpression() {
                // 广告曝光时调用
            }
        });

        // 创建广告请求，加载广告
        bannerView.loadAd(new AdParam.Builder().build());
    }

    /**
     * 华为 Banner 广告（long 广告位，内部转换为 String）。
     */
    
    public static void HwBannerAd(Activity activity, long adId, ViewGroup container) {
        HwBannerAd(activity, String.valueOf(adId), container);
    }
    
    public static void HwNativeAd(Context context, String adId) {
        // "testy63txaom86"为测试专用的广告位ID，App正式发布时需要改为正式的广告位ID
        NativeAdLoader.Builder builder = new NativeAdLoader.Builder(context, adId);
        builder.setNativeAdLoadedListener(nativeAd -> {
            // 广告加载成功后调用
        }).setAdListener(new AdListener() {
            @Override
            
            public void onAdFailed(int errorCode) {
                // 广告加载失败时调用
            }
        });
        //NativeAdLoader nativeAdLoader = builder.build();
    }
    public static void HwSDKrenderedAd(Context context, String AdId, NativeView adContainer) {
        // 实例化NativeAdLoader,传入广告位id列表
        NativeAdLoader.Builder builder = new NativeAdLoader.Builder(context, AdId);
        NativeAdLoader nativeAdLoader = builder.setNativeAdLoadedListener(nativeAd -> {
            if (adContainer != null) {
                adContainer.setNativeAd(nativeAd);
            }
        }).setAdListener(new AdListener() {
            @Override
            
            public void onAdLoaded() {
                Log.i(TAG, "load ad, success:");
            }

            @Override
            
            public void onAdFailed(int errorCode) {
                Log.e(TAG, "fail to load ad, errorCode is:" + errorCode);
            }
        }).build();

        // setSupportTemplate:当开发者完成适配后，需求设置为true才会返回SDK自渲染广告
        // 从云端获取广告
        nativeAdLoader.loadAd(new AdParam.Builder().setSupportTemplate(true).build());
    }
    /**
     * 兼容旧入口：统一转发到新的插屏契约方法。
     */
    
    public static void HwInterstitialAd(Context context, Activity activity, String slotId){
        HwInterstitialAd(activity, slotId);
    }

    /**
     * 华为插屏广告（String 广告位）。
     * 加载成功后回调 onInterstitialAdLoaded 并自动展示；
     * 展示回调 onInterstitialAdShow，关闭回调 onInterstitialAdClose，失败回调 onInterstitialAdError。
     *
     * @param activity 展示插屏所需的 Activity
     * @param slotId   插屏广告位 ID
     */
    
    public static void HwInterstitialAd(Activity activity, String slotId){
        if (activity == null) {
            InterstitialAdCallback.onInterstitialAdError(null, -1, "插屏展示需要 Activity 上下文");
            return;
        }
        if (slotId == null || slotId.trim().isEmpty()) {
            InterstitialAdCallback.onInterstitialAdError(activity, -1, "广告位 ID 为空");
            return;
        }
        final InterstitialAd interstitialAd = new InterstitialAd(activity);
        // "testb4znbuh3n2"为测试专用的广告位ID，App正式发布时需要改为正式的广告位ID
        interstitialAd.setAdId(slotId);
        interstitialAd.setAdListener(new AdListener(){
                                         @Override
                                         
                                         public void onAdLoaded() {
                                             // 广告加载成功时调用
                                             Log.i(TAG, "插屏广告加载成功");
                                             InterstitialAdCallback.onInterstitialAdLoaded(activity);
                                             // 加载成功后自动展示
                                             if (interstitialAd.isLoaded()) {
                                                 interstitialAd.show(activity);
                                             } else {
                                                 InterstitialAdCallback.onInterstitialAdError(activity, -1, "插屏广告尚未就绪");
                                             }
                                         }
                                         @Override
                                         
                                         public void onAdFailed(int errorCode) {
                                             // 广告加载失败时调用
                                             Log.e(TAG, "插屏广告加载失败，错误码：" + errorCode);
                                             InterstitialAdCallback.onInterstitialAdError(activity, errorCode, null);
                                         }
                                         @Override
                                         
                                         public void onAdClosed() {
                                             // 广告关闭时调用
                                             Log.i(TAG, "插屏广告关闭");
                                             InterstitialAdCallback.onInterstitialAdClose(activity);
                                         }
                                         @Override
                                         public void onAdClicked() {
                                             // 广告点击时调用
                                         }
                                         @Override
                                         
                                         public void onAdLeave() {
                                             // 广告离开时调用
                                         }
                                         @Override
                                         public void onAdOpened() {
                                             // 广告打开时调用
                                         }
                                         @Override
                                         public void onAdImpression() {
                                             // 广告曝光时调用
                                             Log.i(TAG, "插屏广告曝光");
                                             InterstitialAdCallback.onInterstitialAdShow(activity);
                                         }
                                     }
        );
        // 加载插屏广告
        interstitialAd.loadAd(new AdParam.Builder().build());
    }

    /**
     * 华为插屏广告（long 广告位，内部转换为 String）。
     */
    
    public static void HwInterstitialAd(Activity activity, long slotId){
        HwInterstitialAd(activity, String.valueOf(slotId));
    }
    
    public static void HwInstreamAd(Context context, String slotId, @NonNull InstreamView instreamView){
        // "testy3cglm3pj0"为测试专用的广告位ID，App正式发布时需要改为正式的广告位ID
        InstreamAdLoader.Builder builder = new InstreamAdLoader.Builder(context, slotId);
        // 设置贴片最大时长
        InstreamAdLoader adLoader = builder.setTotalDuration(15)
                // 设置贴片返回的最大数量
                .setMaxCount(1)
                .setInstreamAdLoadListener(new InstreamAdLoadListener() {
                    @Override
                    
                    public void onAdLoaded(List<InstreamAd> ads) {
                        // 广告加载成功后调用
                    }

                    @Override
                    public void onAdFailed(int errorCode) {
                        // 广告加载失败后调用
                    }
                }).build();
        AdParam.Builder paramBuilder = new AdParam.Builder();
        // 可选 设置实时bidding广告位参数
        BiddingParam biddingParam = new BiddingParam();
        paramBuilder.addBiddingParamMap(slotId, biddingParam);
        paramBuilder.setTMax(500);
//        builder.setCur();
        instreamView.setInstreamMediaChangeListener(ad -> {
            // 广告媒体切换
        });

        instreamView.setInstreamMediaStateListener(new InstreamMediaStateListener() {
            @Override
            
            public void onMediaProgress(int percent, int playTime) {
                // 播放过程
            }

            @Override
            public void onMediaStart(int playTime) {
                // 播放开始
            }

            @Override
            
            public void onMediaPause(int playTime) {
                // 播放暂停
            }

            @Override
            public void onMediaStop(int playTime) {
                // 播放停止
            }

            @Override
            
            public void onMediaCompletion(int playTime) {
                // 播放完成
            }

            @Override
            public void onMediaError(int playTime, int errorCode, int extra) {
                // 播放错误
            }
        });

        instreamView.setMediaMuteListener(new MediaMuteListener() {
            @Override
            public void onMute() {
                // 贴片广告静音
            }

            @Override
            public void onUnmute() {
                // 贴片广告取消静音
            }
        });
        adLoader.loadAd(new AdParam.Builder().build());
    }

    /**
     * 加载并展示华为激励视频广告。
     *
     * @param activity 展示广告的页面
     * @param slotId   激励视频广告位 ID
     */
    
    public static void HwRewardVideoAd(final Activity activity, String slotId) {
        final RewardAd rewardAd = new RewardAd(activity, slotId);
        rewardAd.loadAd(new AdParam.Builder().build(), new RewardAdLoadListener() {
            @Override
            
            public void onRewardAdFailedToLoad(int errorCode) {
                Log.e(TAG, "激励视频加载失败，错误码：" + errorCode);
                RewardVideoAdCallback.onRewardAdError(activity, errorCode, null);
            }

            @Override
            
            public void onRewardedLoaded() {
                Log.i(TAG, "激励视频加载成功");
                if (!rewardAd.isLoaded()) {
                    RewardVideoAdCallback.onRewardAdError(activity, -1, "激励视频尚未就绪");
                    return;
                }
                RewardVideoAdCallback.onRewardAdLoaded(activity);
                rewardAd.show(activity, new RewardAdStatusListener() {
                    @Override
                    
                    public void onRewardAdClosed() {
                        Log.i(TAG, "激励视频关闭");
                        RewardVideoAdCallback.onRewardAdClose(activity);
                    }

                    @Override
                    
                    public void onRewardAdFailedToShow(int errorCode) {
                        Log.e(TAG, "激励视频展示失败，错误码：" + errorCode);
                        RewardVideoAdCallback.onRewardAdError(activity, errorCode, null);
                    }

                    @Override
                    
                    public void onRewardAdOpened() {
                        Log.i(TAG, "激励视频开始播放");
                        RewardVideoAdCallback.onRewardAdShow(activity);
                    }

                    @Override
                    
                    public void onRewarded(Reward reward) {
                        Log.i(TAG, "激励视频发放奖励");
                        RewardVideoAdCallback.onRewardAdRewarded(activity,
                                reward != null ? reward.getName() : null,
                                reward != null ? reward.getAmount() : 0);
                    }
                });
            }
        });
    }
}