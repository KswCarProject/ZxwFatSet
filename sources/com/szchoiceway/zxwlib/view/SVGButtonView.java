package com.szchoiceway.zxwlib.view;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.ViewCompat;
import com.szchoiceway.zxwlib.R;
import com.szchoiceway.zxwlib.utils.MultipleUtils;

public class SVGButtonView extends View {
    private Drawable mBackgroundDrawable;
    private int mBackgroundDrawableHeight;
    private int mBackgroundDrawableWidth;
    private int mBackgroundMiddleDrawableHeight;
    private int mBackgroundMiddleDrawableWidth;
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
    private int mPressedDayColor = ViewCompat.MEASURED_STATE_MASK;
    private int mPressedDownColor = -16776961;
    private int mPressedUpColor = -1;
    private Drawable mSrcDrawable;

    public SVGButtonView(Context context) {
        super(context);
    }

    public SVGButtonView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context, attributeSet);
    }

    public SVGButtonView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context, attributeSet);
    }

    private void init(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.SVGButtonView);
        this.mSrcDrawable = obtainStyledAttributes.getDrawable(R.styleable.SVGButtonView_srcDrawable);
        this.mBackgroundDrawable = obtainStyledAttributes.getDrawable(R.styleable.SVGButtonView_backgroundDrable);
        this.mDrawableScale = obtainStyledAttributes.getFloat(R.styleable.SVGButtonView_drawableScale, 1.0f);
        this.mPressedDownColor = obtainStyledAttributes.getColor(R.styleable.SVGButtonView_pressedDownColor, this.mPressedDownColor);
        this.mPressedUpColor = obtainStyledAttributes.getColor(R.styleable.SVGButtonView_pressedUpColor, this.mPressedUpColor);
        this.mPressedDayColor = obtainStyledAttributes.getColor(R.styleable.SVGButtonView_pressedDayColor, this.mPressedDayColor);
        this.mLeftOffset = obtainStyledAttributes.getDimension(R.styleable.SVGButtonView_drawableLeftOffset, 0.0f);
        this.mInNightMode = MultipleUtils.curUIModeNight(context.getResources().getConfiguration());
        Drawable drawable = this.mSrcDrawable;
        if (drawable != null) {
            this.mDrawableWidth = drawable.getIntrinsicWidth();
            int intrinsicHeight = this.mSrcDrawable.getIntrinsicHeight();
            this.mDrawableHeight = intrinsicHeight;
            this.mMiddleDrawableWidth = this.mDrawableWidth;
            this.mMiddleDrawableHeight = intrinsicHeight;
            press(this.mInNightMode ? this.mPressedUpColor : this.mPressedDayColor);
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
            int i = this.mMeasureWidth;
            int i2 = this.mDrawableWidth;
            if (i < i2) {
                this.mMiddleDrawableWidth = (int) (((float) i) * 0.9f);
            } else {
                this.mMiddleDrawableWidth = i2;
            }
            int i3 = this.mMeasureHeight;
            int i4 = this.mDrawableHeight;
            if (i3 < i4) {
                this.mMiddleDrawableHeight = (int) (((float) i3) * 0.9f);
            } else {
                this.mMiddleDrawableHeight = i4;
            }
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

    private void press(int i) {
        setDrawableColor(i);
    }

    private void setDrawableColor(int i) {
        Drawable mutate = DrawableCompat.wrap(this.mSrcDrawable).mutate();
        mutate.setTint(i);
        this.mSrcDrawable = mutate;
        resetDrawable();
    }

    private void resetDrawable() {
        postInvalidate();
    }

    private void resetDrawable(int i, int i2) {
        this.mSrcDrawable.setBounds(0, 0, i, i2);
    }

    private void resetBackgroundDrawable(int i, int i2) {
        this.mBackgroundDrawable.setBounds(0, 0, i, i2);
    }

    public void setLevel(int i) {
        this.mSrcDrawable.setLevel(i);
        resetDrawable();
    }

    public void setNeedOffset(boolean z) {
        this.mNeedOffset = z;
    }

    /* access modifiers changed from: protected */
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        boolean curUIModeNight = MultipleUtils.curUIModeNight(configuration);
        if (curUIModeNight != this.mInNightMode) {
            this.mInNightMode = curUIModeNight;
            press(curUIModeNight ? this.mPressedUpColor : this.mPressedDayColor);
        }
    }
}
