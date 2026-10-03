package com.FreshingAir.Ad.Aggregation.ads;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

import android.util.Log;
import android.app.Application;
import android.app.Activity;
import android.os.Bundle;

import android.view.View;
import android.view.ViewGroup;

import com.FreshingAir.Ad.Aggregation.AdPlatform;
import com.FreshingAir.Ad.Aggregation.BannerAdCallback;
import com.FreshingAir.Ad.Aggregation.Init;
import com.FreshingAir.Ad.Aggregation.InterstitialAdCallback;
import com.FreshingAir.Ad.Aggregation.RewardVideoAdCallback;
import com.kc.aw.lib.PtgAdSdk;
import com.kc.aw.lib.interf.PtgRewardVideoAd;
import com.kc.aw.lib.model.AdError;
import com.kc.aw.lib.provider.PtgAdNative;
import com.kc.openset.config.OSETSDK;
import com.kc.openset.listener.OSETInitListener;
import com.kc.openset.ad.banner.OSETBanner;
import com.kc.openset.ad.banner.OSETBannerAd;
import com.kc.openset.ad.intestitial.OSETInterstitial;
import com.kc.openset.ad.intestitial.OSETInterstitialAd;
import com.kc.openset.ad.listener.OSETBannerAdLoadListener;
import com.kc.openset.ad.listener.OSETBannerListener;
import com.kc.openset.ad.listener.OSETInterstitialAdLoadListener;
import com.kc.openset.ad.listener.OSETInterstitialListener;
import com.kc.openset.ad.listener.OSETSplashAdLoadListener;
import com.kc.openset.ad.listener.OSETSplashListener;
import com.kc.openset.ad.splash.OSETSplash;
import com.kc.openset.ad.splash.OSETSplashAd;
import com.FreshingAir.Ad.Aggregation.SplashAdCallback;

public class OSETAd {
    private static final String TAG = "OSET广告 SDK";
    public static void initOSETSDK(Application application, String APPKEY){
        OSETSDK.getInstance()
//                .setCustomController(new OSETCustomController(){})
                .init(application, APPKEY, new OSETInitListener(){
                    @Override
                    
                    public void onError(String s) {
                        Log.e(TAG,"SDK初始化失败，错误信息："+s);
                    }

                    @Override
                    public void onSuccess() {
                        Log.i(TAG,"SDK初始化成功");
                        Init.adSDKisLoaded.put(AdPlatform.OSET, true);
                    }
                });
    }
    
    
    public static void OSETSplashAd(Activity activity,String PosId,ViewGroup adContainer) {
        OSETSplashAdLoadListener osetSplashAdLoadListener ;
        osetSplashAdLoadListener = new OSETSplashAdLoadListener() {

            @Override
            public void onLoadFail(String s, String s1) {
                Log.e(TAG,"广告加载失败，错误信息："+s1+"("+s+")");
                SplashAdCallback.goToMainActivity(activity.getApplicationContext());
            }

            @Override
            
            public void onLoadSuccess(OSETSplashAd osetSplashAd) {
                osetSplashAd.showAd(activity, adContainer, new OSETSplashListener(){

                    @Override
                    public void onError(String s, String s1) {
                        Log.e(TAG,"广告加载失败，错误信息："+s1+"("+s+")");
                        SplashAdCallback.goToMainActivity(adContainer.getContext());
                    }

                    @Override
                    public void onAdDetailViewClosed() {
                        SplashAdCallback.goToMainActivity(adContainer.getContext());
                    }

                    @Override
                    public void onClick() {
                        Log.i(TAG,"广告被点击");
                    }

                    @Override
                    
                    public void onClose() {
                        Log.i(TAG,"广告关闭");
                        SplashAdCallback.goToMainActivity(adContainer.getContext());
                    }

                    @Override
                    
                    public void onShow() {
                        Log.i(TAG,"广告展示");
                        SplashAdCallback.onSplashAdLoaded(activity.getApplicationContext(), adContainer);
                    }
                });
            }

        };
        OSETSplash.getInstance()
                .setContext(activity)
                .setPosId(PosId)
                .loadAd(osetSplashAdLoadListener);

    }

    /**
     * 加载并展示 OSET 激励视频广告。
     *
     * @param activity 展示广告的页面
     * @param PosId    激励视频广告位 ID
     */
    
    public static void OSETRewardVideoAd(Activity activity, String PosId) {
        PtgAdNative adNative = PtgAdSdk.get();
        if (adNative == null) {
            RewardVideoAdCallback.onRewardAdError(activity, -1, "SDK 未初始化");
            return;
        }
        com.kc.aw.lib.model.AdSlot adSlot = new com.kc.aw.lib.model.AdSlot.Builder()
                .setCodeId(PosId)
                .build();
        adNative.loadRewardVideoAd(activity, adSlot, new PtgAdNative.RewardVideoAdListener() {
            @Override
            
            public void onRewardVideoAdLoad(PtgRewardVideoAd ad) {
                Log.i(TAG, "激励视频加载成功");
                RewardVideoAdCallback.onRewardAdLoaded(activity);
                ad.setRewardAdInteractionListener(new PtgRewardVideoAd.RewardAdInteractionListener() {
                    @Override
                    
                    public void onAdClose() {
                        Log.i(TAG, "激励视频关闭");
                        RewardVideoAdCallback.onRewardAdClose(activity);
                    }

                    @Override
                    
                    public void onAdShow() {
                        Log.i(TAG, "激励视频开始播放");
                        RewardVideoAdCallback.onRewardAdShow(activity);
                    }

                    @Override
                    public void onAdVideoBarClick() {
                    }

                    @Override
                    
                    public void onRenderError(AdError adError) {
                        int code = adError != null ? adError.getErrorCode() : 0;
                        String msg = adError != null ? adError.getMessage() : null;
                        Log.e(TAG, "激励视频失败，错误信息：" + msg + "(" + code + ")");
                        RewardVideoAdCallback.onRewardAdError(activity, code, msg);
                    }

                    @Override
                    
                    public void onRewardVerify(boolean rewardVerify, Bundle bundle) {
                        if (rewardVerify) {
                            Log.i(TAG, "激励视频发放奖励");
                            RewardVideoAdCallback.onRewardAdRewarded(activity, null, 0);
                        }
                    }

                    @Override
                    public void onSkippedVideo() {
                    }

                    @Override
                    public void onVideoComplete() {
                    }

                    @Override
                    
                    public void onVideoError(int code, String msg) {
                        Log.e(TAG, "激励视频播放失败，错误信息：" + msg + "(" + code + ")");
                        RewardVideoAdCallback.onRewardAdError(activity, code, msg);
                    }

                    @Override
                    public void onVideoPause() {
                    }

                    @Override
                    public void onVideoProgressUpdate(long l, long l1) {
                    }

                    @Override
                    public void onVideoResume() {
                    }

                    @Override
                    public void onVideoStart() {
                    }
                });
                ad.showRewardVideoAd(activity);
            }

            @Override
            
            public void onError(AdError adError) {
                int code = adError != null ? adError.getErrorCode() : 0;
                String msg = adError != null ? adError.getMessage() : null;
                Log.e(TAG, "激励视频加载失败，错误信息：" + msg + "(" + code + ")");
                RewardVideoAdCallback.onRewardAdError(activity, code, msg);
            }
        });
    }

    /**
     * 加载并展示 OSET 插屏广告（加载成功后自动展示）。
     *
     * @param activity 展示广告的页面
     * @param PosId    插屏广告位 ID
     */
    
    public static void OSETInterstitialAd(Activity activity, String PosId) {
        if (activity == null || PosId == null || PosId.trim().isEmpty()) {
            InterstitialAdCallback.onInterstitialAdError(activity, -1, "参数非法（Activity/广告位不能为空）");
            return;
        }
        OSETInterstitial.getInstance()
                .setContext(activity)
                .setPosId(PosId)
                .loadAd(new OSETInterstitialAdLoadListener() {
                    @Override
                    public void onLoadSuccess(OSETInterstitialAd ad) {
                        Log.i(TAG, "插屏广告加载成功");
                        InterstitialAdCallback.onInterstitialAdLoaded(activity);
                        if (ad == null) {
                            InterstitialAdCallback.onInterstitialAdError(activity, -1, "插屏广告数据为空");
                            return;
                        }
                        ad.showAd(activity, new OSETInterstitialListener() {
                            @Override
                            
                            public void onShow() {
                                Log.i(TAG, "插屏广告展示");
                                InterstitialAdCallback.onInterstitialAdShow(activity);
                            }

                            @Override
                            public void onClick() {
                            }

                            @Override
                            
                            public void onClose() {
                                Log.i(TAG, "插屏广告关闭");
                                InterstitialAdCallback.onInterstitialAdClose(activity);
                            }

                            @Override
                            
                            public void onError(String code, String msg) {
                                Log.e(TAG, "插屏广告失败，错误信息：" + msg + "(" + code + ")");
                                InterstitialAdCallback.onInterstitialAdError(activity, parseErrorCode(code), msg);
                            }
                        });
                    }

                    @Override
                    
                    public void onLoadFail(String code, String msg) {
                        Log.e(TAG, "插屏广告加载失败，错误信息：" + msg + "(" + code + ")");
                        InterstitialAdCallback.onInterstitialAdError(activity, parseErrorCode(code), msg);
                    }
                });
    }

    /**
     * 加载并展示 OSET 插屏广告（广告位为 long 时的便捷重载）。
     */
    public static void OSETInterstitialAd(Activity activity, long PosId) {
        OSETInterstitialAd(activity, String.valueOf(PosId));
    }

    /**
     * 加载并展示 OSET Banner 广告。
     *
     * @param activity  展示广告的页面
     * @param PosId     Banner 广告位 ID
     * @param container 承载广告 View 的容器
     */
    
    public static void OSETBannerAd(Activity activity, String PosId, @NonNull ViewGroup container) {
        if (activity == null || container == null || PosId == null || PosId.trim().isEmpty()) {
            BannerAdCallback.onBannerAdError(activity, -1, "参数非法（Activity/容器/广告位不能为空）");
            return;
        }
        container.removeAllViews();
        OSETBanner.getInstance()
                .setContext(activity)
                .setPosId(PosId)
                .loadAd(new OSETBannerAdLoadListener() {
                    @Override
                    public void onLoadSuccess(OSETBannerAd ad) {
                        Log.i(TAG, "Banner广告加载成功");
                        if (ad == null) {
                            BannerAdCallback.onBannerAdError(activity, -1, "Banner广告数据为空");
                            return;
                        }
                        ad.render(activity, new OSETBannerListener() {
                            @Override
                            public void onRenderSuccess(View view) {
                                Log.i(TAG, "Banner广告渲染成功");
                                if (view == null) {
                                    BannerAdCallback.onBannerAdError(activity, -1, "Banner广告视图为空");
                                    return;
                                }
                                container.removeAllViews();
                                container.addView(view);
                                BannerAdCallback.onBannerAdLoaded(activity, container);
                            }

                            @Override
                            public void onShow(View view) {
                                Log.i(TAG, "Banner广告展示");
                            }

                            @Override
                            public void onClick(View view) {
                                Log.i(TAG, "Banner广告被点击");
                            }

                            @Override
                            public void onClose(View view) {
                                Log.i(TAG, "Banner广告关闭");
                            }

                            @Override
                            
                            public void onError(String code, String msg) {
                                Log.e(TAG, "Banner广告失败，错误信息：" + msg + "(" + code + ")");
                                BannerAdCallback.onBannerAdError(activity, parseErrorCode(code), msg);
                            }
                        });
                    }

                    @Override
                    
                    public void onLoadFail(String code, String msg) {
                        Log.e(TAG, "Banner广告加载失败，错误信息：" + msg + "(" + code + ")");
                        BannerAdCallback.onBannerAdError(activity, parseErrorCode(code), msg);
                    }
                });
    }

    /**
     * 加载并展示 OSET Banner 广告（广告位为 long 时的便捷重载）。
     */
    public static void OSETBannerAd(Activity activity, long PosId, @NonNull ViewGroup container) {
        OSETBannerAd(activity, String.valueOf(PosId), container);
    }

    /** OSET 的错误码为字符串，尽量解析为 int，失败时返回 0。 */
    
    private static int parseErrorCode(String code) {
        try {
            return Integer.parseInt(code);
        } catch (Exception e) {
            return 0;
        }
    }
}
