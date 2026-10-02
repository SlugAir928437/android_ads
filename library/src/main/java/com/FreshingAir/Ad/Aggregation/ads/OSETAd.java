package com.FreshingAir.Ad.Aggregation.ads;
import bin.mt.annotations.MTProtector;
import androidx.annotation.Keep;

import android.util.Log;
import android.app.Application;
import android.app.Activity;
import android.os.Bundle;

import android.view.ViewGroup;

import com.FreshingAir.Ad.Aggregation.AdPlatform;
import com.FreshingAir.Ad.Aggregation.Init;
import com.FreshingAir.Ad.Aggregation.RewardVideoAdCallback;
import com.kc.aw.lib.PtgAdSdk;
import com.kc.aw.lib.interf.PtgRewardVideoAd;
import com.kc.aw.lib.model.AdError;
import com.kc.aw.lib.provider.PtgAdNative;
import com.kc.openset.config.OSETSDK;
import com.kc.openset.listener.OSETInitListener;
import com.kc.openset.ad.listener.OSETSplashAdLoadListener;
import com.kc.openset.ad.listener.OSETSplashListener;
import com.kc.openset.ad.splash.OSETSplash;
import com.kc.openset.ad.splash.OSETSplashAd;
import com.FreshingAir.Ad.Aggregation.SplashAdCallback;
@MTProtector
public class OSETAd {
    private static final String TAG = "OSET广告 SDK";
    public static void initOSETSDK(Application application, String APPKEY){
        OSETSDK.getInstance()
//                .setCustomController(new OSETCustomController(){})
                .init(application, APPKEY, new OSETInitListener(){
                    @Override
                    @MTProtector
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
    
    @MTProtector
    public static void OSETSplashAd(Activity activity,String PosId,ViewGroup adContainer) {
        OSETSplashAdLoadListener osetSplashAdLoadListener ;
        osetSplashAdLoadListener = new OSETSplashAdLoadListener() {

            @Override
            public void onLoadFail(String s, String s1) {
                Log.e(TAG,"广告加载失败，错误信息："+s1+"("+s+")");
                SplashAdCallback.goToMainActivity(activity.getApplicationContext());
            }

            @Override
            @MTProtector
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
                    @MTProtector
                    public void onClose() {
                        Log.i(TAG,"广告关闭");
                        SplashAdCallback.goToMainActivity(adContainer.getContext());
                    }

                    @Override
                    @MTProtector
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
    @MTProtector
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
            @MTProtector
            public void onRewardVideoAdLoad(PtgRewardVideoAd ad) {
                Log.i(TAG, "激励视频加载成功");
                RewardVideoAdCallback.onRewardAdLoaded(activity);
                ad.setRewardAdInteractionListener(new PtgRewardVideoAd.RewardAdInteractionListener() {
                    @Override
                    @MTProtector
                    public void onAdClose() {
                        Log.i(TAG, "激励视频关闭");
                        RewardVideoAdCallback.onRewardAdClose(activity);
                    }

                    @Override
                    @MTProtector
                    public void onAdShow() {
                        Log.i(TAG, "激励视频开始播放");
                        RewardVideoAdCallback.onRewardAdShow(activity);
                    }

                    @Override
                    public void onAdVideoBarClick() {
                    }

                    @Override
                    @MTProtector
                    public void onRenderError(AdError adError) {
                        int code = adError != null ? adError.getErrorCode() : 0;
                        String msg = adError != null ? adError.getMessage() : null;
                        Log.e(TAG, "激励视频失败，错误信息：" + msg + "(" + code + ")");
                        RewardVideoAdCallback.onRewardAdError(activity, code, msg);
                    }

                    @Override
                    @MTProtector
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
                    @MTProtector
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
            @MTProtector
            public void onError(AdError adError) {
                int code = adError != null ? adError.getErrorCode() : 0;
                String msg = adError != null ? adError.getMessage() : null;
                Log.e(TAG, "激励视频加载失败，错误信息：" + msg + "(" + code + ")");
                RewardVideoAdCallback.onRewardAdError(activity, code, msg);
            }
        });
    }
}
