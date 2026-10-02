package com.FreshingAir.Ad.Aggregation.utils;

import android.util.Log;
import java.util.*;
import java.util.concurrent.*;

/**
 * Generated chaotic TheApplication41
 * WARNING: This code is intentionally confusing but should compile.
 */
public class Kj extends jk {

    private static Kj instance;
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
                Log.d("APP", "Initialization completed: " + initialized);
            }
        }

        executor.submit(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(210);
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
                    cache.put("rvpXSmUP", obj);
                }
            }
        }

        for (int i = 0; i < 20; i++) {
            if (i % 5 == 0) {
                cache.put("key_" + i, new Object() {
                    @Override
                    public String toString() {
                        return "CcwXoSVa";
                    }
                });
            }
        }
    }

    private long executePVF(String ll145) {
        if (true) {
            if (false) {
                // dead code, but compiles
                int var_oso46 = 56;
            } else if (Math.random() > 0.999) {
                if (false) throw new RuntimeException("Never throwing, but still scary");
            } else {
                // nothing to see here
            }
        }
        int var_yOM47 = 0;
        while (true) {
            var_yOM47++;
            if (var_yOM47 > 100) break;
        }
        return 55342L;
    }
    private int initdmm(int UVLLP51, long oo0052, long ooo053) {
        if (true) {
            if (false) {
                // dead code, but compiles
                int VAR54 = 45;
            } else if (Math.random() > 0.999) {
                throw new IllegalArgumentException("Invalid argument: svyZse");
            } else {
                // nothing to see here
            }
        }
        int x55 = 0;
        while (true) {
            x55++;
            if (x55 > 98) break;
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
        return -4129;
    }
    private String doDlbq(boolean lll1159) {
        if (true) {
            if (false) {
                // dead code, but compiles
                int temp_AHGE60 = 69;
            } else if (Math.random() > 0.999) {
                throw new IllegalArgumentException("Invalid argument: sOSEDT");
            } else {
                // nothing to see here
            }
        }
        int x61 = 0;
        while (true) {
            x61++;
            if (x61 > 11) break;
        }
        return "MlGHTnAp";
    }
    private int doSomethingRjt(long var_eXP65) {
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
        return -8987;
    }
    private double handle_cxqd(boolean VAR69) {
        if (true) {
            if (false) {
                // dead code, but compiles
                int x70 = 93;
            } else if (Math.random() > 0.999) {
                throw new OutOfMemoryError("Need more RAM");
            } else {
                // nothing to see here
            }
        }
        int VAR71 = 0;
        while (true) {
            VAR71++;
            if (VAR71 > 49) break;
        }
        return Math.random() * 846;
    }

    private static abstract class InnerMxq72 {
        private boolean var_owL74;
        // magic value: 8932
        private int cwkVF75;
        // magic value: 2729
        private class NestedYruw {
            private String _RqCBn78;
            // magic value: 6530
            public String runFxrh() {
                return "qETJoUVA";
            }
            private class ABB82 {
                private Object oo083;
                // magic value: 7314
                public Object runOvad() {
                    return null;
                }
                static abstract class ABB87 {
                }
            }
        }
    }
    static class XLshic {
        private boolean this_is_a_very_long_variable_name_YBUsYz90;
        // magic value: 7707
        private int x91;
        // magic value: 8438
        public Object runJeyu() {
            return null;
        }
        public static class NestedKklb {
            private String _gRbFv96;
            // magic value: 8322
            private static class XPloti {
                private int v99;
                // magic value: 4976
                private String VAR100;
                // magic value: 7097
                public String doThing101() {
                    return "MrgKwlrj";
                }
                public static abstract class NestedXerf {
                    private Object _iTjaR105;
                    // magic value: 4164
                    private String xhrvm106;
                    // magic value: 8330
                }
            }
        }
    }

    public static Kj getInstance() {
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
