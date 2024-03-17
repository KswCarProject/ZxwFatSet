package com.szchoiceway.fatset;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

public class SysProviderOpt {
    public static final String AUDI_GT6_LEFT_CAR_ICON = "AUDI_GT6_LEFT_CAR_ICON";
    public static final String AUDI_GT6_RIGHT_WIDGET = "AUDI_GT6_RIGHT_WIDGET";
    private static final String CONTENT_NAME = "content://com.szchoiceway.eventcenter.SysVarProvider/SysVar";
    public static final String KESAIWEI_HORN_SELECTION = "KESAIWEI_HORN_SELECTION";
    public static final String KESAIWEI_RECORD_AMPLIFIER = "KESAIWEI_RECORD_AMPLIFIER";
    public static final String KESAIWEI_RECORD_CAR_TYPE = "KESAIWEI_RECORD_CAR_TYPE";
    public static final String KESAIWEI_SYS_FRONT_CAMERA = "KESAIWEI_SYS_FRONT_CAMERA";
    public static final String KESAIWEI_SYS_MODE_SELECTION = "KESAIWEI_SYS_MODE_SELECTION";
    public static final String KSW_360_CAMERA_TYPE_INDEX = "KSW_360_CAMERA_TYPE_INDEX";
    public static final String KSW_AGREEMENT_SELECT_INDEX = "KSW_AGREEMENT_SELECT_INDEX";
    public static final String KSW_AUX_ACTIVATION_FUNCTION_INDEX = "KSW_AUX_ACTIVATION_FUNCTION_INDEX";
    public static final String KSW_BOOT_360_CAMERA_INDEX = "KSW_BOOT_360_CAMERA_INDEX";
    public static final String KSW_CCC_IDRIVE_TYPE = "KSW_CCC_IDRIVE_TYPE";
    public static final String KSW_COLLECT_CAN_DATA_SWITCH_INDEX = "KSW_COLLECT_CAN_DATA_SWITCH_INDEX";
    public static final String KSW_DATA_PRODUCT_INDEX = "KSW_DATA_PRODUCT_INDEX";
    public static final String KSW_EXTERNAL_INTERNAL_MIC_SELECTION = "KSW_EXTERNAL_INTERNAL_MIC_SELECTION";
    public static final String KSW_FACTORY_SET_CLIENT = "KSW_FACTORY_SET_CLIENT";
    public static final String KSW_FACTORY_VER = "KSW_FACTORY_VER";
    public static final String KSW_HANDSET_AUTOMATIC_SET_INDEX = "KSW_HANDSET_AUTOMATIC_SET_INDEX";
    public static final String KSW_HAVE_AUX = "KSW_HAVE_AUX";
    public static final String KSW_HAVE_CARAUTO = "KSW_HAVE_CARAUTO";
    public static final String KSW_HAVE_DVD = "KSW_HAVE_DVD";
    public static final String KSW_HAVE_EQ = "KSW_HAVE_EQ";
    public static final String KSW_HAVE_FRONT_CAMERA = "KSW_HAVE_FRONT_CAMERA";
    public static final String KSW_HAVE_HICAR = "KSW_HAVE_HICAR";
    public static final String KSW_HAVE_TV = "KSW_HAVE_TV";
    public static final String KSW_HAVE_TXZ = "KSW_HAVE_TXZ";
    public static final String KSW_HAVE_WEATHER = "KSW_HAVE_WEATHER";
    public static final String KSW_IS_9211_DEVICE = "KSW_IS_9211_DEVICE";
    public static final String KSW_LANDROVER_COPILOT_UI__STYLE_INDEX = "KSW_LANDROVER_COPILOT_UI__STYLE_INDEX";
    public static final String KSW_LANDROVER_HOST_INDEX = "KSW_LANDROVER_HOST_INDEX";
    public static final String KSW_LANDROVER_KEY_PANEL_LEFT_INDEX = "KSW_LANDROVER_KEY_PANEL_LEFT_INDEX";
    public static final String KSW_LANDROVER_KEY_PANEL_RIGHT_INDEX = "KSW_LANDROVER_KEY_PANEL_RIGHT_INDEX";
    public static final String KSW_LANDRVOER_WHEEL_CONTROL_TYPE = "KSW_LANDRVOER_WHEEL_CONTROL_TYPE";
    public static final String KSW_LEXUS_AIR_CONTROL = "KSW_LEXUS_AIR_CONTROL";
    public static final String KSW_LEXUS_ORIGIN_FM = "KSW_LEXUS_ORIGIN_FM";
    public static final String KSW_MAP_KEY_FUNCTION_INDEX = "KSW_MAP_KEY_FUNCTION_INDEX";
    public static final String KSW_MODE_KEY_FUNCTION_INDEX = "KSW_MODE_KEY_FUNCTION_INDEX";
    public static final String KSW_ORIGINAL_CAR_VIDEO_DISPLAY = "KSW_ORIGINAL_CAR_VIDEO_DISPLAY";
    public static final String KSW_ORIGINAL_INSTALL_MIC_SELECTION = "KSW_ORIGINAL_INSTALL_MIC_SELECTION";
    public static final String KSW_PHONE_KEY_FUNCTION_INDEX = "KSW_PHONE_KEY_FUNCTION_INDEX";
    public static final String KSW_REVERSE_EXIT_TIME_CUSTOMIZE = "KSW_REVERSE_EXIT_TIME_CUSTOMIZE";
    public static final String KSW_REVERSE_EXIT_TIME_INDEX = "KSW_REVERSE_EXIT_TIME_INDEX";
    public static final String KSW_SCREEN_CAST_MS9120 = "KSW_SCREEN_CAST_MS9120";
    public static final String KSW_SEND_TOUCH_DATA_CONTINUED = "KSW_SEND_TOUCH_DATA_CONTINUED";
    public static final String KSW_SHOW_AIR = "KSW_SHOW_AIR";
    public static final String KSW_SPEED_TYPE_SELECTION = "KSW_SPEED_TYPE_SELECTION";
    public static final String KSW_SPLITTING_MACHINE_LVDS_MODE = "KSW_SPLITTING_MACHINE_LVDS_MODE";
    public static final String KSW_TURN_SIGNAL_CONTROL = "KSW_TURN_SIGNAL_CONTROL";
    public static final String KSW_UI_RESOLUTION = "KSW_UI_RESOLUTION";
    public static final String KSW_USB_HOST_MODE = "KSW_USB_HOST_MODE";
    public static final String KSW_VOICE_KEY_FUNCTION_INDEX = "KSW_VOICE_KEY_FUNCTION_INDEX";
    public static final String KSW_WHELLTRACK_INDEX = "KSW_WHELLTRACK_INDEX";
    public static final String MAISILUO_SYS_GOOGLEPLAY = "MAISILUO_SYS_GOOGLEPLAY";
    public static final String RESOLUTION = "RESOLUTION";
    public static final String SET_USER_UI_TYPE = "Set_User_UI_Type";
    public static final String SYS_BENZ_CONTROL_INDEX = "SYS_BENZ_CONTROL_INDEX";
    public static final String SYS_DOOR_DISPLAYSET_VALUE_INDEX_KEY = "SYS_DOOR_DISPLAYSET_VALUE_INDEX_KEY";
    public static final String SYS_DOOR_SET_VALUE_INDEX_KEY = "SYS_DOOR_SET_VALUE_INDEX_KEY";
    public static final String SYS_FACTORY_SET_SHOW_KSW_LOGO = "SYS_FACTORY_SET_SHOW_KSW_LOGO";
    private static final String TAG = "SysProviderOpt";
    private static SysProviderOpt mSysProviderOpt;
    private ContentResolver mCntResolver;
    private Uri mUri = Uri.parse(CONTENT_NAME);

    public SysProviderOpt(Context context) {
        this.mCntResolver = context.getContentResolver();
    }

    public static SysProviderOpt getInstance(Context context) {
        if (mSysProviderOpt == null) {
            synchronized (SysProviderOpt.class) {
                if (mSysProviderOpt == null) {
                    mSysProviderOpt = new SysProviderOpt(context);
                }
            }
        }
        return mSysProviderOpt;
    }

    public Uri insertRecord(String str, String str2) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("keyname", str);
        contentValues.put("keyvalue", str2);
        try {
            return this.mCntResolver.insert(this.mUri, contentValues);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            return null;
        }
    }

    public String getRecordValue(String str) {
        return getRecordValue(str, "");
    }

    public String getRecordValue(String str, String str2) {
        String str3 = "";
        String[] strArr = {str};
        Cursor cursor = null;
        try {
            Cursor query = this.mCntResolver.query(this.mUri, (String[]) null, "keyname=?", strArr, (String) null);
            if (query != null && query.getCount() > 0 && query.moveToNext()) {
                str3 = query.getString(query.getColumnIndex("keyvalue"));
            }
            if (query != null) {
                query.close();
            }
        } catch (Exception e) {
            if (cursor != null) {
                cursor.close();
            }
            Log.e(TAG, e.toString());
        }
        return str3;
    }

    public int getRecordInteger(String str, int i) {
        String[] strArr = {str};
        Cursor cursor = null;
        try {
            Cursor query = this.mCntResolver.query(this.mUri, (String[]) null, "keyname=?", strArr, (String) null);
            if (query != null && query.getCount() > 0 && query.moveToNext()) {
                String string = query.getString(query.getColumnIndex("keyvalue"));
                if (string.length() > 0) {
                    i = Integer.valueOf(string).intValue();
                }
            }
            if (query != null) {
                query.close();
            }
        } catch (Exception e) {
            if (cursor != null) {
                cursor.close();
            }
            Log.e(TAG, e.toString());
        }
        return i;
    }

    public long getRecordLong(String str, long j) {
        String[] strArr = {str};
        Cursor cursor = null;
        try {
            Cursor query = this.mCntResolver.query(this.mUri, (String[]) null, "keyname=?", strArr, (String) null);
            if (query != null && query.getCount() > 0 && query.moveToNext()) {
                String string = query.getString(query.getColumnIndex("keyvalue"));
                if (string.length() > 0) {
                    j = Long.valueOf(string).longValue();
                }
            }
            if (query != null) {
                query.close();
            }
        } catch (Exception e) {
            if (cursor != null) {
                cursor.close();
            }
            Log.e(TAG, e.toString());
        }
        return j;
    }

    public float getRecordFloat(String str, float f) {
        String[] strArr = {str};
        Cursor cursor = null;
        try {
            Cursor query = this.mCntResolver.query(this.mUri, (String[]) null, "keyname=?", strArr, (String) null);
            if (query != null && query.getCount() > 0 && query.moveToNext()) {
                String string = query.getString(query.getColumnIndex("keyvalue"));
                if (string.length() > 0) {
                    f = Float.valueOf(string).floatValue();
                }
            }
            if (query != null) {
                query.close();
            }
        } catch (Exception e) {
            if (cursor != null) {
                cursor.close();
            }
            Log.e(TAG, e.toString());
        }
        return f;
    }

    public boolean getRecordBoolean(String str, boolean z) {
        boolean z2 = true;
        String[] strArr = {str};
        Cursor cursor = null;
        try {
            Cursor query = this.mCntResolver.query(this.mUri, (String[]) null, "keyname=?", strArr, (String) null);
            if (query != null && query.getCount() > 0 && query.moveToNext()) {
                String string = query.getString(query.getColumnIndex("keyvalue"));
                if (string.length() > 0) {
                    if (Integer.valueOf(string).intValue() != 1) {
                        z2 = false;
                    }
                    z = z2;
                }
            }
            if (query != null) {
                query.close();
            }
        } catch (Exception e) {
            if (cursor != null) {
                cursor.close();
            }
            Log.e(TAG, e.toString());
        }
        return z;
    }

    public byte getRecordByte(String str, byte b) {
        String[] strArr = {str};
        Cursor cursor = null;
        try {
            Cursor query = this.mCntResolver.query(this.mUri, (String[]) null, "keyname=?", strArr, (String) null);
            if (query != null && query.getCount() > 0 && query.moveToNext()) {
                String string = query.getString(query.getColumnIndex("keyvalue"));
                if (string.length() > 0) {
                    b = Byte.valueOf(string).byteValue();
                }
            }
            if (query != null) {
                query.close();
            }
        } catch (Exception e) {
            if (cursor != null) {
                cursor.close();
            }
            Log.e(TAG, e.toString());
        }
        return b;
    }

    public double getRecordDouble(String str, double d) {
        String recordValue = getRecordValue(str, "");
        return recordValue.length() > 0 ? Double.valueOf(recordValue).doubleValue() : d;
    }

    public void updateRecord(String str, String str2) {
        updateRecord(str, str2, true);
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x0044 A[SYNTHETIC, Splitter:B:20:0x0044] */
    /* JADX WARNING: Removed duplicated region for block: B:27:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void updateRecord(java.lang.String r11, java.lang.String r12, boolean r13) {
        /*
            r10 = this;
            java.lang.String r6 = "keyname=?"
            r0 = 1
            java.lang.String[] r7 = new java.lang.String[r0]
            r0 = 0
            r7[r0] = r11
            java.lang.String r8 = "keyvalue"
            java.lang.String[] r2 = new java.lang.String[]{r8}
            r9 = 0
            android.content.ContentResolver r0 = r10.mCntResolver     // Catch:{ Exception -> 0x0048 }
            android.net.Uri r1 = r10.mUri     // Catch:{ Exception -> 0x0048 }
            r5 = 0
            r3 = r6
            r4 = r7
            android.database.Cursor r0 = r0.query(r1, r2, r3, r4, r5)     // Catch:{ Exception -> 0x0048 }
            if (r0 == 0) goto L_0x003c
            int r1 = r0.getCount()     // Catch:{ Exception -> 0x0039 }
            if (r1 <= 0) goto L_0x003c
            android.content.ContentValues r11 = new android.content.ContentValues     // Catch:{ Exception -> 0x0039 }
            r11.<init>()     // Catch:{ Exception -> 0x0039 }
            r11.put(r8, r12)     // Catch:{ Exception -> 0x0039 }
            if (r0 == 0) goto L_0x0030
            r0.close()     // Catch:{ Exception -> 0x0039 }
            goto L_0x0031
        L_0x0030:
            r9 = r0
        L_0x0031:
            android.content.ContentResolver r12 = r10.mCntResolver     // Catch:{ Exception -> 0x0048 }
            android.net.Uri r13 = r10.mUri     // Catch:{ Exception -> 0x0048 }
            r12.update(r13, r11, r6, r7)     // Catch:{ Exception -> 0x0048 }
            goto L_0x0042
        L_0x0039:
            r11 = move-exception
            r9 = r0
            goto L_0x0049
        L_0x003c:
            if (r13 == 0) goto L_0x0041
            r10.insertRecord(r11, r12)     // Catch:{ Exception -> 0x0039 }
        L_0x0041:
            r9 = r0
        L_0x0042:
            if (r9 == 0) goto L_0x0057
            r9.close()     // Catch:{ Exception -> 0x0048 }
            goto L_0x0057
        L_0x0048:
            r11 = move-exception
        L_0x0049:
            if (r9 == 0) goto L_0x004e
            r9.close()
        L_0x004e:
            java.lang.String r11 = r11.toString()
            java.lang.String r12 = "SysProviderOpt"
            android.util.Log.e(r12, r11)
        L_0x0057:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.szchoiceway.fatset.SysProviderOpt.updateRecord(java.lang.String, java.lang.String, boolean):void");
    }

    public void setRecordDefaultValue(String str, String str2) {
        if (!checkRecordExist(str)) {
            insertRecord(str, str2);
        }
    }

    public boolean checkRecordExist(String str) {
        boolean z = true;
        boolean z2 = false;
        String[] strArr = {str};
        Cursor cursor = null;
        try {
            cursor = this.mCntResolver.query(this.mUri, new String[]{"keyvalue"}, "keyname=?", strArr, (String) null);
            if (cursor == null || cursor.getCount() <= 0) {
                z = false;
            }
            if (cursor == null) {
                return z;
            }
            try {
                cursor.close();
                return z;
            } catch (Exception e) {
                e = e;
                z2 = z;
            }
        } catch (Exception e2) {
            e = e2;
            if (cursor != null) {
                cursor.close();
            }
            Log.e(TAG, e.toString());
            return z2;
        }
    }
}
