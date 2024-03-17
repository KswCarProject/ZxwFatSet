package com.szchoiceway.fatset.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListUtil {

    public interface Function0<R> {
        R invoke();
    }

    public interface Function1<P1, R> {
        R invoke(P1 p1);
    }

    private ListUtil() {
    }

    public static <T> T getOrNull(List<T> list, int i) {
        if (i >= list.size()) {
            return null;
        }
        return list.get(i);
    }

    public static <T> T getOrDefault(List<T> list, int i, T t) {
        T orNull = getOrNull(list, i);
        return orNull != null ? orNull : t;
    }

    public static <T> T getOrElse(List<T> list, int i, Function0<T> function0) {
        T orNull = getOrNull(list, i);
        if (orNull != null) {
            return orNull;
        }
        return function0.invoke();
    }

    public static <T> T find(List<T> list, Function1<T, Boolean> function1) {
        for (T next : list) {
            if (function1.invoke(next).booleanValue()) {
                return next;
            }
        }
        return null;
    }

    public static <T> List<T> take(List<T> list, int i) {
        return list.subList(0, i);
    }

    public static <T> List<T> takeLast(List<T> list, int i) {
        return list.subList(list.size() - i, list.size());
    }

    public static <T> T first(List<T> list) {
        return list.get(0);
    }

    public static <T> T firstOrNull(List<T> list) {
        return getOrNull(list, 0);
    }

    public static <T> T firstOrDefault(List<T> list, T t) {
        return getOrDefault(list, 0, t);
    }

    public static <T> T firstOrElse(List<T> list, Function0<T> function0) {
        return getOrElse(list, 0, function0);
    }

    public static <T> T last(List<T> list) {
        return list.get(list.size() - 1);
    }

    public static <T> T lastOrNull(List<T> list) {
        return getOrNull(list, list.size() - 1);
    }

    public static <T> T lastOrDefault(List<T> list, T t) {
        return getOrDefault(list, list.size() - 1, t);
    }

    public static <T> T lastOrElse(List<T> list, Function0<T> function0) {
        return getOrElse(list, list.size() - 1, function0);
    }

    public static <T> List<T> listOf(T... tArr) {
        ArrayList arrayList = new ArrayList(tArr.length);
        arrayList.addAll(Arrays.asList(tArr));
        return arrayList;
    }
}
