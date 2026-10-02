package com.FreshingAir.Ad.Aggregation;
import bin.mt.annotations.MTProtector;
import androidx.annotation.Keep;

import static com.FreshingAir.Ad.Aggregation.SplashAdCallback.goToMainActivity;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.FreshingAir.Ad.Aggregation.utils.Id;

import com.FreshingAir.Ad.Aggregation.ads.*;
import com.huawei.hms.ads.splash.SplashView;

@Keep
public class loadAdByType {

    @MTProtector
    public static AdPlatform getAdPlatform(@NonNull String adPlatform){
        switch (adPlatform){
            case "ADMOB":
                return AdPlatform.ADMOB;
            case "BAIDU":
                return AdPlatform.BAIDU;
            case "BEIZI":
                return AdPlatform.BEIZI;
            case "CSJ":
                return AdPlatform.CSJ;
            case "GDT":
                return AdPlatform.GDT;
            case "HW":
                return AdPlatform.HW;
            case "KS":
                return AdPlatform.KS;
            case "SIGMOB":
                return AdPlatform.SIGMOB;
            case "MIMO":
                return AdPlatform.MIMO;
            case "MS":
                return AdPlatform.MS;
            case "OCTOPUS":
                return AdPlatform.OCTOPUS;
            case "TAPTAP":
                return AdPlatform.TAPTAP;
            case "OSET":
                return AdPlatform.OSET;
            case "QIMING":
                return AdPlatform.QIMING;
            case "JD":
                return AdPlatform.JD;
            case "INMOBI":
                return AdPlatform.INMOBI;
            case "UMENG":
                return AdPlatform.UMENG;
            case "SINA":
                return AdPlatform.SINA;
            default:
                throw new IllegalArgumentException("非法参数：" + adPlatform);
        }
    }
    public static void initSDKByAdPlatform(Context context, @NonNull AdPlatform adPlatform){
        initSDKByAdPlatform(context, adPlatform, null, null);
    }

    /**
     * 初始化指定平台的 SDK，可自定义应用级 ID。
     *
     * @param appId  平台应用 ID（Taptap 为 MediaID）。传 null / 空串时使用 {@link Id} 中的示例值
     * @param appKey 平台密钥，仅 Sigmob、Tanx、OSET 使用。传 null / 空串时使用示例值
     */
    public static void initSDKByAdPlatform(Context context, @NonNull AdPlatform adPlatform,
                                           @Nullable String appId, @Nullable String appKey){
        switch (adPlatform) {
            case ADMOB:
                AdMob.initAdMobSDK(context);
                break;
            case BAIDU:
                BaiduAd.InitBaiduSDK(context, useOrDefault(appId, Id.BaiduId.APP_ID));
                break;
            case BEIZI:
                BeiZiAd.InitBeiZiSDK(context, useOrDefault(appId, ""));
                break;
            case CSJ:
                CsjAd.InitCsjSDK(context, useOrDefault(appId, Id.CsjId.APP_ID), context.getString(R.string.app_name));
                break;
            case GDT:
                GDTAd.InitGDTSDK(context, useOrDefault(appId, Id.GDTId.APP_ID));
                break;
            case HW:
                HwAd.InitHwSDK(context);
                break;
            case KS:
                KsAd.initKSSDK(context, useOrDefault(appId, Id.KsId.APP_ID), context.getString(R.string.app_name));
                break;
            case SIGMOB:
                SigmobAd.initSigmobSDK(context, useOrDefault(appId, Id.SigmobId.APP_ID), useOrDefault(appKey, Id.SigmobId.APP_KEY));
                break;
            case MIMO:
                MimoAd.initMimoSDK(context);
                break;
            case MS:
                MsAd.initMsAdSDK(context, useOrDefault(appId, Id.MSId.APP_ID));
                break;
            case OCTOPUS:
                OctopusAd.InitOctopusSDK(context, useOrDefault(appId, Id.OctopusId.APP_ID));
                break;
            case TAPTAP:
                TaptapAd.InitTaptapSDK(context,
                        parseLongOr(useOrDefault(appId, String.valueOf(Id.TaptapId.MEDIA_ID)), Id.TaptapId.MEDIA_ID),
                        Id.TaptapId.MEDIA_NAME,
                        useOrDefault(appKey, Id.TaptapId.MEDIA_KEY));
                break;
            case QIMING:
                QiMingAd.InitQiMingSDK(context, useOrDefault(appId, "100002"));//包名不对
                break;
            case OSET:
                OSETAd.initOSETSDK((Application) context.getApplicationContext(), useOrDefault(appKey, Id.OpenSetId.APP_KEY));
                break;
            case JD:
                JdAd.initJadSDK(context, useOrDefault(appId, Id.JdId.APP_ID));
                break;
            case TANX:
                TanxAd.initTanxSDK((Activity) context, useOrDefault(appId, Id.TanxId.APP_ID), useOrDefault(appKey, Id.TanxId.APP_KEY));
                break;
            case INMOBI:
                InMobiAd.InitInMobiSDK(context, useOrDefault(appId, Id.InMobiId.APP_ID));
                break;
            case UMENG:
                UmengAd.InitUmengSDK(context, useOrDefault(appId, Id.UmengId.APP_KEY));
                break;
            case SINA:
                SinaAd.InitSinaSDK(context, useOrDefault(appId, Id.SinaId.APP_KEY));
                break;
        }
    }
    @MTProtector
    public static void loadSplashAd(Context context, @NonNull AdPlatform selectedPlatform, ViewGroup SplashAdContainer){
        loadSplashAd(context, selectedPlatform, SplashAdContainer, null);
    }

    /**
     * 加载开屏广告，可自定义广告位 ID。
     *
     * @param splashAdId 开屏广告位 ID。传 null / 空串时使用 {@link Id} 中的示例值
     *                   （华为需传 SplashView 类型的容器，此处传入的 ID 同样适用）
     */
    @MTProtector
    public static void loadSplashAd(Context context, @NonNull AdPlatform selectedPlatform, ViewGroup SplashAdContainer,
                                    @Nullable String splashAdId){
        if(!Boolean.TRUE.equals(Init.adSDKisLoaded.get(selectedPlatform))) {
            initSDKByAdPlatform(context, selectedPlatform);
        }
        switch (context.getResources().getConfiguration().orientation){
            case Configuration.ORIENTATION_PORTRAIT:
                switch (selectedPlatform) {
                    case ADMOB:
                        AdMob.AdMobSplashAd((Activity) context, useOrDefault(splashAdId, Id.AdMobId.SPLASH_ID));
                        break;
                    case BAIDU:
                        BaiduAd.BaiduSplashAd(context, useOrDefault(splashAdId, Id.BaiduId.SPLASH_ID), SplashAdContainer);
                        break;
                    case BEIZI:
                        BeiZiAd.BeiziSplashAd(context, useOrDefault(splashAdId, Id.BeiziId.SPLASH_ID), SplashAdContainer);
                        break;
                    case CSJ:
                        CsjAd.CsjSplashAd(context, useOrDefault(splashAdId, Id.CsjId.SPLASH_ID), SplashAdContainer);
                        break;
                    case GDT:
                        GDTAd.GDTSplashAd(context, useOrDefault(splashAdId, Id.GDTId.SPLASH_ID), SplashAdContainer);
                        break;
                    case HW:
                        HwAd.HwSplashAd(context, useOrDefault(splashAdId, Id.HwId.SPLASH_ID_PORTRAIT), (SplashView) SplashAdContainer);
                        break;
                    case KS:
                        KsAd.KSSplashAd(context, parseLongOr(useOrDefault(splashAdId, String.valueOf(Id.KsId.SPLASH_ID)), Id.KsId.SPLASH_ID), SplashAdContainer);
                        break;
                    case SIGMOB:
                        // Sigmob ID提供：https://github.com/gstory0404/sigmobad
                        SigmobAd.SigmobSplashAd(context, useOrDefault(splashAdId, Id.SigmobId.SPLASH_ID), null, null, SplashAdContainer);
                        break;
                    case MIMO:
                        MimoAd.MimoSplashAd(context, useOrDefault(splashAdId, Id.MimoId.SPLASH_ID), SplashAdContainer);
                        break;
                    case MS:
                        MsAd.MsSplashAd(context, useOrDefault(splashAdId, Id.MSId.SPLASH_ID), SplashAdContainer);
                        break;
                    case TAPTAP:
                        TaptapAd.TaptapSplashAd(context,
                                parseLongOr(useOrDefault(splashAdId, String.valueOf(Id.TaptapId.horizontal.SPLASH_ID)), Id.TaptapId.horizontal.SPLASH_ID),
                                (Activity) context, SplashAdContainer);
                        break;
                    case OCTOPUS:
                        OctopusAd.OctopusSplashAd(context, useOrDefault(splashAdId, Id.OctopusId.SPLASH_ID), SplashAdContainer);
                        break;
                    case OSET:
                        OSETAd.OSETSplashAd((Activity) context, useOrDefault(splashAdId, Id.OpenSetId.SPLASH_ID), SplashAdContainer);
                        break;
                    case JD:
                        JdAd.JadSplashAd(context, useOrDefault(splashAdId, Id.JdId.ESPLASH_ID), JdAd.getWidth(context), JdAd.getHeight(context), SplashAdContainer);
                        break;
                    case TANX:
                        TanxAd.TanxSplashAd(context, useOrDefault(splashAdId, "TODO tanx广告位"), SplashAdContainer);
                        break;
                    case QIMING:
                        QiMingAd.QiMingSplashAd(context, useOrDefault(splashAdId, "TODO QiMing广告位"), SplashAdContainer);
                        break;
                    case UMENG:
                        UmengAd.UmengSplashAd(context, useOrDefault(splashAdId, Id.UmengId.SPLASH_ID), SplashAdContainer);
                        break;
                    default:
                        goToMainActivity(context);
                        break;
                }
                break;
            case Configuration.ORIENTATION_LANDSCAPE:
                switch (selectedPlatform){
                    case MIMO:
                        MimoAd.MimoSplashAd(context, useOrDefault(splashAdId, Id.MimoId.SPLASH_ID), SplashAdContainer);
                        break;
                    case TAPTAP:
                        TaptapAd.TaptapSplashAd(context,
                                parseLongOr(useOrDefault(splashAdId, String.valueOf(Id.TaptapId.vertical.SPLASH_ID)), Id.TaptapId.vertical.SPLASH_ID),
                                (Activity) context, SplashAdContainer);
                        break;
                    default:
                        goToMainActivity(context);
                        break;
                }
                break;
            default:
                goToMainActivity(context);
                break;
        }
    }
    public static void loadFeedAd(@NonNull Activity activity,@NonNull ViewGroup feedAdContainer,@NonNull AdPlatform adPlatform){
        loadFeedAd(activity, feedAdContainer, adPlatform, null);
    }

    /**
     * 加载信息流广告，可自定义广告位 ID。
     *
     * @param feedAdId 信息流广告位 ID。传 null / 空串时使用 {@link Id} 中的示例值
     */
    public static void loadFeedAd(@NonNull Activity activity,@NonNull ViewGroup feedAdContainer,@NonNull AdPlatform adPlatform,
                                  @Nullable String feedAdId){
        //feedAdContainer.setBackgroundColor(getResources().getColor(R.color.gray));
        if(Boolean.FALSE.equals(Init.adSDKisLoaded.get(adPlatform))) {
            initSDKByAdPlatform(activity, adPlatform);
        }
        switch (adPlatform) {
            case CSJ:
                CsjAd.CsjFeedAd(feedAdContainer.getContext(), useOrDefault(feedAdId, Id.CsjId.NATIVE_RECYCLERVIEW_ID), feedAdContainer);
                break;
            case BAIDU:
                BaiduAd.BaiduFeedAd(feedAdContainer.getContext(), useOrDefault(feedAdId, Id.BaiduId.NATIVE_SIMPLE_ID), feedAdContainer);
                break;
            case GDT:
                GDTAd.GDTFeedAd(feedAdContainer.getContext(), useOrDefault(feedAdId, Id.GDTId.NATIVE_EXPRESS_ID_PICTURE_VIDEO), feedAdContainer);
                break;
            case TAPTAP:
                TaptapAd.TaptapNativeAd(activity,
                        parseLongOr(useOrDefault(feedAdId, String.valueOf(Id.TaptapId.FEED.PICTURE_ID)), Id.TaptapId.FEED.PICTURE_ID),
                        feedAdContainer);
                break;
            case SIGMOB:
                SigmobAd.SigmobNativeAd(feedAdContainer.getContext(), useOrDefault(feedAdId, Id.SigmobId.FEED_ID), null, feedAdContainer);
                break;
            case MS:
                MsAd.MsRecyclerMixAdAd(activity, useOrDefault(feedAdId, Id.MSId.FEED_ID), feedAdContainer);
                break;
            case KS:
                KsAd.KsFeedAd(parseLongOr(useOrDefault(feedAdId, String.valueOf(Id.KsId.FEED_ID)), Id.KsId.FEED_ID), feedAdContainer);
                break;
            case OCTOPUS:
                OctopusAd.OctopusNativeAd(feedAdContainer.getContext(), useOrDefault(feedAdId, Id.OctopusId.NATIVE_RECYCLERVIEW_ID), feedAdContainer);
                break;
            case INMOBI:
                InMobiAd.InMobiFeedAd(activity, parseLongOr(useOrDefault(feedAdId, String.valueOf(Id.InMobiId.FEED_ID)), Id.InMobiId.FEED_ID), feedAdContainer);
                break;
            case UMENG:
                UmengAd.UmengFeedAd(feedAdContainer.getContext(), useOrDefault(feedAdId, Id.UmengId.FEED_ID), feedAdContainer);
                break;
            case SINA:
                SinaAd.SinaFeedAd(feedAdContainer.getContext(), useOrDefault(feedAdId, Id.SinaId.APP_RID), feedAdContainer);
                break;
            default:
                Log.i("广告", "没有选中广告");
                break;
            //        FeedAds.HwAd(feedAdContainer.getContext(), AppAdId.HwId.NATIVE_ID_SMALL,adItem.HwNativeAdContainer);
        }
    }

    /**
     * 加载视频广告（激励视频），使用 {@link Id} 中的示例广告位。
     *
     * @param activity 展示广告的页面，广告展示、关闭等回调均回到该页面
     */
    @MTProtector
    public static void loadRewardVideoAd(@NonNull Activity activity, @NonNull AdPlatform selectedPlatform){
        loadRewardVideoAd(activity, selectedPlatform, null);
    }

    /**
     * 加载视频广告（激励视频），可自定义广告位 ID。
     *
     * 广告加载成功、开始播放、发放奖励、关闭、失败时，会分别回调
     * {@link LocalAdBridge#onRewardVideoAdLoaded} 等回调，使用方在此接管。
     *
     * @param rewardAdId 激励视频广告位 ID。传 null / 空串时使用 {@link Id} 中的示例值；
     *                   快手、Taptap 的广告位为 long，传入非数字字符串时同样回退到示例值
     */
    @MTProtector
    public static void loadRewardVideoAd(@NonNull Activity activity, @NonNull AdPlatform selectedPlatform,
                                         @Nullable String rewardAdId){
        if(!Boolean.TRUE.equals(Init.adSDKisLoaded.get(selectedPlatform))) {
            initSDKByAdPlatform(activity, selectedPlatform);
        }
        switch (selectedPlatform) {
            case CSJ:
                CsjAd.CsjRewardVideoAd(activity, useOrDefault(rewardAdId, Id.CsjId.REWARD_ID));
                break;
            case GDT:
                GDTAd.GDTRewardVideoAd(activity, useOrDefault(rewardAdId, Id.GDTId.REWARD_VIDEO_AD_ID_SUPPORT_H));
                break;
            case BAIDU:
                BaiduAd.BaiduRewardVideoAd(activity, useOrDefault(rewardAdId, Id.BaiduId.REWARD_ID));
                break;
            case KS:
                KsAd.KsRewardVideoAd(activity, parseLongOr(useOrDefault(rewardAdId, String.valueOf(Id.KsId.REWARD_ID)), Id.KsId.REWARD_ID));
                break;
            case SIGMOB:
                SigmobAd.SigmobRewardVideoAd(activity, useOrDefault(rewardAdId, Id.SigmobId.REWARD_ID), null);
                break;
            case MS:
                MsAd.MsRewardVideoAd(activity, useOrDefault(rewardAdId, Id.MSId.REWARD_ID));
                break;
            case OCTOPUS:
                OctopusAd.OctopusRewardVideoAd(activity, useOrDefault(rewardAdId, Id.OctopusId.REWARD_ID));
                break;
            case TAPTAP:
                TaptapAd.TaptapRewardVideoAd(activity,
                        parseLongOr(useOrDefault(rewardAdId, String.valueOf(Id.TaptapId.horizontal.REWARD_ID)), Id.TaptapId.horizontal.REWARD_ID));
                break;
            case MIMO:
                MimoAd.MimoRewardVideoAd(activity, useOrDefault(rewardAdId, Id.MimoId.REWARD_ID));
                break;
            case OSET:
                OSETAd.OSETRewardVideoAd(activity, useOrDefault(rewardAdId, Id.OpenSetId.REWARD_ID));
                break;
            case QIMING:
                QiMingAd.QiMingRewardVideoAd(activity, useOrDefault(rewardAdId, Id.QiMingId.REWARD_ID));
                break;
            case BEIZI:
                BeiZiAd.BeiziRewardVideoAd(activity, useOrDefault(rewardAdId, Id.BeiziId.REWARD_ID));
                break;
            case HW:
                HwAd.HwRewardVideoAd(activity, useOrDefault(rewardAdId, Id.HwId.REWARD_ID));
                break;
            case ADMOB:
                AdMob.AdMobRewardVideoAd(activity, useOrDefault(rewardAdId, Id.AdMobId.REWARD_ID));
                break;
            case UMENG:
                UmengAd.UmengRewardVideoAd(activity, useOrDefault(rewardAdId, Id.UmengId.REWARD_ID));
                break;
            default:
                Log.i("广告", "该平台未实现视频广告：" + selectedPlatform);
                RewardVideoAdCallback.onRewardAdError(activity, -1, "该平台未实现视频广告：" + selectedPlatform);
                break;
        }
    }

    /** 使用方传入的 ID 为空时回退到示例值（{@link Id}）。 */
    @MTProtector
    private static String useOrDefault(@Nullable String value, String sample) {
        return (value == null || value.trim().isEmpty()) ? sample : value.trim();
    }

    /** 部分平台（快手、Taptap）的广告位 ID 为 long，传入值非法时回退到示例值。 */
    @MTProtector
    private static long parseLongOr(String value, long sample) {
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            return sample;
        }
    }
}