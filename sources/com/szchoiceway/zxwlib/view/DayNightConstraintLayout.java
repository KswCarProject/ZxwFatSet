package com.szchoiceway.zxwlib.view;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ViewOverlay;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.szchoiceway.zxwlib.R;
import com.szchoiceway.zxwlib.utils.MultipleUtils;

public class DayNightConstraintLayout extends ConstraintLayout {
    private Drawable mDayDrawable = null;
    boolean mInNightMode = false;
    private Drawable mNightDrawable = null;

    public /* bridge */ /* synthetic */ ViewOverlay getOverlay() {
        return super.getOverlay();
    }

    public DayNightConstraintLayout(Context context) {
        super(context);
    }

    public DayNightConstraintLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context, attributeSet);
    }

    public DayNightConstraintLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context, attributeSet);
    }

    private void init(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.DayNightConstraintLayout);
        this.mDayDrawable = obtainStyledAttributes.getDrawable(R.styleable.DayNightConstraintLayout_constraintLayoutBackgroundDay);
        this.mNightDrawable = obtainStyledAttributes.getDrawable(R.styleable.DayNightConstraintLayout_constraintLayoutBackgroundNight);
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
