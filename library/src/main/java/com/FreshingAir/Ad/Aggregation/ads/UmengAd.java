package com.FreshingAir.Ad.Aggregation.ads;

import android.content.Context;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.FreshingAir.Ad.Aggregation.AdPlatform;
import com.FreshingAir.Ad.Aggregation.Init;
import com.FreshingAir.Ad.Aggregation.RewardVideoAdCallback;
import com.FreshingAir.Ad.Aggregation.SplashAdCallback;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.union.UMNativeAD;
import com.umeng.union.UMRewardAD;
import com.umeng.union.UMSplashAD;
import com.umeng.union.UMUnionSdk;
import com.umeng.union.api.UMAdConfig;
import com.umeng.union.api.UMUnionApi;
import com.umeng.union.widget.UMNativeLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 友盟+ U-AppWin（umeng-union）接入实现。
 *
 * 覆盖开屏与信息流两种形式：
 *   开屏   {@link UMUnionSdk#loadSplashAd} → {@link UMSplashAD#show(ViewGroup)}
 *   信息流 {@link UMUnionSdk#loadFeedAd}    → {@link UMNativeAD#bindView}
 */
public class UmengAd {

    /**
     * 初始化友盟+ 基础组件与广告联盟 SDK。
     *
     * 友盟的 appkey 由 {@link UMConfigure#init} 传入；广告位（slotId）在每次加载时通过
     * {@link UMAdConfig.Builder#setSlotId} 指定。
     *
     * @param appKey 友盟后台的应用 appkey，为空时 SDK 无法上报与拉取广告
     */
    public static void InitUmengSDK(Context context, String appKey) {
        // 合规要求：用户同意隐私政策后再初始化。这里由调用方保证时机，显式提交授权结果
        UMConfigure.submitPolicyGrantResult(context, true);
        UMConfigure.setLogEnabled(true);
        UMConfigure.init(context, appKey, "Umeng", UMConfigure.DEVICE_TYPE_PHONE, null);
        UMUnionSdk.init(context);
        Init.adSDKisLoaded.put(AdPlatform.UMENG, true);
    }

    /**
     * 加载友盟开屏广告。
     *
     * 广告位为空或加载失败 / 曝光结束 / 出错时，统一回调 {@link SplashAdCallback#goToMainActivity}，
     * 保证调用方页面一定会进入主页。
     */
    public static void UmengSplashAd(Context context, String slotId, @NonNull final ViewGroup adContainer) {
        if (slotId == null || slotId.trim().isEmpty()) {
            Log.e("UmengAd", "开屏广告位为空：请在友盟后台创建广告位后通过 loadSplashAd 的 splashAdId 传入");
            SplashAdCallback.goToMainActivity(context);
            return;
        }
        UMAdConfig adConfig = new UMAdConfig.Builder().setSlotId(slotId).build();
        UMUnionSdk.loadSplashAd(adConfig, new UMUnionApi.AdLoadListener<UMSplashAD>() {

            @Override
            public void onSuccess(UMUnionApi.AdType adType, UMSplashAD splashAD) {
                Log.i("UmengAd", "开屏加载成功:" + adType);
                splashAD.setAdEventListener(new UMUnionApi.SplashAdListener() {

                    @Override
                    public void onExposed() {
                        Log.i("UmengAd", "onExposed");
                    }

                    @Override
                    public void onDismissed() {
                        Log.i("UmengAd", "onDismissed");
                        SplashAdCallback.goToMainActivity(context);
                    }

                    @Override
                    public void onClicked(View view) {
                        Log.i("UmengAd", "onClicked");
                    }

                    @Override
                    public void onError(int code, String message) {
                        Log.e("UmengAd", "onError:" + code + " / " + message);
                        SplashAdCallback.goToMainActivity(context);
                    }
                });
                // 友盟的开屏由 SDK 自行渲染进容器，与倍孜的实现保持一致，渲染后回调容器
                splashAD.show(adContainer);
                SplashAdCallback.onSplashAdLoaded(context, adContainer);
            }

            @Override
            public void onFailure(UMUnionApi.AdType adType, String message) {
                Log.e("UmengAd", "开屏加载失败:" + adType + " / " + message);
                SplashAdCallback.goToMainActivity(context);
            }
        }, 5000);
    }

    /**
     * 加载友盟信息流（原生模板）广告并渲染进容器。
     */
    public static void UmengFeedAd(@NonNull Context context, String slotId, @NonNull final ViewGroup feedAdContainer) {
        if (slotId == null || slotId.trim().isEmpty()) {
            Log.e("UmengAd", "信息流广告位为空：请在友盟后台创建广告位后通过 loadFeedAd 的 feedAdId 传入");
            return;
        }
        UMAdConfig adConfig = new UMAdConfig.Builder().setSlotId(slotId).build();
        UMUnionSdk.loadFeedAd(adConfig, new UMUnionApi.AdLoadListener<UMNativeAD>() {

            @Override
            public void onSuccess(UMUnionApi.AdType adType, UMNativeAD nativeAD) {
                Log.i("UmengAd", "信息流加载成功:" + adType + " title=" + nativeAD.getTitle());
                UMNativeLayout nativeLayout = new UMNativeLayout(context);
                List<View> clickViews = new ArrayList<>();
                clickViews.add(nativeLayout);
                nativeAD.bindView(context, nativeLayout, clickViews);
                feedAdContainer.removeAllViews();
                feedAdContainer.addView(nativeLayout);
            }

            @Override
            public void onFailure(UMUnionApi.AdType adType, String message) {
                Log.e("UmengAd", "信息流加载失败:" + adType + " / " + message);
            }
        });
    }

    /**
     * 加载友盟激励视频广告并展示。
     *
     * 广告位为空时直接回调错误；加载成功、播放、发放奖励、关闭、失败分别回调
     * {@link RewardVideoAdCallback} 对应的出口方法。
     */
    public static void UmengRewardVideoAd(@NonNull Activity activity, String slotId) {
        if (slotId == null || slotId.trim().isEmpty()) {
            Log.e("UmengAd", "激励视频广告位为空：请在友盟后台创建广告位后通过 loadRewardVideoAd 的 rewardAdId 传入");
            RewardVideoAdCallback.onRewardAdError(activity, -1, "激励视频广告位为空");
            return;
        }
        UMAdConfig adConfig = new UMAdConfig.Builder().setSlotId(slotId).build();
        UMUnionSdk.loadRewardAd(adConfig, new UMUnionApi.AdLoadListener<UMRewardAD>() {

            @Override
            public void onSuccess(UMUnionApi.AdType adType, UMRewardAD rewardAD) {
                Log.i("UmengAd", "激励视频加载成功:" + adType);
                RewardVideoAdCallback.onRewardAdLoaded(activity);
                rewardAD.setAdEventListener(new UMUnionApi.RewardAdListener() {

                    @Override
                    public void onExposed() {
                        RewardVideoAdCallback.onRewardAdShow(activity);
                    }

                    @Override
                    public void onDismissed() {
                        RewardVideoAdCallback.onRewardAdClose(activity);
                    }

                    @Override
                    public void onClicked(View view) {
                    }

                    @Override
                    public void onError(int code, String message) {
                        Log.e("UmengAd", "激励视频出错:" + code + " / " + message);
                        RewardVideoAdCallback.onRewardAdError(activity, code, message);
                    }

                    @Override
                    public void onReward(boolean success, Map<String, Object> map) {
                        if (success) {
                            RewardVideoAdCallback.onRewardAdRewarded(activity, null, 0);
                        }
                    }
                });
                rewardAD.setVideoListener(new UMUnionApi.VideoListener() {
                    @Override
                    public void onReady() {
                    }

                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onPause() {
                    }

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(String message) {
                        Log.e("UmengAd", "激励视频播放失败:" + message);
                        RewardVideoAdCallback.onRewardAdError(activity, -1, message);
                    }
                });
                rewardAD.show();
            }

            @Override
            public void onFailure(UMUnionApi.AdType adType, String message) {
                Log.e("UmengAd", "激励视频加载失败:" + adType + " / " + message);
                RewardVideoAdCallback.onRewardAdError(activity, -1, message);
            }
        });
    }
}
