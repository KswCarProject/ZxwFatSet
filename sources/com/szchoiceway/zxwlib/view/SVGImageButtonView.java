package com.szchoiceway.zxwlib.view;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.core.graphics.drawable.DrawableCompat;
import com.szchoiceway.zxwlib.R;
import com.szchoiceway.zxwlib.utils.MultipleUtils;

public class SVGImageButtonView extends AppCompatImageButton {
    private int mBackgroundDrawableDay;
    private int mBackgroundDrawableNight;
    private int mDayColor = -16776961;
    private int mDrawableHeight;
    private float mDrawableScale = 1.0f;
    private int mDrawableWidth;
    boolean mInNightMode = false;
    private float mLeftOffset = 0.0f;
    private int mMeasureHeight = 0;
    private int mMeasureWidth = 0;
    private int mMiddleDrawableHeight;
    private int mMiddleDrawableWidth;
    private boolean mNeedOffset = false;
    private int mNightColor = -1;
    private Drawable mSrcDrawable;

    public SVGImageButtonView(Context context) {
        super(context);
    }

    public SVGImageButtonView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context, attributeSet);
    }

    public SVGImageButtonView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context, attributeSet);
    }

    private void init(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.SVGImageButtonView);
        this.mSrcDrawable = obtainStyledAttributes.getDrawable(R.styleable.SVGImageButtonView_svgImageButtonDrawable);
        this.mDayColor = obtainStyledAttributes.getColor(R.styleable.SVGImageButtonView_drawableColorDay, this.mDayColor);
        this.mNightColor = obtainStyledAttributes.getColor(R.styleable.SVGImageButtonView_drawableColorNight, this.mNightColor);
        this.mDrawableScale = obtainStyledAttributes.getFloat(R.styleable.SVGImageButtonView_svgImageButtonDrawableScale, this.mDrawableScale);
        this.mBackgroundDrawableDay = obtainStyledAttributes.getResourceId(R.styleable.SVGImageButtonView_backgroundColorDay, 0);
        this.mBackgroundDrawableNight = obtainStyledAttributes.getResourceId(R.styleable.SVGImageButtonView_backgroundColorNight, 0);
        this.mLeftOffset = obtainStyledAttributes.getDimension(R.styleable.SVGImageButtonView_svgImageButtonDrawableLeftOffset, 0.0f);
        this.mNeedOffset = obtainStyledAttributes.getBoolean(R.styleable.SVGImageButtonView_drawableNeedOffset, false);
        Drawable drawable = this.mSrcDrawable;
        if (drawable != null) {
            this.mDrawableWidth = drawable.getIntrinsicWidth();
            int intrinsicHeight = this.mSrcDrawable.getIntrinsicHeight();
            this.mDrawableHeight = intrinsicHeight;
            this.mMiddleDrawableWidth = this.mDrawableWidth;
            this.mMiddleDrawableHeight = intrinsicHeight;
            boolean curUIModeNight = MultipleUtils.curUIModeNight(context.getResources().getConfiguration());
            this.mInNightMode = curUIModeNight;
            press(curUIModeNight ? this.mNightColor : this.mDayColor);
            setBackgroundResource(this.mInNightMode ? this.mBackgroundDrawableNight : this.mBackgroundDrawableDay);
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
            resetDrawable(this.mMiddleDrawableWidth, this.mMiddleDrawableHeight);
            float f = this.mDrawableScale;
            float f2 = (((float) this.mMeasureWidth) - (((float) this.mMiddleDrawableWidth) * f)) * 0.5f;
            float f3 = (((float) this.mMeasureHeight) - (((float) this.mMiddleDrawableHeight) * f)) * 0.5f;
            if (this.mNeedOffset) {
                f2 += this.mLeftOffset;
            }
            canvas.translate(f2, f3);
            float f4 = this.mDrawableScale;
            canvas.scale(f4, f4);
            this.mSrcDrawable.draw(canvas);
            canvas.restore();
        }
    }

    public void setLevel(int i) {
        this.mSrcDrawable.setLevel(i);
        resetDrawable();
    }

    private void press(int i) {
        setDrawableColor(i);
    }

    private void setDrawableColor(int i) {
        Drawable mutate = DrawableCompat.wrap(this.mSrcDrawable).mutate();
        mutate.setTint(i);
        this.mSrcDrawable = mutate;
        resetDrawable();
    }

    public void setNeedOffset(boolean z) {
        this.mNeedOffset = z;
    }

    private void resetDrawable() {
        postInvalidate();
    }

    private void resetDrawable(int i, int i2) {
        this.mSrcDrawable.setBounds(0, 0, i, i2);
    }

    /* access modifiers changed from: protected */
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        boolean curUIModeNight = MultipleUtils.curUIModeNight(configuration);
        if (curUIModeNight != this.mInNightMode) {
            this.mInNightMode = curUIModeNight;
            press(curUIModeNight ? this.mNightColor : this.mDayColor);
            setBackgroundResource(this.mInNightMode ? this.mBackgroundDrawableNight : this.mBackgroundDrawableDay);
        }
    }

    public void setDayColor(int i) {
        this.mDayColor = i;
        if (this.mInNightMode) {
            i = this.mNightColor;
        }
        press(i);
    }

    public void setBackgroundDrawableDay(int i) {
        this.mBackgroundDrawableDay = i;
        if (this.mInNightMode) {
            i = this.mBackgroundDrawableNight;
        }
        setBackgroundResource(i);
    }

    public void setBackgroundDrawableNight(int i) {
        this.mBackgroundDrawableNight = i;
        if (!this.mInNightMode) {
            i = this.mBackgroundDrawableDay;
        }
        setBackgroundResource(i);
    }

    public void setScale(float f) {
        this.mDrawableScale = f;
        postInvalidate();
    }
}
