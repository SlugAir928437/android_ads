package com.FreshingAir.Ad.Aggregation.ads;

import androidx.annotation.Keep;

import android.content.Context;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.util.Log;
import com.FreshingAir.Ad.Aggregation.BuildConfig;
import android.util.DisplayMetrics;

import androidx.annotation.NonNull;

import com.FreshingAir.Ad.Aggregation.AdPlatform;
import com.FreshingAir.Ad.Aggregation.BannerAdCallback;
import com.FreshingAir.Ad.Aggregation.Init;
import com.FreshingAir.Ad.Aggregation.InterstitialAdCallback;
import com.FreshingAir.Ad.Aggregation.RewardVideoAdCallback;
import com.tapsdk.tapad.AdRequest;
import com.tapsdk.tapad.TapAdManager;
import com.tapsdk.tapad.TapAdNative;
import com.tapsdk.tapad.TapBannerAd;
import com.tapsdk.tapad.TapFeedAd;
import com.tapsdk.tapad.TapInterstitialAd;
import com.tapsdk.tapad.TapRewardVideoAd;
import com.tapsdk.tapad.TapSplashAd;
import com.tapsdk.tapad.TapAdConfig;
import com.tapsdk.tapad.TapAdSdk;
import com.FreshingAir.Ad.Aggregation.SplashAdCallback;
import com.tapsdk.tapad.feed.FeedOption;

import java.util.List;


public class TaptapAd {

    private static final String TAG = "DirichletAD SDK";
    public static void InitTaptapSDK(Context context,long MediaId,String MediaName,String MediaKey){
        // 构建SDK配置对象
        TapAdConfig config = new TapAdConfig.Builder()
                .withMediaId(MediaId)           // 必填：媒体ID（在 Dirichlet Ad 平台申请）
                .withMediaName(MediaName)     // 必填：媒体名称
                .withMediaKey(MediaKey)       // 必填：媒体密钥（用于签名验证）
                .enableDebug(BuildConfig.DEBUG)                   // 选填：是否开启调试模式（正式版务必设置为false）
                .shakeEnabled(true)                   // 选填：是否启用摇一摇广告交互功能，默认true开启
                .build();

        // 初始化 Dirichlet Ad SDK
        // 注意：必须在Application的onCreate中初始化，且只需初始化一次
        TapAdSdk.init(context, config);
// enableFrequencyControl: true-启用频控，false-不启用频控
// 当启用频控时，SDK会记录是否已经请求过权限，避免重复弹窗
//        TapAdManager.get().requestPermissionIfNecessary(context, true);
        Init.adSDKisLoaded.put(AdPlatform.TAPTAP, true);
    }
    
    public static void TaptapSplashAd(Context context,long SpaceId,Activity activity,ViewGroup container){
        // 第一步：创建广告加载器
        // 注意：每个页面建议创建独立的TapAdNative实例
        TapAdNative tapAdNative = TapAdManager.get().createAdNative(context);

        // 第二步：获取屏幕尺寸（可选，用于优化广告展示效果）
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int screenWidth = displayMetrics.widthPixels;
        int screenHeight = displayMetrics.heightPixels;

        // 第三步：构建广告请求参数
        AdRequest adRequest = new AdRequest.Builder()
                .withSpaceId(SpaceId)    // 必填：开屏广告位ID（在 Dirichlet Ad 后台获取）
                .withExpressViewAcceptedSize(screenWidth, screenHeight)
                // 选填：期望的广告尺寸（像素），建议传入屏幕实际宽高
                .build();

        // 第四步：加载开屏广告
        tapAdNative.loadSplashAd(adRequest, new TapAdNative.SplashAdListener() {

            /**
             * 广告加载成功回调
             * @param splashAd 开屏广告对象，用于后续展示
             */
            @Override
            public void onSplashAdLoad(TapSplashAd splashAd) {
                Log.d(TAG, "开屏广告加载成功");
                // 第一步：设置广告交互监听器
                splashAd.setSplashInteractionListener(new TapSplashAd.AdInteractionListener() {

                    /**
                     * 用户点击跳过按钮
                     * 在此进行资源清理和页面跳转
                     */
                    @Override
                    public void onAdSkip() {
                        Log.d(TAG, "用户点击跳过开屏广告");
                        // 销毁广告视图
                        splashAd.destroyView();
                        // 释放广告资源
                        splashAd.dispose();
                        // 跳转到主页
                        SplashAdCallback.goToMainActivity(context);
                    }

                    /**
                     * 广告倒计时结束（自动关闭）
                     * 通常为3-5秒后自动触发
                     */
                    @Override
                    public void onAdTimeOver() {
                        Log.d(TAG, "开屏广告倒计时结束");

                        // 销毁广告视图
                        splashAd.destroyView();
                        // 释放广告资源
                        splashAd.dispose();
                        // 跳转到主页
                        SplashAdCallback.goToMainActivity(context);
                    }

                    /**
                     * 用户点击广告（跳转到落地页）
                     */
                    @Override
                    public void onAdClick() {
                        Log.d(TAG, "用户点击开屏广告");
                        // 通常无需特殊处理，SDK会自动处理跳转
                    }

                    /**
                     * 广告开始展示
                     */
                    @Override
                    public void onAdShow() {
                        Log.d(TAG, "开屏广告开始展示");
                        // 可以在此记录广告展示事件
                        SplashAdCallback.onSplashAdLoaded(context, container);
                    }

                    /**
                     * 广告有效曝光（满足曝光条件）
                     * 曝光条件通常包括：展示时长、可见面积等
                     */
                    @Override
                    public void onAdValidShow() {
                        Log.d(TAG, "开屏广告有效曝光");
                        // 建议在此上报广告曝光事件用于数据分析
                    }
                });

                // 第二步：获取广告View
                View splashView = splashAd.getSplashView(activity);

                // 第三步：从父容器中移除（如果已添加过）
                // 这一步是为了避免重复添加导致的异常
                if (splashView.getParent() != null) {
                    ((ViewGroup) splashView.getParent()).removeView(splashView);
                }

                // 第四步：添加到布局容器
                ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,  // 宽度充满父容器
                        ViewGroup.LayoutParams.MATCH_PARENT   // 高度充满父容器
                );
                container.addView(splashView, layoutParams);
            }

            @Override
            
            public void onError(int code, String message) {
                Log.e(TAG, "开屏广告加载失败，错误信息：" + message+"("+code+")");
                // 根据错误码进行相应处理
//                switch (code) {
//                    case 102001:  // 网络异常
//                    case 102002:  // 网络超时
//                    case 102003:  // 无网络连接
//                        // 可以提示用户检查网络或直接跳过
//                        break;
//                    case 103004:  // 请求频繁
//                        // 建议延迟一段时间后重试
//                        break;
//                    case 103002:  // 无广告填充
//                        // 正常情况，直接跳过
//                        break;
//                    default:
//                        // 其他错误，建议跳过广告直接进入应用
//                        break;
//                }
                SplashAdCallback.goToMainActivity(context);
            }
        });
    }
    public static void TaptapNativeAd(Activity activity, long spaceId, @NonNull ViewGroup adContainer){
        // 注意，一个 Activity/Fragment 中只需要创建一个 TapAdNative 对象
        adContainer.removeAllViews();
        TapAdNative tapAdNative = TapAdManager.get().createAdNative(activity);
// 广告后台获取广告位 id
        tapAdNative.loadFeedAd(new com.tapsdk.tapad.AdRequest.Builder()
//                .withQuery("{QUERY}")                 //  搜索词,可选
                        .withSpaceId(spaceId)
                        .build(),
                new TapAdNative.FeedAdListener() {
                    @Override
                    public void onFeedAdLoad(List<TapFeedAd> list) {
                        TapFeedAd tapFeedAd = list.get(0);
                        tapFeedAd.setExpressRenderListener(new TapFeedAd.ExpressRenderListener() {
                            @Override
                            public void onRenderSuccess(View view) {
                                Log.i(TAG,"广告渲染成功");
                                adContainer.addView(view);
                            }

                            @Override
                            
                            public void onRenderFail(View view, TapFeedAd tapFeedAd, int i, String s) {
                                Log.e(TAG,"广告渲染失败，错误信息：" + s + "(" + i + ")");
                            }

                            @Override
                            public void onAdShow(View view) {
                                Log.i(TAG,"广告展示");
                            }

                            @Override
                            
                            public void onAdClicked(View view) {
                                Log.i(TAG,"广告被点击");
                            }

                            @Override
                            
                            public void onAdClosed(View view) {

                                Log.i(TAG,"广告关闭");
                            }

                            @Override
                            
                            public void onAdValidShow(View view) {
                                Log.i(TAG,"广告有效曝光");
                            }
                        });
                        tapFeedAd.render(new FeedOption.Builder().build());
                    }

                    @Override
                    
                    public void onError(int code, String message) {
                        // 获取广告失败
                        Log.e(TAG,"获取广告失败，错误信息："+message+"("+code+")");
                    }

                });
    }

    /**
     * 加载并展示 Taptap（Dirichlet Ad）激励视频广告。
     *
     * @param activity 展示广告的页面
     * @param spaceId  激励视频广告位 ID（long）
     */
    
    public static void TaptapRewardVideoAd(Activity activity, long spaceId){
        TapAdNative tapAdNative = TapAdManager.get().createAdNative(activity);
        AdRequest adRequest = new AdRequest.Builder()
                .withSpaceId(spaceId)
                .build();
        tapAdNative.loadRewardVideoAd(adRequest, new TapAdNative.RewardVideoAdListener() {
            @Override
            
            public void onRewardVideoAdLoad(TapRewardVideoAd ad) {
                Log.i(TAG, "激励视频加载成功");
                RewardVideoAdCallback.onRewardAdLoaded(activity);
                ad.setRewardAdInteractionListener(new TapRewardVideoAd.RewardAdInteractionListener() {
                    @Override
                    
                    public void onAdShow(TapRewardVideoAd ad) {
                        Log.i(TAG, "激励视频开始播放");
                        RewardVideoAdCallback.onRewardAdShow(activity);
                    }

                    @Override
                    
                    public void onAdClose(TapRewardVideoAd ad) {
                        Log.i(TAG, "激励视频关闭");
                        RewardVideoAdCallback.onRewardAdClose(activity);
                    }

                    @Override
                    public void onVideoComplete(TapRewardVideoAd ad) {
                    }

                    @Override
                    
                    public void onVideoError(TapRewardVideoAd ad) {
                        Log.e(TAG, "激励视频播放失败");
                        RewardVideoAdCallback.onRewardAdError(activity, -1, "激励视频播放失败");
                    }

                    @Override
                    
                    public void onRewardVerify(TapRewardVideoAd ad, boolean verify, int amount, String name, int code, String msg) {
                        if (verify) {
                            Log.i(TAG, "激励视频发放奖励：" + name + "x" + amount);
                            RewardVideoAdCallback.onRewardAdRewarded(activity, name, amount);
                        }
                    }

                    @Override
                    public void onSkippedVideo(TapRewardVideoAd ad) {
                    }

                    @Override
                    public void onAdClick(TapRewardVideoAd ad) {
                    }

                    @Override
                    public void onAdValidShow(TapRewardVideoAd ad) {
                    }
                });
                ad.showRewardVideoAd(activity);
            }

            @Override
            public void onRewardVideoCached(TapRewardVideoAd ad) {
            }

            @Override
            
            public void onError(int code, String message) {
                Log.e(TAG, "激励视频加载失败，错误信息：" + message + "(" + code + ")");
                RewardVideoAdCallback.onRewardAdError(activity, code, message);
            }
        });
    }

    /**
     * 加载并展示 Taptap 插屏广告（加载成功后自动展示）。
     *
     * @param activity 展示广告的页面
     * @param spaceId  插屏广告位 ID（long）
     */
    
    public static void TaptapInterstitialAd(Activity activity, long spaceId) {
        if (activity == null || spaceId <= 0) {
            InterstitialAdCallback.onInterstitialAdError(activity, -1, "参数非法（Activity 为空或广告位非法）");
            return;
        }
        TapAdNative tapAdNative = TapAdManager.get().createAdNative(activity);
        DisplayMetrics displayMetrics = activity.getResources().getDisplayMetrics();
        AdRequest adRequest = new AdRequest.Builder()
                .withSpaceId(spaceId)
                .withExpressViewAcceptedSize(displayMetrics.widthPixels, displayMetrics.heightPixels)
                .build();
        tapAdNative.loadInterstitialAd(adRequest, new TapAdNative.InterstitialAdListener() {
            @Override
            public void onInterstitialAdLoad(TapInterstitialAd ad) {
                Log.i(TAG, "插屏广告加载成功");
                InterstitialAdCallback.onInterstitialAdLoaded(activity);
                if (ad == null) {
                    InterstitialAdCallback.onInterstitialAdError(activity, -1, "插屏广告数据为空");
                    return;
                }
                ad.setInteractionListener(new TapInterstitialAd.InterstitialAdInteractionListener() {
                    @Override
                    
                    public void onAdShow() {
                        Log.i(TAG, "插屏广告展示");
                        InterstitialAdCallback.onInterstitialAdShow(activity);
                    }

                    @Override
                    
                    public void onAdClose() {
                        Log.i(TAG, "插屏广告关闭");
                        InterstitialAdCallback.onInterstitialAdClose(activity);
                    }

                    @Override
                    
                    public void onAdError() {
                        Log.e(TAG, "插屏广告展示失败");
                        InterstitialAdCallback.onInterstitialAdError(activity, -1, "插屏广告展示失败");
                    }

                    @Override
                    public void onAdValidShow() {
                    }

                    @Override
                    public void onAdClick() {
                    }
                });
                ad.show(activity);
            }

            @Override
            
            public void onError(int code, String message) {
                Log.e(TAG, "插屏广告加载失败，错误信息：" + message + "(" + code + ")");
                InterstitialAdCallback.onInterstitialAdError(activity, code, message);
            }
        });
    }

    /**
     * 加载并展示 Taptap 插屏广告（广告位为 String 时的便捷重载）。
     */
    public static void TaptapInterstitialAd(Activity activity, String adId) {
        long spaceId;
        try {
            spaceId = Long.parseLong(adId);
        } catch (Exception e) {
            InterstitialAdCallback.onInterstitialAdError(activity, -1, "广告位不是合法的 long：" + adId);
            return;
        }
        TaptapInterstitialAd(activity, spaceId);
    }

    /**
     * 加载并展示 Taptap Banner 广告。
     *
     * @param activity  展示广告的页面
     * @param spaceId   Banner 广告位 ID（long）
     * @param container 承载广告 View 的容器
     */
    
    public static void TaptapBannerAd(Activity activity, long spaceId, @NonNull ViewGroup container) {
        if (activity == null || container == null || spaceId <= 0) {
            BannerAdCallback.onBannerAdError(activity, -1, "参数非法（Activity/容器为空或广告位非法）");
            return;
        }
        container.removeAllViews();
        TapAdNative tapAdNative = TapAdManager.get().createAdNative(activity);
        DisplayMetrics displayMetrics = activity.getResources().getDisplayMetrics();
        int width = container.getMeasuredWidth() > 0 ? container.getMeasuredWidth() : displayMetrics.widthPixels;
        int height = container.getMeasuredHeight() > 0 ? container.getMeasuredHeight() : (int) (80 * displayMetrics.density);
        AdRequest adRequest = new AdRequest.Builder()
                .withSpaceId(spaceId)
                .withExpressViewAcceptedSize(width, height)
                .build();
        tapAdNative.loadBannerAd(adRequest, new TapAdNative.BannerAdListener() {
            @Override
            public void onBannerAdLoad(TapBannerAd ad) {
                Log.i(TAG, "Banner广告加载成功");
                if (ad == null) {
                    BannerAdCallback.onBannerAdError(activity, -1, "Banner广告数据为空");
                    return;
                }
                ad.setBannerInteractionListener(new TapBannerAd.BannerInteractionListener() {
                    @Override
                    public void onAdShow() {
                        Log.i(TAG, "Banner广告展示");
                    }

                    @Override
                    public void onAdClose() {
                        Log.i(TAG, "Banner广告关闭");
                    }

                    @Override
                    public void onAdClick() {
                        Log.i(TAG, "Banner广告被点击");
                    }

                    @Override
                    public void onAdValidShow() {
                    }
                });
                // 该 SDK 的 show(Activity,int,int) 两个 int 语义不明确，此处仅使用 getBannerView() 挂载
                View bannerView = ad.getBannerView();
                if (bannerView == null) {
                    BannerAdCallback.onBannerAdError(activity, -1, "Banner广告视图为空");
                    return;
                }
                container.removeAllViews();
                container.addView(bannerView);
                BannerAdCallback.onBannerAdLoaded(activity, container);
            }

            @Override
            
            public void onError(int code, String message) {
                Log.e(TAG, "Banner广告加载失败，错误信息：" + message + "(" + code + ")");
                BannerAdCallback.onBannerAdError(activity, code, message);
            }
        });
    }

    /**
     * 加载并展示 Taptap Banner 广告（广告位为 String 时的便捷重载）。
     */
    public static void TaptapBannerAd(Activity activity, String adId, @NonNull ViewGroup container) {
        long spaceId;
        try {
            spaceId = Long.parseLong(adId);
        } catch (Exception e) {
            BannerAdCallback.onBannerAdError(activity, -1, "广告位不是合法的 long：" + adId);
            return;
        }
        TaptapBannerAd(activity, spaceId, container);
    }

}
