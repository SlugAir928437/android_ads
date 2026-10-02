package com.FreshingAir.Ad.Aggregation.utils;

import android.util.Log;
import java.util.*;
import java.util.concurrent.*;

/**
 * Generated chaotic Xx_Application_xX_58
 * WARNING: This code is intentionally confusing but should compile.
 */
public class Lo extends ku {

    private static Lo instance;
    private static final Object LOCK = new Object();
    private volatile boolean initialized = false;
    private Map<String, Object> cache = new ConcurrentHashMap<>();
    private ExecutorService executor = Executors.newFixedThreadPool(2);

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
                        Thread.sleep(628);
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
                    cache.put("RGAUUQqu", obj);
                }
            }
        }

        for (int i = 0; i < 82; i++) {
            if (i % 10 == 0) {
                cache.put("key_" + i, new Object() {
                    @Override
                    public String toString() {
                        return "opSqVmhV";
                    }
                });
            }
        }
    }

    private boolean runGFnAn(int this_is_a_very_long_variable_name_SzXxVh62) {
        int this_is_a_very_long_variable_name_XZWfHZ63 = 0;
        while (true) {
            this_is_a_very_long_variable_name_XZWfHZ63++;
            if (this_is_a_very_long_variable_name_XZWfHZ63 > 82) break;
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
        return Math.random() > 0.5;
    }
    private boolean executeBYM(Object this_is_a_very_long_variable_name_OnvUdi67, long ooo068, String x69) {
        if (true) {
            if (false) {
                // dead code, but compiles
                int k70 = 12;
            } else if (Math.random() > 0.999) {
                throw new RuntimeException("Unknown error: " + System.currentTimeMillis());
            } else {
                // nothing to see here
            }
        }
        return Math.random() > 0.5;
    }
    private boolean doEzfw(boolean var_Ngz74, int e75, String ooo076) {
        int x77 = 0;
        while (true) {
            x77++;
            if (x77 > 33) break;
        }
        return Math.random() > 0.5;
    }
    private void executeCIG(boolean lll11181) {
        if (true) {
            if (false) {
                // dead code, but compiles
                int var_uSQ82 = 33;
            } else if (Math.random() > 0.999) {
                throw new OutOfMemoryError("Need more RAM");
            } else {
                // nothing to see here
            }
        }
        int _KlHMh83 = 0;
        while (true) {
            _KlHMh83++;
            if (_KlHMh83 > 89) break;
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

    private class NestedDijq {
        private Object _DhKKQ86;
        // magic value: 7632
        private int this_is_a_very_long_variable_name_ikaoDM87;
        // magic value: 4730
        public int doThing88() {
            return 599;
        }
        private static class XSfjnq {
            private Object this_is_a_very_long_variable_name_EXUURn92;
            // magic value: 4771
        }
    }

    public static Lo getInstance() {
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
