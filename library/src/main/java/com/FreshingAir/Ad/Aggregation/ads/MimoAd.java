package com.FreshingAir.Ad.Aggregation.ads;
import bin.mt.annotations.MTProtector;
import androidx.annotation.Keep;
import android.content.Context;
import android.os.Build;
import android.view.ViewGroup;

import com.FreshingAir.Ad.Aggregation.AdPlatform;
import com.FreshingAir.Ad.Aggregation.Init;
import com.FreshingAir.Ad.Aggregation.RewardVideoAdCallback;
import com.miui.zeus.mimo.sdk.MimoSdk;
import com.miui.zeus.mimo.sdk.ADParams;
import com.miui.zeus.mimo.sdk.RewardVideoAd;
import android.app.Activity;
import android.util.Log;
import com.miui.zeus.mimo.sdk.SplashAd;
import com.miui.zeus.mimo.sdk.SplashAd.SplashAdLoadListener;
import com.miui.zeus.mimo.sdk.SplashAd.SplashAdInteractionListener;
import com.FreshingAir.Ad.Aggregation.SplashAdCallback;
@MTProtector
public class MimoAd {

    private static final String TAG = "米盟广告 SDK";
    private static boolean isXiaomiDevice = false;
    public static void initMimoSDK(Context context){
        //这个字符串可以自己定义,例如判断华为就填写huawei,魅族就填写meizu
        if ("xiaomi".equalsIgnoreCase(Build.MANUFACTURER)) {
            isXiaomiDevice = true;
            MimoSdk.init(context, new MimoSdk.InitCallback() {

                @Override
                @MTProtector
                public void success() {
                    Log.i(TAG,"SDK初始化成功");
                    Init.adSDKisLoaded.put(AdPlatform.MIMO, true);
                }

                @Override
                @MTProtector
                public void fail(int code, String msg) {
                    Log.e(TAG,"SDK初始化失败，错误信息："+msg+"("+code+")");
                }
            });
        } else {
            Log.i(TAG, "非小米设备，程序结束。");
        }
    }
    
    public static void MimoSplashAd(Context context,String upId,ViewGroup container){
        if(isXiaomiDevice) {
            SplashAd splashAd = new SplashAd();
            ADParams params = new ADParams.Builder().setUpId(upId).build();
            splashAd.loadAd(params, new SplashAdLoadListener() {

                @Override
                public void onAdRequestSuccess() {
                    Log.i(TAG, "广告请求成功");
                    // 广告请求成功
                }

                @Override
                public void onAdLoaded() {
                    // 广告加载成功，在需要的时候在此处展示广告

                    splashAd.showAd(container, new SplashAdInteractionListener() {

                        @Override
                        public void onAdShow() {
                            // 广告展示
                            Log.i(TAG, "广告曝光");
                            SplashAdCallback.onSplashAdLoaded(context, container);
                        }

                        @Override
                        @MTProtector
                        public void onAdClick() {
                            // 广告被点击
                            Log.i(TAG, "广告被点击");
                        }

                        @Override
                        @MTProtector
                        public void onAdDismissed() {

                            Log.i(TAG, "广告被关闭");
                            // 点击关闭按钮广告消失回调
                            SplashAdCallback.goToMainActivity(context);
                        }

                        @Override
                        public void onAdRenderFailed(int errorCode, String errorMsg) {
                            //广告渲染失败
                            //container.setVisibility(View.GONE);
                            Log.e(TAG, "广告渲染失败，错误信息：" + errorMsg + "(" + errorCode + ")");
                            SplashAdCallback.goToMainActivity(context);
                        }
                    });
                }

                @Override
                public void onAdLoadFailed(int errorCode, String errorMsg) {
                    // 广告加载失败
                    Log.e(TAG, "广告加载失败，错误信息：" + errorMsg + "(" + errorCode + ")");
                    SplashAdCallback.goToMainActivity(context);
                }
            });
        } else {
            Log.i(TAG, "非小米设备，程序结束。");
            SplashAdCallback.goToMainActivity(context);
        }
    }

    /**
     * 加载并展示米盟激励视频广告（仅小米设备可用）。
     *
     * @param context 需要是 Activity（激励视频展示依赖 Activity）
     * @param upId    激励视频广告位 ID
     */
    @MTProtector
    public static void MimoRewardVideoAd(Context context, String upId){
        if (!isXiaomiDevice) {
            Log.i(TAG, "非小米设备，程序结束。");
            RewardVideoAdCallback.onRewardAdError(context, -1, "非小米设备，无法加载米盟广告");
            return;
        }
        Activity activity = context instanceof Activity ? (Activity) context : null;
        if (activity == null) {
            RewardVideoAdCallback.onRewardAdError(context, -1, "激励视频展示需要 Activity 上下文");
            return;
        }
        RewardVideoAd rewardVideoAd = new RewardVideoAd();
        ADParams params = new ADParams.Builder().setUpId(upId).build();
        rewardVideoAd.loadAd(params, new RewardVideoAd.RewardVideoLoadListener() {
            @Override
            public void onAdRequestSuccess() {
            }

            @Override
            @MTProtector
            public void onAdLoadSuccess() {
                Log.i(TAG, "激励视频加载成功");
                RewardVideoAdCallback.onRewardAdLoaded(context);
                rewardVideoAd.showAd(activity, new RewardVideoAd.RewardVideoInteractionListener() {
                    @Override
                    @MTProtector
                    public void onAdPresent() {
                        Log.i(TAG, "激励视频开始播放");
                        RewardVideoAdCallback.onRewardAdShow(context);
                    }

                    @Override
                    public void onAdClick() {
                    }

                    @Override
                    @MTProtector
                    public void onAdDismissed() {
                        Log.i(TAG, "激励视频关闭");
                        RewardVideoAdCallback.onRewardAdClose(context);
                    }

                    @Override
                    @MTProtector
                    public void onAdFailed(String errorMsg) {
                        Log.e(TAG, "激励视频失败，错误信息：" + errorMsg);
                        RewardVideoAdCallback.onRewardAdError(context, -1, errorMsg);
                    }

                    @Override
                    public void onVideoStart() {
                    }

                    @Override
                    public void onVideoPause() {
                    }

                    @Override
                    public void onVideoSkip() {
                    }

                    @Override
                    public void onVideoComplete() {
                    }

                    @Override
                    public void onPicAdEnd() {
                    }

                    @Override
                    @MTProtector
                    public void onReward() {
                        Log.i(TAG, "激励视频发放奖励");
                        RewardVideoAdCallback.onRewardAdRewarded(context, null, 0);
                    }
                });
            }

            @Override
            @MTProtector
            public void onAdLoadFailed(int errorCode, String errorMsg) {
                Log.e(TAG, "激励视频加载失败，错误信息：" + errorMsg + "(" + errorCode + ")");
                RewardVideoAdCallback.onRewardAdError(context, errorCode, errorMsg);
            }
        });
    }
}
