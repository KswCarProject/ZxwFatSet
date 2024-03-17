package com.szchoiceway.fatset.fragment;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import com.szchoiceway.eventcenter.EventUtils;
import com.szchoiceway.fatset.R;
import com.szchoiceway.fatset.SysProviderOpt;
import com.szchoiceway.fatset.ZXWFatApp;
import java.util.List;

public class FragmentBase extends Fragment {
    private static final String TAG = "FragmentBase";
    protected String client = "KSW";
    protected int mProductIndex = 0;
    protected SysProviderOpt mProvider;
    private Toast mToast;
    protected int m_iModeSet = 0;
    protected int m_iUITypeVer = 0;
    protected View mainView;
    protected ZXWFatApp zxwFatApp;

    public void onAttach(Context context) {
        super.onAttach(context);
        this.mProvider = SysProviderOpt.getInstance(context);
        if (getActivity() != null) {
            this.zxwFatApp = (ZXWFatApp) getActivity().getApplication();
        }
        ZXWFatApp zXWFatApp = this.zxwFatApp;
        if (zXWFatApp != null) {
            this.m_iUITypeVer = zXWFatApp.GetUItypeVer();
            this.m_iModeSet = this.zxwFatApp.ksw_GetModeSet();
            this.client = this.zxwFatApp.getClient();
            this.mProductIndex = this.zxwFatApp.getProductIndex();
        }
    }

    /* access modifiers changed from: protected */
    public void initSingleSettings(String str, CheckBox checkBox, boolean z) {
        Log.d(TAG, "initSingleSettings: keyName = " + str + ", def =  " + z);
        boolean recordBoolean = this.mProvider.getRecordBoolean(str, z);
        Log.d(TAG, "checked = " + recordBoolean);
        if (SysProviderOpt.KSW_ORIGINAL_CAR_VIDEO_DISPLAY.equals(str)) {
            recordBoolean = !recordBoolean;
        }
        checkBox.setChecked(recordBoolean);
    }

    /* access modifiers changed from: protected */
    public void refreshSingleSettings(String str, CheckBox checkBox) {
        boolean z = !checkBox.isChecked();
        checkBox.setChecked(z);
        String str2 = "1";
        if (SysProviderOpt.KSW_ORIGINAL_CAR_VIDEO_DISPLAY.equals(str)) {
            SysProviderOpt sysProviderOpt = this.mProvider;
            if (z) {
                str2 = "0";
            }
            sysProviderOpt.updateRecord(str, str2);
            return;
        }
        SysProviderOpt sysProviderOpt2 = this.mProvider;
        if (!z) {
            str2 = "0";
        }
        sysProviderOpt2.updateRecord(str, str2);
    }

    /* access modifiers changed from: protected */
    public void initMultiSettings(String str, List<CheckBox> list, int i) {
        int recordInteger = this.mProvider.getRecordInteger(str, i);
        for (int i2 = 0; i2 < list.size(); i2++) {
            if (i2 == recordInteger) {
                list.get(i2).setChecked(true);
            } else {
                list.get(i2).setChecked(false);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void refreshMultiSettings(String str, List<CheckBox> list, int i) {
        for (int i2 = 0; i2 < list.size(); i2++) {
            if (i2 == i) {
                list.get(i2).setChecked(true);
            } else {
                list.get(i2).setChecked(false);
            }
        }
        this.mProvider.updateRecord(str, i + "");
    }

    /* access modifiers changed from: protected */
    public void kesaiwei_sendBroadcast(int i) {
        Intent intent = new Intent(EventUtils.ZXW_SENDBROADCAST8902MOD);
        intent.putExtra(EventUtils.ZXW_SENDBROADCAST8902MOD_EXTRA, i);
        if (getActivity() != null) {
            getActivity().sendBroadcast(intent);
        }
    }

    /* access modifiers changed from: protected */
    public void showToast(String str) {
        Toast toast = this.mToast;
        if (toast != null) {
            toast.cancel();
        }
        this.mToast = Toast.makeText(getContext(), "", 0);
        ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(getContext()).inflate(R.layout.layout_toast, (ViewGroup) null, false);
        ((TextView) viewGroup.findViewById(R.id.tvMessage)).setText(str);
        this.mToast.setView(viewGroup);
        this.mToast.show();
    }
}
