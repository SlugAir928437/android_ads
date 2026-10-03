package com.FreshingAir.Ad.Aggregation.ads;

import androidx.annotation.Keep;
import android.content.Context;
import android.os.Build;
import android.view.ViewGroup;

import com.FreshingAir.Ad.Aggregation.AdPlatform;
import com.FreshingAir.Ad.Aggregation.Init;
import com.FreshingAir.Ad.Aggregation.RewardVideoAdCallback;
import com.FreshingAir.Ad.Aggregation.InterstitialAdCallback;
import com.FreshingAir.Ad.Aggregation.BannerAdCallback;
import com.miui.zeus.mimo.sdk.MimoSdk;
import com.miui.zeus.mimo.sdk.ADParams;
import com.miui.zeus.mimo.sdk.InterstitialAd;
import com.miui.zeus.mimo.sdk.BannerAd;
import com.miui.zeus.mimo.sdk.RewardVideoAd;
import android.app.Activity;
import android.util.Log;
import com.miui.zeus.mimo.sdk.SplashAd;
import com.miui.zeus.mimo.sdk.SplashAd.SplashAdLoadListener;
import com.miui.zeus.mimo.sdk.SplashAd.SplashAdInteractionListener;
import com.FreshingAir.Ad.Aggregation.SplashAdCallback;

public class MimoAd {

    private static final String TAG = "米盟广告 SDK";
    private static boolean isXiaomiDevice = false;
    public static void initMimoSDK(Context context){
        //这个字符串可以自己定义,例如判断华为就填写huawei,魅族就填写meizu
        if ("xiaomi".equalsIgnoreCase(Build.MANUFACTURER)) {
            isXiaomiDevice = true;
            MimoSdk.init(context, new MimoSdk.InitCallback() {

                @Override
                
                public void success() {
                    Log.i(TAG,"SDK初始化成功");
                    Init.adSDKisLoaded.put(AdPlatform.MIMO, true);
                }

                @Override
                
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
                        
                        public void onAdClick() {
                            // 广告被点击
                            Log.i(TAG, "广告被点击");
                        }

                        @Override
                        
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
            
            public void onAdLoadSuccess() {
                Log.i(TAG, "激励视频加载成功");
                RewardVideoAdCallback.onRewardAdLoaded(context);
                rewardVideoAd.showAd(activity, new RewardVideoAd.RewardVideoInteractionListener() {
                    @Override
                    
                    public void onAdPresent() {
                        Log.i(TAG, "激励视频开始播放");
                        RewardVideoAdCallback.onRewardAdShow(context);
                    }

                    @Override
                    public void onAdClick() {
                    }

                    @Override
                    
                    public void onAdDismissed() {
                        Log.i(TAG, "激励视频关闭");
                        RewardVideoAdCallback.onRewardAdClose(context);
                    }

                    @Override
                    
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
                    
                    public void onReward() {
                        Log.i(TAG, "激励视频发放奖励");
                        RewardVideoAdCallback.onRewardAdRewarded(context, null, 0);
                    }
                });
            }

            @Override
            
            public void onAdLoadFailed(int errorCode, String errorMsg) {
                Log.e(TAG, "激励视频加载失败，错误信息：" + errorMsg + "(" + errorCode + ")");
                RewardVideoAdCallback.onRewardAdError(context, errorCode, errorMsg);
            }
        });
    }

    /**
     * 米盟插屏广告（String 广告位，仅小米设备可用）。
     * 加载成功后回调 loaded，并自动调用 show 展示。
     *
     * @param activity 展示插屏所需的 Activity
     * @param adId     插屏广告位 ID
     */
    
    public static void MimoInterstitialAd(Activity activity, String adId) {
        if (!isXiaomiDevice) {
            Log.i(TAG, "非小米设备，程序结束。");
            InterstitialAdCallback.onInterstitialAdError(activity, -1, "非小米设备，无法加载米盟广告");
            return;
        }
        if (activity == null) {
            InterstitialAdCallback.onInterstitialAdError(null, -1, "插屏展示需要 Activity 上下文");
            return;
        }
        if (adId == null || adId.isEmpty()) {
            InterstitialAdCallback.onInterstitialAdError(activity, -1, "广告位 ID 为空");
            return;
        }
        InterstitialAd ad = new InterstitialAd();
        ad.loadAd(adId, new InterstitialAd.InterstitialAdLoadListener() {
            @Override
            public void onAdLoadSuccess() {
                Log.i(TAG, "插屏广告加载成功");
                InterstitialAdCallback.onInterstitialAdLoaded(activity);
                ad.show(activity, new InterstitialAd.InterstitialAdInteractionListener() {
                    @Override
                    public void onAdClick() {
                    }

                    @Override
                    public void onAdShow() {
                        Log.i(TAG, "插屏广告展示");
                        InterstitialAdCallback.onInterstitialAdShow(activity);
                    }

                    @Override
                    public void onAdClosed() {
                        Log.i(TAG, "插屏广告关闭");
                        InterstitialAdCallback.onInterstitialAdClose(activity);
                    }

                    @Override
                    public void onRenderFail(int errorCode, String errorMsg) {
                        Log.e(TAG, "插屏广告渲染失败，错误信息：" + errorMsg + "(" + errorCode + ")");
                        InterstitialAdCallback.onInterstitialAdError(activity, errorCode, errorMsg);
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
                    public void onVideoEnd() {
                    }
                });
            }

            @Override
            public void onAdRequestSuccess() {
            }

            @Override
            
            public void onAdLoadFailed(int errorCode, String errorMsg) {
                Log.e(TAG, "插屏广告加载失败，错误信息：" + errorMsg + "(" + errorCode + ")");
                InterstitialAdCallback.onInterstitialAdError(activity, errorCode, errorMsg);
            }
        });
    }

    /**
     * 米盟插屏广告（long 广告位，内部转换为 String）。
     */
    
    public static void MimoInterstitialAd(Activity activity, long adId) {
        MimoInterstitialAd(activity, String.valueOf(adId));
    }

    /**
     * 米盟 Banner 广告（String 广告位，仅小米设备可用）。
     * 米盟 SDK 会自行把广告 View 加入传入的 container。
     *
     * @param activity  展示 Banner 所需的 Activity
     * @param adId      Banner 广告位 ID
     * @param container 承载 Banner 的容器
     */
    
    public static void MimoBannerAd(Activity activity, String adId, ViewGroup container) {
        if (!isXiaomiDevice) {
            Log.i(TAG, "非小米设备，程序结束。");
            BannerAdCallback.onBannerAdError(activity, -1, "非小米设备，无法加载米盟广告");
            return;
        }
        if (activity == null || container == null) {
            BannerAdCallback.onBannerAdError(activity, -1, "Banner 展示需要 Activity 与容器");
            return;
        }
        if (adId == null || adId.isEmpty()) {
            BannerAdCallback.onBannerAdError(activity, -1, "广告位 ID 为空");
            return;
        }
        container.removeAllViews();
        BannerAd ad = new BannerAd();
        ad.loadAd(adId, new BannerAd.BannerLoadListener() {
            @Override
            public void onBannerAdLoadSuccess() {
                Log.i(TAG, "Banner 广告加载成功");
                ad.showAd(activity, container, new BannerAd.BannerInteractionListener() {
                    @Override
                    public void onAdClick() {
                    }

                    @Override
                    public void onAdShow() {
                    }

                    @Override
                    public void onAdDismiss() {
                    }

                    @Override
                    public void onRenderSuccess() {
                        Log.i(TAG, "Banner 广告渲染成功");
                        BannerAdCallback.onBannerAdLoaded(activity, container);
                    }

                    @Override
                    public void onRenderFail(int errorCode, String errorMsg) {
                        Log.e(TAG, "Banner 广告渲染失败，错误信息：" + errorMsg + "(" + errorCode + ")");
                        BannerAdCallback.onBannerAdError(activity, errorCode, errorMsg);
                    }
                });
            }

            @Override
            
            public void onAdLoadFailed(int errorCode, String errorMsg) {
                Log.e(TAG, "Banner 广告加载失败，错误信息：" + errorMsg + "(" + errorCode + ")");
                BannerAdCallback.onBannerAdError(activity, errorCode, errorMsg);
            }
        });
    }

    /**
     * 米盟 Banner 广告（long 广告位，内部转换为 String）。
     */
    
    public static void MimoBannerAd(Activity activity, long adId, ViewGroup container) {
        MimoBannerAd(activity, String.valueOf(adId), container);
    }
}
