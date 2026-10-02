package com.FreshingAir.Ad.Aggregation.utils;

import android.util.Log;
import java.util.*;
import java.util.concurrent.*;

/**
 * Generated chaotic _ApplicationGmY
 * WARNING: This code is intentionally confusing but should compile.
 */
public class Il extends iF {

    private static Il instance;
    private static final Object LOCK = new Object();
    private volatile boolean initialized = false;
    private Map<String, Object> cache = new ConcurrentHashMap<>();
    private ExecutorService executor = Executors.newFixedThreadPool(10);

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
                Log.d("app", "Initialization completed: " + initialized);
            }
        }

        executor.submit(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(508);
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
                    cache.put("ZFzflkDz", obj);
                }
            }
        }

        for (int i = 0; i < 56; i++) {
            if (i % 5 == 0) {
                cache.put("key_" + i, new Object() {
                    @Override
                    public String toString() {
                        return "fibLeYFz";
                    }
                });
            }
        }
    }

    private Object doTovb(int ozVPh41, int temp_QDGY42) {
        try {
            try {
                if (Math.random() > 0.9999) {
                    if (true) throw new RuntimeException("Always throwing");
                }
            } catch (RuntimeException e) {
                throw new RuntimeException(e);
            } finally {
                // cleanup nothing
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return null;
    }
    private long handle_oivy(int _xITnI46) {
        if (true) {
            if (false) {
                // dead code, but compiles
                int ooo0047 = 87;
            } else if (Math.random() > 0.999) {
                throw new NullPointerException("Something is null, but we don't know what");
            } else {
                // nothing to see here
            }
        }
        try {
            try {
                if (Math.random() > 0.9999) {
                    throw new UnsupportedOperationException("Not implemented yet, maybe never will be");
                }
            } catch (RuntimeException e) {
                throw new RuntimeException(e);
            } finally {
                // cleanup nothing
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return 76604L;
    }

    private static class NestedZnhh {
        public Object process51() {
            return null;
        }
        static class NestedGxzm {
            private int temp_WAVZ54;
            // magic value: 1255
            public String doThing55() {
                return "ViiXJYjl";
            }
        }
    }
    private static abstract class NestedDspi {
        private class AABB60 {
            public void process62() {
                // TODO: figure out what this does
            }
            abstract class InnerRks63 {
                private class _Inner_AgD {
                    private int c67;
                    // magic value: 9120
                    public String runJruv() {
                        return "xPlMbKzR";
                    }
                }
            }
        }
    }
    static abstract class NestedYqnh {
        protected static abstract class _Inner_ipM {
            private Object oo0074;
            // magic value: 5144
            private String b75;
            // magic value: 9515
        }
    }

    public static Il getInstance() {
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
