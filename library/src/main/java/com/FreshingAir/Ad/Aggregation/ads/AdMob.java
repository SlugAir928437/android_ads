package com.FreshingAir.Ad.Aggregation.ads;

import androidx.annotation.Keep;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.FreshingAir.Ad.Aggregation.BannerAdCallback;
import com.FreshingAir.Ad.Aggregation.InterstitialAdCallback;
import com.FreshingAir.Ad.Aggregation.RewardVideoAdCallback;
import com.FreshingAir.Ad.Aggregation.SplashAdCallback;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.appopen.AppOpenAd;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;


public class AdMob {

    private static final String TAG = "AdMob Ad";
    
    public static void initAdMobSDK(Context context){
        new Thread(
                () -> {
                    // Initialize the Google Mobile Ads SDK on a background thread.
                    MobileAds.initialize(context, initializationStatus -> {});
                })
                .start();
    }
    public static void AdMobSplashAd(@NonNull final Activity activity, String adUnitId) {
        AppOpenAd.load(
                activity.getApplicationContext(),
                adUnitId,
                new AdRequest.Builder().build(),
                new AppOpenAd.AppOpenAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull AppOpenAd ad) {
                        // Called when an app open ad has loaded.
                        Log.d(TAG, "App open ad loaded.");
                        //SplashAdCallback.onAdLoaded(context);
                        ad.setFullScreenContentCallback(
                                new FullScreenContentCallback() {
                                    @Override
                                    public void onAdDismissedFullScreenContent() {
                                        // Called when full screen content is dismissed.
                                        Log.d(TAG, "Ad dismissed fullscreen content.");
                                        SplashAdCallback.goToMainActivity(activity.getApplicationContext());
                                    }

                                    @Override
                                    public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                                        // Called when full screen content failed to show.
                                        Log.d(TAG, adError.getMessage());
                                        SplashAdCallback.goToMainActivity(activity.getApplicationContext());
                                    }

                                    @Override
                                    
                                    public void onAdShowedFullScreenContent() {
                                        Log.d(TAG, "Ad showed fullscreen content.");
                                    }

                                    @Override
                                    
                                    public void onAdImpression() {
                                        // Called when an impression is recorded for an ad.
                                        Log.d(TAG, "The ad recorded an impression.");
                                    }

                                    @Override
                                    public void onAdClicked() {
                                        // Called when ad is clicked.
                                        Log.d(TAG, "The ad was clicked.");
                                    }
                                });
                        ad.show(activity);
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Called when an app open ad has failed to load.
                        Log.d(TAG, "App open ad failed to load with error: " + loadAdError.getMessage());
                        SplashAdCallback.goToMainActivity(activity.getApplicationContext());
                    }
                });
    }

    /**
     * 加载并展示 AdMob 激励视频广告。
     *
     * @param activity 展示广告的页面
     * @param adUnitId 激励视频广告位 ID
     */
    public static void AdMobRewardVideoAd(@NonNull final Activity activity, String adUnitId) {
        RewardedAd.load(activity, adUnitId, new AdRequest.Builder().build(), new RewardedAdLoadCallback() {
            @Override
            
            public void onAdLoaded(@NonNull RewardedAd ad) {
                Log.d(TAG, "Rewarded ad loaded.");
                RewardVideoAdCallback.onRewardAdLoaded(activity);
                ad.setFullScreenContentCallback(new FullScreenContentCallback() {
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        Log.d(TAG, "Rewarded ad dismissed.");
                        RewardVideoAdCallback.onRewardAdClose(activity);
                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                        Log.d(TAG, "Rewarded ad failed to show: " + adError.getMessage());
                        RewardVideoAdCallback.onRewardAdError(activity, adError.getCode(), adError.getMessage());
                    }

                    @Override
                    
                    public void onAdShowedFullScreenContent() {
                        Log.d(TAG, "Rewarded ad showed.");
                        RewardVideoAdCallback.onRewardAdShow(activity);
                    }
                });
                ad.show(activity, new OnUserEarnedRewardListener() {
                    @Override
                    public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                        Log.d(TAG, "Rewarded ad earned reward.");
                        RewardVideoAdCallback.onRewardAdRewarded(activity,
                                rewardItem != null ? rewardItem.getType() : null,
                                rewardItem != null ? rewardItem.getAmount() : 0);
                    }
                });
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                Log.d(TAG, "Rewarded ad failed to load: " + loadAdError.getMessage());
                RewardVideoAdCallback.onRewardAdError(activity, loadAdError.getCode(), loadAdError.getMessage());
            }
        });
    }

    /**
     * AdMob 插屏广告（String 广告位）。
     * 加载成功后回调 onInterstitialAdLoaded 并自动展示；
     * 展示回调 onInterstitialAdShow，关闭回调 onInterstitialAdClose，失败回调 onInterstitialAdError。
     *
     * @param activity 展示插屏所需的 Activity
     * @param adId     插屏广告位 ID
     */
    
    public static void AdMobInterstitialAd(@NonNull final Activity activity, String adId) {
        if (activity == null) {
            InterstitialAdCallback.onInterstitialAdError(null, -1, "插屏展示需要 Activity 上下文");
            return;
        }
        if (adId == null || adId.trim().isEmpty()) {
            InterstitialAdCallback.onInterstitialAdError(activity, -1, "广告位 ID 为空");
            return;
        }
        InterstitialAd.load(activity, adId, new AdRequest.Builder().build(), new InterstitialAdLoadCallback() {
            @Override
            
            public void onAdLoaded(@NonNull InterstitialAd ad) {
                Log.d(TAG, "Interstitial ad loaded.");
                ad.setFullScreenContentCallback(new FullScreenContentCallback() {
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        Log.d(TAG, "Interstitial ad dismissed.");
                        InterstitialAdCallback.onInterstitialAdClose(activity);
                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                        Log.d(TAG, "Interstitial ad failed to show: " + adError.getMessage());
                        InterstitialAdCallback.onInterstitialAdError(activity, adError.getCode(), adError.getMessage());
                    }

                    @Override
                    
                    public void onAdShowedFullScreenContent() {
                        Log.d(TAG, "Interstitial ad showed.");
                        InterstitialAdCallback.onInterstitialAdShow(activity);
                    }

                    @Override
                    
                    public void onAdImpression() {
                        Log.d(TAG, "Interstitial ad recorded an impression.");
                    }

                    @Override
                    public void onAdClicked() {
                        Log.d(TAG, "Interstitial ad was clicked.");
                    }
                });
                InterstitialAdCallback.onInterstitialAdLoaded(activity);
                // 加载成功后自动展示
                ad.show(activity);
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                Log.d(TAG, "Interstitial ad failed to load: " + loadAdError.getMessage());
                InterstitialAdCallback.onInterstitialAdError(activity, loadAdError.getCode(), loadAdError.getMessage());
            }
        });
    }

    /**
     * AdMob 插屏广告（long 广告位，内部转换为 String）。
     */
    
    public static void AdMobInterstitialAd(@NonNull final Activity activity, long adId) {
        AdMobInterstitialAd(activity, String.valueOf(adId));
    }

    /**
     * AdMob Banner 广告（String 广告位）。
     * 先清空容器并加入 AdView，加载成功后回调 onBannerAdLoaded，失败回调 onBannerAdError。
     *
     * @param activity  展示广告的页面
     * @param adId      Banner 广告位 ID
     * @param container Banner 容器，广告 View 会加入其中
     */
    
    public static void AdMobBannerAd(@NonNull final Activity activity, String adId, @NonNull final ViewGroup container) {
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
        final AdView adView = new AdView(activity);
        adView.setAdUnitId(adId);
        adView.setAdSize(AdSize.BANNER);
        container.removeAllViews();
        container.addView(adView);
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                Log.d(TAG, "Banner ad loaded.");
                BannerAdCallback.onBannerAdLoaded(activity, container);
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                Log.d(TAG, "Banner ad failed to load: " + loadAdError.getMessage());
                BannerAdCallback.onBannerAdError(activity, loadAdError.getCode(), loadAdError.getMessage());
            }

            @Override
            public void onAdOpened() {
                Log.d(TAG, "Banner ad opened.");
            }

            @Override
            public void onAdClosed() {
                Log.d(TAG, "Banner ad closed.");
            }

            @Override
            public void onAdClicked() {
                Log.d(TAG, "Banner ad clicked.");
            }

            @Override
            
            public void onAdImpression() {
                Log.d(TAG, "Banner ad recorded an impression.");
            }

            @Override
            public void onAdSwipeGestureClicked() {
            }
        });
        adView.loadAd(new AdRequest.Builder().build());
    }

    /**
     * AdMob Banner 广告（long 广告位，内部转换为 String）。
     */
    
    public static void AdMobBannerAd(@NonNull final Activity activity, long adId, @NonNull final ViewGroup container) {
        AdMobBannerAd(activity, String.valueOf(adId), container);
    }
}
