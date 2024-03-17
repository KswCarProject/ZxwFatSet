package com.szchoiceway.zxwlib.view;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import com.szchoiceway.zxwlib.R;
import com.szchoiceway.zxwlib.utils.MultipleUtils;

public class DayNightView2 extends View {
    private Drawable mDayDrawable = null;
    private Drawable mDrawable = null;
    boolean mInNightMode = false;
    private int mMeasureHeight = 0;
    private int mMeasureWidth = 0;
    private boolean mNeedMirror = false;
    private Drawable mNightDrawable = null;

    public DayNightView2(Context context) {
        super(context);
    }

    public DayNightView2(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context, attributeSet);
    }

    public DayNightView2(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context, attributeSet);
    }

    private void init(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.DayNightView);
        this.mDayDrawable = obtainStyledAttributes.getDrawable(R.styleable.DayNightView_backgroundDay);
        this.mNightDrawable = obtainStyledAttributes.getDrawable(R.styleable.DayNightView_backgroundNight);
        this.mInNightMode = MultipleUtils.curUIModeNight(context.getResources().getConfiguration());
        this.mNeedMirror = obtainStyledAttributes.getBoolean(R.styleable.DayNightView_backgroundMirror, this.mNeedMirror);
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

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        this.mMeasureWidth = View.MeasureSpec.getSize(i);
        int size = View.MeasureSpec.getSize(i2);
        this.mMeasureHeight = size;
        if (this.mMeasureWidth > 0 && size > 0) {
            setBackground(this.mInNightMode ? this.mNightDrawable : this.mDayDrawable);
        }
    }

    public void setBackground(Drawable drawable) {
        this.mDrawable = drawable;
        drawable.setBounds(0, 0, this.mMeasureWidth, this.mMeasureHeight);
        invalidate();
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        canvas.save();
        if (isLayoutRtl() && this.mNeedMirror) {
            canvas.scale(-1.0f, 1.0f, (float) (getWidth() / 2), 0.0f);
        }
        Drawable drawable = this.mDrawable;
        if (drawable != null) {
            drawable.draw(canvas);
        }
        canvas.restore();
    }
}
