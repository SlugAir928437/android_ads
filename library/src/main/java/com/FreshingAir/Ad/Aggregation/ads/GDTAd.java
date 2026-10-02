package com.FreshingAir.Ad.Aggregation.ads;
import bin.mt.annotations.MTProtector;
import androidx.annotation.Keep;

import android.content.Context;
import android.app.Activity;
import android.util.Log;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import android.widget.VideoView;

import com.FreshingAir.Ad.Aggregation.AdPlatform;
import com.FreshingAir.Ad.Aggregation.Init;
import com.FreshingAir.Ad.Aggregation.RewardVideoAdCallback;
import com.FreshingAir.Ad.Aggregation.SplashAdCallback;
import com.qq.e.ads.nativ.ADSize;
import com.qq.e.ads.nativ.NativeExpressAD;
import com.qq.e.ads.nativ.NativeExpressADView;
import com.qq.e.ads.nativ.NativeExpressMediaListener;
import com.qq.e.ads.rewardvideo.RewardVideoAD;
import com.qq.e.ads.rewardvideo.RewardVideoADListener;
import com.qq.e.ads.splash.SplashAD;
import com.qq.e.ads.splash.SplashADListener;
import com.qq.e.comm.constants.AdPatternType;
import com.qq.e.comm.constants.LoadAdParams;
import com.qq.e.comm.managers.GDTAdSdk;
import com.qq.e.comm.util.AdError;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

@MTProtector
public class GDTAd {
    private static final String TAG = "广点通广告 SDK";
    public static void InitGDTSDK(Context context, String appId){
        GDTAdSdk.initWithoutStart(context,appId);  // 该接口不会采集用户信息
        // 调用initWithoutStart后请尽快调用start，否则可能影响广告填充，造成收入下降
        GDTAdSdk.start(new GDTAdSdk.OnStartListener() {
            @Override
            @MTProtector
            public void onStartSuccess() {
                // 推荐开发者在onStartSuccess回调后开始拉广告
                Log.i(TAG, "SDK初始化成功");
                Init.adSDKisLoaded.put(AdPlatform.GDT, true);
            }

            @Override
            public void onStartFailed(Exception e) {
                Log.e(TAG, "SDK初始化失败，错误信息："+e.getMessage());
            }
        });
    }
    @MTProtector
    public static void GDTSplashAd(Context context,String posId,ViewGroup container){
        // 创建开屏广告实例
        SplashAD splashAD = new SplashAD(context, posId,  new SplashADListener() {
            @Override
            @MTProtector
            public void onADDismissed() {
                // 广告关闭，进入主界面
                Log.i(TAG, "广告关闭");
                SplashAdCallback.goToMainActivity(context);
            }
            @Override
            @MTProtector
            public void onNoAD(com.qq.e.comm.util.AdError adError) {
                // 广告加载失败，也进入主界面
                Log.e(TAG, "广告加载失败，错误信息：" + adError.getErrorMsg() + "(" + adError.getErrorCode() + ")");
                SplashAdCallback.goToMainActivity(context);
            }
            @Override
            public void onADPresent() {
                // 广告成功展示
                Log.i(TAG, "广告成功展示");
            }
            @Override
            @MTProtector
            public void onADClicked() {
                // 广告被点击
                Log.i(TAG, "用户点击了广告");
            }
            @Override
            public void onADTick(long millisUntilFinished) {

            }
            @Override
            @MTProtector
            public void onADExposure() {
                // 广告曝光
                Log.i(TAG, "广告曝光");
                SplashAdCallback.onSplashAdLoaded(context, container);
            }
            @Override
            public void onADLoaded(long l) {
                // 广告加载成功（开屏广告加载成功后会立即展示，此回调可能不会触发）
                Log.i(TAG, "广告加载成功");
            }
        });
        // 拉取并展示广告，传入广告容器和自定义的跳过按钮（可选）
        splashAD.fetchAdOnly();
        splashAD.showAd(container);
        // 如果不需要自定义跳过按钮，可以使用下面这个方法
        // splashAD.fetchAndShowIn(container);
    }
    public static void GDTPreRollAd(Context context, String posID, @NonNull ViewGroup adContainer, VideoView videoView){
        adContainer.removeAllViews();
        NativeExpressAD nativeExpressAD = new NativeExpressAD(context, new ADSize(ADSize.FULL_WIDTH, ADSize.AUTO_HEIGHT), posID, new NativeExpressAD.NativeExpressADListener() {
            @Override
            @MTProtector
            public void onADLoaded(List<NativeExpressADView> list) {
                NativeExpressADView nativeExpressADView = list.get(0);
                nativeExpressADView.setMediaListener(new NativeExpressMediaListener() {
                    @Override
                    @MTProtector
                    public void onVideoInit(NativeExpressADView nativeExpressADView) {
                        Log.i(TAG,"视频初始化");
                    }

                    @Override
                    @MTProtector
                    public void onVideoLoading(NativeExpressADView nativeExpressADView) {
                        Log.i(TAG,"视频正在加载");
                    }

                    @Override
                    public void onVideoCached(NativeExpressADView nativeExpressADView) {
                        Log.i(TAG,"视频已缓存");
                    }

                    @Override
                    @MTProtector
                    public void onVideoReady(NativeExpressADView nativeExpressADView, long l) {
                        Log.i(TAG,"视频已准备好");
                        nativeExpressADView.render();
                        adContainer.setVisibility(ViewGroup.VISIBLE);
                        adContainer.addView(nativeExpressADView);
                    }

                    @Override
                    public void onVideoStart(NativeExpressADView nativeExpressADView) {
                        Log.i(TAG,"视频开始播放");
                    }

                    @Override
                    public void onVideoPause(NativeExpressADView nativeExpressADView) {
                        Log.i(TAG,"视频暂停");
                    }

                    @Override
                    public void onVideoComplete(NativeExpressADView nativeExpressADView) {
                        Log.i(TAG,"视频播放完毕");
                        nativeExpressADView.destroy();
                        videoView.start();
                    }

                    @Override
                    @MTProtector
                    public void onVideoError(NativeExpressADView nativeExpressADView, AdError adError) {
                        Log.e(TAG,"视频播放失败，错误信息："+adError.getErrorMsg()+"("+adError.getErrorCode()+")");
                        nativeExpressADView.destroy();
                        videoView.start();
                    }

                    @Override
                    @MTProtector
                    public void onVideoPageOpen(NativeExpressADView nativeExpressADView) {
                        Log.i(TAG,"视频详情页打开");
                    }

                    @Override
                    @MTProtector
                    public void onVideoPageClose(NativeExpressADView nativeExpressADView) {
                        Log.i(TAG,"视频详情页关闭");
                    }
                });
                nativeExpressADView.preloadVideo();
            }

            @Override
            public void onRenderFail(NativeExpressADView nativeExpressADView) {
                Log.e(TAG,"广告染失败");
                nativeExpressADView.destroy();
                videoView.start();
            }

            @Override
            @MTProtector
            public void onRenderSuccess(NativeExpressADView nativeExpressADView) {
                Log.i(TAG,"广告渲染成功");
            }

            @Override
            public void onADExposure(NativeExpressADView nativeExpressADView) {
                Log.i(TAG,"广告曝光");
            }

            @Override
            public void onADClicked(NativeExpressADView nativeExpressADView) {
                Log.i(TAG,"广告被点击");
            }

            @Override
            public void onADClosed(NativeExpressADView nativeExpressADView) {
                Log.i(TAG,"广告关闭");
                nativeExpressADView.destroy();
                videoView.start();
            }

            @Override
            public void onADLeftApplication(NativeExpressADView nativeExpressADView) {

            }

            @Override
            @MTProtector
            public void onNoAD(AdError adError) {
                Log.i(TAG,"广告加载失败，错误信息："+adError.getErrorMsg()+"("+adError.getErrorCode()+")");
                videoView.start();
            }
        });
        LoadAdParams loadAdParams = new LoadAdParams();
        Map<String, String> info = new HashMap<>();
        info.put("custom_key", "native_express");
        info.put("staIn", "com.qq.e.demo");
        info.put("thrmei", "aaaa_bbbb_cccc_dddd");
        loadAdParams.setDevExtra(info);
        nativeExpressAD.loadAD(1,loadAdParams);
    }
    public static void GDTFeedAd(Context context, String posID, @NonNull ViewGroup adContainer){
        adContainer.removeAllViews();
        NativeExpressAD nativeExpressAD = new NativeExpressAD(context, new ADSize(ADSize.FULL_WIDTH,ADSize.AUTO_HEIGHT), posID, new NativeExpressAD.NativeExpressADListener() {
            @Override
            public void onADLoaded(List<NativeExpressADView> list) {
                Log.i(TAG, "onADLoaded: " + list.size());
                NativeExpressADView nativeExpressADView = list.get(0);
                nativeExpressADView.render();
                // 3.返回数据后，SDK 会返回可以用于展示 NativeExpressADView 列表
                if (nativeExpressADView.getBoundData().getAdPatternType() == AdPatternType.NATIVE_VIDEO) {
                    nativeExpressADView.setMediaListener(new NativeExpressMediaListener() {
                        @Override
                        @MTProtector
                        public void onVideoInit(NativeExpressADView nativeExpressADView) {
                            Log.i(TAG, "视频初始化");
                        }

                        @Override
                        public void onVideoLoading(NativeExpressADView nativeExpressADView) {
                            Log.i(TAG, "视频加载中");
                        }

                        @Override
                        public void onVideoCached(NativeExpressADView nativeExpressADView) {
                            Log.i(TAG, "视频已缓存");
                        }

                        @Override
                        public void onVideoReady(NativeExpressADView nativeExpressADView, long l) {
                            Log.i(TAG, "视频已准备好");
                        }

                        @Override
                        public void onVideoStart(NativeExpressADView nativeExpressADView) {
                            Log.i(TAG, "视频开始播放");
                        }

                        @Override
                        @MTProtector
                        public void onVideoPause(NativeExpressADView nativeExpressADView) {
                            Log.i(TAG, "视频暂停");
                        }

                        @Override
                        public void onVideoComplete(NativeExpressADView nativeExpressADView) {
                            Log.i(TAG, "视频完成");
                        }

                        @Override
                        public void onVideoError(NativeExpressADView nativeExpressADView, AdError adError) {
                            Log.e(TAG, "视频播放错误，错误信息："+adError.getErrorMsg()+"("+adError.getErrorCode()+")");
                        }

                        @Override
                        public void onVideoPageOpen(NativeExpressADView nativeExpressADView) {

                        }

                        @Override
                        @MTProtector
                        public void onVideoPageClose(NativeExpressADView nativeExpressADView) {

                        }
                    });
                }
                nativeExpressADView.render();
                if (adContainer.getChildCount() > 0) {
                    adContainer.removeAllViews();
                }

                // 需要保证 View 被绘制的时候是可见的，否则将无法产生曝光和收益。
                adContainer.addView(nativeExpressADView);
            }

            @Override
            @MTProtector
            public void onRenderFail(NativeExpressADView nativeExpressADView) {
                nativeExpressADView.destroy();
            }

            @Override
            public void onRenderSuccess(NativeExpressADView nativeExpressADView) {
                Log.i(TAG, "广告渲染成功");
            }

            @Override
            public void onADExposure(NativeExpressADView nativeExpressADView) {
                Log.i(TAG, "广告曝光");
            }

            @Override
            public void onADClicked(NativeExpressADView nativeExpressADView) {
                Log.i(TAG, "广告被点击");
            }

            @Override
            public void onADClosed(NativeExpressADView nativeExpressADView) {
                Log.i(TAG, "广告关闭");
                nativeExpressADView.destroy();
            }

            @Override
            public void onADLeftApplication(NativeExpressADView nativeExpressADView) {
                nativeExpressADView.destroy();
            }

            @Override
            @MTProtector
            public void onNoAD(AdError adError) {
                Log.e(TAG, "广告加载失败，错误信息："+adError.getErrorMsg()+"("+adError.getErrorCode()+")");
            }
        });
        nativeExpressAD.loadAD(1);
    }

    /**
     * 加载并展示广点通激励视频广告。
     *
     * @param context 需要是 Activity（激励视频展示依赖 Activity）
     * @param posId   激励视频广告位 ID
     */
    @MTProtector
    public static void GDTRewardVideoAd(Context context, String posId) {
        Activity activity = context instanceof Activity ? (Activity) context : null;
        if (activity == null) {
            RewardVideoAdCallback.onRewardAdError(context, -1, "激励视频展示需要 Activity 上下文");
            return;
        }
        final RewardVideoAD[] holder = new RewardVideoAD[1];
        RewardVideoAD rewardVideoAD = new RewardVideoAD(context, posId, new RewardVideoADListener() {
            @Override
            @MTProtector
            public void onADLoad() {
                Log.i(TAG, "激励视频加载成功");
                RewardVideoAdCallback.onRewardAdLoaded(context);
                if (holder[0] != null) {
                    holder[0].showAD();
                }
            }

            @Override
            public void onVideoCached() {
            }

            @Override
            @MTProtector
            public void onADShow() {
                Log.i(TAG, "激励视频开始播放");
                RewardVideoAdCallback.onRewardAdShow(context);
            }

            @Override
            public void onADExpose() {
            }

            @Override
            @MTProtector
            public void onReward(Map<String, Object> map) {
                Log.i(TAG, "激励视频发放奖励");
                RewardVideoAdCallback.onRewardAdRewarded(context, null, 0);
            }

            @Override
            public void onADClick() {
            }

            @Override
            public void onVideoComplete() {
            }

            @Override
            @MTProtector
            public void onADClose() {
                Log.i(TAG, "激励视频关闭");
                RewardVideoAdCallback.onRewardAdClose(context);
            }

            @Override
            @MTProtector
            public void onError(AdError adError) {
                int code = adError != null ? adError.getErrorCode() : 0;
                String msg = adError != null ? adError.getErrorMsg() : null;
                Log.e(TAG, "激励视频加载失败，错误信息：" + msg + "(" + code + ")");
                RewardVideoAdCallback.onRewardAdError(context, code, msg);
            }
        });
        holder[0] = rewardVideoAD;
        rewardVideoAD.loadAD();
    }
}
