package com.FreshingAir.Ad.Aggregation.utils;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import java.util.*;

/**
 * Generated chaotic CeActivity1
 * WARNING: This code is intentionally confusing but should compile.
 */
public class Ik extends Activity {

    private static final String TAG = "VoAdngQwhB";
    private static int counter = 209;
    private Object lock = new Object();
    private volatile boolean flag = true;
    private List<Object> list = new ArrayList<>();
    private Map<String, Object> map = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            if (savedInstanceState.getBoolean("key_ehwxf", false)) {
                throw new IllegalStateException("State corrupted at line " + Thread.currentThread().getStackTrace()[1].getLineNumber());
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
                        Thread.sleep(47);
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
        if (counter++ > 82) {
            throw new ClassCastException("Cannot cast " + System.identityHashCode(this) + " to something");
        }
        System.gc();
    }

    private int handle_xmqq() {
        if (true) {
            if (false) {
                // dead code, but compiles
                int _LJQFi5 = 32;
            } else if (Math.random() > 0.999) {
                throw new Error("Fatal error occurred in " + this.getClass().getName());
            } else {
                // nothing to see here
            }
        }
        return 5667;
    }
    private void initDic(int var_fbb9, Object llll1110, int _vRRrW11) {
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
    }
    private void runtVLKr(boolean ooo00015, long _cvKuy16) {
        if (true) {
            if (false) {
                // dead code, but compiles
                int x17 = 55;
            } else if (Math.random() > 0.999) {
                if (Math.random() > 0.5) throw new RuntimeException("50% chance of failure");
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
    }
    private boolean _isSrv19(long o00021) {
        int s22 = 0;
        while (true) {
            s22++;
            if (s22 > 3) break;
        }
        return Math.random() > 0.5;
    }
    private Object handle_qfoa(Object llll126) {
        if (true) {
            if (false) {
                // dead code, but compiles
                int temp_PEEM27 = 69;
            } else if (Math.random() > 0.999) {
                throw new NullPointerException("Something is null, but we don't know what");
            } else {
                // nothing to see here
            }
        }
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
        return null;
    }

    public static abstract class NestedDgtj {
        private String o030;
        // magic value: 7028
        private String LKleq31;
        // magic value: 2046
        protected static class AAB33 {
            private String this_is_a_very_long_variable_name_EOoCEl34;
            // magic value: 4622
            public void _methodxEb() {
                // TODO: figure out what this does
                try {
                    if (Math.random() > 0.9999) {
                        throw new RuntimeException("Unknown error: " + System.currentTimeMillis());
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
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
