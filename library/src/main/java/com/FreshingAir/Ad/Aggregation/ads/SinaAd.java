package com.FreshingAir.Ad.Aggregation.ads;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.FreshingAir.Ad.Aggregation.AdPlatform;
import com.FreshingAir.Ad.Aggregation.Init;
import com.FreshingAir.Ad.Aggregation.utils.Id;
import cn.com.sina.mobileads.feedad.SinaFeedAdView;

/**
 * 新浪移动联盟（SinaAdAndrsdk v1.2.0）接入实现。
 *
 * 说明：新浪这个 SDK 只有信息流一种形式，且没有初始化接口 ——
 * {@link SinaFeedAdView} 本身就是一个 FrameLayout，设置 appkey / apprid 后直接挂进容器即可，
 * 因此这里的「初始化」只负责记录 appkey。
 */
public class SinaAd {

    /**
     * 新浪 SDK 没有初始化接口，这里只记录 appkey 并标记为已就绪。
     *
     * @param appKey 新浪移动联盟后台的应用 appkey
     */
    public static void InitSinaSDK(Context context, String appKey) {
        Id.SinaId.APP_KEY = appKey;
        Init.adSDKisLoaded.put(AdPlatform.SINA, true);
    }

    /**
     * 加载新浪信息流广告。
     *
     * @param appRid 新浪后台的广告位 ID（对应示例中的 setApprid）
     */
    public static void SinaFeedAd(@NonNull Context context, String appRid, @NonNull ViewGroup feedAdContainer) {
        SinaFeedAdView sinaFeedAdView = new SinaFeedAdView(context);
        sinaFeedAdView.setAppkey(Id.SinaId.APP_KEY);
        sinaFeedAdView.setApprid(appRid);
        feedAdContainer.removeAllViews();
        feedAdContainer.addView(sinaFeedAdView);
    }
}
