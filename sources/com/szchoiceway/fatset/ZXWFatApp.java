package com.szchoiceway.fatset;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import com.szchoiceway.eventcenter.IEventService;
import com.szchoiceway.fatset.util.MultipleUtils;
import com.szchoiceway.zxwlib.utils.CrashHandler;
import com.szchoiceway.zxwlib.utils.EventUtils;

public class ZXWFatApp extends Application {
    public static final String TAG = "ZXWFatApp";
    private boolean isUpdatingLogo = false;
    protected IEventService mService = null;
    public SysProviderOpt mSysProvider;
    private ServiceConnection osc = new ServiceConnection() {
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.i(ZXWFatApp.TAG, "ServiceConnection");
            ZXWFatApp.this.mService = IEventService.Stub.asInterface(iBinder);
            if (ZXWFatApp.this.mService != null) {
                try {
                    ZXWFatApp.this.mService.getBALVal();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }

        public void onServiceDisconnected(ComponentName componentName) {
            Log.i(ZXWFatApp.TAG, "onServiceDisconnected");
            ZXWFatApp.this.mService = null;
        }
    };

    public void onCreate() {
        super.onCreate();
        MultipleUtils.setCustomDensity((Activity) null, this);
        Log.i(TAG, "onCreate");
        CrashHandler.getInstance().init(getApplicationContext());
        this.mSysProvider = SysProviderOpt.getInstance(this);
        startConnectService();
    }

    public void onTerminate() {
        super.onTerminate();
        Log.i(TAG, "onTerminate");
        stopConntectService();
    }

    public IEventService getService() {
        return this.mService;
    }

    public SysProviderOpt getSysProvider() {
        return this.mSysProvider;
    }

    private void startConnectService() {
        try {
            bindService(new Intent(EventUtils.EVENT_SERVICE_NAME).setPackage("com.szchoiceway.eventcenter"), this.osc, 1);
            Log.i(TAG, "onBindService ok........ ..........");
        } catch (Exception e) {
            Log.e(TAG, "onBindService error " + e.toString());
        }
    }

    private void stopConntectService() {
        try {
            IEventService iEventService = this.mService;
            if (iEventService != null) {
                iEventService.getBALVal();
            }
        } catch (RemoteException e) {
            Log.e(TAG, "stopConntectService error " + e.toString());
        }
        try {
            unbindService(this.osc);
        } catch (Exception e2) {
            Log.e(TAG, "unbindService error " + e2.toString());
        }
    }

    public int GetUItypeVer() {
        return this.mSysProvider.getRecordInteger("Set_User_UI_Type", 41);
    }

    public int ksw_GetModeSet() {
        return this.mSysProvider.getRecordInteger("KESAIWEI_SYS_MODE_SELECTION", 16);
    }

    public String getClient() {
        return this.mSysProvider.getRecordValue(SysProviderOpt.KSW_FACTORY_SET_CLIENT, "KSW");
    }

    public boolean getUpdatingLogo() {
        return this.isUpdatingLogo;
    }

    public void setUpdatingLogo(boolean z) {
        this.isUpdatingLogo = z;
    }

    public int getProductIndex() {
        return this.mSysProvider.getRecordInteger(SysProviderOpt.KSW_DATA_PRODUCT_INDEX, 0);
    }
}
