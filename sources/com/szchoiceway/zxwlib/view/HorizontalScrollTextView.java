package com.szchoiceway.zxwlib.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.view.ViewCompat;
import com.szchoiceway.zxwlib.R;

public class HorizontalScrollTextView extends AppCompatTextView implements View.OnClickListener {
    public static final String TAG = "HorizontalScrollTextView";
    private boolean isSizedChanged;
    public boolean isStarting;
    public String mText;
    private int mTextColor;
    private int mode;
    private int offsetX;
    private OnScrollCompleteListener onScrollCompleteListener;
    private Paint paint;
    private float textLength;
    private float viewWidth;
    private float x;
    private float y;

    public interface OnScrollCompleteListener {
        void onScrollComplete();
    }

    public HorizontalScrollTextView(Context context) {
        this(context, (AttributeSet) null);
    }

    public HorizontalScrollTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, -1);
    }

    public HorizontalScrollTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.textLength = 0.0f;
        this.viewWidth = 0.0f;
        this.x = 0.0f;
        this.y = 0.0f;
        this.offsetX = 0;
        this.isStarting = false;
        this.paint = null;
        this.mText = "";
        this.mTextColor = ViewCompat.MEASURED_SIZE_MASK;
        this.mode = 0;
        this.isSizedChanged = false;
        TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R.styleable.HorizontalScrollTextView, i, 0);
        this.mTextColor = obtainStyledAttributes.getColor(R.styleable.HorizontalScrollTextView_textColor, -1);
        this.mode = obtainStyledAttributes.getInt(R.styleable.HorizontalScrollTextView_scroll_mode, 0);
        this.isStarting = obtainStyledAttributes.getBoolean(R.styleable.HorizontalScrollTextView_scroll, false);
        Log.i(TAG, "HorizontalScrollTextView: mTextColor = " + this.mTextColor);
        Log.i(TAG, "HorizontalScrollTextView: mode = " + this.mode);
        obtainStyledAttributes.recycle();
        init();
    }

    public OnScrollCompleteListener getOnScrollCompleteListener() {
        return this.onScrollCompleteListener;
    }

    public void setOnScrollCompleteListener(OnScrollCompleteListener onScrollCompleteListener2) {
        this.onScrollCompleteListener = onScrollCompleteListener2;
    }

    private void initView() {
        setOnClickListener(this);
    }

    public void init() {
        TextPaint paint2 = getPaint();
        this.paint = paint2;
        paint2.setColor(this.mTextColor);
    }

    public void setText(String str) {
        if (!this.mText.equals(str) || this.isSizedChanged) {
            this.isSizedChanged = false;
            float f = 0.0f;
            this.x = 0.0f;
            this.mText = str;
            this.textLength = this.paint.measureText(str);
            Log.i(TAG, "setText: textLength = " + this.textLength + this.viewWidth);
            this.viewWidth = (float) ((getWidth() - getPaddingLeft()) - getPaddingRight());
            Log.i(TAG, "setText: viewWidth = " + this.viewWidth);
            this.y = getTextSize() + ((float) getPaddingTop());
            int gravity = getGravity();
            if (17 == gravity || 1 == gravity) {
                float f2 = this.viewWidth;
                float f3 = this.textLength;
                if (f2 > f3) {
                    f = (f2 - f3) / 2.0f;
                }
                this.x = f;
            }
            Log.i(TAG, "setText: x = " + this.x);
            invalidate();
        }
    }

    public void startScroll() {
        this.isStarting = true;
        invalidate();
    }

    public void stopScroll() {
        this.isStarting = false;
        invalidate();
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        this.viewWidth = (float) i;
        this.isSizedChanged = true;
        setText(this.mText);
    }

    /* access modifiers changed from: protected */
    public void onFocusChanged(boolean z, int i, Rect rect) {
        super.onFocusChanged(z, i, rect);
        Log.i(TAG, "onFocusChanged: focused = " + z);
    }

    public void onDraw(Canvas canvas) {
        if (this.isStarting) {
            if (this.textLength > this.viewWidth) {
                canvas.drawText(this.mText, (-this.x) - ((float) this.offsetX), this.y, this.paint);
                float f = (float) (((double) this.x) + 1.0d);
                this.x = f;
                if (f <= this.textLength) {
                    postInvalidateDelayed(16);
                } else if (this.mode == 1) {
                    this.x = 0.0f;
                    OnScrollCompleteListener onScrollCompleteListener2 = this.onScrollCompleteListener;
                    if (onScrollCompleteListener2 != null) {
                        onScrollCompleteListener2.onScrollComplete();
                    }
                    canvas.drawText(this.mText, this.x, this.y, this.paint);
                } else {
                    this.x = -this.viewWidth;
                    postInvalidateDelayed(16);
                }
            } else if (getGravity() == 17) {
                canvas.drawText(this.mText, (((float) getWidth()) - this.textLength) / 2.0f, this.y, this.paint);
            } else {
                canvas.drawText(this.mText, this.x, this.y, this.paint);
            }
        }
    }

    public void onClick(View view) {
        if (this.isStarting) {
            stopScroll();
        } else {
            startScroll();
        }
    }
}
