package com.szchoiceway.zxwlib.view;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;

public class DayNightSeekBar2 extends DayNightSeekBar {
    private Context mContext;

    public DayNightSeekBar2(Context context) {
        super(context);
        this.mContext = context;
    }

    public DayNightSeekBar2(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mContext = context;
    }

    public DayNightSeekBar2(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mContext = context;
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        canvas.save();
        if (isLayoutRtl()) {
            canvas.scale(-1.0f, 1.0f, (float) (getWidth() / 2), 0.0f);
        }
        int i = getImageWidthHeight(this.mProgressDrawableDay)[1];
        float f = ((float) (this.mMeasureHeight - i)) * 0.5f;
        canvas.drawBitmap(this.mInNightMode ? this.mBackgroundBitmapNightM : this.mBackgroundBitmapDayM, 0.0f, f, this.mPaint);
        Rect rect = new Rect();
        rect.top = 0;
        rect.left = 0;
        rect.right = (int) this.x;
        rect.bottom = i;
        Rect rect2 = new Rect();
        rect2.top = (int) f;
        rect2.left = 0;
        rect2.right = (int) this.x;
        rect2.bottom = rect2.top + i;
        canvas.drawBitmap(this.mInNightMode ? this.mProgressBitmapNightM : this.mProgressBitmapDayM, rect, rect2, this.mPaint);
        if (this.x > 1.0f) {
            if (this.x < ((float) this.mThumbBitmap.getWidth())) {
                canvas.drawBitmap(this.mInNightMode ? this.mThumbBitmapNight : this.mThumbBitmap, 0.0f, 0.0f, this.mPaint);
            } else {
                canvas.drawBitmap(this.mInNightMode ? this.mThumbBitmapNight : this.mThumbBitmap, this.x - ((float) this.mThumbBitmap.getWidth()), 0.0f, this.mPaint);
            }
        }
        canvas.restore();
    }

    public int[] getImageWidthHeight(int i) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(this.mContext.getResources(), i, options);
        return new int[]{options.outWidth, options.outHeight};
    }

    /* JADX WARNING: Code restructure failed: missing block: B:7:0x0011, code lost:
        if (r0 != 3) goto L_0x00a6;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean onTouchEvent(android.view.MotionEvent r6) {
        /*
            r5 = this;
            int r0 = r6.getAction()
            r1 = 1
            r2 = 1077936128(0x40400000, float:3.0)
            if (r0 == 0) goto L_0x0085
            r3 = 1065353216(0x3f800000, float:1.0)
            if (r0 == r1) goto L_0x0062
            r4 = 2
            if (r0 == r4) goto L_0x0015
            r6 = 3
            if (r0 == r6) goto L_0x0062
            goto L_0x00a6
        L_0x0015:
            boolean r0 = r5.isLayoutRtl()
            if (r0 == 0) goto L_0x0028
            int r0 = r5.getWidth()
            float r0 = (float) r0
            float r6 = r6.getX()
            float r0 = r0 - r6
            r5.mX = r0
            goto L_0x002e
        L_0x0028:
            float r6 = r6.getX()
            r5.mX = r6
        L_0x002e:
            float r6 = r5.mX
            int r0 = r5.mMeasureWidth
            float r0 = (float) r0
            int r6 = (r6 > r0 ? 1 : (r6 == r0 ? 0 : -1))
            if (r6 > 0) goto L_0x00a6
            float r6 = r5.mX
            r0 = 0
            int r6 = (r6 > r0 ? 1 : (r6 == r0 ? 0 : -1))
            if (r6 < 0) goto L_0x00a6
            float r6 = r5.mX
            int r6 = (r6 > r2 ? 1 : (r6 == r2 ? 0 : -1))
            if (r6 >= 0) goto L_0x0046
            r5.mX = r2
        L_0x0046:
            float r6 = r5.mX
            r5.x = r6
            r5.invalidate()
            com.szchoiceway.zxwlib.view.DayNightSeekBar$OnSeekFreqListener r6 = r5.mSeekListener
            if (r6 == 0) goto L_0x00a6
            float r6 = r5.mX
            int r0 = r5.mMaxProgress
            float r0 = (float) r0
            float r6 = r6 * r0
            float r6 = r6 * r3
            int r0 = r5.mMeasureWidth
            float r0 = (float) r0
            float r6 = r6 / r0
            com.szchoiceway.zxwlib.view.DayNightSeekBar$OnSeekFreqListener r0 = r5.mSeekListener
            r0.onSeeking(r6)
            goto L_0x00a6
        L_0x0062:
            com.szchoiceway.zxwlib.view.DayNightSeekBar$OnSeekFreqListener r6 = r5.mSeekListener
            if (r6 == 0) goto L_0x00a6
            float r6 = r5.mX
            int r0 = r5.mMeasureWidth
            float r0 = (float) r0
            int r6 = (r6 > r0 ? 1 : (r6 == r0 ? 0 : -1))
            if (r6 <= 0) goto L_0x0074
            int r6 = r5.mMeasureWidth
            float r6 = (float) r6
            r5.mX = r6
        L_0x0074:
            float r6 = r5.mX
            int r0 = r5.mMaxProgress
            float r0 = (float) r0
            float r6 = r6 * r0
            float r6 = r6 * r3
            int r0 = r5.mMeasureWidth
            float r0 = (float) r0
            float r6 = r6 / r0
            com.szchoiceway.zxwlib.view.DayNightSeekBar$OnSeekFreqListener r0 = r5.mSeekListener
            r0.onSeekStop(r6)
            goto L_0x00a6
        L_0x0085:
            boolean r0 = r5.isLayoutRtl()
            if (r0 == 0) goto L_0x0098
            int r0 = r5.getWidth()
            float r0 = (float) r0
            float r6 = r6.getX()
            float r0 = r0 - r6
            r5.mX = r0
            goto L_0x009e
        L_0x0098:
            float r6 = r6.getX()
            r5.mX = r6
        L_0x009e:
            float r6 = r5.mX
            int r6 = (r6 > r2 ? 1 : (r6 == r2 ? 0 : -1))
            if (r6 >= 0) goto L_0x00a6
            r5.mX = r2
        L_0x00a6:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.szchoiceway.zxwlib.view.DayNightSeekBar2.onTouchEvent(android.view.MotionEvent):boolean");
    }
}
