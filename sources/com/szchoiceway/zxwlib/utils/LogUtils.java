package com.szchoiceway.zxwlib.utils;

import android.util.Log;

public class LogUtils {
    private static boolean isDebug = true;

    public static void d(String str, String str2) {
        if (isDebug) {
            Log.d(str, str2);
        }
    }

    public static void e(String str, String str2) {
        if (isDebug) {
            Log.e(str, str2);
        }
    }

    public static void i(String str, String str2) {
        if (isDebug) {
            Log.i(str, str2);
        }
    }

    public static void w(String str, String str2) {
        if (isDebug) {
            Log.w(str, str2);
        }
    }

    public static void setDebug(boolean z) {
        isDebug = z;
    }
}
