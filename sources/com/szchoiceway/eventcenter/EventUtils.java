package com.szchoiceway.eventcenter;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.storage.StorageManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;

public class EventUtils {
    public static final byte ADDR_CMD_DVR = 29;
    public static final String CAR_AIR_DATA = "EventUtils.CAR_AIR_DATA";
    public static final byte CMD_ARM_SEND_PASSWORD = 30;
    public static final byte CMD_ARM_SYS_RTC_TIME = 19;
    public static final byte CMD_BACK_RADAR = 34;
    public static final byte CMD_BAL_FAD_VAL = 122;
    public static final byte CMD_BEEP = 6;
    public static final byte CMD_BMT_VAL = 118;
    public static final byte CMD_BREAK_STATE = 28;
    public static final byte CMD_BT_STATE = 11;
    public static final byte CMD_CAMRY_CAN_BAIC_INFO = 36;
    public static final byte CMD_CAMRY_CAN_CURRENT_TRIP = 34;
    public static final byte CMD_CAMRY_CAN_HISTORY_TRIP = 35;
    public static final byte CMD_CAMRY_CAN_MIN_TRIP = 39;
    public static final byte CMD_CAMRY_CAN_TPMS_STATUS = 37;
    public static final byte CMD_CAMRY_CAN_TRIP_INFO = 33;
    public static final byte CMD_CAMRY_CAN_VEHICLE_SETTINGS = 38;
    public static final byte CMD_CANBUS_ADDRESS = 13;
    public static final byte CMD_CANBUS_ALL_ADD = -91;
    public static final byte CMD_CAN_AIR = -96;
    public static final byte CMD_CAR_AIR = 33;
    public static final byte CMD_CAR_INFO = -76;
    public static final byte CMD_CRV_SIYU_COMPASS_STATUS = -46;
    public static final byte CMD_CRV_SIYU_MEDIA_STATUS = 33;
    public static final byte CMD_CRV_SIYU_TIME_STATUS = -47;
    public static final byte CMD_DISC_AUTO_IN = -126;
    public static final byte CMD_DSP_TYTE = 119;
    public static final byte CMD_DVD_FLIE_TYPE = 18;
    public static final byte CMD_DVD_RESET = 22;
    public static final byte CMD_FACTORY_SET = 15;
    public static final byte CMD_FM_EVENT = 115;
    public static final byte CMD_FM_KEY = 2;
    public static final byte CMD_FREQ_SEL = Byte.MIN_VALUE;
    public static final byte CMD_GPS_SOUND_TYPE = 20;
    public static final byte CMD_HEAD_RADAR = 35;
    public static final byte CMD_KEY_AD = 126;
    public static final byte CMD_KEY_EVENT = 114;
    public static final byte CMD_LOUD_NESS = 21;
    public static final byte CMD_LOUND = 123;
    public static final byte CMD_MAIN_VOL = 121;
    public static final byte CMD_MCU_CONTROL_BLACK_SCREEN = -124;
    public static final byte CMD_MCU_INIT = 124;
    public static final byte CMD_MCU_SEND_PASSWORD = -123;
    public static final byte CMD_MCU_UPGRADE = 14;
    public static final byte CMD_MODE = 1;
    public static final byte CMD_MODE_ASK = 112;
    public static final byte CMD_MODE_POWERON = -127;
    public static final byte CMD_MUTE = 120;
    public static final byte CMD_NOTIFY_MCU_GPSMODE = 39;
    public static final byte CMD_PLAY_STATE = 25;
    public static final byte CMD_RADIO_ONOFF = 125;
    public static final byte CMD_SEND_8825_VALUE = 35;
    public static final byte CMD_SEND_AUDIO_VALUE = 34;
    public static final byte CMD_SEND_BLACK_SCREEN = 31;
    public static final byte CMD_SEND_DIM = 36;
    public static final byte CMD_SEND_GPS_VOL = 38;
    public static final byte CMD_SEND_KEY_MUTE = 17;
    public static final byte CMD_SEND_NOTIFY_HAS_RADAR_SIGNAL = 41;
    public static final byte CMD_SEND_RADA = 40;
    public static final byte CMD_SEND_STUDY_KEY_FLAG = -120;
    public static final byte CMD_SEND_TPMS_DATA = 37;
    public static final byte CMD_SET_FM_FREQ = 12;
    public static final byte CMD_SET_MUTE = 10;
    public static final byte CMD_SWITCH_DVD_IPOD_COM = 23;
    public static final byte CMD_SWITCH_MODE_SND = 24;
    public static final byte CMD_SYS_EQ = 9;
    public static final byte CMD_SYS_EVENT = 113;
    public static final byte CMD_SYS_KEY = 8;
    public static final byte CMD_SYS_RTC_TIME = -125;
    public static final byte CMD_SYS_SET = 5;
    public static final byte CMD_TOUCH_BTN_KEY = 27;
    public static final byte CMD_TOUCH_POS = 4;
    public static final byte CMD_TV_EVENT = 117;
    public static final byte CMD_TV_KEY = 3;
    public static final byte CMD_UPGRADE_ACK = Byte.MAX_VALUE;
    public static final byte CMD_VIDEO_FORMAT = 26;
    public static final byte CMD_WHEEL = 7;
    public static final byte CMD_WHEEL_STATE = 116;
    public static final int EVENT_MODE_CHANGE = 4097;
    public static final int EVENT_START = 4096;
    public static final int FM_BAND_NUM = 1;
    public static final int FM_CURR_FREQ = 3;
    public static final int FM_FREQ_LIST = 4;
    public static final int FM_PS_NAME = 6;
    public static final int FM_PTY_TYPE = 5;
    public static final int FM_REFRESH_UI = 255;
    public static final int FM_STATE = 0;
    public static final int FM_TUNER_NUM = 2;
    public static final int HANDLER_QUIT = 256;
    public static final String KEY_BAL_SETTINGS = "COM.SZCHOICEWAY_BAL_SETTINGS";
    public static final String KEY_BASS_FREQ_SETTINGS = "COM.SZCHOICEWAY_BASS_FREQ_SETTINGS";
    public static final String KEY_BASS_SETTINGS = "COM.SZCHOICEWAY_BASS_SETTINGS";
    public static final String KEY_BRIGHTNESS_SETTINGS = "COM.SZCHOICEWAY_BRIGHTNESS_SETTINGS";
    public static final String KEY_BackcarFullview = "Set_BackcarFullview";
    public static final String KEY_BackcarRadar = "Set_BackcarRadar";
    public static final String KEY_BackcarTrack = "Set_BackcarTrack";
    public static final String KEY_CHECKASSISTIVE_TOUCH_SETTINGS = "COM.SZCHOICEWAY_CHECKASSISTIVE_TOUCH_SETTINGS";
    public static final String KEY_CHECKBRAKE_SETTINGS = "COM.SZCHOICEWAY_CHECKBRAKE_SETTINGS";
    public static final String KEY_EQ_MODE_SETTINGS = "COM.SZCHOICEWAY_EQ_MODE_SETTINGS";
    public static final String KEY_FAD_SETTINGS = "COM.SZCHOICEWAY_FAD_SETTINGS";
    public static final String KEY_HIGHZ_SETTINGS = "COM.SZCHOICEWAY_HIGHZ_SETTINGS";
    public static final String KEY_KEYDOWNSND_SETTINGS = "COM.SZCHOICEWAY_KEYDOWNSND_SETTINGS";
    public static final String KEY_LOGO_SETTINGS = "COM.SZCHOICEWAY_LOGO_SETTINGS";
    public static final String KEY_LOUDNESS_SETTINGS = "COM.SZCHOICEWAY_LOUDNESS_SETTINGS";
    public static final String KEY_LiShengDVD = "Set_LiShengDVD";
    public static final String KEY_LiShengType = "Set_LiShengType";
    public static final String KEY_MAINVOL_SETTINGS = "COM.SZCHOICEWAY_MAINVOL_SETTINGS";
    public static final String KEY_MID_FREQ_SETTINGS = "COM.SZCHOICEWAY_MID_FREQ_SETTINGS";
    public static final String KEY_MID_SETTINGS = "COM.SZCHOICEWAY_MID_SETTINGS";
    public static final String KEY_NBRIGHTNESS_SETTINGS = "COM.SZCHOICEWAY_NBRIGHTNESS_SETTINGS";
    public static final String KEY_NaiAoShiDVD = "Set_NaiAoShiDVD";
    public static final String KEY_NaiAoShiType = "Set_NaiAoShiType";
    public static final String KEY_OrgBackcar = "Set_OrgBackcar";
    public static final String KEY_RADIO_ZONE_SETTINGS = "COM.SZCHOICEWAY_RADIO_ZONE_SETTINGS";
    public static final String KEY_STANDBY_SETTINGS = "COM.SZCHOICEWAY_STANDBY_SETTINGS";
    public static final String KEY_SYS_LAST_MODE = "KEY_SYS_LAST_MODE_SAVE";
    public static final String KEY_TRE_FREQ_SETTINGS = "COM.SZCHOICEWAY_TRE_FREQ_SETTINGS";
    public static final String KEY_TRE_SETTINGS = "COM.SZCHOICEWAY_TRE_SETTINGS";
    public static final String MCU_MSG_BAL_FAD = "com.choiceway.eventcenter.EventUtils.MCU_MSG_BAL_FAD";
    public static final String MCU_MSG_CAR_AIR = "com.choiceway.eventcenter.EventUtils.CMD_CAR_AIR";
    public static final String MCU_MSG_CAR_INFO = "com.choiceway.eventcenter.EventUtils.MCU_MSG_CAR_INFO";
    public static final String MCU_MSG_EQ_MODE = "com.choiceway.eventcenter.EventUtils.MCU_MSG_EQ_MODE";
    public static final String MCU_MSG_FINISH_EVT = "com.choiceway.eventcenter.EventUtils.MCU_MSG_FINISH_EVT";
    public static final String MCU_MSG_INIT_EVT = "com.choiceway.eventcenter.EventUtils.MCU_MSG_INIT_EVT";
    public static final String MCU_MSG_LOUD_EVT = "com.choiceway.eventcenter.EventUtils.MCU_MSG_LOUD_EVT";
    public static final String MCU_MSG_MAIL_VOL = "com.choiceway.eventcenter.EventUtils.MCU_MSG_MAIL_VOL";
    public static final String MCU_MSG_MUTE_STATE = "com.choiceway.eventcenter.EventUtils.MCU_MSG_MUTE_STATE";
    public static final String MCU_MSG_WHEEL_INFO = "com.choiceway.eventcenter.EventUtils.MCU_MSG_WHEEL_INFO";
    public static final byte MCU_SEND_8825_VALUE = -122;
    public static final String NAV_SET_GOTO_PAGE = "com.szchoiceway.NaviSettings.GotoPage";
    public static final int RADIO_AF = 21;
    public static final int RADIO_AMS = 18;
    public static final int RADIO_AM_BND1 = 29;
    public static final int RADIO_APS = 13;
    public static final int RADIO_BND = 24;
    public static final int RADIO_CH_DOWN = 26;
    public static final int RADIO_CH_UP = 25;
    public static final int RADIO_FM_BND1 = 27;
    public static final int RADIO_FM_BND2 = 28;
    public static final int RADIO_FREQ_DOWN = 15;
    public static final int RADIO_FREQ_UP = 14;
    public static final int RADIO_LOC_DX = 20;
    public static final int RADIO_L_NUM1 = 7;
    public static final int RADIO_L_NUM2 = 8;
    public static final int RADIO_L_NUM3 = 9;
    public static final int RADIO_L_NUM4 = 10;
    public static final int RADIO_L_NUM5 = 11;
    public static final int RADIO_L_NUM6 = 12;
    public static final String RADIO_MODE_CLASS_NAME = "com.szchoiceway.radio.MainActivity";
    public static final String RADIO_MODE_PACKAGE_NAME = "com.szchoiceway.radio";
    public static final int RADIO_NONE = 0;
    public static final int RADIO_NUM1 = 1;
    public static final int RADIO_NUM2 = 2;
    public static final int RADIO_NUM3 = 3;
    public static final int RADIO_NUM4 = 4;
    public static final int RADIO_NUM5 = 5;
    public static final int RADIO_NUM6 = 6;
    public static final int RADIO_PTY = 22;
    public static final int RADIO_SEEK_DOWN = 17;
    public static final int RADIO_SEEK_UP = 16;
    public static final int RADIO_ST_MONO = 19;
    public static final int RADIO_TA = 23;
    public static final String STEER_WHEEL_INFOR = "com.choiceway.eventcenter.EventUtils.STEER_WHEEL_INFOR";
    public static final String STEER_WHEEL_INFOR_LPARAM = "EventUtils.STEER_WHEEL_INFOR_LPARAM";
    public static final String STEER_WHEEL_INFOR_WPARAM = "EventUtils.STEER_WHEEL_INFOR_WPARAM";
    public static final String STEER_WHEEL_STATUS = "com.choiceway.eventcenter.EventUtils.STEER_WHEEL_STATUS";
    public static final String STEER_WHEEL_STUDY_STATUS = "EventUtils.STEER_WHEEL_STUDY_STATUS";
    public static final int SYS_BAL_ADD = 8;
    public static final int SYS_BAL_SUB = 9;
    public static final int SYS_BASS_ADD = 2;
    public static final int SYS_BASS_FREQ_ADD = 17;
    public static final int SYS_BASS_FREQ_SUB = 18;
    public static final int SYS_BASS_SUB = 3;
    public static final byte SYS_BEEP_ONOFF = 2;
    public static final int SYS_EJECT = 15;
    public static final int SYS_FAD_ADD = 10;
    public static final int SYS_FAD_SUB = 11;
    public static final byte SYS_FM_ZONE = 1;
    public static final int SYS_FORCE_EJECT = 16;
    public static final byte SYS_GPS_SND = 4;
    public static final int SYS_INIT_AUDIO = 14;
    public static final int SYS_LOUD = 13;
    public static final int SYS_MID_ADD = 4;
    public static final int SYS_MID_FREQ_ADD = 19;
    public static final int SYS_MID_FREQ_SUB = 20;
    public static final int SYS_MID_SUB = 5;
    public static final int SYS_MUTE = 12;
    public static final byte SYS_PTY_NUM = 3;
    public static final byte SYS_RDS_ONFF = 0;
    public static final byte SYS_SYS_VOL = 5;
    public static final int SYS_TREB_FREQ_ADD = 21;
    public static final int SYS_TREB_FREQ_SUB = 22;
    public static final int SYS_TRE_ADD = 6;
    public static final int SYS_TRE_SUB = 7;
    public static final int SYS_VOL_ADD = 0;
    public static final int SYS_VOL_SUB = 1;
    private static final String TAG = "EventUtils";
    public static final String UART_DEV_PATH = "/dev/ttyS1";
    public static final int UART_DEV_SPEED = 38400;
    public static final int WM_REFRESH_SND_UI = 4098;
    public static final int WSK_BANK = 4;
    public static final int WSK_CLEAR = 115;
    public static final int WSK_ENTER = 112;
    public static final int WSK_HANDUP = 6;
    public static final int WSK_HIGHZ = 116;
    public static final int WSK_LOWZ = 117;
    public static final int WSK_MODE = 0;
    public static final int WSK_MUTE = 5;
    public static final int WSK_NAVI = 10;
    public static final int WSK_NEXT = 1;
    public static final int WSK_OK = 114;
    public static final int WSK_PLAY_PAUSE = 12;
    public static final int WSK_PREV = 2;
    public static final int WSK_PWOFF = 3;
    public static final int WSK_QUIT = 113;
    public static final int WSK_SEL = 11;
    public static final int WSK_TURNON = 7;
    public static final int WSK_VOLDEC = 9;
    public static final int WSK_VOLINC = 8;
    public static final String ZXW_ACTION_FINISH_ACTIVITY = "com.choiceway.eventcenter.ZXW_ACTION_FINISH_ACTIVITY";
    public static final String ZXW_ACTION_FINISH_ACTIVITY_EXTRA = "com.choiceway.eventcenter.ZXW_ACTION_FINISH_ACTIVITY_EXTRA";
    public static final String ZXW_ACTION_LAUNCHER_REFRESH_APPLIST = "com.choiceway.eventcenter.ZXW_ACTION_FINISH_ACTIVITY_EXTRA";
    public static final String ZXW_ACTION_ORIGINAL_INSTALL_MIC_SWITCH = "ZXW_ACTION_ORIGINAL_INSTALL_MIC_SWITCH";
    public static final String ZXW_ACTION_SYS_MODE_TITLE_CHANGE_EVT = "com.szchoiceway.action.mode_title_change";
    public static final String ZXW_ACTION_SYS_MODE_TITLE_CHANGE_EXTRA_EVT = "com.szchoiceway.action.mode_title_change_extra";
    public static final String ZXW_ACTION_SYS_MODE_TITLE_CHANGE_EXTRA_SCREEN_EVT = "com.szchoiceway.action.mode_title_change_screen_extra";
    public static final String ZXW_ACTION_UPDATE_CONFIGURATION = "ZXW_ACTION_UPDATE_CONFIGURATION";
    public static final String ZXW_ACTION_UPDATE_CONFIGURATION_FAILED = "ZXW_ACTION_UPDATE_CONFIGURATION_FAILED";
    public static final String ZXW_ACTION_UPDATE_CONFIGURATION_FAILED_VERSION = "ZXW_ACTION_UPDATE_CONFIGURATION_FAILED_VERSION";
    public static final String ZXW_ACTION_UPDATE_CONFIGURATION_FILE_ERROR = "ZXW_ACTION_UPDATE_CONFIGURATION_FILE_ERROR";
    public static final String ZXW_ACTION_UPDATE_CONFIGURATION_FILE_NOT_EXIT = "ZXW_ACTION_UPDATE_CONFIGURATION_FILE_NOT_EXIT";
    public static final String ZXW_ACTION_UPDATE_CONFIGURATION_LCD_FILE_ERROR = "ZXW_ACTION_UPDATE_CONFIGURATION_LCD_FILE_ERROR";
    public static final String ZXW_ACTION_UPDATE_CONFIGURATION_SUCCED = "ZXW_ACTION_UPDATE_CONFIGURATION_SUCCED";
    public static final String ZXW_DATABASE_CAN_FILENAME = "com.szchoiceway.eventcenter.can";
    public static final String ZXW_DATABASE_CARTYPE_FILENAME = "com.szchoiceway.eventcenter.carType";
    public static final String ZXW_DATABASE_UI_FILENAME = "com.szchoiceway.eventcenter.ui";
    public static final String ZXW_REFRESH_WALL_LOGO = "com.choiceway.FatUtils.ZXW_REFRESH_WALL_LOGO";
    public static final String ZXW_RELOAD_TOUCH_KEY_CFG = "com.choiceway.FatUtils.ZXW_RELOAD_TOUCH_KEY_CFG";
    public static final String ZXW_SENDBROADCAST8902MOD = "com.szchoiceway.eventcenter.EventUtils.ZXW_SENDBROADCAST8902MOD";
    public static final String ZXW_SENDBROADCAST8902MOD_DATA0 = "com.szchoiceway.eventcenter.EventUtils.ZXW_SENDBROADCAST8902MOD_DATA0";
    public static final String ZXW_SENDBROADCAST8902MOD_DATA1 = "com.szchoiceway.eventcenter.EventUtils.ZXW_SENDBROADCAST8902MOD_DATA1";
    public static final String ZXW_SENDBROADCAST8902MOD_EXTRA = "com.szchoiceway.eventcenter.EventUtils.ZXW_SENDBROADCAST8902MOD_EXTRA";
    public static final int eRZY_AUSTRALIA = 5;
    public static final int eRZY_BRAZIL = 6;
    public static final int eRZY_CHINA = 0;
    public static final int eRZY_JAPAN = 4;
    public static final int eRZY_MAX = 7;
    public static final int eRZY_RUSSIA = 3;
    public static final int eRZY_USA1 = 1;
    public static final int eRZY_USA2 = 2;
    /* access modifiers changed from: private */
    public static final Map<Integer, eSrcMode> mValueList = new HashMap();

    public enum eSrcMode {
        SRC_NONE(0),
        SRC_RADIO(1),
        SRC_DVD(2),
        SRC_USB(3),
        SRC_CARD(4),
        SRC_IPOD(5),
        SRC_BT(6),
        SRC_BTMUSIC(7),
        SRC_CMMB(8),
        SRC_TV(9),
        SRC_MOVIE(10),
        SRC_MUSIC(11),
        SRC_EBOOK(12),
        SRC_IMAGE(13),
        SRC_ANDROID(14),
        SRC_VMCD(15),
        SRC_NETWORK(16),
        SRC_CARMEDIA(17),
        SRC_CAR_BT(18),
        SRC_DVR(19),
        SRC_AUX(40),
        SRC_BACKCAR(41),
        SRC_GPS(42),
        SRC_HOME(43),
        SRC_REHOME(44),
        SRC_COMPASS(45),
        SRC_STANDBY(46),
        SRC_EQ(47),
        SRC_BACKLIGHT_SET(48),
        SRC_SETUP(49),
        SRC_MCU_VERSION(80),
        SRC_NULL(99),
        SRC_POWERON(100),
        SRC_POWEROFF(101),
        SRC_FATSET(200);
        
        private int value;

        private eSrcMode(int i) {
            this.value = i;
            EventUtils.mValueList.put(Integer.valueOf(i), this);
        }

        public byte getValue() {
            return (byte) (this.value & 255);
        }

        public int getIntValue() {
            return this.value;
        }

        public static eSrcMode valueOf(int i) {
            eSrcMode esrcmode = (eSrcMode) EventUtils.mValueList.get(Integer.valueOf(i));
            return esrcmode == null ? SRC_NONE : esrcmode;
        }

        public String toString() {
            return Integer.toString(this.value);
        }
    }

    public static String bytesToHexString(byte[] bArr) {
        StringBuilder sb = new StringBuilder("");
        if (bArr == null || bArr.length <= 0) {
            return null;
        }
        for (byte b : bArr) {
            String hexString = Integer.toHexString(b & 255);
            sb.append("0x");
            if (hexString.length() < 2) {
                sb.append(0);
            }
            sb.append(hexString + " ");
        }
        return sb.toString();
    }

    public static class LongClickThread extends Thread {
        private static final int TOUCH_SLOP = 20;
        private boolean isMoved;
        private boolean isReleased;
        private View.OnClickListener mClickLis = null;
        private int mCounter = 0;
        private int mLastMotionX;
        private int mLastMotionY;
        /* access modifiers changed from: private */
        public View.OnLongClickListener mLongClickLis = null;
        private Runnable mLongPressRunnable = new Runnable() {
            public void run() {
                if (LongClickThread.this.mLongClickLis != null) {
                    LongClickThread.this.mLongClickLis.onLongClick(LongClickThread.this.mView);
                }
            }
        };
        private boolean mRunOnce = false;
        /* access modifiers changed from: private */
        public View mView = null;

        public void setView(View view, View.OnClickListener onClickListener, View.OnLongClickListener onLongClickListener) {
            this.mView = view;
            this.mClickLis = onClickListener;
            this.mLongClickLis = onLongClickListener;
        }

        public void onlyRunOnce() {
            this.mRunOnce = true;
        }

        public void exitThread() {
            this.isReleased = true;
            this.isMoved = true;
        }

        public boolean dispatchTouchEvent(MotionEvent motionEvent) {
            View.OnClickListener onClickListener;
            View view;
            int x = (int) motionEvent.getX();
            int y = (int) motionEvent.getY();
            int action = motionEvent.getAction();
            if (action == 0) {
                this.mLastMotionX = x;
                this.mLastMotionY = y;
                this.isReleased = false;
                this.isMoved = false;
            } else if (action == 1) {
                this.isReleased = true;
                if (this.mCounter == 0 && !this.isMoved && (onClickListener = this.mClickLis) != null && (view = this.mView) != null) {
                    onClickListener.onClick(view);
                }
            } else if (action == 2 && !this.isMoved && (Math.abs(this.mLastMotionX - x) > 20 || Math.abs(this.mLastMotionY - y) > 20)) {
                this.isMoved = true;
            }
            return false;
        }

        public void run() {
            View view;
            View view2;
            if (!this.isMoved && !this.isReleased) {
                try {
                    Thread.sleep(800);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (!this.isMoved && !this.isReleased) {
                    this.mCounter++;
                    if (!(this.mLongClickLis == null || (view2 = this.mView) == null)) {
                        view2.post(this.mLongPressRunnable);
                    }
                    while (!this.mRunOnce && !this.isMoved && !this.isReleased) {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e2) {
                            e2.printStackTrace();
                        }
                        if (!this.isMoved && !this.isReleased) {
                            this.mCounter++;
                            if (!(this.mLongClickLis == null || (view = this.mView) == null)) {
                                view.post(this.mLongPressRunnable);
                            }
                        } else {
                            return;
                        }
                    }
                }
            }
        }
    }

    public static void sendBroadcast(Context context, String str, byte[] bArr) {
        if (context != null && bArr != null) {
            Intent intent = new Intent(str);
            intent.putExtra("EventUtils.CAR_AIR_DATA", bArr);
            context.sendBroadcast(intent);
        }
    }

    public static boolean startActivityIfNotRuning(Context context, Class<?> cls) {
        if (cls.getName().equals(((ActivityManager) context.getSystemService("activity")).getRunningTasks(1).get(0).topActivity.getClassName())) {
            return true;
        }
        Intent intent = new Intent(context, cls);
        intent.addFlags(268435456);
        context.startActivity(intent);
        return false;
    }

    public static void startActivityIfNotRuning(Context context, String str, String str2) {
        if (context != null && str != null && str2 != null) {
            ComponentName componentName = ((ActivityManager) context.getSystemService("activity")).getRunningTasks(1).get(0).topActivity;
            if (!componentName.getClassName().equals(str2) || !componentName.getPackageName().equals(str)) {
                Intent intent = new Intent();
                intent.addCategory("android.intent.category.LAUNCHER");
                intent.setComponent(new ComponentName(str, str2));
                intent.setFlags(270532608);
                context.startActivity(intent);
            }
        }
    }

    public static void runCmdAtRootMode(String str, int i) {
        int lastIndexOf;
        if (Build.VERSION.SDK_INT >= 23) {
            if (str.length() > 91 && (lastIndexOf = str.lastIndexOf(".")) != -1) {
                str = str.substring(0, lastIndexOf - (str.length() - 91)) + ".apk";
                Log.i("TAG", "runCmdAtRootMode: cmds = " + str + "，cmds.length() = " + str.length());
            }
            SystemProperties.set("sys.apk_path", str);
            SystemProperties.set("ctl.start", "install_apk");
            String str2 = str;
            while (str2.equals(str)) {
                str2 = SystemProperties.get("sys.apk_path");
                SystemClock.sleep((long) i);
            }
            return;
        }
        try {
            try {
                Runtime.getRuntime().exec(str).waitFor();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    public static String getPinYin(String str) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.setLength(0);
        char[] charArray = str.toCharArray();
        HanyuPinyinOutputFormat hanyuPinyinOutputFormat = new HanyuPinyinOutputFormat();
        hanyuPinyinOutputFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        hanyuPinyinOutputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < charArray.length; i++) {
            if (charArray[i] > 128) {
                try {
                    stringBuffer.append(PinyinHelper.toHanyuPinyinStringArray(charArray[i], hanyuPinyinOutputFormat)[0]);
                    stringBuffer.append(" ");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                stringBuffer.append(charArray[i]);
            }
        }
        return stringBuffer.toString();
    }

    public static String[] getAllPath(Context context) {
        StorageManager storageManager = (StorageManager) context.getSystemService("storage");
        String[] strArr = null;
        try {
            Method method = StorageManager.class.getMethod("getVolumePaths", new Class[0]);
            method.setAccessible(true);
            try {
                strArr = (String[]) method.invoke(storageManager, new Object[0]);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < strArr.length; i++) {
                Log.d(TAG, "path----> " + strArr[i] + "\n");
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return strArr;
    }
}
