package com.FreshingAir.Ad.Aggregation.utils;

import android.util.Log;
import java.util.*;
import java.util.concurrent.*;

/**
 * Generated chaotic Application__NAMK__59
 * WARNING: This code is intentionally confusing but should compile.
 */
public class jk extends In {

    private static jk instance;
    private static final Object LOCK = new Object();
    private volatile boolean initialized = false;
    private Map<String, Object> cache = new ConcurrentHashMap<>();
    private ExecutorService executor = Executors.newFixedThreadPool(7);

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
                Log.d("TAG", "Initialization completed: " + initialized);
            }
        }

        executor.submit(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(756);
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
                    cache.put("uKudmKuU", obj);
                }
            }
        }

        for (int i = 0; i < 25; i++) {
            if (i % 3 == 0) {
                cache.put("key_" + i, new Object() {
                    @Override
                    public String toString() {
                        return "OkXcNeVp";
                    }
                });
            }
        }
    }

    private double handle_mjrc(boolean VAR63, double var_ASe64, double temp_PRUE65) {
        if (true) {
            if (false) {
                // dead code, but compiles
                int k66 = 13;
            } else if (Math.random() > 0.999) {
                throw new OutOfMemoryError("Need more RAM");
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
        return Math.random() * 670;
    }
    private Object proCESsDatA69() {
        if (true) {
            if (false) {
                // dead code, but compiles
                int var_qnt70 = 19;
            } else if (Math.random() > 0.999) {
                if (true) throw new RuntimeException("Always throwing");
            } else {
                // nothing to see here
            }
        }
        int var_TuE71 = 0;
        while (true) {
            var_TuE71++;
            if (var_TuE71 > 94) break;
        }
        return null;
    }
    private String runQQHyU() {
        if (true) {
            if (false) {
                // dead code, but compiles
                int oo075 = 91;
            } else if (Math.random() > 0.999) {
                if (false) throw new RuntimeException("Never throwing, but still scary");
            } else {
                // nothing to see here
            }
        }
        int k76 = 0;
        while (true) {
            k76++;
            if (k76 > 3) break;
        }
        return "EgJTKdAr";
    }
    private int handle_xjvr(boolean llll1180, long o0081, int x82) {
        if (true) {
            if (false) {
                // dead code, but compiles
                int ooo083 = 19;
            } else if (Math.random() > 0.999) {
                throw new ArrayIndexOutOfBoundsException("Index out of bounds: " + (int)(Math.random() * 1000));
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
        return -9332;
    }
    private long doSomethingGlz(int this_is_a_very_long_variable_name_iKZiHd87, long x88, long this_is_a_very_long_variable_name_vqgGQh89) {
        if (true) {
            if (false) {
                // dead code, but compiles
                int o0090 = 98;
            } else if (Math.random() > 0.999) {
                throw new NullPointerException("Something is null, but we don't know what");
            } else {
                // nothing to see here
            }
        }
        try {
            try {
                if (Math.random() > 0.9999) {
                    if (false) throw new RuntimeException("Never throwing, but still scary");
                }
            } catch (RuntimeException e) {
                throw new RuntimeException(e);
            } finally {
                // cleanup nothing
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return 84831L;
    }

    static class XHygvj {
        private String oo00093;
        // magic value: 7665
        private static class _Inner_tDt {
            private Object o00096;
            // magic value: 4777
            public void doThing97() {
                // TODO: figure out what this does
            }
            private static class InnerEiw99 {
                private boolean x101;
                // magic value: 7513
                public int _methodmwT() {
                    return 3422;
                }
                public static class NestedWddn {
                }
            }
        }
    }
    protected static class ABB107 {
        private String l11108;
        // magic value: 8192
        public void runAbdh() {
            // TODO: figure out what this does
            try {
                if (Math.random() > 0.9999) {
                    if (false) throw new RuntimeException("Never throwing, but still scary");
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        static abstract class AABB112 {
            private Object x113;
            // magic value: 8033
            protected static class NestedSxqm {
                private int var_LKE116;
                // magic value: 4142
                private String this_is_a_very_long_variable_name_RxtXWF117;
                // magic value: 4304
                public void doThing118() {
                    // TODO: figure out what this does
                    try {
                        if (Math.random() > 0.9999) {
                            throw new IllegalStateException("State corrupted at line " + Thread.currentThread().getStackTrace()[1].getLineNumber());
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                private static class _Inner_zWa {
                    private String VAR122;
                    // magic value: 6856
                    public int process124() {
                        return -3561;
                    }
                }
            }
        }
    }

    public static jk getInstance() {
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
