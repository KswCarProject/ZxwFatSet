package com.szchoiceway.zxwlib.utils;

import android.app.Activity;
import android.app.Application;
import android.app.Instrumentation;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;
import com.szchoiceway.zxwlib.GyroScopeWithCompassView;
import java.util.Locale;

public class MultipleUtils {
    public static final int DOWN_2 = 4;
    public static final int DOWN_5 = 5;
    public static final int DOWN_8 = 6;
    public static final int FULLSCREEN_WITH_STATUSBAR_NAVIGATIONBAR = 0;
    private static final String TAG = "MultipleUtils";
    public static final int UP_2 = 1;
    public static final int UP_5 = 2;
    public static final int UP_8 = 3;
    private static float sNoncompatDensity = 0.0f;
    private static float sNoncompatScaledDensity = 0.0f;
    public static float sScaledDensity = 1.0f;
    public static float sTargetDensity = 1.0f;

    public static int getCurActivityDisplayMode(Activity activity) {
        Display defaultDisplay = activity.getWindowManager().getDefaultDisplay();
        int height = defaultDisplay.getHeight();
        int statusBarHeight = getStatusBarHeight(activity);
        int navigationBarHeight = getNavigationBarHeight(activity);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        defaultDisplay.getRealMetrics(displayMetrics);
        int i = (displayMetrics.heightPixels - statusBarHeight) - navigationBarHeight;
        int windowingMode = activity.getResources().getConfiguration().windowConfiguration.getWindowingMode();
        Log.i(TAG, "getCurActivityDisplayMode:curWindowMode    " + windowingMode);
        if (windowingMode == 1 || windowingMode == 2) {
            height = i;
        } else if (windowingMode == 3) {
            height -= statusBarHeight;
        } else if (windowingMode != 4) {
            height = 0;
        }
        Log.i(TAG, "getCurActivityDisplayMode: " + height + "  |  " + i);
        int parseFloat = (int) (Float.parseFloat(String.format(Locale.CHINA, "%.2f", new Object[]{Float.valueOf((((float) height) * 1.0f) / ((float) i))})) * 100.0f);
        Log.i(TAG, "getCurActivityDisplayMode:up----   " + parseFloat);
        if (parseFloat < 49) {
            return 1;
        }
        if (parseFloat <= 50 || parseFloat >= 100) {
            return (parseFloat < 49 || parseFloat > 50) ? 0 : 2;
        }
        return 3;
    }

    public static int getStatusBarHeight(Context context) {
        String screenSize = getScreenSize(context);
        screenSize.hashCode();
        if (screenSize.equals("1080x1920")) {
            return 70;
        }
        if (screenSize.equals("768x1024")) {
            return 38;
        }
        Resources resources = context.getResources();
        if (resources.getConfiguration().orientation == 1) {
            return resources.getDimensionPixelSize(resources.getIdentifier("status_bar_height", "dimen", "android"));
        }
        return resources.getDimensionPixelSize(resources.getIdentifier("status_bar_height_landscape", "dimen", "android"));
    }

    public static String getScreenSize(Context context) {
        Point point = new Point();
        ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getRealSize(point);
        int i = point.x;
        return i + "x" + point.y;
    }

    public static int getNavigationBarHeight(Context context) {
        String screenSize = getScreenSize(context);
        screenSize.hashCode();
        if (screenSize.equals("1080x1920")) {
            return 280;
        }
        if (screenSize.equals("768x1024")) {
            return GyroScopeWithCompassView.CARTYPE_yinglang_LO_NEW;
        }
        Resources resources = context.getResources();
        return resources.getDimensionPixelSize(resources.getIdentifier("navigation_bar_height", "dimen", "android"));
    }

    public static void sendKeyEvent(final int i) {
        new Thread() {
            public void run() {
                try {
                    new Instrumentation().sendKeyDownUpSync(i);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap.Config config;
        int intrinsicWidth = drawable.getIntrinsicWidth();
        int intrinsicHeight = drawable.getIntrinsicHeight();
        if (drawable.getOpacity() != -1) {
            config = Bitmap.Config.ARGB_8888;
        } else {
            config = Bitmap.Config.RGB_565;
        }
        Bitmap createBitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, config);
        Canvas canvas = new Canvas(createBitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return createBitmap;
    }

    public static Bitmap changeAlpha(Bitmap bitmap, int i) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap createBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444);
        int i2 = width * height;
        int[] iArr = new int[i2];
        int[] iArr2 = new int[i2];
        bitmap.getPixels(iArr, 0, width, 0, 0, width, height);
        int i3 = width / i;
        for (int i4 = 0; i4 < height; i4++) {
            for (int i5 = 0; i5 < width; i5++) {
                int i6 = (i4 * width) + i5;
                int i7 = iArr[i6];
                int red = Color.red(i7);
                int green = Color.green(i7);
                int blue = Color.blue(i7);
                int alpha = Color.alpha(i7);
                if (i5 <= i3) {
                    alpha = (int) (((((float) i5) * 1.0f) / ((float) i3)) * ((float) alpha));
                }
                iArr2[i6] = Color.argb(alpha, red, green, blue);
            }
        }
        createBitmap.setPixels(iArr2, 0, width, 0, 0, width, height);
        return createBitmap;
    }

    public static Bitmap changeAlpha(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap createBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444);
        int i = width > height ? (32768 * height) / width : (32768 * width) / height;
        int i2 = width * height;
        int[] iArr = new int[i2];
        int[] iArr2 = new int[i2];
        int i3 = width >> 1;
        int i4 = height >> 1;
        int i5 = (i3 * i3) + (i4 * i4);
        Bitmap bitmap2 = createBitmap;
        int i6 = i5 - ((int) (((float) i5) * 1.5f));
        bitmap.getPixels(iArr, 0, width, 0, 0, width, height);
        for (int i7 = 0; i7 < width; i7++) {
            int i8 = 0;
            while (i8 < height) {
                int i9 = (i7 * height) + i8;
                int i10 = iArr[i9];
                int red = Color.red(i10);
                int green = Color.green(i10);
                int blue = Color.blue(i10);
                int alpha = Color.alpha(i10);
                int i11 = i3 - i7;
                int i12 = i4 - i8;
                if (width > height) {
                    i11 = (i11 * i) >> 15;
                } else {
                    i12 = (i12 * i) >> 15;
                }
                int i13 = i;
                int i14 = (int) (((float) alpha) + ((((float) ((i11 * i11) + (i12 * i12))) / ((float) i6)) * 255.0f));
                if (i14 > 255) {
                    i14 = 255;
                } else if (i14 < 0) {
                    i14 = 0;
                }
                iArr2[i9] = Color.argb(i14, red, green, blue);
                i8++;
                i = i13;
            }
            int i15 = i;
        }
        bitmap2.setPixels(iArr2, 0, width, 0, 0, width, height);
        return bitmap2;
    }

    public static String formatTime(int i) {
        int i2 = i / 60;
        if ((i2 / 60) / 1000 > 0) {
            long j = (long) ((i / 60000) % 60);
            long j2 = (long) ((i / 3600000) % 24);
            return String.format(Locale.CHINA, "%02d:%02d:%02d", new Object[]{Long.valueOf(j2), Long.valueOf(j), Long.valueOf((long) ((i / 1000) % 60))});
        } else if (i2 / 1000 > 0) {
            long j3 = (long) ((i / 60000) % 60);
            return String.format(Locale.CHINA, "%02d:%02d", new Object[]{Long.valueOf(j3), Long.valueOf((long) ((i / 1000) % 60))});
        } else {
            long j4 = (long) ((i / 1000) % 60);
            return String.format(Locale.CHINA, "00:%02d", new Object[]{Long.valueOf(j4)});
        }
    }

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

    public static View getViewByPosition(int i, ListView listView) {
        View view;
        if (listView == null) {
            return null;
        }
        try {
            int firstVisiblePosition = listView.getFirstVisiblePosition();
            int childCount = (listView.getChildCount() + firstVisiblePosition) - 1;
            if (i >= firstVisiblePosition) {
                if (i <= childCount) {
                    view = listView.getChildAt(i - firstVisiblePosition);
                    return view;
                }
            }
            view = listView.getAdapter().getView(i, (View) null, listView);
            return view;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
