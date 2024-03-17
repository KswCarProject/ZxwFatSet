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

public class SVGImageButtonView1 extends AppCompatImageButton {
    private int mBackgroundDrawableDay;
    private int mBackgroundDrawableNight;
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
    private Drawable mSrcDrawable;
    private Drawable mSrcDrawableDay;

    public SVGImageButtonView1(Context context) {
        super(context);
    }

    public SVGImageButtonView1(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context, attributeSet);
    }

    public SVGImageButtonView1(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context, attributeSet);
    }

    private void init(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.SVGImageButtonView1);
        this.mSrcDrawable = obtainStyledAttributes.getDrawable(R.styleable.SVGImageButtonView1_svgImageButtonDrawable);
        this.mSrcDrawableDay = obtainStyledAttributes.getDrawable(R.styleable.SVGImageButtonView1_svgImageButtonDrawableDay);
        this.mDrawableScale = obtainStyledAttributes.getFloat(R.styleable.SVGImageButtonView1_svgImageButtonDrawableScale, this.mDrawableScale);
        this.mBackgroundDrawableDay = obtainStyledAttributes.getResourceId(R.styleable.SVGImageButtonView1_backgroundColorDay, 0);
        this.mBackgroundDrawableNight = obtainStyledAttributes.getResourceId(R.styleable.SVGImageButtonView1_backgroundColorNight, 0);
        this.mLeftOffset = obtainStyledAttributes.getDimension(R.styleable.SVGImageButtonView1_svgImageButtonDrawableLeftOffset, 0.0f);
        Drawable drawable = this.mSrcDrawable;
        if (drawable != null) {
            this.mDrawableWidth = drawable.getIntrinsicWidth();
            int intrinsicHeight = this.mSrcDrawable.getIntrinsicHeight();
            this.mDrawableHeight = intrinsicHeight;
            this.mMiddleDrawableWidth = this.mDrawableWidth;
            this.mMiddleDrawableHeight = intrinsicHeight;
            boolean curUIModeNight = MultipleUtils.curUIModeNight(context.getResources().getConfiguration());
            this.mInNightMode = curUIModeNight;
            setBackgroundResource(curUIModeNight ? this.mBackgroundDrawableNight : this.mBackgroundDrawableDay);
            setDrawableColor();
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
            if (this.mInNightMode) {
                this.mSrcDrawable.draw(canvas);
            } else {
                Drawable drawable = this.mSrcDrawableDay;
                if (drawable != null) {
                    drawable.draw(canvas);
                }
            }
            canvas.restore();
        }
    }

    private void setDrawableColor() {
        this.mSrcDrawable = DrawableCompat.wrap(this.mSrcDrawable).mutate();
        postInvalidate();
    }

    public void setNeedOffset(boolean z) {
        this.mNeedOffset = z;
    }

    private void resetDrawable(int i, int i2) {
        if (this.mInNightMode) {
            this.mSrcDrawable.setBounds(0, 0, i, i2);
        } else {
            this.mSrcDrawableDay.setBounds(0, 0, i, i2);
        }
    }

    /* access modifiers changed from: protected */
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        boolean curUIModeNight = MultipleUtils.curUIModeNight(configuration);
        if (curUIModeNight != this.mInNightMode) {
            this.mInNightMode = curUIModeNight;
            setBackgroundResource(curUIModeNight ? this.mBackgroundDrawableNight : this.mBackgroundDrawableDay);
            postInvalidate();
        }
    }

    public void setScale(float f) {
        this.mDrawableScale = f;
        postInvalidate();
    }
}
