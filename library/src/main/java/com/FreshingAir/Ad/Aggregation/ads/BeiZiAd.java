package com.FreshingAir.Ad.Aggregation.ads;

import androidx.annotation.Keep;

import android.util.Log;
import android.content.Context;
import android.app.Activity;

import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.beizi.fusion.AdListener;
import com.beizi.fusion.InterstitialAd;
import com.beizi.fusion.InterstitialAdListener;
import com.beizi.fusion.RewardedVideoAd;
import com.beizi.fusion.RewardedVideoAdListener;
import com.beizi.fusion.SplashAd;
import com.beizi.fusion.BeiZis;
import com.FreshingAir.Ad.Aggregation.AdPlatform;
import com.FreshingAir.Ad.Aggregation.BannerAdCallback;
import com.FreshingAir.Ad.Aggregation.Init;
import com.FreshingAir.Ad.Aggregation.InterstitialAdCallback;
import com.FreshingAir.Ad.Aggregation.RewardVideoAdCallback;
import com.FreshingAir.Ad.Aggregation.SplashAdCallback;

public class BeiZiAd {
    private static final String TAG = "倍孜广告 SDK";
    public static void InitBeiZiSDK(Context context,String appId){
        //建议在application里面调用；假如App有功能引导，也可点击"立即体验"按钮中调用
        BeiZis.init(context, appId);
        Init.adSDKisLoaded.put(AdPlatform.BEIZI, true);
    }
    private static SplashAd splashAd;
    
    public static void BeiziSplashAd(Context context, String GroupId, @NonNull ViewGroup adContainer){
        //跳过按钮传null
        splashAd = new SplashAd(context, null, GroupId, new AdListener() {

            /**
             * 广告加载成功
             */
            @Override
            public void onAdLoaded() {
                Log.i("BeiZisDemo", "onAdLoaded");
                if (splashAd != null) {
                    splashAd.show(adContainer);
                }
            }

            @Override
            public void onAdShown() {
                SplashAdCallback.onSplashAdLoaded(context, adContainer);
                Log.i("BeiZisDemo", "onAdShown");
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                Log.i("BeiZisDemo", "onAdFailedToLoad:" + errorCode);
                SplashAdCallback.goToMainActivity(context);
            }

            /**
             * 广告关闭
             */
            @Override
            
            public void onAdClosed() {
                Log.i("BeiZisDemo", "onAdClosed");
                SplashAdCallback.goToMainActivity(context);
            }

            /**
             * 倒计时回调，返回广告还将被展示的剩余时间。
             * @param millisUnitFinished 单位是毫秒，转换成秒需除以1000（如：Math.round(millisUnitFinished / 1000f)）
             */
            @Override
            public void onAdTick(long millisUnitFinished) {
            }

            /**
             * 广告点击
             */
            @Override
            
            public void onAdClicked() {
                Log.i("BeiZisDemo", "onAdClick");
            }
        }, 5000);//广告请求超时时长，建议5000毫秒,该参数单位为ms
        //第一个参数是广告宽度，第二个参数是广告高度，单位是dp，
        //按照实际的容器宽度和高度传递，默认屏幕的宽度和高度，
        //如需添加底部logo view ，则传递的高度为屏幕高度减去logo view的高度
        splashAd.loadAd(adContainer.getWidth(),adContainer.getHeight());
    }

    /**
     * 加载并展示倍孜激励视频广告。
     *
     * @param context 需要是 Activity（激励视频展示依赖 Activity）
     * @param slotId  激励视频广告位 ID
     */
    
    public static void BeiziRewardVideoAd(Context context, String slotId){
        Activity activity = context instanceof Activity ? (Activity) context : null;
        if (activity == null) {
            RewardVideoAdCallback.onRewardAdError(context, -1, "激励视频展示需要 Activity 上下文");
            return;
        }
        final RewardedVideoAd[] holder = new RewardedVideoAd[1];
        RewardedVideoAd rewardedVideoAd = new RewardedVideoAd(context, slotId, new RewardedVideoAdListener() {
            @Override
            
            public void onRewarded() {
                Log.i("BeiZisDemo", "激励视频发放奖励");
                RewardVideoAdCallback.onRewardAdRewarded(context, null, 0);
            }

            @Override
            
            public void onRewardedVideoAdFailedToLoad(int errorCode) {
                Log.e("BeiZisDemo", "激励视频加载失败，错误码：" + errorCode);
                RewardVideoAdCallback.onRewardAdError(context, errorCode, null);
            }

            @Override
            
            public void onRewardedVideoAdLoaded() {
                Log.i("BeiZisDemo", "激励视频加载成功");
                RewardVideoAdCallback.onRewardAdLoaded(context);
                if (holder[0] != null) {
                    holder[0].showAd(activity);
                }
            }

            @Override
            public void onRewardedVideoCacheSuccess() {
            }

            @Override
            
            public void onRewardedVideoAdShown() {
                Log.i("BeiZisDemo", "激励视频开始播放");
                RewardVideoAdCallback.onRewardAdShow(context);
            }

            @Override
            
            public void onRewardedVideoAdClosed() {
                Log.i("BeiZisDemo", "激励视频关闭");
                RewardVideoAdCallback.onRewardAdClose(context);
            }

            @Override
            public void onRewardedVideoClick() {
            }

            @Override
            public void onRewardedVideoComplete() {
            }

            @Override
            
            public void onRewardedVideoPlayError() {
                Log.e("BeiZisDemo", "激励视频播放失败");
                RewardVideoAdCallback.onRewardAdError(context, -1, "激励视频播放失败");
            }
        }, 5000L, 1);//广告请求超时时长，建议5000毫秒
        holder[0] = rewardedVideoAd;
        rewardedVideoAd.loadAd();
    }

    /**
     * 加载并展示倍孜插屏广告（加载成功后自动展示）。
     *
     * @param activity 展示广告的页面
     * @param adId     插屏广告位 ID
     */
    
    public static void BeiziInterstitialAd(Activity activity, String adId){
        if (activity == null || adId == null || adId.trim().isEmpty()) {
            InterstitialAdCallback.onInterstitialAdError(activity, -1, "参数非法（Activity/广告位不能为空）");
            return;
        }
        final InterstitialAd[] holder = new InterstitialAd[1];
        InterstitialAd interstitialAd = new InterstitialAd(activity, adId, new InterstitialAdListener() {
            @Override
            
            public void onAdLoaded() {
                Log.i(TAG, "插屏广告加载成功");
                InterstitialAdCallback.onInterstitialAdLoaded(activity);
                if (holder[0] != null) {
                    holder[0].showAd(activity);
                }
            }

            @Override
            
            public void onAdFailed(int errorCode) {
                Log.e(TAG, "插屏广告加载失败，错误码：" + errorCode);
                InterstitialAdCallback.onInterstitialAdError(activity, errorCode, null);
            }

            @Override
            
            public void onAdShown() {
                Log.i(TAG, "插屏广告展示");
                InterstitialAdCallback.onInterstitialAdShow(activity);
            }

            @Override
            
            public void onAdClosed() {
                Log.i(TAG, "插屏广告关闭");
                InterstitialAdCallback.onInterstitialAdClose(activity);
            }

            @Override
            public void onAdClick() {
            }
        }, 5000L);//广告请求超时时长，建议5000毫秒
        holder[0] = interstitialAd;
        interstitialAd.loadAd();
    }

    /**
     * 加载并展示倍孜插屏广告（广告位为 long 时的便捷重载）。
     */
    public static void BeiziInterstitialAd(Activity activity, long adId){
        BeiziInterstitialAd(activity, String.valueOf(adId));
    }

    /**
     * 倍孜 SDK 无 Banner 广告类，直接回调不支持。
     */
    
    public static void BeiziBannerAd(Activity activity, String adId, @NonNull ViewGroup container){
        Log.e(TAG, "倍孜 SDK 不支持 Banner 广告");
        BannerAdCallback.onBannerAdError(activity, -1, "倍孜 SDK 不支持 Banner 广告");
    }

    /**
     * 倍孜 SDK 无 Banner 广告类，直接回调不支持（广告位为 long 时的便捷重载）。
     */
    public static void BeiziBannerAd(Activity activity, long adId, @NonNull ViewGroup container){
        BeiziBannerAd(activity, String.valueOf(adId), container);
    }
}
