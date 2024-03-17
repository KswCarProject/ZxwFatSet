package com.szchoiceway.fatset.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import com.szchoiceway.fatset.R;
import com.szchoiceway.fatset.SysProviderOpt;
import com.szchoiceway.fatset.util.ListUtil;
import com.szchoiceway.zxwlib.bean.Customer;
import java.util.ArrayList;
import java.util.List;

public class FragmentCarSpecial extends FragmentBase implements View.OnClickListener {
    private int carFactoryIndex;
    CheckBox chkBenzAux;
    CheckBox chkControl0;
    CheckBox chkControl1;
    CheckBox chkControl2;
    CheckBox chkControl3;
    CheckBox chkHostBs;
    CheckBox chkHostBsCapacitive;
    CheckBox chkHostBsResistance1;
    CheckBox chkHostBsResistance2;
    CheckBox chkHostHm;
    List<CheckBox> chkLRHost;
    List<CheckBox> chkLRKeyPanelLeft;
    CheckBox chkLRKeyPanelLeft0;
    CheckBox chkLRKeyPanelLeft1;
    CheckBox chkLRKeyPanelLeft2;
    CheckBox chkLRKeyPanelLeft3;
    CheckBox chkLRKeyPanelLeft4;
    CheckBox chkLRKeyPanelLeft5;
    List<CheckBox> chkLRKeyPanelRight;
    CheckBox chkLRKeyPanelRight0;
    CheckBox chkLRKeyPanelRight1;
    CheckBox chkLRKeyPanelRight2;
    CheckBox chkLRKeyPanelRight3;
    CheckBox chkLRKeyPanelRight4;
    List<CheckBox> chkLRWheelControlType;
    CheckBox chkLexusAirControl;
    CheckBox chkLexusOriginFM;
    CheckBox chkMainLeftCarIconA3;
    CheckBox chkMainLeftCarIconA4;
    CheckBox chkMainLeftCarIconA4L;
    CheckBox chkMainLeftCarIconA4Old;
    CheckBox chkMainLeftCarIconA5;
    CheckBox chkMainLeftCarIconA6;
    CheckBox chkMainLeftCarIconHide;
    CheckBox chkMainLeftCarIconQ3;
    CheckBox chkMainLeftCarIconQ5;
    CheckBox chkMainLeftCarIconQ7;
    CheckBox chkMainRightWidgetsDriving;
    CheckBox chkMainRightWidgetsHide;
    CheckBox chkMainRightWidgetsMusic;
    CheckBox chkMainRightWidgetsWeather;
    CheckBox chkMainRightWidgetsWeatherAndGaode;
    CheckBox chkShowAir;
    CheckBox chkWheelControlADC;
    CheckBox chkWheelControlLin;
    List<CheckBox> controlChkBoxs;
    List<CheckBox> gt6AudiLeftCarIconChkBoxes;
    List<CheckBox> gt6AudiRightWidgets;
    View viewBenzControl;

    public void onAttach(Context context) {
        super.onAttach(context);
        this.carFactoryIndex = Customer.getCarFactoryIndex(context);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        if (Customer.isSmallResolution(getContext())) {
            int i = this.carFactoryIndex;
            if (i == 2) {
                return layoutInflater.inflate(R.layout.small_fragment_special_benz, (ViewGroup) null);
            }
            if (i == 3) {
                return layoutInflater.inflate(R.layout.fragment_special_audi, (ViewGroup) null);
            }
            if (i == 4) {
                return layoutInflater.inflate(R.layout.fragment_special_lexus, (ViewGroup) null);
            }
            if (i == 5) {
                return layoutInflater.inflate(R.layout.fragment_special_landrover, (ViewGroup) null);
            }
            return layoutInflater.inflate(R.layout.fragment_special_bwm, (ViewGroup) null);
        }
        int i2 = this.carFactoryIndex;
        if (i2 == 2) {
            return layoutInflater.inflate(R.layout.fragment_special_benz, (ViewGroup) null);
        }
        if (i2 == 3) {
            return layoutInflater.inflate(R.layout.fragment_special_audi, (ViewGroup) null);
        }
        if (i2 == 4) {
            return layoutInflater.inflate(R.layout.fragment_special_lexus, (ViewGroup) null);
        }
        if (i2 == 5) {
            return layoutInflater.inflate(R.layout.fragment_special_landrover, (ViewGroup) null);
        }
        return layoutInflater.inflate(R.layout.fragment_special_bwm, (ViewGroup) null);
    }

    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        int[] iArr = {R.id.viewShowAir, R.id.viewControl0, R.id.viewControl1, R.id.viewControl2, R.id.viewControl3, R.id.viewBenzAux, R.id.viewMainLeftCarIconHide, R.id.viewMainLeftCarIconA4, R.id.viewMainLeftCarIconA4old, R.id.viewMainLeftCarIconA4L, R.id.viewMainLeftCarIconA3, R.id.viewMainLeftCarIconA5, R.id.viewMainLeftCarIconA6, R.id.viewMainLeftCarIconQ3, R.id.viewMainLeftCarIconQ5, R.id.viewMainLeftCarIconQ7, R.id.viewMainRightWidgetsHide, R.id.viewMainRightWidgetsWeatherAndGaode, R.id.viewMainRightWidgetsWeather, R.id.viewMainRightWidgetsDriving, R.id.viewMainRightWidgetsMusic, R.id.viewMainRightWidgetsInstrumentCluster, R.id.viewHostBsCapacitive, R.id.viewHostBsResistance1, R.id.viewHostBsResistance2, R.id.viewHostHm, R.id.viewWheelControlLin, R.id.viewWheelControlADC, R.id.viewLRKeyPanelLeft0, R.id.viewLRKeyPanelLeft1, R.id.viewLRKeyPanelLeft2, R.id.viewLRKeyPanelLeft3, R.id.viewLRKeyPanelLeft4, R.id.viewLRKeyPanelLeft5, R.id.viewLRKeyPanelRight0, R.id.viewLRKeyPanelRight1, R.id.viewLRKeyPanelRight2, R.id.viewLRKeyPanelRight3, R.id.viewLRKeyPanelRight4, R.id.viewAirControl, R.id.viewOriginFM};
        for (int i = 0; i < 41; i++) {
            View findViewById = view.findViewById(iArr[i]);
            if (findViewById != null) {
                findViewById.setOnClickListener(this);
            }
        }
        CheckBox checkBox = (CheckBox) view.findViewById(R.id.chkShowAir);
        this.chkShowAir = checkBox;
        if (checkBox != null) {
            initSingleSettings(SysProviderOpt.KSW_SHOW_AIR, checkBox, false);
        }
        this.viewBenzControl = view.findViewById(R.id.viewBenzControl);
        this.chkControl0 = (CheckBox) view.findViewById(R.id.chkControl0);
        this.chkControl1 = (CheckBox) view.findViewById(R.id.chkControl1);
        this.chkControl2 = (CheckBox) view.findViewById(R.id.chkControl2);
        CheckBox checkBox2 = (CheckBox) view.findViewById(R.id.chkControl3);
        this.chkControl3 = checkBox2;
        if (!(this.chkControl0 == null || this.chkControl1 == null || this.chkControl2 == null || checkBox2 == null)) {
            ArrayList arrayList = new ArrayList();
            this.controlChkBoxs = arrayList;
            arrayList.add(this.chkControl0);
            this.controlChkBoxs.add(this.chkControl1);
            this.controlChkBoxs.add(this.chkControl2);
            this.controlChkBoxs.add(this.chkControl3);
            initMultiSettings(SysProviderOpt.SYS_BENZ_CONTROL_INDEX, this.controlChkBoxs, 0);
        }
        CheckBox checkBox3 = (CheckBox) view.findViewById(R.id.chkBenzAux);
        this.chkBenzAux = checkBox3;
        if (checkBox3 != null) {
            initSingleSettings(SysProviderOpt.KSW_AUX_ACTIVATION_FUNCTION_INDEX, checkBox3, false);
        }
        this.chkMainLeftCarIconHide = (CheckBox) view.findViewById(R.id.chkMainLeftCarIconHide);
        this.chkMainLeftCarIconA4 = (CheckBox) view.findViewById(R.id.chkMainLeftCarIconA4);
        this.chkMainLeftCarIconA4Old = (CheckBox) view.findViewById(R.id.chkMainLeftCarIconA4old);
        this.chkMainLeftCarIconA4L = (CheckBox) view.findViewById(R.id.chkMainLeftCarIconA4L);
        this.chkMainLeftCarIconA3 = (CheckBox) view.findViewById(R.id.chkMainLeftCarIconA3);
        this.chkMainLeftCarIconA5 = (CheckBox) view.findViewById(R.id.chkMainLeftCarIconA5);
        this.chkMainLeftCarIconA6 = (CheckBox) view.findViewById(R.id.chkMainLeftCarIconA6);
        this.chkMainLeftCarIconQ3 = (CheckBox) view.findViewById(R.id.chkMainLeftCarIconQ3);
        this.chkMainLeftCarIconQ5 = (CheckBox) view.findViewById(R.id.chkMainLeftCarIconQ5);
        CheckBox checkBox4 = (CheckBox) view.findViewById(R.id.chkMainLeftCarIconQ7);
        this.chkMainLeftCarIconQ7 = checkBox4;
        boolean z = true;
        int i2 = 8;
        if (ListUtil.listOf(this.chkMainLeftCarIconHide, this.chkMainLeftCarIconA4, this.chkMainLeftCarIconA4Old, this.chkMainLeftCarIconA4L, this.chkMainLeftCarIconA3, this.chkMainLeftCarIconA5, this.chkMainLeftCarIconA6, this.chkMainLeftCarIconQ3, this.chkMainLeftCarIconQ5, checkBox4).stream().allMatch($$Lambda$FragmentCarSpecial$99rgmS_txLPJdfHWhlcti9DG8Mw.INSTANCE)) {
            ArrayList arrayList2 = new ArrayList();
            this.gt6AudiLeftCarIconChkBoxes = arrayList2;
            arrayList2.add(this.chkMainLeftCarIconHide);
            this.gt6AudiLeftCarIconChkBoxes.add(this.chkMainLeftCarIconA4);
            this.gt6AudiLeftCarIconChkBoxes.add(this.chkMainLeftCarIconA4Old);
            this.gt6AudiLeftCarIconChkBoxes.add(this.chkMainLeftCarIconA4L);
            this.gt6AudiLeftCarIconChkBoxes.add(this.chkMainLeftCarIconA3);
            this.gt6AudiLeftCarIconChkBoxes.add(this.chkMainLeftCarIconA5);
            this.gt6AudiLeftCarIconChkBoxes.add(this.chkMainLeftCarIconA6);
            this.gt6AudiLeftCarIconChkBoxes.add(this.chkMainLeftCarIconQ3);
            this.gt6AudiLeftCarIconChkBoxes.add(this.chkMainLeftCarIconQ5);
            this.gt6AudiLeftCarIconChkBoxes.add(this.chkMainLeftCarIconQ7);
            initMultiSettings(SysProviderOpt.AUDI_GT6_LEFT_CAR_ICON, this.gt6AudiLeftCarIconChkBoxes, 0);
        }
        this.chkMainRightWidgetsHide = (CheckBox) view.findViewById(R.id.chkMainRightWidgetsHide);
        this.chkMainRightWidgetsWeatherAndGaode = (CheckBox) view.findViewById(R.id.chkMainRightWidgetsWeatherAndGaode);
        this.chkMainRightWidgetsWeather = (CheckBox) view.findViewById(R.id.chkMainRightWidgetsWeather);
        this.chkMainRightWidgetsDriving = (CheckBox) view.findViewById(R.id.chkMainRightWidgetsDriving);
        CheckBox checkBox5 = (CheckBox) view.findViewById(R.id.chkMainRightWidgetsMusic);
        this.chkMainRightWidgetsMusic = checkBox5;
        if (!(this.chkMainRightWidgetsHide == null || this.chkMainRightWidgetsWeatherAndGaode == null || this.chkMainRightWidgetsWeather == null || this.chkMainRightWidgetsDriving == null || checkBox5 == null)) {
            ArrayList arrayList3 = new ArrayList();
            this.gt6AudiRightWidgets = arrayList3;
            arrayList3.add(this.chkMainRightWidgetsHide);
            this.gt6AudiRightWidgets.add(this.chkMainRightWidgetsWeatherAndGaode);
            this.gt6AudiRightWidgets.add(this.chkMainRightWidgetsWeather);
            this.gt6AudiRightWidgets.add(this.chkMainRightWidgetsDriving);
            this.gt6AudiRightWidgets.add(this.chkMainRightWidgetsMusic);
            initMultiSettings(SysProviderOpt.AUDI_GT6_RIGHT_WIDGET, this.gt6AudiRightWidgets, 0);
        }
        View findViewById2 = view.findViewById(R.id.viewAudiWidget);
        if (findViewById2 != null) {
            if (this.m_iModeSet == 38) {
                i2 = 0;
            }
            findViewById2.setVisibility(i2);
        }
        this.chkHostBs = (CheckBox) view.findViewById(R.id.chkHostBs);
        this.chkHostBsCapacitive = (CheckBox) view.findViewById(R.id.chkHostBsCapacitive);
        this.chkHostBsResistance1 = (CheckBox) view.findViewById(R.id.chkHostBsResistance1);
        this.chkHostBsResistance2 = (CheckBox) view.findViewById(R.id.chkHostBsResistance2);
        CheckBox checkBox6 = (CheckBox) view.findViewById(R.id.chkHostHm);
        this.chkHostHm = checkBox6;
        if (!(this.chkHostBsCapacitive == null || this.chkHostBsResistance1 == null || this.chkHostBsResistance2 == null || checkBox6 == null)) {
            ArrayList arrayList4 = new ArrayList();
            this.chkLRHost = arrayList4;
            arrayList4.add(this.chkHostBsCapacitive);
            this.chkLRHost.add(this.chkHostBsResistance1);
            this.chkLRHost.add(this.chkHostBsResistance2);
            this.chkLRHost.add(this.chkHostHm);
            initMultiSettings(SysProviderOpt.KSW_LANDROVER_HOST_INDEX, this.chkLRHost, 0);
        }
        if (this.chkHostBs != null) {
            int recordInteger = this.mProvider.getRecordInteger(SysProviderOpt.KSW_LANDROVER_HOST_INDEX, 0);
            CheckBox checkBox7 = this.chkHostBs;
            if (recordInteger == 3) {
                z = false;
            }
            checkBox7.setChecked(z);
        }
        this.chkWheelControlLin = (CheckBox) view.findViewById(R.id.chkWheelControlLin);
        CheckBox checkBox8 = (CheckBox) view.findViewById(R.id.chkWheelControlADC);
        this.chkWheelControlADC = checkBox8;
        if (!(this.chkWheelControlLin == null || checkBox8 == null)) {
            ArrayList arrayList5 = new ArrayList();
            this.chkLRWheelControlType = arrayList5;
            arrayList5.add(this.chkWheelControlLin);
            this.chkLRWheelControlType.add(this.chkWheelControlADC);
            initMultiSettings(SysProviderOpt.KSW_LANDRVOER_WHEEL_CONTROL_TYPE, this.chkLRWheelControlType, 0);
        }
        this.chkLRKeyPanelLeft0 = (CheckBox) view.findViewById(R.id.chkLRKeyPanelLeft0);
        this.chkLRKeyPanelLeft1 = (CheckBox) view.findViewById(R.id.chkLRKeyPanelLeft1);
        this.chkLRKeyPanelLeft2 = (CheckBox) view.findViewById(R.id.chkLRKeyPanelLeft2);
        this.chkLRKeyPanelLeft3 = (CheckBox) view.findViewById(R.id.chkLRKeyPanelLeft3);
        this.chkLRKeyPanelLeft4 = (CheckBox) view.findViewById(R.id.chkLRKeyPanelLeft4);
        CheckBox checkBox9 = (CheckBox) view.findViewById(R.id.chkLRKeyPanelLeft5);
        this.chkLRKeyPanelLeft5 = checkBox9;
        if (!(this.chkLRKeyPanelLeft0 == null || this.chkLRKeyPanelLeft1 == null || this.chkLRKeyPanelLeft2 == null || this.chkLRKeyPanelLeft3 == null || this.chkLRKeyPanelLeft4 == null || checkBox9 == null)) {
            ArrayList arrayList6 = new ArrayList();
            this.chkLRKeyPanelLeft = arrayList6;
            arrayList6.add(this.chkLRKeyPanelLeft0);
            this.chkLRKeyPanelLeft.add(this.chkLRKeyPanelLeft1);
            this.chkLRKeyPanelLeft.add(this.chkLRKeyPanelLeft2);
            this.chkLRKeyPanelLeft.add(this.chkLRKeyPanelLeft3);
            this.chkLRKeyPanelLeft.add(this.chkLRKeyPanelLeft4);
            this.chkLRKeyPanelLeft.add(this.chkLRKeyPanelLeft5);
            initMultiSettings(SysProviderOpt.KSW_LANDROVER_KEY_PANEL_LEFT_INDEX, this.chkLRKeyPanelLeft, 0);
        }
        this.chkLRKeyPanelRight0 = (CheckBox) view.findViewById(R.id.chkLRKeyPanelRight0);
        this.chkLRKeyPanelRight1 = (CheckBox) view.findViewById(R.id.chkLRKeyPanelRight1);
        this.chkLRKeyPanelRight2 = (CheckBox) view.findViewById(R.id.chkLRKeyPanelRight2);
        this.chkLRKeyPanelRight3 = (CheckBox) view.findViewById(R.id.chkLRKeyPanelRight3);
        CheckBox checkBox10 = (CheckBox) view.findViewById(R.id.chkLRKeyPanelRight4);
        this.chkLRKeyPanelRight4 = checkBox10;
        if (!(this.chkLRKeyPanelRight0 == null || this.chkLRKeyPanelRight1 == null || this.chkLRKeyPanelRight2 == null || this.chkLRKeyPanelRight3 == null || checkBox10 == null)) {
            ArrayList arrayList7 = new ArrayList();
            this.chkLRKeyPanelRight = arrayList7;
            arrayList7.add(this.chkLRKeyPanelRight0);
            this.chkLRKeyPanelRight.add(this.chkLRKeyPanelRight1);
            this.chkLRKeyPanelRight.add(this.chkLRKeyPanelRight2);
            this.chkLRKeyPanelRight.add(this.chkLRKeyPanelRight3);
            this.chkLRKeyPanelRight.add(this.chkLRKeyPanelRight4);
            initMultiSettings(SysProviderOpt.KSW_LANDROVER_KEY_PANEL_RIGHT_INDEX, this.chkLRKeyPanelRight, 0);
        }
        CheckBox checkBox11 = (CheckBox) view.findViewById(R.id.chkAirControl);
        this.chkLexusAirControl = checkBox11;
        if (checkBox11 != null) {
            initSingleSettings(SysProviderOpt.KSW_LEXUS_AIR_CONTROL, checkBox11, false);
        }
        CheckBox checkBox12 = (CheckBox) view.findViewById(R.id.chkOriginFM);
        this.chkLexusOriginFM = checkBox12;
        if (checkBox12 != null) {
            initSingleSettings(SysProviderOpt.KSW_LEXUS_ORIGIN_FM, checkBox12, false);
        }
    }

    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.viewAirControl /*2131231228*/:
                refreshSingleSettings(SysProviderOpt.KSW_LEXUS_AIR_CONTROL, this.chkLexusAirControl);
                return;
            case R.id.viewBenzAux /*2131231232*/:
                refreshSingleSettings(SysProviderOpt.KSW_AUX_ACTIVATION_FUNCTION_INDEX, this.chkBenzAux);
                kesaiwei_sendBroadcast(35);
                return;
            case R.id.viewOriginFM /*2131231311*/:
                refreshSingleSettings(SysProviderOpt.KSW_LEXUS_ORIGIN_FM, this.chkLexusOriginFM);
                return;
            case R.id.viewShowAir /*2131231324*/:
                refreshSingleSettings(SysProviderOpt.KSW_SHOW_AIR, this.chkShowAir);
                return;
            default:
                switch (id) {
                    case R.id.viewControl0 /*2131231244*/:
                        refreshMultiSettings(SysProviderOpt.SYS_BENZ_CONTROL_INDEX, this.controlChkBoxs, 0);
                        return;
                    case R.id.viewControl1 /*2131231245*/:
                        refreshMultiSettings(SysProviderOpt.SYS_BENZ_CONTROL_INDEX, this.controlChkBoxs, 1);
                        return;
                    case R.id.viewControl2 /*2131231246*/:
                        refreshMultiSettings(SysProviderOpt.SYS_BENZ_CONTROL_INDEX, this.controlChkBoxs, 2);
                        return;
                    case R.id.viewControl3 /*2131231247*/:
                        refreshMultiSettings(SysProviderOpt.SYS_BENZ_CONTROL_INDEX, this.controlChkBoxs, 3);
                        return;
                    default:
                        switch (id) {
                            case R.id.viewHostBsCapacitive /*2131231263*/:
                                refreshMultiSettings(SysProviderOpt.KSW_LANDROVER_HOST_INDEX, this.chkLRHost, 0);
                                kesaiwei_sendBroadcast(67);
                                CheckBox checkBox = this.chkHostBs;
                                if (checkBox != null) {
                                    checkBox.setChecked(true);
                                    return;
                                }
                                return;
                            case R.id.viewHostBsResistance1 /*2131231264*/:
                                refreshMultiSettings(SysProviderOpt.KSW_LANDROVER_HOST_INDEX, this.chkLRHost, 1);
                                kesaiwei_sendBroadcast(67);
                                CheckBox checkBox2 = this.chkHostBs;
                                if (checkBox2 != null) {
                                    checkBox2.setChecked(true);
                                    return;
                                }
                                return;
                            case R.id.viewHostBsResistance2 /*2131231265*/:
                                refreshMultiSettings(SysProviderOpt.KSW_LANDROVER_HOST_INDEX, this.chkLRHost, 2);
                                kesaiwei_sendBroadcast(67);
                                CheckBox checkBox3 = this.chkHostBs;
                                if (checkBox3 != null) {
                                    checkBox3.setChecked(true);
                                    return;
                                }
                                return;
                            case R.id.viewHostHm /*2131231266*/:
                                refreshMultiSettings(SysProviderOpt.KSW_LANDROVER_HOST_INDEX, this.chkLRHost, 3);
                                kesaiwei_sendBroadcast(67);
                                CheckBox checkBox4 = this.chkHostBs;
                                if (checkBox4 != null) {
                                    checkBox4.setChecked(false);
                                    return;
                                }
                                return;
                            default:
                                switch (id) {
                                    case R.id.viewLRKeyPanelLeft0 /*2131231270*/:
                                        refreshMultiSettings(SysProviderOpt.KSW_LANDROVER_KEY_PANEL_LEFT_INDEX, this.chkLRKeyPanelLeft, 0);
                                        kesaiwei_sendBroadcast(67);
                                        return;
                                    case R.id.viewLRKeyPanelLeft1 /*2131231271*/:
                                        refreshMultiSettings(SysProviderOpt.KSW_LANDROVER_KEY_PANEL_LEFT_INDEX, this.chkLRKeyPanelLeft, 1);
                                        kesaiwei_sendBroadcast(67);
                                        return;
                                    case R.id.viewLRKeyPanelLeft2 /*2131231272*/:
                                        refreshMultiSettings(SysProviderOpt.KSW_LANDROVER_KEY_PANEL_LEFT_INDEX, this.chkLRKeyPanelLeft, 2);
                                        kesaiwei_sendBroadcast(67);
                                        return;
                                    case R.id.viewLRKeyPanelLeft3 /*2131231273*/:
                                        refreshMultiSettings(SysProviderOpt.KSW_LANDROVER_KEY_PANEL_LEFT_INDEX, this.chkLRKeyPanelLeft, 3);
                                        kesaiwei_sendBroadcast(67);
                                        return;
                                    case R.id.viewLRKeyPanelLeft4 /*2131231274*/:
                                        refreshMultiSettings(SysProviderOpt.KSW_LANDROVER_KEY_PANEL_LEFT_INDEX, this.chkLRKeyPanelLeft, 4);
                                        kesaiwei_sendBroadcast(67);
                                        return;
                                    case R.id.viewLRKeyPanelLeft5 /*2131231275*/:
                                        refreshMultiSettings(SysProviderOpt.KSW_LANDROVER_KEY_PANEL_LEFT_INDEX, this.chkLRKeyPanelLeft, 5);
                                        kesaiwei_sendBroadcast(67);
                                        return;
                                    default:
                                        switch (id) {
                                            case R.id.viewLRKeyPanelRight0 /*2131231277*/:
                                                refreshMultiSettings(SysProviderOpt.KSW_LANDROVER_KEY_PANEL_RIGHT_INDEX, this.chkLRKeyPanelRight, 0);
                                                kesaiwei_sendBroadcast(67);
                                                return;
                                            case R.id.viewLRKeyPanelRight1 /*2131231278*/:
                                                refreshMultiSettings(SysProviderOpt.KSW_LANDROVER_KEY_PANEL_RIGHT_INDEX, this.chkLRKeyPanelRight, 1);
                                                kesaiwei_sendBroadcast(67);
                                                return;
                                            case R.id.viewLRKeyPanelRight2 /*2131231279*/:
                                                refreshMultiSettings(SysProviderOpt.KSW_LANDROVER_KEY_PANEL_RIGHT_INDEX, this.chkLRKeyPanelRight, 2);
                                                kesaiwei_sendBroadcast(67);
                                                return;
                                            case R.id.viewLRKeyPanelRight3 /*2131231280*/:
                                                refreshMultiSettings(SysProviderOpt.KSW_LANDROVER_KEY_PANEL_RIGHT_INDEX, this.chkLRKeyPanelRight, 3);
                                                kesaiwei_sendBroadcast(67);
                                                return;
                                            case R.id.viewLRKeyPanelRight4 /*2131231281*/:
                                                refreshMultiSettings(SysProviderOpt.KSW_LANDROVER_KEY_PANEL_RIGHT_INDEX, this.chkLRKeyPanelRight, 4);
                                                kesaiwei_sendBroadcast(67);
                                                return;
                                            default:
                                                switch (id) {
                                                    case R.id.viewMainLeftCarIconA3 /*2131231287*/:
                                                        refreshMultiSettings(SysProviderOpt.AUDI_GT6_LEFT_CAR_ICON, this.gt6AudiLeftCarIconChkBoxes, 4);
                                                        return;
                                                    case R.id.viewMainLeftCarIconA4 /*2131231288*/:
                                                        refreshMultiSettings(SysProviderOpt.AUDI_GT6_LEFT_CAR_ICON, this.gt6AudiLeftCarIconChkBoxes, 1);
                                                        return;
                                                    case R.id.viewMainLeftCarIconA4L /*2131231289*/:
                                                        refreshMultiSettings(SysProviderOpt.AUDI_GT6_LEFT_CAR_ICON, this.gt6AudiLeftCarIconChkBoxes, 3);
                                                        return;
                                                    case R.id.viewMainLeftCarIconA4old /*2131231290*/:
                                                        refreshMultiSettings(SysProviderOpt.AUDI_GT6_LEFT_CAR_ICON, this.gt6AudiLeftCarIconChkBoxes, 2);
                                                        return;
                                                    case R.id.viewMainLeftCarIconA5 /*2131231291*/:
                                                        refreshMultiSettings(SysProviderOpt.AUDI_GT6_LEFT_CAR_ICON, this.gt6AudiLeftCarIconChkBoxes, 5);
                                                        return;
                                                    case R.id.viewMainLeftCarIconA6 /*2131231292*/:
                                                        refreshMultiSettings(SysProviderOpt.AUDI_GT6_LEFT_CAR_ICON, this.gt6AudiLeftCarIconChkBoxes, 6);
                                                        return;
                                                    case R.id.viewMainLeftCarIconHide /*2131231293*/:
                                                        refreshMultiSettings(SysProviderOpt.AUDI_GT6_LEFT_CAR_ICON, this.gt6AudiLeftCarIconChkBoxes, 0);
                                                        return;
                                                    case R.id.viewMainLeftCarIconQ3 /*2131231294*/:
                                                        refreshMultiSettings(SysProviderOpt.AUDI_GT6_LEFT_CAR_ICON, this.gt6AudiLeftCarIconChkBoxes, 7);
                                                        return;
                                                    case R.id.viewMainLeftCarIconQ5 /*2131231295*/:
                                                        refreshMultiSettings(SysProviderOpt.AUDI_GT6_LEFT_CAR_ICON, this.gt6AudiLeftCarIconChkBoxes, 8);
                                                        return;
                                                    case R.id.viewMainLeftCarIconQ7 /*2131231296*/:
                                                        refreshMultiSettings(SysProviderOpt.AUDI_GT6_LEFT_CAR_ICON, this.gt6AudiLeftCarIconChkBoxes, 9);
                                                        return;
                                                    case R.id.viewMainRightWidgetsDriving /*2131231297*/:
                                                        refreshMultiSettings(SysProviderOpt.AUDI_GT6_RIGHT_WIDGET, this.gt6AudiRightWidgets, 3);
                                                        return;
                                                    case R.id.viewMainRightWidgetsHide /*2131231298*/:
                                                        refreshMultiSettings(SysProviderOpt.AUDI_GT6_RIGHT_WIDGET, this.gt6AudiRightWidgets, 0);
                                                        return;
                                                    case R.id.viewMainRightWidgetsInstrumentCluster /*2131231299*/:
                                                        refreshMultiSettings(SysProviderOpt.AUDI_GT6_RIGHT_WIDGET, this.gt6AudiRightWidgets, 5);
                                                        return;
                                                    case R.id.viewMainRightWidgetsMusic /*2131231300*/:
                                                        refreshMultiSettings(SysProviderOpt.AUDI_GT6_RIGHT_WIDGET, this.gt6AudiRightWidgets, 4);
                                                        return;
                                                    case R.id.viewMainRightWidgetsWeather /*2131231301*/:
                                                        refreshMultiSettings(SysProviderOpt.AUDI_GT6_RIGHT_WIDGET, this.gt6AudiRightWidgets, 2);
                                                        return;
                                                    case R.id.viewMainRightWidgetsWeatherAndGaode /*2131231302*/:
                                                        refreshMultiSettings(SysProviderOpt.AUDI_GT6_RIGHT_WIDGET, this.gt6AudiRightWidgets, 1);
                                                        return;
                                                    default:
                                                        switch (id) {
                                                            case R.id.viewWheelControlADC /*2131231338*/:
                                                                refreshMultiSettings(SysProviderOpt.KSW_LANDRVOER_WHEEL_CONTROL_TYPE, this.chkLRWheelControlType, 1);
                                                                kesaiwei_sendBroadcast(67);
                                                                return;
                                                            case R.id.viewWheelControlLin /*2131231339*/:
                                                                refreshMultiSettings(SysProviderOpt.KSW_LANDRVOER_WHEEL_CONTROL_TYPE, this.chkLRWheelControlType, 0);
                                                                kesaiwei_sendBroadcast(67);
                                                                return;
                                                            default:
                                                                return;
                                                        }
                                                }
                                        }
                                }
                        }
                }
        }
    }
}
