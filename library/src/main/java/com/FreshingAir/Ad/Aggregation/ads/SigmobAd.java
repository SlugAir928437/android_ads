package com.FreshingAir.Ad.Aggregation.ads;

import androidx.annotation.Keep;

import android.content.Context;
import android.app.Activity;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.FreshingAir.Ad.Aggregation.AdPlatform;
import com.FreshingAir.Ad.Aggregation.Init;
import com.FreshingAir.Ad.Aggregation.R;
import com.FreshingAir.Ad.Aggregation.RewardVideoAdCallback;
import com.FreshingAir.Ad.Aggregation.SplashAdCallback;
import com.FreshingAir.Ad.Aggregation.InterstitialAdCallback;
import com.FreshingAir.Ad.Aggregation.BannerAdCallback;
import com.sigmob.windad.OnInitializationListener;
import com.sigmob.windad.OnStartListener;
import com.sigmob.windad.Splash.WindSplashAD;
import com.sigmob.windad.Splash.WindSplashADListener;
import com.sigmob.windad.Splash.WindSplashAdRequest;
import com.sigmob.windad.WindAdError;
import com.sigmob.windad.WindAdOptions;
import com.sigmob.windad.WindAds;
import com.sigmob.windad.newInterstitial.WindNewInterstitialAd;
import com.sigmob.windad.newInterstitial.WindNewInterstitialAdListener;
import com.sigmob.windad.newInterstitial.WindNewInterstitialAdRequest;
import com.sigmob.windad.natives.NativeADEventListener;
import com.sigmob.windad.natives.NativeAdPatternType;
import com.sigmob.windad.natives.WindNativeAdData;
import com.sigmob.windad.natives.WindNativeAdRequest;
import com.sigmob.windad.natives.WindNativeUnifiedAd;
import com.sigmob.windad.rewardVideo.WindRewardAdRequest;
import com.sigmob.windad.rewardVideo.WindRewardInfo;
import com.sigmob.windad.rewardVideo.WindRewardVideoAd;
import com.sigmob.windad.rewardVideo.WindRewardVideoAdListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SigmobAd {

    private static final String TAG = "Sigmob广告 SDK";
    
    public static void initSigmobSDK(Context context, String appId, String appKey){
        WindAds ads = WindAds.sharedAds();
        WindAdOptions options = new WindAdOptions(appId, appKey);
// 设置自定义设备信息控制器（可选）
        ads.init(context,options, new OnInitializationListener() {

            @Override
            
            public void onInitializationSuccess() {
                Log.e(TAG, "SDK初始化成功");
                Init.adSDKisLoaded.put(AdPlatform.SIGMOB, true);
            }

            @Override
            public void onInitializationFail(String error) {
                Log.e(TAG, "SDK初始化失败，错误信息：" + error);
            }
        });
        ads.start(new OnStartListener() {
            @Override
            
            public void onStartSuccess() {
                Log.i(TAG, "SDK开始成功");
            }

            @Override
            public void onStartFail(String error) {
                Log.i(TAG, "SDK开始失败，错误信息："+error);
            }
        });
    }
    
    public static void SigmobSplashAd(Context context, String PLACEMENT_ID, String USER_ID, Map<String, Object> OPTIONS, ViewGroup adContainer){
        // PLACEMENT_ID 必填
        WindSplashAdRequest splashAdRequest = new WindSplashAdRequest(PLACEMENT_ID, USER_ID,OPTIONS);
        splashAdRequest.setDisableAutoHideAd(true);
        // 广告允许最大等待返回时间
        //splashAdRequest.setFetchDelay(5);
        WindSplashAD mWindSplashAD = new WindSplashAD(splashAdRequest, new WindSplashADListener() {
            @Override
            
            public void onSplashAdShow(String placementId) {
                Log.i(TAG, "广告曝光");
                SplashAdCallback.onSplashAdLoaded(context, adContainer);
            }

            @Override
            public void onSplashAdLoadSuccess(String placementId) {

                Log.i(TAG, "广告加载成功");
            }

            @Override
            
            public void onSplashAdLoadFail(WindAdError error, String placementId) {

                Log.i(TAG, "广告加载失败，错误信息："+error.getMessage()+"("+error.getErrorCode()+")");
                SplashAdCallback.goToMainActivity(context);
            }

            @Override
            
            public void onSplashAdShowError(WindAdError error, String placementId) {
                Log.i(TAG, "广告曝光失败，错误信息："+error.getMessage()+"("+error.getErrorCode()+")");
                SplashAdCallback.goToMainActivity(context);

            }

            @Override
            
            public void onSplashAdClick(String placementId) {

                Log.i(TAG, "用户点击了广告");
            }

            @Override
            
            public void onSplashAdClose(String placementId) {

                Log.i(TAG, "广告关闭");
                SplashAdCallback.goToMainActivity(context);
            }

            @Override
            public void onSplashAdSkip(String placementId) {

                Log.i(TAG, "广告被跳过");
                SplashAdCallback.goToMainActivity(context);
            }
        });
        mWindSplashAD.loadAndShow(adContainer); // 不需要再调用 mWindSplashAD.showAd();
    }
    public static void SigmobNativeAd(Context context, String placementId, String userId, @NonNull ViewGroup adContainer){
        adContainer.removeAllViews();

        View unifiedView = LayoutInflater.from(adContainer.getContext()).inflate(R.layout.ad_list,null);
        ImageView adImage = unifiedView.findViewById(R.id.ad_image);
        Button adButton = unifiedView.findViewById(R.id.ad_button);
        ViewGroup adVideo = unifiedView.findViewById(R.id.ad_video);
        ImageView adIcon = unifiedView.findViewById(R.id.ad_icon);
        TextView adTitle = unifiedView.findViewById(R.id.ad_title);
        ImageView adClose = unifiedView.findViewById(R.id.ad_close);

        WindNativeUnifiedAd windNativeUnifiedAd = new WindNativeUnifiedAd(new WindNativeAdRequest(placementId, userId, null));
        windNativeUnifiedAd.setNativeAdLoadListener(new WindNativeUnifiedAd.WindNativeAdLoadListener() {
            @Override
            
            public void onAdError(WindAdError error, String placementId) {
                Log.e("Sigmob广告","广告加载失败，错误信息："+error.getMessage()+"("+error.getErrorCode()+")");
                adContainer.removeAllViews();
            }
            @Override
            
            public void onAdLoad(List<WindNativeAdData> adDataList, String placementId) {
                Log.d("Sigmob广告", "onAdLoaded");
                WindNativeAdData windNativeAdData = adDataList.get(0);
                // 返回广告的标题
                String title = windNativeAdData.getTitle();
                if (!TextUtils.isEmpty(title)) {
                    adTitle.setVisibility(View.VISIBLE);
                    adTitle.setText(title);
                }

                // 返回广告的描述文本信息
                String CTA = windNativeAdData.getCTAText();
                if (!TextUtils.isEmpty(CTA)) {
                    adButton.setVisibility(View.VISIBLE);
                    adButton.setText(CTA);
                }

                // 返回广告的AppIcon的图片URL
                String iconUrl = windNativeAdData.getIconUrl();
                if (!TextUtils.isEmpty(iconUrl)) {
                    // 需要开发者自己处理图片URL，可使用图片加载框架去处理，本示例使用glide加载仅供参考
                    Glide.with(context).load(iconUrl).into(adIcon);
                }

                if (windNativeAdData.getAdPatternType() == NativeAdPatternType.NATIVE_VIDEO_AD) {
                    // 视频容器背景默认设置为黑色
                    adVideo.setBackgroundColor(Color.BLACK);
                    windNativeAdData.bindMediaView(adVideo, new WindNativeAdData.NativeADMediaListener() {
                        @Override
                        public void onVideoStart() {
                            Log.d("Sigmob广告", "onVideoStart: ");
                        }

                        @Override
                        public void onVideoPause() {
                            Log.d("Sigmob广告", "onVideoPause: ");
                        }

                        @Override
                        public void onVideoResume() {
                            Log.d("Sigmob广告", "onVideoResume: ");
                        }

                        @Override
                        
                        public void onVideoCompleted() {
                            windNativeAdData.startVideo();
                            Log.d("Sigmob广告", "onVideoCompleted: ");
                        }

                        @Override
                        
                        public void onVideoError(WindAdError windAdError) {
                            Log.d("Sigmob广告", "onVideoError: " + windAdError.toString());
                            adContainer.removeAllViews();
                        }

                        @Override
                        public void onVideoLoad() {
                            Log.d("Sigmob广告", "onVideoLoad: ");
                        }
                    });
                } else if (windNativeAdData.getAdPatternType() == NativeAdPatternType.NATIVE_BIG_IMAGE_AD) {
                    List<ImageView> imageViews=new ArrayList<>();
                    imageViews.add(adImage);
                    windNativeAdData.bindImageViews(imageViews, 0);
                }
                // 把允许点击的View添加到集合里面
                ArrayList<View> clickViews = new ArrayList<>();
                clickViews.add(adImage);
                clickViews.add(adVideo);
                clickViews.add(adButton);
                windNativeAdData.bindViewForInteraction(adContainer, clickViews, clickViews, null, new NativeADEventListener() {
                    @Override
                    public void onAdExposed() {

                    }

                    @Override
                    
                    public void onAdClicked() {

                    }

                    @Override
                    public void onAdDetailShow() {

                    }

                    @Override
                    public void onAdDetailDismiss() {

                    }

                    @Override
                    
                    public void onAdError(WindAdError error) {
                        adContainer.removeAllViews();
                    }
                });
                adClose.setOnClickListener(v -> adContainer.removeAllViews());
            }
        });
        windNativeUnifiedAd.loadAd(1);
        // 把自定义View添加到广告容器里面
        adContainer.removeAllViews();
        adContainer.addView(unifiedView);
    }

    /**
     * 加载并展示 Sigmob 激励视频广告。
     *
     * @param placementId 激励视频广告位 ID
     * @param userId      用户标识，可传 null
     */
    
    public static void SigmobRewardVideoAd(Context context, String placementId, String userId){
        final WindRewardVideoAd[] holder = new WindRewardVideoAd[1];
        WindRewardVideoAd rewardVideoAd = new WindRewardVideoAd(new WindRewardAdRequest(placementId, userId, null));
        rewardVideoAd.setWindRewardVideoAdListener(new WindRewardVideoAdListener() {
            @Override
            
            public void onRewardAdLoadSuccess(String placementId) {
                Log.i(TAG, "激励视频加载成功");
                RewardVideoAdCallback.onRewardAdLoaded(context);
                if (holder[0] != null) {
                    holder[0].show(new HashMap<String, String>());
                }
            }

            @Override
            public void onRewardAdPreLoadSuccess(String placementId) {
            }

            @Override
            public void onRewardAdPreLoadFail(String placementId) {
            }

            @Override
            
            public void onRewardAdPlayStart(String placementId) {
                Log.i(TAG, "激励视频开始播放");
                RewardVideoAdCallback.onRewardAdShow(context);
            }

            @Override
            public void onRewardAdPlayEnd(String placementId) {
            }

            @Override
            public void onRewardAdClicked(String placementId) {
            }

            @Override
            
            public void onRewardAdClosed(String placementId) {
                Log.i(TAG, "激励视频关闭");
                RewardVideoAdCallback.onRewardAdClose(context);
            }

            @Override
            
            public void onRewardAdRewarded(WindRewardInfo windRewardInfo, String placementId) {
                Log.i(TAG, "激励视频发放奖励");
                RewardVideoAdCallback.onRewardAdRewarded(context, null, 0);
            }

            @Override
            
            public void onRewardAdLoadError(WindAdError error, String placementId) {
                int code = error != null ? error.getErrorCode() : 0;
                String msg = error != null ? error.getMessage() : null;
                Log.e(TAG, "激励视频加载失败，错误信息：" + msg + "(" + code + ")");
                RewardVideoAdCallback.onRewardAdError(context, code, msg);
            }

            @Override
            
            public void onRewardAdPlayError(WindAdError error, String placementId) {
                int code = error != null ? error.getErrorCode() : 0;
                String msg = error != null ? error.getMessage() : null;
                Log.e(TAG, "激励视频播放失败，错误信息：" + msg + "(" + code + ")");
                RewardVideoAdCallback.onRewardAdError(context, code, msg);
            }
        });
        holder[0] = rewardVideoAd;
        rewardVideoAd.loadAd();
    }

    /**
     * Sigmob 插屏广告（String 广告位）。
     * 加载成功后回调 loaded，并自动调用 show 展示。
     *
     * 注意：WindNewInterstitialAdRequest 的第 1 个参数为 placementId（推断，需真机验证）。
     *
     * @param activity 展示插屏所需的 Activity
     * @param adId     插屏广告位 ID
     */
    
    public static void SigmobInterstitialAd(Activity activity, String adId) {
        if (activity == null) {
            InterstitialAdCallback.onInterstitialAdError(null, -1, "插屏展示需要 Activity 上下文");
            return;
        }
        if (adId == null || adId.isEmpty()) {
            InterstitialAdCallback.onInterstitialAdError(activity, -1, "广告位 ID 为空");
            return;
        }
        final WindNewInterstitialAd[] holder = new WindNewInterstitialAd[1];
        WindNewInterstitialAd ad = new WindNewInterstitialAd(
                new WindNewInterstitialAdRequest(adId, null, null));
        ad.setWindNewInterstitialAdListener(new WindNewInterstitialAdListener() {
            @Override
            public void onInterstitialAdLoadSuccess(String placementId) {
                Log.i(TAG, "插屏广告加载成功");
                InterstitialAdCallback.onInterstitialAdLoaded(activity);
                if (holder[0] != null) {
                    holder[0].show(new HashMap<String, String>());
                }
            }

            @Override
            public void onInterstitialAdPreLoadSuccess(String placementId) {
            }

            @Override
            public void onInterstitialAdPreLoadFail(String placementId) {
            }

            @Override
            public void onInterstitialAdShow(String placementId) {
                Log.i(TAG, "插屏广告展示");
                InterstitialAdCallback.onInterstitialAdShow(activity);
            }

            @Override
            public void onInterstitialAdClicked(String placementId) {
            }

            @Override
            public void onInterstitialAdClosed(String placementId) {
                Log.i(TAG, "插屏广告关闭");
                InterstitialAdCallback.onInterstitialAdClose(activity);
            }

            @Override
            public void onInterstitialAdLoadError(WindAdError error, String placementId) {
                int code = error != null ? error.getErrorCode() : 0;
                String msg = error != null ? error.getMessage() : null;
                Log.e(TAG, "插屏广告加载失败，错误信息：" + msg + "(" + code + ")");
                InterstitialAdCallback.onInterstitialAdError(activity, code, msg);
            }

            @Override
            public void onInterstitialAdShowError(WindAdError error, String placementId) {
                int code = error != null ? error.getErrorCode() : 0;
                String msg = error != null ? error.getMessage() : null;
                Log.e(TAG, "插屏广告展示失败，错误信息：" + msg + "(" + code + ")");
                InterstitialAdCallback.onInterstitialAdError(activity, code, msg);
            }
        });
        holder[0] = ad;
        ad.loadAd();
    }

    /**
     * Sigmob 插屏广告（long 广告位，内部转换为 String）。
     */
    
    public static void SigmobInterstitialAd(Activity activity, long adId) {
        SigmobInterstitialAd(activity, String.valueOf(adId));
    }

    /**
     * Sigmob SDK 未提供 Banner API，直接回调错误，不抛异常。
     */
    
    public static void SigmobBannerAd(Activity activity, String adId, ViewGroup container) {
        Log.e(TAG, "Sigmob SDK 无 Banner API，不支持 Banner 广告");
        BannerAdCallback.onBannerAdError(activity, -1, "Sigmob SDK 不支持 Banner 广告");
    }

    /**
     * Sigmob Banner（long 广告位），同样不支持，直接回调错误。
     */
    
    public static void SigmobBannerAd(Activity activity, long adId, ViewGroup container) {
        SigmobBannerAd(activity, String.valueOf(adId), container);
    }
}
