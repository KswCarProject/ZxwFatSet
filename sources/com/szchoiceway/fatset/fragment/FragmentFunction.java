package com.szchoiceway.fatset.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import com.szchoiceway.fatset.R;
import com.szchoiceway.fatset.SysProviderOpt;
import com.szchoiceway.zxwlib.bean.Customer;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FragmentFunction extends FragmentBase implements View.OnClickListener {
    private static final String TAG = "FragmentFunction";
    private static final String usb_otg_switch = "/sys/devices/platform/soc/4e00000.ssusb/mode";
    List<CheckBox> amplifierCheckBoxs;
    CheckBox chk9211;
    CheckBox chkAux;
    CheckBox chkDTV;
    CheckBox chkEq;
    CheckBox chkExternal;
    CheckBox chkForward;
    CheckBox chkGoogle;
    CheckBox chkHicar;
    CheckBox chkHorn0;
    CheckBox chkHorn1;
    CheckBox chkOriginal;
    CheckBox chkScreenCast;
    CheckBox chkSendData;
    CheckBox chkTxz;
    CheckBox chkUsbHost;
    CheckBox chkWeather;
    CheckBox chkZlink;
    List<CheckBox> hornCheckBoxs;
    boolean open = false;
    View viewWeather;

    public void onAttach(Context context) {
        super.onAttach(context);
        checkAdb();
        Log.d(TAG, "open = " + this.open);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_function, (ViewGroup) null);
        if (Customer.isSmallResolution(getContext())) {
            inflate = layoutInflater.inflate(R.layout.small_fragment_function, (ViewGroup) null);
        }
        this.mainView = inflate;
        return inflate;
    }

    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        CheckBox checkBox = (CheckBox) view.findViewById(R.id.chkUsbHost);
        this.chkUsbHost = checkBox;
        if (checkBox != null) {
            checkBox.setChecked(this.open);
        }
        CheckBox checkBox2 = (CheckBox) view.findViewById(R.id.chkHicar);
        this.chkHicar = checkBox2;
        if (checkBox2 != null) {
            initSingleSettings(SysProviderOpt.KSW_HAVE_HICAR, checkBox2, false);
        }
        CheckBox checkBox3 = (CheckBox) view.findViewById(R.id.chkGoogle);
        this.chkGoogle = checkBox3;
        if (checkBox3 != null) {
            initSingleSettings(SysProviderOpt.MAISILUO_SYS_GOOGLEPLAY, checkBox3, false);
        }
        CheckBox checkBox4 = (CheckBox) view.findViewById(R.id.chkAux);
        this.chkAux = checkBox4;
        if (checkBox4 != null) {
            initSingleSettings(SysProviderOpt.KSW_HAVE_AUX, checkBox4, false);
            if (this.mProductIndex == 2) {
                this.chkAux.setChecked(false);
            }
        }
        CheckBox checkBox5 = (CheckBox) view.findViewById(R.id.chkDTV);
        this.chkDTV = checkBox5;
        if (checkBox5 != null) {
            initSingleSettings(SysProviderOpt.KSW_HAVE_TV, checkBox5, false);
            if (this.mProductIndex == 2) {
                this.chkDTV.setChecked(false);
            }
        }
        CheckBox checkBox6 = (CheckBox) view.findViewById(R.id.chkForward);
        this.chkForward = checkBox6;
        if (checkBox6 != null) {
            initSingleSettings(SysProviderOpt.KSW_HAVE_FRONT_CAMERA, checkBox6, false);
            if (this.mProductIndex == 2) {
                this.chkForward.setChecked(false);
            }
        }
        CheckBox checkBox7 = (CheckBox) view.findViewById(R.id.chkTxz);
        this.chkTxz = checkBox7;
        if (checkBox7 != null) {
            initSingleSettings(SysProviderOpt.KSW_HAVE_TXZ, checkBox7, false);
        }
        CheckBox checkBox8 = (CheckBox) view.findViewById(R.id.chkScreenCast);
        this.chkScreenCast = checkBox8;
        if (checkBox8 != null) {
            initSingleSettings(SysProviderOpt.KSW_SCREEN_CAST_MS9120, checkBox8, false);
        }
        CheckBox checkBox9 = (CheckBox) view.findViewById(R.id.chkSendData);
        this.chkSendData = checkBox9;
        if (checkBox9 != null) {
            initSingleSettings(SysProviderOpt.KSW_SEND_TOUCH_DATA_CONTINUED, checkBox9, false);
        }
        CheckBox checkBox10 = (CheckBox) view.findViewById(R.id.chkEQ);
        this.chkEq = checkBox10;
        if (checkBox10 != null) {
            initSingleSettings(SysProviderOpt.KSW_HAVE_EQ, checkBox10, true);
        }
        this.chkOriginal = (CheckBox) view.findViewById(R.id.chkOriginal);
        CheckBox checkBox11 = (CheckBox) view.findViewById(R.id.chkExternal);
        this.chkExternal = checkBox11;
        if (!(this.chkOriginal == null || checkBox11 == null)) {
            ArrayList arrayList = new ArrayList();
            this.amplifierCheckBoxs = arrayList;
            arrayList.add(this.chkOriginal);
            this.amplifierCheckBoxs.add(this.chkExternal);
            initMultiSettings(SysProviderOpt.KESAIWEI_RECORD_AMPLIFIER, this.amplifierCheckBoxs, 0);
        }
        this.chkHorn0 = (CheckBox) view.findViewById(R.id.chkHorn0);
        CheckBox checkBox12 = (CheckBox) view.findViewById(R.id.chkHorn1);
        this.chkHorn1 = checkBox12;
        if (!(this.chkHorn0 == null || checkBox12 == null)) {
            ArrayList arrayList2 = new ArrayList();
            this.hornCheckBoxs = arrayList2;
            arrayList2.add(this.chkHorn0);
            this.hornCheckBoxs.add(this.chkHorn1);
            initMultiSettings(SysProviderOpt.KESAIWEI_HORN_SELECTION, this.hornCheckBoxs, 0);
        }
        View findViewById = view.findViewById(R.id.viewWeather);
        this.viewWeather = findViewById;
        if (findViewById != null) {
            if (Customer.getUIIndex(getContext()) == 2) {
                this.viewWeather.setVisibility(0);
            } else {
                this.viewWeather.setVisibility(8);
            }
        }
        CheckBox checkBox13 = (CheckBox) view.findViewById(R.id.chkWeather);
        this.chkWeather = checkBox13;
        if (checkBox13 != null) {
            initSingleSettings(SysProviderOpt.KSW_HAVE_WEATHER, checkBox13, true);
        }
        View findViewById2 = view.findViewById(R.id.view9211);
        if (findViewById2 != null) {
            findViewById2.setVisibility(8);
        }
        CheckBox checkBox14 = (CheckBox) view.findViewById(R.id.chk9211);
        this.chk9211 = checkBox14;
        if (checkBox14 != null) {
            initSingleSettings(SysProviderOpt.KSW_IS_9211_DEVICE, checkBox14, false);
        }
        int[] iArr = {R.id.viewUsbHost, R.id.viewHicar, R.id.viewGoogle, R.id.viewAux, R.id.viewDTV, R.id.viewForward, R.id.viewTXZ, R.id.viewScreenCast, R.id.viewEQ, R.id.viewSendData, R.id.viewOriginal, R.id.viewExternal, R.id.viewHorn0, R.id.viewHorn1, R.id.viewWeather, R.id.view9211};
        for (int i = 0; i < 16; i++) {
            View findViewById3 = view.findViewById(iArr[i]);
            if (findViewById3 != null) {
                findViewById3.setOnClickListener(this);
            }
        }
    }

    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        checkAdb();
        CheckBox checkBox = this.chkUsbHost;
        if (checkBox != null) {
            checkBox.setChecked(this.open);
        }
    }

    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.view9211 /*2131231227*/:
                view.setClickable(false);
                refreshSingleSettings(SysProviderOpt.KSW_IS_9211_DEVICE, this.chk9211);
                if (this.chk9211 != null) {
                    getContext().sendBroadcast(new Intent("switch_9211").putExtra("data", this.chk9211.isChecked() ? "1" : "0"));
                    return;
                }
                return;
            case R.id.viewAux /*2131231231*/:
                if (this.mProductIndex != 2) {
                    refreshSingleSettings(SysProviderOpt.KSW_HAVE_AUX, this.chkAux);
                    return;
                }
                return;
            case R.id.viewDTV /*2131231250*/:
                if (this.mProductIndex != 2) {
                    refreshSingleSettings(SysProviderOpt.KSW_HAVE_TV, this.chkDTV);
                    return;
                }
                return;
            case R.id.viewEQ /*2131231251*/:
                refreshSingleSettings(SysProviderOpt.KSW_HAVE_EQ, this.chkEq);
                return;
            case R.id.viewExternal /*2131231252*/:
                refreshMultiSettings(SysProviderOpt.KESAIWEI_RECORD_AMPLIFIER, this.amplifierCheckBoxs, 1);
                kesaiwei_sendBroadcast(7);
                return;
            case R.id.viewForward /*2131231253*/:
                if (this.mProductIndex != 2) {
                    refreshSingleSettings(SysProviderOpt.KSW_HAVE_FRONT_CAMERA, this.chkForward);
                    kesaiwei_sendBroadcast(24);
                    return;
                }
                return;
            case R.id.viewGoogle /*2131231258*/:
                refreshSingleSettings(SysProviderOpt.MAISILUO_SYS_GOOGLEPLAY, this.chkGoogle);
                kesaiwei_sendBroadcast(66);
                return;
            case R.id.viewHicar /*2131231259*/:
                refreshSingleSettings(SysProviderOpt.KSW_HAVE_HICAR, this.chkHicar);
                showToast(getString(R.string.lb_tip_change_settings_item));
                return;
            case R.id.viewHorn0 /*2131231261*/:
                refreshMultiSettings(SysProviderOpt.KESAIWEI_HORN_SELECTION, this.hornCheckBoxs, 0);
                kesaiwei_sendBroadcast(54);
                return;
            case R.id.viewHorn1 /*2131231262*/:
                refreshMultiSettings(SysProviderOpt.KESAIWEI_HORN_SELECTION, this.hornCheckBoxs, 1);
                kesaiwei_sendBroadcast(54);
                return;
            case R.id.viewOriginal /*2131231312*/:
                refreshMultiSettings(SysProviderOpt.KESAIWEI_RECORD_AMPLIFIER, this.amplifierCheckBoxs, 0);
                kesaiwei_sendBroadcast(7);
                return;
            case R.id.viewScreenCast /*2131231320*/:
                refreshSingleSettings(SysProviderOpt.KSW_SCREEN_CAST_MS9120, this.chkScreenCast);
                return;
            case R.id.viewSendData /*2131231323*/:
                refreshSingleSettings(SysProviderOpt.KSW_SEND_TOUCH_DATA_CONTINUED, this.chkSendData);
                kesaiwei_sendBroadcast(51);
                return;
            case R.id.viewTXZ /*2131231327*/:
                refreshSingleSettings(SysProviderOpt.KSW_HAVE_TXZ, this.chkTxz);
                return;
            case R.id.viewUsbHost /*2131231331*/:
                refreshSingleSettings(SysProviderOpt.KSW_USB_HOST_MODE, this.chkUsbHost);
                openAdb(this.chkUsbHost.isChecked());
                return;
            case R.id.viewWeather /*2131231337*/:
                refreshSingleSettings(SysProviderOpt.KSW_HAVE_WEATHER, this.chkWeather);
                return;
            default:
                return;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:25:0x0039 A[SYNTHETIC, Splitter:B:25:0x0039] */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0044 A[SYNTHETIC, Splitter:B:30:0x0044] */
    /* JADX WARNING: Removed duplicated region for block: B:36:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void openAdb(boolean r4) {
        /*
            r3 = this;
            if (r4 == 0) goto L_0x0005
            java.lang.String r4 = "host"
            goto L_0x0007
        L_0x0005:
            java.lang.String r4 = "peripheral"
        L_0x0007:
            java.io.File r0 = new java.io.File
            java.lang.String r1 = "/sys/devices/platform/soc/4e00000.ssusb/mode"
            r0.<init>(r1)
            boolean r1 = r0.exists()
            if (r1 != 0) goto L_0x0015
            return
        L_0x0015:
            r1 = 0
            java.io.FileOutputStream r2 = new java.io.FileOutputStream     // Catch:{ FileNotFoundException -> 0x0033 }
            r2.<init>(r0)     // Catch:{ FileNotFoundException -> 0x0033 }
            byte[] r4 = r4.getBytes()     // Catch:{ IOException -> 0x0029 }
            r2.write(r4)     // Catch:{ IOException -> 0x0029 }
            goto L_0x002d
        L_0x0023:
            r4 = move-exception
            r1 = r2
            goto L_0x0042
        L_0x0026:
            r4 = move-exception
            r1 = r2
            goto L_0x0034
        L_0x0029:
            r4 = move-exception
            r4.printStackTrace()     // Catch:{ FileNotFoundException -> 0x0026, all -> 0x0023 }
        L_0x002d:
            r2.close()     // Catch:{ IOException -> 0x003d }
            goto L_0x0041
        L_0x0031:
            r4 = move-exception
            goto L_0x0042
        L_0x0033:
            r4 = move-exception
        L_0x0034:
            r4.printStackTrace()     // Catch:{ all -> 0x0031 }
            if (r1 == 0) goto L_0x0041
            r1.close()     // Catch:{ IOException -> 0x003d }
            goto L_0x0041
        L_0x003d:
            r4 = move-exception
            r4.printStackTrace()
        L_0x0041:
            return
        L_0x0042:
            if (r1 == 0) goto L_0x004c
            r1.close()     // Catch:{ IOException -> 0x0048 }
            goto L_0x004c
        L_0x0048:
            r0 = move-exception
            r0.printStackTrace()
        L_0x004c:
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.szchoiceway.fatset.fragment.FragmentFunction.openAdb(boolean):void");
    }

    private void checkAdb() {
        String str;
        File file = new File(usb_otg_switch);
        if (file.exists()) {
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                StringBuilder sb = new StringBuilder();
                while (true) {
                    String readLine = bufferedReader.readLine();
                    if (readLine == null) {
                        break;
                    }
                    sb.append(readLine);
                }
                str = sb.toString();
            } catch (IOException e) {
                e.printStackTrace();
                str = "";
            }
            if ("peripheral".equals(str)) {
                this.open = false;
            } else {
                this.open = true;
            }
            Log.d(TAG, "open " + this.open);
            if (this.mProvider.getRecordBoolean(SysProviderOpt.KSW_USB_HOST_MODE, true) != this.open) {
                this.mProvider.updateRecord(SysProviderOpt.KSW_USB_HOST_MODE, this.open ? "1" : "0");
            }
        }
    }
}
