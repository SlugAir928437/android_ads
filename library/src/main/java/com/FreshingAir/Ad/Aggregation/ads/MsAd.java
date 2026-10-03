package com.FreshingAir.Ad.Aggregation.ads;

import androidx.annotation.Keep;
import android.app.Activity;
import android.util.Log;
import android.content.Context;
import android.view.ViewGroup;

import com.FreshingAir.Ad.Aggregation.AdPlatform;
import com.FreshingAir.Ad.Aggregation.BannerAdCallback;
import com.FreshingAir.Ad.Aggregation.Init;
import com.FreshingAir.Ad.Aggregation.InterstitialAdCallback;
import com.FreshingAir.Ad.Aggregation.RewardVideoAdCallback;
import com.meishu.sdk.core.AdSdk;
import com.meishu.sdk.core.MSAdConfig;
import com.meishu.sdk.core.ad.MsAdSlot;
import com.meishu.sdk.core.ad.banner.BannerAdEventLoader;
import com.meishu.sdk.core.ad.banner.BannerAdLoadListener;
import com.meishu.sdk.core.ad.banner.IBannerAd;
import com.meishu.sdk.core.ad.draw.DrawAd;
import com.meishu.sdk.core.ad.draw.DrawAdEventLoader;
import com.meishu.sdk.core.ad.draw.DrawAdLoadListener;
import com.meishu.sdk.core.ad.draw.IDrawAd;
import com.meishu.sdk.core.ad.fullscreenvideo.FullScreenAdLoadListener;
import com.meishu.sdk.core.ad.fullscreenvideo.IFullScreenMediaListener;
import com.meishu.sdk.core.ad.fullscreenvideo.IFullScreenVideoAd;
import com.meishu.sdk.core.ad.interstitial.InterstitialAd;
import com.meishu.sdk.core.ad.interstitial.InterstitialAdEventLoader;
import com.meishu.sdk.core.ad.interstitial.InterstitialAdLoadListener;
import com.meishu.sdk.core.ad.paster.PasterAd;
import com.meishu.sdk.core.ad.paster.PasterAdLoadListener;
import com.meishu.sdk.core.ad.recycler.FeedExpressAdInteractionListener;
import com.meishu.sdk.core.ad.recycler.RecyclerAdData;
import com.meishu.sdk.core.ad.recycler.RecyclerAdLoadListener;
import com.meishu.sdk.core.ad.reward.RewardAdLoadListener;
import com.meishu.sdk.core.ad.reward.RewardAdMediaListener;
import com.meishu.sdk.core.ad.reward.RewardInteractionListener;
import com.meishu.sdk.core.ad.reward.RewardVideoAd;
import com.meishu.sdk.core.ad.reward.RewardVideoEventLoader;
import com.meishu.sdk.core.ad.splash.ISplashAd;
import com.meishu.sdk.core.ad.splash.SplashAdEventLoader;
import com.meishu.sdk.core.ad.splash.SplashAdLoadListener;
import com.meishu.sdk.core.loader.InteractionListener;
import com.meishu.sdk.core.utils.AdError;
import androidx.annotation.NonNull;
import android.widget.VideoView;

import com.FreshingAir.Ad.Aggregation.SplashAdCallback;

import java.util.List;
import java.util.Map;

import com.meishu.sdk.core.ad.recycler.RecyclerMixAdEventLoader;
import com.meishu.sdk.core.ad.fullscreenvideo.FullScreenVideoEventAdLoader;

import com.meishu.sdk.core.ad.paster.PasterAdEventLoader;

public class MsAd {

    private static final String TAG = "美数广告 SDK";
    public static void initMsAdSDK(Context context,String appId){
        MSAdConfig sdkConfig = new MSAdConfig.Builder()
                .appId(appId)
                .enableDebug(com.FreshingAir.Ad.Aggregation.BuildConfig.DEBUG)  //开启DEBUG模式，打印内部LOG
                .build();
        AdSdk.init(context, sdkConfig);
        Init.adSDKisLoaded.put(AdPlatform.MS, true);
    }
    public static void MsSplashAd(Context context,String pid,ViewGroup adContainer){
        //只加载广告
        new SplashAdEventLoader(context,
                new MsAdSlot.Builder()
                        .setPid(pid)
                        .setFetchCount(1)
                        .setIsHideSkipBtn(false)
                        .build(),
                new SplashAdLoadListener() {
            @Override
            public void onLoadSuccess(@NonNull ISplashAd iSplashAd) {
                Log.i(TAG,"广告加载成功");
                iSplashAd.setInteractionListener(new InteractionListener() {
                    @Override
                    public void onAdClicked() {
                        Log.i(TAG, "广告被点击");
                    }

                    @Override
                    
                    public void onAdExposure() {
                        SplashAdCallback.onSplashAdLoaded(context, adContainer);
                    }

                    @Override
                    public void onAdClosed() {
                        Log.i(TAG, "广告关闭");
                        SplashAdCallback.goToMainActivity(context);
                    }
                });
                iSplashAd.showAd(adContainer);
            }

            @Override
            
            public void onLoadFail(AdError adError) {
                Log.e(TAG,"广告加载失败，错误信息："+adError.getMessage()+"("+adError.getCode()+")");
                SplashAdCallback.goToMainActivity(context);
            }

            @Override
            public void onRenderSuccess(ISplashAd iSplashAd) {
                Log.i(TAG,"广告渲染成功");
            }

            @Override
            
            public void onAdFail(ISplashAd iSplashAd, AdError adError, int i) {
                Log.e(TAG,"广告渲染失败，错误信息："+adError.getMessage()+"("+adError.getCode()+")");
                SplashAdCallback.goToMainActivity(context);
            }
        },5000).loadAd();

    }
    public static void MsRecyclerMixAdAd(Activity activity, String Pid, @NonNull ViewGroup adContainer){
        adContainer.removeAllViews();
        new RecyclerMixAdEventLoader(adContainer.getContext(),
                new MsAdSlot(new MsAdSlot.Builder()
                        .setFetchCount(1)
                        .setPid(Pid)
                        .setIsVideoAutoPlay(true)
                        .setIsHideSkipBtn(false)
                ), new RecyclerAdLoadListener() {
            @Override
            
            public void onLoadedSuccess(List<RecyclerAdData> list) {
                Log.i("美数广告","广告加载成功");
                RecyclerAdData recyclerAdData = list.get(0);
                recyclerAdData.setExpressAdInteractionListener(new FeedExpressAdInteractionListener() {
                    @Override
                    public void onAdClicked() {

                    }

                    @Override
                    
                    public void onAdExposure() {

                    }

                    @Override
                    
                    public void onAdClosed() {

                    }

                    @Override
                    public void onRenderError(int i, String s) {
                        Log.e("美数广告","广告渲染失败，错误信息："+s+"("+i+")");
                    }

                    @Override
                    public void onRenderSuccess() {
                        Log.i("美数广告","广告渲染成功");
                        adContainer.addView(recyclerAdData.getExpressView());
                    }
                });
                recyclerAdData.render(activity);
            }

            @Override
            public void onLoadFail(AdError adError) {
                Log.i("美数广告","广告加载失败，错误信息："+adError.getMessage()+"("+adError.getCode()+")");
            }
        }).loadAd();
    }
    /**
     * 加载并展示美数 Banner 广告。
     *
     * @param activity        展示广告的页面
     * @param pid             Banner 广告位 ID
     * @param bannerContainer 承载广告 View 的容器
     */
    public static void MsBannerAd(Activity activity, String pid, @NonNull ViewGroup bannerContainer){
        if (activity == null || bannerContainer == null || pid == null || pid.trim().isEmpty()) {
            BannerAdCallback.onBannerAdError(activity, -1, "参数非法（Activity/容器/广告位不能为空）");
            return;
        }
        bannerContainer.removeAllViews();
        new BannerAdEventLoader(activity,
                new MsAdSlot.Builder()
                        .setPid(pid)
                        .setAutoRender(true)
                        .setCloseButtonVisible(true)
                        .setWidth(bannerContainer.getMeasuredWidth())
                        .setHeight(bannerContainer.getMeasuredHeight())
                        .build(),
                new BannerAdLoadListener() {
            @Override
            public void onLoadSuccess(IBannerAd ad) {
                Log.i(TAG, "Banner广告加载成功");
                ad.setInteractionListener(new InteractionListener() {
                    @Override
                    public void onAdClicked() {
                        Log.i(TAG, "Banner广告被点击");
                    }

                    @Override
                    
                    public void onAdExposure() {
                        Log.i(TAG, "Banner广告曝光");
                    }

                    @Override
                    public void onAdClosed() {
                        Log.i(TAG, "Banner广告关闭");
                    }
                });
                ad.render();
                ad.showAd(bannerContainer);
                BannerAdCallback.onBannerAdLoaded(activity, bannerContainer);
            }

            @Override
            
            public void onLoadFail(AdError adError) {
                String msg = adError != null ? adError.getMessage() : null;
                int code = adError != null ? adError.getCode() : 0;
                Log.e(TAG, "Banner广告加载失败，错误信息：" + msg + "(" + code + ")");
                BannerAdCallback.onBannerAdError(activity, code, msg);
            }

            @Override
            public void onRenderSuccess(IBannerAd ad) {
                Log.i(TAG, "Banner广告渲染成功");
            }

            @Override
            
            public void onAdFail(IBannerAd ad, AdError adError, int type) {
                String msg = adError != null ? adError.getMessage() : null;
                int code = adError != null ? adError.getCode() : 0;
                Log.e(TAG, "Banner广告失败，错误信息：" + msg + "(" + type + ")");
                BannerAdCallback.onBannerAdError(activity, code != 0 ? code : type, msg);
            }
        }).loadAd();
    }

    /**
     * 加载并展示美数 Banner 广告（广告位为 long 时的便捷重载）。
     */
    public static void MsBannerAd(Activity activity, long pid, @NonNull ViewGroup bannerContainer){
        MsBannerAd(activity, String.valueOf(pid), bannerContainer);
    }
    
    public static void MsFullScreenVideoAd(Activity activity, String pid){
        new FullScreenVideoEventAdLoader(activity, new MsAdSlot.Builder().setPid(pid).build(), new FullScreenAdLoadListener() {
            @Override
            
            public void onLoadSuccess(IFullScreenVideoAd ad) {
                Log.d(TAG, "DEMO ADEVENT " + (new Throwable().getStackTrace()[0].getMethodName()));
            }

            @Override
            public void onLoadFail(AdError adError) {
                Log.d(TAG, "DEMO ADEVENT " + (new Throwable().getStackTrace()[0].getMethodName()));
            }

            @Override
            public void onRenderSuccess(IFullScreenVideoAd fullScreenVideoAd) {
                Log.d(TAG, "DEMO ADEVENT " + (new Throwable().getStackTrace()[0].getMethodName()));
                fullScreenVideoAd.setInteractionListener(new InteractionListener() {
                    @Override
                    
                    public void onAdClicked() {
                        Log.d(TAG, "DEMO ADEVENT " + (new Throwable().getStackTrace()[0].getMethodName()));
                    }

                    @Override
                    public void onAdExposure() {
                        Log.e(TAG,"ecpm="+fullScreenVideoAd.getData().getEcpm());
                        Log.d(TAG, "DEMO ADEVENT " + (new Throwable().getStackTrace()[0].getMethodName()));
                    }

                    @Override
                    public void onAdClosed() {
                        Log.d(TAG, "DEMO ADEVENT " + (new Throwable().getStackTrace()[0].getMethodName()));
                    }

                });
                fullScreenVideoAd.setMediaListener(new IFullScreenMediaListener() {
                    @Override
                    public void onVideoLoaded() {
                        Log.d(TAG, "DEMO ADEVENT " + (new Throwable().getStackTrace()[0].getMethodName()));
                    }

                    @Override
                    public void onVideoStart() {
                        Log.d(TAG, "DEMO ADEVENT " + (new Throwable().getStackTrace()[0].getMethodName()));
                    }

                    @Override
                    public void onVideoPause() {
                        Log.d(TAG, "DEMO ADEVENT " + (new Throwable().getStackTrace()[0].getMethodName()));
                    }

                    @Override
                    public void onVideoResume() {
                        Log.d(TAG, "DEMO ADEVENT " + (new Throwable().getStackTrace()[0].getMethodName()));
                    }

                    @Override
                    
                    public void onVideoCompleted() {
                        Log.d(TAG, "DEMO ADEVENT " + (new Throwable().getStackTrace()[0].getMethodName()));
                    }

                    @Override
                    public void onVideoError() {
                        Log.d(TAG, "DEMO ADEVENT " + (new Throwable().getStackTrace()[0].getMethodName()));
                    }

                    @Override
                    public void onSkippedVideo() {
                        Log.d(TAG, "DEMO ADEVENT " + (new Throwable().getStackTrace()[0].getMethodName()));
                    }
                });
            }

            @Override
            public void onAdFail(IFullScreenVideoAd ad, AdError adError, int type) {
                Log.d(TAG, "DEMO ADEVENT " + (new Throwable().getStackTrace()[0].getMethodName()));
            }
        }).loadAd();
    }
    public static void MsPasterAd(Context context, String pid, @NonNull ViewGroup videoContainer, VideoView videoView){
        videoContainer.removeAllViews();
        MsAdSlot msAdSlot = new MsAdSlot.Builder()
                .setPid(pid)
                .build();
        PasterAdEventLoader pasterAdLoader = new PasterAdEventLoader(context, videoContainer, msAdSlot, new PasterAdLoadListener() {
            @Override
            
            public void onVideoLoaded() {

            }

            @Override
            
            public void onVideoComplete() {
                //videoView.start();
            }

            @Override
            public void onLoadSuccess(PasterAd pasterAd) {
                pasterAd.setInteractionListener(new InteractionListener() {
                    @Override
                    
                    public void onAdClicked() {
                        // 点击时可以把广告关掉
                        Log.d(TAG, "DEMO ADEVENT " + (new Throwable().getStackTrace()[0].getMethodName()));
                    }

                    @Override
                    public void onAdExposure() {
                        Log.d(TAG, "DEMO ADEVENT " + (new Throwable().getStackTrace()[0].getMethodName()));
                    }

                    @Override
                    
                    public void onAdClosed() {
                        Log.d(TAG, "DEMO ADEVENT " + (new Throwable().getStackTrace()[0].getMethodName()));
                        videoView.start();
                    }

                });
            }

            @Override
            public void onLoadFail(AdError adError) {
                videoView.start();
            }

            @Override
            
            public void onRenderSuccess(PasterAd pasterAd) {

            }

            @Override
            
            public void onAdFail(PasterAd pasterAd, AdError adError, int i) {
                videoView.start();
            }
        });
        pasterAdLoader.loadAd();
    }
    /**
     * 加载并展示美数激励视频广告。
     *
     * @param activity 展示广告的页面
     * @param pid      激励视频广告位 ID
     */
    public static void MsRewardVideoAd(Activity activity, String pid){
        new RewardVideoEventLoader(activity, new MsAdSlot.Builder().setPid(pid).build(), new RewardAdLoadListener() {
            @Override
            public void onLoadSuccess(RewardVideoAd ad) {
                Log.d(TAG, "激励视频加载成功");
                RewardVideoAdCallback.onRewardAdLoaded(activity);
                ad.setInteractionListener(new RewardInteractionListener() {
                    @Override
                    
                    public void onReward(Map<String, Object> map) {
                        Log.i(TAG, "激励视频发放奖励");
                        RewardVideoAdCallback.onRewardAdRewarded(activity, null, 0);
                    }

                    @Override
                    public void onAdClicked() {
                    }

                    @Override
                    
                    public void onAdExposure() {
                        Log.i(TAG, "激励视频开始播放");
                        RewardVideoAdCallback.onRewardAdShow(activity);
                    }

                    @Override
                    
                    public void onAdClosed() {
                        Log.i(TAG, "激励视频关闭");
                        RewardVideoAdCallback.onRewardAdClose(activity);
                    }

                });
                ad.setMediaListener(new RewardAdMediaListener() {
                    @Override
                    
                    public void onVideoLoaded() {
                    }

                    @Override
                    public void onVideoStart() {
                    }

                    @Override
                    public void onVideoPause() {
                    }

                    @Override
                    
                    public void onVideoResume() {
                    }

                    @Override
                    
                    public void onVideoCompleted() {
                    }

                    @Override
                    public void onVideoError() {
                    }

                    @Override
                    public void onSkippedVideo() {
                    }
                });
                // 美数激励视频需要先渲染，渲染成功后再展示
                ad.render();
            }

            @Override
            
            public void onLoadFail(AdError adError) {
                String msg = adError != null ? adError.getMessage() : null;
                Log.e(TAG, "激励视频加载失败，错误信息：" + msg);
                RewardVideoAdCallback.onRewardAdError(activity, adError != null ? adError.getCode() : 0, msg);
            }

            @Override
            public void onRenderSuccess(RewardVideoAd ad) {
                Log.i(TAG, "激励视频渲染成功");
                ad.showAd(activity);
            }

            @Override
            
            public void onAdFail(RewardVideoAd ad, AdError adError, int type) {
                String msg = adError != null ? adError.getMessage() : null;
                Log.e(TAG, "激励视频失败，错误信息：" + msg + "(" + type + ")");
                RewardVideoAdCallback.onRewardAdError(activity, type, msg);
            }
        }).loadAd();
    }
    /**
     * 加载并展示美数插屏广告（加载成功后自动展示）。
     *
     * @param activity 展示广告的页面
     * @param pid      插屏广告位 ID
     */
    
    public static void MsInterstitialAd(Activity activity, String pid){
        if (activity == null || pid == null || pid.trim().isEmpty()) {
            InterstitialAdCallback.onInterstitialAdError(activity, -1, "参数非法（Activity/广告位不能为空）");
            return;
        }
        new InterstitialAdEventLoader(activity,
                new MsAdSlot.Builder()
                        .setPid(pid)
                        .setIsClickToClose(true)
                        .build(),
                new InterstitialAdLoadListener() {
                    @Override
                    public void onLoadSuccess(InterstitialAd ad) {
                        Log.i(TAG, "插屏广告加载成功");
                        InterstitialAdCallback.onInterstitialAdLoaded(activity);
                        ad.setInteractionListener(new InteractionListener() {
                            @Override
                            public void onAdClicked() {
                                Log.i(TAG, "插屏广告被点击");
                            }

                            @Override
                            public void onAdExposure() {
                                Log.i(TAG, "插屏广告展示");
                                InterstitialAdCallback.onInterstitialAdShow(activity);
                            }

                            @Override
                            
                            public void onAdClosed() {
                                Log.i(TAG, "插屏广告关闭");
                                InterstitialAdCallback.onInterstitialAdClose(activity);
                            }
                        });
                        // 美数插屏需要先渲染，渲染成功后再展示
                        ad.render();
                    }

                    @Override
                    
                    public void onLoadFail(AdError adError) {
                        String msg = adError != null ? adError.getMessage() : null;
                        int code = adError != null ? adError.getCode() : 0;
                        Log.e(TAG, "插屏广告加载失败，错误信息：" + msg + "(" + code + ")");
                        InterstitialAdCallback.onInterstitialAdError(activity, code, msg);
                    }

                    @Override
                    public void onRenderSuccess(InterstitialAd ad) {
                        Log.i(TAG, "插屏广告渲染成功");
                        ad.showAd(activity);
                    }

                    @Override
                    
                    public void onAdFail(InterstitialAd ad, AdError adError, int type) {
                        String msg = adError != null ? adError.getMessage() : null;
                        int code = adError != null ? adError.getCode() : 0;
                        Log.e(TAG, "插屏广告失败，错误信息：" + msg + "(" + type + ")");
                        InterstitialAdCallback.onInterstitialAdError(activity, code != 0 ? code : type, msg);
                    }
                }).loadAd();
    }

    /**
     * 加载并展示美数插屏广告（广告位为 long 时的便捷重载）。
     */
    public static void MsInterstitialAd(Activity activity, long pid){
        MsInterstitialAd(activity, String.valueOf(pid));
    }
    public static void MsDrawAd(Context context, String pid, ViewGroup adContainer){
        new DrawAdEventLoader(context,
                new MsAdSlot.Builder()
                        .setPid(pid)
                        .setAutoRender(true)
                        .build(),
                new DrawAdLoadListener() {
                    @Override
                    
                    public void onLoadSuccess(IDrawAd ad) {
                        Log.d(TAG, "DEMO ADEVENT " + (new Throwable().getStackTrace()[0].getMethodName()));
                        adContainer.addView(ad.getAdView());
                    }

                    @Override
                    public void onLoadFail(AdError adError) {
                        Log.d(TAG, "DEMO ADEVENT " + (new Throwable().getStackTrace()[0].getMethodName()));
                    }

                    @Override
                    
                    public void onRenderSuccess(IDrawAd iDrawAd) {
                        Log.d(TAG, "DEMO ADEVENT " + (new Throwable().getStackTrace()[0].getMethodName()));
                        //Toast.makeText(VideoFeedActivity.this, "渲染成功", Toast.LENGTH_SHORT).show();

                        iDrawAd.setInteractionListener(new InteractionListener() {
                            @Override
                            
                            public void onAdClicked() {
                                Log.d(TAG, "DEMO ADEVENT " + (new Throwable().getStackTrace()[0].getMethodName()));
                            }

                            @Override
                            
                            public void onAdExposure() {
                                Log.d(TAG, "DEMO ADEVENT " + (new Throwable().getStackTrace()[0].getMethodName()));
                            }

                            @Override
                            
                            public void onAdClosed() {
                                Log.d(TAG, "DEMO ADEVENT " + (new Throwable().getStackTrace()[0].getMethodName()));
                            }
                        });

                        iDrawAd.setOnDrawVideoListener(new DrawAd.IDrawVideoListener() {
                            @Override
                            
                            public void playRenderingStart() {
                                Log.e(TAG, "playRenderingStart: " );
                            }

                            @Override
                            public void playPause() {
                                Log.e(TAG, "playPause: " );
                            }

                            @Override
                            
                            public void playResume() {
                                Log.e(TAG, "playResume: " );
                            }

                            @Override
                            
                            public void playCompletion() {
                                Log.e(TAG, "playCompletion: " );
                            }

                            @Override
                            public void playError() {
                                Log.e(TAG, "playError: " );
                            }

                            @Override
                            
                            public void pauseBtnClick() {
                                Log.e(TAG, "pauseBtnClick: " );
                            }

                            @Override
                            public void onProgressUpdate(long current, long duration) {
                                Log.e(TAG, "onProgressUpdate: "+current);
                            }

                            @Override
                            public void onClickRetry() {
                                Log.e(TAG, "onClickRetry: " );
                            }

                            @Override
                            public void onVideoLoad() {
                                Log.e(TAG, "onVideoLoad: " );
                            }

                            @Override
                            
                            public void onVideoError(int errorCode, String errorMsg) {
                                Log.e(TAG, "onVideoError: "+errorCode );
                            }
                        });
                    }

                    @Override
                    public void onAdFail(IDrawAd ad, AdError adError, int type) {
                        Log.d(TAG, "DEMO ADEVENT " + (new Throwable().getStackTrace()[0].getMethodName()));
                    }

        }).loadAd();
    }
}