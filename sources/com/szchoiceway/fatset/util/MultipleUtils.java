package com.szchoiceway.fatset.util;

import android.app.Activity;
import android.app.Application;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.util.Log;

public class MultipleUtils {
    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;
    private static float sNoncompatDensity = 0.0f;
    private static float sNoncompatScaledDensity = 0.0f;
    public static float sScaledDensity = 1.0f;
    public static float sTargetDensity = 1.0f;

    public static void setCustomDensity(Activity activity, Application application) {
        DisplayMetrics displayMetrics = application.getResources().getDisplayMetrics();
        sNoncompatDensity = displayMetrics.density;
        sNoncompatScaledDensity = displayMetrics.scaledDensity / application.getResources().getConfiguration().fontScale;
        float f = (((float) displayMetrics.heightPixels) * 1.0f) / 480.0f;
        float f2 = (sNoncompatScaledDensity / sNoncompatDensity) * f;
        int i = (int) (160.0f * f);
        sTargetDensity = f;
        displayMetrics.density = f;
        displayMetrics.scaledDensity = f2;
        displayMetrics.densityDpi = i;
        if (activity != null) {
            DisplayMetrics displayMetrics2 = activity.getResources().getDisplayMetrics();
            displayMetrics2.density = f;
            displayMetrics2.scaledDensity = f2;
            displayMetrics2.densityDpi = i;
        }
        Log.i("TAG", "setCustomDensity: " + f + "  |   " + f2 + " | " + i);
    }

    public static boolean curUIModeNight(Configuration configuration) {
        return (configuration.uiMode & 48) == 32;
    }
}
