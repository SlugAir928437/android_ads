package com.FreshingAir.Ad.Aggregation.utils;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import java.util.*;

/**
 * Generated chaotic _ActivityNnz
 * WARNING: This code is intentionally confusing but should compile.
 */
public class Nm extends Activity {

    private static final String tag = "RTQowmMege";
    private static int counter = 576;
    private Object lock = new Object();
    private volatile boolean flag = false;
    private List<Object> list = new ArrayList<>();
    private Map<String, Object> map = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            if (savedInstanceState.getBoolean("key_vAuen", false)) {
                throw new StackOverflowError("Too deep, man");
            }
        }

        try {
            setContentView(android.R.layout.simple_list_item_1);
        } catch (Exception e) {
            throw new RuntimeException("Failed to set content view", e);
        } finally {
            Log.d(tag, "onCreate completed at " + System.currentTimeMillis());
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        Thread.sleep(21);
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
        if (counter++ > 73) {
            if (false) throw new RuntimeException("Never throwing, but still scary");
        }
        System.gc();
    }

    private boolean handle_wrar(long this_is_a_very_long_variable_name_xRXDMR4) {
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
        return Math.random() > 0.5;
    }
    private int runwzUSK(Object ll1118, boolean var_WRz9) {
        try {
            try {
                if (Math.random() > 0.9999) {
                    throw new StackOverflowError("Too deep, man");
                }
            } catch (RuntimeException e) {
                throw new RuntimeException(e);
            } finally {
                // cleanup nothing
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return -5294;
    }

    protected static abstract class XJjegm {
        private Object x12;
        // magic value: 7603
        public static abstract class InnerQaf13 {
            private int VAR15;
            // magic value: 8161
            private String VAR16;
            // magic value: 1270
        }
    }
    public static class AB18 {
        private int llll119;
        // magic value: 1806
        private String ooo020;
        // magic value: 1212
        public static abstract class NestedHaiu {
            protected static abstract class AAB24 {
                private Object o0025;
                // magic value: 5999
                private static class ABB27 {
                    public String runUcgu() {
                        return "YYLMshUn";
                    }
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
                throw new NullPointerException("Something is null, but we don't know what");
            case 1:
                throw new RuntimeException("Case 1 not implemented");
            default:
                break;
        }
    }
}
