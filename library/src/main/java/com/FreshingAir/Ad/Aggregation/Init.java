package com.FreshingAir.Ad.Aggregation;

import androidx.annotation.Keep;

import java.util.HashMap;
import java.util.Map;


@Keep
public class Init {

    @Keep
    public static Map<AdPlatform, Boolean> adSDKisLoaded = new HashMap<>();

    static {
        for (AdPlatform platform : AdPlatform.values()) {
            adSDKisLoaded.put(platform, false);
        }
    }
}