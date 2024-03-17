package com.szchoiceway.fatset.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import com.szchoiceway.fatset.R;
import com.szchoiceway.fatset.SysProviderOpt;
import com.szchoiceway.zxwlib.bean.Customer;
import java.util.ArrayList;
import java.util.List;

public class FragmentVehicleButton extends FragmentBase implements View.OnClickListener {
    private static final String TAG = "VehicleButton";
    CheckBox chkAndroidNav;
    CheckBox chkModeEffect;
    CheckBox chkModeUnEffect;
    CheckBox chkOriginalNav;
    CheckBox chkPhoneSelection0;
    CheckBox chkPhoneSelection1;
    CheckBox chkPhoneSelection2;
    CheckBox chkVoiceSelection0;
    CheckBox chkVoiceSelection1;
    CheckBox chkVoiceSelection2;
    CheckBox chkVoiceSelection3;
    CheckBox chkVoiceSelection4;
    List<CheckBox> modeCheckBoxs;
    List<CheckBox> navCheckBoxs;
    List<CheckBox> phoneCheckBoxs;
    List<CheckBox> voiceCheckBoxs;

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return Customer.isSmallResolution(getContext()) ? layoutInflater.inflate(R.layout.small_fragment_vehicle_button, (ViewGroup) null) : layoutInflater.inflate(R.layout.fragment_vehicle_button, (ViewGroup) null);
    }

    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.chkAndroidNav = (CheckBox) view.findViewById(R.id.chkAndroidNav);
        CheckBox checkBox = (CheckBox) view.findViewById(R.id.chkOriginalNav);
        this.chkOriginalNav = checkBox;
        if (!(this.chkAndroidNav == null || checkBox == null)) {
            ArrayList arrayList = new ArrayList();
            this.navCheckBoxs = arrayList;
            arrayList.add(this.chkAndroidNav);
            this.navCheckBoxs.add(this.chkOriginalNav);
            initMultiSettings(SysProviderOpt.KSW_MAP_KEY_FUNCTION_INDEX, this.navCheckBoxs, 0);
        }
        this.chkModeEffect = (CheckBox) view.findViewById(R.id.chkModeEffect);
        CheckBox checkBox2 = (CheckBox) view.findViewById(R.id.chkModeUnEffect);
        this.chkModeUnEffect = checkBox2;
        if (!(this.chkModeEffect == null || checkBox2 == null)) {
            ArrayList arrayList2 = new ArrayList();
            this.modeCheckBoxs = arrayList2;
            arrayList2.add(this.chkModeEffect);
            this.modeCheckBoxs.add(this.chkModeUnEffect);
            initMultiSettings(SysProviderOpt.KSW_MODE_KEY_FUNCTION_INDEX, this.modeCheckBoxs, 0);
        }
        this.chkVoiceSelection0 = (CheckBox) view.findViewById(R.id.chkVoiceSelection0);
        this.chkVoiceSelection1 = (CheckBox) view.findViewById(R.id.chkVoiceSelection1);
        this.chkVoiceSelection2 = (CheckBox) view.findViewById(R.id.chkVoiceSelection2);
        this.chkVoiceSelection3 = (CheckBox) view.findViewById(R.id.chkVoiceSelection3);
        CheckBox checkBox3 = (CheckBox) view.findViewById(R.id.chkVoiceSelection4);
        this.chkVoiceSelection4 = checkBox3;
        if (!(this.chkVoiceSelection0 == null || this.chkVoiceSelection1 == null || this.chkVoiceSelection2 == null || this.chkVoiceSelection3 == null || checkBox3 == null)) {
            ArrayList arrayList3 = new ArrayList();
            this.voiceCheckBoxs = arrayList3;
            arrayList3.add(this.chkVoiceSelection0);
            this.voiceCheckBoxs.add(this.chkVoiceSelection1);
            this.voiceCheckBoxs.add(this.chkVoiceSelection2);
            this.voiceCheckBoxs.add(this.chkVoiceSelection3);
            this.voiceCheckBoxs.add(this.chkVoiceSelection4);
            initMultiSettings(SysProviderOpt.KSW_VOICE_KEY_FUNCTION_INDEX, this.voiceCheckBoxs, 0);
        }
        this.chkPhoneSelection0 = (CheckBox) view.findViewById(R.id.chkPhoneSelection0);
        this.chkPhoneSelection1 = (CheckBox) view.findViewById(R.id.chkPhoneSelection1);
        CheckBox checkBox4 = (CheckBox) view.findViewById(R.id.chkPhoneSelection2);
        this.chkPhoneSelection2 = checkBox4;
        if (!(this.chkPhoneSelection0 == null || this.chkPhoneSelection1 == null || checkBox4 == null)) {
            ArrayList arrayList4 = new ArrayList();
            this.phoneCheckBoxs = arrayList4;
            arrayList4.add(this.chkPhoneSelection0);
            this.phoneCheckBoxs.add(this.chkPhoneSelection1);
            this.phoneCheckBoxs.add(this.chkPhoneSelection2);
            initMultiSettings(SysProviderOpt.KSW_PHONE_KEY_FUNCTION_INDEX, this.phoneCheckBoxs, 0);
        }
        int[] iArr = {R.id.viewAndroidNav, R.id.viewOriginalNav, R.id.viewModeEffect, R.id.viewModeUnEffect, R.id.viewVoiceSelection0, R.id.viewVoiceSelection1, R.id.viewVoiceSelection2, R.id.viewVoiceSelection3, R.id.viewVoiceSelection4, R.id.viewPhoneSelection0, R.id.viewPhoneSelection1, R.id.viewPhoneSelection2};
        for (int i = 0; i < 12; i++) {
            View findViewById = view.findViewById(iArr[i]);
            if (findViewById != null) {
                findViewById.setOnClickListener(this);
            }
        }
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.viewAndroidNav) {
            refreshMultiSettings(SysProviderOpt.KSW_MAP_KEY_FUNCTION_INDEX, this.navCheckBoxs, 0);
            kesaiwei_sendBroadcast(41);
        } else if (id != R.id.viewOriginalNav) {
            switch (id) {
                case R.id.viewModeEffect:
                    refreshMultiSettings(SysProviderOpt.KSW_MODE_KEY_FUNCTION_INDEX, this.modeCheckBoxs, 0);
                    kesaiwei_sendBroadcast(49);
                    return;
                case R.id.viewModeUnEffect:
                    refreshMultiSettings(SysProviderOpt.KSW_MODE_KEY_FUNCTION_INDEX, this.modeCheckBoxs, 1);
                    kesaiwei_sendBroadcast(49);
                    return;
                default:
                    switch (id) {
                        case R.id.viewPhoneSelection0:
                            refreshMultiSettings(SysProviderOpt.KSW_PHONE_KEY_FUNCTION_INDEX, this.phoneCheckBoxs, 0);
                            kesaiwei_sendBroadcast(50);
                            return;
                        case R.id.viewPhoneSelection1:
                            refreshMultiSettings(SysProviderOpt.KSW_PHONE_KEY_FUNCTION_INDEX, this.phoneCheckBoxs, 1);
                            kesaiwei_sendBroadcast(50);
                            return;
                        case R.id.viewPhoneSelection2:
                            refreshMultiSettings(SysProviderOpt.KSW_PHONE_KEY_FUNCTION_INDEX, this.phoneCheckBoxs, 2);
                            kesaiwei_sendBroadcast(50);
                            return;
                        default:
                            switch (id) {
                                case R.id.viewVoiceSelection0:
                                    refreshMultiSettings(SysProviderOpt.KSW_VOICE_KEY_FUNCTION_INDEX, this.voiceCheckBoxs, 0);
                                    kesaiwei_sendBroadcast(36);
                                    return;
                                case R.id.viewVoiceSelection1:
                                    refreshMultiSettings(SysProviderOpt.KSW_VOICE_KEY_FUNCTION_INDEX, this.voiceCheckBoxs, 1);
                                    kesaiwei_sendBroadcast(36);
                                    return;
                                case R.id.viewVoiceSelection2:
                                    refreshMultiSettings(SysProviderOpt.KSW_VOICE_KEY_FUNCTION_INDEX, this.voiceCheckBoxs, 2);
                                    kesaiwei_sendBroadcast(36);
                                    return;
                                case R.id.viewVoiceSelection3:
                                    refreshMultiSettings(SysProviderOpt.KSW_VOICE_KEY_FUNCTION_INDEX, this.voiceCheckBoxs, 3);
                                    kesaiwei_sendBroadcast(36);
                                    return;
                                case R.id.viewVoiceSelection4:
                                    refreshMultiSettings(SysProviderOpt.KSW_VOICE_KEY_FUNCTION_INDEX, this.voiceCheckBoxs, 4);
                                    kesaiwei_sendBroadcast(36);
                                    return;
                                default:
                                    return;
                            }
                    }
            }
        } else {
            refreshMultiSettings(SysProviderOpt.KSW_MAP_KEY_FUNCTION_INDEX, this.navCheckBoxs, 1);
            kesaiwei_sendBroadcast(41);
        }
    }
}
