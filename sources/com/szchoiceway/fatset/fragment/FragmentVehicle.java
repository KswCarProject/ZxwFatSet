package com.szchoiceway.fatset.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;
import com.szchoiceway.eventcenter.EventUtils;
import com.szchoiceway.fatset.R;
import com.szchoiceway.fatset.SysProviderOpt;
import com.szchoiceway.zxwlib.bean.Customer;
import java.util.ArrayList;
import java.util.List;

public class FragmentVehicle extends FragmentBase implements View.OnClickListener {
    private static final String TAG = "FragmentVehicle";
    List<CheckBox> backcarCheckBoxs;
    SeekBar backcarTimeSeekbar;
    TextView backcarTimeValue;
    List<CheckBox> cameraCheckBoxs;
    List<CheckBox> cameraChkBoxs;
    CheckBox chkCamera0;
    CheckBox chkCamera1;
    CheckBox chkCamera2;
    CheckBox chkCameraCVBS;
    CheckBox chkCameraVGA;
    CheckBox chkCarWithoutScreen;
    CheckBox chkCustomizeBackcarQuit;
    CheckBox chkFourDoors;
    CheckBox chkGear1;
    CheckBox chkGear2;
    CheckBox chkGear3;
    CheckBox chkHideDoors;
    CheckBox chkKnobA;
    CheckBox chkKnobB;
    CheckBox chkLight0;
    CheckBox chkLight1;
    CheckBox chkLvdsJeida;
    CheckBox chkLvdsVesa;
    CheckBox chkMic0;
    CheckBox chkMic1;
    CheckBox chkMicInstall;
    CheckBox chkMicOriginal;
    CheckBox chkOriginalBackcarQuit;
    CheckBox chkSeatLeft;
    CheckBox chkSeatRight;
    CheckBox chkSpeed0;
    CheckBox chkSpeed1;
    CheckBox chkTrack1;
    CheckBox chkTrack2;
    CheckBox chkTwoDoors;
    List<CheckBox> doorChkBoxs;
    List<CheckBox> gearCheckBoxs;
    List<CheckBox> knobCheckBoxs;
    List<CheckBox> lightChkBoxs;
    List<CheckBox> mLvdsChkBoxs;
    int mPosition = 0;
    List<CheckBox> micChkBoxs;
    List<CheckBox> micTypeChkBoxes;
    List<CheckBox> seatChkBoxs;
    List<CheckBox> speedChkBoxs;
    List<CheckBox> trackCheckBoxs;
    View viewCustomizeSeekbar;
    View viewMic;
    View viewMicType;

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_vehicle, (ViewGroup) null);
        if (Customer.isSmallResolution(getContext())) {
            inflate = layoutInflater.inflate(R.layout.small_fragment_vehicle, (ViewGroup) null);
        }
        this.mainView = inflate;
        return inflate;
    }

    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        CheckBox checkBox = (CheckBox) view.findViewById(R.id.chkCarWithoutScreen);
        this.chkCarWithoutScreen = checkBox;
        if (checkBox != null) {
            initSingleSettings(SysProviderOpt.KSW_ORIGINAL_CAR_VIDEO_DISPLAY, checkBox, false);
        }
        this.chkKnobA = (CheckBox) view.findViewById(R.id.chkKnobA);
        CheckBox checkBox2 = (CheckBox) view.findViewById(R.id.chkKnobB);
        this.chkKnobB = checkBox2;
        if (!(this.chkKnobA == null || checkBox2 == null)) {
            ArrayList arrayList = new ArrayList();
            this.knobCheckBoxs = arrayList;
            arrayList.add(this.chkKnobA);
            this.knobCheckBoxs.add(this.chkKnobB);
            initMultiSettings(SysProviderOpt.KSW_CCC_IDRIVE_TYPE, this.knobCheckBoxs, 0);
        }
        this.chkCameraCVBS = (CheckBox) view.findViewById(R.id.chkCameraCVBS);
        CheckBox checkBox3 = (CheckBox) view.findViewById(R.id.chkCameraVGA);
        this.chkCameraVGA = checkBox3;
        if (!(this.chkCameraCVBS == null || checkBox3 == null)) {
            ArrayList arrayList2 = new ArrayList();
            this.cameraCheckBoxs = arrayList2;
            arrayList2.add(this.chkCameraCVBS);
            this.cameraCheckBoxs.add(this.chkCameraVGA);
            initMultiSettings(SysProviderOpt.KSW_360_CAMERA_TYPE_INDEX, this.cameraCheckBoxs, 0);
        }
        this.chkGear1 = (CheckBox) view.findViewById(R.id.chkGear1);
        this.chkGear2 = (CheckBox) view.findViewById(R.id.chkGear2);
        CheckBox checkBox4 = (CheckBox) view.findViewById(R.id.chkGear3);
        this.chkGear3 = checkBox4;
        if (!(this.chkGear1 == null || this.chkGear2 == null || checkBox4 == null)) {
            ArrayList arrayList3 = new ArrayList();
            this.gearCheckBoxs = arrayList3;
            arrayList3.add(this.chkGear1);
            this.gearCheckBoxs.add(this.chkGear2);
            this.gearCheckBoxs.add(this.chkGear3);
            initMultiSettings(SysProviderOpt.KSW_HANDSET_AUTOMATIC_SET_INDEX, this.gearCheckBoxs, 0);
        }
        this.chkTrack1 = (CheckBox) view.findViewById(R.id.chkTrack1);
        CheckBox checkBox5 = (CheckBox) view.findViewById(R.id.chkTrack2);
        this.chkTrack2 = checkBox5;
        if (!(this.chkTrack1 == null || checkBox5 == null)) {
            ArrayList arrayList4 = new ArrayList();
            this.trackCheckBoxs = arrayList4;
            arrayList4.add(this.chkTrack1);
            this.trackCheckBoxs.add(this.chkTrack2);
            initMultiSettings(SysProviderOpt.KSW_WHELLTRACK_INDEX, this.trackCheckBoxs, 0);
        }
        this.chkOriginalBackcarQuit = (CheckBox) view.findViewById(R.id.chkOriginalBackcarQuit);
        this.chkCustomizeBackcarQuit = (CheckBox) view.findViewById(R.id.chkCustomizeBackcarQuit);
        this.backcarTimeValue = (TextView) view.findViewById(R.id.backcarTimeValue);
        this.backcarTimeSeekbar = (SeekBar) view.findViewById(R.id.backcarTimeSeekbar);
        this.mPosition = this.mProvider.getRecordInteger(SysProviderOpt.KSW_REVERSE_EXIT_TIME_CUSTOMIZE, 0);
        TextView textView = this.backcarTimeValue;
        if (textView != null) {
            textView.setText("" + this.mPosition);
        }
        SeekBar seekBar = this.backcarTimeSeekbar;
        if (seekBar != null) {
            seekBar.setProgress(this.mPosition);
            this.backcarTimeSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                    if (z) {
                        FragmentVehicle.this.mPosition = i;
                        if (FragmentVehicle.this.backcarTimeValue != null) {
                            FragmentVehicle.this.backcarTimeValue.setText("" + FragmentVehicle.this.mPosition);
                        }
                    }
                }

                public void onStopTrackingTouch(SeekBar seekBar) {
                    FragmentVehicle.this.mProvider.updateRecord(SysProviderOpt.KSW_REVERSE_EXIT_TIME_CUSTOMIZE, FragmentVehicle.this.mPosition + "");
                    FragmentVehicle.this.kesaiwei_sendBroadcast(53);
                }
            });
        }
        this.viewCustomizeSeekbar = view.findViewById(R.id.viewCustomizeSeekbar);
        int i = 8;
        if (!(this.chkOriginalBackcarQuit == null || this.chkCustomizeBackcarQuit == null)) {
            ArrayList arrayList5 = new ArrayList();
            this.backcarCheckBoxs = arrayList5;
            arrayList5.add(this.chkOriginalBackcarQuit);
            this.backcarCheckBoxs.add(this.chkCustomizeBackcarQuit);
            initMultiSettings(SysProviderOpt.KSW_REVERSE_EXIT_TIME_INDEX, this.backcarCheckBoxs, 0);
            if (this.chkCustomizeBackcarQuit.isChecked()) {
                View view2 = this.viewCustomizeSeekbar;
                if (view2 != null) {
                    view2.setVisibility(0);
                }
            } else {
                View view3 = this.viewCustomizeSeekbar;
                if (view3 != null) {
                    view3.setVisibility(8);
                }
            }
        }
        this.chkSeatLeft = (CheckBox) view.findViewById(R.id.chkSeatLeft);
        CheckBox checkBox6 = (CheckBox) view.findViewById(R.id.chkSeatRight);
        this.chkSeatRight = checkBox6;
        if (!(this.chkSeatLeft == null || checkBox6 == null)) {
            ArrayList arrayList6 = new ArrayList();
            this.seatChkBoxs = arrayList6;
            arrayList6.add(this.chkSeatLeft);
            this.seatChkBoxs.add(this.chkSeatRight);
            initMultiSettings(SysProviderOpt.SYS_DOOR_SET_VALUE_INDEX_KEY, this.seatChkBoxs, 0);
        }
        this.chkFourDoors = (CheckBox) view.findViewById(R.id.chkFourDoors);
        this.chkTwoDoors = (CheckBox) view.findViewById(R.id.chkTwoDoors);
        CheckBox checkBox7 = (CheckBox) view.findViewById(R.id.chkHideDoors);
        this.chkHideDoors = checkBox7;
        if (!(this.chkFourDoors == null || this.chkTwoDoors == null || checkBox7 == null)) {
            ArrayList arrayList7 = new ArrayList();
            this.doorChkBoxs = arrayList7;
            arrayList7.add(this.chkFourDoors);
            this.doorChkBoxs.add(this.chkTwoDoors);
            this.doorChkBoxs.add(this.chkHideDoors);
            initMultiSettings(SysProviderOpt.SYS_DOOR_DISPLAYSET_VALUE_INDEX_KEY, this.doorChkBoxs, 0);
        }
        this.chkCamera0 = (CheckBox) view.findViewById(R.id.chkCamera0);
        this.chkCamera1 = (CheckBox) view.findViewById(R.id.chkCamera1);
        CheckBox checkBox8 = (CheckBox) view.findViewById(R.id.chkCamera2);
        this.chkCamera2 = checkBox8;
        if (!(this.chkCamera0 == null || this.chkCamera1 == null || checkBox8 == null)) {
            ArrayList arrayList8 = new ArrayList();
            this.cameraChkBoxs = arrayList8;
            arrayList8.add(this.chkCamera0);
            this.cameraChkBoxs.add(this.chkCamera1);
            this.cameraChkBoxs.add(this.chkCamera2);
            initMultiSettings(SysProviderOpt.KSW_BOOT_360_CAMERA_INDEX, this.cameraChkBoxs, 0);
        }
        this.chkLight0 = (CheckBox) view.findViewById(R.id.chkLight0);
        CheckBox checkBox9 = (CheckBox) view.findViewById(R.id.chkLight1);
        this.chkLight1 = checkBox9;
        if (!(this.chkLight0 == null || checkBox9 == null)) {
            ArrayList arrayList9 = new ArrayList();
            this.lightChkBoxs = arrayList9;
            arrayList9.add(this.chkLight0);
            this.lightChkBoxs.add(this.chkLight1);
            initMultiSettings(SysProviderOpt.KSW_TURN_SIGNAL_CONTROL, this.lightChkBoxs, 0);
        }
        this.chkSpeed0 = (CheckBox) view.findViewById(R.id.chkSpeed0);
        CheckBox checkBox10 = (CheckBox) view.findViewById(R.id.chkSpeed1);
        this.chkSpeed1 = checkBox10;
        if (!(this.chkSpeed0 == null || checkBox10 == null)) {
            ArrayList arrayList10 = new ArrayList();
            this.speedChkBoxs = arrayList10;
            arrayList10.add(this.chkSpeed0);
            this.speedChkBoxs.add(this.chkSpeed1);
            initMultiSettings(SysProviderOpt.KSW_SPEED_TYPE_SELECTION, this.speedChkBoxs, 0);
        }
        View findViewById = view.findViewById(R.id.viewMicType);
        this.viewMicType = findViewById;
        if (findViewById != null) {
            if ("ALS_6208".equals(this.client)) {
                i = 0;
            }
            findViewById.setVisibility(i);
        }
        this.chkMic0 = (CheckBox) view.findViewById(R.id.chkMic0);
        CheckBox checkBox11 = (CheckBox) view.findViewById(R.id.chkMic1);
        this.chkMic1 = checkBox11;
        if (!(this.chkMic0 == null || checkBox11 == null)) {
            ArrayList arrayList11 = new ArrayList();
            this.micChkBoxs = arrayList11;
            arrayList11.add(this.chkMic0);
            this.micChkBoxs.add(this.chkMic1);
            initMultiSettings(SysProviderOpt.KSW_EXTERNAL_INTERNAL_MIC_SELECTION, this.micChkBoxs, 1);
        }
        this.chkMicOriginal = (CheckBox) view.findViewById(R.id.chkMicOriginal);
        CheckBox checkBox12 = (CheckBox) view.findViewById(R.id.chkMicInstall);
        this.chkMicInstall = checkBox12;
        if (!(this.chkMicOriginal == null || checkBox12 == null)) {
            ArrayList arrayList12 = new ArrayList();
            this.micTypeChkBoxes = arrayList12;
            arrayList12.add(this.chkMicOriginal);
            this.micTypeChkBoxes.add(this.chkMicInstall);
            initMultiSettings(SysProviderOpt.KSW_ORIGINAL_INSTALL_MIC_SELECTION, this.micTypeChkBoxes, 1);
        }
        this.chkLvdsVesa = (CheckBox) view.findViewById(R.id.chkLvdsVesa);
        CheckBox checkBox13 = (CheckBox) view.findViewById(R.id.chkLvdsJeida);
        this.chkLvdsJeida = checkBox13;
        if (!(this.chkLvdsVesa == null || checkBox13 == null)) {
            ArrayList arrayList13 = new ArrayList();
            this.mLvdsChkBoxs = arrayList13;
            arrayList13.add(this.chkLvdsVesa);
            this.mLvdsChkBoxs.add(this.chkLvdsJeida);
            initMultiSettings(SysProviderOpt.KSW_SPLITTING_MACHINE_LVDS_MODE, this.mLvdsChkBoxs, 0);
        }
        int[] iArr = {R.id.viewCarWithoutScreen, R.id.viewKnobA, R.id.viewKnobB, R.id.viewCameraCVBS, R.id.viewCameraVGA, R.id.viewGear1, R.id.viewGear2, R.id.viewGear3, R.id.viewTrack1, R.id.viewTrack2, R.id.viewOriginalBackcar, R.id.viewCustomizeBackcarQuit, R.id.viewSeatLeft, R.id.viewSeatRight, R.id.viewFourDoors, R.id.viewTwoDoors, R.id.viewHideDoors, R.id.viewCamera0, R.id.viewCamera1, R.id.viewCamera2, R.id.viewLight0, R.id.viewLight1, R.id.viewMic0, R.id.viewMic1, R.id.viewSpeed0, R.id.viewSpeed1, R.id.viewMicOriginal, R.id.viewMicInstall, R.id.viewLvdsVesa, R.id.viewLvdsJeida};
        for (int i2 = 0; i2 < 30; i2++) {
            View findViewById2 = view.findViewById(iArr[i2]);
            if (findViewById2 != null) {
                findViewById2.setOnClickListener(this);
            }
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.viewCamera0:
                refreshMultiSettings(SysProviderOpt.KSW_BOOT_360_CAMERA_INDEX, this.cameraChkBoxs, 0);
                kesaiwei_sendBroadcast(55);
                return;
            case R.id.viewCamera1:
                refreshMultiSettings(SysProviderOpt.KSW_BOOT_360_CAMERA_INDEX, this.cameraChkBoxs, 1);
                kesaiwei_sendBroadcast(55);
                return;
            case R.id.viewCamera2:
                refreshMultiSettings(SysProviderOpt.KSW_BOOT_360_CAMERA_INDEX, this.cameraChkBoxs, 2);
                kesaiwei_sendBroadcast(55);
                return;
            case R.id.viewCameraCVBS:
                refreshMultiSettings(SysProviderOpt.KSW_360_CAMERA_TYPE_INDEX, this.cameraCheckBoxs, 0);
                kesaiwei_sendBroadcast(37);
                return;
            case R.id.viewCameraVGA:
                refreshMultiSettings(SysProviderOpt.KSW_360_CAMERA_TYPE_INDEX, this.cameraCheckBoxs, 1);
                kesaiwei_sendBroadcast(37);
                return;
            case R.id.viewCarWithoutScreen:
                refreshSingleSettings(SysProviderOpt.KSW_ORIGINAL_CAR_VIDEO_DISPLAY, this.chkCarWithoutScreen);
                kesaiwei_sendBroadcast(12);
                return;
            case R.id.viewCustomizeBackcarQuit:
                refreshMultiSettings(SysProviderOpt.KSW_REVERSE_EXIT_TIME_INDEX, this.backcarCheckBoxs, 1);
                kesaiwei_sendBroadcast(53);
                View view2 = this.viewCustomizeSeekbar;
                if (view2 != null) {
                    view2.setVisibility(0);
                    return;
                }
                return;
            case R.id.viewFourDoors:
                refreshMultiSettings(SysProviderOpt.SYS_DOOR_DISPLAYSET_VALUE_INDEX_KEY, this.doorChkBoxs, 0);
                return;
            case R.id.viewGear1:
                refreshMultiSettings(SysProviderOpt.KSW_HANDSET_AUTOMATIC_SET_INDEX, this.gearCheckBoxs, 0);
                kesaiwei_sendBroadcast(31);
                return;
            case R.id.viewGear2:
                refreshMultiSettings(SysProviderOpt.KSW_HANDSET_AUTOMATIC_SET_INDEX, this.gearCheckBoxs, 1);
                kesaiwei_sendBroadcast(31);
                return;
            case R.id.viewGear3:
                refreshMultiSettings(SysProviderOpt.KSW_HANDSET_AUTOMATIC_SET_INDEX, this.gearCheckBoxs, 2);
                kesaiwei_sendBroadcast(31);
                return;
            case R.id.viewHideDoors:
                refreshMultiSettings(SysProviderOpt.SYS_DOOR_DISPLAYSET_VALUE_INDEX_KEY, this.doorChkBoxs, 2);
                return;
            case R.id.viewKnobA:
                refreshMultiSettings(SysProviderOpt.KSW_CCC_IDRIVE_TYPE, this.knobCheckBoxs, 0);
                kesaiwei_sendBroadcast(23);
                return;
            case R.id.viewKnobB:
                refreshMultiSettings(SysProviderOpt.KSW_CCC_IDRIVE_TYPE, this.knobCheckBoxs, 1);
                kesaiwei_sendBroadcast(23);
                return;
            case R.id.viewLight0:
                refreshMultiSettings(SysProviderOpt.KSW_TURN_SIGNAL_CONTROL, this.lightChkBoxs, 0);
                kesaiwei_sendBroadcast(56);
                return;
            case R.id.viewLight1:
                refreshMultiSettings(SysProviderOpt.KSW_TURN_SIGNAL_CONTROL, this.lightChkBoxs, 1);
                kesaiwei_sendBroadcast(56);
                return;
            case R.id.viewLvdsJeida:
                refreshMultiSettings(SysProviderOpt.KSW_SPLITTING_MACHINE_LVDS_MODE, this.mLvdsChkBoxs, 1);
                kesaiwei_sendBroadcast(68);
                return;
            case R.id.viewLvdsVesa:
                refreshMultiSettings(SysProviderOpt.KSW_SPLITTING_MACHINE_LVDS_MODE, this.mLvdsChkBoxs, 0);
                kesaiwei_sendBroadcast(68);
                return;
            case R.id.viewMic0:
                refreshMultiSettings(SysProviderOpt.KSW_EXTERNAL_INTERNAL_MIC_SELECTION, this.micChkBoxs, 0);
                kesaiwei_sendBroadcast(57);
                return;
            case R.id.viewMic1:
                refreshMultiSettings(SysProviderOpt.KSW_EXTERNAL_INTERNAL_MIC_SELECTION, this.micChkBoxs, 1);
                kesaiwei_sendBroadcast(57);
                return;
            case R.id.viewMicInstall:
                refreshMultiSettings(SysProviderOpt.KSW_ORIGINAL_INSTALL_MIC_SELECTION, this.micTypeChkBoxes, 1);
                if (getActivity() != null) {
                    getActivity().sendBroadcast(new Intent(EventUtils.ZXW_ACTION_ORIGINAL_INSTALL_MIC_SWITCH));
                    return;
                }
                return;
            case R.id.viewMicOriginal:
                refreshMultiSettings(SysProviderOpt.KSW_ORIGINAL_INSTALL_MIC_SELECTION, this.micTypeChkBoxes, 0);
                if (getActivity() != null) {
                    getActivity().sendBroadcast(new Intent(EventUtils.ZXW_ACTION_ORIGINAL_INSTALL_MIC_SWITCH));
                    return;
                }
                return;
            case R.id.viewOriginalBackcar:
                refreshMultiSettings(SysProviderOpt.KSW_REVERSE_EXIT_TIME_INDEX, this.backcarCheckBoxs, 0);
                kesaiwei_sendBroadcast(53);
                View view3 = this.viewCustomizeSeekbar;
                if (view3 != null) {
                    view3.setVisibility(8);
                    return;
                }
                return;
            case R.id.viewSeatLeft:
                refreshMultiSettings(SysProviderOpt.SYS_DOOR_SET_VALUE_INDEX_KEY, this.seatChkBoxs, 0);
                return;
            case R.id.viewSeatRight:
                refreshMultiSettings(SysProviderOpt.SYS_DOOR_SET_VALUE_INDEX_KEY, this.seatChkBoxs, 1);
                return;
            case R.id.viewSpeed0:
                refreshMultiSettings(SysProviderOpt.KSW_SPEED_TYPE_SELECTION, this.speedChkBoxs, 0);
                kesaiwei_sendBroadcast(64);
                return;
            case R.id.viewSpeed1:
                refreshMultiSettings(SysProviderOpt.KSW_SPEED_TYPE_SELECTION, this.speedChkBoxs, 1);
                kesaiwei_sendBroadcast(64);
                return;
            case R.id.viewTrack1:
                refreshMultiSettings(SysProviderOpt.KSW_WHELLTRACK_INDEX, this.trackCheckBoxs, 0);
                kesaiwei_sendBroadcast(52);
                return;
            case R.id.viewTrack2:
                refreshMultiSettings(SysProviderOpt.KSW_WHELLTRACK_INDEX, this.trackCheckBoxs, 1);
                kesaiwei_sendBroadcast(52);
                return;
            case R.id.viewTwoDoors:
                refreshMultiSettings(SysProviderOpt.SYS_DOOR_DISPLAYSET_VALUE_INDEX_KEY, this.doorChkBoxs, 1);
                return;
            default:
                return;
        }
    }
}
