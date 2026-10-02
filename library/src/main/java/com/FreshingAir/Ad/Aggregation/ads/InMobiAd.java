package com.FreshingAir.Ad.Aggregation.ads;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.FreshingAir.Ad.Aggregation.AdPlatform;
import com.FreshingAir.Ad.Aggregation.Init;
import com.inmobi.ads.AdMetaInfo;
import com.inmobi.ads.InMobiAdRequestStatus;
import com.inmobi.ads.InMobiNative;
import com.inmobi.ads.exceptions.SdkNotInitializedException;
import com.inmobi.ads.listeners.NativeAdEventListener;
import com.inmobi.sdk.InMobiSdk;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * InMobi（海外）接入实现。
 *
 * 说明：InMobi Android SDK 只提供 Banner / 插屏 / 原生三种广告格式，没有开屏格式，
 * 因此本类只实现信息流（原生）加载，开屏由 {@code loadAdByType} 的默认分支直接进入主页。
 */
public class InMobiAd {

    /**
     * 初始化 InMobi SDK。
     *
     * @param accountId InMobi 后台的账号 ID（Account ID），为空时初始化会失败并回调
     */
    public static void InitInMobiSDK(Context context, String accountId) {
        JSONObject consent = new JSONObject();
        try {
            // InMobi 要求显式声明 GDPR 同意状态；无 GDPR 场景下传 false 即可
            consent.put(InMobiSdk.IM_GDPR_CONSENT_AVAILABLE, false);
        } catch (JSONException e) {
            Log.e("InMobiAd", "consent build failed:" + e.getMessage());
        }
        InMobiSdk.setLogLevel(InMobiSdk.LogLevel.DEBUG);
        InMobiSdk.init(context, accountId, consent, error -> {
            if (error == null) {
                Init.adSDKisLoaded.put(AdPlatform.INMOBI, true);
                Log.i("InMobiAd", "init success, version=" + InMobiSdk.getVersion());
            } else {
                // 账号 ID 为空时这里会返回 INVALID_ACCOUNT_ID，广告位无法拉取
                Log.e("InMobiAd", "init failed:" + error.getMessage());
            }
        });
    }

    private static InMobiNative inMobiNative;

    /**
     * 加载 InMobi 信息流（原生）广告并渲染进容器。
     *
     * @param placementId InMobi 后台创建的原生广告位 ID（long），必须大于 0
     */
    public static void InMobiFeedAd(@NonNull Activity activity, long placementId, @NonNull ViewGroup feedAdContainer) {
        if (placementId <= 0L) {
            Log.e("InMobiAd", "信息流广告位为空：InMobi 后台创建原生广告位后通过 loadFeedAd 的 feedAdId 传入");
            return;
        }
        try {
            inMobiNative = new InMobiNative(activity, placementId, new NativeAdEventListener() {

                @Override
                public void onAdLoadSucceeded(@NonNull InMobiNative ad, @NonNull AdMetaInfo info) {
                    super.onAdLoadSucceeded(ad, info);
                    Log.i("InMobiAd", "onAdLoadSucceeded, creativeId=" + info.getCreativeID());
                    View adView = ad.getPrimaryViewOfWidth(activity, null, feedAdContainer, feedAdContainer.getWidth());
                    if (adView != null) {
                        feedAdContainer.removeAllViews();
                        feedAdContainer.addView(adView);
                    }
                }

                @Override
                public void onAdLoadFailed(@NonNull InMobiNative ad, @NonNull InMobiAdRequestStatus status) {
                    super.onAdLoadFailed(ad, status);
                    Log.e("InMobiAd", "onAdLoadFailed:" + status.getStatusCode() + " / " + status.getMessage());
                }

                @Override
                public void onAdClicked(@NonNull InMobiNative ad) {
                    super.onAdClicked(ad);
                    Log.i("InMobiAd", "onAdClicked");
                }

                @Override
                public void onAdImpressed(@NonNull InMobiNative ad) {
                    super.onAdImpressed(ad);
                    Log.i("InMobiAd", "onAdImpressed");
                }
            });
            inMobiNative.load();
        } catch (SdkNotInitializedException e) {
            Log.e("InMobiAd", "SDK 未初始化:" + e.getMessage());
        }
    }
}
