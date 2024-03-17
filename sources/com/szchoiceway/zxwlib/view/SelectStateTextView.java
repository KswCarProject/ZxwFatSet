package com.szchoiceway.zxwlib.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.AccelerateInterpolator;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.internal.view.SupportMenu;
import com.szchoiceway.zxwlib.R;

public class SelectStateTextView extends AppCompatTextView {
    private int animDurition = 200;
    /* access modifiers changed from: private */
    public ValueAnimator animator = ValueAnimator.ofFloat(new float[]{1.0f});
    private int linePaintWidth = 1;
    private Paint mBKPaint = new Paint();
    private int mBkColor = -16776961;
    private int mColor = SupportMenu.CATEGORY_MASK;
    private Paint mLinePaint = new Paint();
    private Paint mPaint = new Paint();
    /* access modifiers changed from: private */
    public float mRadius = 0.0f;
    private boolean mSelected = false;
    private int mTouchDown = -1;
    /* access modifiers changed from: private */
    public float mX = 0.0f;
    /* access modifiers changed from: private */
    public float mY = 0.0f;

    public SelectStateTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context, attributeSet);
    }

    private void init(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.SelectStateTextView);
        this.mColor = obtainStyledAttributes.getColor(R.styleable.SelectStateTextView_paintColor, this.mColor);
        this.mBkColor = obtainStyledAttributes.getColor(R.styleable.SelectStateTextView_bkColor, this.mBkColor);
        this.animDurition = obtainStyledAttributes.getInt(R.styleable.SelectStateTextView_animDuration, this.animDurition);
        obtainStyledAttributes.recycle();
        this.mPaint.setAntiAlias(true);
        this.mPaint.setStyle(Paint.Style.FILL);
        this.mPaint.setColor(this.mColor);
        this.mBKPaint.setAntiAlias(true);
        this.mBKPaint.setStyle(Paint.Style.FILL);
        this.mBKPaint.setColor(this.mBkColor);
        this.mLinePaint.setAntiAlias(true);
        this.mLinePaint.setStyle(Paint.Style.STROKE);
        this.mLinePaint.setStrokeWidth((float) this.linePaintWidth);
        this.mLinePaint.setColor(this.mColor);
        this.animator.setDuration((long) this.animDurition);
        this.animator.setInterpolator(new AccelerateInterpolator());
        this.animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int max = Math.max(SelectStateTextView.this.getHeight(), SelectStateTextView.this.getWidth());
                float unused = SelectStateTextView.this.mRadius = valueAnimator.getAnimatedFraction() * ((float) max);
                SelectStateTextView.this.invalidate();
            }
        });
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        Canvas canvas2 = canvas;
        if (this.mTouchDown == 0) {
            canvas.save();
            int height = getHeight();
            int width = getWidth();
            Path path = new Path();
            int i = height / 2;
            float f = (float) i;
            path.moveTo(f, 0.0f);
            float f2 = (float) height;
            float f3 = f2;
            path.arcTo(0.0f, 0.0f, f2, f2, 270.0f, -180.0f, true);
            int i2 = width - i;
            path.lineTo((float) i2, f3);
            int i3 = width - height;
            path.arcTo((float) i3, 0.0f, (float) width, f3, 90.0f, -180.0f, false);
            path.lineTo(f, 0.0f);
            canvas2.clipPath(path);
            canvas2.drawColor(this.mBkColor);
            canvas.restore();
            canvas.save();
            Path path2 = new Path();
            path2.moveTo(f, (float) this.linePaintWidth);
            int i4 = this.linePaintWidth;
            float f4 = (float) (height - i4);
            path2.arcTo((float) i4, (float) i4, (float) (height - i4), f4, 270.0f, -180.0f, true);
            int i5 = this.linePaintWidth;
            path2.lineTo((float) (i2 - i5), (float) (height - i5));
            int i6 = this.linePaintWidth;
            path2.arcTo((float) (i3 - i6), (float) i6, (float) (width - i6), (float) (height - i6), 90.0f, -180.0f, false);
            path2.lineTo(f, (float) this.linePaintWidth);
            canvas2.drawPath(path2, this.mLinePaint);
            canvas.restore();
        } else if (!this.mSelected) {
            canvas2.drawColor(0);
        } else {
            canvas.save();
            int height2 = getHeight();
            int width2 = getWidth();
            Path path3 = new Path();
            int i7 = height2 / 2;
            float f5 = (float) i7;
            path3.moveTo(f5, 0.0f);
            float f6 = (float) height2;
            float f7 = f6;
            path3.arcTo(0.0f, 0.0f, f6, f7, 270.0f, -180.0f, true);
            int i8 = width2 - i7;
            path3.lineTo((float) i8, f6);
            int i9 = width2 - height2;
            path3.arcTo((float) i9, 0.0f, (float) width2, f7, 90.0f, -180.0f, false);
            path3.lineTo(f5, 0.0f);
            canvas2.clipPath(path3);
            canvas2.drawCircle(this.mX, this.mY, this.mRadius, this.mPaint);
            canvas.restore();
            canvas.save();
            Path path4 = new Path();
            path4.moveTo(f5, (float) this.linePaintWidth);
            int i10 = this.linePaintWidth;
            Path path5 = path4;
            path5.arcTo((float) i10, (float) i10, (float) (height2 - i10), (float) (height2 - i10), 270.0f, -180.0f, true);
            int i11 = this.linePaintWidth;
            path4.lineTo((float) (i8 - i11), (float) (height2 - i11));
            int i12 = this.linePaintWidth;
            path5.arcTo((float) (i9 - i12), (float) i12, (float) (width2 - i12), (float) (height2 - i12), 90.0f, -180.0f, false);
            path4.lineTo(f5, (float) this.linePaintWidth);
            canvas2.drawPath(path4, this.mLinePaint);
            canvas.restore();
        }
        super.onDraw(canvas);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        this.mTouchDown = motionEvent.getAction();
        int action = motionEvent.getAction();
        if (action == 0) {
            this.mX = motionEvent.getX();
            this.mY = motionEvent.getY();
            invalidate();
        } else if (action == 1) {
            if (new Rect(0, 0, getWidth(), getHeight()).contains((int) motionEvent.getX(), (int) motionEvent.getY())) {
                performClick();
            }
            invalidate();
        } else if (action == 3) {
            invalidate();
        }
        return true;
    }

    public void setSelected(boolean z) {
        if (this.mSelected != z) {
            this.mTouchDown = 1;
            this.mSelected = z;
            if (z) {
                post(new Runnable() {
                    public void run() {
                        if (new Rect(0, 0, SelectStateTextView.this.getWidth(), SelectStateTextView.this.getHeight()).contains((int) SelectStateTextView.this.mX, (int) SelectStateTextView.this.mY)) {
                            if (SelectStateTextView.this.animator.isRunning()) {
                                SelectStateTextView.this.animator.cancel();
                            }
                            SelectStateTextView.this.animator.start();
                        }
                    }
                });
            } else {
                postInvalidate();
            }
        }
    }
}
