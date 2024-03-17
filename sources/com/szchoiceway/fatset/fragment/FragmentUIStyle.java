package com.szchoiceway.fatset.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.szchoiceway.fatset.R;
import com.szchoiceway.fatset.SysProviderOpt;

public class FragmentUIStyle extends FragmentBase {
    private static final String TAG = "FragmentUISelect";
    private int uiStyleIndex = 1;

    public void onAttach(Context context) {
        super.onAttach(context);
        this.uiStyleIndex = this.mProvider.getRecordInteger(SysProviderOpt.KSW_LANDROVER_COPILOT_UI__STYLE_INDEX, 1);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(R.layout.fragment_ui_style, (ViewGroup) null);
    }
}
