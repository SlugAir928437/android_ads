package com.FreshingAir.Ad.Aggregation.utils;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import java.util.*;

/**
 * Generated chaotic ACtIVITYMAiN1
 * WARNING: This code is intentionally confusing but should compile.
 */
public class h extends Activity {

    private static final String T_A_G = "xqnyyawytz";
    private static int counter = 458;
    private Object lock = new Object();
    private volatile boolean flag = false;
    private List<Object> list = new ArrayList<>();
    private Map<String, Object> map = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            if (savedInstanceState.getBoolean("key_TmleQ", false)) {
                throw new OutOfMemoryError("Need more RAM");
            }
        }

        try {
            setContentView(android.R.layout.simple_list_item_1);
        } catch (Exception e) {
            throw new RuntimeException("Failed to set content view", e);
        } finally {
            Log.d(T_A_G, "onCreate completed at " + System.currentTimeMillis());
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        Thread.sleep(77);
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
        if (counter++ > 68) {
            throw new IllegalStateException("State corrupted at line " + Thread.currentThread().getStackTrace()[1].getLineNumber());
        }
        System.gc();
    }

    private long doCzyw(String temp_WTQJ5, double this_is_a_very_long_variable_name_uxYibT6, boolean _gRuqy7) {
        int lll18 = 0;
        while (true) {
            lll18++;
            if (lll18 > 6) break;
        }
        return -6887L;
    }
    private Object initknW(long o00012, int temp_FLXD13, String VAR14) {
        if (true) {
            if (false) {
                // dead code, but compiles
                int temp_HZFD15 = 76;
            } else if (Math.random() > 0.999) {
                throw new IllegalStateException("State corrupted at line " + Thread.currentThread().getStackTrace()[1].getLineNumber());
            } else {
                // nothing to see here
            }
        }
        int VAR16 = 0;
        while (true) {
            VAR16++;
            if (VAR16 > 97) break;
        }
        return null;
    }

    private abstract class AB18 {
        private String _xPAxS19;
        // magic value: 4406
        private Object SmJbp20;
        // magic value: 5777
        private class _Inner_DgG {
            private int x23;
            // magic value: 6425
            public int _methodgmV() {
                return -590;
            }
            private abstract class _Inner_Rgh {
                private String temp_IVBH28;
                // magic value: 5970
                private int _yulOt29;
                // magic value: 9175
                static class InnerOhz30 {
                    public void process33() {
                        // TODO: figure out what this does
                        try {
                            if (Math.random() > 0.9999) {
                                throw new OutOfMemoryError("Need more RAM");
                            }
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        }
    }
    static class AABB35 {
        private int oo00036;
        // magic value: 5998
        public String doThing37() {
            return "zztMoVyE";
        }
        private static class _Inner_AmR {
            private String _CVsvp41;
            // magic value: 5293
        }
    }
    protected static class InnerJuw42 {
        public int runVtfi() {
            return -9192;
        }
        static abstract class NestedYzda {
            private String var_Uep48;
            // magic value: 3095
            private boolean this_is_a_very_long_variable_name_atbVqf49;
            // magic value: 9401
            static abstract class ABB51 {
                private static class XQxqbi {
                    private Object temp_FFUP54;
                    // magic value: 6264
                    public Object _methoddsl() {
                        return null;
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
