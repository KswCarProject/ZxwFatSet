package com.szchoiceway.zxwlib.utils;

import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.UserHandle;
import com.szchoiceway.zxwlib.GyroScopeWithCompassView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventUtils {
    public static final int ACC_CHANGE_EVENT = 4106;
    public static final String ACTION_ACC_SLEEP_STATUS_EVT = "com.szchoiceway.eventcenter.EventUtils.ACTION_ACC_SLEEP_STATUS_EVT";
    public static final String ACTION_ACC_SLEEP_STATUS_EXTRA = "ACC_Status";
    public static final String ACTION_CHANGE_MODE_EVENT = "com.szchoiceway.eventcenter.EventUtils.ACTION_CHANGE_MODE_EVENT";
    public static final String ACTION_CUR_PLAYER_STATE = "com.szchoiceway.eventcenter.ACTION_CUR_PLAYER_STATE";
    public static final String ACTION_MCU_CMD_EVENT = "com.szchoiceway.eventcenter.EventUtils.ACTION_MCU_CMD_EVENT";
    public static final String ACTION_MODE_DATA = "EventUtils.ACTION_MODE_DATA";
    public static final String ACTION_MODE_SCREEN_SIZE = "com.szchoiceway.eventcenter.EventUtils.ACTION_MODE_SCREEN_SIZE";
    public static final String ACTION_NAVI_START_PLAY_SOUND = "com.mxnavi.broadcast.startplaysound";
    public static final String ACTION_NAVI_STOP_PLAY_SOUND = "com.mxnavi.broadcast.stopplaysound";
    public static final String ACTION_SWITCH_ORIGINACAR = "com.szchoiceway.eventcenter.EventUtils.ACTION_SWITCH_ORIGINACAR";
    public static final String ACTION_VOICE_CTRL = "com.szchoiceway.ACTION_VOICE_CTRL";
    public static final byte ADDR_CMD_DVR = 29;
    public static final String AUTONAVI_STANDARD_BROADCAST_SEND = "AUTONAVI_STANDARD_BROADCAST_SEND";
    public static final String BLUETOOTH_DEVICE_PID = "BLUETOOTH_DEVICE_PID";
    public static final String BTMUSIC_MODE_CLASS_NAME = "com.szchoiceway.btsuite.BTMusicActivity";
    public static final String BT_MODE_CLASS_NAME = "com.szchoiceway.btsuite.BTMainActivity";
    public static final String BT_MODE_PACKAGE_NAME = "com.szchoiceway.btsuite";
    public static final int CAN_KEY_DOWN = 118;
    public static final int CAN_KEY_ENTER = 123;
    public static final int CAN_KEY_LEFT = 119;
    public static final int CAN_KEY_LEFT_REVOLVE = 121;
    public static final int CAN_KEY_RIGHT = 120;
    public static final int CAN_KEY_RIGHT_REVOLVE = 122;
    public static final int CAN_KEY_UP = 117;
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
    public static final byte CMD_SEND_MUTE_TIME = 76;
    public static final byte CMD_SEND_NAV_SND_STATE = 63;
    public static final byte CMD_SEND_NOTIFY_HAS_RADAR_SIGNAL = 41;
    public static final byte CMD_SEND_PHONE_NUM = 60;
    public static final byte CMD_SEND_RADA = 40;
    public static final byte CMD_SEND_STUDY_KEY_FLAG = -120;
    public static final byte CMD_SEND_TALK_TIME = 61;
    public static final byte CMD_SEND_TPMS_DATA = 37;
    public static final byte CMD_SEND_VOICE_STATE = 66;
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
    public static final String CONNECT_BLUETOOTH_DEVICE_ACTION = "CONNECT_BLUETOOTH_DEVICE_ACTION";
    public static final String DISMISS_SPLIT_SCREEN_ACTION = "dismissSplitScreen";
    public static final String DISMISS_SPLIT_SCREEN_EXTRA = "mode";
    public static final String DSP_CLASS_NAME = "com.choiceway.dsp.MainActivity";
    public static final String DSP_PACKAGE_NAME = "com.choiceway.dsp";
    public static final int EVENT_BACKCAR_END = 4100;
    public static final int EVENT_BACKCAR_END_DELAY = 4103;
    public static final int EVENT_BACKCAR_START = 4099;
    public static final int EVENT_BREAK_STATE = 4104;
    public static final int EVENT_BT_EVENT = 4102;
    public static final int EVENT_CAMERA_STATUS = 4107;
    public static final String EVENT_DISCONNECT_BT = "EventUtils.EVENT_DISCONNECT_BT";
    public static final int EVENT_KEY_EVENT = 4098;
    public static final int EVENT_MODE_CHANGE = 4097;
    public static final int EVENT_NAV_SAVE_LAST = 4109;
    public static final int EVENT_NAV_SOUND_EVENT = 4108;
    public static final int EVENT_POWER = 4101;
    public static final String EVENT_SERVICE_NAME = "com.szchoiceway.eventcenter.EventService";
    public static final int EVENT_START = 4096;
    public static final String EXTRA_STATE = "EXTRA_STATE";
    public static final String EXTRA_VOICE_KEY_WORD = "VoiceKeyWord";
    public static final String EXTRA_VOICE_PARAM = "VoiceParam";
    public static final int FM_BAND_NUM = 1;
    public static final int FM_CURR_FREQ = 3;
    public static final int FM_FREQ_LIST = 4;
    public static final int FM_PS_NAME = 6;
    public static final int FM_PTY_TYPE = 5;
    public static final int FM_RADIO_ZONE = 8;
    public static final int FM_RDS_STATE = 7;
    public static final int FM_REFRESH_UI = 255;
    public static final int FM_SERVICE_CONNECT = 254;
    public static final int FM_STATE = 0;
    public static final int FM_TUNER_NUM = 2;
    public static final String GOOGLE_MAP_MODE_CLASS_NAME = "com.google.android.maps.MapsActivity";
    public static final String GOOGLE_MAP_MODE_PACKAGE_NAME = "com.google.android.apps.maps";
    public static final int HANDLER_QUIT = 256;
    public static final String KESAIWEI_DISPLAY_MODE_CHANGED_ACTION = "KESAIWEI_DISPLAY_MODE_CHANGED_ACTION";
    public static final String KESAIWEI_SYS_DISPLAY_MODE = "KESAIWEI_SYS_DISPLAY_MODE";
    public static final byte KEY_CLOCK = 73;
    public static final byte KEY_NUM1_LONG = 64;
    public static final byte KEY_NUM2_LONG = 65;
    public static final byte KEY_NUM3_LONG = 66;
    public static final byte KEY_NUM4_LONG = 67;
    public static final byte KEY_NUM5_LONG = 68;
    public static final byte KEY_NUM6_LONG = 69;
    public static final byte KEY_PROG = 72;
    public static final byte KEY_RADIO_NEXT = 70;
    public static final byte KEY_RADIO_PREV = 71;
    public static final byte KEY_TFT_CLOSE = 75;
    public static final byte KEY_TFT_OPEN = 74;
    public static final String KSW_STOP_BTMUSIC_SWITCH = "com.choiceway.eventcenter.KSW_STOP_BTMUSIC_SWITCH";
    public static final String KSW_STOP_BTMUSIC_SWITCH_PHONELINK = "com.choiceway.eventcenter.KSW_STOP_BTMUSIC_SWITCH_PHONELINK";
    public static final String KSW_ZXW_VIDEO_BAN_PARK = "com.choiceway.eventcenter.EventUtils.KSW_ZXW_VIDEO_BAN_PARK";
    public static final String KSW_ZXW_VIDEO_BAN_PARK_EXTRA_BAN = "com.choiceway.eventcenter.EventUtils.KSW_ZXW_VIDEO_BAN_PARK_EXTRA_BAN";
    public static final String KSW_ZXW_VIDEO_BAN_PARK_EXTRA_PARK = "com.choiceway.eventcenter.EventUtils.KSW_ZXW_VIDEO_BAN_PARK_EXTRA_PARK";
    public static final int LEAVE_STANDBY_EVENT = 4105;
    public static final String LIDIAN_MODE_CLASS_NAME = "com.megaview.avm.ui.activity.PermissionsActivity";
    public static final String LIDIAN_MODE_PACKAGE_NAME = "com.fibocom.multicamera";
    public static final String LIDIAN_MODE_SERVICE_NAME = "com.megaview.avm.service.AvmService";
    public static final String MCU_CMD_DATA = "EventUtils.MCU_CMD_DATA";
    public static final byte MCU_KEY_AMS = 12;
    public static final byte MCU_KEY_ANDRIOD_EXCHANGE = 79;
    public static final byte MCU_KEY_ANDRIOD_EXT = 78;
    public static final byte MCU_KEY_ANDRIOD_HOME = 76;
    public static final byte MCU_KEY_ANDRIOD_MENU = 77;
    public static final byte MCU_KEY_ANGLE = 31;
    public static final byte MCU_KEY_APS = 13;
    public static final byte MCU_KEY_BACKLIGHT_SUB = -10;
    public static final byte MCU_KEY_BEEP = -15;
    public static final byte MCU_KEY_BLACK = 21;
    public static final byte MCU_KEY_BND = 14;
    public static final byte MCU_KEY_BT = 59;
    public static final byte MCU_KEY_CMMB = 62;
    public static final byte MCU_KEY_DISP = 10;
    public static final byte MCU_KEY_DISPLAY = 32;
    public static final byte MCU_KEY_DOWN = 26;
    public static final byte MCU_KEY_DVD = 57;
    public static final byte MCU_KEY_EJECT = 15;
    public static final byte MCU_KEY_ENTER = 28;
    public static final byte MCU_KEY_EQ = 51;
    public static final int MCU_KEY_EXIT = 511;
    public static final byte MCU_KEY_FF = 7;
    public static final byte MCU_KEY_FORCE_EJECT = 56;
    public static final byte MCU_KEY_GOTO = 47;
    public static final byte MCU_KEY_HANGUP = 22;
    public static final String MCU_KEY_INFOR_ACTION = "com.szchoiceway.eventcenter.EventUtils.MCU_KEY_INFOR";
    public static final byte MCU_KEY_LEFT = 24;
    public static final byte MCU_KEY_LOCDX = 48;
    public static final byte MCU_KEY_L_R = 50;
    public static final byte MCU_KEY_MENU = 9;
    public static final byte MCU_KEY_MODE = 16;
    public static final byte MCU_KEY_MOVIE = -13;
    public static final byte MCU_KEY_MP5 = 60;
    public static final byte MCU_KEY_MUSIC = -14;
    public static final byte MCU_KEY_MUTE = 17;
    public static final byte MCU_KEY_NAV = 55;
    public static final byte MCU_KEY_NEXT = 2;
    public static final byte MCU_KEY_NONE = 0;
    public static final byte MCU_KEY_NP = -12;
    public static final byte MCU_KEY_NUM0 = 36;
    public static final byte MCU_KEY_NUM1 = 37;
    public static final byte MCU_KEY_NUM10 = 46;
    public static final byte MCU_KEY_NUM2 = 38;
    public static final byte MCU_KEY_NUM3 = 39;
    public static final byte MCU_KEY_NUM4 = 40;
    public static final byte MCU_KEY_NUM5 = 41;
    public static final byte MCU_KEY_NUM6 = 42;
    public static final byte MCU_KEY_NUM7 = 43;
    public static final byte MCU_KEY_NUM8 = 44;
    public static final byte MCU_KEY_NUM9 = 45;
    public static final byte MCU_KEY_PAUSE = -75;
    public static final byte MCU_KEY_PIP = -16;
    public static final byte MCU_KEY_PLAY = 4;
    public static final byte MCU_KEY_PLAYPAUSE = 6;
    public static final byte MCU_KEY_POWER = 1;
    public static final byte MCU_KEY_POWEROFF = -7;
    public static final byte MCU_KEY_POWERON = -8;
    public static final byte MCU_KEY_PREV = 3;
    public static final byte MCU_KEY_RADIO = 54;
    public static final byte MCU_KEY_RADIO_AM = -47;
    public static final byte MCU_KEY_RADIO_FM = -48;
    public static final byte MCU_KEY_RAIDO_BND = -56;
    public static final byte MCU_KEY_RANDOM = 30;
    public static final byte MCU_KEY_REP = 11;
    public static final byte MCU_KEY_REPEAT = 29;
    public static final byte MCU_KEY_RETURN = 85;
    public static final byte MCU_KEY_RF = 8;
    public static final byte MCU_KEY_RIGHT = 27;
    public static final byte MCU_KEY_SEL = 58;
    public static final byte MCU_KEY_SETUP = 20;
    public static final byte MCU_KEY_SLOW = 53;
    public static final byte MCU_KEY_SOUNDLANG = 34;
    public static final byte MCU_KEY_STANDBY = -9;
    public static final byte MCU_KEY_STMONO = 49;
    public static final byte MCU_KEY_STOP = 5;
    public static final byte MCU_KEY_SUBT = 33;
    public static final byte MCU_KEY_TALK = 23;
    public static final byte MCU_KEY_TITLE = 52;
    public static final byte MCU_KEY_TOUCH = 63;
    public static final byte MCU_KEY_TOUCH_CAL = -11;
    public static final byte MCU_KEY_TV = 61;
    public static final byte MCU_KEY_UP = 25;
    public static final String MCU_KEY_VALUE = "EventUtils.MCU_KEY_VALUE";
    public static final byte MCU_KEY_VOL_ADD = 18;
    public static final byte MCU_KEY_VOL_SUB = 19;
    public static final byte MCU_KEY_WAKE_UP = -57;
    public static final byte MCU_KEY_ZOOM = 35;
    public static final String MCU_MSG_BRAKE_EVT = "com.choiceway.eventcenter.EventUtils.MCU_MSG_BRAKE_EVT";
    public static final String MCU_MSG_CAR_AIR = "com.szchoiceway.eventcenter.EventUtils.CMD_CAR_AIR";
    public static final String MCU_MSG_CAR_INFO = "com.szchoiceway.eventcenter.EventUtils.MCU_MSG_CAR_INFO";
    public static final byte MCU_SEND_8825_VALUE = -122;
    public static final String MOVIE_MODE_CLASS_NAME = "com.szchoiceway.videoplayer.MainActivity";
    public static final String MOVIE_MODE_PACKAGE_NAME = "com.szchoiceway.videoplayer";
    public static final String NAV_MODE_CLASS_NAME = "com.szchoiceway.navigation.MainActivity";
    public static final String NAV_MODE_PACKAGE_NAME = "com.szchoiceway.navigation";
    public static final String NAV_SET_GOTO_PAGE = "com.szchoiceway.NaviSettings.GotoPage";
    public static final String PLAYER_STATE = "PlayState";
    public static final String PLAYER_STATE_CUR_TIMER = "CurrTimer";
    public static final String PLAYER_STATE_TOT_TIMER = "TotTimer";
    public static final int RADIO_AF = 21;
    public static final int RADIO_AM = 31;
    public static final int RADIO_AMS = 18;
    public static final int RADIO_AM_BND1 = 29;
    public static final int RADIO_APS = 13;
    public static final int RADIO_BND = 24;
    public static final int RADIO_CH_DOWN = 26;
    public static final int RADIO_CH_UP = 25;
    public static final int RADIO_FM = 30;
    public static final int RADIO_FM_BND1 = 27;
    public static final int RADIO_FM_BND2 = 28;
    public static final int RADIO_FREQ_DOWN = 15;
    public static final int RADIO_FREQ_UP = 14;
    public static final int RADIO_KEY_ADDR = 100;
    public static final int RADIO_KEY_ADDR_LONG = 101;
    public static final int RADIO_LOC_DX = 20;
    public static final int RADIO_L_NUM1 = 7;
    public static final int RADIO_L_NUM2 = 8;
    public static final int RADIO_L_NUM3 = 9;
    public static final int RADIO_L_NUM4 = 10;
    public static final int RADIO_L_NUM5 = 11;
    public static final int RADIO_L_NUM6 = 12;
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
    public static final String SET_MODE_CLASS_NAME = "com.szchoiceway.settings.MainActivity";
    public static final String SET_MODE_PACKAGE_NAME = "com.szchoiceway.settings";
    public static final byte SYS_PTY_NUM = 3;
    public static final String UART_DEV_PATH = "/dev/ttyS1";
    public static final int UART_DEV_SPEED = 38400;
    public static final String VIDEO_CLASS_NAME = "com.szchoiceway.videoplayer.MainActivity";
    public static final String VIDEO_PACKAGE_NAME = "com.szchoiceway.videoplayer";
    public static final String XYQ_MODE_CLASS_NAME = "com.ivicar.modules.main.view.MainActivity";
    public static final String XYQ_MODE_PACKAGE_NAME = "com.ivicar.avm";
    public static final String XYQ_MODE_SERVICE_NAME = "com.szchoiceway.AvmService";
    public static final String ZXW_ACTION_ENTER_THIRD_MEDIA = "com.szchoiceway.eventcenter.EventUtils.ZXW_ACTION_ENTER_THIRD_MEDIA";
    public static final String ZXW_ACTION_ENTER_THIRD_MEDIA_EXTRA = "com.szchoiceway.eventcenter.EventUtils.ZXW_ACTION_ENTER_THIRD_MEDIA_EXTRA";
    public static final String ZXW_ACTION_HIDE_SYSTEM_TIME_EXTRA = "com.szchoiceway.customerui.view.ClockTextView";
    public static final String ZXW_ACTION_KSW_LANDROVER_TOUCH_KEY = "com.choiceway.eventcenter.EventUtils.ZXW_ACTION_KSW_LANDROVER_TOUCH_KEY";
    public static final String ZXW_ACTION_KSW_LANDROVER_TOUCH_KEY_ACTION = "com.choiceway.eventcenter.EventUtils.ZXW_ACTION_KSW_LANDROVER_TOUCH_KEY_ACTION";
    public static final String ZXW_ACTION_KSW_LANDROVER_TOUCH_KEY_EXTRA = "com.choiceway.eventcenter.EventUtils.ZXW_ACTION_KSW_LANDROVER_TOUCH_KEY_EXTRA";
    public static final String ZXW_ACTION_KSW_LANDROVER_TOUCH_KEY_P = "ZXW_ACTION_KSW_LANDROVER_TOUCH_KEY_P";
    public static final String ZXW_ACTION_KSW_LANDROVER_TOUCH_KEY_P_EXTRA = "ZXW_ACTION_KSW_LANDROVER_TOUCH_KEY_P_EXTRA";
    public static final String ZXW_ACTION_NOTIIFY_MEDIA_PLAY_PATH = "com.szchoiceway.eventcenter.EventUtils.ZXW_ACTION_NOTIIFY_MEDIA_PLAY_PATH";
    public static final String ZXW_ACTION_NOTIIFY_MEDIA_PLAY_PATH_EXTRA = "com.szchoiceway.eventcenter.EventUtils.ZXW_ACTION_NOTIIFY_MEDIA_PLAY_PATH_EXTRA";
    public static final String ZXW_ACTION_NOTIIFY_VIDEO_PLAY_PATH = "com.szchoiceway.eventcenter.EventUtils.ZXW_ACTION_NOTIIFY_VIDEO_PLAY_PATH";
    public static final String ZXW_ACTION_NOTIIFY_VIDEO_PLAY_PATH_EXTRA = "com.szchoiceway.eventcenter.EventUtils.ZXW_ACTION_NOTIIFY_VIDEO_PLAY_PATH_EXTRA";
    public static final String ZXW_ACTION_REFRESH_SMALL_CLOCK_INDEX = "ZXW_ACTION_REFRESH_SMALL_CLOCK_INDEX";
    public static final String ZXW_ACTION_SEND_THIRD_MEDIA_SOURCE = "ZXW_ACTION_SEND_THIRD_MEDIA_SOURCE";
    public static final String ZXW_ACTION_SYS_MODE_TITLE_CHANGE_EVT = "com.szchoiceway.action.mode_title_change";
    public static final String ZXW_ACTION_SYS_MODE_TITLE_CHANGE_EXTRA_EVT = "com.szchoiceway.action.mode_title_change_extra";
    public static final String ZXW_ACTION_SYS_MODE_TITLE_CHANGE_EXTRA_SCREEN_EVT = "com.szchoiceway.action.mode_title_change_screen_extra";
    public static final String ZXW_ACTION_VOLUME_ADD = "ZXW_ACTION_VOLUME_ADD";
    public static final String ZXW_ACTION_VOLUME_REDUCE = "ZXW_ACTION_VOLUME_REDUCE";
    public static final String ZXW_BT_CONNECT_CAN_KEY_EVT = "com.choiceway.eventcenter.EventUtils.ZXW_BT_CONNECT_CAN_KEY_EVT";
    public static final String ZXW_BT_CONNECT_CAN_KEY_EVT_EXTRA = "com.choiceway.eventcenter.EventUtils.ZXW_BT_CONNECT_CAN_KEY_EVT_EXTRA";
    public static final String ZXW_CAN_KEY_EVT = "com.choiceway.eventcenter.EventUtils.ZXW_CAN_KEY_EVT";
    public static final String ZXW_CAN_KEY_EVT_EXTRA = "com.choiceway.eventcenter.EventUtils.ZXW_CAN_KEY_EVT_EXTRA";
    public static final int ZXW_ORIGINAL_MCU_KEY_DOWN = 4;
    public static final int ZXW_ORIGINAL_MCU_KEY_ENTER = 5;
    public static final String ZXW_ORIGINAL_MCU_KEY_FOCUS_MOVE_DATA = "com.choiceway.eventcenter.EventUtils.ZXW_ORIGINAL_MCU_KEY_FOCUS_MOVE_DATA";
    public static final String ZXW_ORIGINAL_MCU_KEY_FOCUS_MOVE_EVT = "com.choiceway.eventcenter.EventUtils.ZXW_ORIGINAL_MCU_KEY_FOCUS_MOVE_EVT";
    public static final String ZXW_ORIGINAL_MCU_KEY_FOCUS_MOVE_EVT2 = "com.choiceway.eventcenter.EventUtils.ZXW_ORIGINAL_MCU_KEY_FOCUS_MOVE_EVT2";
    public static final int ZXW_ORIGINAL_MCU_KEY_LEFT = 1;
    public static final int ZXW_ORIGINAL_MCU_KEY_LEFT_HANDED = 7;
    public static final int ZXW_ORIGINAL_MCU_KEY_RIGHT = 2;
    public static final int ZXW_ORIGINAL_MCU_KEY_RIGHT_HANDED = 8;
    public static final int ZXW_ORIGINAL_MCU_KEY_UP = 3;
    static ActivityManager activityManager = null;
    /* access modifiers changed from: private */
    public static final Map<Integer, eSrcMode> mValueList = new HashMap();

    public static void sendBroadcastCanKeyExtra(Context context, String str, int i) {
        if (context != null) {
            Intent intent = new Intent(str);
            intent.putExtra(ZXW_CAN_KEY_EVT_EXTRA, i);
            context.sendBroadcast(intent);
        }
    }

    public static void sendBroadcastToCarplay(Context context, int i, int i2) {
        Intent intent = new Intent("com.szchoiceway.zxwauto.ACTION_KEYEVENTNOTIFY");
        intent.putExtra("keycode", i);
        intent.putExtra("down", i2);
        context.sendBroadcast(intent);
    }

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
        SRC_PHONELINK(31),
        SRC_CARPLAY(32),
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
        SRC_SETTINGS_NAVIGATION(GyroScopeWithCompassView.CARTYPE_Polo2016_HI),
        SRC_SETTINGS_LANGUAGE(GyroScopeWithCompassView.CARTYPE_DAQIE2013_LO),
        SRC_SETTINGS_DATATIMER(GyroScopeWithCompassView.CARTYPE_DAQIE2013_HI),
        SRC_SETTINGS_VOLUME(GyroScopeWithCompassView.CARTYPE_MALIBU_H_H),
        SRC_SETTINGS_EQ(GyroScopeWithCompassView.CARTYPE_Kaluola_OLD_LO),
        SRC_SETTINGS_SYSTEM(GyroScopeWithCompassView.CARTYPE_Kaluola_OLD_HI),
        SRC_SETTINGS_FACTORY(GyroScopeWithCompassView.CARTYPE_JIANGLING_S330_LO),
        SRC_SETTINGS_SYSTEM_INFO(GyroScopeWithCompassView.CARTYPE_JIANGLING_S330_HI),
        SRC_BT_DIAL_PAGE(GyroScopeWithCompassView.CARTYPE_DAOQI_GOMGYANG_LO),
        SRC_BT_CALL_RECORD_PAGE(GyroScopeWithCompassView.CARTYPE_DAOQI_GONGYANG_HI),
        SRC_BT_PHONE_BOOK_PAGE(GyroScopeWithCompassView.CARTYPE_KOLEOS_LO_LO),
        SRC_BT_SET_PAGE(GyroScopeWithCompassView.CARTYPE_HavalH6_Coolpe),
        SRC_BT_MUSIC_PAGE(GyroScopeWithCompassView.CARTYPE_EDGE_2013_HI);
        
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

    public static void startActivityIfNotRuning(Context context, String str, String str2) {
        if (context != null && str != null && str2 != null) {
            if (activityManager == null) {
                activityManager = (ActivityManager) context.getSystemService("activity");
            }
            List<ActivityManager.RunningTaskInfo> runningTasks = activityManager.getRunningTasks(1);
            if (runningTasks != null && runningTasks.size() > 0) {
                ComponentName componentName = activityManager.getRunningTasks(1).get(0).topActivity;
                if (!componentName.getClassName().equals(str2) || !componentName.getPackageName().equals(str)) {
                    Intent intent = new Intent();
                    intent.addCategory("android.intent.category.LAUNCHER");
                    intent.setComponent(new ComponentName(str, str2));
                    intent.setFlags(270532608);
                    try {
                        context.startActivityAsUser(intent, UserHandle.CURRENT);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void startActivityIfNotRuning(Context context, String str, String str2, int i) {
        if (context != null && str != null && str2 != null) {
            if (activityManager == null) {
                activityManager = (ActivityManager) context.getSystemService("activity");
            }
            List<ActivityManager.RunningTaskInfo> runningTasks = activityManager.getRunningTasks(1);
            if (runningTasks != null && runningTasks.size() > 0) {
                ComponentName componentName = activityManager.getRunningTasks(1).get(0).topActivity;
                if (!componentName.getClassName().equals(str2) || !componentName.getPackageName().equals(str)) {
                    Intent intent = new Intent();
                    intent.addCategory("android.intent.category.LAUNCHER");
                    intent.setComponent(new ComponentName(str, str2));
                    intent.setFlags(270532608);
                    ActivityOptions makeBasic = ActivityOptions.makeBasic();
                    makeBasic.setLaunchDisplayId(i);
                    try {
                        context.startActivityAsUser(intent, makeBasic.toBundle(), UserHandle.CURRENT);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static String getTopPackageName(Context context) {
        List<ActivityManager.RunningTaskInfo> runningTasks = ((ActivityManager) context.getSystemService("activity")).getRunningTasks(1);
        return (runningTasks == null || runningTasks.size() <= 0) ? "" : runningTasks.get(0).topActivity.getPackageName();
    }

    public static String getTopActivity(Context context) {
        List<ActivityManager.RunningTaskInfo> runningTasks;
        if (context == null || (runningTasks = ((ActivityManager) context.getSystemService("activity")).getRunningTasks(1)) == null || runningTasks.size() <= 0) {
            return "";
        }
        return runningTasks.get(0).topActivity.getClassName();
    }

    public static boolean getInstallStatus(Context context, String str) {
        PackageManager packageManager = context.getPackageManager();
        new ArrayList();
        List<PackageInfo> installedPackages = packageManager.getInstalledPackages(0);
        boolean z = false;
        for (int i = 0; i < installedPackages.size(); i++) {
            if (str.equals(installedPackages.get(i).packageName)) {
                z = true;
            }
        }
        return z;
    }
}
