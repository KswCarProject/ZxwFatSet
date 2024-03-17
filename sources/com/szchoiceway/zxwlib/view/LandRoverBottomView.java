package com.szchoiceway.zxwlib.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.input.InputManager;
import android.os.Build;
import android.os.RemoteException;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOverlay;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.vectordrawable.graphics.drawable.PathInterpolatorCompat;
import com.szchoiceway.eventcenter.IEventService;
import com.szchoiceway.zxwlib.R;
import com.szchoiceway.zxwlib.SysProviderOpt;
import com.szchoiceway.zxwlib.bean.Customer;
import com.szchoiceway.zxwlib.utils.EventUtils;
import java.util.Locale;

public class LandRoverBottomView extends RelativeLayout implements View.OnTouchListener {
    private static final String TAG = "LandRoverBottomView";
    /* access modifiers changed from: private */
    public CheckBox chkRadar;
    private int delayMills = PathInterpolatorCompat.MAX_NUM_POINTS;
    private int extra = 0;
    private final Runnable longClickRunnable = new Runnable() {
        public final void run() {
            LandRoverBottomView.this.lambda$new$1$LandRoverBottomView();
        }
    };
    /* access modifiers changed from: private */
    public boolean mIsPon = false;
    private IEventService mService;
    private MyBroadcast myBroadcast;

    public /* bridge */ /* synthetic */ ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return super.generateLayoutParams(attributeSet);
    }

    public /* bridge */ /* synthetic */ ViewOverlay getOverlay() {
        return super.getOverlay();
    }

    public LandRoverBottomView(Context context) {
        super(context);
    }

    public LandRoverBottomView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initView(context);
    }

    public LandRoverBottomView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initView(context);
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.d(TAG, "onAttachedToWindow");
        if (this.myBroadcast == null) {
            this.myBroadcast = new MyBroadcast();
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(EventUtils.ZXW_ACTION_KSW_LANDROVER_TOUCH_KEY_P);
        this.mContext.registerReceiver(this.myBroadcast, intentFilter);
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.d(TAG, "onDetachedFromWindow");
        if (this.myBroadcast != null) {
            this.mContext.unregisterReceiver(this.myBroadcast);
            this.myBroadcast = null;
        }
        if (this.mService != null) {
            this.mService = null;
        }
    }

    private void initView(Context context) {
        ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(context, R.style.landrover_bootom_blue);
        int recordInteger = SysProviderOpt.getInstance(this.mContext).getRecordInteger(SysProviderOpt.KSW_LANDROVER_THEME_BACKGROUND_INDEX, 0);
        if (Customer.getIModeSet(this.mContext) == 32) {
            contextThemeWrapper.setTheme(recordInteger == 0 ? R.style.landrover_bootom_blue : R.style.landrover_bottom_white);
        } else {
            contextThemeWrapper.setTheme(R.style.landrover_bootom_blue);
        }
        LayoutInflater.from(contextThemeWrapper).inflate(R.layout.layout_landrover_bottom_view, this, true);
        this.mIsPon = SysProviderOpt.getInstance(this.mContext).getRecordBoolean("KESAIWEI_RECORD_BT_INDEX", false);
        CheckBox checkBox = (CheckBox) findViewById(R.id.chkBottomRadar);
        this.chkRadar = checkBox;
        if (checkBox != null) {
            checkBox.setChecked(this.mIsPon);
        }
        int[] iArr = {R.id.btnBottomHome, R.id.btnBottomNavi, R.id.btnBottomSettings, R.id.btnBottomOriginal, R.id.btnBottom360};
        for (int i = 0; i < 5; i++) {
            View findViewById = findViewById(iArr[i]);
            if (findViewById != null) {
                findViewById.setTag(Integer.valueOf(i));
                findViewById.setOnClickListener(new View.OnClickListener() {
                    public final void onClick(View view) {
                        LandRoverBottomView.this.clickBottom(view);
                    }
                });
                findViewById.setOnLongClickListener(new View.OnLongClickListener() {
                    public final boolean onLongClick(View view) {
                        return LandRoverBottomView.this.lambda$initView$0$LandRoverBottomView(view);
                    }
                });
            }
        }
        int[] iArr2 = {R.id.btnBottomUp, R.id.btnBottomDown, R.id.btnBottomRadar};
        for (int i2 = 0; i2 < 3; i2++) {
            View findViewById2 = findViewById(iArr2[i2]);
            if (findViewById2 != null) {
                findViewById2.setOnTouchListener(this);
            }
        }
    }

    public /* synthetic */ boolean lambda$initView$0$LandRoverBottomView(View view) {
        clickBottom(view);
        return true;
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action == 0) {
            if (view.getId() == R.id.btnBottomUp) {
                this.extra = 12;
            } else if (view.getId() == R.id.btnBottomDown) {
                this.extra = 13;
            } else if (view.getId() == R.id.btnBottomRadar) {
                this.extra = 9;
            }
            sendLandRoverKey(this.extra, 1);
            this.mContext.getMainThreadHandler().postDelayed(this.longClickRunnable, (long) this.delayMills);
        } else if (action == 1) {
            this.mContext.getMainThreadHandler().removeCallbacks(this.longClickRunnable);
            sendLandRoverKey(this.extra, 0);
            this.extra = 0;
        }
        return false;
    }

    /* access modifiers changed from: private */
    public void clickBottom(View view) {
        if (view.getId() == R.id.btnBottomHome) {
            sendKeyDownUpSync(3);
        } else if (view.getId() == R.id.btnBottomNavi) {
            enterNavi();
        } else if (view.getId() == R.id.btnBottomSettings) {
            enterSettings();
        } else if (view.getId() == R.id.btnBottomOriginal) {
            IEventService iEventService = this.mService;
            if (iEventService != null) {
                try {
                    if (iEventService.GetBTStatus() < 4) {
                        enterOriginal();
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            } else {
                enterOriginal();
            }
        } else if (view.getId() == R.id.btnBottom360) {
            enter360();
        }
    }

    private void enterOriginal() {
        this.mContext.sendBroadcast(new Intent(EventUtils.ACTION_SWITCH_ORIGINACAR));
    }

    private void sendLandRoverKey(int i, int i2) {
        Intent intent = new Intent(EventUtils.ZXW_ACTION_KSW_LANDROVER_TOUCH_KEY);
        intent.putExtra(EventUtils.ZXW_ACTION_KSW_LANDROVER_TOUCH_KEY_EXTRA, i);
        intent.putExtra(EventUtils.ZXW_ACTION_KSW_LANDROVER_TOUCH_KEY_ACTION, i2);
        this.mContext.sendBroadcast(intent);
    }

    private void enterNavi() {
        boolean z = Locale.getDefault().getLanguage().equals("zh") && Locale.getDefault().getCountry().equals("CN");
        SysProviderOpt instance = SysProviderOpt.getInstance(this.mContext);
        String str = EventUtils.NAV_MODE_PACKAGE_NAME;
        String recordValue = instance.getRecordValue(SysProviderOpt.NAV_PACKAGENAME, str);
        SysProviderOpt instance2 = SysProviderOpt.getInstance(this.mContext);
        String str2 = EventUtils.NAV_MODE_CLASS_NAME;
        String recordValue2 = instance2.getRecordValue(SysProviderOpt.NAV_ACTIVITYNAME, str2);
        if ((!z || !EventUtils.GOOGLE_MAP_MODE_PACKAGE_NAME.equals(recordValue)) && EventUtils.getInstallStatus(this.mContext, recordValue)) {
            str = recordValue;
            str2 = recordValue2;
        }
        EventUtils.startActivityIfNotRuning(this.mContext, str, str2);
    }

    private void enterBT() {
        if (SysProviderOpt.getInstance(this.mContext).getRecordInteger("KESAIWEI_RECORD_BT_INDEX", 0) == 0) {
            EventUtils.startActivityIfNotRuning(this.mContext, EventUtils.BT_MODE_PACKAGE_NAME, EventUtils.BT_MODE_CLASS_NAME);
            return;
        }
        this.mContext.sendBroadcast(new Intent(EventUtils.ACTION_SWITCH_ORIGINACAR));
    }

    private void enterVideo() {
        String str = "com.szchoiceway.videoplayer";
        String recordValue = SysProviderOpt.getInstance(this.mContext).getRecordValue(SysProviderOpt.VIDEO_PACKAGENAME, str);
        String str2 = "com.szchoiceway.videoplayer.MainActivity";
        String recordValue2 = SysProviderOpt.getInstance(this.mContext).getRecordValue(SysProviderOpt.VIDEO_ACTIVITYNAME, str2);
        if (EventUtils.getInstallStatus(this.mContext, recordValue)) {
            Intent intent = new Intent(EventUtils.ZXW_ACTION_ENTER_THIRD_MEDIA);
            intent.putExtra(EventUtils.ZXW_ACTION_ENTER_THIRD_MEDIA_EXTRA, 1);
            this.mContext.sendBroadcast(intent);
            str = recordValue;
            str2 = recordValue2;
        }
        EventUtils.startActivityIfNotRuning(this.mContext, str, str2);
    }

    private void enterSettings() {
        EventUtils.startActivityIfNotRuning(this.mContext, EventUtils.SET_MODE_PACKAGE_NAME, EventUtils.SET_MODE_CLASS_NAME);
    }

    private void enter360() {
        if (SysProviderOpt.getInstance(this.mContext).getRecordInteger(SysProviderOpt.KESAIWEI_SYS_CAMERA_SELECTION, 1) == 3) {
            if (Build.VERSION.SDK_INT == 31) {
                EventUtils.startActivityIfNotRuning(this.mContext, EventUtils.LIDIAN_MODE_PACKAGE_NAME, EventUtils.LIDIAN_MODE_CLASS_NAME);
            } else {
                EventUtils.startActivityIfNotRuning(this.mContext, EventUtils.XYQ_MODE_PACKAGE_NAME, EventUtils.XYQ_MODE_CLASS_NAME);
            }
        }
    }

    public void setEventService(IEventService iEventService) {
        this.mService = iEventService;
    }

    class MyBroadcast extends BroadcastReceiver {
        MyBroadcast() {
        }

        public void onReceive(Context context, Intent intent) {
            boolean booleanExtra;
            if (EventUtils.ZXW_ACTION_KSW_LANDROVER_TOUCH_KEY_P.equals(intent.getAction()) && LandRoverBottomView.this.mIsPon != (booleanExtra = intent.getBooleanExtra(EventUtils.ZXW_ACTION_KSW_LANDROVER_TOUCH_KEY_P_EXTRA, false))) {
                boolean unused = LandRoverBottomView.this.mIsPon = booleanExtra;
                if (LandRoverBottomView.this.chkRadar != null) {
                    LandRoverBottomView.this.chkRadar.setChecked(LandRoverBottomView.this.mIsPon);
                }
            }
        }
    }

    public void sendKeyDownUpSync(int i) {
        sendKeySync(new KeyEvent(0, i));
        SystemClock.sleep(10);
        sendKeySync(new KeyEvent(1, i));
    }

    public void sendKeySync(KeyEvent keyEvent) {
        long downTime = keyEvent.getDownTime();
        long eventTime = keyEvent.getEventTime();
        int action = keyEvent.getAction();
        int keyCode = keyEvent.getKeyCode();
        int repeatCount = keyEvent.getRepeatCount();
        int metaState = keyEvent.getMetaState();
        int deviceId = keyEvent.getDeviceId();
        int scanCode = keyEvent.getScanCode();
        int source = keyEvent.getSource();
        int flags = keyEvent.getFlags();
        if (source == 0) {
            source = 257;
        }
        int i = source;
        if (eventTime == 0) {
            eventTime = SystemClock.uptimeMillis();
        }
        if (downTime == 0) {
            downTime = eventTime;
        }
        KeyEvent keyEvent2 = r4;
        KeyEvent keyEvent3 = new KeyEvent(downTime, eventTime, action, keyCode, repeatCount, metaState, deviceId, scanCode, flags | 8, i);
        InputManager.getInstance().injectInputEvent(keyEvent2, 2);
    }

    public /* synthetic */ void lambda$new$1$LandRoverBottomView() {
        sendLandRoverKey(this.extra, 2);
    }
}
