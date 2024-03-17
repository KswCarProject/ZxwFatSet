package com.szchoiceway.zxwlib.bean;

import android.content.Context;
import com.szchoiceway.zxwlib.SysProviderOpt;

public class Customer {
    public static final int CAR_FACTORY_AUDI = 3;
    public static final int CAR_FACTORY_BENZ = 2;
    public static final int CAR_FACTORY_BWM = 1;
    public static final int CAR_FACTORY_LANDROVER = 5;
    public static final int CAR_FACTORY_LEXUS = 4;
    public static final int CAR_FACTORY_NULL = 0;
    public static final int UI_CHWY_1280X480 = 48;
    public static final int UI_IMITATE_ORIGINAL_CAR_STYLE = 102;
    public static final int UI_INDEX_KSW_AUDI = 4;
    public static final int UI_INDEX_KSW_BENZ = 6;
    public static final int UI_INDEX_KSW_BWM_ID7 = 1;
    public static final int UI_INDEX_KSW_BWM_ID8 = 2;
    public static final int UI_INDEX_KSW_BWM_PEMP_ID7 = 3;
    public static final int UI_INDEX_KSW_LANDROVER = 5;
    public static final int UI_INDEX_LEXUS = 7;
    public static final int UI_INDEX_SAILOR = 8;
    public static final int UI_KESAIWEI_1280X480 = 41;
    public static final int UI_NORMAL_1920X720 = 101;
    public static final int UI_NUM_COMMON_GS = 26;
    public static final int UI_NUM_KSW_AUDI = 23;
    public static final int UI_NUM_KSW_AUDI_BENTLEY = 40;
    public static final int UI_NUM_KSW_AUDI_CADILLAC = 29;
    public static final int UI_NUM_KSW_AUDI_FY = 24;
    public static final int UI_NUM_KSW_AUDI_TY = 27;
    public static final int UI_NUM_KSW_BENZ = 18;
    public static final int UI_NUM_KSW_BENZ_FY = 22;
    public static final int UI_NUM_KSW_BENZ_NTG6_0_2021 = 21;
    public static final int UI_NUM_KSW_BENZ_NTG6_0_2021_V1 = 31;
    public static final int UI_NUM_KSW_BENZ_V1 = 30;
    public static final int UI_NUM_KSW_BMW_ID7 = 16;
    public static final int UI_NUM_KSW_BMW_ID8 = 17;
    public static final int UI_NUM_KSW_Common_UI_GS_UG = 34;
    public static final int UI_NUM_KSW_Common_UI_GS_UG_1024 = 35;
    public static final int UI_NUM_KSW_GT6_AUDI = 38;
    public static final int UI_NUM_KSW_LANDROVER = 25;
    public static final int UI_NUM_KSW_LANDROVER_BLUE_WHITE = 32;
    public static final int UI_NUM_KSW_LANDROVER_COPILOT = 39;
    public static final int UI_NUM_KSW_MBUX_1024 = 33;
    public static final int UI_NUM_KSW_PEMP_BMW_ID7 = 19;
    public static final int UI_NUM_KSW_PEMP_BMW_ID8 = 20;
    public static final int UI_NUM_KSW_SAILOR_T001 = 100;
    public static final int UI_NUM_KSW_SAILOR_T002 = 101;
    public static final int UI_NUM_KSW_SAILOR_T003 = 102;
    public static final int UI_NUM_LEXUS_LS_UI = 37;
    public static final int UI_NUM_LEXUS_UI = 36;

    public static int getIModeSet(Context context) {
        return SysProviderOpt.getInstance(context).getRecordInteger("KESAIWEI_SYS_MODE_SELECTION", 16);
    }

    public static int getUIIndex(Context context) {
        int recordInteger = SysProviderOpt.getInstance(context).getRecordInteger("KESAIWEI_SYS_MODE_SELECTION", 16);
        if (recordInteger != 35) {
            if (recordInteger == 34) {
                int carFactoryIndex = getCarFactoryIndex(context);
                if (carFactoryIndex != 2) {
                    if (carFactoryIndex != 3) {
                        if (carFactoryIndex != 4) {
                            if (carFactoryIndex != 5) {
                                return 1;
                            }
                            return 5;
                        }
                    }
                    return 4;
                }
            } else if (recordInteger == 100 || recordInteger == 101 || recordInteger == 102) {
                return 8;
            } else {
                if (recordInteger == 16 || recordInteger == 26) {
                    return 1;
                }
                if (recordInteger == 19) {
                    return 3;
                }
                if (recordInteger == 17 || recordInteger == 20) {
                    return 2;
                }
                if (!(recordInteger == 25 || recordInteger == 32 || recordInteger == 39)) {
                    if (!(recordInteger == 23 || recordInteger == 24 || recordInteger == 27 || recordInteger == 29 || recordInteger == 38 || recordInteger == 40)) {
                        if (!(recordInteger == 18 || recordInteger == 21 || recordInteger == 22 || recordInteger == 30 || recordInteger == 31 || recordInteger == 33)) {
                            if (!(recordInteger == 36 || recordInteger == 37)) {
                                return 0;
                            }
                        }
                    }
                    return 4;
                }
                return 5;
            }
            return 7;
        }
        return 6;
    }

    public static int getCarFactoryIndex(Context context) {
        int recordInteger = SysProviderOpt.getInstance(context).getRecordInteger(SysProviderOpt.KSW_DATA_CAR_MANUFACTURER_INDEX, 0);
        if (recordInteger != 0) {
            return recordInteger;
        }
        if (SysProviderOpt.getInstance(context).getRecordInteger("KESAIWEI_SYS_MODE_SELECTION", 16) == 34) {
            return 0;
        }
        int uIIndex = getUIIndex(context);
        if (uIIndex == 3 || uIIndex == 1 || uIIndex == 2) {
            return 1;
        }
        if (uIIndex == 6) {
            return 2;
        }
        if (uIIndex == 4) {
            return 3;
        }
        if (uIIndex == 7) {
            return 4;
        }
        if (uIIndex == 5) {
            return 5;
        }
        return recordInteger;
    }

    public static boolean isSmallResolution(Context context) {
        String recordValue = SysProviderOpt.getInstance(context).getRecordValue("KSW_UI_RESOLUTION", "");
        return "1024x600".equalsIgnoreCase(recordValue) || "1280x720".equalsIgnoreCase(recordValue);
    }

    public static boolean isCopilot(Context context) {
        return SysProviderOpt.getInstance(context).getRecordInteger("KESAIWEI_SYS_MODE_SELECTION", 16) == 39;
    }
}
