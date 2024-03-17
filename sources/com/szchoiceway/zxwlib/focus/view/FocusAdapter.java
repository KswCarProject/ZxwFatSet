package com.szchoiceway.zxwlib.focus.view;

import android.widget.AdapterView;
import android.widget.BaseAdapter;

public abstract class FocusAdapter extends BaseAdapter implements AdapterView.OnItemClickListener {
    public int mCurFocusPositon = -1;

    public void setCurFocusItemPosition(int i) {
        this.mCurFocusPositon = i;
    }
}
