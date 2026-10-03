package com.FreshingAir.Ad.Aggregation.ads;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

import android.content.Context;
import android.app.Activity;
import android.view.ViewGroup;
import android.view.View;

import com.FreshingAir.Ad.Aggregation.AdPlatform;
import com.FreshingAir.Ad.Aggregation.BannerAdCallback;
import com.FreshingAir.Ad.Aggregation.Init;
import com.FreshingAir.Ad.Aggregation.InterstitialAdCallback;
import com.FreshingAir.Ad.Aggregation.RewardVideoAdCallback;
import com.ja.adx.qiming.ad.BannerAd;
import com.ja.adx.qiming.ad.InterstitialAd;
import com.ja.adx.qiming.ad.RewardAd;
import com.ja.adx.qiming.ad.bean.BannerAdInfo;
import com.ja.adx.qiming.ad.bean.InterstitialAdInfo;
import com.ja.adx.qiming.ad.bean.RewardAdInfo;
import com.ja.adx.qiming.ad.bean.SplashAdInfo;
import com.ja.adx.qiming.ad.error.Error;
import com.ja.adx.qiming.ad.listener.BannerAdListener;
import com.ja.adx.qiming.ad.listener.InterstitialAdListener;
import com.ja.adx.qiming.ad.listener.RewardAdListener;
import com.ja.adx.qiming.QiMingADXSDK;
import com.ja.adx.qiming.config.InitConfig;
import android.util.Log;
import com.FreshingAir.Ad.Aggregation.BuildConfig;
import com.FreshingAir.Ad.Aggregation.SplashAdCallback;

public class QiMingAd {

    private static final String TAG = "QiMingADX SDK";
    
    public static void InitQiMingSDK(Context context,String appId){
        // 初始化QiMingADX广告SDK
        QiMingADXSDK.getInstance().init(context, new InitConfig.Builder()
                // 设置AppId，必须的
                .appId(appId)
                // 是否开启Debug，开启会有详细的日志信息打印
                // 注意上线后请置为false
                .debug(BuildConfig.DEBUG)
                .build());
        Init.adSDKisLoaded.put(AdPlatform.QIMING, true);
    }
    public static void QiMingSplashAd(Context context,String posId,ViewGroup adContainer){
        com.ja.adx.qiming.ad.SplashAd splashAd = new com.ja.adx.qiming.ad.SplashAd(context);
        splashAd.setListener(new com.ja.adx.qiming.ad.listener.SplashAdListener() {
            @Override
            public void onAdTick(long millisUntilFinished) {
                // 倒计时剩余时长（单位：秒）
            }

            @Override
            public void onAdReceive(SplashAdInfo splashAdInfo) {
                // 广告获取成功回调，在此回调中展示广告
                // 获取开屏广告视图
                View view = splashAdInfo.getSplashAdView();
                // 将广告视图添加到容器中，注意容器高度要大于屏幕75%
                adContainer.addView(view);
                // 渲染广告，一定要最后调用
                splashAdInfo.render();
            }

            @Override
            public void onAdExpose(SplashAdInfo splashAdInfo) {
                // 广告展示回调
                SplashAdCallback.onSplashAdLoaded(context, adContainer);
            }

            @Override
            
            public void onAdClick(SplashAdInfo splashAdInfo) {
                // 广告点击回调
            }

            @Override
            
            public void onAdSkip(SplashAdInfo splashAdInfo) {
                // 广告跳过回调
                SplashAdCallback.goToMainActivity(context);
            }

            @Override
            public void onAdClose(SplashAdInfo splashAdInfo) {
                // 广告关闭回调，可在此处进入应用首页
                SplashAdCallback.goToMainActivity(context);
            }

            @Override
            
            public void onAdFailed(Error error) {
                Log.e(TAG,"广告加载失败，错误信息："+error.getError()+"("+error.getCode()+")");
                SplashAdCallback.goToMainActivity(context);
            }
        });
        splashAd.loadAd(posId);
    }

    /**
     * 加载并展示启明激励视频广告。
     *
     * @param activity 展示广告的页面
     * @param posId    激励视频广告位 ID
     */
    
    public static void QiMingRewardVideoAd(Activity activity, String posId){
        RewardAd rewardAd = new RewardAd(activity);
        rewardAd.setListener(new RewardAdListener() {
            @Override
            
            public void onAdReceive(RewardAdInfo rewardAdInfo) {
                Log.i(TAG, "激励视频加载成功");
                RewardVideoAdCallback.onRewardAdLoaded(activity);
                if (rewardAdInfo != null) {
                    rewardAdInfo.showRewardAd(activity);
                }
            }

            @Override
            
            public void onAdExpose(RewardAdInfo rewardAdInfo) {
                Log.i(TAG, "激励视频开始播放");
                RewardVideoAdCallback.onRewardAdShow(activity);
            }

            @Override
            public void onAdClick(RewardAdInfo rewardAdInfo) {
            }

            @Override
            
            public void onAdClose(RewardAdInfo rewardAdInfo) {
                Log.i(TAG, "激励视频关闭");
                RewardVideoAdCallback.onRewardAdClose(activity);
            }

            @Override
            
            public void onAdFailed(Error error) {
                int code = error != null ? error.getCode() : 0;
                String msg = error != null ? error.getError() : null;
                Log.e(TAG, "激励视频加载失败，错误信息：" + msg + "(" + code + ")");
                RewardVideoAdCallback.onRewardAdError(activity, code, msg);
            }

            @Override
            
            public void onAdReward(RewardAdInfo rewardAdInfo) {
                Log.i(TAG, "激励视频发放奖励");
                RewardVideoAdCallback.onRewardAdRewarded(activity, null, 0);
            }

            @Override
            public void onVideoCompleted(RewardAdInfo rewardAdInfo) {
            }

            @Override
            public void onVideoSkip(RewardAdInfo rewardAdInfo) {
            }

            @Override
            
            public void onVideoError(RewardAdInfo rewardAdInfo, String errorMsg) {
                Log.e(TAG, "激励视频播放失败，错误信息：" + errorMsg);
                RewardVideoAdCallback.onRewardAdError(activity, -1, errorMsg);
            }
        });
        rewardAd.loadAd(posId);
    }

    /**
     * 加载并展示启明插屏广告（加载成功后自动展示）。
     *
     * @param activity 展示广告的页面
     * @param posId    插屏广告位 ID
     */
    
    public static void QiMingInterstitialAd(Activity activity, String posId){
        if (activity == null || posId == null || posId.trim().isEmpty()) {
            InterstitialAdCallback.onInterstitialAdError(activity, -1, "参数非法（Activity/广告位不能为空）");
            return;
        }
        InterstitialAd interstitialAd = new InterstitialAd(activity);
        interstitialAd.setListener(new InterstitialAdListener() {
            @Override
            public void onAdReceive(InterstitialAdInfo interstitialAdInfo) {
                Log.i(TAG, "插屏广告加载成功");
                InterstitialAdCallback.onInterstitialAdLoaded(activity);
                if (interstitialAdInfo != null) {
                    interstitialAdInfo.showInterstitial(activity);
                }
            }

            @Override
            
            public void onAdExpose(InterstitialAdInfo interstitialAdInfo) {
                Log.i(TAG, "插屏广告展示");
                InterstitialAdCallback.onInterstitialAdShow(activity);
            }

            @Override
            public void onAdClick(InterstitialAdInfo interstitialAdInfo) {
            }

            @Override
            
            public void onAdClose(InterstitialAdInfo interstitialAdInfo) {
                Log.i(TAG, "插屏广告关闭");
                InterstitialAdCallback.onInterstitialAdClose(activity);
            }

            @Override
            
            public void onAdFailed(Error error) {
                int code = error != null ? error.getCode() : 0;
                String msg = error != null ? error.getError() : null;
                Log.e(TAG, "插屏广告加载失败，错误信息：" + msg + "(" + code + ")");
                InterstitialAdCallback.onInterstitialAdError(activity, code, msg);
            }

            @Override
            public void onVideoStart(InterstitialAdInfo interstitialAdInfo) {
            }

            @Override
            public void onVideoFinish(InterstitialAdInfo interstitialAdInfo) {
            }

            @Override
            public void onVideoPause(InterstitialAdInfo interstitialAdInfo) {
            }

            @Override
            public void onVideoError(InterstitialAdInfo interstitialAdInfo) {
            }
        });
        interstitialAd.loadAd(posId);
    }

    /**
     * 加载并展示启明插屏广告（广告位为 long 时的便捷重载）。
     */
    public static void QiMingInterstitialAd(Activity activity, long posId){
        QiMingInterstitialAd(activity, String.valueOf(posId));
    }

    /**
     * 加载并展示启明 Banner 广告。
     *
     * @param activity  展示广告的页面
     * @param posId     Banner 广告位 ID
     * @param container 承载广告 View 的容器
     */
    
    public static void QiMingBannerAd(Activity activity, String posId, @NonNull ViewGroup container){
        if (activity == null || container == null || posId == null || posId.trim().isEmpty()) {
            BannerAdCallback.onBannerAdError(activity, -1, "参数非法（Activity/容器/广告位不能为空）");
            return;
        }
        container.removeAllViews();
        BannerAd bannerAd = new BannerAd(activity);
        bannerAd.setListener(new BannerAdListener() {
            @Override
            public void onAdReceive(BannerAdInfo bannerAdInfo) {
                Log.i(TAG, "Banner广告加载成功");
                if (bannerAdInfo == null) {
                    BannerAdCallback.onBannerAdError(activity, -1, "Banner广告数据为空");
                    return;
                }
                bannerAdInfo.render();
                View adView = bannerAdInfo.getAdView();
                // 若 SDK 已自动加入容器则不再重复添加
                if (adView != null && adView.getParent() == null) {
                    container.addView(adView);
                }
                BannerAdCallback.onBannerAdLoaded(activity, container);
            }

            @Override
            public void onAdExpose(BannerAdInfo bannerAdInfo) {
                Log.i(TAG, "Banner广告曝光");
            }

            @Override
            public void onAdClick(BannerAdInfo bannerAdInfo) {
                Log.i(TAG, "Banner广告被点击");
            }

            @Override
            public void onAdClose(BannerAdInfo bannerAdInfo) {
                Log.i(TAG, "Banner广告关闭");
            }

            @Override
            
            public void onAdFailed(Error error) {
                int code = error != null ? error.getCode() : 0;
                String msg = error != null ? error.getError() : null;
                Log.e(TAG, "Banner广告加载失败，错误信息：" + msg + "(" + code + ")");
                BannerAdCallback.onBannerAdError(activity, code, msg);
            }
        });
        bannerAd.loadAd(posId);
    }

    /**
     * 加载并展示启明 Banner 广告（广告位为 long 时的便捷重载）。
     */
    public static void QiMingBannerAd(Activity activity, long posId, @NonNull ViewGroup container){
        QiMingBannerAd(activity, String.valueOf(posId), container);
    }
}
