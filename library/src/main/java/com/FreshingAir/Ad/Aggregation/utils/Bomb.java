package com.FreshingAir.Ad.Aggregation.utils;

import android.util.Log;

/**
 * 总 Application：把 io.github.ff.myapp 包内 9 个 Application 串成一条继承链后继承链尾。
 *
 * 继承链：
 * android.app.Application
 *   <- Ib <- ic <- iF <- Il <- In <- jk <- Kj <- ku <- Lo <- TotalApplication
 *
 * 因此 TotalApplication 的 onCreate() 会沿 super 链依次触发上面 9 个类的 onCreate()。
 */
public class Bomb extends Lo {

    private static final String TAG = "TotalApplication";
    private static Bomb instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Log.d(TAG, "TotalApplication onCreate: Ib -> ic -> iF -> Il -> In -> jk -> Kj -> ku -> Lo");
    }

    public static Bomb getInstance() {
        if (instance == null) {
            throw new IllegalStateException("TotalApplication not initialized yet");
        }
        return instance;
    }

    /** 链上任意一层都只是 Application 的子类，这里做一次统一校验。 */
    public static boolean isReady() {
        return instance != null;
    }
}