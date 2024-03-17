package com.szchoiceway.zxwlib;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOverlay;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.szchoiceway.eventcenter.IEventService;

public class GyroScopeWithCompassView extends RelativeLayout implements View.OnLongClickListener {
    public static final String ACTION_GPS_LOCATION_INFOR = "com.szchoiceway.eventcenter.ACTION_GPS_LOCATION_INFOR";
    public static final int CARTYPE_2013EXPLORER_HI = 120;
    public static final int CARTYPE_2013EXPLORER_LO = 119;
    public static final int CARTYPE_2016EXLORER_HI = 185;
    public static final int CARTYPE_2016EXLORER_LO = 184;
    public static final int CARTYPE_ACCORD_HI = 9;
    public static final int CARTYPE_ACCORD_LO = 10;
    public static final int CARTYPE_ACCORD_MID = 13;
    public static final int CARTYPE_AOHU = 217;
    public static final int CARTYPE_AX7_HI = 139;
    public static final int CARTYPE_AX7_LO = 138;
    public static final int CARTYPE_BENCHIWEITING = 143;
    public static final int CARTYPE_BIAOZHI405 = 142;
    public static final int CARTYPE_BIAOZHI408_HI = 124;
    public static final int CARTYPE_BIAOZHI408_LO = 123;
    public static final int CARTYPE_BUCIK = 15;
    public static final int CARTYPE_BUCIK_HI = 16;
    public static final int CARTYPE_BYD_F6_HI = 236;
    public static final int CARTYPE_BYD_F6_LO = 235;
    public static final int CARTYPE_BYD_S6_HI = 171;
    public static final int CARTYPE_BYD_S6_LO = 170;
    public static final int CARTYPE_BYD_SURUI = 272;
    public static final int CARTYPE_Badao_HI = 64;
    public static final int CARTYPE_Badao_HI_Old = 169;
    public static final int CARTYPE_Badao_LO = 63;
    public static final int CARTYPE_Badao_LO_Old = 168;
    public static final int CARTYPE_Baojun560 = 49;
    public static final int CARTYPE_Beiqi_S50_HI = 163;
    public static final int CARTYPE_Beiqi_S50_LO = 162;
    public static final int CARTYPE_Benchi = 89;
    public static final int CARTYPE_C4L_HI = 29;
    public static final int CARTYPE_C4L_LOW = 28;
    public static final int CARTYPE_CADILLAC_ATSL_HI = 7;
    public static final int CARTYPE_CADILLAC_ATSL_LO = 8;
    public static final int CARTYPE_CHANGAN_NEW = 148;
    public static final int CARTYPE_CQ_YEMA_HI = 226;
    public static final int CARTYPE_CQ_YEMA_LO = 225;
    public static final int CARTYPE_CRIDER_HI = 122;
    public static final int CARTYPE_CRIDER_LO = 121;
    public static final int CARTYPE_CRUZE_2017_HI = 145;
    public static final int CARTYPE_CRUZE_2017_LO = 144;
    public static final int CARTYPE_CRUZE_HI = 18;
    public static final int CARTYPE_CRUZE_LO = 17;
    public static final int CARTYPE_CRUZE_OLD = 20;
    public static final int CARTYPE_CRUZE_OLD_LOW = 25;
    public static final int CARTYPE_CRV2008_HI = 258;
    public static final int CARTYPE_CRV2008_LO = 257;
    public static final int CARTYPE_CRV2012_HI = 200;
    public static final int CARTYPE_CRV2012_LO = 199;
    public static final int CARTYPE_CRV2015_HI = 167;
    public static final int CARTYPE_CRV2015_LO = 166;
    public static final int CARTYPE_CS35_HI = 54;
    public static final int CARTYPE_CS35_LO = 53;
    public static final int CARTYPE_CS75_2017 = 232;
    public static final int CARTYPE_CS75_HI = 27;
    public static final int CARTYPE_CS75_LOW = 26;
    public static final int CARTYPE_CS95_HI = 234;
    public static final int CARTYPE_CS95_LO = 233;
    public static final int CARTYPE_CX70_HI = 84;
    public static final int CARTYPE_CX70_LO = 83;
    public static final int CARTYPE_Camery_HI = 37;
    public static final int CARTYPE_Camery_LOW = 36;
    public static final int CARTYPE_Cherokee_HI = 48;
    public static final int CARTYPE_Cherokee_LO = 47;
    public static final int CARTYPE_Chuangku_HI = 106;
    public static final int CARTYPE_Chuangku_LO = 105;
    public static final int CARTYPE_Coolpe_HI = 31;
    public static final int CARTYPE_Coolpe_LOW = 30;
    public static final int CARTYPE_Cruiser_HI = 88;
    public static final int CARTYPE_Cruiser_HI_HI = 141;
    public static final int CARTYPE_Cruiser_LO = 87;
    public static final int CARTYPE_DAOQI_GOMGYANG_LO = 210;
    public static final int CARTYPE_DAOQI_GONGYANG_HI = 211;
    public static final int CARTYPE_DAQIE2013_HI = 204;
    public static final int CARTYPE_DAQIE2013_LO = 203;
    public static final int CARTYPE_DAQIE_HI = 98;
    public static final int CARTYPE_DAQIE_LO = 97;
    public static final int CARTYPE_DONGFENGA30_HI = 134;
    public static final int CARTYPE_DONGFENGA30_LO = 133;
    public static final int CARTYPE_DONGFENGAX3_HI = 132;
    public static final int CARTYPE_DONGFENGAX3_LO = 131;
    public static final int CARTYPE_DongfengA60_HI = 69;
    public static final int CARTYPE_DongfengA60_LO = 68;
    public static final int CARTYPE_EDGE_2013_HI = 214;
    public static final int CARTYPE_EDGE_2013_LO = 215;
    public static final int CARTYPE_EDGE_HI = 5;
    public static final int CARTYPE_EDGE_LO = 6;
    public static final int CARTYPE_EDGE_LO_LO = 251;
    public static final int CARTYPE_FOCUS_HI = 11;
    public static final int CARTYPE_FOCUS_LO = 12;
    public static final int CARTYPE_FOCUS_MID = 14;
    public static final int CARTYPE_GS4_HI = 82;
    public static final int CARTYPE_GS4_LO = 81;
    public static final int CARTYPE_GS4_MI = 140;
    public static final int CARTYPE_GUANDAO_HI = 187;
    public static final int CARTYPE_GUANDAO_LO = 186;
    public static final int CARTYPE_HAIMA_S5 = 229;
    public static final int CARTYPE_HAIMA_S7_HI = 222;
    public static final int CARTYPE_HAIMA_S7_LO = 221;
    public static final int CARTYPE_HAIQUAN_DIANDONG = 137;
    public static final int CARTYPE_HIGHLANDER_HI = 183;
    public static final int CARTYPE_HIGHLANDER_LO = 182;
    public static final int CARTYPE_HavalH1_HI = 58;
    public static final int CARTYPE_HavalH1_LO = 57;
    public static final int CARTYPE_HavalH2_HI = 60;
    public static final int CARTYPE_HavalH2_LO = 59;
    public static final int CARTYPE_HavalH6_Coolpe = 213;
    public static final int CARTYPE_HavalH6_HI = 62;
    public static final int CARTYPE_HavalH6_LO = 61;
    public static final int CARTYPE_Huangguang_HI = 108;
    public static final int CARTYPE_Huangguang_LO = 107;
    public static final int CARTYPE_Infinint_QX50_2010_hi = 230;
    public static final int CARTYPE_Infinint_QX50_HI = 165;
    public static final int CARTYPE_Infinint_QX50_LO = 164;
    public static final int CARTYPE_JIANGLING_S330_HI = 209;
    public static final int CARTYPE_JIANGLING_S330_LO = 208;
    public static final int CARTYPE_JIANGLING_S330_MID = 252;
    public static final int CARTYPE_JIANGLING_S350 = 188;
    public static final int CARTYPE_K3_HI = 104;
    public static final int CARTYPE_K3_LO = 103;
    public static final int CARTYPE_KELEIJIA_HI = 102;
    public static final int CARTYPE_KELEIJIA_LO = 101;
    public static final int CARTYPE_KOLEOS_HI = 173;
    public static final int CARTYPE_KOLEOS_LO = 172;
    public static final int CARTYPE_KOLEOS_LO_LO = 212;
    public static final int CARTYPE_KUGA_HI = 1;
    public static final int CARTYPE_KUGA_LO = 2;
    public static final int CARTYPE_Kaluola_HI = 114;
    public static final int CARTYPE_Kaluola_LO = 113;
    public static final int CARTYPE_Kaluola_OLD_HI = 207;
    public static final int CARTYPE_Kaluola_OLD_LO = 206;
    public static final int CARTYPE_LANGYI_HI = 246;
    public static final int CARTYPE_LANGYI_LO = 245;
    public static final int CARTYPE_LAOJUNYUE_HI = 270;
    public static final int CARTYPE_LAOJUNYUE_LO = 269;
    public static final int CARTYPE_LINGDONG_HI = 190;
    public static final int CARTYPE_LINGDONG_LO = 189;
    public static final int CARTYPE_LISHI_HI = 224;
    public static final int CARTYPE_LISHI_LO = 223;
    public static final int CARTYPE_Langdong_HI = 155;
    public static final int CARTYPE_Langdong_HI_2015 = 157;
    public static final int CARTYPE_Langdong_HI_2016 = 159;
    public static final int CARTYPE_Langdong_LO = 154;
    public static final int CARTYPE_Langdong_LO_2015 = 156;
    public static final int CARTYPE_Langdong_LO_2016 = 158;
    public static final int CARTYPE_Laojunwei_HI = 67;
    public static final int CARTYPE_Laojunwei_LO = 66;
    public static final int CARTYPE_Leiding = 65;
    public static final int CARTYPE_MACAN_HI = 130;
    public static final int CARTYPE_MACAN_LO = 129;
    public static final int CARTYPE_MAGOTAN = 19;
    public static final int CARTYPE_MAGOTAN_MANUAL_AIR = 247;
    public static final int CARTYPE_MAITENG2017_HI = 196;
    public static final int CARTYPE_MAITENG2017_LO = 195;
    public static final int CARTYPE_MAITENG2017_MANUAL_LO = 275;
    public static final int CARTYPE_MALIBU_H_H = 205;
    public static final int CARTYPE_MAX = 276;
    public static final int CARTYPE_MKC_HI = 181;
    public static final int CARTYPE_MKC_LO = 180;
    public static final int CARTYPE_MONDEO_HI = 3;
    public static final int CARTYPE_MONDEO_LO = 4;
    public static final int CARTYPE_MX6_HI = 86;
    public static final int CARTYPE_MX6_LO = 85;
    public static final int CARTYPE_Mondeo_Low_low = 74;
    public static final int CARTYPE_Mondeo_zhisheng_HI = 79;
    public static final int CARTYPE_Mondeo_zhisheng_LO = 80;
    public static final int CARTYPE_NEWKAIYUE = 231;
    public static final int CARTYPE_NONE = 0;
    public static final int CARTYPE_Nazhijie_U3_HI = 161;
    public static final int CARTYPE_Nazhijie_U3_LO = 160;
    public static final int CARTYPE_OLD_HUANGGUAN_HI = 175;
    public static final int CARTYPE_OLD_HUANGGUAN_HI_RIGHT = 274;
    public static final int CARTYPE_OLD_HUANGGUAN_LO = 174;
    public static final int CARTYPE_OULANDE2008_HI = 265;
    public static final int CARTYPE_OULANDE2008_LO = 264;
    public static final int CARTYPE_OULANDE_NEW_HI = 267;
    public static final int CARTYPE_OULANDE_NEW_LO = 266;
    public static final int CARTYPE_PAJIELUO_HI = 228;
    public static final int CARTYPE_PAJIELUO_LO = 227;
    public static final int CARTYPE_Polo2016_HI = 202;
    public static final int CARTYPE_Polo2016_LO = 201;
    public static final int CARTYPE_QIDA_HI = 242;
    public static final int CARTYPE_QIDA_LO = 241;
    public static final int CARTYPE_Qashqai_HI = 33;
    public static final int CARTYPE_Qashqai_LOW = 32;
    public static final int CARTYPE_RONGWEIRX5_HI = 126;
    public static final int CARTYPE_RONGWEIRX5_LO = 125;
    public static final int CARTYPE_RONGWEIW5_HI = 128;
    public static final int CARTYPE_RONGWEIW5_LO = 127;
    public static final int CARTYPE_RUIZHI_OLD = 273;
    public static final int CARTYPE_SDF_HI = 240;
    public static final int CARTYPE_SDF_LO = 239;
    public static final int CARTYPE_SENBAOX65_HI = 198;
    public static final int CARTYPE_SENBAOX65_LO = 197;
    public static final int CARTYPE_SENLINREN_HI = 100;
    public static final int CARTYPE_SENLINREN_LO = 99;
    public static final int CARTYPE_SIYU_2017_LO = 261;
    public static final int CARTYPE_SLS2007_HI = 136;
    public static final int CARTYPE_SLS2007_LO = 135;
    public static final int CARTYPE_SLS_HI = 118;
    public static final int CARTYPE_SLS_LO = 117;
    public static final int CARTYPE_SR7_HI = 77;
    public static final int CARTYPE_SR7_LO = 76;
    public static final int CARTYPE_SRX_2008_HI = 147;
    public static final int CARTYPE_SRX_2008_LO = 146;
    public static final int CARTYPE_SUTENG_HI = 244;
    public static final int CARTYPE_SUTENG_LO = 243;
    public static final int CARTYPE_Sail3 = 50;
    public static final int CARTYPE_SenyaR7_HI = 112;
    public static final int CARTYPE_SenyaR7_LO = 111;
    public static final int CARTYPE_Siyu2016_HI = 116;
    public static final int CARTYPE_Siyu2016_LO = 115;
    public static final int CARTYPE_Siyu_HI = 56;
    public static final int CARTYPE_Siyu_LO = 55;
    public static final int CARTYPE_Sonata8_LO = 75;
    public static final int CARTYPE_T70 = 248;
    public static final int CARTYPE_TANJIEZHE_HI = 254;
    public static final int CARTYPE_TANJIEZHE_LO = 253;
    public static final int CARTYPE_TANTU_HI = 256;
    public static final int CARTYPE_TANTU_LO = 255;
    public static final int CARTYPE_TUCSON_HI = 192;
    public static final int CARTYPE_TUCSON_LO = 191;
    public static final int CARTYPE_TUGUAN_HI = 194;
    public static final int CARTYPE_TUGUAN_LO = 193;
    public static final int CARTYPE_TULE_HI = 179;
    public static final int CARTYPE_TULE_LO = 178;
    public static final int CARTYPE_Tianlai_HI = 52;
    public static final int CARTYPE_Tianlai_LO = 51;
    public static final int CARTYPE_U6_HI = 110;
    public static final int CARTYPE_U6_LO = 109;
    public static final int CARTYPE_VW_CC_HI = 250;
    public static final int CARTYPE_VW_CC_LO = 249;
    public static final int CARTYPE_VW_TURUI_HI = 263;
    public static final int CARTYPE_VW_TURUI_LO = 262;
    public static final int CARTYPE_WEILANG_HI = 177;
    public static final int CARTYPE_WEILANG_LO = 176;
    public static final int CARTYPE_WEIWANG_MX30_HI = 220;
    public static final int CARTYPE_WEIWANG_MX30_LO = 219;
    public static final int CARTYPE_XIANDAI_IX25 = 216;
    public static final int CARTYPE_XIANDAI_IX35 = 218;
    public static final int CARTYPE_XIANDAI_IX35_LO = 268;
    public static final int CARTYPE_XIMA_HI = 96;
    public static final int CARTYPE_XIMA_LO = 95;
    public static final int CARTYPE_Xtrail_HI = 35;
    public static final int CARTYPE_Xtrail_HI_OLD = 94;
    public static final int CARTYPE_Xtrail_LOW = 34;
    public static final int CARTYPE_Xtrail_LOW_OLD = 93;
    public static final int CARTYPE_Xuanyi_HI = 71;
    public static final int CARTYPE_Xuanyi_HI_NEW = 92;
    public static final int CARTYPE_Xuanyi_LO = 70;
    public static final int CARTYPE_Xuanyi_LO_NEW = 91;
    public static final int CARTYPE_YAGE7 = 271;
    public static final int CARTYPE_YIDONG_HI = 238;
    public static final int CARTYPE_YIDONG_LO = 237;
    public static final int CARTYPE_Yuedong_HI = 73;
    public static final int CARTYPE_Yuedong_LO = 72;
    public static final int CARTYPE_airuize5 = 78;
    public static final int CARTYPE_baojun630_1376 = 38;
    public static final int CARTYPE_beiqishenbaod50_1376 = 42;
    public static final int CARTYPE_biekekaiyue_1376 = 40;
    public static final int CARTYPE_biekelaoyinglang_1376 = 41;
    public static final int CARTYPE_biekelaoyinglang_LO = 149;
    public static final int CARTYPE_kewozi_HI = 153;
    public static final int CARTYPE_kewozi_LO = 152;
    public static final int CARTYPE_ruihu_1376 = 39;
    public static final int CARTYPE_ruizhi = 44;
    public static final int CARTYPE_sonata8_1376 = 43;
    public static final int CARTYPE_sonata8_HI_HI = 90;
    public static final int CARTYPE_yema = 45;
    public static final int CARTYPE_yema_HI = 46;
    public static final int CARTYPE_yinglang_HI_NEW = 151;
    public static final int CARTYPE_yinglang_LO_NEW = 150;
    public static final int CarBaojun730_RZC = 21;
    public static final int CarTYPE_ACCORD_L_Daojun = 22;
    public static final int CarTYPE_MALIBU_H = 23;
    public static final int CarTYPE_MALIBU_L = 24;
    public static final int CarTYPE_MALIBU_XL_HI = 260;
    public static final int CarTYPE_MALIBU_XL_LO = 259;
    private static final int EVT_UPDATE_3D_DATA = 0;
    private static final int EVT_UPDATE_COMPASS = 1;
    /* access modifiers changed from: private */
    public DecelerateInterpolator decelerateInterpolator = new DecelerateInterpolator();
    private boolean isRegistered = false;
    /* access modifiers changed from: private */
    public Animation mAnimation;
    /* access modifiers changed from: private */
    public float mBaseX = 0.0f;
    /* access modifiers changed from: private */
    public float mBaseY = 0.0f;
    private float mBaseZ = 0.0f;
    /* access modifiers changed from: private */
    public ImageView mCompass;
    private Context mContext;
    /* access modifiers changed from: private */
    public float mDirection = 0.0f;
    /* access modifiers changed from: private */
    public boolean mFirstInit = true;
    /* access modifiers changed from: private */
    public boolean mGyroscopeVersion = false;
    private Handler mHandler = new Handler() {
        LinearInterpolator linearInterpolator = new LinearInterpolator();
        private float mPreviousDegree = 360.0f;

        public void handleMessage(Message message) {
            int i = message.what;
            if (i == 0) {
                removeMessages(0);
                GyroScopeWithCompassView.this.get3DData();
                float access$300 = GyroScopeWithCompassView.this.mYdegree;
                float access$400 = GyroScopeWithCompassView.this.mXdegree;
                if (GyroScopeWithCompassView.this.mGyroscopeVersion) {
                    access$300 = GyroScopeWithCompassView.this.mXdegree;
                    access$400 = GyroScopeWithCompassView.this.mYdegree;
                }
                float abs = access$400 - Math.abs(GyroScopeWithCompassView.this.mBaseX);
                float abs2 = access$300 - Math.abs(GyroScopeWithCompassView.this.mBaseY);
                synchronized (this) {
                    GyroScopeWithCompassView.access$808(GyroScopeWithCompassView.this);
                    GyroScopeWithCompassView.access$916(GyroScopeWithCompassView.this, abs2);
                    if (GyroScopeWithCompassView.this.mYValueCount == 5) {
                        GyroScopeWithCompassView.access$940(GyroScopeWithCompassView.this, 5.0f);
                        int unused = GyroScopeWithCompassView.this.mYValueCount = 0;
                        if (Math.abs(GyroScopeWithCompassView.this.mYAvarageValue - GyroScopeWithCompassView.this.mPrevY) > 2.0f) {
                            GyroScopeWithCompassView gyroScopeWithCompassView = GyroScopeWithCompassView.this;
                            RotateAnimation rotateAnimation = r10;
                            RotateAnimation rotateAnimation2 = new RotateAnimation(GyroScopeWithCompassView.this.mPrevY, GyroScopeWithCompassView.this.mYAvarageValue, 1, 0.5f, 1, 0.5f);
                            Animation unused2 = gyroScopeWithCompassView.mAnimation = rotateAnimation;
                            GyroScopeWithCompassView.this.mAnimation.setDuration(1000);
                            GyroScopeWithCompassView.this.mAnimation.setInterpolator(GyroScopeWithCompassView.this.decelerateInterpolator);
                            GyroScopeWithCompassView.this.mAnimation.setFillAfter(true);
                            rotateCarByY(GyroScopeWithCompassView.this.mAnimation);
                            GyroScopeWithCompassView gyroScopeWithCompassView2 = GyroScopeWithCompassView.this;
                            float unused3 = gyroScopeWithCompassView2.mPrevY = gyroScopeWithCompassView2.mYAvarageValue;
                        }
                    }
                    GyroScopeWithCompassView.access$1308(GyroScopeWithCompassView.this);
                    GyroScopeWithCompassView.access$1416(GyroScopeWithCompassView.this, abs);
                    if (GyroScopeWithCompassView.this.mXValueCount == 5) {
                        GyroScopeWithCompassView.access$1440(GyroScopeWithCompassView.this, 5.0f);
                        int unused4 = GyroScopeWithCompassView.this.mXValueCount = 0;
                        if (Math.abs(GyroScopeWithCompassView.this.mXAvarageValue - GyroScopeWithCompassView.this.mPrevX) > 2.0f) {
                            Animation unused5 = GyroScopeWithCompassView.this.mAnimation = new RotateAnimation(GyroScopeWithCompassView.this.mGyroscopeVersion ? GyroScopeWithCompassView.this.mPrevX : -GyroScopeWithCompassView.this.mPrevX, GyroScopeWithCompassView.this.mGyroscopeVersion ? GyroScopeWithCompassView.this.mXAvarageValue : -GyroScopeWithCompassView.this.mXAvarageValue, 1, 0.5f, 1, 0.5f);
                            GyroScopeWithCompassView.this.mAnimation.setDuration(1000);
                            GyroScopeWithCompassView.this.mAnimation.setInterpolator(GyroScopeWithCompassView.this.decelerateInterpolator);
                            GyroScopeWithCompassView.this.mAnimation.setFillAfter(true);
                            rotateCarByX(GyroScopeWithCompassView.this.mAnimation);
                            GyroScopeWithCompassView gyroScopeWithCompassView3 = GyroScopeWithCompassView.this;
                            float unused6 = gyroScopeWithCompassView3.mPrevX = gyroScopeWithCompassView3.mXAvarageValue;
                        }
                    }
                }
            } else if (i == 1) {
                if (GyroScopeWithCompassView.this.mSpeed > 3.0f || GyroScopeWithCompassView.this.mFirstInit) {
                    try {
                        rotateCompass(360.0f - GyroScopeWithCompassView.this.mDirection);
                        boolean unused7 = GyroScopeWithCompassView.this.mFirstInit = false;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                sendEmptyMessageDelayed(1, 100);
                sendEmptyMessageDelayed(0, 150);
            }
        }

        private void rotateCarByX(Animation animation) {
            if (GyroScopeWithCompassView.this.mYAxis != null) {
                GyroScopeWithCompassView.this.mYAxis.startAnimation(animation);
            }
        }

        private void rotateCarByY(Animation animation) {
            if (GyroScopeWithCompassView.this.mXAxis != null) {
                GyroScopeWithCompassView.this.mXAxis.startAnimation(animation);
            }
        }

        private void rotateCompass(float f) {
            RotateAnimation rotateAnimation = new RotateAnimation(this.mPreviousDegree, f, 1, 0.5f, 1, 0.5f);
            rotateAnimation.setDuration(1000);
            rotateAnimation.setInterpolator(this.linearInterpolator);
            rotateAnimation.setFillAfter(true);
            if (GyroScopeWithCompassView.this.mCompass != null) {
                GyroScopeWithCompassView.this.mCompass.startAnimation(rotateAnimation);
            }
            this.mPreviousDegree = f;
        }
    };
    private int mLogoType = 0;
    /* access modifiers changed from: private */
    public float mPrevX = 0.0f;
    /* access modifiers changed from: private */
    public float mPrevY = 0.0f;
    private SysProviderOpt mProvider;
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(GyroScopeWithCompassView.ACTION_GPS_LOCATION_INFOR)) {
                double doubleExtra = intent.getDoubleExtra("GPS_DEGREE", 0.0d);
                intent.getDoubleExtra("GPS_LATITUDE", 0.0d);
                intent.getDoubleExtra("GPS_LONGTITUDE", 0.0d);
                intent.getDoubleExtra("GPS_ALTITUDE", 0.0d);
                float unused = GyroScopeWithCompassView.this.mDirection = (float) doubleExtra;
                float unused2 = GyroScopeWithCompassView.this.mSpeed = (float) ((double) intent.getFloatExtra("GPS_SPEED", 0.0f));
            }
        }
    };
    private IEventService mService;
    /* access modifiers changed from: private */
    public float mSpeed = 0.0f;
    /* access modifiers changed from: private */
    public float mXAvarageValue = 0.0f;
    /* access modifiers changed from: private */
    public ImageView mXAxis;
    /* access modifiers changed from: private */
    public int mXValueCount = 0;
    /* access modifiers changed from: private */
    public float mXdegree = 0.0f;
    /* access modifiers changed from: private */
    public float mYAvarageValue = 0.0f;
    /* access modifiers changed from: private */
    public ImageView mYAxis;
    /* access modifiers changed from: private */
    public int mYValueCount = 0;
    /* access modifiers changed from: private */
    public float mYdegree = 0.0f;
    private float mZdegree = 0.0f;

    static /* synthetic */ int access$1308(GyroScopeWithCompassView gyroScopeWithCompassView) {
        int i = gyroScopeWithCompassView.mXValueCount;
        gyroScopeWithCompassView.mXValueCount = i + 1;
        return i;
    }

    static /* synthetic */ float access$1416(GyroScopeWithCompassView gyroScopeWithCompassView, float f) {
        float f2 = gyroScopeWithCompassView.mXAvarageValue + f;
        gyroScopeWithCompassView.mXAvarageValue = f2;
        return f2;
    }

    static /* synthetic */ float access$1440(GyroScopeWithCompassView gyroScopeWithCompassView, float f) {
        float f2 = gyroScopeWithCompassView.mXAvarageValue / f;
        gyroScopeWithCompassView.mXAvarageValue = f2;
        return f2;
    }

    static /* synthetic */ int access$808(GyroScopeWithCompassView gyroScopeWithCompassView) {
        int i = gyroScopeWithCompassView.mYValueCount;
        gyroScopeWithCompassView.mYValueCount = i + 1;
        return i;
    }

    static /* synthetic */ float access$916(GyroScopeWithCompassView gyroScopeWithCompassView, float f) {
        float f2 = gyroScopeWithCompassView.mYAvarageValue + f;
        gyroScopeWithCompassView.mYAvarageValue = f2;
        return f2;
    }

    static /* synthetic */ float access$940(GyroScopeWithCompassView gyroScopeWithCompassView, float f) {
        float f2 = gyroScopeWithCompassView.mYAvarageValue / f;
        gyroScopeWithCompassView.mYAvarageValue = f2;
        return f2;
    }

    public /* bridge */ /* synthetic */ ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return super.generateLayoutParams(attributeSet);
    }

    public /* bridge */ /* synthetic */ ViewOverlay getOverlay() {
        return super.getOverlay();
    }

    public GyroScopeWithCompassView(Context context) {
        super(context);
        this.mContext = context;
    }

    public GyroScopeWithCompassView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mContext = context;
    }

    public GyroScopeWithCompassView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mContext = context;
    }

    public GyroScopeWithCompassView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    private void init() {
        this.mXAxis = (ImageView) findViewById(R.id.carXAxis);
        this.mYAxis = (ImageView) findViewById(R.id.carYAxis);
        this.mCompass = (ImageView) findViewById(R.id.compass);
        SysProviderOpt sysProviderOpt = new SysProviderOpt(this.mContext);
        this.mProvider = sysProviderOpt;
        this.mGyroscopeVersion = sysProviderOpt.getRecordBoolean(SysProviderOpt.SYS_GYROSCOPE_SET, this.mGyroscopeVersion);
        this.mBaseX = this.mProvider.getRecordFloat(SysProviderOpt.SYS_3D_DATA_RE_X, 0.0f);
        this.mBaseY = this.mProvider.getRecordFloat(SysProviderOpt.SYS_3D_DATA_RE_Y, 0.0f);
        this.mBaseZ = this.mProvider.getRecordFloat(SysProviderOpt.SYS_3D_DATA_RE_Z, 0.0f);
        this.mLogoType = this.mProvider.getRecordInteger(SysProviderOpt.SYS_LOGO_TYPE_KEY, 0);
        ImageView imageView = this.mXAxis;
        if (imageView != null) {
            imageView.setOnLongClickListener(this);
            int i = this.mLogoType;
            if (i == 179 || i == 178) {
                this.mXAxis.setBackgroundResource(R.drawable.ck_tule1);
            } else if (i == 98 || i == 97 || i == 204 || i == 203) {
                this.mXAxis.setBackgroundResource(R.drawable.ck_daqie1);
            }
        }
        ImageView imageView2 = this.mYAxis;
        if (imageView2 != null) {
            imageView2.setOnLongClickListener(this);
            int i2 = this.mLogoType;
            if (i2 == 179 || i2 == 178) {
                this.mYAxis.setBackgroundResource(R.drawable.ck_tule2);
            } else if (i2 == 98 || i2 == 97 || i2 == 204 || i2 == 203) {
                this.mYAxis.setBackgroundResource(R.drawable.ck_daqie2);
            }
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_GPS_LOCATION_INFOR);
        this.mContext.registerReceiver(this.mReceiver, intentFilter);
        this.isRegistered = true;
    }

    public void setEventService(IEventService iEventService) {
        this.mService = iEventService;
    }

    public void startMonitor() {
        init();
        this.mHandler.sendEmptyMessage(0);
        this.mHandler.sendEmptyMessage(1);
    }

    public void stopMonitor() {
        this.mHandler.removeCallbacksAndMessages((Object) null);
        if (this.isRegistered) {
            this.isRegistered = false;
            this.mContext.unregisterReceiver(this.mReceiver);
        }
        this.mFirstInit = true;
    }

    /* access modifiers changed from: private */
    public void get3DData() {
        if (this.mService == null) {
        }
    }

    private void reset3DData() {
        if (this.mGyroscopeVersion) {
            this.mBaseX = this.mYdegree;
            this.mBaseY = this.mXdegree;
        } else {
            this.mBaseX = this.mXdegree;
            this.mBaseY = this.mYdegree;
        }
        this.mBaseZ = this.mZdegree;
        SysProviderOpt sysProviderOpt = this.mProvider;
        if (sysProviderOpt != null) {
            sysProviderOpt.updateRecord(SysProviderOpt.SYS_3D_DATA_RE_X, String.valueOf(this.mBaseX));
            this.mProvider.updateRecord(SysProviderOpt.SYS_3D_DATA_RE_Y, String.valueOf(this.mBaseY));
            this.mProvider.updateRecord(SysProviderOpt.SYS_3D_DATA_RE_Z, String.valueOf(this.mBaseZ));
        }
    }

    public boolean onLongClick(View view) {
        if (view != this.mXAxis && view != this.mYAxis) {
            return true;
        }
        reset3DData();
        return true;
    }
}
