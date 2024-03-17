package com.szchoiceway.zxwlib.view;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import androidx.core.view.ViewCompat;
import com.szchoiceway.zxwlib.R;
import com.szchoiceway.zxwlib.utils.MultipleUtils;

public class DayNightEditText extends TextEditTextView {
    private Drawable mDayDrawable = null;
    private int mHintTextColorDay = ViewCompat.MEASURED_STATE_MASK;
    private int mHintTextColorNight = -1;
    boolean mInNightMode = false;
    private Drawable mNightDrawable = null;
    private int mTextColorDay = ViewCompat.MEASURED_STATE_MASK;
    private int mTextColorNight = -1;

    public DayNightEditText(Context context) {
        super(context);
    }

    public DayNightEditText(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context, attributeSet);
    }

    public DayNightEditText(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context, attributeSet);
    }

    private void init(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.DayNightEditText);
        this.mDayDrawable = obtainStyledAttributes.getDrawable(R.styleable.DayNightEditText_editTextBackgroundDay);
        this.mNightDrawable = obtainStyledAttributes.getDrawable(R.styleable.DayNightEditText_editTextBackgroundNight);
        this.mHintTextColorNight = obtainStyledAttributes.getColor(R.styleable.DayNightEditText_editTextHintColorNight, this.mHintTextColorNight);
        this.mHintTextColorDay = obtainStyledAttributes.getColor(R.styleable.DayNightEditText_editTextHintColorDay, this.mHintTextColorDay);
        this.mTextColorDay = obtainStyledAttributes.getColor(R.styleable.DayNightEditText_editTextColorDay, this.mTextColorDay);
        this.mTextColorNight = obtainStyledAttributes.getColor(R.styleable.DayNightEditText_editTextColorNight, this.mTextColorNight);
        boolean curUIModeNight = MultipleUtils.curUIModeNight(context.getResources().getConfiguration());
        this.mInNightMode = curUIModeNight;
        setBackground(curUIModeNight ? this.mNightDrawable : this.mDayDrawable);
        setHintTextColor(this.mInNightMode ? this.mHintTextColorNight : this.mHintTextColorDay);
        setTextColor(this.mInNightMode ? this.mTextColorNight : this.mTextColorDay);
        obtainStyledAttributes.recycle();
    }

    /* access modifiers changed from: protected */
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        boolean curUIModeNight = MultipleUtils.curUIModeNight(configuration);
        if (curUIModeNight != this.mInNightMode) {
            this.mInNightMode = curUIModeNight;
            setBackground(curUIModeNight ? this.mNightDrawable : this.mDayDrawable);
            setHintTextColor(this.mInNightMode ? this.mHintTextColorNight : this.mHintTextColorDay);
            setTextColor(this.mInNightMode ? this.mTextColorNight : this.mTextColorDay);
            invalidate();
        }
    }

    public boolean isLayoutRtl() {
        boolean isLayoutRtl = super.isLayoutRtl();
        setGravity((isLayoutRtl ? 5 : 3) | 16);
        return isLayoutRtl;
    }
}
