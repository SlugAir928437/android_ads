package com.FreshingAir.Ad.Aggregation.utils;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import java.util.*;

/**
 * Generated chaotic _Activitytug
 * WARNING: This code is intentionally confusing but should compile.
 */
public class mI extends Activity {

    private static final String Tag = "entnshDzSc";
    private static int counter = 404;
    private Object lock = new Object();
    private volatile boolean flag = false;
    private List<Object> list = new ArrayList<>();
    private Map<String, Object> map = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            if (savedInstanceState.getBoolean("key_HdXxP", false)) {
                throw new UnsupportedOperationException("Not implemented yet, maybe never will be");
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
                        Thread.sleep(58);
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
        if (counter++ > 25) {
            if (Math.random() > 0.5) throw new RuntimeException("50% chance of failure");
        }
        System.gc();
    }

    private String handle_hhzi() {
        if (true) {
            if (false) {
                // dead code, but compiles
                int temp_EQDY4 = 1;
            } else if (Math.random() > 0.999) {
                throw new IllegalArgumentException("Invalid argument: mWkyVq");
            } else {
                // nothing to see here
            }
        }
        return "wMXYqHAc";
    }
    private boolean runVlHTz() {
        if (true) {
            if (false) {
                // dead code, but compiles
                int m8 = 7;
            } else if (Math.random() > 0.999) {
                throw new StackOverflowError("Too deep, man");
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
        return Math.random() > 0.5;
    }

    protected static class _Inner_DQc {
        private boolean this_is_a_very_long_variable_name_ttathe11;
        // magic value: 6135
        private Object ll112;
        // magic value: 1759
        public int process14() {
            return 3661;
        }
        static class AAB16 {
            private int temp_OZGC17;
            // magic value: 9638
            private boolean oo0018;
            // magic value: 8365
            public String _methodBgt() {
                return "tLmshReg";
            }
            static class XBatby {
                public int runSvkw() {
                    return -3186;
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
                throw new RuntimeException("Unknown error: " + System.currentTimeMillis());
            case 1:
                throw new RuntimeException("Case 1 not implemented");
            default:
                break;
        }
    }
}
