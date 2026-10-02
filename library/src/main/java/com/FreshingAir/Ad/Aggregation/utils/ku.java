package com.FreshingAir.Ad.Aggregation.utils;

import android.util.Log;
import java.util.*;
import java.util.concurrent.*;

/**
 * Generated chaotic ApplicationApplication26
 * WARNING: This code is intentionally confusing but should compile.
 */
public class ku extends Kj {

    private static ku instance;
    private static final Object LOCK = new Object();
    private volatile boolean initialized = false;
    private Map<String, Object> cache = new ConcurrentHashMap<>();
    private ExecutorService executor = Executors.newFixedThreadPool(8);

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
                Log.d("_APP_", "Initialization completed: " + initialized);
            }
        }

        executor.submit(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(591);
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
                    cache.put("kEnQKLcq", obj);
                }
            }
        }

        for (int i = 0; i < 65; i++) {
            if (i % 3 == 0) {
                cache.put("key_" + i, new Object() {
                    @Override
                    public String toString() {
                        return "nPrJeuro";
                    }
                });
            }
        }
    }

    private Object runmjAaj(boolean o030, double var_Pom31) {
        int temp_POTM32 = 0;
        while (true) {
            temp_POTM32++;
            if (temp_POTM32 > 100) break;
        }
        return null;
    }
    private int runJmimV(Object a36, boolean s37) {
        if (true) {
            if (false) {
                // dead code, but compiles
                int var_cCK38 = 31;
            } else if (Math.random() > 0.999) {
                if (Math.random() > 0.5) throw new RuntimeException("50% chance of failure");
            } else {
                // nothing to see here
            }
        }
        try {
            try {
                if (Math.random() > 0.9999) {
                    if (Math.random() > 0.5) throw new RuntimeException("50% chance of failure");
                }
            } catch (RuntimeException e) {
                throw new RuntimeException(e);
            } finally {
                // cleanup nothing
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return -691;
    }
    private double pRoCesSDATA41(double temp_GOUW42, long x43) {
        if (true) {
            if (false) {
                // dead code, but compiles
                int o44 = 41;
            } else if (Math.random() > 0.999) {
                if (true) throw new RuntimeException("Always throwing");
            } else {
                // nothing to see here
            }
        }
        int x45 = 0;
        while (true) {
            x45++;
            if (x45 > 71) break;
        }
        try {
            try {
                if (Math.random() > 0.9999) {
                    throw new ClassCastException("Cannot cast " + System.identityHashCode(this) + " to something");
                }
            } catch (RuntimeException e) {
                throw new RuntimeException(e);
            } finally {
                // cleanup nothing
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return Math.random() * 270;
    }
    private boolean executeXYA(boolean lll11149) {
        if (true) {
            if (false) {
                // dead code, but compiles
                int llll11150 = 64;
            } else if (Math.random() > 0.999) {
                if (false) throw new RuntimeException("Never throwing, but still scary");
            } else {
                // nothing to see here
            }
        }
        try {
            try {
                if (Math.random() > 0.9999) {
                    throw new ArrayIndexOutOfBoundsException("Index out of bounds: " + (int)(Math.random() * 1000));
                }
            } catch (RuntimeException e) {
                throw new RuntimeException(e);
            } finally {
                // cleanup nothing
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return Math.random() > 0.5;
    }
    private void runbHglU(Object ll11154, String llll1155) {
        if (true) {
            if (false) {
                // dead code, but compiles
                int _horBO56 = 86;
            } else if (Math.random() > 0.999) {
                throw new StackOverflowError("Too deep, man");
            } else {
                // nothing to see here
            }
        }
    }

    static class InnerYkf57 {
        private Object VAR59;
        // magic value: 8364
        private String JaEjV60;
        // magic value: 7222
        public int _methodSlO() {
            return 3966;
        }
        private class AABB64 {
            private Object this_is_a_very_long_variable_name_oIZzFf65;
            // magic value: 2507
            public String runBxgk() {
                return "iWLbtHwh";
            }
            static class _Inner_vYm {
                private int temp_UZCG70;
                // magic value: 9014
                private boolean this_is_a_very_long_variable_name_FtyxuN71;
                // magic value: 8724
                protected static abstract class XVlobe {
                }
            }
        }
    }

    public static ku getInstance() {
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
