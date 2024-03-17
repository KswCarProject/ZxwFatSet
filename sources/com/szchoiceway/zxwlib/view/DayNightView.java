package com.szchoiceway.zxwlib.view;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import com.szchoiceway.zxwlib.R;
import com.szchoiceway.zxwlib.utils.MultipleUtils;

public class DayNightView extends View {
    private Drawable mDayDrawable = null;
    boolean mInNightMode = false;
    private Drawable mNightDrawable = null;

    public DayNightView(Context context) {
        super(context);
    }

    public DayNightView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context, attributeSet);
    }

    public DayNightView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context, attributeSet);
    }

    private void init(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.DayNightView);
        this.mDayDrawable = obtainStyledAttributes.getDrawable(R.styleable.DayNightView_backgroundDay);
        this.mNightDrawable = obtainStyledAttributes.getDrawable(R.styleable.DayNightView_backgroundNight);
        boolean curUIModeNight = MultipleUtils.curUIModeNight(context.getResources().getConfiguration());
        this.mInNightMode = curUIModeNight;
        setBackground(curUIModeNight ? this.mNightDrawable : this.mDayDrawable);
        obtainStyledAttributes.recycle();
    }

    /* access modifiers changed from: protected */
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        boolean curUIModeNight = MultipleUtils.curUIModeNight(configuration);
        if (curUIModeNight != this.mInNightMode) {
            this.mInNightMode = curUIModeNight;
            setBackground(curUIModeNight ? this.mNightDrawable : this.mDayDrawable);
        }
    }

    public void reSet() {
        boolean curUIModeNight = MultipleUtils.curUIModeNight(getContext().getResources().getConfiguration());
        this.mInNightMode = curUIModeNight;
        setBackground(curUIModeNight ? this.mNightDrawable : this.mDayDrawable);
    }

    public void setDayDrawable(int i) {
        this.mDayDrawable = getResources().getDrawable(i, (Resources.Theme) null);
    }

    public void setNightDrawable(int i) {
        this.mNightDrawable = getResources().getDrawable(i, (Resources.Theme) null);
    }
}
