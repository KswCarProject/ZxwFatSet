package com.szchoiceway.zxwlib.view;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.view.ViewCompat;
import com.szchoiceway.zxwlib.R;
import com.szchoiceway.zxwlib.utils.MultipleUtils;

public class DayNightButton extends AppCompatButton {
    private int mBackgroundDrawableDay;
    private int mBackgroundDrawableNight;
    private Context mContext;
    private int mDayColor = ViewCompat.MEASURED_STATE_MASK;
    boolean mInNightMode = false;
    private int mMeasureHeight = 0;
    private int mMeasureWidth = 0;
    private float mMiddleTextSize = 0.0f;
    private int mNightColor = -1;
    private float mTextSize = 0.0f;

    public DayNightButton(Context context) {
        super(context);
    }

    public DayNightButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context, attributeSet);
    }

    public DayNightButton(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context, attributeSet);
    }

    private void init(Context context, AttributeSet attributeSet) {
        this.mContext = context;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.DayNightButton);
        this.mDayColor = obtainStyledAttributes.getColor(R.styleable.DayNightButton_buttonTextDayColor, this.mDayColor);
        this.mNightColor = obtainStyledAttributes.getColor(R.styleable.DayNightButton_buttonTextNightColor, this.mNightColor);
        this.mBackgroundDrawableDay = obtainStyledAttributes.getResourceId(R.styleable.DayNightButton_buttonDayPressColor, 0);
        this.mBackgroundDrawableNight = obtainStyledAttributes.getResourceId(R.styleable.DayNightButton_buttonNightPressColor, 0);
        boolean curUIModeNight = MultipleUtils.curUIModeNight(context.getResources().getConfiguration());
        this.mInNightMode = curUIModeNight;
        setTextColor(curUIModeNight ? this.mNightColor : this.mDayColor);
        float textSize = getTextSize();
        this.mTextSize = textSize;
        this.mMiddleTextSize = textSize;
        setBackgroundResource(this.mInNightMode ? this.mBackgroundDrawableNight : this.mBackgroundDrawableDay);
        obtainStyledAttributes.recycle();
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        this.mMeasureWidth = View.MeasureSpec.getSize(i);
        int size = View.MeasureSpec.getSize(i2);
        this.mMeasureHeight = size;
        setMeasuredDimension(this.mMeasureWidth, size);
        measureText((String) getText());
    }

    private void measureText(String str) {
        Paint paint = new Paint();
        new Rect();
        paint.setTextSize(this.mMiddleTextSize);
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        if (((int) (fontMetrics.descent - fontMetrics.ascent)) > this.mMeasureHeight) {
            this.mMiddleTextSize *= 0.9f;
        } else {
            float f = this.mMiddleTextSize * 1.1f;
            this.mMiddleTextSize = f;
            float f2 = this.mTextSize;
            if (f > f2) {
                this.mMiddleTextSize = f2;
            }
        }
        setTextSize(0, this.mMiddleTextSize);
    }

    /* access modifiers changed from: protected */
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        boolean curUIModeNight = MultipleUtils.curUIModeNight(configuration);
        if (curUIModeNight != this.mInNightMode) {
            this.mInNightMode = curUIModeNight;
            setTextColor(curUIModeNight ? this.mNightColor : this.mDayColor);
            setBackgroundResource(this.mInNightMode ? this.mBackgroundDrawableNight : this.mBackgroundDrawableDay);
        }
    }
}
