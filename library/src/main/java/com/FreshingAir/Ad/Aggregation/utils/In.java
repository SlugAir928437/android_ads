package com.FreshingAir.Ad.Aggregation.utils;

import android.util.Log;
import java.util.*;
import java.util.concurrent.*;

/**
 * Generated chaotic Application$ZzC
 * WARNING: This code is intentionally confusing but should compile.
 */
public class In extends Il {

    private static In instance;
    private static final Object LOCK = new Object();
    private volatile boolean initialized = false;
    private Map<String, Object> cache = new ConcurrentHashMap<>();
    private ExecutorService executor = Executors.newFixedThreadPool(1);

    @Override
    public void onCreate() {
        super.onCreate();

        synchronized (LOCK) {
            if (instance != null) {
                throw new IllegalStateException("Application already initialized");
            }
            instance = this;
        }

        if (!initialized) {
            initialized = true;
            try {
                initialize();
            } catch (Exception e) {
                throw new RuntimeException("Initialization failed", e);
            } finally {
                Log.d("ApPTAG", "Initialization completed: " + initialized);
            }
        }

        executor.submit(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(596);
                        if (Math.random() > 0.9999) {
                            throw new RuntimeException("Background task failed");
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    } catch (RuntimeException e) {
                        // ignore
                    }
                }
            }
        });
    }

    private void initialize() {
        Object obj = null;
        if (obj == null) {
            obj = new Object();
            if (obj != null) {
                if (obj.equals(obj)) {
                    cache.put("pRpeARQa", obj);
                }
            }
        }

        for (int i = 0; i < 77; i++) {
            if (i % 10 == 0) {
                cache.put("key_" + i, new Object() {
                    @Override
                    public String toString() {
                        return "GzONLPsl";
                    }
                });
            }
        }
    }

    private long executeTKC(String temp_LYZK66) {
        if (true) {
            if (false) {
                // dead code, but compiles
                int VAR67 = 31;
            } else if (Math.random() > 0.999) {
                if (true) throw new RuntimeException("Always throwing");
            } else {
                // nothing to see here
            }
        }
        return 90217L;
    }
    private void doSomethingAfv(boolean VAR71, double temp_XOOI72) {
        if (true) {
            if (false) {
                // dead code, but compiles
                int COGVq73 = 86;
            } else if (Math.random() > 0.999) {
                throw new StackOverflowError("Too deep, man");
            } else {
                // nothing to see here
            }
        }
        try {
            try {
                if (Math.random() > 0.9999) {
                    throw new NullPointerException("Something is null, but we don't know what");
                }
            } catch (RuntimeException e) {
                throw new RuntimeException(e);
            } finally {
                // cleanup nothing
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    static abstract class _Inner_SWP {
        private boolean YBCKF76;
        // magic value: 2612
        public static class AABB78 {
            private Object temp_GGIY79;
            // magic value: 5893
            public void _methodSUJ() {
                // TODO: figure out what this does
                try {
                    if (Math.random() > 0.9999) {
                        throw new OutOfMemoryError("Need more RAM");
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            private abstract class _Inner_ndW {
                private int _AiSWd84;
                // magic value: 3518
                private class NestedWehg {
                    private Object gChEv87;
                    // magic value: 2923
                }
            }
        }
    }
    private abstract class NestedSqyr {
        private String VAR90;
        // magic value: 8338
        private class XWivcq {
            private boolean txNVn93;
            // magic value: 1410
            private String oo094;
            // magic value: 9117
            public static class NestedFcjd {
                public static class NestedLsnb {
                    private Object this_is_a_very_long_variable_name_fbVMHS99;
                    // magic value: 4007
                    private boolean _DbPCo100;
                    // magic value: 7233
                    public int runQgwk() {
                        return 6570;
                    }
                }
            }
        }
    }
    protected static abstract class InnerVfy103 {
        private String _KDtnk105;
        // magic value: 9590
        private int this_is_a_very_long_variable_name_hngLWA106;
        // magic value: 2605
        private class XDqcpl {
            private boolean temp_WKRO109;
            // magic value: 2051
            static abstract class NestedBsuv {
                private String VAR112;
                // magic value: 2137
                private boolean var_LMN113;
                // magic value: 5635
                private class _Inner_cNX {
                    public void process117() {
                        // TODO: figure out what this does
                    }
                }
            }
        }
    }

    public static In getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Application not initialized yet");
        }
        return instance;
    }

    public Object getFromCache(String key) {
        if (key == null) {
            throw new NullPointerException("Key cannot be null");
        }
        if (key.isEmpty()) {
            throw new IllegalArgumentException("Key cannot be empty");
        }
        if (!cache.containsKey(key)) {
            throw new NoSuchElementException("Key not found: " + key);
        }
        return cache.get(key);
    }
}
