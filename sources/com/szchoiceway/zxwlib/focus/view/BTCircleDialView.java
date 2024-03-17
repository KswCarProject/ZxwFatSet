package com.szchoiceway.zxwlib.focus.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.graphics.Region;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import com.szchoiceway.zxwlib.GyroScopeWithCompassView;
import com.szchoiceway.zxwlib.R;
import java.util.Timer;
import java.util.TimerTask;

public class BTCircleDialView extends View implements FocusStateInterface {
    private static final int LONGPRESSTIME = 600;
    private static final int MOHUFANWEI = 10;
    private static final String TAG = "BTCircleDialView";
    boolean isCick = true;
    int lastX = 0;
    int lastY = 0;
    private float mAngle = 0.0f;
    private String[] mBtnCharList;
    private float[] mDegreeList;
    private int mDivisionsNumber = 15;
    private int mIndex = 0;
    private Bitmap mInnerCircleBitmap;
    private int mInnerCircleRadius = GyroScopeWithCompassView.CARTYPE_Nazhijie_U3_LO;
    public OnSelectChangeListener mListener;
    private Bitmap mOuterCircleBitmap;
    private int mOuterCircleRadius = GyroScopeWithCompassView.CARTYPE_NEWKAIYUE;
    private Paint mPaint;
    private float[] mPointList;
    private float mStartCoordinateX = 0.0f;
    private float mStartCoordinateY = 0.0f;
    private Bitmap mThumbBitmap;
    private Bitmap mThumbBitmapM;
    private Bitmap mThumbBitmapNormal;
    private int mThumbStartIndex = 6;
    private Timer timer;
    private TimerTask timerTask;

    public interface OnSelectChangeListener {
        void onClick(int i, String str, boolean z);

        void onSelect(String str);
    }

    public BTCircleDialView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context, attributeSet);
    }

    public void init(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.BTCircleDialView);
        this.mOuterCircleBitmap = getBitmap(this.mContext, obtainStyledAttributes.getResourceId(R.styleable.BTCircleDialView_outerCircleBitmap, 0));
        this.mInnerCircleBitmap = getBitmap(this.mContext, obtainStyledAttributes.getResourceId(R.styleable.BTCircleDialView_innerCircleBitmap, 0));
        this.mThumbBitmap = getBitmap(this.mContext, obtainStyledAttributes.getResourceId(R.styleable.BTCircleDialView_thumbBitmap, 0));
        this.mThumbBitmapNormal = getBitmap(this.mContext, obtainStyledAttributes.getResourceId(R.styleable.BTCircleDialView_thumbBitmapNormal, 0));
        this.mOuterCircleRadius = obtainStyledAttributes.getInt(R.styleable.BTCircleDialView_outerCircleRadius, this.mOuterCircleRadius);
        this.mInnerCircleRadius = obtainStyledAttributes.getInt(R.styleable.BTCircleDialView_innerCircleRadius, this.mInnerCircleRadius);
        this.mDivisionsNumber = obtainStyledAttributes.getInt(R.styleable.BTCircleDialView_divisionsNumber, this.mDivisionsNumber);
        this.mThumbStartIndex = obtainStyledAttributes.getInt(R.styleable.BTCircleDialView_thumbStartIndex, this.mThumbStartIndex);
        int resourceId = obtainStyledAttributes.getResourceId(R.styleable.BTCircleDialView_degreeList, 0);
        if (resourceId != 0) {
            String[] stringArray = context.getResources().getStringArray(resourceId);
            int length = stringArray.length;
            this.mDegreeList = new float[length];
            for (int i = 0; i < length; i++) {
                this.mDegreeList[i] = Float.valueOf(stringArray[i]).floatValue();
            }
        }
        int resourceId2 = obtainStyledAttributes.getResourceId(R.styleable.BTCircleDialView_btnList, 0);
        if (resourceId2 != 0) {
            this.mBtnCharList = context.getResources().getStringArray(resourceId2);
        }
        int resourceId3 = obtainStyledAttributes.getResourceId(R.styleable.BTCircleDialView_pointRect, 0);
        if (resourceId3 != 0) {
            String[] stringArray2 = context.getResources().getStringArray(resourceId3);
            int length2 = stringArray2.length;
            this.mPointList = new float[length2];
            for (int i2 = 0; i2 < length2; i2++) {
                this.mPointList[i2] = Float.valueOf(stringArray2[i2]).floatValue();
                Log.i(TAG, "init:sss " + this.mDegreeList[i2]);
            }
        }
        this.mStartCoordinateX = obtainStyledAttributes.getFloat(R.styleable.BTCircleDialView_startCoordinateX, 0.0f);
        this.mStartCoordinateY = obtainStyledAttributes.getFloat(R.styleable.BTCircleDialView_startCoordinateY, 0.0f);
        obtainStyledAttributes.recycle();
        this.mThumbBitmapM = this.mThumbBitmapNormal;
        Paint paint = new Paint();
        this.mPaint = paint;
        paint.setAntiAlias(true);
    }

    private Bitmap getBitmap(Context context, int i) {
        return BitmapFactory.decodeResource(context.getResources(), i);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Bitmap bitmap = this.mOuterCircleBitmap;
        if (bitmap != null) {
            canvas.drawBitmap(bitmap, 0.0f, 0.0f, this.mPaint);
        }
        Bitmap bitmap2 = this.mInnerCircleBitmap;
        if (bitmap2 != null) {
            canvas.drawBitmap(bitmap2, 0.0f, 0.0f, this.mPaint);
        }
        if (this.mThumbBitmapM != null) {
            canvas.save();
            canvas.rotate(this.mAngle, (float) (getWidth() / 2), (float) (getHeight() / 2));
            canvas.drawBitmap(this.mThumbBitmapM, 0.0f, 0.0f, this.mPaint);
            canvas.restore();
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        final float x = motionEvent.getX();
        final float y = motionEvent.getY();
        Log.i(TAG, "onTouchEvent: X = " + x + ", Y = " + y);
        int action = motionEvent.getAction();
        if (action == 0) {
            this.timer = new Timer();
            AnonymousClass1 r6 = new TimerTask() {
                public void run() {
                    BTCircleDialView.this.isCick = false;
                    Log.i(BTCircleDialView.TAG, "run: longClick ");
                    BTCircleDialView.this.post(new Runnable() {
                        public void run() {
                            BTCircleDialView.this.doClick(x, y, true);
                        }
                    });
                }
            };
            this.timerTask = r6;
            this.timer.schedule(r6, 600);
            this.isCick = true;
        } else if (action == 1) {
            if (this.isCick) {
                Log.i(TAG, "onTouchEvent: onClick ");
                doClick(x, y, false);
            }
            this.timerTask.cancel();
            this.timer.cancel();
        } else if (action == 2) {
            this.isCick = false;
            this.timerTask.cancel();
            this.timer.cancel();
        }
        return true;
    }

    /* access modifiers changed from: private */
    public void doClick(float f, float f2, boolean z) {
        boolean z2;
        String[] strArr;
        int i;
        int width = getWidth();
        int height = getHeight();
        Path path = new Path();
        float f3 = (float) (width / 2);
        float f4 = (float) (height / 2);
        path.moveTo(f3, f4);
        path.lineTo(f, f2);
        PathMeasure pathMeasure = new PathMeasure();
        pathMeasure.setPath(path, false);
        float length = pathMeasure.getLength();
        if (length > ((float) this.mInnerCircleRadius) && length < ((float) this.mOuterCircleRadius)) {
            float[] fArr = this.mPointList;
            if (fArr == null || fArr.length != 8) {
                z2 = true;
            } else {
                Path path2 = new Path();
                float[] fArr2 = this.mPointList;
                path2.moveTo(fArr2[0], fArr2[1]);
                float[] fArr3 = this.mPointList;
                path2.lineTo(fArr3[2], fArr3[3]);
                float[] fArr4 = this.mPointList;
                path2.lineTo(fArr4[4], fArr4[5]);
                float[] fArr5 = this.mPointList;
                path2.lineTo(fArr5[6], fArr5[7]);
                RectF rectF = new RectF();
                path2.computeBounds(rectF, true);
                Region region = new Region();
                region.setPath(path2, new Region((int) rectF.left, (int) rectF.top, (int) rectF.right, (int) rectF.bottom));
                z2 = region.contains((int) f, (int) f2);
            }
            double actionDegrees = getActionDegrees(f3, f4, this.mStartCoordinateX, this.mStartCoordinateY, f, f2, z2);
            Log.i(TAG, "doClick:degree " + actionDegrees);
            float[] fArr6 = this.mDegreeList;
            if (fArr6 != null) {
                int length2 = fArr6.length;
                for (int i2 = 0; i2 < length2; i2++) {
                    float f5 = this.mDegreeList[i2];
                    if (actionDegrees > ((double) f5)) {
                        this.mAngle = f5;
                        this.mIndex = i2;
                    }
                }
            } else {
                float f6 = 360.0f / ((float) this.mDivisionsNumber);
                Log.i(TAG, "doClick: " + f6);
                this.mAngle = ((float) (((int) (actionDegrees / ((double) f6))) + this.mThumbStartIndex)) * f6;
            }
            OnSelectChangeListener onSelectChangeListener = this.mListener;
            if (!(onSelectChangeListener == null || (strArr = this.mBtnCharList) == null || strArr.length <= (i = this.mIndex))) {
                onSelectChangeListener.onClick(i, strArr[i], true);
            }
            postInvalidate();
        }
    }

    public double getActionDegrees(float f, float f2, float f3, float f4, float f5, float f6, boolean z) {
        float f7 = f3 - f5;
        float f8 = f4 - f6;
        double sqrt = Math.sqrt((double) ((f7 * f7) + (f8 * f8)));
        float f9 = f - f5;
        float f10 = f2 - f6;
        double sqrt2 = Math.sqrt((double) ((f9 * f9) + (f10 * f10)));
        float f11 = f3 - f;
        float f12 = f4 - f2;
        double sqrt3 = Math.sqrt((double) ((f11 * f11) + (f12 * f12)));
        double acos = (Math.acos((((sqrt2 * sqrt2) + (sqrt3 * sqrt3)) - (sqrt * sqrt)) / ((sqrt2 * 2.0d) * sqrt3)) * 180.0d) / 3.141592653589793d;
        Log.i(TAG, "getActionDegrees: " + acos);
        if (!z) {
            acos = 360.0d - acos;
        }
        Log.i(TAG, "getActionDegrees:ff--- " + acos);
        return acos;
    }

    public void doClick() {
        String[] strArr;
        int i;
        OnSelectChangeListener onSelectChangeListener = this.mListener;
        if (onSelectChangeListener != null && (strArr = this.mBtnCharList) != null && strArr.length > (i = this.mIndex)) {
            onSelectChangeListener.onClick(i, strArr[i], false);
        }
    }

    public void setFocusIndex(int i) {
        String[] strArr;
        float[] fArr = this.mDegreeList;
        if (fArr != null && fArr.length > i) {
            this.mAngle = fArr[i];
        }
        this.mIndex = i;
        OnSelectChangeListener onSelectChangeListener = this.mListener;
        if (onSelectChangeListener != null && (strArr = this.mBtnCharList) != null && strArr.length > i) {
            onSelectChangeListener.onSelect(strArr[i]);
        }
    }

    public int getMaxLength() {
        float[] fArr = this.mDegreeList;
        if (fArr != null) {
            return fArr.length;
        }
        return this.mDivisionsNumber;
    }

    public void setThumbBitmap(Bitmap bitmap) {
        this.mThumbBitmap = bitmap;
        this.mThumbBitmapM = bitmap;
        postInvalidate();
    }

    public int getCurIndex() {
        return this.mIndex;
    }

    public void setOnSelectChangeListener(OnSelectChangeListener onSelectChangeListener) {
        this.mListener = onSelectChangeListener;
    }

    public void setFocusedState() {
        this.mThumbBitmapM = this.mThumbBitmap;
        postInvalidate();
    }

    public void clearFocusedState() {
        this.mThumbBitmapM = this.mThumbBitmapNormal;
        postInvalidate();
    }
}
