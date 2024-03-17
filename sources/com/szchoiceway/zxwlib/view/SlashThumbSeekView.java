package com.szchoiceway.zxwlib.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import androidx.core.internal.view.SupportMenu;
import com.szchoiceway.zxwlib.R;

public class SlashThumbSeekView extends View {
    private float angle = 30.0f;
    private int mBarHeitht = 17;
    private int mCurProgress = 50;
    private int mMaxProgress = 100;
    public int mMeasureHeight = 0;
    public int mMeasureWidth = 0;
    Paint mPaint;
    Bitmap mProgressBackgroundBimap = null;
    private Bitmap mProgressBackgroundBimapM;
    Bitmap mProgressBackgroundBimapSmall = null;
    Bitmap mProgressBimap = null;
    private Bitmap mProgressBimapM;
    Bitmap mProgressBimapSmall = null;
    Paint mProgressPaint;
    public OnSeekListener mSeekListener;
    Bitmap mThumbBimap = null;
    private Bitmap mThumbBimapM;
    Bitmap mThumbBimapSmall = null;
    private int mThumbLeftOffset = 0;
    float mX = 0.0f;

    public interface OnSeekListener {
        void onSeekStop(int i);

        void onSeeking(int i);
    }

    public SlashThumbSeekView(Context context) {
        super(context);
    }

    public SlashThumbSeekView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context, attributeSet);
    }

    public SlashThumbSeekView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context, attributeSet);
    }

    public void setProgressBackgroundBimap(Bitmap bitmap) {
        this.mProgressBackgroundBimapM = bitmap;
    }

    public void setProgressBimap(Bitmap bitmap) {
        this.mProgressBimapM = bitmap;
    }

    public void setThumbBimap(Bitmap bitmap) {
        this.mThumbBimapM = bitmap;
    }

    public void reLoad(boolean z) {
        if (z) {
            Bitmap bitmap = this.mProgressBackgroundBimap;
            if (bitmap != null) {
                this.mProgressBackgroundBimapM = bitmap;
            }
            Bitmap bitmap2 = this.mProgressBimap;
            if (bitmap2 != null) {
                this.mProgressBimapM = bitmap2;
            }
            Bitmap bitmap3 = this.mThumbBimap;
            if (bitmap3 != null) {
                this.mThumbBimapM = bitmap3;
                return;
            }
            return;
        }
        Bitmap bitmap4 = this.mProgressBackgroundBimapSmall;
        if (bitmap4 != null) {
            this.mProgressBackgroundBimapM = bitmap4;
        }
        Bitmap bitmap5 = this.mProgressBimapSmall;
        if (bitmap5 != null) {
            this.mProgressBimapM = bitmap5;
        }
        Bitmap bitmap6 = this.mThumbBimapSmall;
        if (bitmap6 != null) {
            this.mThumbBimapM = bitmap6;
        }
    }

    private void init(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.SlashThumbSeekView);
        this.mProgressBackgroundBimap = getBitmap(this.mContext, obtainStyledAttributes.getResourceId(R.styleable.SlashThumbSeekView_progressBackgroundBimap, 0));
        this.mProgressBimap = getBitmap(this.mContext, obtainStyledAttributes.getResourceId(R.styleable.SlashThumbSeekView_progressBimap, 0));
        this.mThumbBimap = getBitmap(context, obtainStyledAttributes.getResourceId(R.styleable.SlashThumbSeekView_thumbBimap, 0));
        this.mProgressBackgroundBimapSmall = getBitmap(this.mContext, obtainStyledAttributes.getResourceId(R.styleable.SlashThumbSeekView_progressBackgroundBimapSmall, 0));
        this.mProgressBimapSmall = getBitmap(this.mContext, obtainStyledAttributes.getResourceId(R.styleable.SlashThumbSeekView_progressBimapSmall, 0));
        this.mThumbBimapSmall = getBitmap(context, obtainStyledAttributes.getResourceId(R.styleable.SlashThumbSeekView_thumbBimapSmall, 0));
        this.mBarHeitht = obtainStyledAttributes.getInt(R.styleable.SlashThumbSeekView_barHeitht, this.mBarHeitht);
        this.mThumbLeftOffset = obtainStyledAttributes.getInt(R.styleable.SlashThumbSeekView_thumbLeftOffset, this.mThumbLeftOffset);
        obtainStyledAttributes.recycle();
        Paint paint = new Paint();
        this.mPaint = paint;
        paint.setAntiAlias(true);
        Paint paint2 = new Paint();
        this.mProgressPaint = paint2;
        paint2.setAntiAlias(true);
        this.mProgressPaint.setStrokeWidth(2.0f);
        this.mProgressPaint.setColor(SupportMenu.CATEGORY_MASK);
        this.mProgressPaint.setStyle(Paint.Style.STROKE);
        reLoad(true);
    }

    private Bitmap getBitmap(Context context, int i) {
        return BitmapFactory.decodeResource(context.getResources(), i);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        this.mMeasureWidth = View.MeasureSpec.getSize(i);
        this.mMeasureHeight = View.MeasureSpec.getSize(i2);
        Log.i("TAG", "onMeasureffff: " + this.mMeasureWidth + "  |  " + this.mMeasureHeight);
        setMeasuredDimension(this.mMeasureWidth, this.mMeasureHeight);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mProgressBackgroundBimapM != null && this.mProgressBimapM != null && this.mThumbBimapM != null) {
            canvas.save();
            canvas.drawBitmap(this.mProgressBackgroundBimapM, 0.0f, 0.0f, this.mPaint);
            canvas.restore();
            canvas.save();
            Path path = new Path();
            int height = getHeight();
            int width = getWidth();
            path.moveTo(0.0f, 0.0f);
            float f = (float) height;
            path.lineTo(0.0f, f);
            float f2 = (((float) (this.mCurProgress * width)) * 1.0f) / ((float) this.mMaxProgress);
            float width2 = f2 - ((float) this.mThumbBimapM.getWidth());
            path.lineTo(width2, f);
            path.lineTo(width2, f - (((float) (height - this.mBarHeitht)) * 0.5f));
            path.lineTo(f2, ((float) (height - this.mBarHeitht)) * 0.5f);
            path.lineTo(f2, 0.0f);
            path.lineTo(0.0f, 0.0f);
            canvas.clipPath(path);
            canvas.drawBitmap(this.mProgressBimapM, 0.0f, 0.0f, this.mProgressPaint);
            canvas.restore();
            canvas.save();
            canvas.drawBitmap(this.mThumbBimapM, width2 - ((float) this.mThumbLeftOffset), 0.0f, this.mPaint);
            canvas.restore();
        }
    }

    public void setMaxProgress(int i) {
        this.mMaxProgress = i;
    }

    public void setCurProgress(int i) {
        this.mCurProgress = i;
        postInvalidate();
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
            if (r0 == r1) goto L_0x0045
            r4 = 2
            if (r0 == r4) goto L_0x0014
            r6 = 3
            if (r0 == r6) goto L_0x0045
            goto L_0x006f
        L_0x0014:
            float r6 = r6.getX()
            r5.mX = r6
            r0 = 0
            int r0 = (r6 > r0 ? 1 : (r6 == r0 ? 0 : -1))
            if (r0 < 0) goto L_0x006f
            int r0 = r5.mMeasureWidth
            float r4 = (float) r0
            int r4 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1))
            if (r4 < 0) goto L_0x002a
            float r6 = (float) r0
            r5.mX = r6
            goto L_0x0030
        L_0x002a:
            int r6 = (r6 > r2 ? 1 : (r6 == r2 ? 0 : -1))
            if (r6 >= 0) goto L_0x0030
            r5.mX = r2
        L_0x0030:
            float r6 = r5.mX
            int r2 = r5.mMaxProgress
            float r2 = (float) r2
            float r6 = r6 * r2
            float r6 = r6 * r3
            float r0 = (float) r0
            float r6 = r6 / r0
            int r6 = (int) r6
            r5.setCurProgress(r6)
            com.szchoiceway.zxwlib.view.SlashThumbSeekView$OnSeekListener r0 = r5.mSeekListener
            if (r0 == 0) goto L_0x006f
            r0.onSeeking(r6)
            goto L_0x006f
        L_0x0045:
            com.szchoiceway.zxwlib.view.SlashThumbSeekView$OnSeekListener r6 = r5.mSeekListener
            if (r6 == 0) goto L_0x006f
            float r0 = r5.mX
            int r2 = r5.mMeasureWidth
            float r4 = (float) r2
            int r0 = (r0 > r4 ? 1 : (r0 == r4 ? 0 : -1))
            if (r0 <= 0) goto L_0x0055
            float r0 = (float) r2
            r5.mX = r0
        L_0x0055:
            float r0 = r5.mX
            int r4 = r5.mMaxProgress
            float r4 = (float) r4
            float r0 = r0 * r4
            float r0 = r0 * r3
            float r2 = (float) r2
            float r0 = r0 / r2
            int r0 = (int) r0
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
        throw new UnsupportedOperationException("Method not decompiled: com.szchoiceway.zxwlib.view.SlashThumbSeekView.onTouchEvent(android.view.MotionEvent):boolean");
    }

    public void setOnSeekListener(OnSeekListener onSeekListener) {
        this.mSeekListener = onSeekListener;
    }
}
