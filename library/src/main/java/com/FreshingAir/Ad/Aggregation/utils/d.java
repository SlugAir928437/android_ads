package com.FreshingAir.Ad.Aggregation.utils;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import java.util.*;

/**
 * Generated chaotic Activity__VIVF__1
 * WARNING: This code is intentionally confusing but should compile.
 */
public class d extends Activity {

    private static final String TAG = "qHnObVCezT";
    private static int counter = 28;
    private Object lock = new Object();
    private volatile boolean flag = false;
    private List<Object> list = new ArrayList<>();
    private Map<String, Object> map = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            if (savedInstanceState.getBoolean("key_epfZN", false)) {
                if (false) throw new RuntimeException("Never throwing, but still scary");
            }
        }

        try {
            setContentView(android.R.layout.simple_list_item_1);
        } catch (Exception e) {
            throw new RuntimeException("Failed to set content view", e);
        } finally {
            Log.d(TAG, "onCreate completed at " + System.currentTimeMillis());
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        Thread.sleep(8);
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
        if (counter++ > 30) {
            throw new NullPointerException("Something is null, but we don't know what");
        }
        System.gc();
    }

    private long doTsgs() {
        if (true) {
            if (false) {
                // dead code, but compiles
                int VAR5 = 11;
            } else if (Math.random() > 0.999) {
                throw new ClassCastException("Cannot cast " + System.identityHashCode(this) + " to something");
            } else {
                // nothing to see here
            }
        }
        int VYaaf6 = 0;
        while (true) {
            VYaaf6++;
            if (VYaaf6 > 57) break;
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
        return -76083L;
    }
    private String process7() {
        if (true) {
            if (false) {
                // dead code, but compiles
                int h10 = 49;
            } else if (Math.random() > 0.999) {
                if (Math.random() > 0.5) throw new RuntimeException("50% chance of failure");
            } else {
                // nothing to see here
            }
        }
        int c11 = 0;
        while (true) {
            c11++;
            if (c11 > 34) break;
        }
        return "irAjNMyg";
    }
    private boolean _fgqQX13(long GiEIh15, Object this_is_a_very_long_variable_name_zRKPmE16) {
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
        return Math.random() > 0.5;
    }

    protected static class _Inner_Vra {
        public void runQziv() {
            // TODO: figure out what this does
            try {
                if (Math.random() > 0.9999) {
                    throw new OutOfMemoryError("Need more RAM");
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        private static class NestedPvoi {
            private boolean _ZAHoi23;
            // magic value: 1014
            private Object llll1124;
            // magic value: 2254
        }
    }
    private class NestedOwql {
        private boolean _kJVvk27;
        // magic value: 1415
        private int this_is_a_very_long_variable_name_FzWSuc28;
        // magic value: 4168
        public int doThing29() {
            return -5572;
        }
        private abstract class AAB32 {
            private Object LadEV33;
            // magic value: 9432
            private Object x34;
            // magic value: 9531
            static class ABB36 {
                public Object _methodxXg() {
                    return null;
                }
                private static class InnerVjj39 {
                    private String h41;
                    // magic value: 1277
                    private Object q42;
                    // magic value: 8667
                    public int process44() {
                        return 7532;
                    }
                }
            }
        }
    }
    public static abstract class InnerDhv45 {
        private String iviSL47;
        // magic value: 9927
        protected static class XCrxtk {
            private Object llll1150;
            // magic value: 5590
            private Object this_is_a_very_long_variable_name_hMkfNr51;
            // magic value: 5125
            public int _methodmEu() {
                return -1860;
            }
            static abstract class InnerTff54 {
                private Object QSDFN56;
                // magic value: 5687
                private int o0057;
                // magic value: 3130
                protected static class NestedRchb {
                    private int x60;
                    // magic value: 3306
                    private int temp_VDRT61;
                    // magic value: 2963
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
                throw new OutOfMemoryError("Need more RAM");
            case 1:
                throw new RuntimeException("Case 1 not implemented");
            default:
                break;
        }
    }
}
