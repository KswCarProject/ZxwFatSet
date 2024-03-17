package com.szchoiceway.zxwlib.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatTextView;
import com.szchoiceway.zxwlib.R;

public class MarqueeTextView extends AppCompatTextView implements Runnable {
    private static final int MARQUEE_DOWN = 3;
    private static final int MARQUEE_FRONT = 0;
    private static final int MARQUEE_HORIZONTAL_EDGE_LOOP = 4;
    private static final int MARQUEE_REAR = 1;
    private static final int MARQUEE_UP = 2;
    private static final int SCROLL_DIRECTION_LEFT = 0;
    private static final int SCROLL_DIRECTION_RIGHT = 1;
    private int currentScroll;
    private boolean isMeasure = false;
    private boolean isStop = false;
    private int mGra = 0;
    private int mMarqueeDirection = 0;
    private int mMarqueeSpeed = 1;
    private boolean mNeedResetTextLocal = false;
    private int scrollDirection = 0;
    private int textHeight = 0;
    private int textWidth = 0;

    public MarqueeTextView(Context context) {
        super(context);
    }

    public MarqueeTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context, attributeSet);
    }

    public MarqueeTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context, attributeSet);
    }

    private void init(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.marqueeType);
        this.mMarqueeDirection = obtainStyledAttributes.getInt(R.styleable.marqueeType_marqueeDirection, 0);
        this.mMarqueeSpeed = obtainStyledAttributes.getInt(R.styleable.marqueeType_marqueeSpeed, 1);
        this.mGra = obtainStyledAttributes.getInt(R.styleable.marqueeType_textDefaultGravity, 3);
        this.mNeedResetTextLocal = obtainStyledAttributes.getBoolean(R.styleable.marqueeType_needRestTextLocal, this.mNeedResetTextLocal);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!this.isMeasure) {
            getTextWidth();
            this.isMeasure = true;
        }
    }

    private void getTextWidth() {
        int i = this.mMarqueeDirection;
        if (i == 0 || i == 1 || i == 4) {
            setSingleLine(true);
        }
        TextPaint paint = getPaint();
        String charSequence = getText().toString();
        Rect rect = new Rect();
        paint.getTextBounds(charSequence, 0, charSequence.length(), rect);
        int width = rect.width();
        int height = rect.height();
        this.textWidth = width;
        int i2 = this.mMarqueeDirection;
        if (!(i2 == 0 || i2 == 1)) {
            if (i2 == 2 || i2 == 3) {
                float width2 = (((float) width) * 1.0f) / ((float) getWidth());
                if (width2 <= 1.0f) {
                    this.isStop = true;
                    return;
                } else {
                    this.textHeight = (int) (((float) height) * (width2 + 1.0f));
                    return;
                }
            } else if (i2 != 4) {
                return;
            }
        }
        if (getWidth() >= this.textWidth) {
            if (this.mNeedResetTextLocal) {
                setGravity(this.mGra);
            }
            this.isStop = true;
        } else if (this.mNeedResetTextLocal) {
            setGravity(19);
        }
    }

    public void run() {
        if (!this.isStop) {
            int i = this.mMarqueeDirection;
            if (i == 0) {
                int i2 = this.currentScroll + this.mMarqueeSpeed;
                this.currentScroll = i2;
                scrollTo(i2, 0);
                if (getScrollX() >= this.textWidth) {
                    scrollTo(-getWidth(), 0);
                    this.currentScroll = -getWidth();
                }
            } else if (i == 1) {
                int i3 = this.currentScroll - this.mMarqueeSpeed;
                this.currentScroll = i3;
                scrollTo(i3, 0);
                if (getScrollX() <= (-getWidth())) {
                    scrollTo(this.textWidth, 0);
                    this.currentScroll = this.textWidth;
                }
            } else if (i == 2) {
                int i4 = this.currentScroll + this.mMarqueeSpeed;
                this.currentScroll = i4;
                scrollTo(0, i4);
                if (getScrollY() >= this.textHeight) {
                    scrollTo(0, -getHeight());
                    this.currentScroll = -getHeight();
                }
            } else if (i == 3) {
                int i5 = this.currentScroll - this.mMarqueeSpeed;
                this.currentScroll = i5;
                scrollTo(0, i5);
                if (getScrollY() <= (-getHeight())) {
                    scrollTo(0, this.textHeight);
                    this.currentScroll = this.textHeight;
                }
            } else if (i == 4) {
                int width = this.textWidth - getWidth();
                int i6 = this.currentScroll;
                if (i6 >= width) {
                    this.scrollDirection = 1;
                } else if (i6 <= 0) {
                    this.scrollDirection = 0;
                }
                int i7 = this.scrollDirection;
                if (i7 == 0) {
                    this.currentScroll = i6 + this.mMarqueeSpeed;
                } else if (i7 == 1) {
                    this.currentScroll = i6 - this.mMarqueeSpeed;
                }
                scrollTo(this.currentScroll, 0);
            }
            postDelayed(this, 20);
        }
    }

    public void scrollTo(int i, int i2) {
        if (this.isStop || i != 0 || i2 != 0) {
            super.scrollTo(i, i2);
        }
    }

    public void startScroll() {
        this.isStop = false;
        getTextWidth();
        removeCallbacks(this);
        post(this);
    }

    public void stopScroll() {
        this.isStop = true;
    }

    public void startFor0() {
        Log.i("TAG", "startFor0: ");
        this.currentScroll = 0;
        startScroll();
    }

    public void setMarqueeDirection(int i) {
        this.mMarqueeDirection = i;
    }

    public void setMarqueeSpeed(int i) {
        this.mMarqueeSpeed = i;
    }

    public void setText(CharSequence charSequence, TextView.BufferType bufferType) {
        super.setText(charSequence, bufferType);
        startScroll();
    }

    public void destroyDrawingCache() {
        super.destroyDrawingCache();
    }

    /* access modifiers changed from: protected */
    public void onVisibilityChanged(View view, int i) {
        super.onVisibilityChanged(view, i);
        if (i == 0) {
            startFor0();
        } else {
            stopScroll();
        }
    }
}
