package com.szchoiceway.zxwlib.view;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.ViewCompat;
import com.szchoiceway.zxwlib.R;
import com.szchoiceway.zxwlib.utils.MultipleUtils;

public class SVGCheckView extends View {
    private int mBackgoundDayId;
    private int mBackgroundNightId;
    private int mCheckedDayColor = ViewCompat.MEASURED_STATE_MASK;
    private int mCheckedDayTrueColor = -1;
    private int mCheckedFlaseColor = -1;
    private int mCheckedTrueColor = -16776961;
    private float mDrawableScale = 1.0f;
    boolean mInNightMode = false;
    private boolean mIsChecked = false;
    private int mMeasureHeight = 0;
    private int mMeasureWidth = 0;
    private Drawable mSrcDrawable;

    public SVGCheckView(Context context) {
        super(context);
    }

    public SVGCheckView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context, attributeSet);
    }

    public SVGCheckView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context, attributeSet);
    }

    private void init(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.SVGCheckView);
        this.mSrcDrawable = obtainStyledAttributes.getDrawable(R.styleable.SVGCheckView_srcDrawableCheck);
        this.mDrawableScale = obtainStyledAttributes.getFloat(R.styleable.SVGCheckView_drawableScaleCheck, 1.0f);
        this.mCheckedTrueColor = obtainStyledAttributes.getColor(R.styleable.SVGCheckView_checkedTrueColor, this.mCheckedTrueColor);
        this.mCheckedFlaseColor = obtainStyledAttributes.getColor(R.styleable.SVGCheckView_checkedFalseColor, this.mCheckedFlaseColor);
        this.mCheckedDayColor = obtainStyledAttributes.getColor(R.styleable.SVGCheckView_checkedDayColor, this.mCheckedDayColor);
        this.mCheckedDayTrueColor = obtainStyledAttributes.getColor(R.styleable.SVGCheckView_checkedTrueDayColor, this.mCheckedDayTrueColor);
        this.mBackgoundDayId = obtainStyledAttributes.getResourceId(R.styleable.SVGCheckView_checkViewBackgroundDay, this.mBackgoundDayId);
        this.mBackgroundNightId = obtainStyledAttributes.getResourceId(R.styleable.SVGCheckView_checkViewBackgroundNight, this.mBackgroundNightId);
        boolean curUIModeNight = MultipleUtils.curUIModeNight(context.getResources().getConfiguration());
        this.mInNightMode = curUIModeNight;
        setBackgroundResource(curUIModeNight ? this.mBackgroundNightId : this.mBackgoundDayId);
        if (this.mSrcDrawable != null) {
            setChecked(this.mIsChecked);
        }
        obtainStyledAttributes.recycle();
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        this.mMeasureWidth = View.MeasureSpec.getSize(i);
        int size = View.MeasureSpec.getSize(i2);
        this.mMeasureHeight = size;
        setMeasuredDimension(this.mMeasureWidth, size);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mSrcDrawable != null) {
            canvas.save();
            canvas.translate((((float) this.mMeasureWidth) - (((float) this.mSrcDrawable.getIntrinsicWidth()) * this.mDrawableScale)) * 0.5f, (((float) this.mMeasureHeight) - (((float) this.mSrcDrawable.getIntrinsicHeight()) * this.mDrawableScale)) * 0.5f);
            float f = this.mDrawableScale;
            canvas.scale(f, f);
            this.mSrcDrawable.draw(canvas);
            canvas.restore();
        }
    }

    private void press(int i) {
        setDrawableColor(i);
        postInvalidate();
    }

    private void setDrawableColor(int i) {
        Drawable mutate = DrawableCompat.wrap(this.mSrcDrawable).mutate();
        mutate.setTint(i);
        this.mSrcDrawable = mutate;
        mutate.setBounds(0, 0, mutate.getIntrinsicWidth(), this.mSrcDrawable.getIntrinsicHeight());
    }

    public void setLevel(int i) {
        this.mSrcDrawable.setLevel(i);
    }

    public void setChecked(boolean z) {
        this.mIsChecked = z;
        if (z) {
            press(this.mInNightMode ? this.mCheckedTrueColor : this.mCheckedDayTrueColor);
        } else {
            press(this.mInNightMode ? this.mCheckedFlaseColor : this.mCheckedDayColor);
        }
    }

    /* access modifiers changed from: protected */
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        boolean curUIModeNight = MultipleUtils.curUIModeNight(configuration);
        if (curUIModeNight != this.mInNightMode) {
            this.mInNightMode = curUIModeNight;
            setChecked(this.mIsChecked);
            setBackgroundResource(this.mInNightMode ? this.mBackgroundNightId : this.mBackgoundDayId);
        }
    }

    public void setSrcDrawable(int i) {
        this.mSrcDrawable = getResources().getDrawable(i, (Resources.Theme) null);
        Log.i("TAG", "setSrcDrawable: " + (this.mSrcDrawable == null));
        press(this.mInNightMode ? this.mCheckedFlaseColor : this.mCheckedDayColor);
    }

    public void setSrcDrawableColorDayNight(int i, int i2) {
        this.mCheckedDayColor = i;
        this.mCheckedFlaseColor = i2;
        if (this.mInNightMode) {
            i = i2;
        }
        press(i);
    }
}
