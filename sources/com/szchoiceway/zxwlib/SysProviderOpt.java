package com.szchoiceway.zxwlib;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

public class SysProviderOpt {
    public static final String ADO_FullscreenMode_KEY = "Ado_FullscreenMode";
    public static final String ADO_LastLoopMode_KEY = "Ado_LastLoopMode";
    public static final String ADO_LastLrcMode_KEY = "Ado_LastLrcMode";
    public static final String ADO_LastPlayFilePath_KEY = "Ado_LastPlayFilePath";
    public static final String ADO_LastPlayFilePos_KEY = "Ado_LastPlayFilePos";
    public static final String ADO_LastPlayFileSize_KEY = "Ado_LastPlayFileSize";
    public static final String ADO_StorageType_KEY = "Ado_StorageType";
    public static final String APPLIST_Fullscreen_KEY = "APPLIST_Fullscreen";
    public static final String AUX_FullScreenMode_Key = "AUX_FullScreenModeKey";
    public static final String BTMUSIC_Fullscreen_KEY = "BTMusic_Fullscreen";
    public static final String BT_AutoAnswer_KEY = "BT_AutoAnswer";
    public static final String BT_FullscreenMode_KEY = "BT_FullscreenMode";
    public static final String CAM_FullScreenMode_Key = "CAM_FullScreenMode_Key";
    private static final String CONTENT_NAME = "content://com.szchoiceway.eventcenter.SysVarProvider/SysVar";
    public static final String DVD_FullScreenMode_Key = "DVD_FullScreenMode_Key";
    public static final String DVR_FullScreenMode_Key = "DVR_FullScreenMode_Key";
    public static final String FREE_FORM_NAVI_APP_CLASS_NAME = "FREE_FORM_NAVI_APP_CLASS_NAME";
    public static final String FREE_FORM_NAVI_APP_PACKAGE_NAME = "FREE_FORM_NAVI_APP_PACKAGE_NAME";
    public static final String FREE_FORM_VIDEO_APP_1_CLASS_NAME = "FREE_FORM_VIDEO_APP_1_CLASS_NAME";
    public static final String FREE_FORM_VIDEO_APP_1_PACKAGE_NAME = "FREE_FORM_VIDEO_APP_1_PACKAGE_NAME";
    public static final String FREE_FORM_VIDEO_APP_2_CLASS_NAME = "FREE_FORM_VIDEO_APP_2_CLASS_NAME";
    public static final String FREE_FORM_VIDEO_APP_2_PACKAGE_NAME = "FREE_FORM_VIDEO_APP_2_PACKAGE_NAME";
    public static final String Factory_Settings_Fullscreen_KEY = "Factory_Settings_Fullscreen";
    public static final String KESAIWEI_BOTTOM_P_SWITCH = "KESAIWEI_RECORD_BT_INDEX";
    public static final String KESAIWEI_RECORD_BT_INDEX = "KESAIWEI_RECORD_BT_INDEX";
    public static final String KESAIWEI_SYS_CAMERA_SELECTION = "KESAIWEI_SYS_CAMERA_SELECTION";
    public static final String KESAIWEI_SYS_DISPLAY_MODE = "KESAIWEI_SYS_DISPLAY_MODE";
    public static final String KESAIWEI_SYS_MODE_SELECTION = "KESAIWEI_SYS_MODE_SELECTION";
    public static final String KESAIWEI_SYS_VIDEO_DRIVING_BAN = "KESAIWEI_SYS_VIDEO_DRIVING_BAN";
    public static final String KEY_RADIO_ZONE_SETTINGS = "COM.SZCHOICEWAY_RADIO_ZONE_SETTINGS";
    public static final String KSW_DATA_CAR_MANUFACTURER_INDEX = "KSW_DATA_CAR_MANUFACTURER_INDEX";
    public static final String KSW_LANDROVER_THEME_BACKGROUND_INDEX = "KSW_LANDROVER_THEME_BACKGROUND_INDEX";
    public static final String KSW_SELECTION_SMALL_CLOCK_INDEX = "KSW_SELECTION_SMALL_CLOCK_INDEX";
    public static final String KSW_UI_RESOLUTION = "KSW_UI_RESOLUTION";
    public static final String NAV_ACTIVITYNAME = "NAV_ACTIVITYNAME";
    public static final String NAV_PACKAGENAME = "NAV_PACKAGENAME";
    public static final String RDO_FullscreenMode_KEY = "Rdo_FullscreenMode";
    public static final String RDO_MyFavorite0_KEY = "Rdo_MyFavorite0";
    public static final String RDO_MyFavorite1_KEY = "Rdo_MyFavorite1";
    public static final String RDO_MyFavorite2_KEY = "Rdo_MyFavorite2";
    public static final String RDO_MyFavorite3_KEY = "Rdo_MyFavorite3";
    public static final String RDO_MyFavorite4_KEY = "Rdo_MyFavorite4";
    public static final String RDO_MyFavorite5_KEY = "Rdo_MyFavorite5";
    public static final String RESOLUTION = "RESOLUTION";
    public static final String SAILOR_T001_CUSTOM_WINDOW_OTHER_APP_CLASS_NAME = "SAILOR_T001_CUSTOM_WINDOW_OTHER_APP_CLASS_NAME";
    public static final String SAILOR_T001_CUSTOM_WINDOW_OTHER_APP_PACKAGE_NAME = "SAILOR_T001_CUSTOM_WINDOW_OTHER_APP_PACKAGE_NAME";
    public static final String SET_AUTO_RUN_GPS_KEY = "Set_AutoRunGPS";
    public static final String SET_BAL_FA_KEY = "Set_BalanaceFA";
    public static final String SET_BAL_LR_KEY = "Set_BalanaceLR";
    public static final String SET_BASS_FREQ_KEY = "Set_Bass_Freq";
    public static final String SET_BASS_KEY = "Set_Bass_Val";
    public static final String SET_BRAKE_DETECT_KEY = "Set_BrakeDetection";
    public static final String SET_CAR_PLATE_NUM = "Set_CarPlateNum";
    public static final String SET_DAY_LIGHT_KEY = "Set_Day_Light";
    public static final String SET_EQMODE_KEY = "Set_Eq_Mode";
    public static final String SET_LOCAL_NAV_KEY = "Set_LocalNavi";
    public static final String SET_LOUDNESS_KEY = "Set_Loudness";
    public static final String SET_MIDDLE_FREQ_KEY = "Set_Middle_Freq";
    public static final String SET_MIDDLE_KEY = "Set_Middle_Val";
    public static final String SET_NIGHT_LIGHT_KEY = "Set_Night_Light";
    public static final String SET_NavClassName_KEY = "Set_NavClassName";
    public static final String SET_NavPackageName_KEY = "Set_NavPackageName";
    public static final String SET_SHOW_CAR_SPEED_KEY = "Set_ShowCarSpeed";
    public static final String SET_SUBWOOFER_KEY = "Set_Subwoofer";
    public static final String SET_TOUCH_BEEP_KEY = "Set_TouchBeep";
    public static final String SET_TREBLE_FREQ_KEY = "Set_Treble_Freq";
    public static final String SET_TREBLE_KEY = "Set_Treble_Val";
    public static final String SET_USER_BASS_KEY = "Set_User_Bass";
    public static final String SET_USER_MIDDLE_KEY = "Set_User_Middle";
    public static final String SET_USER_TREBLE_KEY = "Set_User_Treble";
    public static final String SET_USER_UI_TYPE = "Set_User_UI_Type";
    public static final String SET_USER_UI_TYPE_INDEX = "Set_User_UI_Type_index";
    public static final String SYS_3D_DATA_RE_X = "Sys_3D_Data_Re_X";
    public static final String SYS_3D_DATA_RE_Y = "Sys_3D_Data_Re_Y";
    public static final String SYS_3D_DATA_RE_Z = "Sys_3D_Data_Re_Z";
    public static final String SYS_8825_UI_NUM_KEY = "Sys_8825UINumber";
    public static final String SYS_BT_ENABLE_NAME_KEY = "SYS_BT_ENABLE_NAME_KEY";
    public static final String SYS_BT_MOUDLE_VERSION = "Sys_BT_Moudle_Software_Version";
    public static final String SYS_BT_NAME_KEY = "SYS_BT_NAME_KEY";
    public static final String SYS_BT_TYPE_KEY = "Sys_BTDeviceType";
    public static final String SYS_CAMRY_AIR_SUPPLIER_KEY = "Sys_camry_air_Supplier_id";
    public static final String SYS_CARAUTO_RADIO_RUNNING = "Sys_Carauti_Radio_Running";
    public static final String SYS_CAR_SPEED_UNIT = "Sys_Car_Speed_Unit";
    public static final String SYS_CAR_TYPE_KEY = "Sys_CarType";
    public static final String SYS_COMPASS_VIEW_ENABLE = "SYS_COMPASS_VIEW_ENABLE";
    public static final String SYS_CUR_BREAK_STATE_KEY = "Sys_CurBreakSate";
    public static final String SYS_CUSTOMER_SYSTEMUI_PANEL_KEY = "Sys_customer_systemui_panel_bar";
    public static final String SYS_CUSTOMER_TYPE_KEY = "Sys_CustomerType";
    public static final String SYS_DAY_NIGHT_MODE = "SYS_DAY_NIGHT_MODE";
    public static final String SYS_ENGLISH_VER = "Sys_EnglishVer";
    public static final String SYS_FORD_LAMP_ENABLE = "Sys_FordLampEnable";
    public static final String SYS_FORD_TPMS_ENABLE = "Sys_FordTpmsEnable";
    public static final String SYS_FULLMODE_ENABLE = "Sys_FullModeEnable";
    public static final String SYS_GOOGLE_APP_KEY = "Sys_GoogleAppKey";
    public static final String SYS_GYROSCOPE_SET = "Sys_gyroscope";
    public static final String SYS_GYRO_ENABLE = "SYS_GYRO_ENABLE";
    public static final String SYS_HAS_SET_VOLUME_BY_CALL = "Sys_Has_set_volume_by_call";
    public static final String SYS_LAMP_STATUS = "Sys_LAMP_STAUS_CHECK";
    public static final String SYS_LANDSCAPE_KEY = "Sys_Landscape";
    public static final String SYS_LAST_MODE_KEY = "Sys_Last_Mode";
    public static final String SYS_LOGO_TYPE_KEY = "Sys_LogoType";
    public static final String SYS_MCU_SET_KEY = "Sys_McuSet";
    public static final String SYS_MUTE_MIC = "SYS_MUTE_MIC";
    public static final String SYS_OUTSIDETEMP_ENABLE = "Sys_OutsideTempEnable";
    public static final String SYS_RDS_ONOFF_KEY = "SYS_RDS_OnOff";
    public static final String SYS_RESTORE_BACKGROUD_KEY = "Sys_RestoreBackgroud";
    public static final String SYS_SHOW_CAR_INFOR = "Sys_ShowCarInfor";
    public static final String SYS_TITLE_HEIGHT = "Sys_TitleHeight";
    public static final String SYS_TMP_UNIT_SET = "Sys_Tmp_Unit_set";
    public static final String SYS_TV_ON_OFF_KEY = "Sys_TVOnOff";
    public static final String SYS_UI_CATEGORY_KEY = "Sys_UICategory";
    public static final String SYS_UI_NUMBER_KEY = "Sys_UINumber";
    public static final String SYS_USART_TPMS_ENABLE = "Sys_Usart_Tpms";
    public static final String SYS_VOLUME_BEFORE_BLUETOOTH_SET = "Sys_volume_before_bluetooth_set";
    public static final String SYS_WEATHER_ENABLE_KEY = "Sys_WeatherEnable";
    public static final String SYS_WND_IN_TOP = "Sys_Wnd_In_Top";
    public static final String SYS_YOUTAI_CAR_SET = "Sys_YouTai_Car_Set";
    public static final String Settings_Fullscreen_KEY = "Settings_Fullscreen";
    private static final String TAG = "SysProviderOpt";
    public static final String TV_FullScreenMode_Key = "TV_FullScreenModeKey";
    public static final int UI_KESAIWEI_1280X480 = 41;
    public static final String VDO_FullscreenMode_KEY = "Vdo_FullscreenMode";
    public static final String VDO_LastLoopMode_KEY = "Vdo_LastLoopMode";
    public static final String VDO_LastPlayFilePath_KEY = "Vdo_LastPlayFilePath";
    public static final String VDO_LastPlayFilePos_KEY = "Vdo_LastPlayFilePos";
    public static final String VDO_LastPlayFileSize_KEY = "Vdo_LastPlayFileSize";
    public static final String VDO_LastScreenScaleMode_KEY = "Vdo_LastScreenScaleMode";
    public static final String VDO_StorageType_KEY = "Vdo_StorageType";
    public static final String VIDEO_ACTIVITYNAME = "Video_ActivityName";
    public static final String VIDEO_PACKAGENAME = "Video_PackageName";
    private static SysProviderOpt mSysProviderOpt;
    private ContentResolver mCntResolver;
    private Context mContext;
    private Object mEvtService = null;
    private Uri mUri = Uri.parse(CONTENT_NAME);

    public SysProviderOpt(Context context) {
        this.mContext = context;
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

    private void checkEventService() {
        this.mEvtService = null;
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
        String str2 = "";
        String[] strArr = {str};
        Cursor cursor = null;
        try {
            Cursor query = this.mCntResolver.query(this.mUri, (String[]) null, "keyname=?", strArr, (String) null);
            if (query != null && query.getCount() > 0 && query.moveToNext()) {
                str2 = query.getString(query.getColumnIndex("keyvalue"));
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
        return str2;
    }

    /* JADX WARNING: Removed duplicated region for block: B:23:0x0053 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0054 A[RETURN] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String getRecordValue(java.lang.String r9, java.lang.String r10) {
        /*
            r8 = this;
            java.lang.String r0 = ""
            java.lang.String r4 = "keyname=?"
            r1 = 1
            java.lang.String[] r5 = new java.lang.String[r1]
            r1 = 0
            r5[r1] = r9
            r9 = 0
            android.content.ContentResolver r1 = r8.mCntResolver     // Catch:{ Exception -> 0x003a }
            android.net.Uri r2 = r8.mUri     // Catch:{ Exception -> 0x003a }
            r3 = 0
            r6 = 0
            android.database.Cursor r9 = r1.query(r2, r3, r4, r5, r6)     // Catch:{ Exception -> 0x003a }
            if (r9 == 0) goto L_0x002e
            int r1 = r9.getCount()     // Catch:{ Exception -> 0x003a }
            if (r1 <= 0) goto L_0x002e
            boolean r1 = r9.moveToNext()     // Catch:{ Exception -> 0x003a }
            if (r1 == 0) goto L_0x002e
            java.lang.String r1 = "keyvalue"
            int r1 = r9.getColumnIndex(r1)     // Catch:{ Exception -> 0x003a }
            java.lang.String r1 = r9.getString(r1)     // Catch:{ Exception -> 0x003a }
            goto L_0x002f
        L_0x002e:
            r1 = r0
        L_0x002f:
            if (r9 == 0) goto L_0x004b
            r9.close()     // Catch:{ Exception -> 0x0035 }
            goto L_0x004b
        L_0x0035:
            r2 = move-exception
            r7 = r2
            r2 = r1
            r1 = r7
            goto L_0x003c
        L_0x003a:
            r1 = move-exception
            r2 = r0
        L_0x003c:
            if (r9 == 0) goto L_0x0041
            r9.close()
        L_0x0041:
            java.lang.String r9 = r1.toString()
            java.lang.String r1 = "SysProviderOpt"
            android.util.Log.e(r1, r9)
            r1 = r2
        L_0x004b:
            if (r1 == 0) goto L_0x0054
            boolean r9 = r0.equals(r1)
            if (r9 != 0) goto L_0x0054
            return r1
        L_0x0054:
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.szchoiceway.zxwlib.SysProviderOpt.getRecordValue(java.lang.String, java.lang.String):java.lang.String");
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
        throw new UnsupportedOperationException("Method not decompiled: com.szchoiceway.zxwlib.SysProviderOpt.updateRecord(java.lang.String, java.lang.String, boolean):void");
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
