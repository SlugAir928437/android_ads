package com.FreshingAir.Ad.Aggregation.ads;

import androidx.annotation.Keep;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.FreshingAir.Ad.Aggregation.AdPlatform;
import com.FreshingAir.Ad.Aggregation.BannerAdCallback;
import com.FreshingAir.Ad.Aggregation.Init;
import com.FreshingAir.Ad.Aggregation.InterstitialAdCallback;
import com.FreshingAir.Ad.Aggregation.SplashAdCallback;
import com.jd.ad.sdk.banner.JADBanner;
import com.jd.ad.sdk.banner.JADBannerListener;
import com.jd.ad.sdk.bl.initsdk.JADInitCallback;
import com.jd.ad.sdk.bl.initsdk.JADPrivateController;
import com.jd.ad.sdk.bl.initsdk.JADYunSdk;
import com.jd.ad.sdk.bl.initsdk.JADYunSdkConfig;
import com.jd.ad.sdk.dl.addata.JADMaterialData;
import com.jd.ad.sdk.dl.model.JADSlot;
import com.jd.ad.sdk.fdt.utils.ScreenUtils;
import com.jd.ad.sdk.interstitial.JADInterstitial;
import com.jd.ad.sdk.interstitial.JADInterstitialListener;
import com.jd.ad.sdk.nativead.JADNative;
import com.jd.ad.sdk.nativead.JADNativeLoadListener;
import com.jd.ad.sdk.nativead.JADNativeSplashInteractionListener;
import com.jd.ad.sdk.nativead.JADNativeWidget;

import java.util.ArrayList;
import java.util.List;


public class JdAd {

    private static final String TAG = "京媒广告 SDK";
    public static int getWidth(Context context){
        return ScreenUtils.getPhoneWidth(context);
    }
    
    public static int getHeight(Context context){
        return ScreenUtils.getPhoneHeight(context);
    }
    public static void initJadSDK(Context context,String APP_ID){
        //初始化SDK配置
        JADYunSdkConfig config = new JADYunSdkConfig
                .Builder()
                .setAppId(APP_ID) //媒体在平台申请的 APP ID
                .setEnableLog(com.FreshingAir.Ad.Aggregation.BuildConfig.DEBUG) //测试阶段打开，可以通过日志排查问题，上线时去除该调用
                .setPrivateController(new JADPrivateController() {
                    @Override
                    
                    public String getOaid() {
                        return "";
                    }
                }) //隐私信息控制设置，此项必须设置！！
                .setSupportMultiProcess(true)//是否支持多进程，true表示支持，默认不支持,若不支持多进程场景，无需设置该配置
                .build();
    
        //初始化SDK
        //JADYunSdk.init(context, config);
        //同步初始化SDK
        final boolean[] initstatus = new boolean[2];
        JADYunSdk.syncInit(context, config, new JADInitCallback() {
            /**
             * 初始化成功
             */
            @Override
            
            public void onInitSuccess() {
                Log.i(TAG, "SDK同步初始化成功");
                initstatus[0] = true;
                if(initstatus[1]){
                    Init.adSDKisLoaded.put(AdPlatform.JD, true);
                }
            }
            /**
             * 初始化失败
             */
            @Override
            public void onInitFailure(int code, String msg) {
                Log.e(TAG, "SDK同步初始化失败，错误信息："+msg+"("+code+")");
            }
        });
    
        //异步初始化SDK
        JADYunSdk.asyncInit(context, config, new JADInitCallback() {
            /**
             * 初始化成功
             */
            @Override
            
            public void onInitSuccess() {
                Log.i(TAG, "SDK异步初始化成功");
                initstatus[1] = true;
                if(initstatus[0]){
                    Init.adSDKisLoaded.put(AdPlatform.JD, true);
                }
            }
            /**
             * 初始化失败
             */
            @Override
            public void onInitFailure(int code, String msg) {
                Log.e(TAG, "SDK异步初始化失败，错误信息："+msg+"("+code+")");
            }
        });
    }

    
    public static void JadSplashAd(Context context,String slotID,float expressImageWidth,float expressImageHeight,ViewGroup adView){
        JADSlot slot = new JADSlot.Builder()
                .setSlotID(slotID)
                .setImageSize(expressImageWidth, expressImageHeight)
                .setAdType(JADSlot.AdType.SPLASH)
                .build();
        JADNative mJADNative = getJadNative(context, slot);
        // 摇一摇组件,返回大小为（100dp,100dp）的View，当View attachedToWindow时，动画start，detachedFromWindow时，动画end
        View shakeAnimationView = JADNativeWidget.getShakeAnimationView(context);

          // 滑动组件，返回大小为（matchParent，120dp）的View，当View attachedToWindow时，动画start，detachedFromWindow时，动画end
        View swipeAnimationView = JADNativeWidget.getSwipeAnimationView(context);
        //可点击View列表
        List<View> clickList = new ArrayList<>();
          //        clickList.add(imageView);
        clickList.add(shakeAnimationView);
        clickList.add(swipeAnimationView);

      // 关闭View列表
  //        View skipBtn = adView.findViewById(R.id.jad_splash_skip_btn);
        List<View> closeList = new ArrayList<>();
  //        closeList.add(skipBtn);

  // 注册需要监听的视图，包括整体的广告View、点击视图列表、关闭视图列表
        mJADNative.registerNativeView((Activity) context, adView, clickList, closeList,
                new JADNativeSplashInteractionListener() {

                    /**
                     * 广告曝光
                     */
                    @Override
                    
                    public void onExposure() {
                        // TODO 广告曝光上报
                        Log.i(TAG, "广告曝光");
                        SplashAdCallback.onSplashAdLoaded(context, adView);
                    }

                    /**
                     * 广告倒计时,
                     *
                     * @param time 倒计时当前数字
                     */
                    @Override
                    
                    public void onCountdown(int time) {
                        // TODO：关于倒计时视图刷新可在这个回调中进行操作

                    }
                    /**
                     * 广告点击
                     */
                    @Override
                    public void onClick(View view) {
                        // TODO 广告点击上报

                    }

                    /**
                     * 广告关闭
                     */
                    @Override
                    public void onClose(View view) {
                        SplashAdCallback.goToMainActivity(context);
                    }
                });
        mJADNative.destroy();
    }

    @NonNull
    private static JADNative getJadNative(Context context, JADSlot slot) {
        JADNative mJADNative = new JADNative(slot);
        mJADNative.loadAd(new JADNativeLoadListener() {

            /**
             * 广告数据加载成功
             */
            @Override
            
            public void onLoadSuccess() {
                // TODO：广告数据返回上报
            }

            /**
             * 广告数据加载失败
             *
             * @param code  错误码
             * @param error 错误描述信息
             */
            @Override
            public void onLoadFailure(int code, String error) {
                SplashAdCallback.goToMainActivity(context);
            }
        });
        List<JADMaterialData> adList = mJADNative.getDataList();
        return mJADNative;
    }

    /**
     * 加载并展示京媒插屏广告（加载成功后自动渲染并展示）。
     *
     * @param activity 展示广告的页面
     * @param adId     插屏广告位 ID
     */
    
    public static void JdInterstitialAd(Activity activity, String adId) {
        if (activity == null || adId == null || adId.trim().isEmpty()) {
            InterstitialAdCallback.onInterstitialAdError(activity, -1, "参数非法（Activity/广告位不能为空）");
            return;
        }
        int width = getWidth(activity);
        int height = getHeight(activity);
        JADSlot slot = new JADSlot.Builder()
                .setSlotID(adId)
                .setAdType(JADSlot.AdType.INTERSTITIAL)
                .setSize(width, height)
                .build();
        final JADInterstitial[] holder = new JADInterstitial[1];
        JADInterstitial interstitial = new JADInterstitial(activity, slot);
        holder[0] = interstitial;
        interstitial.loadAd(new JADInterstitialListener() {
            @Override
            public void onLoadSuccess() {
                Log.i(TAG, "插屏广告加载成功");
                InterstitialAdCallback.onInterstitialAdLoaded(activity);
                // 加载成功后开始渲染
                if (holder[0] != null) {
                    holder[0].startRender();
                }
            }

            @Override
            
            public void onLoadFailure(int code, String error) {
                Log.e(TAG, "插屏广告加载失败，错误信息：" + error + "(" + code + ")");
                InterstitialAdCallback.onInterstitialAdError(activity, code, error);
            }

            @Override
            public void onRenderSuccess(View view) {
                Log.i(TAG, "插屏广告渲染成功");
                // 渲染成功后展示
                if (holder[0] != null) {
                    holder[0].showAd(activity);
                }
            }

            @Override
            
            public void onRenderFailure(int code, String error) {
                Log.e(TAG, "插屏广告渲染失败，错误信息：" + error + "(" + code + ")");
                InterstitialAdCallback.onInterstitialAdError(activity, code, error);
            }

            @Override
            
            public void onExposure() {
                Log.i(TAG, "插屏广告展示");
                InterstitialAdCallback.onInterstitialAdShow(activity);
            }

            @Override
            public void onClick() {
                Log.i(TAG, "插屏广告被点击");
            }

            @Override
            
            public void onClose() {
                Log.i(TAG, "插屏广告关闭");
                InterstitialAdCallback.onInterstitialAdClose(activity);
            }
        });
    }

    /**
     * 加载并展示京媒插屏广告（广告位为 long 时的便捷重载）。
     */
    public static void JdInterstitialAd(Activity activity, long adId) {
        JdInterstitialAd(activity, String.valueOf(adId));
    }

    /**
     * 加载并展示京媒 Banner 广告。
     *
     * @param activity  展示广告的页面
     * @param adId      Banner 广告位 ID
     * @param container 承载广告 View 的容器
     */
    
    public static void JdBannerAd(Activity activity, String adId, @NonNull ViewGroup container) {
        if (activity == null || container == null || adId == null || adId.trim().isEmpty()) {
            BannerAdCallback.onBannerAdError(activity, -1, "参数非法（Activity/容器/广告位不能为空）");
            return;
        }
        container.removeAllViews();
        int width = container.getMeasuredWidth() > 0 ? container.getMeasuredWidth() : getWidth(activity);
        int height = container.getMeasuredHeight() > 0 ? container.getMeasuredHeight() : getWidth(activity) / 6;
        JADSlot slot = new JADSlot.Builder()
                .setSlotID(adId)
                .setAdType(JADSlot.AdType.BANNER)
                .setSize(width, height)
                .build();
        new JADBanner(activity, slot).loadAd(new JADBannerListener() {
            @Override
            public void onLoadSuccess() {
                Log.i(TAG, "Banner广告加载成功");
            }

            @Override
            
            public void onLoadFailure(int code, String error) {
                Log.e(TAG, "Banner广告加载失败，错误信息：" + error + "(" + code + ")");
                BannerAdCallback.onBannerAdError(activity, code, error);
            }

            @Override
            public void onRenderSuccess(View view) {
                Log.i(TAG, "Banner广告渲染成功");
                if (view == null) {
                    BannerAdCallback.onBannerAdError(activity, -1, "Banner广告视图为空");
                    return;
                }
                container.removeAllViews();
                container.addView(view);
                BannerAdCallback.onBannerAdLoaded(activity, container);
            }

            @Override
            
            public void onRenderFailure(int code, String error) {
                Log.e(TAG, "Banner广告渲染失败，错误信息：" + error + "(" + code + ")");
                BannerAdCallback.onBannerAdError(activity, code, error);
            }

            @Override
            public void onExposure() {
                Log.i(TAG, "Banner广告曝光");
            }

            @Override
            public void onClick() {
                Log.i(TAG, "Banner广告被点击");
            }

            @Override
            public void onClose() {
                Log.i(TAG, "Banner广告关闭");
            }
        });
    }

    /**
     * 加载并展示京媒 Banner 广告（广告位为 long 时的便捷重载）。
     */
    public static void JdBannerAd(Activity activity, long adId, @NonNull ViewGroup container) {
        JdBannerAd(activity, String.valueOf(adId), container);
    }
}
