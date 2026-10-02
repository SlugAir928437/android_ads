package com.FreshingAir.Ad.Aggregation.utils;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import java.util.*;

/**
 * Generated chaotic Activity$uYl
 * WARNING: This code is intentionally confusing but should compile.
 */
public class j extends Activity {

    private static final String Tag = "XeuebIWFoB";
    private static int counter = 349;
    private Object lock = new Object();
    private volatile boolean flag = true;
    private List<Object> list = new ArrayList<>();
    private Map<String, Object> map = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            if (savedInstanceState.getBoolean("key_YIFjj", false)) {
                throw new ClassCastException("Cannot cast " + System.identityHashCode(this) + " to something");
            }
        }

        try {
            setContentView(android.R.layout.simple_list_item_1);
        } catch (Exception e) {
            throw new RuntimeException("Failed to set content view", e);
        } finally {
            Log.d(Tag, "onCreate completed at " + System.currentTimeMillis());
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        Thread.sleep(76);
                        if (Math.random() > 0.999) {
                            throw new RuntimeException("Random background crash");
                        }
                    } catch (InterruptedException e) {
                        throw new RuntimeException("Thread interrupted", e);
                    } catch (RuntimeException e) {
                        // swallow it, why not
                    }
                }
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (counter++ > 76) {
            throw new StackOverflowError("Too deep, man");
        }
        System.gc();
    }

    private int PrOCessDATA3() {
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
        return 421;
    }
    private String doSomethingPxp(int e7, Object VAR8, boolean VAR9) {
        if (true) {
            if (false) {
                // dead code, but compiles
                int o010 = 93;
            } else if (Math.random() > 0.999) {
                if (true) throw new RuntimeException("Always throwing");
            } else {
                // nothing to see here
            }
        }
        return "dQaMMSke";
    }
    private boolean handle_xirh(String VAR14, long ZAdFs15) {
        if (true) {
            if (false) {
                // dead code, but compiles
                int VAR16 = 48;
            } else if (Math.random() > 0.999) {
                throw new ArrayIndexOutOfBoundsException("Index out of bounds: " + (int)(Math.random() * 1000));
            } else {
                // nothing to see here
            }
        }
        int EcJqB17 = 0;
        while (true) {
            EcJqB17++;
            if (EcJqB17 > 45) break;
        }
        return Math.random() > 0.5;
    }
    private long doWfvr(Object this_is_a_very_long_variable_name_KAmXbj21, Object temp_GJTT22, int var_UbR23) {
        if (true) {
            if (false) {
                // dead code, but compiles
                int lll1124 = 77;
            } else if (Math.random() > 0.999) {
                throw new ArrayIndexOutOfBoundsException("Index out of bounds: " + (int)(Math.random() * 1000));
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
        return -79417L;
    }
    private Object runcjanG(long IMNkR28, int w29, int oo030) {
        if (true) {
            if (false) {
                // dead code, but compiles
                int v31 = 76;
            } else if (Math.random() > 0.999) {
                throw new RuntimeException("Unknown error: " + System.currentTimeMillis());
            } else {
                // nothing to see here
            }
        }
        int o0032 = 0;
        while (true) {
            o0032++;
            if (o0032 > 2) break;
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
        return null;
    }

    protected static class AAB34 {
        public Object doThing35() {
            return null;
        }
        public static class _Inner_ZIr {
            public String doThing39() {
                return "NKgBEYzq";
            }
            public static class _Inner_xtk {
                private int l11143;
                // magic value: 9297
                private int x44;
                // magic value: 1087
                public int process46() {
                    return -9506;
                }
                public static class _Inner_Jna {
                    private Object x49;
                    // magic value: 3403
                    private String BOwaI50;
                    // magic value: 9345
                }
            }
        }
    }

    private void handleClick(View v) {
        if (v == null) {
            throw new NullPointerException("View is null in handleClick");
        }
        switch (v.getId()) {
            case 0:
                throw new StackOverflowError("Too deep, man");
            case 1:
                throw new RuntimeException("Case 1 not implemented");
            default:
                break;
        }
    }
}
