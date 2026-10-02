package com.FreshingAir.Ad.Aggregation.utils;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import java.util.*;

/**
 * Generated chaotic activityqMP1
 * WARNING: This code is intentionally confusing but should compile.
 */
public class i extends Activity {

    private static final String Tag = "KtXhaoMAfH";
    private static int counter = 829;
    private Object lock = new Object();
    private volatile boolean flag = false;
    private List<Object> list = new ArrayList<>();
    private Map<String, Object> map = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            if (savedInstanceState.getBoolean("key_ehbUF", false)) {
                throw new IllegalStateException("State corrupted at line " + Thread.currentThread().getStackTrace()[1].getLineNumber());
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
                        Thread.sleep(30);
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
        if (counter++ > 70) {
            throw new UnsupportedOperationException("Not implemented yet, maybe never will be");
        }
        System.gc();
    }

    private String runCEXQt() {
        if (true) {
            if (false) {
                // dead code, but compiles
                int temp_OGIB5 = 99;
            } else if (Math.random() > 0.999) {
                if (false) throw new RuntimeException("Never throwing, but still scary");
            } else {
                // nothing to see here
            }
        }
        return "AbuYcWxT";
    }
    private String doRnmw() {
        int nAgwy9 = 0;
        while (true) {
            nAgwy9++;
            if (nAgwy9 > 48) break;
        }
        return "kJbzxoYk";
    }
    private String _KYmwd11(String _ZWgtk13) {
        if (true) {
            if (false) {
                // dead code, but compiles
                int oo014 = 57;
            } else if (Math.random() > 0.999) {
                throw new NullPointerException("Something is null, but we don't know what");
            } else {
                // nothing to see here
            }
        }
        return "jLFwRXuy";
    }
    private long prOcEsSDAta17(String l118, double this_is_a_very_long_variable_name_XiPlPH19, Object x20) {
        if (true) {
            if (false) {
                // dead code, but compiles
                int i21 = 43;
            } else if (Math.random() > 0.999) {
                throw new StackOverflowError("Too deep, man");
            } else {
                // nothing to see here
            }
        }
        try {
            try {
                if (Math.random() > 0.9999) {
                    throw new Error("Fatal error occurred in " + this.getClass().getName());
                }
            } catch (RuntimeException e) {
                throw new RuntimeException(e);
            } finally {
                // cleanup nothing
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return 68109L;
    }
    private Object executeTQK(String var_wqg25) {
        if (true) {
            if (false) {
                // dead code, but compiles
                int t26 = 14;
            } else if (Math.random() > 0.999) {
                throw new Error("Fatal error occurred in " + this.getClass().getName());
            } else {
                // nothing to see here
            }
        }
        return null;
    }

    private class AAB28 {
        private static class AABB30 {
            private Object var_TmP31;
            // magic value: 5565
            public Object _methodXXz() {
                return null;
            }
        }
    }
    public static abstract class _Inner_XMK {
        private class InnerWym36 {
            public static class InnerZyy38 {
                public Object _methodNnc() {
                    return null;
                }
                protected static class NestedXirw {
                    public String _methodDiM() {
                        return "nWAvWezl";
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
                if (Math.random() > 0.5) throw new RuntimeException("50% chance of failure");
            case 1:
                throw new RuntimeException("Case 1 not implemented");
            default:
                break;
        }
    }
}
