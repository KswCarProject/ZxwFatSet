package com.szchoiceway.zxwlib.focus.view;

import android.util.Log;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;

public abstract class FocusRecyclerViewAdapter extends RecyclerView.Adapter<ViewHolder> {
    public int mCurFocusPositon = -1;

    public abstract void performItemClick(int i);

    public void setCurFocusItemPosition(int i) {
        this.mCurFocusPositon = i;
        Log.i("TAG", "setCurFocusItemPosition: " + this.mCurFocusPositon);
    }

    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.onBind(i);
    }

    public static abstract class ViewHolder extends RecyclerView.ViewHolder {
        /* access modifiers changed from: protected */
        public abstract void onBind(int i);

        public ViewHolder(View view) {
            super(view);
        }
    }
}
