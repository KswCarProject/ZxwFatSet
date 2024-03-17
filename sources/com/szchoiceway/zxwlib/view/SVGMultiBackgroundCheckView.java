package com.szchoiceway.zxwlib.view;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import androidx.core.graphics.drawable.DrawableCompat;
import com.szchoiceway.zxwlib.R;
import com.szchoiceway.zxwlib.utils.MultipleUtils;

public class SVGMultiBackgroundCheckView extends View {
    private int mBackgoundDayId;
    private int mBackgroundNightId;
    private Drawable mCheckedFalseDrawable;
    private int mCheckedFalseDrawableColorDay = -16776961;
    private int mCheckedFalseDrawableColorNight = -1;
    private Drawable mCheckedTrueDrawable;
    private int mCheckedTrueDrawableColorDay = -16776961;
    private int mCheckedTrueDrawableColorNight = -1;
    private float mDrawableScale = 1.0f;
    boolean mInNightMode = false;
    private boolean mIsChecked = false;
    private int mMeasureHeight = 0;
    private int mMeasureWidth = 0;
    private Drawable mSrcDrawable;
    private int mSrcDrawableColorDay = -16776961;
    private int mSrcDrawableColorNight = -1;

    public SVGMultiBackgroundCheckView(Context context) {
        super(context);
    }

    public SVGMultiBackgroundCheckView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context, attributeSet);
    }

    public SVGMultiBackgroundCheckView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context, attributeSet);
    }

    private void init(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.SVGMultiBackgroundCheckView);
        this.mSrcDrawable = obtainStyledAttributes.getDrawable(R.styleable.SVGMultiBackgroundCheckView_multiBackgroundCheckViewSrcDrawable);
        this.mSrcDrawableColorDay = obtainStyledAttributes.getColor(R.styleable.SVGMultiBackgroundCheckView_multiBackgroundCheckViewSrcDrawableColorDay, this.mSrcDrawableColorDay);
        this.mSrcDrawableColorNight = obtainStyledAttributes.getColor(R.styleable.SVGMultiBackgroundCheckView_multiBackgroundCheckViewSrcDrawableColorNight, this.mSrcDrawableColorNight);
        this.mCheckedTrueDrawable = obtainStyledAttributes.getDrawable(R.styleable.SVGMultiBackgroundCheckView_multiBackgroundCheckViewCheckTrueDrawable);
        this.mCheckedTrueDrawableColorDay = obtainStyledAttributes.getColor(R.styleable.SVGMultiBackgroundCheckView_multiBackgroundCheckViewCheckTrueDrawableColorDay, this.mCheckedTrueDrawableColorDay);
        this.mCheckedTrueDrawableColorNight = obtainStyledAttributes.getColor(R.styleable.SVGMultiBackgroundCheckView_multiBackgroundCheckViewCheckTrueDrawableColorNight, this.mCheckedTrueDrawableColorNight);
        this.mCheckedFalseDrawable = obtainStyledAttributes.getDrawable(R.styleable.SVGMultiBackgroundCheckView_multiBackgroundCheckViewCheckFalseStateDrawable);
        this.mCheckedFalseDrawableColorDay = obtainStyledAttributes.getColor(R.styleable.SVGMultiBackgroundCheckView_multiBackgroundCheckViewCheckFalseStateDrawableColorDay, this.mCheckedFalseDrawableColorDay);
        this.mCheckedFalseDrawableColorNight = obtainStyledAttributes.getColor(R.styleable.SVGMultiBackgroundCheckView_multiBackgroundCheckViewCheckFalseStateDrawableColorNight, this.mCheckedFalseDrawableColorNight);
        this.mDrawableScale = obtainStyledAttributes.getFloat(R.styleable.SVGMultiBackgroundCheckView_drawableScaleMultiCheck, 1.0f);
        this.mBackgoundDayId = obtainStyledAttributes.getResourceId(R.styleable.SVGMultiBackgroundCheckView_multiBackgroundCheckViewBackgroundDay, this.mBackgoundDayId);
        this.mBackgroundNightId = obtainStyledAttributes.getResourceId(R.styleable.SVGMultiBackgroundCheckView_multiBackgroundCheckViewBackgroundNight, this.mBackgroundNightId);
        boolean curUIModeNight = MultipleUtils.curUIModeNight(context.getResources().getConfiguration());
        this.mInNightMode = curUIModeNight;
        setBackgroundResource(curUIModeNight ? this.mBackgroundNightId : this.mBackgoundDayId);
        if (!(this.mSrcDrawable == null || this.mCheckedTrueDrawable == null || this.mCheckedFalseDrawable == null)) {
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
        if (this.mSrcDrawable != null && this.mCheckedTrueDrawable != null && this.mCheckedFalseDrawable != null) {
            canvas.save();
            canvas.translate((((float) this.mMeasureWidth) - (((float) this.mCheckedFalseDrawable.getIntrinsicWidth()) * this.mDrawableScale)) * 0.5f, (((float) this.mMeasureHeight) - (((float) this.mCheckedFalseDrawable.getIntrinsicHeight()) * this.mDrawableScale)) * 0.5f);
            float f = this.mDrawableScale;
            canvas.scale(f, f);
            this.mCheckedFalseDrawable.draw(canvas);
            canvas.restore();
            if (this.mIsChecked) {
                canvas.save();
                canvas.translate((((float) this.mMeasureWidth) - (((float) this.mCheckedTrueDrawable.getIntrinsicWidth()) * this.mDrawableScale)) * 0.5f, (((float) this.mMeasureHeight) - (((float) this.mCheckedTrueDrawable.getIntrinsicHeight()) * this.mDrawableScale)) * 0.5f);
                float f2 = this.mDrawableScale;
                canvas.scale(f2, f2);
                this.mCheckedTrueDrawable.draw(canvas);
                canvas.restore();
                canvas.save();
                canvas.translate((((float) this.mMeasureWidth) - (((float) this.mSrcDrawable.getIntrinsicWidth()) * this.mDrawableScale)) * 0.5f, (((float) this.mMeasureHeight) - (((float) this.mSrcDrawable.getIntrinsicHeight()) * this.mDrawableScale)) * 0.5f);
                float f3 = this.mDrawableScale;
                canvas.scale(f3, f3);
                this.mSrcDrawable.draw(canvas);
                canvas.restore();
            }
        }
    }

    private void press(int i, int i2, int i3) {
        setDrawableColor(i, i2, i3);
        invalidate();
    }

    private void setDrawableColor(int i, int i2, int i3) {
        Drawable mutate = DrawableCompat.wrap(this.mSrcDrawable).mutate();
        mutate.setTint(i);
        this.mSrcDrawable = mutate;
        mutate.setBounds(0, 0, mutate.getIntrinsicWidth(), this.mSrcDrawable.getIntrinsicHeight());
        Drawable mutate2 = DrawableCompat.wrap(this.mCheckedTrueDrawable).mutate();
        mutate2.setTint(i2);
        this.mCheckedTrueDrawable = mutate2;
        mutate2.setBounds(0, 0, mutate2.getIntrinsicWidth(), this.mCheckedTrueDrawable.getIntrinsicHeight());
        Drawable mutate3 = DrawableCompat.wrap(this.mCheckedFalseDrawable).mutate();
        mutate3.setTint(i3);
        this.mCheckedFalseDrawable = mutate3;
        mutate3.setBounds(0, 0, mutate3.getIntrinsicWidth(), this.mCheckedFalseDrawable.getIntrinsicHeight());
    }

    public void setLevel(int i) {
        this.mSrcDrawable.setLevel(i);
    }

    public void setChecked(boolean z) {
        this.mIsChecked = z;
        if (z) {
            boolean z2 = this.mInNightMode;
            press(z2 ? this.mSrcDrawableColorNight : this.mSrcDrawableColorDay, z2 ? this.mCheckedTrueDrawableColorNight : this.mCheckedTrueDrawableColorDay, 0);
            return;
        }
        press(0, 0, this.mInNightMode ? this.mCheckedFalseDrawableColorNight : this.mCheckedFalseDrawableColorDay);
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

    public boolean isChecked() {
        return this.mIsChecked;
    }
}
