package com.szchoiceway.zxwlib.focus.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.internal.view.SupportMenu;
import com.szchoiceway.zxwlib.R;

public class FocusTextView extends AppCompatTextView implements FocusStateInterface {
    boolean mDoEnterWhenFocused = false;
    Drawable mFocusedDrawable = null;
    Drawable mFocusedForegroundDrawable = null;
    int mFrameColor = SupportMenu.CATEGORY_MASK;
    int mFrameWidth = 2;
    boolean mInFocus = false;
    boolean mNeedDrawFocusByBackground = true;
    boolean mNeedDrawFocusFrame = false;
    Drawable mOldDrawable = null;
    Drawable mOldForegroundDrawable = null;
    Paint mPaint = new Paint();

    public FocusTextView(Context context) {
        super(context);
    }

    public FocusTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context, attributeSet);
    }

    private void init(Context context, AttributeSet attributeSet) {
        this.mContext = context;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.FocusTextView);
        this.mNeedDrawFocusFrame = obtainStyledAttributes.getBoolean(R.styleable.FocusTextView_needDrawFocusFrame, false);
        this.mNeedDrawFocusByBackground = obtainStyledAttributes.getBoolean(R.styleable.FocusTextView_needDrawFocusByBackground, true);
        this.mFocusedDrawable = obtainStyledAttributes.getDrawable(R.styleable.FocusTextView_focusedDrawable);
        this.mFocusedForegroundDrawable = obtainStyledAttributes.getDrawable(R.styleable.FocusTextView_focusedForegroundDrawable);
        this.mDoEnterWhenFocused = obtainStyledAttributes.getBoolean(R.styleable.FocusTextView_doEnterWhenfocused, false);
        this.mFrameColor = obtainStyledAttributes.getColor(R.styleable.FocusTextView_focusedFrameColor, this.mFrameColor);
        this.mFrameWidth = obtainStyledAttributes.getInt(R.styleable.FocusTextView_focusedFrameWidth, this.mFrameWidth);
        obtainStyledAttributes.recycle();
        this.mOldDrawable = getBackground();
        this.mOldForegroundDrawable = getForeground();
        this.mPaint.setAntiAlias(true);
        this.mPaint.setColor(this.mFrameColor);
        this.mPaint.setStrokeWidth((float) this.mFrameWidth);
        this.mPaint.setStyle(Paint.Style.STROKE);
    }

    public void setFocusedState() {
        this.mInFocus = true;
        if (this.mNeedDrawFocusFrame) {
            postInvalidate();
        } else if (this.mNeedDrawFocusByBackground) {
            Drawable drawable = this.mFocusedDrawable;
            if (drawable != null) {
                setBackground(drawable);
            }
        } else {
            Drawable drawable2 = this.mFocusedForegroundDrawable;
            if (drawable2 != null) {
                setForeground(drawable2);
            }
        }
    }

    public void clearFocusedState() {
        this.mInFocus = false;
        if (this.mNeedDrawFocusFrame) {
            postInvalidate();
        } else if (this.mNeedDrawFocusByBackground) {
            setBackground(this.mOldDrawable);
        } else {
            setForeground(this.mOldForegroundDrawable);
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mInFocus && this.mNeedDrawFocusFrame) {
            canvas.save();
            canvas.drawRect(0.0f, 0.0f, (float) getWidth(), (float) getHeight(), this.mPaint);
            canvas.restore();
        }
    }

    public boolean ismDoEnterWhenFocused() {
        return this.mDoEnterWhenFocused;
    }
}
