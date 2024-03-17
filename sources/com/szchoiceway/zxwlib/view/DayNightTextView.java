package com.szchoiceway.zxwlib.view;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.view.ViewCompat;
import com.szchoiceway.zxwlib.R;
import com.szchoiceway.zxwlib.utils.MultipleUtils;

public class DayNightTextView extends AppCompatTextView {
    private int mBackgroundDrawableDay;
    private int mBackgroundDrawableNight;
    private Context mContext;
    private int mDayColor = ViewCompat.MEASURED_STATE_MASK;
    private int mDaySelectColor = ViewCompat.MEASURED_STATE_MASK;
    boolean mInNightMode = false;
    private boolean mIsSelect = false;
    private int mMeasureHeight = 0;
    private int mMeasureWidth = 0;
    private float mMiddleTextSize = 0.0f;
    private int mNightColor = -1;
    private int mNightSelectColor = ViewCompat.MEASURED_STATE_MASK;
    private float mTextSize = 0.0f;

    public DayNightTextView(Context context) {
        super(context);
    }

    public DayNightTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context, attributeSet);
    }

    public DayNightTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context, attributeSet);
    }

    private void init(Context context, AttributeSet attributeSet) {
        int i;
        this.mContext = context;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.DayNightTextView);
        this.mDayColor = obtainStyledAttributes.getColor(R.styleable.DayNightTextView_textDayColor, this.mDayColor);
        this.mNightColor = obtainStyledAttributes.getColor(R.styleable.DayNightTextView_textNightColor, this.mNightColor);
        this.mDaySelectColor = obtainStyledAttributes.getColor(R.styleable.DayNightTextView_textDaySelectColor, this.mDaySelectColor);
        this.mNightSelectColor = obtainStyledAttributes.getColor(R.styleable.DayNightTextView_textNightSelectColor, this.mNightSelectColor);
        this.mBackgroundDrawableDay = obtainStyledAttributes.getResourceId(R.styleable.DayNightTextView_textDayPressColor, 0);
        this.mBackgroundDrawableNight = obtainStyledAttributes.getResourceId(R.styleable.DayNightTextView_textNightPressColor, 0);
        this.mInNightMode = MultipleUtils.curUIModeNight(context.getResources().getConfiguration());
        obtainStyledAttributes.recycle();
        setTextColor(this.mInNightMode ? this.mNightColor : this.mDayColor);
        float textSize = getTextSize();
        this.mTextSize = textSize;
        this.mMiddleTextSize = textSize;
        int i2 = this.mBackgroundDrawableNight;
        if (i2 != 0 && (i = this.mBackgroundDrawableDay) != 0) {
            if (!this.mInNightMode) {
                i2 = i;
            }
            setBackgroundResource(i2);
        }
    }

    /* access modifiers changed from: protected */
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        boolean curUIModeNight = MultipleUtils.curUIModeNight(configuration);
        if (curUIModeNight != this.mInNightMode) {
            this.mInNightMode = curUIModeNight;
            setBackgroundResource(curUIModeNight ? this.mBackgroundDrawableNight : this.mBackgroundDrawableDay);
            setSelect(this.mIsSelect);
        }
    }

    private void restoreDefaultsColor() {
        setTextColor(this.mInNightMode ? this.mNightColor : this.mDayColor);
    }

    public void setSelect(boolean z) {
        boolean curUIModeNight = MultipleUtils.curUIModeNight(getContext().getResources().getConfiguration());
        this.mInNightMode = curUIModeNight;
        this.mIsSelect = z;
        if (z) {
            setTextColor(curUIModeNight ? this.mNightSelectColor : this.mDaySelectColor);
        } else {
            restoreDefaultsColor();
        }
    }

    public void setDayColor(int i) {
        this.mDayColor = i;
        if (this.mInNightMode) {
            i = this.mNightColor;
        }
        setTextColor(i);
    }
}
