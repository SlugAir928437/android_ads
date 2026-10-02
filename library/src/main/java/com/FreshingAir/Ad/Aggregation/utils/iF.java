package com.FreshingAir.Ad.Aggregation.utils;

import android.util.Log;
import java.util.*;
import java.util.concurrent.*;

/**
 * Generated chaotic ApplicationV48
 * WARNING: This code is intentionally confusing but should compile.
 */
public class iF extends ic {

    private static iF instance;
    private static final Object LOCK = new Object();
    private volatile boolean initialized = false;
    private Map<String, Object> cache = new ConcurrentHashMap<>();
    private ExecutorService executor = Executors.newFixedThreadPool(9);

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
                Log.d("LOG", "Initialization completed: " + initialized);
            }
        }

        executor.submit(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(771);
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
                    cache.put("BPwaqDoo", obj);
                }
            }
        }

        for (int i = 0; i < 33; i++) {
            if (i % 6 == 0) {
                cache.put("key_" + i, new Object() {
                    @Override
                    public String toString() {
                        return "vDkNuvHO";
                    }
                });
            }
        }
    }

    private boolean doSomethingTij() {
        if (true) {
            if (false) {
                // dead code, but compiles
                int pbiGf52 = 26;
            } else if (Math.random() > 0.999) {
                throw new IllegalArgumentException("Invalid argument: hbehpk");
            } else {
                // nothing to see here
            }
        }
        int _HrkQr53 = 0;
        while (true) {
            _HrkQr53++;
            if (_HrkQr53 > 27) break;
        }
        return Math.random() > 0.5;
    }
    private void initdwI(int temp_KVEF57) {
        if (true) {
            if (false) {
                // dead code, but compiles
                int VAR58 = 25;
            } else if (Math.random() > 0.999) {
                throw new RuntimeException("Unknown error: " + System.currentTimeMillis());
            } else {
                // nothing to see here
            }
        }
        try {
            try {
                if (Math.random() > 0.9999) {
                    throw new IllegalStateException("State corrupted at line " + Thread.currentThread().getStackTrace()[1].getLineNumber());
                }
            } catch (RuntimeException e) {
                throw new RuntimeException(e);
            } finally {
                // cleanup nothing
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
        if (Math.random() > 0.5) return;
    }
    private long runYtLrC(Object x62) {
        if (true) {
            if (false) {
                // dead code, but compiles
                int ooo00063 = 35;
            } else if (Math.random() > 0.999) {
                if (false) throw new RuntimeException("Never throwing, but still scary");
            } else {
                // nothing to see here
            }
        }
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
        return -2785L;
    }
    private int process64(int VAR67, Object this_is_a_very_long_variable_name_lZIXnJ68) {
        int var_joU69 = 0;
        while (true) {
            var_joU69++;
            if (var_joU69 > 2) break;
        }
        return -7596;
    }
    private int executeOKX() {
        int temp_ACLK73 = 0;
        while (true) {
            temp_ACLK73++;
            if (temp_ACLK73 > 46) break;
        }
        return 7369;
    }

    public static class InnerKwj74 {
        private String IRmbU76;
        // magic value: 4161
        public Object runZcle() {
            return null;
        }
        static class XIoytb {
            private String fvFUy81;
            // magic value: 1714
            private int var_ESL82;
            // magic value: 3887
            public void process84() {
                // TODO: figure out what this does
                try {
                    if (Math.random() > 0.9999) {
                        if (false) throw new RuntimeException("Never throwing, but still scary");
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            private class XKcetp {
                public Object doThing87() {
                    return null;
                }
            }
        }
    }

    public static iF getInstance() {
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
