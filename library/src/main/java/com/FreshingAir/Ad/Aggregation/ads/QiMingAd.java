package com.FreshingAir.Ad.Aggregation.ads;
import bin.mt.annotations.MTProtector;
import androidx.annotation.Keep;

import android.content.Context;
import android.app.Activity;
import android.view.ViewGroup;
import android.view.View;

import com.FreshingAir.Ad.Aggregation.AdPlatform;
import com.FreshingAir.Ad.Aggregation.Init;
import com.FreshingAir.Ad.Aggregation.RewardVideoAdCallback;
import com.ja.adx.qiming.ad.RewardAd;
import com.ja.adx.qiming.ad.bean.RewardAdInfo;
import com.ja.adx.qiming.ad.bean.SplashAdInfo;
import com.ja.adx.qiming.ad.error.Error;
import com.ja.adx.qiming.ad.listener.RewardAdListener;
import com.ja.adx.qiming.QiMingADXSDK;
import com.ja.adx.qiming.config.InitConfig;
import android.util.Log;
import com.FreshingAir.Ad.Aggregation.BuildConfig;
import com.FreshingAir.Ad.Aggregation.SplashAdCallback;
@MTProtector
public class QiMingAd {

    private static final String TAG = "QiMingADX SDK";
    @MTProtector
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
            @MTProtector
            public void onAdClick(SplashAdInfo splashAdInfo) {
                // 广告点击回调
            }

            @Override
            @MTProtector
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
            @MTProtector
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
    @MTProtector
    public static void QiMingRewardVideoAd(Activity activity, String posId){
        RewardAd rewardAd = new RewardAd(activity);
        rewardAd.setListener(new RewardAdListener() {
            @Override
            @MTProtector
            public void onAdReceive(RewardAdInfo rewardAdInfo) {
                Log.i(TAG, "激励视频加载成功");
                RewardVideoAdCallback.onRewardAdLoaded(activity);
                if (rewardAdInfo != null) {
                    rewardAdInfo.showRewardAd(activity);
                }
            }

            @Override
            @MTProtector
            public void onAdExpose(RewardAdInfo rewardAdInfo) {
                Log.i(TAG, "激励视频开始播放");
                RewardVideoAdCallback.onRewardAdShow(activity);
            }

            @Override
            public void onAdClick(RewardAdInfo rewardAdInfo) {
            }

            @Override
            @MTProtector
            public void onAdClose(RewardAdInfo rewardAdInfo) {
                Log.i(TAG, "激励视频关闭");
                RewardVideoAdCallback.onRewardAdClose(activity);
            }

            @Override
            @MTProtector
            public void onAdFailed(Error error) {
                int code = error != null ? error.getCode() : 0;
                String msg = error != null ? error.getError() : null;
                Log.e(TAG, "激励视频加载失败，错误信息：" + msg + "(" + code + ")");
                RewardVideoAdCallback.onRewardAdError(activity, code, msg);
            }

            @Override
            @MTProtector
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
            @MTProtector
            public void onVideoError(RewardAdInfo rewardAdInfo, String errorMsg) {
                Log.e(TAG, "激励视频播放失败，错误信息：" + errorMsg);
                RewardVideoAdCallback.onRewardAdError(activity, -1, errorMsg);
            }
        });
        rewardAd.loadAd(posId);
    }
}
