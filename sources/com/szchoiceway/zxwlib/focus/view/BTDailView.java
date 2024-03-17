package com.szchoiceway.zxwlib.focus.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import androidx.core.internal.view.SupportMenu;
import com.szchoiceway.zxwlib.GyroScopeWithCompassView;
import com.szchoiceway.zxwlib.R;
import com.szchoiceway.zxwlib.utils.LogUtils;
import java.util.Timer;
import java.util.TimerTask;

public class BTDailView extends View implements FocusStateInterface {
    public static final int DAIL_NUM0 = 14;
    public static final int DAIL_NUM1 = 0;
    public static final int DAIL_NUM2 = 1;
    public static final int DAIL_NUM3 = 2;
    public static final int DAIL_NUM4 = 3;
    public static final int DAIL_NUM5 = 4;
    public static final int DAIL_NUM6 = 5;
    public static final int DAIL_NUM7 = 6;
    public static final int DAIL_NUM8 = 7;
    public static final int DAIL_NUM9 = 8;
    public static final int DAIL_NUM_DAIL = 11;
    public static final int DAIL_NUM_DEL = 12;
    public static final int DAIL_NUM_J = 10;
    public static final int DAIL_NUM_P = 13;
    public static final int DAIL_NUM_X = 9;
    private static final int LONGPRESSTIME = 600;
    private static final int MOHUFANWEI = 10;
    public static final String TAG = "BTDailView";
    protected float[] degreeLst;
    /* access modifiers changed from: private */
    public boolean hasDownEvt;
    private int iTouchMaxRange;
    private int iTouchMinRange;
    boolean isCick;
    int lastX;
    int lastY;
    protected float mCurFocusDegree;
    private Bitmap mDailDownBmp;
    private Bitmap mDailFocusBmp;
    private Bitmap mDailSelectBmp;
    private int mDowmDrawableId;
    private int mFocusDrawableId;
    protected int mFocusIdx;
    int mFrameColor;
    int mFrameWidth;
    boolean mInFocus;
    private OnChangeListener mListener;
    protected int mMidIndex;
    boolean mNeedDrawFocusFrame;
    protected float mOffset;
    private Paint mPaint;
    Paint mPaintFrame;
    private boolean mbFocusMode;
    private Handler mhandler;
    private Timer timer;
    private TimerTask timerTask;
    private int xCenter;
    private int yCenter;

    public interface OnChangeListener {
        void onSelectChange(int i, boolean z);
    }

    public BTDailView(Context context) {
        this(context, (AttributeSet) null);
    }

    public BTDailView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
        init(context, attributeSet);
    }

    public BTDailView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.xCenter = 0;
        this.yCenter = 0;
        this.mDowmDrawableId = R.drawable.kesaiwei_1280x480_evo_id7_dail_num_f;
        this.mFocusDrawableId = R.drawable.kesaiwei_1280x480_evo_id7_dail_num_f;
        this.mDailDownBmp = null;
        this.mDailFocusBmp = null;
        this.mDailSelectBmp = null;
        this.mFocusIdx = 0;
        this.mCurFocusDegree = 0.0f;
        this.hasDownEvt = false;
        this.mOffset = 12.0f;
        this.mMidIndex = 7;
        this.mbFocusMode = true;
        this.iTouchMaxRange = GyroScopeWithCompassView.CARTYPE_DAOQI_GOMGYANG_LO;
        this.iTouchMinRange = GyroScopeWithCompassView.CARTYPE_MACAN_HI;
        this.degreeLst = new float[]{0.0f, 25.71f, 51.43f, 77.14f, 102.86f, 128.57f, 154.29f, 180.0f, 205.71f, 231.43f, 258.14f, 282.86f, 308.57f, 334.29f};
        this.mInFocus = false;
        this.mPaintFrame = new Paint();
        this.mNeedDrawFocusFrame = false;
        this.mFrameColor = SupportMenu.CATEGORY_MASK;
        this.mFrameWidth = 2;
        this.isCick = true;
        this.lastX = 0;
        this.lastY = 0;
        this.mhandler = new Handler() {
            public void handleMessage(Message message) {
                int i;
                int i2 = message.what;
                int i3 = 1;
                if (i2 == 0) {
                    float f = BTDailView.this.degreeLst[BTDailView.this.mFocusIdx];
                    if (Math.abs(f - BTDailView.this.mCurFocusDegree) % 360.0f <= 1.0f || Math.abs(f - BTDailView.this.mCurFocusDegree) % 360.0f >= 359.0f) {
                        BTDailView bTDailView = BTDailView.this;
                        bTDailView.mCurFocusDegree = bTDailView.degreeLst[BTDailView.this.mFocusIdx];
                        BTDailView.this.invalidate();
                        return;
                    }
                    float abs = Math.abs(f - BTDailView.this.mCurFocusDegree);
                    if (Math.abs(f - BTDailView.this.mCurFocusDegree) > 180.0f) {
                        abs = 360.0f - abs;
                        i = -1;
                    } else {
                        i = 1;
                    }
                    if (f < BTDailView.this.mCurFocusDegree) {
                        i3 = -1;
                    }
                    BTDailView.this.mCurFocusDegree += ((float) (i * i3)) * (abs / 3.0f);
                    BTDailView.this.mCurFocusDegree %= 360.0f;
                    if (BTDailView.this.mCurFocusDegree < 0.0f) {
                        BTDailView.this.mCurFocusDegree += 360.0f;
                    }
                    Log.i(BTDailView.TAG, "mCurFocusDegree = " + BTDailView.this.mCurFocusDegree);
                    Log.i(BTDailView.TAG, "foucsDegree = " + f);
                    BTDailView.this.invalidate();
                    sendEmptyMessageDelayed(0, 40);
                } else if (i2 == 1) {
                    boolean unused = BTDailView.this.hasDownEvt = false;
                    BTDailView.this.invalidate();
                }
            }
        };
        this.mListener = null;
        init(context, attributeSet);
    }

    /* access modifiers changed from: protected */
    public void init(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.DailView);
        this.mDowmDrawableId = obtainStyledAttributes.getResourceId(R.styleable.DailView_downDrawable, this.mDowmDrawableId);
        this.mFocusDrawableId = obtainStyledAttributes.getResourceId(R.styleable.DailView_focusDrawable, this.mFocusDrawableId);
        this.iTouchMaxRange = obtainStyledAttributes.getInt(R.styleable.DailView_touchMaxRange, this.iTouchMaxRange);
        this.iTouchMinRange = obtainStyledAttributes.getInt(R.styleable.DailView_touchMinRange, this.iTouchMinRange);
        this.mNeedDrawFocusFrame = obtainStyledAttributes.getBoolean(R.styleable.DailView_needDrawFocusFrame, false);
        this.mFrameColor = obtainStyledAttributes.getColor(R.styleable.DailView_focusedFrameColor, this.mFrameColor);
        this.mFrameWidth = obtainStyledAttributes.getInt(R.styleable.DailView_focusedFrameWidth, this.mFrameWidth);
        obtainStyledAttributes.recycle();
        this.xCenter = getWidth() / 2;
        this.yCenter = getHeight() / 2;
        this.mPaint = new Paint(1);
        this.mDailDownBmp = ((BitmapDrawable) getResources().getDrawable(this.mDowmDrawableId)).getBitmap();
        this.mDailFocusBmp = ((BitmapDrawable) getResources().getDrawable(this.mFocusDrawableId)).getBitmap();
        this.mPaintFrame.setAntiAlias(true);
        this.mPaintFrame.setColor(this.mFrameColor);
        this.mPaintFrame.setStrokeWidth((float) this.mFrameWidth);
        this.mPaintFrame.setStyle(Paint.Style.STROKE);
    }

    public void unin() {
        this.mhandler.removeCallbacksAndMessages((Object) null);
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i2, i, i4, i3);
        this.xCenter = getWidth() / 2;
        this.yCenter = getHeight() / 2;
    }

    /* access modifiers changed from: protected */
    public synchronized void onMeasure(int i, int i2) {
        super.onMeasure(i2, i);
        setMeasuredDimension(getMeasuredHeight(), getMeasuredWidth());
        this.xCenter = getWidth() / 2;
        this.yCenter = getHeight() / 2;
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mbFocusMode) {
            Matrix matrix = new Matrix();
            matrix.postRotate(this.mCurFocusDegree, (float) this.xCenter, (float) this.yCenter);
            canvas.drawBitmap(this.mDailFocusBmp, matrix, (Paint) null);
            if (this.hasDownEvt) {
                Matrix matrix2 = new Matrix();
                Log.i(TAG, "onDraw: mFocusIdx = " + this.mFocusIdx);
                matrix2.postRotate(this.degreeLst[this.mFocusIdx], (float) this.xCenter, (float) this.yCenter);
            }
        }
        if (this.mInFocus && this.mNeedDrawFocusFrame) {
            canvas.save();
            LogUtils.i(TAG, "onDrawfocus: " + getWidth() + "  |  " + getHeight());
            canvas.drawRect(0.0f, 0.0f, (float) getWidth(), (float) getHeight(), this.mPaintFrame);
            canvas.restore();
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        final float x = motionEvent.getX();
        final float y = motionEvent.getY();
        Log.i(TAG, "onTouchEvent: X = " + x + ", Y = " + y);
        float width = (float) (getWidth() / 10);
        int i = (int) (y / width);
        int i2 = (int) (x / width);
        int action = motionEvent.getAction();
        if (action == 0) {
            this.timer = new Timer();
            AnonymousClass1 r10 = new TimerTask() {
                public void run() {
                    BTDailView.this.isCick = false;
                    Log.i(BTDailView.TAG, "run: longClick ");
                    BTDailView.this.post(new Runnable() {
                        public void run() {
                            BTDailView.this.doClick(x, y, true);
                        }
                    });
                }
            };
            this.timerTask = r10;
            this.timer.schedule(r10, 600);
            this.isCick = true;
            this.hasDownEvt = false;
        } else if (action == 1) {
            if (this.isCick) {
                Log.i(TAG, "onTouchEvent: onClick ");
                doClick(x, y, false);
            }
            this.timerTask.cancel();
            this.timer.cancel();
            Log.i(TAG, "onTouchEvent: mFocusIdx = " + this.mFocusIdx);
            this.mhandler.sendEmptyMessageDelayed(1, 100);
        } else if (action == 2) {
            if (this.lastX == i && this.lastY == i2) {
                return true;
            }
            this.isCick = false;
            this.timerTask.cancel();
            this.timer.cancel();
        }
        this.lastY = i2;
        this.lastX = i;
        return true;
    }

    /* access modifiers changed from: private */
    public void doClick(float f, float f2, boolean z) {
        float f3 = f - ((float) this.xCenter);
        float f4 = ((float) this.yCenter) - f2;
        double sqrt = Math.sqrt((double) ((f3 * f3) + (f4 * f4)));
        Log.i(TAG, "doClick: r = " + sqrt);
        if (sqrt >= ((double) this.iTouchMinRange) && sqrt <= ((double) this.iTouchMaxRange)) {
            double atan = (Math.atan((double) (f3 / f4)) * 180.0d) / 3.141592653589793d;
            Log.i(TAG, "onTouchEvent: angle = " + atan);
            if (f4 < 0.0f) {
                atan += 180.0d;
            }
            double d = atan % 360.0d;
            if (d < 0.0d) {
                d += 360.0d;
            }
            int i = 0;
            while (true) {
                float[] fArr = this.degreeLst;
                if (i < fArr.length) {
                    float f5 = this.mOffset;
                    double d2 = (double) (fArr[i] + 180.0f + f5);
                    double d3 = ((double) ((fArr[i] + 180.0f) - f5)) % 360.0d;
                    if (d3 < 0.0d) {
                        d3 += 360.0d;
                    }
                    double d4 = d2 % 360.0d;
                    if (d4 < 0.0d) {
                        d4 += 360.0d;
                    }
                    if (i == this.mMidIndex) {
                        if ((d > d3 && d < 360.0d) || (d > 0.0d && d < d4)) {
                            this.mbFocusMode = true;
                            this.mFocusIdx = i;
                            this.hasDownEvt = true;
                            invalidate();
                            notifySelectCurDailNum(this.mFocusIdx, z);
                            this.mhandler.sendEmptyMessage(0);
                        }
                    } else if (d > d3 && d < d4) {
                        this.mbFocusMode = true;
                        this.mFocusIdx = i;
                        this.hasDownEvt = true;
                        invalidate();
                        notifySelectCurDailNum(this.mFocusIdx, z);
                        this.mhandler.sendEmptyMessage(0);
                        return;
                    }
                    i++;
                } else {
                    return;
                }
            }
            this.mbFocusMode = true;
            this.mFocusIdx = i;
            this.hasDownEvt = true;
            invalidate();
            notifySelectCurDailNum(this.mFocusIdx, z);
            this.mhandler.sendEmptyMessage(0);
        }
    }

    private void notifySelectCurDailNum(int i, boolean z) {
        OnChangeListener onChangeListener = this.mListener;
        if (onChangeListener != null) {
            onChangeListener.onSelectChange(i, z);
        }
    }

    public void rotateDailPaneDown() {
        int i = this.mFocusIdx + 1;
        this.mFocusIdx = i;
        if (i >= this.degreeLst.length) {
            this.mFocusIdx = 0;
        }
        invalidate();
        this.mhandler.sendEmptyMessage(0);
    }

    public void rotateDailPaneUp() {
        int i = this.mFocusIdx - 1;
        this.mFocusIdx = i;
        if (i < 0) {
            this.mFocusIdx = this.degreeLst.length - 1;
        }
        invalidate();
        this.mhandler.sendEmptyMessage(0);
    }

    public void enterDailPane(boolean z) {
        if (this.mbFocusMode) {
            this.hasDownEvt = true;
            invalidate();
            notifySelectCurDailNum(this.mFocusIdx, z);
            this.mhandler.sendEmptyMessage(0);
            this.mhandler.sendEmptyMessageDelayed(1, 200);
        }
    }

    public void setChangeListener(OnChangeListener onChangeListener) {
        this.mListener = onChangeListener;
    }

    public void setFocusMode(boolean z) {
        this.mbFocusMode = z;
        invalidate();
    }

    public int getFocusIndex() {
        return this.mFocusIdx;
    }

    public void setFocusIndex(int i) {
        Log.i(TAG, "setFocusIndex: " + i);
        this.mFocusIdx = i;
        this.hasDownEvt = true;
        invalidate();
        this.mhandler.sendEmptyMessage(0);
        this.mhandler.sendEmptyMessageDelayed(1, 200);
    }

    public void setFocusedState() {
        this.mInFocus = true;
        if (this.mNeedDrawFocusFrame) {
            postInvalidate();
        }
    }

    public void clearFocusedState() {
        this.mInFocus = false;
        if (this.mNeedDrawFocusFrame) {
            postInvalidate();
        }
    }
}
