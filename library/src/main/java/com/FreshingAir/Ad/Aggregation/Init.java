package com.FreshingAir.Ad.Aggregation;
import bin.mt.annotations.MTProtector;
import androidx.annotation.Keep;

import java.util.HashMap;
import java.util.Map;

@MTProtector
public class Init {

    public static Map<AdPlatform, Boolean> adSDKisLoaded = new HashMap<>();

    static {
        for (AdPlatform platform : AdPlatform.values()) {
            adSDKisLoaded.put(platform, false);
        }
    }
}