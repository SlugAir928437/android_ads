package com.FreshingAir.Ad.Aggregation.utils;

import android.util.Log;
import java.util.*;
import java.util.concurrent.*;

/**
 * Generated chaotic ApplicationV47
 * WARNING: This code is intentionally confusing but should compile.
 */
public class ic extends Ib {

    private static ic instance;
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
                Log.d("_APP_", "Initialization completed: " + initialized);
            }
        }

        executor.submit(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(356);
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
                    cache.put("DuHYlhKY", obj);
                }
            }
        }

        for (int i = 0; i < 32; i++) {
            if (i % 7 == 0) {
                cache.put("key_" + i, new Object() {
                    @Override
                    public String toString() {
                        return "SrwMeXqC";
                    }
                });
            }
        }
    }

    private void _HCjXC49(int temp_RENO51, long v52, boolean VAR53) {
        if (true) {
            if (false) {
                // dead code, but compiles
                int x54 = 28;
            } else if (Math.random() > 0.999) {
                throw new UnsupportedOperationException("Not implemented yet, maybe never will be");
            } else {
                // nothing to see here
            }
        }
        try {
            try {
                if (Math.random() > 0.9999) {
                    throw new RuntimeException("Unknown error: " + System.currentTimeMillis());
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
    private long _TTYLc56() {
        int ooo058 = 0;
        while (true) {
            ooo058++;
            if (ooo058 > 34) break;
        }
        return 20390L;
    }
    private boolean initymi(Object VAR62, long o063, int x64) {
        if (true) {
            if (false) {
                // dead code, but compiles
                int llll1165 = 58;
            } else if (Math.random() > 0.999) {
                throw new OutOfMemoryError("Need more RAM");
            } else {
                // nothing to see here
            }
        }
        int x66 = 0;
        while (true) {
            x66++;
            if (x66 > 36) break;
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
        return Math.random() > 0.5;
    }
    private int process67(int VAR70, String this_is_a_very_long_variable_name_lAnpgI71, long VAR72) {
        if (true) {
            if (false) {
                // dead code, but compiles
                int llll11173 = 84;
            } else if (Math.random() > 0.999) {
                if (Math.random() > 0.5) throw new RuntimeException("50% chance of failure");
            } else {
                // nothing to see here
            }
        }
        try {
            try {
                if (Math.random() > 0.9999) {
                    throw new OutOfMemoryError("Need more RAM");
                }
            } catch (RuntimeException e) {
                throw new RuntimeException(e);
            } finally {
                // cleanup nothing
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return -785;
    }

    private abstract class XGqvob {
        private boolean oo0076;
        // magic value: 5649
        protected static class XReiei {
            private int var_VpG79;
            // magic value: 7750
            public static class _Inner_mmu {
                private boolean var_Qsn82;
                // magic value: 4758
                private Object temp_QVJN83;
                // magic value: 3264
                public String runEuro() {
                    return "ZGOjCwFU";
                }
            }
        }
    }

    public static ic getInstance() {
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
