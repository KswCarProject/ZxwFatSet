package com.szchoiceway.zxwlib.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import com.szchoiceway.zxwlib.R;
import com.szchoiceway.zxwlib.utils.MultipleUtils;
import java.util.Timer;
import java.util.TimerTask;

public class DayNightSeekBar extends View {
    float a = 0.0f;
    private ValueAnimator animator = ValueAnimator.ofFloat(new float[]{1.0f});
    boolean hasScanOnce = false;
    public Bitmap mBackgroundBitmapDay;
    public Bitmap mBackgroundBitmapDayM;
    public Bitmap mBackgroundBitmapNight;
    public Bitmap mBackgroundBitmapNightM;
    public int mBackgroundDrawableDay;
    public int mBackgroundDrawableNight;
    private Context mContext;
    public int mCurProgress;
    boolean mInNightMode = false;
    public int mMaxProgress;
    public int mMeasureHeight = 0;
    public int mMeasureWidth = 0;
    public boolean mNeedScanOnce = false;
    float mOldXX = 3.0f;
    public Paint mPaint;
    public Bitmap mProgressBitmapDay;
    public Bitmap mProgressBitmapDayM;
    public Bitmap mProgressBitmapNight;
    public Bitmap mProgressBitmapNightM;
    public int mProgressDrawableDay;
    public int mProgressDrawableNight;
    public OnSeekFreqListener mSeekListener;
    private boolean mSmoothSlide = true;
    public Bitmap mThumbBitmap;
    public Bitmap mThumbBitmapNight;
    public int mThumbDrawable;
    public int mThumbDrawableNight;
    float mX = 0.0f;
    float mXX = 3.0f;
    boolean refreshByProgress = false;
    float x = 0.0f;

    public interface OnSeekFreqListener {
        void onSeekStop(float f);

        void onSeeking(float f);
    }

    public DayNightSeekBar(Context context) {
        super(context);
    }

    public DayNightSeekBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context, attributeSet);
    }

    public DayNightSeekBar(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context, attributeSet);
    }

    private void init(Context context, AttributeSet attributeSet) {
        this.mContext = context;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.DayNightSeekBar);
        this.mProgressDrawableDay = obtainStyledAttributes.getResourceId(R.styleable.DayNightSeekBar_seekBarProgressDrawableDay, 0);
        this.mProgressDrawableNight = obtainStyledAttributes.getResourceId(R.styleable.DayNightSeekBar_seekBarProgressDrawableNight, 0);
        this.mBackgroundDrawableDay = obtainStyledAttributes.getResourceId(R.styleable.DayNightSeekBar_seekBarBackgroundDrawableDay, 0);
        this.mBackgroundDrawableNight = obtainStyledAttributes.getResourceId(R.styleable.DayNightSeekBar_seekBarBackgroundDrawableNight, 0);
        this.mThumbDrawable = obtainStyledAttributes.getResourceId(R.styleable.DayNightSeekBar_seekBarThumbDrawable, 0);
        this.mThumbDrawableNight = obtainStyledAttributes.getResourceId(R.styleable.DayNightSeekBar_seekBarThumbDrawableNight, 0);
        this.mNeedScanOnce = obtainStyledAttributes.getBoolean(R.styleable.DayNightSeekBar_seekBarNeedScanOnce, false);
        this.mSmoothSlide = obtainStyledAttributes.getBoolean(R.styleable.DayNightSeekBar_seekBarSmoothSlide, true);
        this.mInNightMode = MultipleUtils.curUIModeNight(context.getResources().getConfiguration());
        this.mProgressBitmapDay = BitmapFactory.decodeResource(context.getResources(), this.mProgressDrawableDay);
        this.mProgressBitmapNight = BitmapFactory.decodeResource(context.getResources(), this.mProgressDrawableNight);
        this.mBackgroundBitmapDay = BitmapFactory.decodeResource(context.getResources(), this.mBackgroundDrawableDay);
        this.mBackgroundBitmapNight = BitmapFactory.decodeResource(context.getResources(), this.mBackgroundDrawableNight);
        this.mThumbBitmap = BitmapFactory.decodeResource(context.getResources(), this.mThumbDrawable);
        this.mThumbBitmapNight = BitmapFactory.decodeResource(context.getResources(), this.mThumbDrawableNight);
        Paint paint = new Paint();
        this.mPaint = paint;
        paint.setAntiAlias(true);
        obtainStyledAttributes.recycle();
        this.animator.setDuration(350);
        this.animator.setInterpolator(new AccelerateInterpolator());
        this.animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float animatedFraction = valueAnimator.getAnimatedFraction();
                DayNightSeekBar dayNightSeekBar = DayNightSeekBar.this;
                dayNightSeekBar.x = dayNightSeekBar.mOldXX - ((DayNightSeekBar.this.mOldXX - DayNightSeekBar.this.mXX) * animatedFraction);
                DayNightSeekBar.this.invalidate();
            }
        });
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        this.mMeasureWidth = View.MeasureSpec.getSize(i);
        int size = View.MeasureSpec.getSize(i2);
        this.mMeasureHeight = size;
        setMeasuredDimension(this.mMeasureWidth, size);
        if (!this.mNeedScanOnce) {
            this.refreshByProgress = true;
        } else if (!this.hasScanOnce) {
            this.hasScanOnce = true;
        }
        resetBitmapSize();
    }

    private void resetBitmapSize() {
        this.mBackgroundBitmapNightM = alterSizeBitmap(this.mBackgroundBitmapNight, this.mMeasureWidth, 1);
        this.mBackgroundBitmapDayM = alterSizeBitmap(this.mBackgroundBitmapDay, this.mMeasureWidth, 1);
        this.mProgressBitmapNightM = alterSizeBitmap(this.mProgressBitmapNight, this.mMeasureWidth, 1);
        this.mProgressBitmapDayM = alterSizeBitmap(this.mProgressBitmapDay, this.mMeasureWidth, 1);
    }

    public Bitmap alterSizeBitmap(Bitmap bitmap, int i, int i2) {
        Matrix matrix = new Matrix();
        matrix.postScale(((float) i) / ((float) bitmap.getWidth()), 1.0f);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.translate(0.0f, (float) (-((this.mBackgroundBitmapNight.getHeight() - this.mMeasureHeight) / 2)));
        canvas.drawBitmap(this.mInNightMode ? this.mBackgroundBitmapNightM : this.mBackgroundBitmapDayM, 0.0f, 0.0f, this.mPaint);
        Rect rect = new Rect();
        rect.top = 0;
        rect.left = 0;
        rect.right = (int) this.x;
        rect.bottom = this.mMeasureHeight;
        canvas.drawBitmap(this.mInNightMode ? this.mProgressBitmapNightM : this.mProgressBitmapDayM, rect, rect, this.mPaint);
        float f = this.x;
        if (f > 1.0f) {
            if (f < ((float) this.mThumbBitmap.getWidth())) {
                Bitmap bitmap = this.mInNightMode ? this.mThumbBitmapNight : this.mThumbBitmap;
                float f2 = this.x;
                canvas.drawBitmap(Bitmap.createBitmap(bitmap, (int) (((float) this.mThumbBitmap.getWidth()) - f2), 0, (int) f2, this.mThumbBitmap.getHeight()), 0.0f, 0.0f, this.mPaint);
            } else {
                canvas.drawBitmap(this.mInNightMode ? this.mThumbBitmapNight : this.mThumbBitmap, this.x - ((float) this.mThumbBitmap.getWidth()), 0.0f, this.mPaint);
            }
        }
        canvas.restore();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:7:0x0011, code lost:
        if (r0 != 3) goto L_0x006f;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean onTouchEvent(android.view.MotionEvent r6) {
        /*
            r5 = this;
            int r0 = r6.getAction()
            r1 = 1
            r2 = 1077936128(0x40400000, float:3.0)
            if (r0 == 0) goto L_0x0063
            r3 = 1065353216(0x3f800000, float:1.0)
            if (r0 == r1) goto L_0x0046
            r4 = 2
            if (r0 == r4) goto L_0x0014
            r6 = 3
            if (r0 == r6) goto L_0x0046
            goto L_0x006f
        L_0x0014:
            float r6 = r6.getX()
            r5.mX = r6
            int r0 = r5.mMeasureWidth
            float r0 = (float) r0
            int r0 = (r6 > r0 ? 1 : (r6 == r0 ? 0 : -1))
            if (r0 > 0) goto L_0x006f
            r0 = 0
            int r0 = (r6 > r0 ? 1 : (r6 == r0 ? 0 : -1))
            if (r0 < 0) goto L_0x006f
            int r6 = (r6 > r2 ? 1 : (r6 == r2 ? 0 : -1))
            if (r6 >= 0) goto L_0x002c
            r5.mX = r2
        L_0x002c:
            float r6 = r5.mX
            r5.x = r6
            r5.invalidate()
            com.szchoiceway.zxwlib.view.DayNightSeekBar$OnSeekFreqListener r6 = r5.mSeekListener
            if (r6 == 0) goto L_0x006f
            float r0 = r5.mX
            int r2 = r5.mMaxProgress
            float r2 = (float) r2
            float r0 = r0 * r2
            float r0 = r0 * r3
            int r2 = r5.mMeasureWidth
            float r2 = (float) r2
            float r0 = r0 / r2
            r6.onSeeking(r0)
            goto L_0x006f
        L_0x0046:
            com.szchoiceway.zxwlib.view.DayNightSeekBar$OnSeekFreqListener r6 = r5.mSeekListener
            if (r6 == 0) goto L_0x006f
            float r0 = r5.mX
            int r2 = r5.mMeasureWidth
            float r4 = (float) r2
            int r0 = (r0 > r4 ? 1 : (r0 == r4 ? 0 : -1))
            if (r0 <= 0) goto L_0x0056
            float r0 = (float) r2
            r5.mX = r0
        L_0x0056:
            float r0 = r5.mX
            int r4 = r5.mMaxProgress
            float r4 = (float) r4
            float r0 = r0 * r4
            float r0 = r0 * r3
            float r2 = (float) r2
            float r0 = r0 / r2
            r6.onSeekStop(r0)
            goto L_0x006f
        L_0x0063:
            float r6 = r6.getX()
            r5.mX = r6
            int r6 = (r6 > r2 ? 1 : (r6 == r2 ? 0 : -1))
            if (r6 >= 0) goto L_0x006f
            r5.mX = r2
        L_0x006f:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.szchoiceway.zxwlib.view.DayNightSeekBar.onTouchEvent(android.view.MotionEvent):boolean");
    }

    /* access modifiers changed from: protected */
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        boolean curUIModeNight = MultipleUtils.curUIModeNight(configuration);
        if (curUIModeNight != this.mInNightMode) {
            this.mInNightMode = curUIModeNight;
            invalidate();
        }
    }

    public void setMax(int i) {
        this.mMaxProgress = i;
    }

    public void setProgress(int i) {
        if (i >= 0) {
            this.mCurProgress = i;
            if (!this.refreshByProgress) {
                return;
            }
            if (this.mSmoothSlide) {
                float f = ((((float) i) * 1.0f) / ((float) this.mMaxProgress)) * ((float) this.mMeasureWidth);
                this.mXX = f;
                if (f < 3.0f) {
                    this.mXX = 3.0f;
                }
                this.mOldXX = this.x;
                if (this.animator.isRunning()) {
                    this.animator.cancel();
                }
                this.animator.start();
                return;
            }
            float f2 = ((((float) i) * 1.0f) / ((float) this.mMaxProgress)) * ((float) this.mMeasureWidth);
            this.x = f2;
            if (f2 < 3.0f) {
                this.x = 3.0f;
            }
            invalidate();
        }
    }

    public void setOnSeekListener(OnSeekFreqListener onSeekFreqListener) {
        this.mSeekListener = onSeekFreqListener;
    }

    public void run() {
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                try {
                    DayNightSeekBar.this.a += 1.0f;
                    DayNightSeekBar dayNightSeekBar = DayNightSeekBar.this;
                    dayNightSeekBar.x = dayNightSeekBar.a * DayNightSeekBar.this.a;
                    if (DayNightSeekBar.this.x > ((float) DayNightSeekBar.this.mMeasureWidth)) {
                        DayNightSeekBar dayNightSeekBar2 = DayNightSeekBar.this;
                        dayNightSeekBar2.x = (float) dayNightSeekBar2.mMeasureWidth;
                    }
                    DayNightSeekBar.this.invalidate();
                    if (DayNightSeekBar.this.x >= ((float) DayNightSeekBar.this.mMeasureWidth)) {
                        DayNightSeekBar.this.refreshByProgress = true;
                        timer.cancel();
                        DayNightSeekBar.this.post(new Runnable() {
                            public void run() {
                                DayNightSeekBar.this.setProgress(DayNightSeekBar.this.mCurProgress);
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 0, 16);
    }
}
