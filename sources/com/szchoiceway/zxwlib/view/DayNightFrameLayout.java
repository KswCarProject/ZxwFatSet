package com.szchoiceway.zxwlib.view;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.ViewOverlay;
import android.widget.FrameLayout;
import com.szchoiceway.zxwlib.R;
import com.szchoiceway.zxwlib.utils.MultipleUtils;

public class DayNightFrameLayout extends FrameLayout {
    private Drawable mDayDrawable = null;
    boolean mInNightMode = false;
    private Drawable mNightDrawable = null;

    /* access modifiers changed from: protected */
    public /* bridge */ /* synthetic */ ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return super.generateDefaultLayoutParams();
    }

    public /* bridge */ /* synthetic */ ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return super.generateLayoutParams(attributeSet);
    }

    public /* bridge */ /* synthetic */ ViewOverlay getOverlay() {
        return super.getOverlay();
    }

    public DayNightFrameLayout(Context context) {
        super(context);
    }

    public DayNightFrameLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context, attributeSet);
    }

    public DayNightFrameLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context, attributeSet);
    }

    private void init(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.DayNightFrameLayout);
        this.mDayDrawable = obtainStyledAttributes.getDrawable(R.styleable.DayNightFrameLayout_frameLayoutBackgroundDay);
        this.mNightDrawable = obtainStyledAttributes.getDrawable(R.styleable.DayNightFrameLayout_frameLayoutBackgroundNight);
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
            invalidate();
        }
    }
}
