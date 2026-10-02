package com.FreshingAir.Ad.Aggregation.utils;

import android.app.UiAutomation;
import android.content.DialogInterface;
import android.net.vcn.VcnManager;
import android.os.Build;
import android.view.accessibility.AccessibilityEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.google.android.material.slider.LabelFormatter;

@RequiresApi(api = Build.VERSION_CODES.S)
public class Ad extends Aa implements UiAutomation.OnAccessibilityEventListener, DialogInterface.OnClickListener, LabelFormatter {

    @Override
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {

    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {

    }

    @NonNull
    @Override
    public String getFormattedValue(float v) {
        return "";
    }
}
