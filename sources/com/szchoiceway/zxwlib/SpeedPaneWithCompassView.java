package com.szchoiceway.zxwlib;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.ViewOverlay;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SpeedPaneWithCompassView extends RelativeLayout {
    public static final String ACTION_GPS_LOCATION_INFOR = "com.szchoiceway.eventcenter.EventUtils.ACTION_GPS_LOCATION_INFOR";
    public static final String CAR_CAN_DATA = "EventUtils.CAR_CAN_DATA";
    private static final int EVT_CAR_CAN_INFOR = 1;
    private static final int EVT_UPDATE_COMPASS = 0;
    public static final String MCU_CAR_CAN_INFO = "com.szchoiceway.eventcenter.EventUtils.MCU_CAR_CAN_INFO";
    private boolean bSpeedUnit = true;
    private boolean isRegistered = false;
    LinearInterpolator linearInterpolator = new LinearInterpolator();
    private ImageView mCompass;
    private Context mContext;
    /* access modifiers changed from: private */
    public float mDirection = 0.0f;
    /* access modifiers changed from: private */
    public boolean mFirstInit = true;
    /* access modifiers changed from: private */
    public Handler mHandler = new Handler() {
        public void handleMessage(Message message) {
            byte[] bArr;
            int i = message.what;
            byte b = 0;
            if (i == 0) {
                if (SpeedPaneWithCompassView.this.mSpeed > 3.0f || SpeedPaneWithCompassView.this.mFirstInit) {
                    try {
                        SpeedPaneWithCompassView speedPaneWithCompassView = SpeedPaneWithCompassView.this;
                        speedPaneWithCompassView.rotateCompass(360.0f - speedPaneWithCompassView.mDirection);
                        boolean unused = SpeedPaneWithCompassView.this.mFirstInit = false;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                sendEmptyMessageDelayed(0, 100);
            } else if (i == 1 && (bArr = (byte[]) message.obj) != null && bArr.length == 3) {
                byte b2 = bArr[0] & 255;
                byte b3 = (bArr[2] & 255) | ((bArr[1] & 255) << 8);
                if (b2 < 0) {
                    b2 = 0;
                } else if (b2 > 240) {
                    b2 = 240;
                }
                if (b3 >= 0) {
                    b = b3 > 8000 ? 8000 : b3;
                }
                SpeedPaneWithCompassView.this.refreshCarSpeed(b2, b);
            }
        }
    };
    private float mPreviousDegree = 360.0f;
    private SysProviderOpt mProvider = null;
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(SpeedPaneWithCompassView.MCU_CAR_CAN_INFO)) {
                byte[] byteArrayExtra = intent.getByteArrayExtra(SpeedPaneWithCompassView.CAR_CAN_DATA);
                if (byteArrayExtra != null && byteArrayExtra.length > 0) {
                    Message obtainMessage = SpeedPaneWithCompassView.this.mHandler.obtainMessage();
                    obtainMessage.what = 1;
                    obtainMessage.obj = byteArrayExtra;
                    SpeedPaneWithCompassView.this.mHandler.sendMessage(obtainMessage);
                }
            } else if (action.equals(SpeedPaneWithCompassView.ACTION_GPS_LOCATION_INFOR)) {
                double doubleExtra = intent.getDoubleExtra("GPS_DEGREE", 0.0d);
                intent.getDoubleExtra("GPS_LATITUDE", 0.0d);
                intent.getDoubleExtra("GPS_LONGTITUDE", 0.0d);
                intent.getDoubleExtra("GPS_ALTITUDE", 0.0d);
                float unused = SpeedPaneWithCompassView.this.mDirection = (float) doubleExtra;
                float unused2 = SpeedPaneWithCompassView.this.mSpeed = (float) ((double) intent.getFloatExtra("GPS_SPEED", 0.0f));
            }
        }
    };
    private ImageView mRotatePointer;
    private TextView mRotateText;
    /* access modifiers changed from: private */
    public float mSpeed = 0.0f;
    private ImageView mSpeedPointer;
    private TextView mSpeedText;
    private TextView mUnitText;

    public /* bridge */ /* synthetic */ ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return super.generateLayoutParams(attributeSet);
    }

    public /* bridge */ /* synthetic */ ViewOverlay getOverlay() {
        return super.getOverlay();
    }

    public SpeedPaneWithCompassView(Context context) {
        super(context);
        this.mContext = context;
    }

    public SpeedPaneWithCompassView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mContext = context;
    }

    public SpeedPaneWithCompassView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mContext = context;
    }

    public SpeedPaneWithCompassView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    public void startMonitor() {
        SysProviderOpt sysProviderOpt = new SysProviderOpt(this.mContext);
        this.mProvider = sysProviderOpt;
        this.bSpeedUnit = sysProviderOpt.getRecordBoolean(SysProviderOpt.SYS_CAR_SPEED_UNIT, false);
        this.mRotatePointer = (ImageView) findViewById(R.id.imgRotatePointer);
        this.mSpeedPointer = (ImageView) findViewById(R.id.imgSpeedPointer);
        this.mCompass = (ImageView) findViewById(R.id.compass);
        this.mRotateText = (TextView) findViewById(R.id.tvRotateText);
        this.mSpeedText = (TextView) findViewById(R.id.tvSpeedText);
        this.mUnitText = (TextView) findViewById(R.id.tvSpeedUnit);
        this.mHandler.sendEmptyMessage(0);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MCU_CAR_CAN_INFO);
        intentFilter.addAction(ACTION_GPS_LOCATION_INFOR);
        this.mContext.registerReceiver(this.mReceiver, intentFilter);
        this.isRegistered = true;
    }

    public void stopMonitor() {
        this.mHandler.removeCallbacksAndMessages((Object) null);
        if (this.isRegistered) {
            this.mContext.unregisterReceiver(this.mReceiver);
            this.isRegistered = false;
        }
        this.mFirstInit = true;
    }

    /* access modifiers changed from: private */
    public void rotateCompass(float f) {
        RotateAnimation rotateAnimation = new RotateAnimation(this.mPreviousDegree, f, 1, 0.5f, 1, 0.5f);
        rotateAnimation.setDuration(1000);
        rotateAnimation.setInterpolator(this.linearInterpolator);
        rotateAnimation.setFillAfter(true);
        ImageView imageView = this.mCompass;
        if (imageView != null) {
            imageView.startAnimation(rotateAnimation);
        }
        this.mPreviousDegree = f;
    }

    /* access modifiers changed from: private */
    public void refreshCarSpeed(int i, int i2) {
        boolean z = this.bSpeedUnit;
        if (!z) {
            i = (int) (((double) i) * 0.62d);
        }
        TextView textView = this.mUnitText;
        if (textView != null) {
            if (z) {
                textView.setText("km/h");
            } else {
                textView.setText("mph");
            }
        }
        TextView textView2 = this.mRotateText;
        if (textView2 != null) {
            textView2.setText(i2 + "");
        }
        TextView textView3 = this.mSpeedText;
        if (textView3 != null) {
            textView3.setText(i + "");
        }
        ImageView imageView = this.mSpeedPointer;
        if (imageView != null) {
            imageView.setRotation((float) (i + GyroScopeWithCompassView.CARTYPE_SDF_HI));
        }
        ImageView imageView2 = this.mRotatePointer;
        if (imageView2 != null) {
            imageView2.setRotation((float) (((int) ((((float) i2) / 1000.0f) * 30.0f)) + GyroScopeWithCompassView.CARTYPE_SDF_HI));
        }
    }
}
