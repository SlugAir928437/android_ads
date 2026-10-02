package com.FreshingAir.Ad.Aggregation.ads;
import bin.mt.annotations.MTProtector;
import androidx.annotation.Keep;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.FreshingAir.Ad.Aggregation.RewardVideoAdCallback;
import com.FreshingAir.Ad.Aggregation.SplashAdCallback;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.appopen.AppOpenAd;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

@MTProtector
public class AdMob {

    private static final String TAG = "AdMob Ad";
    @MTProtector
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
                                    @MTProtector
                                    public void onAdShowedFullScreenContent() {
                                        Log.d(TAG, "Ad showed fullscreen content.");
                                    }

                                    @Override
                                    @MTProtector
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
            @MTProtector
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
                    @MTProtector
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
}
