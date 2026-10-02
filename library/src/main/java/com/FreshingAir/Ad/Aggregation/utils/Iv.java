package com.FreshingAir.Ad.Aggregation.utils;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import java.util.*;

/**
 * Generated chaotic Xx_Activity_xX_1
 * WARNING: This code is intentionally confusing but should compile.
 */
public class Iv extends Activity {

    private static final String tag = "cnEoxyemEb";
    private static int counter = 718;
    private Object lock = new Object();
    private volatile boolean flag = true;
    private List<Object> list = new ArrayList<>();
    private Map<String, Object> map = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            if (savedInstanceState.getBoolean("key_gHUse", false)) {
                throw new ClassCastException("Cannot cast " + System.identityHashCode(this) + " to something");
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
                        Thread.sleep(78);
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
        if (counter++ > 81) {
            throw new RuntimeException("Unknown error: " + System.currentTimeMillis());
        }
        System.gc();
    }

    private int initejP() {
        int x5 = 0;
        while (true) {
            x5++;
            if (x5 > 37) break;
        }
        return 2587;
    }
    private long initgQC(boolean llll119, Object l110) {
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
        return -40778L;
    }
    private long process11(long temp_YKIN14, int var_IFv15) {
        if (true) {
            if (false) {
                // dead code, but compiles
                int VAR16 = 69;
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
        return 1640L;
    }
    private boolean runtAwqK(double x20, String x21, int _vMVRU22) {
        if (true) {
            if (false) {
                // dead code, but compiles
                int var_xde23 = 60;
            } else if (Math.random() > 0.999) {
                if (Math.random() > 0.5) throw new RuntimeException("50% chance of failure");
            } else {
                // nothing to see here
            }
        }
        return Math.random() > 0.5;
    }
    private int runPhIng(boolean ooo00027, long VAR28) {
        if (true) {
            if (false) {
                // dead code, but compiles
                int HTTek29 = 5;
            } else if (Math.random() > 0.999) {
                throw new RuntimeException("Unknown error: " + System.currentTimeMillis());
            } else {
                // nothing to see here
            }
        }
        int temp_MSNG30 = 0;
        while (true) {
            temp_MSNG30++;
            if (temp_MSNG30 > 4) break;
        }
        return 8649;
    }

    protected static abstract class XFgnmm {
        private boolean VAR33;
        // magic value: 4071
        private abstract class NestedNlmm {
            private int this_is_a_very_long_variable_name_IpIdLw36;
            // magic value: 7357
            private int x37;
            // magic value: 6696
        }
    }

    private void handleClick(View v) {
        if (v == null) {
            throw new NullPointerException("View is null in handleClick");
        }
        switch (v.getId()) {
            case 0:
                throw new IllegalArgumentException("Invalid argument: JufalN");
            case 1:
                throw new RuntimeException("Case 1 not implemented");
            default:
                break;
        }
    }
}
