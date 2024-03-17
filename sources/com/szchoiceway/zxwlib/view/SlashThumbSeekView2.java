package com.szchoiceway.zxwlib.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import com.szchoiceway.zxwlib.R;

public class SlashThumbSeekView2 extends View {
    private int mChamferHeight = 17;
    private int mChamferWidth = 10;
    private int mCurProgress = 50;
    private int mMaxProgress = 100;
    public int mMeasureHeight = 0;
    public int mMeasureWidth = 0;
    Paint mPaint;
    Bitmap mProgressBackgroundBimap = null;
    private Bitmap mProgressBackgroundBimapM;
    Bitmap mProgressBimap = null;
    private Bitmap mProgressBimapM;
    public OnSeekListener mSeekListener;
    Bitmap mThumbBimap = null;
    private Bitmap mThumbBimapM;
    float mX = 0.0f;

    public interface OnSeekListener {
        void onSeekStop(int i);

        void onSeeking(int i);
    }

    public SlashThumbSeekView2(Context context) {
        super(context);
    }

    public SlashThumbSeekView2(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context, attributeSet);
    }

    public SlashThumbSeekView2(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context, attributeSet);
    }

    public void setProgressBackgroundBimap(Bitmap bitmap) {
        this.mProgressBackgroundBimapM = bitmap;
        postInvalidate();
    }

    public void setProgressBimap(Bitmap bitmap) {
        this.mProgressBimapM = bitmap;
        postInvalidate();
    }

    public void setThumbBimap(Bitmap bitmap) {
        this.mThumbBimapM = bitmap;
        postInvalidate();
    }

    public void reLoad() {
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
        }
    }

    private void init(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.SlashThumbSeekView2);
        this.mProgressBackgroundBimap = getBitmap(this.mContext, obtainStyledAttributes.getResourceId(R.styleable.SlashThumbSeekView2_progressBackgroundBimap, 0));
        this.mProgressBimap = getBitmap(this.mContext, obtainStyledAttributes.getResourceId(R.styleable.SlashThumbSeekView2_progressBimap, 0));
        this.mThumbBimap = getBitmap(context, obtainStyledAttributes.getResourceId(R.styleable.SlashThumbSeekView2_thumbBimap, 0));
        this.mChamferWidth = obtainStyledAttributes.getInt(R.styleable.SlashThumbSeekView2_chamferWidth, this.mChamferWidth);
        this.mChamferHeight = obtainStyledAttributes.getInt(R.styleable.SlashThumbSeekView2_chamferHeight, this.mChamferHeight);
        obtainStyledAttributes.recycle();
        Paint paint = new Paint();
        this.mPaint = paint;
        paint.setAntiAlias(true);
        reLoad();
    }

    private Bitmap getBitmap(Context context, int i) {
        return BitmapFactory.decodeResource(context.getResources(), i);
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
        if (this.mProgressBackgroundBimapM != null) {
            canvas.save();
            canvas.drawBitmap(this.mProgressBackgroundBimapM, 0.0f, 0.0f, this.mPaint);
            canvas.restore();
        }
        if (this.mProgressBimapM != null) {
            canvas.save();
            Path path = new Path();
            int height = getHeight();
            int width = getWidth();
            path.moveTo(0.0f, 0.0f);
            float f = (float) height;
            path.lineTo(0.0f, f);
            float f2 = (((float) (this.mCurProgress * width)) * 1.0f) / ((float) this.mMaxProgress);
            path.lineTo(f2, f);
            float f3 = f2 - ((float) this.mChamferWidth);
            path.lineTo(f3, (float) (height - this.mChamferHeight));
            path.lineTo(f2, 0.0f);
            path.lineTo(0.0f, 0.0f);
            canvas.clipPath(path);
            canvas.drawBitmap(this.mProgressBimapM, 0.0f, 0.0f, this.mPaint);
            canvas.restore();
            if (this.mThumbBimapM != null) {
                canvas.save();
                canvas.drawBitmap(this.mThumbBimapM, f3, 0.0f, this.mPaint);
                canvas.restore();
            }
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
            com.szchoiceway.zxwlib.view.SlashThumbSeekView2$OnSeekListener r0 = r5.mSeekListener
            if (r0 == 0) goto L_0x006f
            r0.onSeeking(r6)
            goto L_0x006f
        L_0x0045:
            com.szchoiceway.zxwlib.view.SlashThumbSeekView2$OnSeekListener r6 = r5.mSeekListener
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
        throw new UnsupportedOperationException("Method not decompiled: com.szchoiceway.zxwlib.view.SlashThumbSeekView2.onTouchEvent(android.view.MotionEvent):boolean");
    }

    public void setOnSeekListener(OnSeekListener onSeekListener) {
        this.mSeekListener = onSeekListener;
    }
}
