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

public class LandRoverSeekView extends View {
    private static final String TAG = "MySeekbar";
    private Bitmap landBg = null;
    private Bitmap landPro = null;
    private Bitmap landThumb = null;
    private int mCurProgress = 0;
    private int mMaxProgress = 1000;
    private int mMinProgress = 0;
    private Paint mPaint;
    public OnSeekListener mSeekListener;
    float mX = 0.0f;
    private Path path;

    public interface OnSeekListener {
        void onSeekStop(int i);

        void onSeeking(int i);
    }

    public LandRoverSeekView(Context context) {
        super(context);
    }

    public LandRoverSeekView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context, attributeSet);
    }

    public LandRoverSeekView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context, attributeSet);
    }

    private void init(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.LandRoverSeekView);
        this.landBg = getBitmap(context, obtainStyledAttributes.getResourceId(R.styleable.LandRoverSeekView_LandSeekBg, 0));
        this.landPro = getBitmap(context, obtainStyledAttributes.getResourceId(R.styleable.LandRoverSeekView_LandSeekProgress, 0));
        this.landThumb = getBitmap(context, obtainStyledAttributes.getResourceId(R.styleable.LandRoverSeekView_LandSeekThumb, 0));
        this.mMaxProgress = obtainStyledAttributes.getInt(R.styleable.LandRoverSeekView_LandSeekMax, 1000);
        this.mMinProgress = obtainStyledAttributes.getInt(R.styleable.LandRoverSeekView_LandSeekMin, 0);
        this.mCurProgress = obtainStyledAttributes.getInt(R.styleable.LandRoverSeekView_LandSeekCurrent, 500);
        Paint paint = new Paint();
        this.mPaint = paint;
        paint.setAntiAlias(true);
        obtainStyledAttributes.recycle();
        setCurrentProgress(this.mCurProgress);
    }

    public void setMaxProgress(int i) {
        this.mMaxProgress = i;
    }

    public void setCurrentProgress(int i) {
        this.mCurProgress = i;
        postInvalidate();
    }

    private Bitmap getBitmap(Context context, int i) {
        return BitmapFactory.decodeResource(context.getResources(), i);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        int i = this.mMaxProgress - this.mMinProgress;
        Bitmap bitmap = this.landThumb;
        if (bitmap != null && this.landPro != null && this.landBg != null) {
            float width2 = (((float) (this.mCurProgress * (width - this.landThumb.getWidth()))) * 1.0f) / ((float) i);
            float width3 = (float) (bitmap.getWidth() / 2);
            float f = width2 + width3;
            float height2 = (float) ((height - this.landBg.getHeight()) / 2);
            canvas.drawBitmap(this.landBg, width3, height2, this.mPaint);
            this.path = new Path();
            canvas.save();
            this.path.moveTo(0.0f, 0.0f);
            this.path.lineTo(f, 0.0f);
            float f2 = (float) height;
            this.path.lineTo(f, f2);
            this.path.lineTo(0.0f, f2);
            this.path.lineTo(0.0f, 0.0f);
            canvas.clipPath(this.path);
            canvas.drawBitmap(this.landPro, width3, height2, this.mPaint);
            canvas.restore();
            canvas.drawBitmap(this.landThumb, f - width3, 0.0f, this.mPaint);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:5:0x000d, code lost:
        if (r0 != 3) goto L_0x009e;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean onTouchEvent(android.view.MotionEvent r5) {
        /*
            r4 = this;
            int r0 = r5.getAction()
            r1 = 1065353216(0x3f800000, float:1.0)
            r2 = 1
            if (r0 == r2) goto L_0x0062
            r3 = 2
            if (r0 == r3) goto L_0x0011
            r5 = 3
            if (r0 == r5) goto L_0x0062
            goto L_0x009e
        L_0x0011:
            float r5 = r5.getX()
            r4.mX = r5
            r0 = 0
            int r0 = (r5 > r0 ? 1 : (r5 == r0 ? 0 : -1))
            if (r0 < 0) goto L_0x009e
            int r0 = r4.getWidth()
            float r0 = (float) r0
            int r5 = (r5 > r0 ? 1 : (r5 == r0 ? 0 : -1))
            if (r5 < 0) goto L_0x002d
            int r5 = r4.getWidth()
            float r5 = (float) r5
            r4.mX = r5
            goto L_0x0037
        L_0x002d:
            float r5 = r4.mX
            r0 = 1077936128(0x40400000, float:3.0)
            int r5 = (r5 > r0 ? 1 : (r5 == r0 ? 0 : -1))
            if (r5 >= 0) goto L_0x0037
            r4.mX = r0
        L_0x0037:
            float r5 = r4.mX
            int r0 = r4.mMaxProgress
            float r0 = (float) r0
            float r5 = r5 * r0
            float r5 = r5 * r1
            int r0 = r4.getWidth()
            float r0 = (float) r0
            float r5 = r5 / r0
            int r0 = r4.mMinProgress
            float r1 = (float) r0
            int r1 = (r5 > r1 ? 1 : (r5 == r1 ? 0 : -1))
            if (r1 >= 0) goto L_0x004c
            float r5 = (float) r0
        L_0x004c:
            int r0 = r4.mMaxProgress
            float r1 = (float) r0
            int r1 = (r5 > r1 ? 1 : (r5 == r1 ? 0 : -1))
            if (r1 <= 0) goto L_0x0054
            float r5 = (float) r0
        L_0x0054:
            int r0 = r4.mCurProgress
            int r5 = (int) r5
            if (r0 == r5) goto L_0x009e
            r4.setCurrentProgress(r5)
            com.szchoiceway.zxwlib.view.LandRoverSeekView$OnSeekListener r0 = r4.mSeekListener
            r0.onSeeking(r5)
            goto L_0x009e
        L_0x0062:
            com.szchoiceway.zxwlib.view.LandRoverSeekView$OnSeekListener r5 = r4.mSeekListener
            if (r5 == 0) goto L_0x009e
            float r5 = r4.mX
            int r0 = r4.getWidth()
            float r0 = (float) r0
            int r5 = (r5 > r0 ? 1 : (r5 == r0 ? 0 : -1))
            if (r5 <= 0) goto L_0x0078
            int r5 = r4.getWidth()
            float r5 = (float) r5
            r4.mX = r5
        L_0x0078:
            float r5 = r4.mX
            int r0 = r4.mMaxProgress
            float r0 = (float) r0
            float r5 = r5 * r0
            float r5 = r5 * r1
            int r0 = r4.getWidth()
            float r0 = (float) r0
            float r5 = r5 / r0
            int r0 = r4.mMinProgress
            float r1 = (float) r0
            int r1 = (r5 > r1 ? 1 : (r5 == r1 ? 0 : -1))
            if (r1 >= 0) goto L_0x008d
            float r5 = (float) r0
        L_0x008d:
            int r0 = r4.mMaxProgress
            float r1 = (float) r0
            int r1 = (r5 > r1 ? 1 : (r5 == r1 ? 0 : -1))
            if (r1 <= 0) goto L_0x0095
            float r5 = (float) r0
        L_0x0095:
            int r5 = (int) r5
            r4.setCurrentProgress(r5)
            com.szchoiceway.zxwlib.view.LandRoverSeekView$OnSeekListener r0 = r4.mSeekListener
            r0.onSeekStop(r5)
        L_0x009e:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.szchoiceway.zxwlib.view.LandRoverSeekView.onTouchEvent(android.view.MotionEvent):boolean");
    }

    public void setOnSeekListener(OnSeekListener onSeekListener) {
        this.mSeekListener = onSeekListener;
    }
}
