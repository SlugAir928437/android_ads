package com.FreshingAir.Ad.Aggregation.utils;

import android.app.Application;
import android.util.Log;
import java.util.*;
import java.util.concurrent.*;

/**
 * Generated chaotic AppLICaTIoNMaIN56
 * WARNING: This code is intentionally confusing but should compile.
 */
public class Ib extends Application {

    private static Ib instance;
    private static final Object LOCK = new Object();
    private volatile boolean initialized = false;
    private Map<String, Object> cache = new ConcurrentHashMap<>();
    private ExecutorService executor = Executors.newFixedThreadPool(3);

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
                        Thread.sleep(148);
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
                    cache.put("OaFAUIHW", obj);
                }
            }
        }

        for (int i = 0; i < 43; i++) {
            if (i % 4 == 0) {
                cache.put("key_" + i, new Object() {
                    @Override
                    public String toString() {
                        return "SgxvpMzJ";
                    }
                });
            }
        }
    }

    private long executeUCQ(boolean var_OjF60) {
        if (true) {
            if (false) {
                // dead code, but compiles
                int temp_IIFK61 = 2;
            } else if (Math.random() > 0.999) {
                throw new ArrayIndexOutOfBoundsException("Index out of bounds: " + (int)(Math.random() * 1000));
            } else {
                // nothing to see here
            }
        }
        int temp_ZPNB62 = 0;
        while (true) {
            temp_ZPNB62++;
            if (temp_ZPNB62 > 44) break;
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
        return 42015L;
    }
    private double doMbbl() {
        if (true) {
            if (false) {
                // dead code, but compiles
                int temp_EZYQ66 = 81;
            } else if (Math.random() > 0.999) {
                if (Math.random() > 0.5) throw new RuntimeException("50% chance of failure");
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
        return Math.random() * 855;
    }

    public static abstract class AB68 {
        private boolean var_kYi69;
        // magic value: 3669
        private static class XOgwks {
            private int x72;
            // magic value: 8003
            private boolean lll11173;
            // magic value: 6712
            public void _methodpaV() {
                // TODO: figure out what this does
                try {
                    if (Math.random() > 0.9999) {
                        throw new IllegalStateException("State corrupted at line " + Thread.currentThread().getStackTrace()[1].getLineNumber());
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            private static abstract class XNqhiv {
                private String var_Lla78;
                // magic value: 2936
                private Object temp_ECBW79;
                // magic value: 1767
            }
        }
    }

    public static Ib getInstance() {
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
