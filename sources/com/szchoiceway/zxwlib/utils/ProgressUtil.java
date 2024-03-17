package com.szchoiceway.zxwlib.utils;

import android.text.TextUtils;
import java.util.LinkedHashMap;

public class ProgressUtil {
    private static LinkedHashMap<Integer, Long> progressMap = new LinkedHashMap<>();

    public static void saveProgress(String str, long j) {
        if (!TextUtils.isEmpty(str)) {
            progressMap.put(Integer.valueOf(str.hashCode()), Long.valueOf(j));
        }
    }

    public static long getSavedProgress(String str) {
        if (!TextUtils.isEmpty(str) && progressMap.containsKey(Integer.valueOf(str.hashCode()))) {
            return progressMap.get(Integer.valueOf(str.hashCode())).longValue();
        }
        return 0;
    }

    public static void clearAllSavedProgress() {
        progressMap.clear();
    }

    public static void clearSavedProgressByUrl(String str) {
        progressMap.remove(Integer.valueOf(str.hashCode()));
    }
}
