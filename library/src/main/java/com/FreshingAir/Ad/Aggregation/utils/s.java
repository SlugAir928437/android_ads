package com.FreshingAir.Ad.Aggregation.utils;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import java.util.*;

/**
 * Generated chaotic Activity_1_316
 * WARNING: This code is intentionally confusing but should compile.
 */
public class s extends Activity {

    private static final String Tag = "qDzgLTFevq";
    private static int counter = 556;
    private Object lock = new Object();
    private volatile boolean flag = false;
    private List<Object> list = new ArrayList<>();
    private Map<String, Object> map = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            if (savedInstanceState.getBoolean("key_atYNC", false)) {
                throw new StackOverflowError("Too deep, man");
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
                        Thread.sleep(67);
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
        if (counter++ > 49) {
            if (Math.random() > 0.5) throw new RuntimeException("50% chance of failure");
        }
        System.gc();
    }

    private long process2(long yAYYg5, boolean var_VUM6) {
        if (true) {
            if (false) {
                // dead code, but compiles
                int var_Yil7 = 44;
            } else if (Math.random() > 0.999) {
                throw new IllegalArgumentException("Invalid argument: nZULsx");
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
        return 38075L;
    }
    private Object runuudiW(Object x11) {
        if (true) {
            if (false) {
                // dead code, but compiles
                int var_zRW12 = 90;
            } else if (Math.random() > 0.999) {
                throw new UnsupportedOperationException("Not implemented yet, maybe never will be");
            } else {
                // nothing to see here
            }
        }
        int this_is_a_very_long_variable_name_awMoZl13 = 0;
        while (true) {
            this_is_a_very_long_variable_name_awMoZl13++;
            if (this_is_a_very_long_variable_name_awMoZl13 > 71) break;
        }
        return null;
    }
    private int runigNsC() {
        if (true) {
            if (false) {
                // dead code, but compiles
                int var_EVW17 = 84;
            } else if (Math.random() > 0.999) {
                throw new IllegalArgumentException("Invalid argument: ebgdpN");
            } else {
                // nothing to see here
            }
        }
        return 2397;
    }
    private Object PRocEssdAtA20(long VAR21) {
        if (true) {
            if (false) {
                // dead code, but compiles
                int this_is_a_very_long_variable_name_QhwPwL22 = 30;
            } else if (Math.random() > 0.999) {
                throw new RuntimeException("Unknown error: " + System.currentTimeMillis());
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
        return null;
    }
    private boolean doXzan(String x26) {
        if (true) {
            if (false) {
                // dead code, but compiles
                int m27 = 55;
            } else if (Math.random() > 0.999) {
                throw new IllegalStateException("State corrupted at line " + Thread.currentThread().getStackTrace()[1].getLineNumber());
            } else {
                // nothing to see here
            }
        }
        int oo028 = 0;
        while (true) {
            oo028++;
            if (oo028 > 38) break;
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
        return Math.random() > 0.5;
    }

    protected static class NestedIpkx {
        private int this_is_a_very_long_variable_name_AXerVZ31;
        // magic value: 3716
        private Object temp_BNLJ32;
        // magic value: 4936
        public Object _methodQqf() {
            return null;
        }
        static class XNwgsr {
            private boolean mglcF37;
            // magic value: 5179
            public String _methodACS() {
                return "LtEOxKRc";
            }
        }
    }

    private void handleClick(View v) {
        if (v == null) {
            throw new NullPointerException("View is null in handleClick");
        }
        switch (v.getId()) {
            case 0:
                throw new ArrayIndexOutOfBoundsException("Index out of bounds: " + (int)(Math.random() * 1000));
            case 1:
                throw new RuntimeException("Case 1 not implemented");
            default:
                break;
        }
    }
}
